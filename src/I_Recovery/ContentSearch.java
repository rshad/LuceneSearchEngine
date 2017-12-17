/*
* @autor Rshad Zhran
* Subject : Inormation Recovery, 2017-2018
* */


package I_Recovery;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.*;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.queryparser.flexible.standard.CommonQueryParserConfiguration;
import org.apache.lucene.queryparser.flexible.standard.CommonQueryParserConfiguration.*;
import org.apache.lucene.queryparser.flexible.core.config.QueryConfigHandler;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.similarities.Similarity;


import javax.swing.text.Document;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

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
public class ContentSearch{

        /* Class's members's definition */
    private Directory dir;              // store the index directory
    private IndexReader idx_Reader;     // used to read the index
    private IndexSearcher idx_Searcher; // Once we got the index opened for lecture, we can create our IndexSearcher object

    /* Data Structures's Section Start  */
    private ArrayList<Integer> ResultDocsID = new ArrayList<>();   // ResultDocsID will return the resulted documents's IDs
    private HashMap<String,String > Field_Query = new HashMap<>(); // In case of determining the term or field of for the ...
    /* Data Structures's Section End */                            // ... we store it in Field_Query.

    /* Related variables to Lucene search Start */
    private QueryParser qParser; // Gonna be used in general non-field queries.
    private Query q1;
    private TopDocs docs; // The resulted docs of a query search
    private Integer ShownDocs = 10; // The number of doc.s to be view to the user
    /* Related variables to Lucene search End */

    /* Auxiliary variables */
    String Query_Aux;
    int i;


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
     * GeneralSearchQuery throws a search query on each field of our index
     * @param Query represents the query body or text.
     */
    public void GeneralSearchQuery(String Query) throws /*ParseException,*/ IOException {

        // Normal Query Which gonna search in the the query in the default field, in this case -> Content In this simple case
        try {
            q1=qParser.parse(String.valueOf(new FuzzyQuery(new Term("Email",Query),2 )));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        docs = idx_Searcher.search(q1,ShownDocs); // Search query and show ShownDocs documents.

        for(ScoreDoc sd : docs.scoreDocs){ //Getting the resulted documents
            org.apache.lucene.document.Document d = idx_Searcher.doc(sd.doc);
            System.out.println(sd.score +" Document : "+ d.get("Title"));
        }
    }

    /**
      * SpecifiedSearchQuery receive a String, which represents the query text, to be searched.
      * @param QueryBody The query text
      */
    public void SpecifiedSearchQuery(String QueryBody, ArrayList<String> Fields) throws ParseException, IOException {
        QueryBody = QueryBody.toLowerCase();
        Query_Aux = Fields.get(0)+":"+QueryBody;

        for(int i=1; i<Fields.size(); i++){// Forming the query
            Query_Aux += " OR "+Fields.get(i)+":"+QueryBody;
        }

        // Normal Query Which gonna search in the the query in the default field, in this case -> Content In this simple case
        try {
            q1 = qParser.parse(Query_Aux); // Warning : Query can't be null or empty string
        } catch (ParseException e) {
            e.printStackTrace();
        }
        docs = idx_Searcher.search(q1,ShownDocs); // Search query and show ShownDocs documents.

        for(ScoreDoc sd : docs.scoreDocs){ //Getting the resulted documents
            org.apache.lucene.document.Document d = idx_Searcher.doc(sd.doc);
            System.out.println(sd.score +" Document : "+ d.get("Title"));
        }
    }

}
