/*
 * @author Rshad Zhran
 */

package I_Recovery;

import java.util.*;

/* Jsoup Libraries */
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
/* End Jsoup Libraries */

import java.io.File; // Input Output functionalities
import java.io.IOException;
import java.util.regex.Matcher; // Matcher to detect patterns en a text
import java.util.regex.Pattern; // Pattern

public class DocParser {
                                                /* JSOUP related variables */

    /** Directory_Path stores the main directory path as String */
    private String Directory_Path;

    /**  My_Files contains the files in which the html documents will be read. */
    private ArrayList<String> My_Files;

    /** Doc will be used to read the file and then apply JSOUP functions */
    private Document Doc;

    /** All extractable data of a document which can help us in indexing process */
    private DocFields Document_Data = new DocFields();

                                                /* END JSOUP related variables */

                                                /* Data Structures */
    /** For each document, stores the related data represented by the {@link DocFields} members */
    private HashMap<Document,DocFields> Document_Fields;

    /** for each category X stores the documents which belong to X  */
    private HashMap< String, Set<String> > Category_Documents;

                                                /* END Data Structures */

                                                /* Auxiliary Elements */
    private Set<String> Emails_Set;
                                                /* End Auxiliary Elements */

    /**
     * Constructor
     * @throws IOException
     * @param Directory_Path_Param our directory path as String
     */

    public DocParser(String Directory_Path_Param) throws IOException {
                                        /* Initialize our class members */
        this.Directory_Path = ( new File(".").getCanonicalPath() ) + "/" + Directory_Path_Param;
        this.My_Files = new ArrayList<>();
        this.Category_Documents = new HashMap<>();
        this.Document_Fields = new HashMap<>();
    }

    public DocParser(){
        this.My_Files = new ArrayList<>();
        this.Category_Documents = new HashMap<>();
        this.Document_Fields = new HashMap<>();
    }

    /**
     * CategorizePath used for splitting a path into different categories
     * @param path the path to be categorized
     */
    private void CategorizePath(String path){
        //System.out.println(path);
        String[] categories = path.split("/"); //split path by "/" and store the results into categories
        Set<String> Aux_Docs;

        for(Integer i=0 ; i<categories.length-1 ; i++ ){ // for each result category , (length-1) -> Discount the file name
         /* Update or Initialize the belonging documents set of categories[i] */
            if( ( Category_Documents.get( categories[i] ) ) == null ){
                Aux_Docs = new HashSet<>();
            }
            else{
                Aux_Docs = ( Category_Documents.get( categories[i] ) );
            }
            Aux_Docs.add(categories[ (categories.length-1) ]);
         /* End Update the belonging documents set of categories[i] */
            Category_Documents.put(categories[i], Aux_Docs ); //for the category[i], add Doc to its documents "Doc belongs to categories[i]"
        }

    }

    /**
     * @return the directory path after being completed in the constructor {@link DocParser}
     */
    public String get_DirectoryPath(){
        return this.Directory_Path;
    }

    /**
     * list_files stores all the files's path of a directory, however it contains sub_directoies, will be listed too
     * @param directoryName the directory name to be listed
     */
    public void list_files(String directoryName) throws IOException {

        File directory = new File(directoryName);
        String File_Extension = "";
        String file_name = "";


        // get the list of all files from a directory
        File[] fList = directory.listFiles();

        for (File file : fList){ //iterate over the elements of directory, elements like file or directory
            if (file.isFile()) { //it's file
                file_name = file.getName();
                File_Extension =  file_name.substring( file_name.lastIndexOf(".") + 1, file_name.length() ); //save the file name without the complete path

                if( File_Extension.equals("html") ) { //only stores the html files
                    this.CategorizePath(file.getCanonicalPath().substring(46)); //categorize file's path corresponding to file_name
                    //System.out.println(file.getCanonicalPath());
                    My_Files.add( file_name ); //add file value as a String to My_Files_Path
                }
            }
            else if (file.isDirectory()) { //it's a directory -> recursive call
                list_files(file.getAbsolutePath());
            }

        }
    }

    /**
     * ExtractEmails extract all content emails in Doc_param and store it in Document_Emails data structure
     * @param Doc_Param the document to extract from
     */
    private void ExtractEmails(Document Doc_Param){
        Pattern Emails_Regular_Expression;
        Matcher Emails_Matcher;

        Emails_Regular_Expression = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]"); // Regular expression which represents an email
        Emails_Matcher = Emails_Regular_Expression.matcher(Doc_Param.text()); // Detect the emails in the  document text
        Emails_Set = new HashSet<String>(); // Initialize the detected emails set

        while (Emails_Matcher.find()) { // While exist another email in the matcher
            Emails_Set.add(Emails_Matcher.group()); // Adding the detected emails to the emails set, emails
        }
    }

    /**
     * CreateDocument create a Jsoup Document, using a File as input
     * @param File_Param file to be converted into a Document
     * @throws IOException
     */
    public void CreateDocument(File File_Param ) throws IOException {
        this.Doc = Jsoup.parse(File_Param, "UTF-8", ""); // Creating the document Doc

        this.ExtractEmails(Doc); // Get the content emails in Docs

        /* fill our document data in Document_Data */
        this.Document_Data.set_DocEmails( Emails_Set );
        this.Document_Data.set_DocText( ( Doc.select("main") ).text() );
        this.Document_Data.set_DocLength( (Doc.select("main") ).text().length() ) ;
        this.Document_Data.set_DocTitle( Doc.title() );
      //  this.Document_Data.set_DocName( Doc_Name_String );
        this.Document_Data.set_DocModifiedDate((File_Param.lastModified()));
        System.out.println( (File_Param.lastModified()) );

        System.out.println(new Date( (File_Param.lastModified()) ));
        this.Document_Fields.put(Doc,Document_Data);

    }


    /**
     * @return Document_Fields, the complete related data for each document
     */
    public DocFields get_DocData(){
        return this.Document_Data;
    }

    /**
     * @return My_Files the files names
     */
    public ArrayList<String> get_MyFilesNames(){
        return this.My_Files;
    }

    /**
     * @return Category_Documents
     */
    public HashMap<String,Set<String>> get_CategoryDocs(){
        return this.Category_Documents;
    }






}



