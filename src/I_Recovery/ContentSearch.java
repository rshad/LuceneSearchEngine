/*
 * @autor Rshad Zhran
 * Github Account : https://github.com/rshad
 * LinkedIn Account : https://www.linkedin.com/in/rshad-zhran-b65b5012a/
 */


package I_Recovery;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.facet.*;
import org.apache.lucene.facet.taxonomy.directory.DirectoryTaxonomyReader;
import org.apache.lucene.facet.taxonomy.FastTaxonomyFacetCounts;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.similarities.Similarity;


import javax.swing.text.Document;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

/*
 * Search UI Design :
 *   Option 1 -> [ Query Box ] -> Without specifying the search's field
 *   --------------------------------------------------------
 *   Option 2 -> [Choose Field "Filter"] [Query Box] -> Have to specify the search's field
 *
 */

/**
 * {@link ContentSearch} class, is responsible about the search function in our system
 * */
public class ContentSearch {

    /* Class's members's definition */
    private Directory dir;              // store the index directory
    private IndexReader idx_Reader;     // used to read the index
    private IndexSearcher idx_Searcher; // Once we got the index opened for lecture, we can create our IndexSearcher object

    /* Data Structures's Section Start  */
    private ArrayList<SearchResultObject> Result_Doc; // The resulted docs of a query search
    /* Data Structures's Section End */

    /* Related variables to Lucene search Start */
    private QueryParser qParser; // Gonna be used in general non-field queries.
    private BooleanQuery bq;
    private BooleanQuery.Builder boolQuery;
    private Query MyQuery;
    private TopDocs docs; // The resulted docs of a query search
    private Integer ShownDocs = 10; // The number of doc.s to be view to the user
    private FSDirectory FacetsDirectory; // Gonna be used to classify the search results by facets
    /* Related variables to Lucene search End */

    /* Auxiliary variables */
    StringTokenizer QueryTokenizer;
    String nextElement;
    SearchResultObject Doc_Result_Obj;
    /**
      * Constructor
      * @param  Index_Dir_Path the index directory
      */
    public ContentSearch(String Index_Dir_Path, String taxonomyDirectory_Path) throws IOException {
        this.dir = FSDirectory.open(Paths.get(Index_Dir_Path)); // Created directory by the index directory path
        this.idx_Reader = DirectoryReader.open(dir);            // Open the index directory for lecture
        this.idx_Searcher = new IndexSearcher(idx_Reader);      // created the IndexSearcher object
        this.FacetsDirectory = FSDirectory.open(Paths.get(taxonomyDirectory_Path));
        qParser = new QueryParser("Content",new SpanishAnalyzer()); // Content represents the default field ...
                                                                       // ... SpanishAnalyzer the default Analyzer
    }

