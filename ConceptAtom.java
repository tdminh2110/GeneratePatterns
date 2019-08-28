import java.util.ArrayList;
import org.semanticweb.owlapi.model.IRI;

public class ConceptAtom extends Atom 
{
	private String strVariable;
	private ArrayList<String> individuals;

	public ConceptAtom(IRI iri)
    {
        super(iri);
        this.strVariable = ""; 
        this.individuals = null;
    }
	
	public ConceptAtom(IRI iri, String strVariable)
    {
        super(iri);
        this.strVariable = strVariable; 
        this.individuals = null;
    }
	
	public String getVariable()
    {
        return this.strVariable;
    }
	
	public void addIndividual(String strIndividual)
    {
        if (this.individuals == null)        
        	this.individuals = new ArrayList<String>();
        
        this.individuals.add(strIndividual);
    }
    
    public void setIndividuals(ArrayList<String> individuals)
    {
    	this.individuals = individuals;
    }
    
    public ArrayList<String> getIndividuals()
    {
        return this.individuals;
    }
	
}
