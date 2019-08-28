import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

@SuppressWarnings("serial")
public class Pattern<E> extends ArrayList<E> 
{
	protected int intSupp;
	protected double dblHeadCoverage;
    protected double dblConfidence;
    
    protected int denominatorHeadCoverage;    
    
    protected ArrayList<VariableIndividual> suppSupport;
    protected ArrayList<VariableIndividual> denominatorConfidence;
    
    public Pattern()
    {
    	super();
    	
    	this.intSupp = -1;
    	this.dblHeadCoverage = -1;
    	this.dblConfidence = -1;    	
    	
    	this.denominatorHeadCoverage = -1;
    	
    	this.suppSupport = null;       
    	this.denominatorConfidence = null;
    }
    
    @SuppressWarnings("unchecked")
	public Pattern(Pattern<E> p)
    {
        super(p);        
        
        this.intSupp = p.getSupp();
        this.dblHeadCoverage = p.getHeadCoverage();
        this.dblConfidence = p.getConfidence();
        
        if (p.getSuppSupport() != null)
        	this.suppSupport = (ArrayList<VariableIndividual>) p.getSuppSupport().clone();
        else
        	this.suppSupport = null;        
         
        this.denominatorHeadCoverage = p.getDenominatorHeadCoverage();
        
        if (p.getDenominatorConfidence() != null)        
            this.denominatorConfidence = (ArrayList<VariableIndividual>) p.getDenominatorConfidence().clone();
        else
            this.denominatorConfidence = null;        
    }
    
    public int getSupp()
    {
        return this.intSupp;
    }
    
    public double getHeadCoverage()
    {
        return this.dblHeadCoverage;
    }
    
    public double getConfidence()
    {
        return this.dblConfidence;
    }
    
    public double getPCAConfidence()
    {	
        return 0;
    }
    
    public ArrayList<VariableIndividual> getSuppSupport()
    {
        return this.suppSupport;
    }
    
    public int getDenominatorHeadCoverage()
    {
        return this.denominatorHeadCoverage;
    }
    
    public ArrayList<VariableIndividual> getDenominatorConfidence()
    {
        return this.denominatorConfidence;
    }
    
    @Override
    public boolean add(E e)
    {
    	//Them vao de tranh truong hop 3 ham trung ten    	
    	boolean omit = false;
    	
    	if (this.size() == 2)
    	{
    		E head = this.get(0);
    		E body = this.get(1);
    		
    		String strNameHead = Global.cutString(((Atom) head).getIRI().toString());
    		String strNameBody = Global.cutString(((Atom) body).getIRI().toString());
    		String strNameE = Global.cutString(((Atom) e).getIRI().toString());
    		
    		if ((strNameHead.equals(strNameBody)) && (strNameHead.equals(strNameE)))
    			omit = true;    		
    	}
    	//End
    	
    	boolean blValueReturn = super.add(e);    	
    	
    	if (omit)
    	{
    		this.dblHeadCoverage = 0;
    		this.dblConfidence = 0;    		
    	}
    	else
    	{
	    	if (blValueReturn)
	    	{    		
	    		Atom atom = (Atom) e;
	    		
	    		/*if (atom instanceof RoleAtom)
		    		if (Global.cutString(((RoleAtom) atom).getIRI().toString()).equals("HasSon"))
					{
						if ( (((RoleAtom) atom).getDomainVariable().equals("x_HasSon")) &&
								(((RoleAtom) atom).getRangeVariable().equals("z79")) )
						{
							System.out.println("Chao cac ban");
						}
					}*/
	    		
	    		suppSupportAddAtom(atom);
	    		this.intSupp = computeSupp();
	    		computeDenominatorHeadCoverage(atom);
	    		this.dblHeadCoverage = computeHeadCoverage();
	    		
	    		denominatorConfidenceAddAtom(atom);
	    		this.dblConfidence = computeConfidence();
	    	}
    	}
    	
    	return blValueReturn;
    }
    
    public void removeAllData()
    {
        //suppSupport = null;
        denominatorConfidence = null;
    }
    
    public double computeHeadCoverage()
    {
    	double x = (double) this.intSupp;
        double y = this.denominatorHeadCoverage;

        return x / y;
    }
    
    public double computeConfidence()
    {
    	double x = (double) this.intSupp;
        double y = computeDenominatorConfidence();        

        if (y != 0.0)	
            return x / y;
        else        	
            return 0;
    }
    
    public Set<String> getSupportExtensionConcept()
    {
        Set<String> setConceptIndividual = null;

        if (this.suppSupport != null)        
        	if (this.suppSupport.get(0).getIndividuals().size() > 0)	           
	        	setConceptIndividual = new LinkedHashSet<String>(this.suppSupport.get(0).getIndividuals());
        
        return setConceptIndividual;        	
    }
    
