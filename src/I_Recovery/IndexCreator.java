/*
* @autor Rshad Zhran
* Note : This code was developed, starting from the main template, designed by my profesor of Information Recovery subject, Juan Huete Guadix.
*
* */


package I_Recovery;

/* Input/Outout related libraries */
    import java.io.File;
    import java.io.IOException;

/* Lucene related libraries */
    import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
    import org.apache.lucene.analysis.es.SpanishAnalyzer;
    import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
    import org.apache.lucene.analysis.Analyzer;
    import org.apache.lucene.analysis.standard.StandardAnalyzer;
    import org.apache.lucene.document.*;
    import org.apache.lucene.facet.FacetField;
    import org.apache.lucene.facet.FacetsConfig;
    import org.apache.lucene.index.*;
    import org.apache.lucene.store.*;
    import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyWriter;


/* Data Structures related libraries */
    import java.nio.file.Paths;
    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.Map;

/* IndexCreator class definition */
public class IndexCreator {

/* Class member variables */
    private IndexWriter iwriter;
    private FSDirectory index_dir; // Directory where the index will be stored
    private FSDirectory taxonomy_dir; // Directory where the Facets or Taxonomies index will be stored
    private ArrayList<File> files_to_index = new ArrayList<>();
    private Map<String, Analyzer> analyzerPerField; // analyzerPerField will store the different analyzers, one for each field.
    private Document doc; // The document to be indexed

    /*auxiliary variables */
    private String[] categories;
    private String[] arguments;
    private Integer argumentIndex;

    /**
     * Constructor
     * @param indexDir the folder where the index will be stored
     */
    public IndexCreator(String indexDir, String taxoDir ) throws IOException {
        index_dir= FSDirectory.open( Paths.get(indexDir) );
        taxonomy_dir = FSDirectory.open(Paths.get(taxoDir));

    }

    /**
     * addFiles is an auxiliary function used for adding the files to index in an ArrayList {@link IndexCreator}.files_to_index
     * @param file the file to be added the the list.
     *
     */
    private void addFiles(File file) {

        if (!file.exists()) {
            System.out.println(file + " does not exist.");
        }
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                addFiles(f);
            }
        } else {
            String filename = file.getName().toLowerCase();
            //===================================================
            // Only index text files
            //===================================================
            if (filename.endsWith(".htm") || filename.endsWith(".html") ||
                filename.endsWith(".xml") || filename.endsWith(".txt")) {
                files_to_index.add(file);
            } else {
                System.out.println("Skipped " + filename);
            }
        }
    }

    /**
     * Create The Index of our documents set
     * @param file_path the file to be indexed
     */
    public void createIndex(String file_path ) throws IOException {

        /* analyzerPerField will contains the different analyzers, one per field */
        analyzerPerField = new HashMap<String, Analyzer>();

        /*
         *  - As we're working with spanish texts -> it's better to work with SpanishAnalyzer.
         *  - SpanishAnalyzer.getDefaultStopSet() help us to remove empty words
         *  - We note that in the result of SpanishAnalyzer, automatically applies the stemming technique.
         */
        analyzerPerField.put("Content", new SpanishAnalyzer(SpanishAnalyzer.getDefaultStopSet()));
        analyzerPerField.put("Path", new WhitespaceAnalyzer());   // WhiteSpaceAnalyzer to not divide the path, so the path doesn't have white spaces
        analyzerPerField.put("Email", new WhitespaceAnalyzer());  // The same case as Path
        analyzerPerField.put("FileName", new StandardAnalyzer());
        analyzerPerField.put("Title", new SpanishAnalyzer(SpanishAnalyzer.getDefaultStopSet()));

        //The multi-analyzer set which contains all the previous analyzerPerField's elements
        PerFieldAnalyzerWrapper analyzer = new PerFieldAnalyzerWrapper(
            new SpanishAnalyzer(), analyzerPerField);

        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        config.setOpenMode(IndexWriterConfig.OpenMode.CREATE); //Lo abrimos para crear
        iwriter = new IndexWriter(index_dir, config);

        FacetsConfig facetConf = new FacetsConfig();
        DirectoryTaxonomyWriter taxoWriter = new DirectoryTaxonomyWriter(taxonomy_dir);
        facetConf.setHierarchical("pathCategory",true);
        facetConf.setMultiValued("lastMdoified",true);


        DocParser ParsedFileObject;
        DocFields FileFields;


        addFiles(new File(file_path));

        int originalNumDocs = iwriter.numDocs();

        for (File f : files_to_index) {
            ParsedFileObject = new DocParser();
            ParsedFileObject.CreateDocument(f);
            FileFields = ParsedFileObject.get_DocData();

            try {
                doc = new Document();


                // Adding the fields to the index document
                doc.add(new TextField("Content", (FileFields).get_DocText() , Field.Store.YES ));
                doc.add(new StringField("Path", f.getCanonicalPath().substring(46), Field.Store.YES)); //substring(46) -> to remove the local path
                doc.add(new TextField("FileName", f.getName(), Field.Store.YES));
                doc.add(new TextField("Title",(FileFields).get_DocTitle(), Field.Store.YES));

                for(String email : (FileFields).get_DocEmails()){
                    doc.add(new StringField("Email" , email, Field.Store.YES));
                }
            /*
                IntPoint length_field = new IntPoint("Length",(FileFields).get_DocLength());
                length_field.setIntValue(32);
                doc.add(length_field);
            */

                                                    /* The Facets Section */

                /* FACET : 1 ->  Divide the path of f "a file" into categories */
                categories = f.getCanonicalPath().substring(46).split("/");
                arguments = new String[categories.length]; //store the categories into arguments's array
                argumentIndex=0;
                for(String category : categories){ // storing the diiferent categories
                    arguments[argumentIndex] = category;
                    argumentIndex++;
                }
                doc.add(new FacetField("pathCategory",arguments)); //pathCategory is a (dynamic argument number) facer, so we pass an array if arguments

                /* FACET : 2 -> Last Modified "Date" of each file */
                doc.add(new FacetField("lastMdoified", String.valueOf((FileFields).get_DocDate())));

                /* FACET : 3 -> File's Length */
               // doc.add(new FacetField(  ))

                iwriter.addDocument(facetConf.build(taxoWriter,doc));



                System.out.println("Added: " + f);
            } catch (Exception e) {
                System.out.println("Could not add: " + f);
            }
        }

        int newNumDocs = iwriter.numDocs();
        System.out.println("");
        System.out.println("************************");
        System.out.println((newNumDocs - originalNumDocs) + " documents added.");
        System.out.println("************************");

        files_to_index.clear();
        iwriter.commit();
        iwriter.close();

    }


}
