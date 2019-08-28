
public class RoleIndividual
{
    private String strX, strY;        

    public RoleIndividual(String strX, String strY)
    {
        this.strX = strX;
        this.strY = strY;                
    }

    public String getX()
    {
        return this.strX;
    }

    public String getY()
    {
        return this.strY;
    }
    
    public String getId() 
	{
        return this.strX + this.strY;
    }
	
	@Override
	public int hashCode() 
	{
		return this.getId().hashCode();
	}
	
	public boolean equals(Object obj)
	{
		return ((obj instanceof RoleIndividual) && (((RoleIndividual) obj).strX.equals(this.strX)) &&
											   (((RoleIndividual) obj).strY.equals(this.strY)) );
	}
}