    public Set<RoleIndividual> getSupportExtensionRole()
    {
        Set<RoleIndividual> setRoleIndividual = null;

        if (this.suppSupport != null)        
        	if (this.suppSupport.get(0).getIndividuals().size() > 0)
        	{
        		setRoleIndividual = new LinkedHashSet<RoleIndividual>();
        		
        		ArrayList<String> subjectIndividuals = this.suppSupport.get(0).getIndividuals();
                ArrayList<String> objectIndividuals = this.suppSupport.get(1).getIndividuals();
        		
                for(int i = 0; i < subjectIndividuals.size(); i++)
                	setRoleIndividual.add(new RoleIndividual(subjectIndividuals.get(i), objectIndividuals.get(i)));
        	}
        
        return setRoleIndividual;        	
    }
    
    private int computeSupp() 
    {
        E head = this.get(0);

        if (head instanceof ConceptAtom)
        {        
            Set<String> uniqueIndividuals = new LinkedHashSet<String>(this.suppSupport.get(0).getIndividuals());        
            return uniqueIndividuals.size();
        }
        else if (head instanceof RoleAtom)
        {
            Set<RoleIndividual> uniqueIndividuals = new LinkedHashSet<RoleIndividual>();
            
            ArrayList<String> subjectIndividuals = this.suppSupport.get(0).getIndividuals();
            ArrayList<String> objectIndividuals = this.suppSupport.get(1).getIndividuals();
            
            for(int i = 0; i < subjectIndividuals.size(); i++)            
            	uniqueIndividuals.add(new RoleIndividual(subjectIndividuals.get(i), objectIndividuals.get(i)));
            
            return uniqueIndividuals.size();
        }
        else 
        	return 0;               
    } 
 
    private int computeDenominatorConfidence()
    {
    	if (this.size() > 1)
    	{
    		E head = this.get(0);
    		
    		if (head instanceof ConceptAtom)
            {
    			ArrayList<String> individuals = null;
    			for(int i = 0; i < this.denominatorConfidence.size(); i++)	            
	            	if (this.denominatorConfidence.get(i).getNameVariable().equals(((ConceptAtom) head).getVariable()))
	            	{
	            		individuals = this.denominatorConfidence.get(i).getIndividuals();
	            		break;
	            	}
    			Set<String> uniqueIndividuals = new LinkedHashSet<String>(individuals);    	        
                return uniqueIndividuals.size();
            }
    		else if (head instanceof RoleAtom)
            {	
    			ArrayList<String> subjectIndividuals = null; 
	            ArrayList<String> objectIndividuals = null;
	            for(int i = 0; i < this.denominatorConfidence.size(); i++)
	            {
	            	if (this.denominatorConfidence.get(i).getNameVariable().equals(((RoleAtom) head).getDomainVariable()))	            	
	            		subjectIndividuals = this.denominatorConfidence.get(i).getIndividuals();
	            	if (this.denominatorConfidence.get(i).getNameVariable().equals(((RoleAtom) head).getRangeVariable()))	            	
	            		objectIndividuals = this.denominatorConfidence.get(i).getIndividuals();	            		
	            }
	            
	            if ((subjectIndividuals != null) && (objectIndividuals != null))
	            {
	            	Set<RoleIndividual> uniqueIndividuals = new LinkedHashSet<RoleIndividual>();	            	
		            for(int i = 0; i < subjectIndividuals.size(); i++)            
		            	uniqueIndividuals.add(new RoleIndividual(subjectIndividuals.get(i), objectIndividuals.get(i)));
		            return uniqueIndividuals.size();
	            }
	            else if ((subjectIndividuals != null) && (objectIndividuals == null))
	            {
	            	Set<String> uniqueIndividuals = new LinkedHashSet<String>(subjectIndividuals);
	            	//return uniqueIndividuals.size();
	            	return uniqueIndividuals.size() * Global.numberOfIndividualsInSignature;	            	
	            }
	            else if ((subjectIndividuals == null) && (objectIndividuals != null))
	            {
	            	Set<String> uniqueIndividuals = new LinkedHashSet<String>(objectIndividuals);
	            	//return uniqueIndividuals.size();
	            	return uniqueIndividuals.size() * Global.numberOfIndividualsInSignature;
	            }
	            else
	            	return 0;
            }
    		else
    			return 0;
    	}
    	else
    		return 0;   	
    }
      
