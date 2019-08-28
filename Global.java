import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

import org.semanticweb.HermiT.Reasoner;
import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLVariable;

public class Global 
{
    public static final String FILE_NAME_FULL_BIO = "file:/home/tdminh/Datas/SemanticWeb/owls/Biopax_Full.owl";
    public static final IRI IRI_INPUT_FULL_BIO = IRI.create(FILE_NAME_FULL_BIO);    
    public static final String FILE_NAME_STRATIFIED_BIO_40 = "file:/home/tdminh/Datas/SemanticWeb/owls/Biopax_40.owl";
    public static final IRI IRI_INPUT_STRATIFIED_BIO_40 = IRI.create(FILE_NAME_STRATIFIED_BIO_40);
    public static final String OUTPUT_FILE_NAME_BIO_40 = "BioPax_40.txt";
    public static final String FILE_NAME_STRATIFIED_BIO_30 = "file:/home/tdminh/Datas/SemanticWeb/owls/Biopax_30.owl";
    public static final IRI IRI_INPUT_STRATIFIED_BIO_30 = IRI.create(FILE_NAME_STRATIFIED_BIO_30);
    public static final String OUTPUT_FILE_NAME_BIO_30 = "BioPax_30.txt";
    public static final String FILE_NAME_STRATIFIED_BIO_20 = "file:/home/tdminh/Datas/SemanticWeb/owls/Biopax_20.owl";
    public static final IRI IRI_INPUT_STRATIFIED_BIO_20 = IRI.create(FILE_NAME_STRATIFIED_BIO_20);
    public static final String OUTPUT_FILE_NAME_BIO_20 = "BioPax_20.txt";
    
    
    public static final String FILE_NAME_FULL_FINA = "file:/home/tdminh/Datas/SemanticWeb/owls/Financial_Full.owl";
    public static final IRI IRI_INPUT_FULL_FINA = IRI.create(FILE_NAME_FULL_FINA);    
    public static final String FILE_NAME_STRATIFIED_FINA_40 = "file:/home/tdminh/Datas/SemanticWeb/owls/Financial_40.owl";
    public static final IRI IRI_INPUT_STRATIFIED_FINA_40 = IRI.create(FILE_NAME_STRATIFIED_FINA_40);
    public static final String OUTPUT_FILE_NAME_FINA_40 = "Financial_40.txt";
    public static final String FILE_NAME_STRATIFIED_FINA_30 = "file:/home/tdminh/Datas/SemanticWeb/owls/Financial_30.owl";
    public static final IRI IRI_INPUT_STRATIFIED_FINA_30 = IRI.create(FILE_NAME_STRATIFIED_FINA_30);
    public static final String OUTPUT_FILE_NAME_FINA_30 = "Financial_30.txt";
    public static final String FILE_NAME_STRATIFIED_FINA_20 = "file:/home/tdminh/Datas/SemanticWeb/owls/Financial_20.owl";
    public static final IRI IRI_INPUT_STRATIFIED_FINA_20 = IRI.create(FILE_NAME_STRATIFIED_FINA_20);
    public static final String OUTPUT_FILE_NAME_FINA_20 = "Financial_20.txt";
    
