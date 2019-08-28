import java.util.ArrayList;

public class RoleIndividuals 
{
	private ArrayList<String> domainIndividuals;
	private ArrayList<String> rangeIndividuals;

	public RoleIndividuals()
    {    
        this.domainIndividuals = null;
        this.rangeIndividuals = null;
    }
	
	public void addDomainIndividual(String strIndividual)
    {
        if (this.domainIndividuals == null)        
        	this.domainIndividuals = new ArrayList<String>();
        
        this.domainIndividuals.add(strIndividual);
    }
    
    public void setDomainIndividuals(ArrayList<String> individuals)
    {
    	this.domainIndividuals = individuals;
    }
    
    public ArrayList<String> getDomainIndividuals()
    {
        return this.domainIndividuals;
    }
    
    public String getDomainIndividual(int i)
    {
        return this.domainIndividuals.get(i);
    }
    
    public void addRangeIndividual(String strIndividual)
    {
        if (this.rangeIndividuals == null)        
        	this.rangeIndividuals = new ArrayList<String>();
        
        this.rangeIndividuals.add(strIndividual);
    }
    
    public void setRangeIndividuals(ArrayList<String> individuals)
    {
    	this.rangeIndividuals = individuals;
    }
    
    public ArrayList<String> getRangeIndividuals()
    {
        return this.rangeIndividuals;
    }
    
    public String getRangeIndividual(int i)
    {
        return this.rangeIndividuals.get(i);
    }
}
