import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectUnionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.reasoner.NodeSet;

public class DiscoveringRelationalAssociationRules 
{
    private IRI iriInputFull;

    private KnowledgeBase knowledgeBaseFull;
    private KnowledgeBase knowledgeBaseStratified;	
    private int z_fresh;

    //for statistic
    private int countRules;
    private int countRulesHavePrediction;
    private int countPredictions;
    private double totalMatchRate;
    private double totalInductionRate;

    public DiscoveringRelationalAssociationRules(KnowledgeBase knowledgeBaseFull, KnowledgeBase knowledgeBaseStratified, IRI iriInputFull)
    {	
            this.knowledgeBaseFull = knowledgeBaseFull;
            this.knowledgeBaseStratified = knowledgeBaseStratified;		
            this.iriInputFull = iriInputFull;
    }

    public void runFullVersion()
    {
        createKnowledgeBaseFull();

        this.z_fresh = 0;		
        Global.numberOfIndividualsInSignature = this.knowledgeBaseStratified.getOntology().getIndividualsInSignature().size();

        PatternList<Pattern<Atom>> discoveredRules = discoveringMultirelationalAssociationRules();

        OutputInformation.showDiscoveredRules(discoveredRules, false);

        //OutputPrediction output = new OutputPrediction();

        this.countRules = discoveredRules.size();
        this.countRulesHavePrediction = 0;
        this.countPredictions = 0;
        this.totalMatchRate = 0;
        this.totalInductionRate = 0;

        for(Pattern<Atom> discoveredRule : discoveredRules)
                statisticianForRuleFullVerion(discoveredRule);

        OutputInformation.showText("The number of rules: ", false);
        OutputInformation.showTextln(String.valueOf(this.countRules), false);
        OutputInformation.showText("The number of rules that have got the prediction: ", false);
        OutputInformation.showTextln(String.valueOf(this.countRulesHavePrediction), false);
        OutputInformation.showText("The number of rules that haven't got the prediction: ", false);
        OutputInformation.showTextln(String.valueOf(this.countRules - this.countRulesHavePrediction), false);
        OutputInformation.showText("Total number of predictions: ", false);
        OutputInformation.showTextln(String.valueOf(this.countPredictions), false);
        OutputInformation.showText("Average of Match rate: ", false);
        OutputInformation.showTextln(String.valueOf(this.totalMatchRate / this.countRulesHavePrediction), false);
        OutputInformation.showText("Average of Induction rate: ", false);
        OutputInformation.showTextln(String.valueOf(this.totalInductionRate / this.countRulesHavePrediction), false);

        for(Pattern<Atom> discoveredRule : discoveredRules)
                statisticianForRulePredictionMatchCommisionInduction(discoveredRule);


        //output.close();
    }
	
