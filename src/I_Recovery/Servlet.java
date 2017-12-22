/*
 * @autor Rshad Zhran
 * Github Account : https://github.com/rshad
 * LinkedIn Account : https://www.linkedin.com/in/rshad-zhran-b65b5012a/
 */

package I_Recovery;

import javafx.util.Pair;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.RequestDispatcher;
import java.io.File;
import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;


@WebServlet(name = "Servlet")
public class Servlet extends HttpServlet {
    /* Class Members */

    private String MainIndexPath, FacetIndexPath, DocSetPath; // The indexes's paths and the document set's directory path
    private IndexCreator myIndexCreator; // myIndexCreator gonna be used in case we want to create the index if it doesn't exist
    private ContentSearch mySearchCreator; // mySearchCreator gonna be used as the core for our search process
    private HashMap< String , Pair<String,String> > DocTitle_DocPath;// In DocTitle_DocPath we store the search result's docs. ..
    private ArrayList<SearchResultObject> Document_Resulted = new ArrayList<>();                                                                 // ... in pair (DocTitle , Pair<DocPath,DocHighlighting) form
    private String QueryText = ""; // QueryText gonna store the introduced query
    private String SelectedField="";

    private RequestDispatcher rd; //rd will direct the petition to a determined page

    public Servlet() throws IOException {
        /* Assigning the values of indexes and documents set variables */
        this.MainIndexPath = ( new File(".").getCanonicalPath() ) + "/" + "Index";
        this.FacetIndexPath = ( new File(".").getCanonicalPath() ) + "/" + "facets";
        this.DocSetPath = "/home/rshad/IdeaProjects/_RI_/Documents";

        /*
         * 1) Assigning the indexes paths to our IndexCreator object class member
         * 2) Creating the index on our documents set
         */
        this.myIndexCreator = new IndexCreator(MainIndexPath,FacetIndexPath);
        this.myIndexCreator.createIndex(DocSetPath);

        /* Indicating the main index's path to our ContentSearch object class member */
        this.mySearchCreator = new ContentSearch(MainIndexPath,"");

        /* Defining the not null value for DocTitle_DocPath */
        this.DocTitle_DocPath = new HashMap<>();
    }

    /**
     * Constructor
     * @param indexPath represents MainIndexPath
     * @param facetIndexPath represents FacetIndexPath
     * @param Documents_Set_Path represents DocSetPath
     * */


    /**
     * processRequest represents the main function which receive/send data from/to the jsp files.
     * @param request represents  output of doGet(...) function
     * @param response represents the input of doPost(...) function
     * */
    public void processRequest(HttpServletRequest request, HttpServletResponse response) throws IOException{

        try (PrintWriter output = response.getWriter()) {
            response.setContentType("text/html;charset=UTF-8"); /* Detemine the charset and file text type */

            QueryText = request.getParameter("searchBox");
            SelectedField = request.getParameter("FieldToSelect");

            System.out.println(SelectedField);

            if (SelectedField.equals("None") ) {
                Document_Resulted = this.mySearchCreator.GlobalSearchQuery(QueryText,"None");
            }
            else{
                Document_Resulted = this.mySearchCreator.GlobalSearchQuery(QueryText,SelectedField);
            }

            /* Obtaining the introduced query's text, in the search box of index.jsp */


            request.setAttribute("QueryText",QueryText);
            request.setAttribute("Document_Resulted",Document_Resulted);

            /*
             * 1) Assinging SearchResult.jsp as the redirected-to page
             * 2) Send the petition
             */
            rd = getServletContext().getRequestDispatcher("/SearchResult.jsp");
            rd.forward(request, response);

        } catch (ServletException e) {
            e.printStackTrace();
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        processRequest(request, response);
    }
}