    public static final String FILE_NAME_FULL_NMT = "file:/home/tdminh/Datas/SemanticWeb/owls/NTMerged_Full.owl";
    public static final IRI IRI_INPUT_FULL_NMT = IRI.create(FILE_NAME_FULL_NMT);    
    public static final String FILE_NAME_STRATIFIED_NMT_40 = "file:/home/tdminh/Datas/SemanticWeb/owls/NTMerged_40.owl";
    public static final IRI IRI_INPUT_STRATIFIED_NMT_40 = IRI.create(FILE_NAME_STRATIFIED_NMT_40);
    public static final String OUTPUT_FILE_NAME_NMT_40 = "NTMerged_40.txt";
    public static final String FILE_NAME_STRATIFIED_NMT_30 = "file:/home/tdminh/Datas/SemanticWeb/owls/NTMerged_30.owl";
    public static final IRI IRI_INPUT_STRATIFIED_NMT_30 = IRI.create(FILE_NAME_STRATIFIED_NMT_30);
    public static final String OUTPUT_FILE_NAME_NMT_30 = "NTMerged_30.txt";
    public static final String FILE_NAME_STRATIFIED_NMT_20 = "file:/home/tdminh/Datas/SemanticWeb/owls/NTMerged_20.owl";
    public static final IRI IRI_INPUT_STRATIFIED_NMT_20 = IRI.create(FILE_NAME_STRATIFIED_NMT_20);
    public static final String OUTPUT_FILE_NAME_NMT_20 = "NTMerged_20.txt";
    
    
    /*
    
	//public static final String PREFIX = "<http://semanticbible.org/ns/2005/NTNames#>";
	//public static final String FILE_NAME_FULL = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/NTMerged_Full.owl";
    //public static final String FILE_NAME_STRATIFIED = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/NTMerged_20_1.owl";
	
	public static final String FILE_NAME_FULL_NMT = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/NTMerged_Full.owl";
	public static final String FILE_NAME_STRATIFIED_NMT_40_1 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/NTMerged_Full.owl";
	
	public static final String FILE_NAME_STRATIFIED_NMT_30_1 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/NTMerged_30_1.owl";
	
	public static final String FILE_NAME_STRATIFIED_NMT_20_1 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/NTMerged_20_1.owl";
	
	//public static final String PREFIX = "<http://www.owl-ontologies.com/unnamed.owl#>";
    //public static final String FILE_NAME_FULL = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_Full.owl";
	//public static final String FILE_NAME_STRATIFIED = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_40_1.owl";
	
	public static final String FILE_NAME_FULL_FINA = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_Full.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_40_1 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_Full.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_40_2 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_40_2.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_40_3 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_40_3.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_40_4 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_40_4.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_40_5 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_40_5.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_40_6 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_40_6.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_40_7 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_40_7.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_40_8 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_40_8.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_40_9 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_40_9.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_40_10 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_40_10.owl";
	
	public static final String FILE_NAME_STRATIFIED_FINA_30_1 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_30_1.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_30_2 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_30_2.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_30_3 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_30_3.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_30_4 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_30_4.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_30_5 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_30_5.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_30_6 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_30_6.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_30_7 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_30_7.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_30_8 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_30_8.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_30_9 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_30_9.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_30_10 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_30_10.owl";
	
	public static final String FILE_NAME_STRATIFIED_FINA_20_1 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_20_1.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_20_2 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_20_2.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_20_3 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_20_3.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_20_4 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_20_4.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_20_5 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_20_5.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_20_6 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_20_6.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_20_7 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_20_7.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_20_8 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_20_8.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_20_9 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_20_9.owl";
	public static final String FILE_NAME_STRATIFIED_FINA_20_10 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Financial_20_10.owl";
	
	//public static final String PREFIX = "<http://www.di.uniba.it/~cdamato/ontologies/Dinastie#>";
    //public static final String FILE_NAME_FULL = "file:/home/tdminh/workspace/JenaTutorial/minh_owls/DinastieConPiuFamiglieEnglishVersion.owl";
    //public static final String FILE_NAME_STRATIFIED = "file:/home/tdminh/workspace/PredictingAssertions/Data/StratifiedSampling/owl/Stratified_Family_20.owl";    
    
    //public static final String PREFIX = "<http://www.biopax.org/examples/glycolysis#>";
    //public static final String FILE_NAME_FULL = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_Full.owl";
    //public static final String FILE_NAME_STRATIFIED = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_40_1.owl";
	
	public static final String FILE_NAME_FULL_BIO = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_Full.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_40_1 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_40_1.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_40_2 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_40_2.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_40_3 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_40_3.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_40_4 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_40_4.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_40_5 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_40_5.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_40_6 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_40_6.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_40_7 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_40_7.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_40_8 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_40_8.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_40_9 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_40_9.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_40_10 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_40_10.owl";
    
    public static final String FILE_NAME_STRATIFIED_BIO_30_1 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_30_1.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_30_2 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_30_2.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_30_3 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_30_3.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_30_4 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_30_4.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_30_5 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_30_5.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_30_6 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_30_6.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_30_7 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_30_7.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_30_8 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_30_8.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_30_9 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_30_9.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_30_10 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_30_10.owl";
    
    public static final String FILE_NAME_STRATIFIED_BIO_20_1 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_20_1.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_20_2 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_20_2.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_20_3 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_20_3.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_20_4 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_20_4.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_20_5 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_20_5.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_20_6 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_20_6.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_20_7 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_20_7.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_20_8 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_20_8.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_20_9 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_20_9.owl";
    public static final String FILE_NAME_STRATIFIED_BIO_20_10 = "file:/home/tdminh/workspace/PredictingAssertions/Data/owl/Biopax_20_10.owl";
    
    //public static final String PREFIX = "<http://www.biopax.org/examples/glycolysis#>";
    //public static final String FILE_NAME_FULL = "file:/home/tdminh/workspace/PredictingAssertions/family.owl";
    //public static final String FILE_NAME_STRATIFIED = "file:/home/tdminh/workspace/PredictingAssertions/family.owl";
    
    //public static final IRI IRI_INPUT_FULL = IRI.create(FILE_NAME_FULL);    
    //public static final IRI IRI_INPUT_STRATIFIED = IRI.create(FILE_NAME_STRATIFIED);
    
    public static final IRI IRI_INPUT_FULL_NMT = IRI.create(FILE_NAME_FULL_NMT);    
    public static final IRI IRI_INPUT_STRATIFIED_NMT_40_1 = IRI.create(FILE_NAME_STRATIFIED_NMT_40_1);
    
    public static final IRI IRI_INPUT_STRATIFIED_NMT_30_1 = IRI.create(FILE_NAME_STRATIFIED_NMT_30_1);
    
    public static final IRI IRI_INPUT_STRATIFIED_NMT_20_1 = IRI.create(FILE_NAME_STRATIFIED_NMT_20_1);
    
    public static final IRI IRI_INPUT_FULL_FINA = IRI.create(FILE_NAME_FULL_FINA);    
    public static final IRI IRI_INPUT_STRATIFIED_FINA_40_1 = IRI.create(FILE_NAME_STRATIFIED_FINA_40_1);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_40_2 = IRI.create(FILE_NAME_STRATIFIED_FINA_40_2);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_40_3 = IRI.create(FILE_NAME_STRATIFIED_FINA_40_3);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_40_4 = IRI.create(FILE_NAME_STRATIFIED_FINA_40_4);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_40_5 = IRI.create(FILE_NAME_STRATIFIED_FINA_40_5);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_40_6 = IRI.create(FILE_NAME_STRATIFIED_FINA_40_6);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_40_7 = IRI.create(FILE_NAME_STRATIFIED_FINA_40_7);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_40_8 = IRI.create(FILE_NAME_STRATIFIED_FINA_40_8);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_40_9 = IRI.create(FILE_NAME_STRATIFIED_FINA_40_9);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_40_10 = IRI.create(FILE_NAME_STRATIFIED_FINA_40_10);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_30_1 = IRI.create(FILE_NAME_STRATIFIED_FINA_30_1);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_30_2 = IRI.create(FILE_NAME_STRATIFIED_FINA_30_2);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_30_3 = IRI.create(FILE_NAME_STRATIFIED_FINA_30_3);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_30_4 = IRI.create(FILE_NAME_STRATIFIED_FINA_30_4);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_30_5 = IRI.create(FILE_NAME_STRATIFIED_FINA_30_5);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_30_6 = IRI.create(FILE_NAME_STRATIFIED_FINA_30_6);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_30_7 = IRI.create(FILE_NAME_STRATIFIED_FINA_30_7);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_30_8 = IRI.create(FILE_NAME_STRATIFIED_FINA_30_8);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_30_9 = IRI.create(FILE_NAME_STRATIFIED_FINA_30_9);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_30_10 = IRI.create(FILE_NAME_STRATIFIED_FINA_30_10);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_20_1 = IRI.create(FILE_NAME_STRATIFIED_FINA_20_1);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_20_2 = IRI.create(FILE_NAME_STRATIFIED_FINA_20_2);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_20_3 = IRI.create(FILE_NAME_STRATIFIED_FINA_20_3);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_20_4 = IRI.create(FILE_NAME_STRATIFIED_FINA_20_4);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_20_5 = IRI.create(FILE_NAME_STRATIFIED_FINA_20_5);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_20_6 = IRI.create(FILE_NAME_STRATIFIED_FINA_20_6);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_20_7 = IRI.create(FILE_NAME_STRATIFIED_FINA_20_7);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_20_8 = IRI.create(FILE_NAME_STRATIFIED_FINA_20_8);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_20_9 = IRI.create(FILE_NAME_STRATIFIED_FINA_20_9);
    public static final IRI IRI_INPUT_STRATIFIED_FINA_20_10 = IRI.create(FILE_NAME_STRATIFIED_FINA_20_10);
    
    public static final IRI IRI_INPUT_FULL_BIO = IRI.create(FILE_NAME_FULL_BIO);    
    public static final IRI IRI_INPUT_STRATIFIED_BIO_40_1 = IRI.create(FILE_NAME_STRATIFIED_BIO_40_1);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_40_2 = IRI.create(FILE_NAME_STRATIFIED_BIO_40_2);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_40_3 = IRI.create(FILE_NAME_STRATIFIED_BIO_40_3);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_40_4 = IRI.create(FILE_NAME_STRATIFIED_BIO_40_4);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_40_5 = IRI.create(FILE_NAME_STRATIFIED_BIO_40_5);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_40_6 = IRI.create(FILE_NAME_STRATIFIED_BIO_40_6);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_40_7 = IRI.create(FILE_NAME_STRATIFIED_BIO_40_7);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_40_8 = IRI.create(FILE_NAME_STRATIFIED_BIO_40_8);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_40_9 = IRI.create(FILE_NAME_STRATIFIED_BIO_40_9);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_40_10 = IRI.create(FILE_NAME_STRATIFIED_BIO_40_10);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_30_1 = IRI.create(FILE_NAME_STRATIFIED_BIO_30_1);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_30_2 = IRI.create(FILE_NAME_STRATIFIED_BIO_30_2);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_30_3 = IRI.create(FILE_NAME_STRATIFIED_BIO_30_3);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_30_4 = IRI.create(FILE_NAME_STRATIFIED_BIO_30_4);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_30_5 = IRI.create(FILE_NAME_STRATIFIED_BIO_30_5);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_30_6 = IRI.create(FILE_NAME_STRATIFIED_BIO_30_6);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_30_7 = IRI.create(FILE_NAME_STRATIFIED_BIO_30_7);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_30_8 = IRI.create(FILE_NAME_STRATIFIED_BIO_30_8);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_30_9 = IRI.create(FILE_NAME_STRATIFIED_BIO_30_9);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_30_10 = IRI.create(FILE_NAME_STRATIFIED_BIO_30_10);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_20_1 = IRI.create(FILE_NAME_STRATIFIED_BIO_20_1);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_20_2 = IRI.create(FILE_NAME_STRATIFIED_BIO_20_2);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_20_3 = IRI.create(FILE_NAME_STRATIFIED_BIO_20_3);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_20_4 = IRI.create(FILE_NAME_STRATIFIED_BIO_20_4);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_20_5 = IRI.create(FILE_NAME_STRATIFIED_BIO_20_5);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_20_6 = IRI.create(FILE_NAME_STRATIFIED_BIO_20_6);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_20_7 = IRI.create(FILE_NAME_STRATIFIED_BIO_20_7);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_20_8 = IRI.create(FILE_NAME_STRATIFIED_BIO_20_8);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_20_9 = IRI.create(FILE_NAME_STRATIFIED_BIO_20_9);
    public static final IRI IRI_INPUT_STRATIFIED_BIO_20_10 = IRI.create(FILE_NAME_STRATIFIED_BIO_20_10);
    
    //public static final String OUTPUT_FILE_NAME = "BioPax_40_1.txt";
    
    public static final String OUTPUT_FILE_NAME_NMT_40_1 = "NTMerged_40_1.txt";
    
    public static final String OUTPUT_FILE_NAME_NMT_30_1 = "NTMerged_30_1.txt";
    
    public static final String OUTPUT_FILE_NAME_NMT_20_1 = "NTMerged_20_1.txt";
    
    public static final String OUTPUT_FILE_NAME_FINA_40_1 = "Financial_40_1.txt";
    public static final String OUTPUT_FILE_NAME_FINA_40_2 = "Financial_40_2.txt";
    public static final String OUTPUT_FILE_NAME_FINA_40_3 = "Financial_40_3.txt";
    public static final String OUTPUT_FILE_NAME_FINA_40_4 = "Financial_40_4.txt";
    public static final String OUTPUT_FILE_NAME_FINA_40_5 = "Financial_40_5.txt";
    public static final String OUTPUT_FILE_NAME_FINA_40_6 = "Financial_40_6.txt";
    public static final String OUTPUT_FILE_NAME_FINA_40_7 = "Financial_40_7.txt";
    public static final String OUTPUT_FILE_NAME_FINA_40_8 = "Financial_40_8.txt";
    public static final String OUTPUT_FILE_NAME_FINA_40_9 = "Financial_40_9.txt";
    public static final String OUTPUT_FILE_NAME_FINA_40_10 = "Financial_40_10.txt";
    
    public static final String OUTPUT_FILE_NAME_FINA_30_1 = "Financial_30_1.txt";
    public static final String OUTPUT_FILE_NAME_FINA_30_2 = "Financial_30_2.txt";
    public static final String OUTPUT_FILE_NAME_FINA_30_3 = "Financial_30_3.txt";
    public static final String OUTPUT_FILE_NAME_FINA_30_4 = "Financial_30_4.txt";
    public static final String OUTPUT_FILE_NAME_FINA_30_5 = "Financial_30_5.txt";
    public static final String OUTPUT_FILE_NAME_FINA_30_6 = "Financial_30_6.txt";
    public static final String OUTPUT_FILE_NAME_FINA_30_7 = "Financial_30_7.txt";
    public static final String OUTPUT_FILE_NAME_FINA_30_8 = "Financial_30_8.txt";
    public static final String OUTPUT_FILE_NAME_FINA_30_9 = "Financial_30_9.txt";
    public static final String OUTPUT_FILE_NAME_FINA_30_10 = "Financial_30_10.txt";
    
    public static final String OUTPUT_FILE_NAME_FINA_20_1 = "Financial_20_1.txt";
    public static final String OUTPUT_FILE_NAME_FINA_20_2 = "Financial_20_2.txt";
    public static final String OUTPUT_FILE_NAME_FINA_20_3 = "Financial_20_3.txt";
    public static final String OUTPUT_FILE_NAME_FINA_20_4 = "Financial_20_4.txt";
    public static final String OUTPUT_FILE_NAME_FINA_20_5 = "Financial_20_5.txt";
    public static final String OUTPUT_FILE_NAME_FINA_20_6 = "Financial_20_6.txt";
    public static final String OUTPUT_FILE_NAME_FINA_20_7 = "Financial_20_7.txt";
    public static final String OUTPUT_FILE_NAME_FINA_20_8 = "Financial_20_8.txt";
    public static final String OUTPUT_FILE_NAME_FINA_20_9 = "Financial_20_9.txt";
    public static final String OUTPUT_FILE_NAME_FINA_20_10 = "Financial_20_10.txt";
    
    public static final String OUTPUT_FILE_NAME_BIO_40_1 = "BioPax_40_1.txt";
    public static final String OUTPUT_FILE_NAME_BIO_40_2 = "BioPax_40_2.txt";
    public static final String OUTPUT_FILE_NAME_BIO_40_3 = "BioPax_40_3.txt";
    public static final String OUTPUT_FILE_NAME_BIO_40_4 = "BioPax_40_4.txt";
    public static final String OUTPUT_FILE_NAME_BIO_40_5 = "BioPax_40_5.txt";
    public static final String OUTPUT_FILE_NAME_BIO_40_6 = "BioPax_40_6.txt";
    public static final String OUTPUT_FILE_NAME_BIO_40_7 = "BioPax_40_7.txt";
    public static final String OUTPUT_FILE_NAME_BIO_40_8 = "BioPax_40_8.txt";
    public static final String OUTPUT_FILE_NAME_BIO_40_9 = "BioPax_40_9.txt";
    public static final String OUTPUT_FILE_NAME_BIO_40_10 = "BioPax_40_10.txt";
    
    public static final String OUTPUT_FILE_NAME_BIO_30_1 = "BioPax_30_1.txt";
    public static final String OUTPUT_FILE_NAME_BIO_30_2 = "BioPax_30_2.txt";
    public static final String OUTPUT_FILE_NAME_BIO_30_3 = "BioPax_30_3.txt";
    public static final String OUTPUT_FILE_NAME_BIO_30_4 = "BioPax_30_4.txt";
    public static final String OUTPUT_FILE_NAME_BIO_30_5 = "BioPax_30_5.txt";
    public static final String OUTPUT_FILE_NAME_BIO_30_6 = "BioPax_30_6.txt";
    public static final String OUTPUT_FILE_NAME_BIO_30_7 = "BioPax_30_7.txt";
    public static final String OUTPUT_FILE_NAME_BIO_30_8 = "BioPax_30_8.txt";
    public static final String OUTPUT_FILE_NAME_BIO_30_9 = "BioPax_30_9.txt";
    public static final String OUTPUT_FILE_NAME_BIO_30_10 = "BioPax_30_10.txt";
    
    public static final String OUTPUT_FILE_NAME_BIO_20_1 = "BioPax_20_1.txt";
    public static final String OUTPUT_FILE_NAME_BIO_20_2 = "BioPax_20_2.txt";
    public static final String OUTPUT_FILE_NAME_BIO_20_3 = "BioPax_20_3.txt";
    public static final String OUTPUT_FILE_NAME_BIO_20_4 = "BioPax_20_4.txt";
    public static final String OUTPUT_FILE_NAME_BIO_20_5 = "BioPax_20_5.txt";
    public static final String OUTPUT_FILE_NAME_BIO_20_6 = "BioPax_20_6.txt";
    public static final String OUTPUT_FILE_NAME_BIO_20_7 = "BioPax_20_7.txt";
    public static final String OUTPUT_FILE_NAME_BIO_20_8 = "BioPax_20_8.txt";
    public static final String OUTPUT_FILE_NAME_BIO_20_9 = "BioPax_20_9.txt";
    public static final String OUTPUT_FILE_NAME_BIO_20_10 = "BioPax_20_10.txt";    
      */
    public static final String OUTPUT_FILE_NAME_REDUCE = "BioPax_40_1.owl";    
    
