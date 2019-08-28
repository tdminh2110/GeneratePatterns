import java.util.ArrayList;
import org.semanticweb.owlapi.model.IRI;

public class RoleAtom extends Atom 
{
	private String strDomainVariable;
	private ArrayList<String> domainIndividuals;
	private String strRangeVariable;
	private ArrayList<String> rangeIndividuals;

	public RoleAtom(IRI iri)
    {
        super(iri);
        this.strDomainVariable = "";        
        this.strRangeVariable = "";
        this.domainIndividuals = null;
        this.rangeIndividuals = null;
    }
	
	public RoleAtom(IRI iri, String strVariableDomain, String strVariableRange)
    {
        super(iri);
        this.strDomainVariable = strVariableDomain;        
        this.strRangeVariable = strVariableRange;
        this.domainIndividuals = null;
        this.rangeIndividuals = null;
    }
	
	public String getDomainVariable()
    {
        return this.strDomainVariable;
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
	
	public String getRangeVariable()
    {
        return this.strRangeVariable;
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
	
}
