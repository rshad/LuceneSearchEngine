package I_Recovery;

import jdk.jfr.Category;
import org.apache.lucene.queryparser.classic.ParseException;
import org.jsoup.Jsoup;

import javax.swing.text.Document;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class Main {
    public static void main(String [ ] args) throws IOException, ParseException {
        String IndexDirPath = ( new File(".").getCanonicalPath() ) + "/" + "Index";
        String FacetsIndexDirPath = ( new File(".").getCanonicalPath() ) + "/" + "facets";

       // IndexCreator example = new IndexCreator( IndexDirPath,FacetsIndexDirPath);
       // example.createIndex("/home/rshad/MyFiles/RI/Documents");//( new File(".").getCanonicalPath() ) + "/" + "Documents");

        ContentSearch SearchEngine = new ContentSearch(IndexDirPath);
        SearchEngine.GeneralSearchQuery("defensor@ugr.es");


    }
}