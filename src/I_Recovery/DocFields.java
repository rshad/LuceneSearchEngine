/*
 * @autor Rshad Zhran
 */


package I_Recovery;

//libraries
import java.util.Date;
import java.util.Set;


/**
 *  A FieldsClass will help us to ease the process of accessing and
 *  storing the related data to a document and make more efficient.
 */
public class DocFields {
                            /* Class Members */
    /** The doc name in String */
    private String DocName = "";

    /** An html document ( <main> tag )'s associated text */
    private String Document_Text = "";

    /** A document title */
    private String Document_Title = "";

    /** The last modification date of the document  */
    private long Document_Reference_Date;

    /** A document length */
    private Integer Document_Length = 0;

    /** for each document, stores all content emails  */
    private Set<String> Document_Emails;

                            /* End Class Members */


                            /* Class Functions */
    /**
     * Constructor
     */
    public DocFields(){

    }
                            /* SET functions */
    /**
     * @param Document_Name, the value of Document_Name to be set
     */
    public void set_DocName(String Document_Name){
        this.DocName = Document_Name;
    }

    /**
     * @param DocumentText, the value of Document_Text to be set
     */
    public void set_DocText(String DocumentText){
        this.Document_Text = DocumentText;
    }

    /**
     * @param DocumentTitle, the value of Document_Title to be set
     */
    public void set_DocTitle(String DocumentTitle){
        this.Document_Title = DocumentTitle;
    }

    /**
     * @param Doc_Modify_Date, the value of Document_Reference_Date to be set
     */
    public void set_DocDate(long Doc_Modify_Date){
        Document_Reference_Date = Doc_Modify_Date;
    }

    /**
     * @param TextLength, the Document_Length to be set
     */
    public void set_DocLength(Integer TextLength){
        this.Document_Length = TextLength;
    }

    /**
     * @param Emails, the value of Document_Emails to be set
     */
    public void set_DocEmails( Set<String> Emails ){
        Document_Emails = Emails;
    }

    /**
     * @param ModificationDate the last modification date of the document
     */
    public void set_DocModifiedDate(long ModificationDate){
        this.Document_Reference_Date = ModificationDate;
    }


                            /* End SET functions */


                            /* GET functions */
    /**
     * @return Document_Name, The document name
     */
    public String get_DocName(){
        return this.DocName;
    }

    /**
     * @return Document_Text, The document's body text
     */
    public String get_DocText(){
        return this.Document_Text;
    }

    /**
     * @return Document_Title, the document tile
     */
    public String get_DocTitle(){
        return this.Document_Title;
    }

    /**
     * @return Document_Reference_Date, The last modification date of the document
     */
    public long get_DocDate(){
        return this.Document_Reference_Date;
    }

    /**
     * @return Document_Length, the document length
     */
    public Integer get_DocLength(){
        return this.Document_Length;
    }

    /**
     * @return Document_Emails, content emails in a document
     */
    public Set<String> get_DocEmails(){
        return this.Document_Emails;
    }

    /**
     * @return Document_Reference_Date, the last modified date of the document
     */
    public long getModificationDate(){
        return this.Document_Reference_Date;
    }

                            /* End GET functions */


}

