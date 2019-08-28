import java.util.ArrayList;

public class ConceptIndividuals 
{
	private ArrayList<String> individuals;

	public ConceptIndividuals()
    {    
        this.individuals = null;
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
