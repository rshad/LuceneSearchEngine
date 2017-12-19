/*
 * @autor Rshad Zhran
 * Github Account : https://github.com/rshad
 * LinkedIn Account : https://www.linkedin.com/in/rshad-zhran-b65b5012a/
 */


package I_Recovery;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
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
    private ArrayList<String> Result_Doc; // The resulted docs of a query search
    /* Data Structures's Section End */

    /* Related variables to Lucene search Start */
    private QueryParser qParser; // Gonna be used in general non-field queries.
    private BooleanQuery bq;
    private BooleanQuery.Builder boolQuery;
    private Query MyQuery;
    private TopDocs docs; // The resulted docs of a query search
    private Integer ShownDocs = 10; // The number of doc.s to be view to the user
    /* Related variables to Lucene search End */

    /* Auxiliary variables */
    StringTokenizer QueryTokenizer;
    /**
      * Constructor
      * @param  Index_Dir_Path the index directory
      */
    public ContentSearch(String Index_Dir_Path) throws IOException {
        dir = FSDirectory.open(Paths.get(Index_Dir_Path)); // Created directory by the index directory path
        idx_Reader = DirectoryReader.open(dir);            // Open the index directory for lecture
        idx_Searcher = new IndexSearcher(idx_Reader);      // created the IndexSearcher object
        qParser = new QueryParser("Content",new SpanishAnalyzer()); // Content represents the default field ...
                                                                       // ... SpanishAnalyzer the default Analyzer
    }

    /**
     * GlobalSearchQuery throws a search query on each field of our index
     * @param QueryBody represents the query body or text.
     */
    public ArrayList<String> GlobalSearchQuery(String QueryBody) throws IOException {
    /* Filtering and Preparing variables and parameters Start */

        this.Result_Doc = new ArrayList<>(); //Defining Result_Doc. Result will store the results of the query search
        QueryBody = QueryBody.toLowerCase(); //Converting QueryBody to lowercase
        this.QueryTokenizer = new StringTokenizer(QueryBody); //Creating a StringTokenizer object using QueryBody as parameter
        this.boolQuery = new BooleanQuery.Builder(); //Defining boolQuery

    /* Filtering and Preparing variables and parameters End */

    /* Preparing the query Start */

        while (QueryTokenizer.hasMoreElements()) {
            this.boolQuery.add(new BooleanClause( new TermQuery( new Term( "Content", (String) QueryTokenizer.nextElement() ) ) ,
                                                  BooleanClause.Occur.SHOULD
                                                )
                              );
            this.boolQuery.add(new BooleanClause( new TermQuery( new Term( "Title", (String) QueryTokenizer.nextElement() ) )  ,
                                                  BooleanClause.Occur.SHOULD
                                                )
                              );
        }

        try {
            /* BooleanQuery.Builder.build() return a BooleanQuery with the parameter passed to the BooleanQuery.Builder */
            this.MyQuery = (this.qParser).parse(String.valueOf(boolQuery.build()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
    /* Preparing the query End */

    /* Search for the query and getting the results Start */
        docs = idx_Searcher.search(MyQuery,ShownDocs); // Search query and show ShownDocs documents.

        for(ScoreDoc sd : docs.scoreDocs){ //Getting the resulted documents
            org.apache.lucene.document.Document d = idx_Searcher.doc(sd.doc);
            Result_Doc.add(sd.score +" Document : "+ d.get("Title"));
            System.out.println(sd.score +" Document : "+ d.get("Title"));
        }
    /* Search for the query and getting the results End */

        return Result_Doc;
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

}
