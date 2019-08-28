import java.io.File;
import java.io.PrintStream;
import java.io.IOException;
import java.util.Set;

public class OutputPrediction
{
	public static File outputFile;
	public static PrintStream print_file;
	
	public OutputPrediction()
	{
		try
		{
			//OutputPrediction.outputFile = new File("Prediction_" + Global.OUTPUT_FILE_NAME);
			OutputPrediction.outputFile = new File("Prediction_");
			OutputPrediction.print_file = new PrintStream(OutputPrediction.outputFile);
		}
		catch (IOException e)
		{
			e.getStackTrace();
		}
	}
	
	public static void showText(String strText, boolean type)
	{
		OutputPrediction.outputType(type).print(strText);
	}
	
	public static void showTextln(String strText, boolean type)
	{
		OutputPrediction.outputType(type).println(strText);
	}
	
	public static void showRuleInformation(Pattern<Atom> rule, Set<String> predictionIndividuals, boolean type)
	{
		PrintStream output = OutputPrediction.outputType(type);
		
		for (int i = 0; i < rule.size(); i++)
		{
			Atom atom = rule.get(i);
			
			if (i == 0)
			{
				if (atom instanceof ConceptAtom)
					output.print(Global.cutString(((ConceptAtom) atom).getIRI().toString()) + "(" + ((ConceptAtom) atom).getVariable() + ") <= ");
				else if (atom instanceof RoleAtom)
					output.print(Global.cutString(((RoleAtom) atom).getIRI().toString()) + "(" + ((RoleAtom) atom).getDomainVariable() + ", " + ((RoleAtom) atom).getRangeVariable() + ") <= ");
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
		
		if (predictionIndividuals.size() > 0)
		{			
			for(String individual : predictionIndividuals)
				output.println(Global.cutString(individual));
		}
		else
			output.println("No prediction");
		
		output.println();
	}
	
	
	public static PrintStream outputType(boolean type)
	{
		if (type)
			return System.out;
		else
			return OutputPrediction.print_file;
	}
	
	public void close()
	{
		OutputPrediction.print_file.close();
	}
}