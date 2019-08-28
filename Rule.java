
@SuppressWarnings("serial")
public class Rule<E> extends Pattern<E>
{
    public Rule() 
    {
        super();        
        
        //dblHeadCoverage = getHeadCoverage();
        //dblConfidence = getConfidence();        
    }
	
    public Rule(Pattern<E> p) 
    {
        super(p);        
           
        //this.dblHeadCoverage = getHeadCoverage();
        //this.dblConfidence = getConfidence();        
    }
} 
