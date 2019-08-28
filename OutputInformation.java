import java.io.File;
import java.io.PrintStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectProperty;

public class OutputInformation
{
	public static File outputFile;
	public static PrintStream print_file;
	
	public OutputInformation(String strOutputFileName)
	{
		try
		{
			OutputInformation.outputFile = new File(strOutputFileName);
			OutputInformation.print_file = new PrintStream(OutputInformation.outputFile);
		}
		catch (IOException e)
		{
			e.getStackTrace();
		}
	}
	
	public static void showText(String strText, boolean type)
	{
		OutputInformation.outputType(type).print(strText);
	}
	
	public static void showTextln(String strText, boolean type)
	{
		OutputInformation.outputType(type).println(strText);
	}
	
	public static void showSetOfOWLClass(Set<OWLClass> classes, String strComment, boolean type)
	{
		PrintStream output = OutputInformation.outputType(type);
		output.println("BEGIN -------- " + strComment + " --------");
		int i = 1;
		for(OWLClass conceptAtom : classes)		
			output.println(String.valueOf(i++) + ". " + Global.cutString(conceptAtom.getIRI().toString()));
		output.println("END -------- " + strComment + " --------");
	}
	
	public static void showSetOfOWLObjectProperty(Set<OWLObjectProperty> objectProperties, String strComment, boolean type)
	{
		PrintStream output = OutputInformation.outputType(type);
		output.println("BEGIN -------- " + strComment + " --------");
		int i = 1;
		for(OWLObjectProperty roleAtom : objectProperties)		
			output.println(String.valueOf(i++) + ". " + Global.cutString(roleAtom.getIRI().toString()));		
		output.println("END -------- " + strComment + " --------");
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void showAllFrequentConceptNames(Map allFrequentConceptNames, String strComment, boolean type)
	{
		PrintStream output = OutputInformation.outputType(type);
		output.println("BEGIN -------- " + strComment + " --------");
		Set<IRI> iriConceptNames = allFrequentConceptNames.keySet();
		int i = 1;
		for(IRI iriConceptName : iriConceptNames)	
			output.println(String.valueOf(i++) + ". " + Global.cutString(iriConceptName.toString()));		
		output.println("END -------- " + strComment + " --------");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void showAllFrequentRoleNames(Map allFrequentRoleNames, String strComment, boolean type)
	{
		PrintStream output = OutputInformation.outputType(type);
		output.println("BEGIN -------- " + strComment + " --------");
		Set<IRI> iriRoleNames = allFrequentRoleNames.keySet();
		int i = 1;
		for(IRI iriRoleName : iriRoleNames)	
			output.println(String.valueOf(i++) + ". " + Global.cutString(iriRoleName.toString()));		
		output.println("END -------- " + strComment + " --------");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void showIndividualsOfAllFrequentConceptNames(Map allFrequentConceptNames, String strComment, boolean type)
	{
		PrintStream output = OutputInformation.outputType(type);
		output.println("BEGIN -------- " + strComment + " --------");
		Set<IRI> iriConceptNames = allFrequentConceptNames.keySet();
		int i = 1;
		for(IRI iriConceptName : iriConceptNames)
		{
			output.println(String.valueOf(i++) + ". " + Global.cutString(iriConceptName.toString()));
			ConceptIndividuals conceptIndividuals = ((Map<IRI, ConceptIndividuals>) allFrequentConceptNames).get(iriConceptName);
			int j = 1;
			for(String individual : conceptIndividuals.getIndividuals())
				output.println("   " + String.valueOf(j++) + ": " + Global.cutString(individual));			
		}
		output.println("END -------- " + strComment + " --------");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void showIndividualsOfAllFrequentRoleNames(Map allFrequentRoleNames, String strComment, boolean type)
	{
		PrintStream output = OutputInformation.outputType(type);
		output.println("BEGIN -------- " + strComment + " --------");
		Set<IRI> iriRoleNames = allFrequentRoleNames.keySet();
		int i = 1;
		for(IRI iriRoleName : iriRoleNames)
		{
			output.println(String.valueOf(i++) + ". " + Global.cutString(iriRoleName.toString()));
			RoleIndividuals roleIndividuals = ((Map<IRI, RoleIndividuals>) allFrequentRoleNames).get(iriRoleName);
			
			ArrayList<String> domainIndividuals = roleIndividuals.getDomainIndividuals();
			ArrayList<String> rangeIndividuals = roleIndividuals.getRangeIndividuals();
			
			for(int j = 0; j < domainIndividuals.size(); j++)
			{
				output.println("   Pair " + String.valueOf(j+1) + " - ");
				output.println("      Subject: " + Global.cutString(domainIndividuals.get(j)));
				output.println("      Object : " + Global.cutString(rangeIndividuals.get(j)));
			}
		}
		output.println("END -------- " + strComment + " --------");
	}
	
	public static void showRuleInformation(Rule<Atom> rule, boolean blHeadCoverage, boolean blConfidence, boolean type)
	{
		PrintStream output = OutputInformation.outputType(type);		
		for (int i = 0; i < rule.size(); i++)
		{
			Atom atom = rule.get(i);
			
			if (i == 0)
			{
				if (atom instanceof ConceptAtom)
					output.print("   " + Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") <= ");
				else if (atom instanceof RoleAtom)
					output.print("   " + Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") <= ");
			}
			else
			{
				if (atom instanceof ConceptAtom)
					output.print(Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") & ");
				else if (atom instanceof RoleAtom)
					output.print(Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") & ");
			}
		}
		
		output.println();		
		if (blHeadCoverage) output.println("      HeadCoverage: " + rule.getHeadCoverage());		
		if (blConfidence) output.println("      Confidence: " + rule.getConfidence());
	}
	
	public static void showPatternInformation(Pattern<Atom> pattern, boolean type)
	{
		PrintStream output = OutputInformation.outputType(type);		
		for (int i = 0; i < pattern.size(); i++)
		{
			Atom atom = pattern.get(i);
			
			if (i == 0)
			{
				if (atom instanceof ConceptAtom)
					output.print("   " + Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") <= ");
				else if (atom instanceof RoleAtom)
					output.print("   " + Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") <= ");
			}
			else
			{
				if (atom instanceof ConceptAtom)
					output.print(Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") & ");
				else if (atom instanceof RoleAtom)
					output.print(Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") & ");
			}
		}		
		output.println();
	}
	
	public static void showDiscoveredRules(PatternList<Pattern<Atom>> rules, boolean type)
	{
		PrintStream output = OutputInformation.outputType(type);
		output.println();
		output.println("BEGIN -------- DISCOVERED RULES --------");
		
		for (int k = 0; k < rules.size(); k++)
		{		
			Pattern<Atom> rule = rules.get(k);
			
			for (int i = 0; i < rule.size(); i++)
			{
				Atom atom = rule.get(i);
				
				if (i == 0)
				{
					if (atom instanceof ConceptAtom)
						output.print("   " + Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") <= ");
					else if (atom instanceof RoleAtom)
						output.print("   " + Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") <= ");
				}
				else
				{
					if (atom instanceof ConceptAtom)
						output.print(Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") & ");
					else if (atom instanceof RoleAtom)
						output.print(Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") & ");
				}
			}
			
			output.println();
		}
		output.println("END -------- DISCOVERED RULES --------");
		output.println();
	}
	
	public static void showRuleInformationConceptIndividual(Pattern<Atom> rule, Set<String> predictionIndividuals, Set<String> matchIndividuals, Set<String> inductionIndividuals, boolean type)
	{
		PrintStream output = OutputInformation.outputType(type);
		output.println("BEGIN showing the information of rule: ");
		
		for (int i = 0; i < rule.size(); i++)
		{
			Atom atom = rule.get(i);
			
			if (i == 0)
			{
				if (atom instanceof ConceptAtom)
					output.print("   " + Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") <= ");
				else if (atom instanceof RoleAtom)
					output.print("   " + Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") <= ");
			}
			else
			{
				if (atom instanceof ConceptAtom)
					output.print(Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") & ");
				else if (atom instanceof RoleAtom)
					output.print(Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") & ");
			}
		}
		
		output.println();
		output.println("      HeadCoverage: " + rule.getHeadCoverage());
		output.println("      Confidence: " + rule.getConfidence());
		output.print("      Predictions: ");
		
		if (predictionIndividuals.size() > 0)
		{
			output.println();
			int i = 0;
			for(String individual : predictionIndividuals)
				output.println("         " + String.valueOf(++i) + ". " + Global.cutString(individual));
		}
		else
			output.println("No prediction");
		
		if (matchIndividuals.size() > 0)
		{
			output.println("      Match: " + String.valueOf((double)matchIndividuals.size() / predictionIndividuals.size()));
			int i = 0;
			for(String individual : matchIndividuals)
				output.println("         " + String.valueOf(++i) + ". " + Global.cutString(individual));
		}
		else
			output.println("      Match: No match");
		
		if (inductionIndividuals.size() > 0)
		{
			output.println("      Induction: " + String.valueOf((double)inductionIndividuals.size() / predictionIndividuals.size()));
			int i = 0;
			for(String individual : inductionIndividuals)
				output.println("         " + String.valueOf(++i) + ". " + Global.cutString(individual));
		}
		else
			output.println("      Induction: No induction");
		
		output.println("END ------------------------");
		output.println();
	}
	
	public static void showRuleInformationRoleIndividual(Pattern<Atom> rule, Set<RoleIndividual> predictionIndividuals, Set<RoleIndividual> matchIndividuals, Set<RoleIndividual> inductionIndividuals, boolean type)
	{
		PrintStream output = OutputInformation.outputType(type);
		output.println("BEGIN showing the information of rule: ");
		
		for (int i = 0; i < rule.size(); i++)
		{
			Atom atom = rule.get(i);
			
			if (i == 0)
			{
				if (atom instanceof ConceptAtom)
					output.print("   " + Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") <= ");
				else if (atom instanceof RoleAtom)
					output.print("   " + Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") <= ");
			}
			else
			{
				if (atom instanceof ConceptAtom)
					output.print(Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") & ");
				else if (atom instanceof RoleAtom)
					output.print(Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") & ");
			}
		}
		
		output.println();
		output.println("      HeadCoverage: " + rule.getHeadCoverage());
		output.println("      Confidence: " + rule.getConfidence());
		output.print("      Predictions: ");
		
		if (predictionIndividuals.size() > 0)
		{
			output.println();
			int i = 0;
			for(RoleIndividual individual : predictionIndividuals)
			{
				output.println("         Pair " + String.valueOf(++i) + " - ");
				output.println("            Subject: " + Global.cutString(individual.getX()));
				output.println("            Object : " + Global.cutString(individual.getY()));				
			}
		}
		else
			output.println("No prediction");
		
		if (matchIndividuals.size() > 0)
		{
			output.println("      Match: " + String.valueOf((double)matchIndividuals.size() / predictionIndividuals.size()));
			int i = 0;
			for(RoleIndividual individual : matchIndividuals)
			{
				output.println("         Pair " + String.valueOf(++i) + " - ");
				output.println("            Subject: " + Global.cutString(individual.getX()));
				output.println("            Object : " + Global.cutString(individual.getY()));				
			}
		}
		else
			output.println("      Match: No match");
		
		if (inductionIndividuals.size() > 0)
		{
			output.println("      Induction: " + String.valueOf((double)inductionIndividuals.size() / predictionIndividuals.size()));
			int i = 0;
			for(RoleIndividual individual : inductionIndividuals)
			{
				output.println("         Pair " + String.valueOf(++i) + " - ");
				output.println("            Subject: " + Global.cutString(individual.getX()));
				output.println("            Object : " + Global.cutString(individual.getY()));				
			}
		}
		else
			output.println("      Induction: No induction");
		
		output.println("END ------------------------");
		output.println();
	}
	
	public static void showRuleInformationConceptPredictionMatchCommisionInduction(Pattern<Atom> rule, Set<String> predictionIndividuals, Set<String> matchIndividuals, Set<String> inductionIndividuals, boolean type)
	{
		PrintStream output = OutputInformation.outputType(type);
				
		output.print(String.valueOf(predictionIndividuals.size()) + "\t" + String.valueOf(matchIndividuals.size()) + "\t0\t" + String.valueOf(inductionIndividuals.size()) + "\t");
		
		for (int i = 0; i < rule.size(); i++)
		{
			Atom atom = rule.get(i);
			
			if (i == 0)
			{
				if (atom instanceof ConceptAtom)
					output.print("   " + Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") <= ");
				else if (atom instanceof RoleAtom)
					output.print("   " + Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") <= ");
			}
			else
			{
				if (atom instanceof ConceptAtom)
					output.print(Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") & ");
				else if (atom instanceof RoleAtom)
					output.print(Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") & ");
			}
		}
		
		output.println();
	}
	
	public static void showRuleInformationRolePredictionMatchCommisionInduction(Pattern<Atom> rule, Set<RoleIndividual> predictionIndividuals, Set<RoleIndividual> matchIndividuals, Set<RoleIndividual> inductionIndividuals, boolean type)
	{
		PrintStream output = OutputInformation.outputType(type);
				
		output.print(String.valueOf(predictionIndividuals.size()) + "\t" + String.valueOf(matchIndividuals.size()) + "\t0\t" + String.valueOf(inductionIndividuals.size()) + "\t");
		
		for (int i = 0; i < rule.size(); i++)
		{
			Atom atom = rule.get(i);
			
			if (i == 0)
			{
				if (atom instanceof ConceptAtom)
					output.print("   " + Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") <= ");
				else if (atom instanceof RoleAtom)
					output.print("   " + Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") <= ");
			}
			else
			{
				if (atom instanceof ConceptAtom)
					output.print(Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") & ");
				else if (atom instanceof RoleAtom)
					output.print(Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") & ");
			}
		}
		
		output.println();
	}
	
	public static PrintStream outputType(boolean type)
	{
		if (type)
			return System.out;
		else
			return OutputInformation.print_file;
	}
	
	public void close()
	{
		OutputInformation.print_file.close();
	}
}