    /**
     * GenerateGeneralQuery is used to generate the query which will be used in the search process.
     * @param QueryBody the query's text
     * */
    private Query GenerateGeneralQuery(String QueryBody, String SelectedField){
        /* Filtering and Preparing variables and parameters Start */
        QueryBody = QueryBody.toLowerCase(); //Converting QueryBody to lowercase
        this.QueryTokenizer = new StringTokenizer(QueryBody); //Creating a StringTokenizer object using QueryBody as parameter
        this.boolQuery = new BooleanQuery.Builder(); //Defining boolQuery
        this.Result_Doc = new ArrayList<>();

        /* Filtering and Preparing variables and parameters End */

        /* Preparing the query Start */

        while (QueryTokenizer.hasMoreElements()) {
            /*
             * nextElement is an auxiliary string variable, used to store StringTokenizer.nextElement(). Because if we
             * call StringTokenizer.nextElement() more than 1 time, in each iteration, we are reading 3 different elements
             * and in this case can give us NoElementException or some thing like that.
             */
            this.nextElement = (String) QueryTokenizer.nextElement();

            if( ( SelectedField.equals("None") ) || ( !(SelectedField.equals("None")) && SelectedField.equals("Content") ) ) {
                this.boolQuery.add(new BooleanClause(new TermQuery(new Term("Content", nextElement)),
                                BooleanClause.Occur.SHOULD //SHOULD is equivalent to OR operator
                        )
                );
            }

            if( ( SelectedField.equals("None") ) || ( !(SelectedField.equals("None")) && SelectedField.equals("Title") ) ) {
                this.boolQuery.add(new BooleanClause(new TermQuery(new Term("Title", nextElement)),
                                BooleanClause.Occur.SHOULD
                        )
                );
            }


            /*
             * to search in Email field, i use FuzzyQuery to get better results; Because when a user search for an email,
             * sometimes He/She doesn't know the exact correct email, which requires providing him the possibility of
             * introducing Almost-Correct emails.
             */
            if( ( SelectedField.equals("None") ) || ( !(SelectedField.equals("None")) && SelectedField.equals("Email") ) ) {
                this.boolQuery.add(new BooleanClause(new FuzzyQuery(new Term("Email", nextElement), 2),
                                BooleanClause.Occur.SHOULD
                        )
                );
            }
        }

        try {
            /* BooleanQuery.Builder.build() return a BooleanQuery with the parameter passed to the BooleanQuery.Builder */
            this.MyQuery = (this.qParser).parse(String.valueOf(boolQuery.build()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        /* Preparing the query End */

        return MyQuery;
    }


    /**
     * GlobalSearchQuery throws a search query on each field of our index
     * @param QueryBody represents the query body or text.
     */
    public ArrayList<SearchResultObject> GlobalSearchQuery(String QueryBody,String SelectedField) throws IOException {
        this.Doc_Result_Obj = new SearchResultObject();
        this.GenerateGeneralQuery(QueryBody,SelectedField); // Generating the query to be searched
    /* Search for the query and getting the results Start */
        docs = idx_Searcher.search(MyQuery,ShownDocs); // Search query and show ShownDocs documents.

        for(ScoreDoc sd : docs.scoreDocs){ //Getting the resulted documents
            org.apache.lucene.document.Document d = idx_Searcher.doc(sd.doc);
            this.Doc_Result_Obj = new SearchResultObject();
            Doc_Result_Obj.set(d.get("Title"),d.get("Path"), "",Float.toString(sd.score));
            Result_Doc.add(Doc_Result_Obj);
        }

    /* Search for the query and getting the results End */

        return Result_Doc;
    }


    public void SelectedFieldSearch(String QueryBody, String SelectedField){
        this.Doc_Result_Obj = new SearchResultObject();
        QueryBody = QueryBody.toLowerCase(); //Converting QueryBody to lowercase





    }


    /**
      * FuzzyQueryFields will be used to search in non-tokenized fields, like Email in our case. Using FuzzyQuery
      * @param QueryBody The query text
      * @param SpecifiedField represents the chosen field to search in
      */
    public void FuzzyQueryFields(String QueryBody, String SpecifiedField) throws ParseException, IOException {
        QueryBody = QueryBody.toLowerCase();

        /*
         * Note : QueryBody can't be null or empty string. This will lead to launch an exception and then stop the exec-
         * ution process. In this case we will make the checkup, using JavaScript, so it'll be done in the Client side
         * and not the Server side; That means faster execution process.
         */
        try {
            MyQuery=qParser.parse(String.valueOf((new FuzzyQuery(new Term(SpecifiedField,QueryBody),2 ))));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        docs = idx_Searcher.search(MyQuery,ShownDocs); // Search query and show ShownDocs documents.

        for(ScoreDoc sd : docs.scoreDocs){ //Getting the resulted documents
            org.apache.lucene.document.Document d = idx_Searcher.doc(sd.doc);
            System.out.println(sd.score +" Document : "+ d.get("Title"));
        }
    }

    /**
     * FacetQuerySearch used to satisfy the searching process classifying the results by a facet or category
     * @param Facet the introduced facet to be employed in the search process
     * @param QueryBody the query's text
     */
    public void FacetQuerySearch(String Facet, String QueryBody,String SelectedField) throws IOException, ParseException, NullPointerException {
        DirectoryTaxonomyReader FacetDirectroryReader = new DirectoryTaxonomyReader(this.FacetsDirectory);
        FacetsConfig fconfig = new FacetsConfig();
        FacetsCollector facetsCollector = new FacetsCollector();
        this.GenerateGeneralQuery(QueryBody,SelectedField);
        facetsCollector.search(idx_Searcher, this.MyQuery, 10, facetsCollector);
        Facets facets = new FastTaxonomyFacetCounts(FacetDirectroryReader, fconfig, facetsCollector);
        FacetResult facetResult = facets.getTopChildren(10, "pathCategory","www.ugr.es","estudiantes");
        System.out.println(facetResult.path.length);
        try {
            for (LabelAndValue labelAndValue : facetResult.labelValues) {
                System.out.println(labelAndValue.label + ":" +
                        labelAndValue.value);
            }
        }catch (NullPointerException e){
            System.out.println("No Documents Found");
        }

        try {
            for (String requested : facetResult.path) {
                System.out.println(requested);
            }
        }catch (NullPointerException e){
            System.out.println("No Documents Found");
        }

    }


}
