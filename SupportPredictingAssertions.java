import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Random;
import java.util.Set;

import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassAssertionAxiom;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntologyFormat;
import org.semanticweb.owlapi.model.OWLOntologyStorageException;

public class SupportPredictingAssertions 
{
	private KnowledgeBase knowledgeBase;
	
	public SupportPredictingAssertions(KnowledgeBase knowledgeBase)
	{
		this.knowledgeBase = knowledgeBase;
	}
	
	public void reducingOntology(String strFileOutput, double number)
	{
		Date date = new Date();
    	Random rand = new Random();
    	rand.setSeed(date.getTime());
    	
    	Set<OWLClass> classes = this.knowledgeBase.getOntology().getClassesInSignature(); 
        classes.remove(this.knowledgeBase.getDataFactory().getOWLThing()); 
        
        Set<OWLAxiom> axiomsToRemove = new LinkedHashSet<OWLAxiom>();
        
        for (OWLClass owlClass : classes)
        {
        	int countClassAssertionAxiom = 0;
        	
        	OutputInformation.showTextln("Concept: " + Global.cutString(owlClass.getIRI().toString()), true);
        	
        	ArrayList<OWLClassAssertionAxiom> arrayClassAssertionAxioms = new ArrayList<>(this.knowledgeBase.getOntology().getClassAssertionAxioms(owlClass));
        	
        	if (arrayClassAssertionAxioms.size() > 1)
	        {
        		int totalRemove = Global.getPercent(arrayClassAssertionAxioms.size(), number);
	        	
	        	while(totalRemove > 0)
	            {
	        		int n = rand.nextInt(arrayClassAssertionAxioms.size());	        		
	        		axiomsToRemove.add(arrayClassAssertionAxioms.get(n));
	        		OutputInformation.showTextln("   " + String.valueOf(++countClassAssertionAxiom) + ". Class Assertion Axioms are reduced: " + Global.cutString(arrayClassAssertionAxioms.get(n).getIndividual().asOWLNamedIndividual().getIRI().toString()), true);
	            		
            		ArrayList<OWLObjectPropertyAssertionAxiom> arrayObjectPropertyAssertionAxioms = new ArrayList<>(this.knowledgeBase.getOntology().getObjectPropertyAssertionAxioms(arrayClassAssertionAxioms.get(n).getIndividual()));
            		
            		if (arrayObjectPropertyAssertionAxioms.size() > 0)
	            	{            		
	            		OutputInformation.showTextln("         Object Property Assertion Axioms are removed: ", true);	            		
	            		
	            		for (OWLObjectPropertyAssertionAxiom objectPropertyAssertionAxiom : arrayObjectPropertyAssertionAxioms)
	            		{
	            			axiomsToRemove.add(objectPropertyAssertionAxiom);
	            			OutputInformation.showTextln("            " + objectPropertyAssertionAxiom.toString(), true);
	            		}
	            	}
	        		
	        		arrayClassAssertionAxioms.remove(n);
	            	totalRemove--;
	            }
	        }
        }
        
        this.knowledgeBase.getOntologyManager().removeAxioms(this.knowledgeBase.getOntology(), axiomsToRemove);
        
        File file = new File(strFileOutput);
        OWLOntologyFormat format = this.knowledgeBase.getOntologyManager().getOntologyFormat(this.knowledgeBase.getOntology());
        OWLXMLOntologyFormat owlxmlFormat = new OWLXMLOntologyFormat();
        if (format.isPrefixOWLOntologyFormat())		 
            owlxmlFormat.copyPrefixesFrom(format.asPrefixOWLOntologyFormat()); 

        try 
        {	
        	this.knowledgeBase.getOntologyManager().saveOntology(this.knowledgeBase.getOntology(), owlxmlFormat, IRI.create(file.toURI()));
        } 
        catch (OWLOntologyStorageException e) 
        {        
            e.printStackTrace();
        }
	}
}