	private void createKnowledgeBaseFull()
	{
		Global.allFrequentConceptNamesFull = new HashMap();
        Global.allFrequentRoleNamesFull = new HashMap();
        
        Set<OWLClass> classes = this.knowledgeBaseFull.getOntology().getClassesInSignature(); 
        classes.remove(this.knowledgeBaseFull.getDataFactory().getOWLThing()); 
        for (OWLClass owlClass : classes)
        {
        	ConceptAtom conceptAtom = new ConceptAtom(owlClass.getIRI(), "c_" + Global.cutString(owlClass.getIRI().toString()));
        	NodeSet<OWLNamedIndividual> individualsNodeSet = this.knowledgeBaseFull.getReasoner().getInstances(this.knowledgeBaseFull.getDataFactory().getOWLClass(conceptAtom.getIRI()), false);
        	Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();
        	
        	ConceptIndividuals conceptIndividuals = new ConceptIndividuals();			
			for (OWLNamedIndividual individual : individuals)                    	
				conceptIndividuals.addIndividual(individual.asOWLNamedIndividual().getIRI().toString());
			
			Global.allFrequentConceptNamesFull.put(conceptAtom.getIRI(), conceptIndividuals);
        }
        
        Set<OWLObjectProperty> objectProperties = this.knowledgeBaseFull.getOntology().getObjectPropertiesInSignature();
        for (OWLObjectProperty owlObjectProperty : objectProperties) 
        {
        	RoleAtom roleAtom = new RoleAtom(owlObjectProperty.getIRI(), "x_" + Global.cutString(owlObjectProperty.getIRI().toString()), "y_" + Global.cutString(owlObjectProperty.getIRI().toString()));
        	Map<OWLNamedIndividual,Set<OWLNamedIndividual>> individuals = this.knowledgeBaseFull.getReasoner().getObjectPropertyInstances(this.knowledgeBaseFull.getDataFactory().getOWLObjectProperty(roleAtom.getIRI()));
        	Set<OWLNamedIndividual> individualsDomain = individuals.keySet();
        	
        	RoleIndividuals roleIndividuals = new RoleIndividuals();
    		
    		for(OWLNamedIndividual individualDomain : individualsDomain)
        	{	
				Set<OWLNamedIndividual> individualsRange = individuals.get(individualDomain);            		
        		for(OWLNamedIndividual individualRange : individualsRange)
        		{	
                    roleIndividuals.addDomainIndividual(individualDomain.getIRI().toString());
                    roleIndividuals.addRangeIndividual(individualRange.getIRI().toString());
        		}	
        	}
    		
    		Global.allFrequentRoleNamesFull.put(roleAtom.getIRI(), roleIndividuals);
        }
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public PatternList<Pattern<Atom>> discoveringMultirelationalAssociationRules()	
	{
		Global.allFrequentConceptNamesStratified = new HashMap();
        Global.allFrequentRoleNamesStratified = new HashMap();
        
        PatternList<Pattern<Atom>> infrequent = new PatternList<Pattern<Atom>>();
        PatternList<Pattern<Atom>> frequent = new PatternList<Pattern<Atom>>();
        
        PatternList<Pattern<Atom>> q = createGeneralPatterns();
        
        while(!q.isEmpty())
        {
        	Pattern<Atom> p = q.remove(0);
        	
        	PatternList<Pattern<Atom>> specPatternList = generateSpecializedPatterns(p);
        	
        	boolean specializationAdded = false;
        	
        	for(Pattern<Atom> p_commas : specPatternList)
        	{
        		//boolean pruned = evaluatePatternForPruning(p, p_commas, q, infrequent);
        		int pruned = evaluatePatternForPruning(p, p_commas, q, infrequent); 
        		
        		/*if (pruned)
        		{
        			p_commas.removeAllData();
        			infrequent.add(p_commas);
        		}
        		else if (p_commas.size() < Global.MAX_LENGTH)
        		{
        			q.add(p_commas);
        			specializationAdded = true;
        		}
        		else if (isSafetyConditionSatisfied(p_commas))
        		{
        			frequent.add(p_commas);
        			specializationAdded = true;
        		} */   	
        		
        		if (pruned == 2)
        		{
        			p_commas.removeAllData();
        			infrequent.add(p_commas);
        		}
        		else if ((pruned == 0) && (p_commas.size() < Global.MAX_LENGTH))
        		{
        			q.add(p_commas);
        			specializationAdded = true;
        		}
        		else if ((pruned == 0) && (isSafetyConditionSatisfied(p_commas)))
        		{
        			frequent.add(p_commas);
        			specializationAdded = true;
        		}   
        	}
        	
        	if ((!specializationAdded) && (p.size() >= 2))
        	{
        		Pattern<Atom> p_safety = getPatternOrAncestorPatternSatisfyingSafetyCondition(p);
        		if ((p_safety != null) && (!isPatternAlreadyGenerated(p_safety, frequent)))
        			frequent.add(p_safety);        			
        	}
        }
        
        return frequent;
	}
	
	@SuppressWarnings("unused")
	private PatternList<Pattern<Atom>> createGeneralPatterns()
    {
		PatternList<Pattern<Atom>> q = new PatternList<Pattern<Atom>>();
		
		Set<Atom> allNames = new LinkedHashSet<>();
		Set<OWLClass> classes = this.knowledgeBaseStratified.getOntology().getClassesInSignature(); 
        classes.remove(this.knowledgeBaseStratified.getDataFactory().getOWLThing());                
        for (OWLClass owlClass : classes)
        	allNames.add(new ConceptAtom(owlClass.getIRI(), "c_" + Global.cutString(owlClass.getIRI().toString())));
        
        OutputInformation.showSetOfOWLClass(classes, "Show all concepts in the ontology", false);
        	
        Set<OWLObjectProperty> objectProperties = this.knowledgeBaseStratified.getOntology().getObjectPropertiesInSignature();
        for (OWLObjectProperty owlObjectProperty : objectProperties) 
        	allNames.add(new RoleAtom(owlObjectProperty.getIRI(), "x_" + Global.cutString(owlObjectProperty.getIRI().toString()), "y_" + Global.cutString(owlObjectProperty.getIRI().toString())));
        
        OutputInformation.showSetOfOWLObjectProperty(objectProperties, "Show all roles in the ontology", false);
        
        for (Atom element : allNames)
        {
        	if (element instanceof ConceptAtom)				
            {
        		NodeSet<OWLNamedIndividual> individualsNodeSet = this.knowledgeBaseStratified.getReasoner().getInstances(this.knowledgeBaseStratified.getDataFactory().getOWLClass(element.getIRI()), false);
        		//NodeSet<OWLNamedIndividual> individualsNodeSet = this.knowledgeBaseStratified.getReasoner().getInstances(this.knowledgeBaseStratified.getDataFactory().getOWLClass(element.getIRI()), true);
        		Set<OWLNamedIndividual> individuals = individualsNodeSet.getFlattened();
        		
        		if (individuals.size() >= Global.FR_THR)
                {
        			ConceptIndividuals conceptIndividuals = new ConceptIndividuals();
        			
        			for (OWLNamedIndividual individual : individuals)                    	
        				conceptIndividuals.addIndividual(individual.asOWLNamedIndividual().getIRI().toString());
        			
        			Global.allFrequentConceptNamesStratified.put(element.getIRI(), conceptIndividuals);
        			
        			Pattern<Atom> newPatternConcept = new Pattern<Atom>();                    
                    newPatternConcept.add(new ConceptAtom(element.getIRI(), ((ConceptAtom) element).getVariable()));					
                    q.add(newPatternConcept);
                }
            }
        	else if (element instanceof RoleAtom)
        	{
        		Map<OWLNamedIndividual,Set<OWLNamedIndividual>> individuals = this.knowledgeBaseStratified.getReasoner().getObjectPropertyInstances(this.knowledgeBaseStratified.getDataFactory().getOWLObjectProperty(element.getIRI()));
        		
        		int objectPropertySize = 0;
        		Set<OWLNamedIndividual> individualsDomain = individuals.keySet();            	
            	for(OWLNamedIndividual individualDomain : individualsDomain)
            	{	
    				Set<OWLNamedIndividual> individualsRange = individuals.get(individualDomain);            		
            		for(OWLNamedIndividual individualRange : individualsRange) 
            			objectPropertySize++;
            	}
            	
            	if (objectPropertySize >= Global.FR_THR)
                {
            		RoleIndividuals roleIndividuals = new RoleIndividuals();
            		
            		for(OWLNamedIndividual individualDomain : individualsDomain)
                	{	
        				Set<OWLNamedIndividual> individualsRange = individuals.get(individualDomain);            		
                		for(OWLNamedIndividual individualRange : individualsRange)
                		{	
                            roleIndividuals.addDomainIndividual(individualDomain.getIRI().toString());
                            roleIndividuals.addRangeIndividual(individualRange.getIRI().toString());
                		}	
                	}
            		
            		Global.allFrequentRoleNamesStratified.put(element.getIRI(), roleIndividuals);
            		
            		Pattern<Atom> newPatternObjectProperty = new Pattern<Atom>();                    
                    newPatternObjectProperty.add(new RoleAtom(element.getIRI(), ((RoleAtom) element).getDomainVariable(), ((RoleAtom) element).getRangeVariable()));	
                    q.add(newPatternObjectProperty);
                }
        	}
        }
        
        OutputInformation.showAllFrequentConceptNames(Global.allFrequentConceptNamesStratified, "All frequent concept names", false);
        OutputInformation.showAllFrequentRoleNames(Global.allFrequentRoleNamesStratified, "All frequent role names", false);
        OutputInformation.showIndividualsOfAllFrequentConceptNames(Global.allFrequentConceptNamesStratified, "Individuals of all frequent concept names", false);
        OutputInformation.showIndividualsOfAllFrequentRoleNames(Global.allFrequentRoleNamesStratified, "Individuals of all frequent role names", false);
		
		return q;
    }
	
	private PatternList<Pattern<Atom>> generateSpecializedPatterns(Pattern<Atom> p)
    {
		PatternList<Pattern<Atom>> specializedPatterns = new PatternList<Pattern<Atom>>();
		ArrayList<ConceptAtom> conceptsInThePattern = new ArrayList<ConceptAtom>();
        ArrayList<RoleAtom> rolesInThePattern = new ArrayList<RoleAtom>();
        Set<String> variablesInPattern = new LinkedHashSet<String>();
        
        for (Atom atom : p)		
            if (atom instanceof ConceptAtom)
            {
                conceptsInThePattern.add((ConceptAtom) atom);
                variablesInPattern.add(((ConceptAtom) atom).getVariable());
            }
            else if (atom instanceof RoleAtom)
            {
                rolesInThePattern.add((RoleAtom) atom);
                variablesInPattern.add(((RoleAtom) atom).getDomainVariable());
                variablesInPattern.add(((RoleAtom) atom).getRangeVariable());
            }
        
        for(IRI iriConcept : Global.allFrequentConceptNamesStratified.keySet())
        {	
        	ConceptAtom conceptAtom = new ConceptAtom(iriConcept);
        	PatternList<Pattern<Atom>> patternSpecializations = addConceptAtoms(p, conceptAtom, conceptsInThePattern, rolesInThePattern, variablesInPattern);
            specializedPatterns.addAll(patternSpecializations);
        }
        
        for(IRI iriRole : Global.allFrequentRoleNamesStratified.keySet())
        {
        	RoleAtom roleAtom = new RoleAtom(iriRole);
        	PatternList<Pattern<Atom>> patternSpecializationsFreshVariable = addRoleAtomsWithFreshVariable(p, roleAtom, conceptsInThePattern, rolesInThePattern, variablesInPattern);
        	specializedPatterns.addAll(patternSpecializationsFreshVariable);
        	
        	PatternList<Pattern<Atom>> patternSpecializationsVariablesBounded = addRoleAtomsWithAllVarsBounded(p, roleAtom, conceptsInThePattern, rolesInThePattern, variablesInPattern);
        	specializedPatterns.addAll(patternSpecializationsVariablesBounded);        	
        }
        return specializedPatterns;                
    }
	
	@SuppressWarnings("unchecked")
	private PatternList<Pattern<Atom>> addConceptAtoms(Pattern<Atom> r, ConceptAtom C_commas,
            ArrayList<ConceptAtom> conceptsInThePattern,
            ArrayList<RoleAtom> rolesInThePattern, 
            Set<String> variablesInPattern)
    {	
		PatternList<Pattern<Atom>> specializedPatternsGivenAConceptAtom = new PatternList<Pattern<Atom>>();
		
		ArrayList<ConceptAtom> conceptsInPatternSubsumedCcommas = new ArrayList<ConceptAtom>();
        ArrayList<ConceptAtom> conceptsInPatternSubsumingCcommas = new ArrayList<ConceptAtom>();

        ArrayList<RoleAtom> rolesInPatternDomainOrRangeSubsumedCcommas = new ArrayList<RoleAtom>();
        //ArrayList<RoleAtom> rolesInPatternDomainOrRangeSubsumingCommas = new ArrayList<RoleAtom>();
        
        for(ConceptAtom conceptAtom : conceptsInThePattern)
        {
        	if (isConceptsSubsumedBy(this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()), this.knowledgeBaseStratified.getDataFactory().getOWLClass(C_commas.getIRI())))
        		conceptsInPatternSubsumedCcommas.add(conceptAtom);
    		if (isConceptsSubsumedBy(this.knowledgeBaseStratified.getDataFactory().getOWLClass(C_commas.getIRI()), this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()))) 
    			conceptsInPatternSubsumingCcommas.add(conceptAtom);
        }
        
