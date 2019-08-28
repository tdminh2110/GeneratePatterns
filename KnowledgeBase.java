import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.clarkparsia.pellet.owlapiv3.PelletReasoner;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;

public class KnowledgeBase 
{
	private OWLOntologyManager ontologyManager;
    private OWLDataFactory dataFactory;	
    private OWLOntology ontology;
    private IRI iri;
    private PelletReasoner reasonerPellet;
    private Reasoner reasonerHermit;
    
    public KnowledgeBase(IRI iri)
    {    	
        try 
        {
        	this.iri = iri;
        	this.ontologyManager = OWLManager.createOWLOntologyManager();
            this.dataFactory = this.ontologyManager.getOWLDataFactory();
			this.ontology = this.ontologyManager.loadOntologyFromOntologyDocument(iri);
			/*this.reasonerPellet = com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory.getInstance().createReasoner(this.ontology);
			
			if (reasonerPellet.isConsistent())
			{
	            OutputInformation.showTextln("Ontology is consistent.", true);
	            OutputInformation.showTextln("Ontology is consistent.", false);
	        }
	        else
	        {
	        	OutputInformation.showTextln("Ontology is inconsistent.", true);
	        	OutputInformation.showTextln("Ontology is inconsistent.", false);
	        }*/
			
			
			this.reasonerHermit = new Reasoner(this.ontology);
						
			if (reasonerHermit.isConsistent())
			{
	            OutputInformation.showTextln("Ontology is consistent.", true);
	            OutputInformation.showTextln("Ontology is consistent.", false);
	        }
	        else
	        {
	        	OutputInformation.showTextln("Ontology is inconsistent.", true);
	        	OutputInformation.showTextln("Ontology is inconsistent.", false);
	        }
		} 
        catch (OWLOntologyCreationException e) 
        {	
			e.printStackTrace();
		}        
    }
    
    public OWLOntologyManager getOntologyManager()
    {
        return this.ontologyManager;
    }
    
    public OWLDataFactory getDataFactory()
    {
        return this.dataFactory;
    }
    
    public OWLOntology getOntology()
    {
        return this.ontology;
    }
    
    public IRI getIRI()
    {
        return this.iri;
    }
    
    public PelletReasoner getPelletReasoner()
    {
    	return this.reasonerPellet;
    }
    
    public Reasoner getReasoner()
    {
        return this.reasonerHermit;
    }

    public boolean addRule(Rule<Atom> rule)
    {    	
    	//Them vao de tranh truong hop 3 ham trung ten    	
    	boolean omit = false;
    	
    	if (rule.size() == 3)
    	{
    		Atom head = rule.get(0);
    		Atom body1 = rule.get(1);
    		Atom body2 = rule.get(2);
    		
    		String strNameHead = Global.cutString(((Atom) head).getIRI().toString());
    		String strNameBody1 = Global.cutString(((Atom) body1).getIRI().toString());
    		String strNameBody2 = Global.cutString(((Atom) body2).getIRI().toString());
    		
    		if ((strNameHead.equals(strNameBody1)) && (strNameHead.equals(strNameBody2)))
    			omit = true;    		
    	}
    	//End
    	
    	if (!omit)
    	{    	
	    	try 
	        {
		    	OWLOntologyManager newOntologyManager = OWLManager.createOWLOntologyManager();
		        OWLDataFactory newDataFactory = newOntologyManager.getOWLDataFactory();
		        OWLOntology newOntology = newOntologyManager.loadOntologyFromOntologyDocument(this.iri);        
		    	
		    	SWRLAtom head = null;
		        Set<SWRLAtom> body = new LinkedHashSet<SWRLAtom>();
		        
		        for(int i = 0; i < rule.size(); i++)
		        {
		        	if (i == 0)
		            {
		                if (rule.get(0) instanceof ConceptAtom)
		                {
		                	OWLClass conceptAtom = newDataFactory.getOWLClass(((ConceptAtom) rule.get(i)).getIRI());
		                	SWRLVariable var = newDataFactory.getSWRLVariable(IRI.create(((ConceptAtom) rule.get(i)).getVariable()));
		                	head = newDataFactory.getSWRLClassAtom(conceptAtom, var);
		                }
		                else if (rule.get(0) instanceof RoleAtom)
		                {
		                	OWLObjectProperty roleAtom = newDataFactory.getOWLObjectProperty(((RoleAtom) rule.get(i)).getIRI());
		                	SWRLVariable var1 = newDataFactory.getSWRLVariable(IRI.create(((RoleAtom) rule.get(i)).getDomainVariable()));
		                    SWRLVariable var2 = newDataFactory.getSWRLVariable(IRI.create(((RoleAtom) rule.get(i)).getRangeVariable()));
		                    head = newDataFactory.getSWRLObjectPropertyAtom(roleAtom, var1, var2);
		                }
		            }
		        	else
		        	{
		        		if (rule.get(i) instanceof ConceptAtom)
		                {
		        			OWLClass conceptAtom = newDataFactory.getOWLClass(((ConceptAtom) rule.get(i)).getIRI());
		                    SWRLVariable var = newDataFactory.getSWRLVariable(IRI.create(((ConceptAtom) rule.get(i)).getVariable()));
		                    body.add(newDataFactory.getSWRLClassAtom(conceptAtom, var));
		                }
		                else if (rule.get(i) instanceof RoleAtom)
		                {
		                	OWLObjectProperty roleAtom = newDataFactory.getOWLObjectProperty(((RoleAtom) rule.get(i)).getIRI());
		                    SWRLVariable var1 = newDataFactory.getSWRLVariable(IRI.create(((RoleAtom) rule.get(i)).getDomainVariable()));
		                    SWRLVariable var2 = newDataFactory.getSWRLVariable(IRI.create(((RoleAtom) rule.get(i)).getRangeVariable()));
		                    body.add(newDataFactory.getSWRLObjectPropertyAtom(roleAtom, var1, var2));
		                }
		        	}
		        }
		        
		        SWRLRule swrlRule = newDataFactory.getSWRLRule(body, Collections.singleton(head));
		
		        newOntologyManager.applyChange(new AddAxiom(newOntology, swrlRule));
		        
		        Reasoner reasonerHermit = new Reasoner(newOntology);
		        
		        if (!reasonerHermit.isConsistent())
		        	return false;
		        else
		        	return true;
	        }    
		    catch (OWLOntologyCreationException e) 
		    {	
		        e.printStackTrace();
		        return false;
		    }
    	}
    	
    	return false;
    }
}

