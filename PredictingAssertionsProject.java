import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;

public class PredictingAssertionsProject 
{
    public PredictingAssertionsProject()
    {}
	
    public void run(String strOutputFileName, IRI iriFull, IRI iriStratified)
    {
        OutputInformation output = new OutputInformation(strOutputFileName);

        Date start = new Date();
        long startValue = start.getTime(); 		
        OutputInformation.showTextln(start.toString(), true);

        KnowledgeBase knowledgeBaseFull = new KnowledgeBase(iriFull);
        KnowledgeBase knowledgeBaseStratified = new KnowledgeBase(iriStratified);
        DiscoveringRelationalAssociationRules discoveringRAR = new DiscoveringRelationalAssociationRules(knowledgeBaseFull, knowledgeBaseStratified, iriFull);
        discoveringRAR.runFullVersion();

        Date end = new Date();
        long runTime = end.getTime() - startValue;        
        long milisecond = runTime % 1000;
        long second = runTime / 1000;
        long minute = second / 60;
        second = second % 60;
        long hour = minute / 60;
        minute = minute % 60;        
        String strTime = String.valueOf(hour) + " hours " + String.valueOf(minute) + " minutes " + String.valueOf(second) + " seconds and " + String.valueOf(milisecond) + " miliseconds. ";        		
        OutputInformation.showTextln("Runtime is " + strTime, true);
        OutputInformation.showTextln("Runtime is " + strTime, false);        

            //KnowledgeBase knowledgeBase = new KnowledgeBase(Global.IRI_INPUT_FULL);
            //SupportPredictingAssertions support = new SupportPredictingAssertions(knowledgeBase);
            //support.reducingOntology(Global.OUTPUT_FILE_NAME_REDUCE, (double) 40 / 100);

        output.close();

        OutputInformation.showTextln("Finish!", true);
    }

    public static void main(String[] args) 
    {		
            PredictingAssertionsProject pa = new PredictingAssertionsProject();
            
            pa.run(Global.OUTPUT_FILE_NAME_BIO_40, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_40);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_30, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_30);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_20, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_20);
            
            pa.run(Global.OUTPUT_FILE_NAME_FINA_40, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_40);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_30, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_30);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_20, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_20);
            
            pa.run(Global.OUTPUT_FILE_NAME_NMT_40, Global.IRI_INPUT_FULL_NMT, Global.IRI_INPUT_STRATIFIED_NMT_40);
            pa.run(Global.OUTPUT_FILE_NAME_NMT_30, Global.IRI_INPUT_FULL_NMT, Global.IRI_INPUT_STRATIFIED_NMT_30);
            pa.run(Global.OUTPUT_FILE_NAME_NMT_20, Global.IRI_INPUT_FULL_NMT, Global.IRI_INPUT_STRATIFIED_NMT_20);

            //pa.run(Global.OUTPUT_FILE_NAME_NMT_40_1, Global.IRI_INPUT_FULL_NMT, Global.IRI_INPUT_STRATIFIED_NMT_40_1);	

            //pa.run(Global.OUTPUT_FILE_NAME_NMT_30_1, Global.IRI_INPUT_FULL_NMT, Global.IRI_INPUT_STRATIFIED_NMT_30_1);

            //pa.run(Global.OUTPUT_FILE_NAME_NMT_20_1, Global.IRI_INPUT_FULL_NMT, Global.IRI_INPUT_STRATIFIED_NMT_20_1);

            /*pa.run(Global.OUTPUT_FILE_NAME_BIO_40, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_40);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_40_2, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_40_2);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_40_3, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_40_3);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_40_4, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_40_4);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_40_5, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_40_5);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_40_6, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_40_6);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_40_7, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_40_7);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_40_8, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_40_8);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_40_9, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_40_9);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_40_10, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_40_10);

            pa.run(Global.OUTPUT_FILE_NAME_BIO_30_1, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_30_1);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_30_2, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_30_2);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_30_3, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_30_3);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_30_4, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_30_4);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_30_5, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_30_5);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_30_6, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_30_6);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_30_7, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_30_7);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_30_8, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_30_8);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_30_9, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_30_9);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_30_10, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_30_10);

            pa.run(Global.OUTPUT_FILE_NAME_BIO_20_1, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_20_1);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_20_2, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_20_2);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_20_3, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_20_3);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_20_4, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_20_4);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_20_5, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_20_5);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_20_6, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_20_6);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_20_7, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_20_7);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_20_8, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_20_8);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_20_9, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_20_9);
            pa.run(Global.OUTPUT_FILE_NAME_BIO_20_10, Global.IRI_INPUT_FULL_BIO, Global.IRI_INPUT_STRATIFIED_BIO_20_10);


            pa.run(Global.OUTPUT_FILE_NAME_FINA_40_1, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_40_1);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_40_2, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_40_2);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_40_3, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_40_3);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_40_4, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_40_4);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_40_5, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_40_5);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_40_6, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_40_6);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_40_7, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_40_7);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_40_8, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_40_8);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_40_9, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_40_9);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_40_10, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_40_10);

            pa.run(Global.OUTPUT_FILE_NAME_FINA_30_1, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_30_1);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_30_2, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_30_2);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_30_3, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_30_3);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_30_4, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_30_4);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_30_5, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_30_5);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_30_6, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_30_6);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_30_7, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_30_7);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_30_8, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_30_8);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_30_9, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_30_9);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_30_10, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_30_10);

            pa.run(Global.OUTPUT_FILE_NAME_FINA_20_1, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_20_1);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_20_2, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_20_2);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_20_3, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_20_3);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_20_4, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_20_4);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_20_5, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_20_5);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_20_6, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_20_6);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_20_7, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_20_7);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_20_8, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_20_8);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_20_9, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_20_9);
            pa.run(Global.OUTPUT_FILE_NAME_FINA_20_10, Global.IRI_INPUT_FULL_FINA, Global.IRI_INPUT_STRATIFIED_FINA_20_10);*/ 



    }
}