    private void suppSupportAddAtom(Atom atom)
    {
    	if (this.size() <= 1)         
        { 
            if (this.suppSupport == null) this.suppSupport = new ArrayList<VariableIndividual>();
            
            if (atom instanceof ConceptAtom)
            {
            	VariableIndividual newVariableIndividual = new VariableIndividual(((ConceptAtom) atom).getVariable(), Global.allFrequentConceptNamesStratified.get(((ConceptAtom) atom).getIRI()).getIndividuals());                	                	
            	this.suppSupport.add(newVariableIndividual);            	
            }
            else if (atom instanceof RoleAtom)
            {
            	VariableIndividual newVariableIndividualDomain = new VariableIndividual(((RoleAtom) atom).getDomainVariable(), Global.allFrequentRoleNamesStratified.get(((RoleAtom) atom).getIRI()).getDomainIndividuals());
            	VariableIndividual newVariableIndividualRange = new VariableIndividual(((RoleAtom) atom).getRangeVariable(), Global.allFrequentRoleNamesStratified.get(((RoleAtom) atom).getIRI()).getRangeIndividuals());
            	this.suppSupport.add(newVariableIndividualDomain);
            	this.suppSupport.add(newVariableIndividualRange);
            }
        }
		else
		{
			ArrayList<VariableIndividual> newSuppSupport = new ArrayList<VariableIndividual>();            
            
			for (int i = 0; i < this.suppSupport.size(); i++)
            {
                VariableIndividual variableIndividual = new VariableIndividual(this.suppSupport.get(i).getNameVariable());
                newSuppSupport.add(variableIndividual);
            }
            
            if (atom instanceof ConceptAtom)
            {	
            	VariableIndividual individualsOfNewConceptAtom = new VariableIndividual(((ConceptAtom) atom).getVariable(), Global.allFrequentConceptNamesStratified.get(((ConceptAtom) atom).getIRI()).getIndividuals());
            	
            	VariableIndividual variableIndividual = null;                 
                for (int i = 0; i < this.suppSupport.size(); i++)                
                    if (this.suppSupport.get(i).getNameVariable().equals(((ConceptAtom) atom).getVariable()))
                    {
                        variableIndividual = this.suppSupport.get(i);
                        break;
                    }
                
                for(int i = 0; i < variableIndividual.getIndividuals().size(); i++)                
                	if (individualsOfNewConceptAtom.getIndividuals().contains(variableIndividual.getIndividual(i)))                	
                		for (int j = 0; j < this.suppSupport.size(); j++)
            				newSuppSupport.get(j).getIndividuals().add(this.suppSupport.get(j).getIndividual(i));
                
                //Old version
                /*for(int i = 0; i < variableIndividual.getIndividuals().size(); i++)
                	for(int j = 0; j < individualsOfNewConceptAtom.getIndividuals().size(); j++)
                		if (individualsOfNewConceptAtom.getIndividual(j).equals(variableIndividual.getIndividual(i)))
                			for (int k = 0; k < this.suppSupport.size(); k++)
                				newSuppSupport.get(k).getIndividuals().add(this.suppSupport.get(k).getIndividual(i));*/
                
                this.suppSupport = newSuppSupport;
            }
            else if (atom instanceof RoleAtom)
            {
            	VariableIndividual individualsOfNewDomainRoleAtom = new VariableIndividual(((RoleAtom) atom).getDomainVariable(), Global.allFrequentRoleNamesStratified.get(((RoleAtom) atom).getIRI()).getDomainIndividuals());
            	VariableIndividual individualsOfNewRangeRoleAtom = new VariableIndividual(((RoleAtom) atom).getRangeVariable(), Global.allFrequentRoleNamesStratified.get(((RoleAtom) atom).getIRI()).getRangeIndividuals());
            	
            	VariableIndividual variableIndividualSubject = null; 
                VariableIndividual variableIndividualObject = null;
                
                for (int i = 0; i < this.suppSupport.size(); i++)                
                {
                    if (this.suppSupport.get(i).getNameVariable().equals(((RoleAtom) atom).getDomainVariable()))                                            
                        variableIndividualSubject = this.suppSupport.get(i);
                    if (this.suppSupport.get(i).getNameVariable().equals(((RoleAtom) atom).getRangeVariable()))                     
                        variableIndividualObject = this.suppSupport.get(i);     
                }
                
                if ((variableIndividualSubject != null) && (variableIndividualObject == null))
                {	
                	VariableIndividual variableIndividual = new VariableIndividual(((RoleAtom) atom).getRangeVariable());
                    newSuppSupport.add(variableIndividual);
                    
                    for(int i = 0; i < variableIndividualSubject.getIndividuals().size(); i++)
                    {
                    	VariableIndividual tempIndividualsOfNewDomainRoleAtom = new VariableIndividual(individualsOfNewDomainRoleAtom);
                    	VariableIndividual tempIndividualsOfNewRangeRoleAtom = new VariableIndividual(individualsOfNewRangeRoleAtom);
                    	
                    	if (tempIndividualsOfNewDomainRoleAtom.getIndividuals().contains(variableIndividualSubject.getIndividual(i)))                    		
                    		while(true)
                    		{
                    			int index = tempIndividualsOfNewDomainRoleAtom.getIndividuals().indexOf(variableIndividualSubject.getIndividual(i));
                    			
                    			if (index != -1)
                    			{
                    				for (int j = 0; j < this.suppSupport.size(); j++)
                        				newSuppSupport.get(j).getIndividuals().add(this.suppSupport.get(j).getIndividual(i));
                    				newSuppSupport.get(newSuppSupport.size() - 1).getIndividuals().add(tempIndividualsOfNewRangeRoleAtom.getIndividual(index));
                    				
                    				tempIndividualsOfNewDomainRoleAtom.removeIndividual(index);
                    				tempIndividualsOfNewRangeRoleAtom.removeIndividual(index);
                    			}
                    			else
                    				break;                    			
                    		}
                    }
                    
                    //Old version
                    /*for(int i = 0; i < variableIndividualSubject.getIndividuals().size(); i++)
                    	for(int j = 0; j < individualsOfNewDomainRoleAtom.getIndividuals().size(); j++)
                    		if (individualsOfNewDomainRoleAtom.getIndividual(j).equals(variableIndividualSubject.getIndividual(i)))
                    		{
                    			for (int k = 0; k < this.suppSupport.size(); k++)
                    				newSuppSupport.get(k).getIndividuals().add(this.suppSupport.get(k).getIndividual(i));
                    			newSuppSupport.get(newSuppSupport.size() - 1).getIndividuals().add(individualsOfNewRangeRoleAtom.getIndividual(j));
                    		}*/	
                }
                else if ((variableIndividualSubject == null) && (variableIndividualObject != null))
                {
                	VariableIndividual variableIndividual = new VariableIndividual(((RoleAtom) atom).getDomainVariable());
                    newSuppSupport.add(variableIndividual);
                    
                    for(int i = 0; i < variableIndividualObject.getIndividuals().size(); i++)
                    {
                    	VariableIndividual tempIndividualsOfNewRangeRoleAtom = new VariableIndividual(individualsOfNewRangeRoleAtom);
                    	VariableIndividual tempIndividualsOfNewDomainRoleAtom = new VariableIndividual(individualsOfNewDomainRoleAtom);                    	
                    	
                    	if (tempIndividualsOfNewRangeRoleAtom.getIndividuals().contains(variableIndividualObject.getIndividual(i)))                    		
                    		while(true)
                    		{
                    			int index = tempIndividualsOfNewRangeRoleAtom.getIndividuals().indexOf(variableIndividualObject.getIndividual(i));
                    			
                    			if (index != -1)
                    			{
                    				for (int j = 0; j < this.suppSupport.size(); j++)
                        				newSuppSupport.get(j).getIndividuals().add(this.suppSupport.get(j).getIndividual(i));
                    				newSuppSupport.get(newSuppSupport.size() - 1).getIndividuals().add(tempIndividualsOfNewDomainRoleAtom.getIndividual(index));
                    				
                    				tempIndividualsOfNewRangeRoleAtom.removeIndividual(index);
                    				tempIndividualsOfNewDomainRoleAtom.removeIndividual(index);
                    			}
                    			else
                    				break;
                    		}
                    }
                    
                    //Old version
                    /*for(int i = 0; i < variableIndividualObject.getIndividuals().size(); i++)
                    	for(int j = 0; j < individualsOfNewRangeRoleAtom.getIndividuals().size(); j++)
                    		if (individualsOfNewRangeRoleAtom.getIndividual(j).equals(variableIndividualObject.getIndividual(i)))
                    		{
                    			for (int k = 0; k < this.suppSupport.size(); k++)
                    				newSuppSupport.get(k).getIndividuals().add(this.suppSupport.get(k).getIndividual(i));
                    			newSuppSupport.get(newSuppSupport.size() - 1).getIndividuals().add(individualsOfNewDomainRoleAtom.getIndividual(j));
                    		}*/
                }
                else if ((variableIndividualSubject != null) && (variableIndividualObject != null))
                {	
                	for(int i = 0; i < variableIndividualSubject.getIndividuals().size(); i++)
                	{
                		VariableIndividual tempIndividualsOfNewDomainRoleAtom = new VariableIndividual(individualsOfNewDomainRoleAtom);
                    	VariableIndividual tempIndividualsOfNewRangeRoleAtom = new VariableIndividual(individualsOfNewRangeRoleAtom);
                		
                		if (tempIndividualsOfNewDomainRoleAtom.getIndividuals().contains(variableIndividualSubject.getIndividual(i)))
                		{
                			while(true)
                    		{
                				int index = tempIndividualsOfNewDomainRoleAtom.getIndividuals().indexOf(variableIndividualSubject.getIndividual(i));
                				
                				if (index != -1)
                    			{
                    				if (tempIndividualsOfNewRangeRoleAtom.getIndividual(index).equals(variableIndividualObject.getIndividual(i)))
                    				{                					
                    					for (int j = 0; j < this.suppSupport.size(); j++)
                    						newSuppSupport.get(j).getIndividuals().add(this.suppSupport.get(j).getIndividual(i));
                    				}
                    				tempIndividualsOfNewDomainRoleAtom.removeIndividual(index);
                    				tempIndividualsOfNewRangeRoleAtom.removeIndividual(index);
                    				
                    			}
                    			else
                    				break;
                    		}
                		}
                	}
                	
                	//Old version
                	/*for(int i = 0; i < variableIndividualSubject.getIndividuals().size(); i++)
                		for(int j = 0; j < individualsOfNewDomainRoleAtom.getIndividuals().size(); j++)
                			if ( (individualsOfNewDomainRoleAtom.getIndividual(j).equals(variableIndividualSubject.getIndividual(i))) &&
                				 (individualsOfNewRangeRoleAtom.getIndividual(j).equals(variableIndividualObject.getIndividual(i))) )                			
                				for (int k = 0; k < this.suppSupport.size(); k++)
                					newSuppSupport.get(k).getIndividuals().add(this.suppSupport.get(k).getIndividual(i));*/
                }
                
                this.suppSupport = newSuppSupport;
            }
		}
    }
    
