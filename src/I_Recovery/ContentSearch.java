package I_Recovery;

import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.es.SpanishAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.FieldInvertState;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.LeafReaderContext;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.CollectionStatistics;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermStatistics;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.search.similarities.Similarity;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.ScoreDoc;


import javax.swing.text.Document;
import java.io.IOException;
import java.nio.file.Paths;

public class ContentSearch {
    private Directory dir; // store the index directory
    private IndexReader idx_Reader; // used to read the index
    private IndexSearcher idx_Searcher; // Once we got the index opened for lecture, we can create our IndexSearcher object

    public ContentSearch(String Index_Dir_Path) throws IOException, ParseException {
        dir = FSDirectory.open(Paths.get(Index_Dir_Path)); // Created directory by the index directory path
        idx_Reader = DirectoryReader.open(dir); // Open the index directory for lecture
        idx_Searcher = new IndexSearcher(idx_Reader); // created the IndexSearcher  object
        //Similarity example__;
        //example__ = idx_Searcher.getSimilarity(true);
        //System.out.println(example__.getClass());
        //org.apache.lucene.document.Document ex = idx_Searcher.doc(1);
        //System.out.println(ex.toString());

        // Setting Content as the default field to search into.
        QueryParser parser = new QueryParser("Content", new SpanishAnalyzer());
        Query q1,q2,q3;

        /*
            Normal Query Which gonna search in the the query in the default field, in this case -> Content
            In this simple case
        */
        q1 = parser.parse("Servicios Universidad Alojamiento");
        TopDocs docs = idx_Searcher.search(q1,10);
        for(ScoreDoc sd : docs.scoreDocs){
            org.apache.lucene.document.Document d = idx_Searcher.doc(sd.doc);
            System.out.println(sd.score +" Documento : "+ d.get("Title"));
        }

    }


}
