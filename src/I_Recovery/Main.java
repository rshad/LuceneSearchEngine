/*
 * @autor Rshad Zhran
 * Github Account : https://github.com/rshad
 * LinkedIn Account : https://www.linkedin.com/in/rshad-zhran-b65b5012a/
 */

package I_Recovery;

import org.apache.lucene.queryparser.classic.ParseException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Main {
    public static void main(String [ ] args) throws IOException, ParseException {
        String IndexDirPath = ( new File(".").getCanonicalPath() ) + "/" + "Index";
        String FacetsIndexDirPath = ( new File(".").getCanonicalPath() ) + "/" + "facets";

        //IndexCreator example = new IndexCreator( IndexDirPath,FacetsIndexDirPath);
        //example.createIndex("/home/rshad/MyFiles/RI/Documents");//( new File(".").getCanonicalPath() ) + "/" + "Documents");

        ContentSearch SearchEngine = new ContentSearch(IndexDirPath);
        ArrayList<String> result = SearchEngine.GlobalSearchQuery("Universidad de Granada");


    }
}