    private void computeDenominatorHeadCoverage(Atom atom)
    {
    	if (this.size() <= 1)         
        {
    		if (atom instanceof ConceptAtom)            
    			this.denominatorHeadCoverage = Global.allFrequentConceptNamesStratified.get(((ConceptAtom) atom).getIRI()).getIndividuals().size();            
    		else if (atom instanceof RoleAtom)
    			this.denominatorHeadCoverage = Global.allFrequentRoleNamesStratified.get(((RoleAtom) atom).getIRI()).getDomainIndividuals().size();
        }
    }
	
    private void denominatorConfidenceAddAtom(Atom atom)
    {
    	if (this.size() > 1)
    	{
    		ArrayList<VariableIndividual> newDenominatorConfidence = new ArrayList<VariableIndividual>();
    		
            //Size includes head
    		if (this.size() == 2)
            {   
            	this.denominatorConfidence = new ArrayList<VariableIndividual>();
            	
	    		if (atom instanceof ConceptAtom)
	            {	
	    			VariableIndividual newVariableIndividual = new VariableIndividual(((ConceptAtom) atom).getVariable(), Global.allFrequentConceptNamesStratified.get(((ConceptAtom) atom).getIRI()).getIndividuals());                	                	
	    			newDenominatorConfidence.add(newVariableIndividual);	    			
	            }
	    		else if (atom instanceof RoleAtom)
	    		{
	    			VariableIndividual newVariableIndividualDomain = new VariableIndividual(((RoleAtom) atom).getDomainVariable(), Global.allFrequentRoleNamesStratified.get(((RoleAtom) atom).getIRI()).getDomainIndividuals());
	            	VariableIndividual newVariableIndividualRange = new VariableIndividual(((RoleAtom) atom).getRangeVariable(), Global.allFrequentRoleNamesStratified.get(((RoleAtom) atom).getIRI()).getRangeIndividuals());
	            	newDenominatorConfidence.add(newVariableIndividualDomain);
	            	newDenominatorConfidence.add(newVariableIndividualRange);
	    		}
            }
            else
            {	
            	if (this.denominatorConfidence != null)                
                    for (int i = 0; i < this.denominatorConfidence.size(); i++)
                    {
                        VariableIndividual variableIndividual = new VariableIndividual(this.denominatorConfidence.get(i).getNameVariable());
                        newDenominatorConfidence.add(variableIndividual);
                    }
            	
            	if (atom instanceof ConceptAtom)
	            {
            		VariableIndividual individualsOfNewConceptAtom = new VariableIndividual(((ConceptAtom) atom).getVariable(), Global.allFrequentConceptNamesStratified.get(((ConceptAtom) atom).getIRI()).getIndividuals());
                	
                	VariableIndividual variableIndividual = null;                 
                    for (int i = 0; i < this.denominatorConfidence.size(); i++)                
                        if (this.denominatorConfidence.get(i).getNameVariable().equals(((ConceptAtom) atom).getVariable()))
                        {
                            variableIndividual = this.denominatorConfidence.get(i);
                            break;
                        }
                    
                    if (variableIndividual != null)
                    {                    
                    	for(int i = 0; i < variableIndividual.getIndividuals().size(); i++)
                    		if(individualsOfNewConceptAtom.getIndividuals().contains(variableIndividual.getIndividual(i)))
                    			for (int j = 0; j < this.denominatorConfidence.size(); j++)
                    				newDenominatorConfidence.get(j).getIndividuals().add(this.denominatorConfidence.get(j).getIndividual(i));
                    	
                    	//Old version
                    	/*for(int i = 0; i < variableIndividual.getIndividuals().size(); i++)
	                    	for(int j = 0; j < individualsOfNewConceptAtom.getIndividuals().size(); j++)
	                    		if (individualsOfNewConceptAtom.getIndividual(j).equals(variableIndividual.getIndividual(i)))
	                    			for (int k = 0; k < this.denominatorConfidence.size(); k++)
	                    				newDenominatorConfidence.get(k).getIndividuals().add(this.denominatorConfidence.get(k).getIndividual(i));*/
                    }                    
                    else
                    {	
                    	VariableIndividual newVariableIndividual = new VariableIndividual(((ConceptAtom) atom).getVariable());
                        newDenominatorConfidence.add(newVariableIndividual);                        
                        
                        ArrayList<VariableIndividual> copyDenominatorConfidence = new ArrayList<VariableIndividual>(this.denominatorConfidence);
                        
                        ArrayList<String> newIndividuals = new ArrayList<String>();
                        
                        for (String individualOfNewConceptAtom : individualsOfNewConceptAtom.getIndividuals())
                        {
                        	for (int i = 0; i < this.denominatorConfidence.size(); i++)
                        		newDenominatorConfidence.get(i).getIndividuals().addAll(copyDenominatorConfidence.get(i).getIndividuals());
                        	
                        	String[] strNewIndividual = new String[denominatorConfidence.get(0).getIndividuals().size()];
                        	Arrays.fill(strNewIndividual, individualOfNewConceptAtom);
                        	ArrayList<String> strArrayNewIndividual = new ArrayList<String>(Arrays.asList(strNewIndividual));
                        	newIndividuals.addAll(strArrayNewIndividual);
                        }
                        
                        newDenominatorConfidence.get(newDenominatorConfidence.size() - 1).getIndividuals().addAll(newIndividuals);
                        
                        
                        //Old version
                    	/*for (String individualOfNewConceptAtom : individualsOfNewConceptAtom.getIndividuals())
                        {	
                        	for (int i = 0; i < this.denominatorConfidence.size(); i++)
                        		newDenominatorConfidence.get(i).getIndividuals().addAll(copyDenominatorConfidence.get(i).getIndividuals());
                        	
                        	ArrayList<String> loop = new ArrayList<String>();
                			for (int i = 0; i < denominatorConfidence.get(0).getIndividuals().size(); i++)
                				loop.add(individualOfNewConceptAtom);
                			
                			newDenominatorConfidence.get(newDenominatorConfidence.size() - 1).getIndividuals().addAll(loop);
                        }*/
                    }                   
	            }
	    		else if (atom instanceof RoleAtom)
	    		{	    			
	    			VariableIndividual individualsOfNewDomainRoleAtom = new VariableIndividual(((RoleAtom) atom).getDomainVariable(), Global.allFrequentRoleNamesStratified.get(((RoleAtom) atom).getIRI()).getDomainIndividuals());
	            	VariableIndividual individualsOfNewRangeRoleAtom = new VariableIndividual(((RoleAtom) atom).getRangeVariable(), Global.allFrequentRoleNamesStratified.get(((RoleAtom) atom).getIRI()).getRangeIndividuals());
	    			
	    			VariableIndividual variableIndividualSubject = null; 
	    			VariableIndividual variableIndividualObject = null;  
                    
                    for (int i = 0; i < this.denominatorConfidence.size(); i++)
                    {
                        if (this.denominatorConfidence.get(i).getNameVariable().equals(((RoleAtom) atom).getDomainVariable()))                                            
                            variableIndividualSubject = this.denominatorConfidence.get(i);
                        if (this.denominatorConfidence.get(i).getNameVariable().equals(((RoleAtom) atom).getRangeVariable()))                     
                            variableIndividualObject = this.denominatorConfidence.get(i);
                    }
                    
                    if ((variableIndividualSubject != null) && (variableIndividualObject == null))
                    {
                    	VariableIndividual variableIndividual = new VariableIndividual(((RoleAtom) atom).getRangeVariable());
                        newDenominatorConfidence.add(variableIndividual);
                        
                        for(int i = 0; i < variableIndividualSubject.getIndividuals().size(); i++)
                        {
                        	VariableIndividual tempIndividualsOfNewDomainRoleAtom = new VariableIndividual(individualsOfNewDomainRoleAtom);
                        	VariableIndividual tempIndividualsOfNewRangeRoleAtom = new VariableIndividual(individualsOfNewRangeRoleAtom);
                        	
                        	if (tempIndividualsOfNewDomainRoleAtom.getIndividuals().contains(variableIndividualSubject.getIndividual(i))) 
                        		while (true)
                        		{
                        			int index = tempIndividualsOfNewDomainRoleAtom.getIndividuals().indexOf(variableIndividualSubject.getIndividual(i));
                        			
                        			if (index != -1)
                        			{
                        				for (int j = 0; j < this.denominatorConfidence.size(); j++)
                        					newDenominatorConfidence.get(j).getIndividuals().add(this.denominatorConfidence.get(j).getIndividual(i));
                        				newDenominatorConfidence.get(newDenominatorConfidence.size() - 1).getIndividuals().add(tempIndividualsOfNewRangeRoleAtom.getIndividual(index));
                                        				
                        				tempIndividualsOfNewDomainRoleAtom.removeIndividual(index);
                        				tempIndividualsOfNewRangeRoleAtom.removeIndividual(index);
                        			}
                        			else
                        				break;
                        		}
                        }
                        
                        //Old version                        
                        /*for(int i = 0; i < variableIndividualSubject.getIndividuals().size(); i++)
                        	for(int j = 0; j < individualsOfNewDomainRoleAtom.getIndividuals().size(); j++)
                        		if (individualsOfNewDomainRoleAtom.getIndividual(j).equals(variableIndividualSubject.getIndividual(i)))
                        		{
                        			for (int k = 0; k < this.denominatorConfidence.size(); k++)
                        				newDenominatorConfidence.get(k).getIndividuals().add(this.denominatorConfidence.get(k).getIndividual(i));
                        			newDenominatorConfidence.get(newDenominatorConfidence.size() - 1).getIndividuals().add(individualsOfNewRangeRoleAtom.getIndividual(j));
                        		}*/
                    }
                    else if ((variableIndividualSubject == null) && (variableIndividualObject != null))
                    {
                    	VariableIndividual variableIndividual = new VariableIndividual(((RoleAtom) atom).getDomainVariable());
                        newDenominatorConfidence.add(variableIndividual);
                        
                        for(int i = 0; i < variableIndividualObject.getIndividuals().size(); i++)
                        {
                        	VariableIndividual tempIndividualsOfNewRangeRoleAtom = new VariableIndividual(individualsOfNewRangeRoleAtom);
                        	VariableIndividual tempIndividualsOfNewDomainRoleAtom = new VariableIndividual(individualsOfNewDomainRoleAtom);                        	
                        	
                        	if (tempIndividualsOfNewRangeRoleAtom.getIndividuals().contains(variableIndividualObject.getIndividual(i)))                    		
                        		while(true)
                        		{
                        			int index = tempIndividualsOfNewRangeRoleAtom.getIndividuals().indexOf(variableIndividualObject.getIndividual(i));
                                        			
                        			if (index != -1)
                        			{
                        				for (int j = 0; j < this.denominatorConfidence.size(); j++)
                        					newDenominatorConfidence.get(j).getIndividuals().add(this.denominatorConfidence.get(j).getIndividual(i));
                        				newDenominatorConfidence.get(newDenominatorConfidence.size() - 1).getIndividuals().add(tempIndividualsOfNewDomainRoleAtom.getIndividual(index));
                                        				
                        				tempIndividualsOfNewRangeRoleAtom.removeIndividual(index);
                        				tempIndividualsOfNewDomainRoleAtom.removeIndividual(index);
                        			}
                        			else
                        				break;
                        		}
                        }
                        
                        //Old version
                        /*for(int i = 0; i < variableIndividualObject.getIndividuals().size(); i++)
                        	for(int j = 0; j < individualsOfNewRangeRoleAtom.getIndividuals().size(); j++)
                        		if (individualsOfNewRangeRoleAtom.getIndividual(j).equals(variableIndividualObject.getIndividual(i)))
                        		{
                        			for (int k = 0; k < this.denominatorConfidence.size(); k++)
                        				newDenominatorConfidence.get(k).getIndividuals().add(this.denominatorConfidence.get(k).getIndividual(i));
                        			newDenominatorConfidence.get(newDenominatorConfidence.size() - 1).getIndividuals().add(individualsOfNewDomainRoleAtom.getIndividual(j));
                        		}*/
                    }
                    else if ((variableIndividualSubject != null) && (variableIndividualObject != null))
                    {                    	
                    	for(int i = 0; i < variableIndividualSubject.getIndividuals().size(); i++)
                    	{
                    		VariableIndividual tempIndividualsOfNewDomainRoleAtom = new VariableIndividual(individualsOfNewDomainRoleAtom);
                        	VariableIndividual tempIndividualsOfNewRangeRoleAtom = new VariableIndividual(individualsOfNewRangeRoleAtom);
                    		
                    		if (tempIndividualsOfNewDomainRoleAtom.getIndividuals().contains(variableIndividualSubject.getIndividual(i)))
                    		{
                    			while(true)
                        		{
                    				int index = tempIndividualsOfNewDomainRoleAtom.getIndividuals().indexOf(variableIndividualSubject.getIndividual(i));
                    				if (index != -1)
                        			{
                    					if (tempIndividualsOfNewRangeRoleAtom.getIndividual(index).equals(variableIndividualObject.getIndividual(i)))
                    					{                					
                        					for (int j = 0; j < this.denominatorConfidence.size(); j++)
                        						newDenominatorConfidence.get(j).getIndividuals().add(this.denominatorConfidence.get(j).getIndividual(i));
                        				}
                    					tempIndividualsOfNewDomainRoleAtom.removeIndividual(index);
                    					tempIndividualsOfNewRangeRoleAtom.removeIndividual(index);
                        			}
                    				else
                    					break;
                        		}
                    		}
                    	}
                    	
                    	//Old version
                    	/*for(int i = 0; i < variableIndividualSubject.getIndividuals().size(); i++)
                    		for(int j = 0; j < individualsOfNewDomainRoleAtom.getIndividuals().size(); j++)                    		
                    			if ( (individualsOfNewDomainRoleAtom.getIndividual(j).equals(variableIndividualSubject.getIndividual(i))) &&
                    				 (individualsOfNewRangeRoleAtom.getIndividual(j).equals(variableIndividualObject.getIndividual(i))) )                			
                    				for (int k = 0; k < this.denominatorConfidence.size(); k++)
                    					newDenominatorConfidence.get(k).getIndividuals().add(this.denominatorConfidence.get(k).getIndividual(i));*/
                    }
                    else
                    {
                    	VariableIndividual newVariableIndividualDomain = new VariableIndividual(((RoleAtom) atom).getDomainVariable());
                    	VariableIndividual newVariableIndividualRange = new VariableIndividual(((RoleAtom) atom).getRangeVariable());
                        newDenominatorConfidence.add(newVariableIndividualDomain);  
                        newDenominatorConfidence.add(newVariableIndividualRange); 
                        
                        ArrayList<VariableIndividual> copyDenominatorConfidence = new ArrayList<VariableIndividual>(this.denominatorConfidence);
                        ArrayList<String> newIndividualsDomain = new ArrayList<String>();
                        ArrayList<String> newIndividualsRange = new ArrayList<String>();
                        
                        for (int i = 0; i < individualsOfNewDomainRoleAtom.getIndividuals().size(); i++)
                        {
                        	String individualOfNewDomainRoleAtom = individualsOfNewDomainRoleAtom.getIndividuals().get(i);
                        	String individualOfNewRangeRoleAtom = individualsOfNewDomainRoleAtom.getIndividuals().get(i);
                        	
                        	for (int j = 0; j < this.denominatorConfidence.size(); j++)
                        		newDenominatorConfidence.get(j).getIndividuals().addAll(copyDenominatorConfidence.get(j).getIndividuals());
                        	
                        	String[] strNewIndividualDomain = new String[denominatorConfidence.get(0).getIndividuals().size()];
                        	Arrays.fill(strNewIndividualDomain, individualOfNewDomainRoleAtom);
                        	ArrayList<String> strArrayNewIndividualDomain = new ArrayList<String>(Arrays.asList(strNewIndividualDomain));
                        	newIndividualsDomain.addAll(strArrayNewIndividualDomain);
                        	
                        	String[] strNewIndividualRange = new String[denominatorConfidence.get(0).getIndividuals().size()];
                        	Arrays.fill(strNewIndividualRange, individualOfNewRangeRoleAtom);
                        	ArrayList<String> strArrayNewIndividualRange = new ArrayList<String>(Arrays.asList(strNewIndividualRange));
                        	newIndividualsRange.addAll(strArrayNewIndividualRange);
                        }
                        
                        newDenominatorConfidence.get(newDenominatorConfidence.size() - 2).getIndividuals().addAll(newIndividualsDomain);
                        newDenominatorConfidence.get(newDenominatorConfidence.size() - 1).getIndividuals().addAll(newIndividualsRange);
                        
                        //Old version
                        /*for (int i = 0; i < individualsOfNewDomainRoleAtom.getIndividuals().size(); i++)                        	
                        {
                        	for (int j = 0; j < this.denominatorConfidence.size(); j++)
                        		newDenominatorConfidence.get(j).getIndividuals().addAll(copyDenominatorConfidence.get(j).getIndividuals());
                        	
                        	                        	
                        	ArrayList<String> loopDomain = new ArrayList<String>();
                        	
                			for (int j = 0; j < denominatorConfidence.get(0).getIndividuals().size(); j++)
                				loopDomain.add(individualsOfNewDomainRoleAtom.getIndividual(i));
                			
                			ArrayList<String> loopRange = new ArrayList<String>();
                			for (int j = 0; j < denominatorConfidence.get(0).getIndividuals().size(); j++)
                				loopRange.add(individualsOfNewRangeRoleAtom.getIndividual(i));
                			
                			newDenominatorConfidence.get(newDenominatorConfidence.size() - 2).getIndividuals().addAll(loopDomain);
                			newDenominatorConfidence.get(newDenominatorConfidence.size() - 1).getIndividuals().addAll(loopRange);
                        }*/
                        
                    }
	    		}
            }
            
            this.denominatorConfidence = newDenominatorConfidence; 
    	}
    }
    
    public void denominatorPCAConfidenceAddAtom(Atom atom)
    {
    	
    }
}
