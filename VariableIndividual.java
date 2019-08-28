import java.util.ArrayList;

public class VariableIndividual 
{
    private String strNameVariable;
    private ArrayList<String> individuals;
    
    public VariableIndividual(String strNameVariable)
    {
        this.strNameVariable = strNameVariable;
        individuals = new ArrayList<String>();
    }
    
    public VariableIndividual(VariableIndividual variableIndividual)
    {
        this.strNameVariable = variableIndividual.getNameVariable();
        individuals = new ArrayList<String>(variableIndividual.getIndividuals());
    }
    
    public VariableIndividual(String strNameVariable, ArrayList<String> newIndividuals)
    {
        this.strNameVariable = strNameVariable;
        individuals = new ArrayList<String>(newIndividuals);
    }
    
    public void addIndividual(String newIndividual)
    {
        individuals.add(newIndividual);       
    }
    
    public void addIndividuals(ArrayList<String> newIndividuals)
    {
        individuals.addAll(newIndividuals);     
    }
    
    public ArrayList<String> getIndividuals()
    {
        return this.individuals;
    }
    
    public String getIndividual(int i)
    {
        return this.individuals.get(i);
    }
    
    public void removeIndividual(int i)
    {
    	individuals.remove(i);
    }
    
    public void setNameVariable(String strNameVariable)
    {
        this.strNameVariable = strNameVariable;        
    }
    
    public String getNameVariable()
    {
        return this.strNameVariable;        
    }
    
}