    public static final int MAX_LENGTH = 3;
    public static final int FR_THR = 1;
    public static final double HEADCOV_THR = 0.01;
    public static final double IMPROVEDCONF_THR = 0.0;
    
    public static Map<IRI, ConceptIndividuals> allFrequentConceptNamesStratified;    
    public static Map<IRI, RoleIndividuals> allFrequentRoleNamesStratified;
    
    public static Map<IRI, ConceptIndividuals> allFrequentConceptNamesFull;    
    public static Map<IRI, RoleIndividuals> allFrequentRoleNamesFull;
    
    public static int numberOfIndividualsInSignature = 0;
    
    public static String cutString(String str)
    {
        String[] parts = str.split("#");		
        return parts[1]; 
    }
    
    //return number of percent of the number
    public static int getPercent(int number, double persent)
    {
        return (int)(Math.round((double) number * persent));
    }
    
    public static boolean addRule(Pattern<Atom> rule, OWLOntologyManager newOntologyManager, 
	 		OWLDataFactory newDataFactory, OWLOntology newOntology, Reasoner reasonerHermit)
	{
    	//Them vao de tranh truong hop 3 ham trung ten    	
    	boolean omit = false;
    	
    	if (rule.size() == 3)
    	{
    		Atom head = rule.get(0);
    		Atom body1 = rule.get(1);
    		Atom body2 = rule.get(2);
    		
    		String strNameHead = Global.cutString(((Atom) head).getIRI().toString());
    		String strNameBody1 = Global.cutString(((Atom) body1).getIRI().toString());
    		String strNameBody2 = Global.cutString(((Atom) body2).getIRI().toString());
    		
    		if ((strNameHead.equals(strNameBody1)) && (strNameHead.equals(strNameBody2)))
    			omit = true;    		
    	}
    	//End
    	
    	if (!omit)
    	{    	
	    	SWRLAtom head = null;
			Set<SWRLAtom> body = new LinkedHashSet<SWRLAtom>();
	
			for(int i = 0; i < rule.size(); i++)
			{
				if (i == 0)
				{
					if (rule.get(0) instanceof ConceptAtom)
					{
						OWLClass conceptAtom = newDataFactory.getOWLClass(((ConceptAtom) rule.get(i)).getIRI());
						SWRLVariable var = newDataFactory.getSWRLVariable(IRI.create(((ConceptAtom) rule.get(i)).getVariable()));
						head = newDataFactory.getSWRLClassAtom(conceptAtom, var);
					}
					else if (rule.get(0) instanceof RoleAtom)
					{
						OWLObjectProperty roleAtom = newDataFactory.getOWLObjectProperty(((RoleAtom) rule.get(i)).getIRI());
						SWRLVariable var1 = newDataFactory.getSWRLVariable(IRI.create(((RoleAtom) rule.get(i)).getDomainVariable()));
					    SWRLVariable var2 = newDataFactory.getSWRLVariable(IRI.create(((RoleAtom) rule.get(i)).getRangeVariable()));
					    head = newDataFactory.getSWRLObjectPropertyAtom(roleAtom, var1, var2);
					}
				}
				else
				{
					if (rule.get(i) instanceof ConceptAtom)
					{
						OWLClass conceptAtom = newDataFactory.getOWLClass(((ConceptAtom) rule.get(i)).getIRI());
						SWRLVariable var = newDataFactory.getSWRLVariable(IRI.create(((ConceptAtom) rule.get(i)).getVariable()));
						body.add(newDataFactory.getSWRLClassAtom(conceptAtom, var));
					}
					else if (rule.get(i) instanceof RoleAtom)
					{
						OWLObjectProperty roleAtom = newDataFactory.getOWLObjectProperty(((RoleAtom) rule.get(i)).getIRI());
						SWRLVariable var1 = newDataFactory.getSWRLVariable(IRI.create(((RoleAtom) rule.get(i)).getDomainVariable()));
						SWRLVariable var2 = newDataFactory.getSWRLVariable(IRI.create(((RoleAtom) rule.get(i)).getRangeVariable()));
						body.add(newDataFactory.getSWRLObjectPropertyAtom(roleAtom, var1, var2));
					}
				}
			}
	
			SWRLRule swrlRule = newDataFactory.getSWRLRule(body, Collections.singleton(head));
	
			newOntologyManager.applyChange(new AddAxiom(newOntology, swrlRule));
			
			reasonerHermit.flush();
			
			if (!reasonerHermit.isConsistent())
				return false;
			else
				return true;
    	}
    	else
    		return false;
    	
	}
}