        for(RoleAtom roleAtom : rolesInThePattern)
        {
        	Set<OWLClass> classesOfDomainAndRange = new LinkedHashSet<>();
        	
        	classesOfDomainAndRange.addAll(this.knowledgeBaseStratified.getReasoner().getObjectPropertyDomains(this.knowledgeBaseStratified.getDataFactory().getOWLObjectProperty(roleAtom.getIRI()), true).getFlattened());
        	classesOfDomainAndRange.addAll(this.knowledgeBaseStratified.getReasoner().getObjectPropertyRanges(this.knowledgeBaseStratified.getDataFactory().getOWLObjectProperty(roleAtom.getIRI()), true).getFlattened());
        	
        	for(OWLClass owlClass : classesOfDomainAndRange)
            {
        		if (owlClass instanceof OWLObjectUnionOf)
            	{
        			Set<OWLClassExpression> subOWLClasses = owlClass.asDisjunctSet();
        			
        			for(OWLClassExpression subOWLClass : subOWLClasses)
            		{
        				if (isConceptsSubsumedBy(subOWLClass.asOWLClass(), this.knowledgeBaseStratified.getDataFactory().getOWLClass(C_commas.getIRI())))
        					rolesInPatternDomainOrRangeSubsumedCcommas.add(roleAtom);
        				//if (isConceptsSubsumedBy(this.knowledgeBaseStratified.getDataFactory().getOWLClass(C_commas.getIRI()), subOWLClass.asOWLClass())) 
        				//	rolesInPatternDomainOrRangeSubsumingCommas.add(roleAtom);
            		}
            	}
        		else
        		{
        			if (isConceptsSubsumedBy(owlClass, this.knowledgeBaseStratified.getDataFactory().getOWLClass(C_commas.getIRI())))
    					rolesInPatternDomainOrRangeSubsumedCcommas.add(roleAtom);
    				//if (isConceptsSubsumedBy(this.knowledgeBaseStratified.getDataFactory().getOWLClass(C_commas.getIRI()), owlClass)) 
    				//	rolesInPatternDomainOrRangeSubsumingCommas.add(roleAtom);
        		}
            }
        }
        
        Set<String> usedVar = new LinkedHashSet<>();
        
        for(ConceptAtom concept : conceptsInPatternSubsumedCcommas) 
            usedVar.add(concept.getVariable());
        for(ConceptAtom concept : conceptsInPatternSubsumingCcommas)			
            usedVar.add(concept.getVariable());
        
        for(RoleAtom roleAtom : rolesInPatternDomainOrRangeSubsumedCcommas) 
        {
            usedVar.add(roleAtom.getDomainVariable());
            usedVar.add(roleAtom.getRangeVariable());
        }        
        //for(RoleAtom roleAtom : rolesInPatternDomainOrRangeSubsumingCommas) 
        //{
        //    usedVar.add(roleAtom.getDomainVariable());
        //    usedVar.add(roleAtom.getRangeVariable());
        //}
        
        Set<String> allowedVar = new LinkedHashSet<>(variablesInPattern);
        allowedVar.removeAll(usedVar);
		
        for (String v : allowedVar)
        {	
            Pattern<Atom> specPatts = (Pattern<Atom>) r.clone();			
            ConceptAtom newAtom = new ConceptAtom(C_commas.getIRI(), v);           
            specPatts.add(newAtom);	
            specializedPatternsGivenAConceptAtom.add(specPatts);			
        }
        
        return specializedPatternsGivenAConceptAtom;		
    }
	
	@SuppressWarnings("unchecked")
	private PatternList<Pattern<Atom>> addRoleAtomsWithFreshVariable(Pattern<Atom> r, RoleAtom R_commas, 
            ArrayList<ConceptAtom> conceptsInThePattern,
            ArrayList<RoleAtom> rolesInThePattern, 
            Set<String> variablesInPattern)
    {
		PatternList<Pattern<Atom>> specializedPatteturnernsWithFreshVarGivenARoleAtom = new PatternList<Pattern<Atom>>();
		
		//ArrayList<ConceptAtom> conceptsInPatternSubsumedByDomain = new ArrayList<ConceptAtom>();
        //ArrayList<ConceptAtom> conceptsInPatternSubsumedByRange = new ArrayList<ConceptAtom>();
        
        ArrayList<ConceptAtom> conceptsInPatternSubsumingByDomain = new ArrayList<ConceptAtom>();
        ArrayList<ConceptAtom> conceptsInPatternSubsumingByRange = new ArrayList<ConceptAtom>();
        
        String z = "z" + String.valueOf(this.z_fresh);
        this.z_fresh++;
        
        Set<OWLClass> owlClassesDomainRCommas = this.knowledgeBaseStratified.getReasoner().getObjectPropertyDomains(this.knowledgeBaseStratified.getDataFactory().getOWLObjectProperty(R_commas.getIRI()), true).getFlattened();
        Set<OWLClass> owlClassesRangeRCommas = this.knowledgeBaseStratified.getReasoner().getObjectPropertyRanges(this.knowledgeBaseStratified.getDataFactory().getOWLObjectProperty(R_commas.getIRI()), true).getFlattened();
        
        for(ConceptAtom conceptAtom : conceptsInThePattern)
        {
        	for(OWLClass owlClassDomainRCommas : owlClassesDomainRCommas)        	
        		if (owlClassDomainRCommas instanceof OWLObjectUnionOf)
            	{
        			Set<OWLClassExpression> subSetOWLClassDomainRCommas = owlClassDomainRCommas.asDisjunctSet();
            		
            		for(OWLClassExpression subOWLClassDomainRCommas : subSetOWLClassDomainRCommas)
            		{
            			//if (isConceptsSubsumedBy(this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()), subOWLClassDomainRCommas.asOWLClass())) 
    	            	//	conceptsInPatternSubsumedByDomain.add(conceptAtom);
            			if (isConceptsSubsumedBy(subOWLClassDomainRCommas.asOWLClass(), this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()))) 
            				conceptsInPatternSubsumingByDomain.add(conceptAtom);	
            		}
            	}
        		else
        		{
        			//if (isConceptsSubsumedBy(this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()), owlClassDomainRCommas)) 
	            	//	conceptsInPatternSubsumedByDomain.add(conceptAtom);
        			if (isConceptsSubsumedBy(owlClassDomainRCommas, this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()))) 
        				conceptsInPatternSubsumingByDomain.add(conceptAtom);
        		}        	
        	
        	for(OWLClass owlClassRangeRCommas : owlClassesRangeRCommas)        	
        		if (owlClassRangeRCommas instanceof OWLObjectUnionOf)
            	{
        			Set<OWLClassExpression> subSetOWLClassRangeRCommas = owlClassRangeRCommas.asDisjunctSet();
            		
            		for(OWLClassExpression subOWLClassRangeRCommas : subSetOWLClassRangeRCommas)
            		{
            			//if (isConceptsSubsumedBy(this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()), subOWLClassRangeRCommas.asOWLClass())) 
    	            	//	conceptsInPatternSubsumedByRange.add(conceptAtom);
            			if (isConceptsSubsumedBy(subOWLClassRangeRCommas.asOWLClass(), this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()))) 
            				conceptsInPatternSubsumingByRange.add(conceptAtom);	
            		}
            	}
        		else
        		{
        			//if (isConceptsSubsumedBy(this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()), owlClassRangeRCommas)) 
        			//	conceptsInPatternSubsumedByRange.add(conceptAtom);
        			if (isConceptsSubsumedBy(owlClassRangeRCommas, this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()))) 
        				conceptsInPatternSubsumingByRange.add(conceptAtom);
        		}        	
        }
        
        Set<String> usedVarsForDomain = new LinkedHashSet<String>();		
        //for(ConceptAtom concept : conceptsInPatternSubsumedByDomain) 
        //    usedVarsForDomain.add(concept.getVariable());
        for(ConceptAtom concept : conceptsInPatternSubsumingByDomain) 
            usedVarsForDomain.add(concept.getVariable());
        Set<String> allowedVarDomain = new LinkedHashSet<>(variablesInPattern);
        allowedVarDomain.removeAll(usedVarsForDomain);        
        for (String v : allowedVarDomain)
        {
            Pattern<Atom> specPatts = (Pattern<Atom>) r.clone();		
            RoleAtom newAtom = new RoleAtom(R_commas.getIRI(), v, z);
            specPatts.add(newAtom);	
            specializedPatteturnernsWithFreshVarGivenARoleAtom.add(specPatts);	
        }
        
        Set<String> usedVarsForRange = new LinkedHashSet<String>();		
        //for(ConceptAtom concept : conceptsInPatternSubsumedByRange) 
        //    usedVarsForRange.add(concept.getVariable());
        for(ConceptAtom concept : conceptsInPatternSubsumingByRange) 
            usedVarsForDomain.add(concept.getVariable());
        Set<String> allowedVarRange = new LinkedHashSet<>(variablesInPattern);
        allowedVarRange.removeAll(usedVarsForRange);
        for (String v : allowedVarRange)
        {
            Pattern<Atom> specPatts = (Pattern<Atom>) r.clone();		
            RoleAtom newAtom = new RoleAtom(R_commas.getIRI(), z, v);
            specPatts.add(newAtom);	
            specializedPatteturnernsWithFreshVarGivenARoleAtom.add(specPatts);	
        }

        return specializedPatteturnernsWithFreshVarGivenARoleAtom;	
    }	
	
	@SuppressWarnings("unchecked")
	private PatternList<Pattern<Atom>> addRoleAtomsWithAllVarsBounded(Pattern<Atom> r, RoleAtom R_commas, 
            ArrayList<ConceptAtom> conceptsInThePattern,
            ArrayList<RoleAtom> rolesInThePattern, 
            Set<String> variablesInPattern)
    {	
		PatternList<Pattern<Atom>> specializedPatternsWithBoundedVars = new PatternList<Pattern<Atom>>();
		
		//ArrayList<ConceptAtom> conceptsInPatternSubsumedByDomain = new ArrayList<ConceptAtom>();
        //ArrayList<ConceptAtom> conceptsInPatternSubsumedByRange = new ArrayList<ConceptAtom>();
        
        ArrayList<ConceptAtom> conceptsInPatternSubsumingByDomain = new ArrayList<ConceptAtom>();
        ArrayList<ConceptAtom> conceptsInPatternSubsumingByRange = new ArrayList<ConceptAtom>();
		
        Set<OWLClass> owlClassesDomainRCommas = this.knowledgeBaseStratified.getReasoner().getObjectPropertyDomains(this.knowledgeBaseStratified.getDataFactory().getOWLObjectProperty(R_commas.getIRI()), true).getFlattened();
        Set<OWLClass> owlClassesRangeRCommas = this.knowledgeBaseStratified.getReasoner().getObjectPropertyRanges(this.knowledgeBaseStratified.getDataFactory().getOWLObjectProperty(R_commas.getIRI()), true).getFlattened();
        
        for(ConceptAtom conceptAtom : conceptsInThePattern)
        {
        	for(OWLClass owlClassDomainRCommas : owlClassesDomainRCommas)        	
        		if (owlClassDomainRCommas instanceof OWLObjectUnionOf)
            	{
        			Set<OWLClassExpression> subSetOWLClassDomainRCommas = owlClassDomainRCommas.asDisjunctSet();
            		
            		for(OWLClassExpression subOWLClassDomainRCommas : subSetOWLClassDomainRCommas)
            		{
            			//if (isConceptsSubsumedBy(this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()), subOWLClassDomainRCommas.asOWLClass())) 
    	            	//	conceptsInPatternSubsumedByDomain.add(conceptAtom);
            			if (isConceptsSubsumedBy(subOWLClassDomainRCommas.asOWLClass(), this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()))) 
            				conceptsInPatternSubsumingByDomain.add(conceptAtom);	
            		}
            	}
        		else
        		{
        			//if (isConceptsSubsumedBy(this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()), owlClassDomainRCommas)) 
	            	//	conceptsInPatternSubsumedByDomain.add(conceptAtom);
        			if (isConceptsSubsumedBy(owlClassDomainRCommas, this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()))) 
        				conceptsInPatternSubsumingByDomain.add(conceptAtom);
        		}        	
        	
        	for(OWLClass owlClassRangeRCommas : owlClassesRangeRCommas)        	
        		if (owlClassRangeRCommas instanceof OWLObjectUnionOf)
            	{
        			Set<OWLClassExpression> subSetOWLClassRangeRCommas = owlClassRangeRCommas.asDisjunctSet();
            		
            		for(OWLClassExpression subOWLClassRangeRCommas : subSetOWLClassRangeRCommas)
            		{
            			//if (isConceptsSubsumedBy(this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()), subOWLClassRangeRCommas.asOWLClass())) 
    	            	//	conceptsInPatternSubsumedByRange.add(conceptAtom);
            			if (isConceptsSubsumedBy(subOWLClassRangeRCommas.asOWLClass(), this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()))) 
            				conceptsInPatternSubsumingByRange.add(conceptAtom);	
            		}
            	}
        		else
        		{
        			//if (isConceptsSubsumedBy(this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()), owlClassRangeRCommas)) 
        			//	conceptsInPatternSubsumedByRange.add(conceptAtom);
        			if (isConceptsSubsumedBy(owlClassRangeRCommas, this.knowledgeBaseStratified.getDataFactory().getOWLClass(conceptAtom.getIRI()))) 
        				conceptsInPatternSubsumingByRange.add(conceptAtom);
        		}        	
        }
        
        Set<RoleAtom> setRolesInThePattern = new LinkedHashSet<>(rolesInThePattern);
        
        boolean blContainRCommas = false;
        
        for(RoleAtom roleAtom : setRolesInThePattern)
        	if (this.knowledgeBaseStratified.getDataFactory().getOWLObjectProperty(roleAtom.getIRI()).equals(this.knowledgeBaseStratified.getDataFactory().getOWLObjectProperty(R_commas.getIRI())))
        	{
        		blContainRCommas = true;
                break;
        	}
        
        if (!blContainRCommas)
        {
        	//Set<String> variablesInConceptsInPatterSubsumedByDomain = new LinkedHashSet<String>();		
            //for(ConceptAtom concept : conceptsInPatternSubsumedByDomain) 
            //    variablesInConceptsInPatterSubsumedByDomain.add(concept.getVariable());
            
            Set<String> variablesInConceptsInPatterSubsumingByDomain = new LinkedHashSet<String>();		
            for(ConceptAtom concept : conceptsInPatternSubsumingByDomain) 
            	variablesInConceptsInPatterSubsumingByDomain.add(concept.getVariable());

            //Set<String> variablesInConceptsInPatterSubsumedByRange = new LinkedHashSet<String>();		
            //for(ConceptAtom concept : conceptsInPatternSubsumedByRange) 
            //    variablesInConceptsInPatterSubsumedByRange.add(concept.getVariable());
            
            Set<String> variablesInConceptsInPatterSubsumingByRange = new LinkedHashSet<String>();		
            for(ConceptAtom concept : conceptsInPatternSubsumingByRange) 
            	variablesInConceptsInPatterSubsumingByRange.add(concept.getVariable());
            
            Set<String> allowedVarsForDomain = new LinkedHashSet<>(variablesInPattern);
            //allowedVarsForDomain.removeAll(variablesInConceptsInPatterSubsumedByDomain);
            allowedVarsForDomain.removeAll(variablesInConceptsInPatterSubsumingByDomain);

            Set<String> allowedVarsForRange = new LinkedHashSet<>(variablesInPattern);
            //allowedVarsForRange.removeAll(variablesInConceptsInPatterSubsumedByRange);
            allowedVarsForRange.removeAll(variablesInConceptsInPatterSubsumingByRange);
            
            for(String v : allowedVarsForDomain)
                for(String w : allowedVarsForRange)
                {
                    Pattern<Atom> specPatts = (Pattern<Atom>) r.clone();		
                    RoleAtom newAtom = new RoleAtom(R_commas.getIRI(), v, w);
                    specPatts.add(newAtom);	
                    specializedPatternsWithBoundedVars.add(specPatts);	
                }
        }
        else
        {
        	Set<String> usedVarsForDomain =  new LinkedHashSet<String>();
            Set<String> usedVarsForRange =  new LinkedHashSet<String>();
            
            usedVarsForDomain.add("x_" + Global.cutString(R_commas.getIRI().toString()));
            usedVarsForRange.add("y_" + Global.cutString(R_commas.getIRI().toString()));            
        	
        	/*for(OWLClass owlClassDomainRCommas : owlClassesDomainRCommas)        	
        		if (owlClassDomainRCommas instanceof OWLObjectUnionOf)
            	{
        			Set<OWLClassExpression> subSetOWLClassDomainRCommas = owlClassDomainRCommas.asDisjunctSet();
            		
            		for(OWLClassExpression subOWLClassDomainRCommas : subSetOWLClassDomainRCommas)
            			usedVarsForDomain.add("c_" + Global.cutString(subOWLClassDomainRCommas.asOWLClass().getIRI().toString()));            			
            	}
        		else
        			usedVarsForDomain.add("c_" + Global.cutString(owlClassDomainRCommas.getIRI().toString()));
        	
        	for(OWLClass owlClassRangeRCommas : owlClassesRangeRCommas)        	
        		if (owlClassRangeRCommas instanceof OWLObjectUnionOf)
            	{
        			Set<OWLClassExpression> subSetOWLClassRangeRCommas = owlClassRangeRCommas.asDisjunctSet();
            		
            		for(OWLClassExpression subOWLClassRangeRCommas : subSetOWLClassRangeRCommas)
            			usedVarsForRange.add("c_" + Global.cutString(subOWLClassRangeRCommas.asOWLClass().getIRI().toString()));
            	}
        		else
        			usedVarsForRange.add("c_" + Global.cutString(owlClassRangeRCommas.getIRI().toString())); */
        	
        	
        	//for(ConceptAtom conceptAtom : conceptsInPatternSubsumedByDomain)
            //    usedVarsForDomain.add(conceptAtom.getVariable());
            for(ConceptAtom conceptAtom : conceptsInPatternSubsumingByDomain)
                usedVarsForDomain.add(conceptAtom.getVariable());
            
            //for(ConceptAtom conceptAtom : conceptsInPatternSubsumedByRange)
            //    usedVarsForRange.add(conceptAtom.getVariable());
            for(ConceptAtom conceptAtom : conceptsInPatternSubsumingByRange)
                usedVarsForRange.add(conceptAtom.getVariable());
            
            Set<String> allowedVarsForDomain = new LinkedHashSet<>(variablesInPattern);
            allowedVarsForDomain.removeAll(usedVarsForDomain);

            Set<String> allowedVarsForRange = new LinkedHashSet<>(variablesInPattern);
            allowedVarsForRange.removeAll(usedVarsForRange);
            
            for(String v : allowedVarsForDomain)
                for(String w : allowedVarsForRange)
                {
                    Pattern<Atom> specPatts = (Pattern<Atom>) r.clone();			
                    RoleAtom newAtom = new RoleAtom(R_commas.getIRI(), v, w);
                    specPatts.add(newAtom);	
                    specializedPatternsWithBoundedVars.add(specPatts);	
                }
        }
		return specializedPatternsWithBoundedVars;
    }
	
	private int evaluatePatternForPruning(Pattern<Atom> p, Pattern<Atom> p_commas,             
            PatternList<Pattern<Atom>> q, 
            PatternList<Pattern<Atom>> infrequent)
	{
		//boolean pruned = false;
		//0: No prune
		//1: Pruned by inconsistent
		//2: Pruned by others
		int pruned = 0;
		
		Rule<Atom> rule_r_commas = getRule(p_commas);
        Rule<Atom> rule_r = getRule(p);
        
        OutputInformation.showTextln("BEGIN Rule -------- * * * --------", false);        
        OutputInformation.showRuleInformation(rule_r_commas, true, true, false);        
        
        if (!this.knowledgeBaseStratified.addRule(rule_r_commas))
        {
        	//pruned = true;
        	pruned = 1;
        	OutputInformation.showTextln("   Rule is pruned by inconsistent", false);
        }
        else if (rule_r_commas.getHeadCoverage() < Global.HEADCOV_THR)
        {
        	//pruned = true;
        	pruned = 2;
        	OutputInformation.showTextln("   Rule is pruned by HeadCoverage", false);
        }
        else if (rule_r_commas.getConfidence() - rule_r.getConfidence() < Global.IMPROVEDCONF_THR)
        {
        	//pruned = true;
        	pruned = 2;
        	OutputInformation.showTextln("   Rule is pruned by Confidence", false);
        }
        else if (isPatternAlreadyGenerated(p_commas, q))
        {
        	//pruned = true;
        	pruned = 2;
        	OutputInformation.showTextln("   Rule is pruned by the pattern already generated", false);
        }
        else
        {
        	int i = 0;
        	//while ((i < infrequent.size()) && (!pruned))
        	while ((i < infrequent.size()) && (pruned == 0))
        	{
        		if ((rule_r_commas.get(0) instanceof ConceptAtom) && (infrequent.get(i).get(0) instanceof ConceptAtom))
                {
        			Set<String> setIndividualRCommas = rule_r_commas.getSupportExtensionConcept();
                    Set<String> setIndividualInfrequent = infrequent.get(i).getSupportExtensionConcept();

                    if (setIndividualInfrequent != null)
                    	if (setIndividualInfrequent.containsAll(setIndividualRCommas))
                    	{
                    		//pruned = true;
                    		pruned = 2;
                    		
                    		Pattern<Atom> infrequentPattern = infrequent.get(i);
                    		OutputInformation.showTextln("   Equivalent rule: ", false);
                    		OutputInformation.showPatternInformation(infrequentPattern, false);
                    		
                    		OutputInformation.showTextln("   Rule is already contained in infrequent pattern", false);
                    	}
                }
        		else if ((rule_r_commas.get(0) instanceof RoleAtom) && (infrequent.get(i).get(0) instanceof RoleAtom))
        		{
        			Set<RoleIndividual> setIndividualRCommas = rule_r_commas.getSupportExtensionRole();
                    Set<RoleIndividual> setIndividualInfrequent = infrequent.get(i).getSupportExtensionRole();

                    if (setIndividualInfrequent != null)
                    	if (setIndividualInfrequent.containsAll(setIndividualRCommas))
                    	{
                    		//pruned = true;                    		
                    		pruned = 2;
                    		
                    		Pattern<Atom> infrequentPattern = infrequent.get(i);
                    		OutputInformation.showTextln("   Equivalent rule: ", false);
                    		OutputInformation.showPatternInformation(infrequentPattern, false);
                    		
                    		OutputInformation.showTextln("   Rule is already contained in infrequent pattern", false);
                    	}
        		}
        		i++;
        	}
        }
        
        OutputInformation.showTextln("END Rule -------- * * * --------", false);		
		return pruned;
	}
	
	@SuppressWarnings("unchecked")
	private Rule<Atom> getRule(Pattern<Atom> p)
    {
        return new Rule<Atom>((Pattern<Atom>) p.clone());
    }
	
	private boolean isPatternAlreadyGenerated(Pattern<Atom> pattern, PatternList<Pattern<Atom>> patternList) 
    {	
		if (!patternList.isEmpty())
		{
			/*Atom head = pattern.get(0);
			Atom body = pattern.get(1);
			
			if ((head instanceof ConceptAtom) && (body instanceof RoleAtom))
			{
				if (Global.cutString(((ConceptAtom) head).getIRI().toString()).equals("Nephew"))
				{
					if (((ConceptAtom) head).getVariable().equals("c_Nephew"))
					{					
						if (Global.cutString(((RoleAtom) body).getIRI().toString()).equals("HasParent"))
						{
							if (((RoleAtom) body).getDomainVariable().equals("c_Nephew"))
							{
								if (((RoleAtom) body).getRangeVariable().equals("z5"))
								{
									System.out.println("OK");
								}
							}
						}
					}
				}
			}*/
			
			
			boolean found = false;
			int i = 0;
			
			Rule<Atom> ruleFromPattern = getRule(pattern);
			
			while ((i < patternList.size()) && (!found))
			{
				Pattern<Atom> currentPattern = patternList.get(i);
				Rule<Atom> currentRule = getRule(currentPattern);
				
				if (currentRule.getHeadCoverage() == ruleFromPattern.getHeadCoverage())
				{
					if ((currentRule.get(0) instanceof ConceptAtom) && (ruleFromPattern.get(0) instanceof ConceptAtom))
					{
						Set<String> setCurrentRule = currentRule.getSupportExtensionConcept();
                        Set<String> setRuleFromPattern = ruleFromPattern.getSupportExtensionConcept();

                        if (setCurrentRule.containsAll(setRuleFromPattern) && setRuleFromPattern.containsAll(setCurrentRule))
                        {
                        	OutputInformation.showTextln("   Rule is already contained: ", false);
                    		OutputInformation.showPatternInformation(currentPattern, false);
                            found = true;
                        }
                            
					}
					else if ((currentRule.get(0) instanceof RoleAtom) && (ruleFromPattern.get(0) instanceof RoleAtom))
					{
						Set<RoleIndividual> setCurrentRule = currentRule.getSupportExtensionRole();
                        Set<RoleIndividual> setRuleFromPattern = ruleFromPattern.getSupportExtensionRole();
                        
                        if (setCurrentRule.containsAll(setRuleFromPattern) && setRuleFromPattern.containsAll(setCurrentRule))
                        {
                        	OutputInformation.showTextln("   Rule is already contained: ", false);
                    		OutputInformation.showPatternInformation(currentPattern, false);
                            found = true;
                        }
					}
				}
				i++;
			}			
			return found;
		}
		else
			return false;
    }
	
	private boolean isSafetyConditionSatisfied(Pattern<Atom> rule)
    {
        Set<String> variablesHead = new LinkedHashSet<String>();
        Set<String> variablesBody = new LinkedHashSet<String>();

        for (int i = 0; i < rule.size(); i++)
        {
            Atom r_atom = rule.get(i);

            if (i == 0)
            {
                if (r_atom instanceof ConceptAtom)
                {
                    variablesHead.add(((ConceptAtom) r_atom).getVariable());
                }
                else if (r_atom instanceof RoleAtom)
                {
                    variablesHead.add(((RoleAtom) r_atom).getDomainVariable());
                    variablesHead.add(((RoleAtom) r_atom).getRangeVariable());                    
                }					
            }
            else
            {
                if (r_atom instanceof ConceptAtom)
                {
                    variablesBody.add(((ConceptAtom) r_atom).getVariable());
                }
                else if (r_atom instanceof RoleAtom)
                {
                    variablesBody.add(((RoleAtom) r_atom).getRangeVariable());
                    variablesBody.add(((RoleAtom) r_atom).getRangeVariable());
                }
            }
        }

        boolean conditionSatisfied = false;

        variablesHead.removeAll(variablesBody);		

        if (variablesHead.isEmpty())
            conditionSatisfied = true;			

        return conditionSatisfied;
    }
	
	@SuppressWarnings("unchecked")
	private Pattern<Atom> getPatternOrAncestorPatternSatisfyingSafetyCondition(Pattern<Atom> pattern)
    {   
        Pattern<Atom> parentPattern = null; 
        
        if (isSafetyConditionSatisfied(pattern))
        {
            parentPattern = new Pattern<Atom>((Pattern<Atom>) pattern.clone());
        }
        else if (pattern.size() > 2)
        {
            parentPattern = new Pattern<Atom>((Pattern<Atom>) pattern.clone());
            parentPattern.remove(parentPattern.size() - 1);

            boolean conditionSatisfied = false;

            while((parentPattern.size() >= 2) && (!conditionSatisfied))
            {
                conditionSatisfied = isSafetyConditionSatisfied(parentPattern);
                if (!conditionSatisfied)
                    parentPattern.remove(parentPattern.size() - 1);
            }
        }
        
        return parentPattern;
    }
	
	@SuppressWarnings({ "null", "unused" })
	private void statisticianForRuleFullVerion(Pattern<Atom> rule)
	{	
		try 
    	{
			OWLOntologyManager newOntologyManager = OWLManager.createOWLOntologyManager();
			OWLDataFactory newDataFactory = newOntologyManager.getOWLDataFactory();
			OWLOntology newOntology = newOntologyManager.loadOntologyFromOntologyDocument(this.iriInputFull);
	        Reasoner reasonerHermit = new Reasoner(newOntology);
	        
	        if (Global.addRule(rule, newOntologyManager, newDataFactory, newOntology, reasonerHermit))
	        {
	        	if (rule.get(0) instanceof ConceptAtom)
	            {	
	        		Set<String> predictionIndividuals = new LinkedHashSet<String>();
	        		Set<String> inductionIndividuals = new LinkedHashSet<String>();
	        		
	        		Set<String> conceptIndividualsStratified = new LinkedHashSet<String>(Global.allFrequentConceptNamesStratified.get(((ConceptAtom) rule.get(0)).getIRI()).getIndividuals());
	        		Set<String> conceptIndividualsFull = new LinkedHashSet<String>(Global.allFrequentConceptNamesFull.get(((ConceptAtom) rule.get(0)).getIRI()).getIndividuals());
	        		Set<String> matchIndividuals = new LinkedHashSet<String>(Global.allFrequentConceptNamesFull.get(((ConceptAtom) rule.get(0)).getIRI()).getIndividuals());
	            	
	        		Set<OWLNamedIndividual> ruleOWLNamedIndividuals = reasonerHermit.getInstances(newDataFactory.getOWLClass(((ConceptAtom) rule.get(0)).getIRI()), false).getFlattened();
	            	
	        		for (OWLNamedIndividual individual : ruleOWLNamedIndividuals)
	        		{
	        			predictionIndividuals.add(individual.asOWLNamedIndividual().getIRI().toString());
	        			inductionIndividuals.add(individual.asOWLNamedIndividual().getIRI().toString());
	        		}
	        		
	        		predictionIndividuals.removeAll(conceptIndividualsStratified);
	        		inductionIndividuals.removeAll(conceptIndividualsFull);
	        		matchIndividuals.removeAll(conceptIndividualsStratified);
	        		
	        		OutputInformation.showRuleInformationConceptIndividual(rule, predictionIndividuals, matchIndividuals, inductionIndividuals, false);
	        		//OutputPrediction.showRuleInformation(rule, ruleIndividuals, false);
	        		
	        		//Computing for statistic
	        		if (predictionIndividuals.size() > 0) 
	        		{
	        			this.countRulesHavePrediction++;
	        			this.countPredictions += predictionIndividuals.size();
	        			this.totalMatchRate += (double) matchIndividuals.size() / predictionIndividuals.size();
	        			this.totalInductionRate += (double) inductionIndividuals.size() / predictionIndividuals.size();
	        		}
	            }
	        	else if (rule.get(0) instanceof RoleAtom)
	        	{
	        		Set<RoleIndividual> predictionIndividuals = new LinkedHashSet<RoleIndividual>();
	        		Set<RoleIndividual> inductionIndividuals = new LinkedHashSet<RoleIndividual>();
	        		Set<RoleIndividual> matchIndividuals = new LinkedHashSet<RoleIndividual>();
	        		
	        		Set<RoleIndividual> roleIndividualsStratified = new LinkedHashSet<RoleIndividual>();	        		
	        		RoleIndividuals roleIndividualsInFrequentStratified = Global.allFrequentRoleNamesStratified.get(((RoleAtom) rule.get(0)).getIRI());	        		
	        		for(int i = 0; i < roleIndividualsInFrequentStratified.getDomainIndividuals().size(); i++)	        		
	        			roleIndividualsStratified.add(new RoleIndividual(roleIndividualsInFrequentStratified.getDomainIndividual(i), roleIndividualsInFrequentStratified.getRangeIndividual(i)));
	        		
	        		Set<RoleIndividual> roleIndividualsFull = new LinkedHashSet<RoleIndividual>();	        		
	        		RoleIndividuals roleIndividualsInFrequentFull = Global.allFrequentRoleNamesFull.get(((RoleAtom) rule.get(0)).getIRI());	        		
	        		for(int i = 0; i < roleIndividualsInFrequentFull.getDomainIndividuals().size(); i++)
	        		{
	        			roleIndividualsFull.add(new RoleIndividual(roleIndividualsInFrequentFull.getDomainIndividual(i), roleIndividualsInFrequentFull.getRangeIndividual(i)));
	        			matchIndividuals.add(new RoleIndividual(roleIndividualsInFrequentFull.getDomainIndividual(i), roleIndividualsInFrequentFull.getRangeIndividual(i)));
	        		}
	        		
	        		Map<OWLNamedIndividual,Set<OWLNamedIndividual>> individuals = reasonerHermit.getObjectPropertyInstances(newDataFactory.getOWLObjectProperty(((RoleAtom) rule.get(0)).getIRI()));	        		
	        		Set<OWLNamedIndividual> individualsDomain = individuals.keySet();	        		
	        		for(OWLNamedIndividual individualDomain : individualsDomain)
	            	{	
	    				Set<OWLNamedIndividual> individualsRange = individuals.get(individualDomain);
	            		
	            		for(OWLNamedIndividual individualRange : individualsRange)
	            		{
	            			predictionIndividuals.add(new RoleIndividual(individualDomain.getIRI().toString(), individualRange.getIRI().toString()));
	            			inductionIndividuals.add(new RoleIndividual(individualDomain.getIRI().toString(), individualRange.getIRI().toString()));
	            		}
	            			
	            	}
	        		
	        		predictionIndividuals.removeAll(roleIndividualsStratified);
	        		inductionIndividuals.removeAll(roleIndividualsFull);
	        		matchIndividuals.removeAll(roleIndividualsStratified);
	        		
	        		//OutputInformation.showRuleInformation(rule, ruleIndividuals, false);
	        		OutputInformation.showRuleInformationRoleIndividual(rule, predictionIndividuals, matchIndividuals, inductionIndividuals, false);
	        		
	        		//Computing for statistic
	        		if (predictionIndividuals.size() > 0) 
	        		{
	        			this.countRulesHavePrediction++;
	        			this.countPredictions += predictionIndividuals.size();
	        			this.totalMatchRate += (double) matchIndividuals.size() / predictionIndividuals.size();
	        			this.totalInductionRate += (double) inductionIndividuals.size() / predictionIndividuals.size();
	        		}        		
	        	}	
	        }
    	}    
    	catch (OWLOntologyCreationException e) 
    	{	
    		e.printStackTrace();
    	}        
	}
	
	@SuppressWarnings({ "null", "unused" })
	private void statisticianForRulePredictionMatchCommisionInduction(Pattern<Atom> rule)
	{	
		try 
    	{
			OWLOntologyManager newOntologyManager = OWLManager.createOWLOntologyManager();
			OWLDataFactory newDataFactory = newOntologyManager.getOWLDataFactory();
			OWLOntology newOntology = newOntologyManager.loadOntologyFromOntologyDocument(this.iriInputFull);
	        Reasoner reasonerHermit = new Reasoner(newOntology);
	        
	        if (Global.addRule(rule, newOntologyManager, newDataFactory, newOntology, reasonerHermit))
	        {
	        	if (rule.get(0) instanceof ConceptAtom)
	            {	
	        		Set<String> predictionIndividuals = new LinkedHashSet<String>();
	        		Set<String> inductionIndividuals = new LinkedHashSet<String>();
	        		
	        		Set<String> conceptIndividualsStratified = new LinkedHashSet<String>(Global.allFrequentConceptNamesStratified.get(((ConceptAtom) rule.get(0)).getIRI()).getIndividuals());
	        		Set<String> conceptIndividualsFull = new LinkedHashSet<String>(Global.allFrequentConceptNamesFull.get(((ConceptAtom) rule.get(0)).getIRI()).getIndividuals());
	        		Set<String> matchIndividuals = new LinkedHashSet<String>(Global.allFrequentConceptNamesFull.get(((ConceptAtom) rule.get(0)).getIRI()).getIndividuals());
	            	
	        		Set<OWLNamedIndividual> ruleOWLNamedIndividuals = reasonerHermit.getInstances(newDataFactory.getOWLClass(((ConceptAtom) rule.get(0)).getIRI()), false).getFlattened();
	            	
	        		for (OWLNamedIndividual individual : ruleOWLNamedIndividuals)
	        		{
	        			predictionIndividuals.add(individual.asOWLNamedIndividual().getIRI().toString());
	        			inductionIndividuals.add(individual.asOWLNamedIndividual().getIRI().toString());
	        		}
	        		
	        		predictionIndividuals.removeAll(conceptIndividualsStratified);
	        		inductionIndividuals.removeAll(conceptIndividualsFull);
	        		matchIndividuals.removeAll(conceptIndividualsStratified);
	        		
	        		OutputInformation.showRuleInformationConceptPredictionMatchCommisionInduction(rule, predictionIndividuals, matchIndividuals, inductionIndividuals, false);	        		
	            }
	        	else if (rule.get(0) instanceof RoleAtom)
	        	{
	        		Set<RoleIndividual> predictionIndividuals = new LinkedHashSet<RoleIndividual>();
	        		Set<RoleIndividual> inductionIndividuals = new LinkedHashSet<RoleIndividual>();
	        		Set<RoleIndividual> matchIndividuals = new LinkedHashSet<RoleIndividual>();
	        		
	        		Set<RoleIndividual> roleIndividualsStratified = new LinkedHashSet<RoleIndividual>();	        		
	        		RoleIndividuals roleIndividualsInFrequentStratified = Global.allFrequentRoleNamesStratified.get(((RoleAtom) rule.get(0)).getIRI());	        		
	        		for(int i = 0; i < roleIndividualsInFrequentStratified.getDomainIndividuals().size(); i++)	        		
	        			roleIndividualsStratified.add(new RoleIndividual(roleIndividualsInFrequentStratified.getDomainIndividual(i), roleIndividualsInFrequentStratified.getRangeIndividual(i)));
	        		
	        		Set<RoleIndividual> roleIndividualsFull = new LinkedHashSet<RoleIndividual>();	        		
	        		RoleIndividuals roleIndividualsInFrequentFull = Global.allFrequentRoleNamesFull.get(((RoleAtom) rule.get(0)).getIRI());	        		
	        		for(int i = 0; i < roleIndividualsInFrequentFull.getDomainIndividuals().size(); i++)
	        		{
	        			roleIndividualsFull.add(new RoleIndividual(roleIndividualsInFrequentFull.getDomainIndividual(i), roleIndividualsInFrequentFull.getRangeIndividual(i)));
	        			matchIndividuals.add(new RoleIndividual(roleIndividualsInFrequentFull.getDomainIndividual(i), roleIndividualsInFrequentFull.getRangeIndividual(i)));
	        		}
	        		
	        		Map<OWLNamedIndividual,Set<OWLNamedIndividual>> individuals = reasonerHermit.getObjectPropertyInstances(newDataFactory.getOWLObjectProperty(((RoleAtom) rule.get(0)).getIRI()));	        		
	        		Set<OWLNamedIndividual> individualsDomain = individuals.keySet();	        		
	        		for(OWLNamedIndividual individualDomain : individualsDomain)
	            	{	
	    				Set<OWLNamedIndividual> individualsRange = individuals.get(individualDomain);
	            		
	            		for(OWLNamedIndividual individualRange : individualsRange)
	            		{
	            			predictionIndividuals.add(new RoleIndividual(individualDomain.getIRI().toString(), individualRange.getIRI().toString()));
	            			inductionIndividuals.add(new RoleIndividual(individualDomain.getIRI().toString(), individualRange.getIRI().toString()));
	            		}
	            			
	            	}
	        		
	        		predictionIndividuals.removeAll(roleIndividualsStratified);
	        		inductionIndividuals.removeAll(roleIndividualsFull);
	        		matchIndividuals.removeAll(roleIndividualsStratified);
	        		
	        		OutputInformation.showRuleInformationRolePredictionMatchCommisionInduction(rule, predictionIndividuals, matchIndividuals, inductionIndividuals, false);
	        	}	
	        }
    	}    
    	catch (OWLOntologyCreationException e) 
    	{	
    		e.printStackTrace();
    	}        
	}
	
	//Check owlClassA is subsumed owlClassB ?	
    private boolean isConceptsSubsumedBy(OWLClass owlClassA, OWLClass owlClassB)
    {
        boolean temp_return = false;

        if (owlClassA == owlClassB)
                return true;
        else
        {	
        	NodeSet<OWLClass> owlSubClassesB = this.knowledgeBaseStratified.getReasoner().getSubClasses(owlClassB, true);

            for (OWLClass elementB : owlSubClassesB.getFlattened())
            {
                if (elementB.equals(owlClassA))
                {
                    temp_return = true;
                    break;                
                }
            }			
            return temp_return;
        }
    }
}
