package I_Recovery;

public class SearchResultObject {
    private String Doc_Title;
    private String Doc_Path;
    private String Doc_Highlight;
    private String Doc_Score;

    public SearchResultObject(){
        this.Doc_Title = "";
        this.Doc_Path = "";
        this.Doc_Highlight = "";
        this.Doc_Score = "";
    }
    public void set(String param_Title, String param_Path, String param_Highlight, String param_Score){
        this.Doc_Title = param_Title;
        this.Doc_Path = param_Path;
        this.Doc_Highlight = param_Highlight;
        this.Doc_Score = param_Score;
    }

    public String get_Doc_Title(){
        return this.Doc_Title;
    }
    public String get_Doc_Path(){
        return this.Doc_Path;
    }
    public String get_Doc_Highlight(){
        return this.Doc_Highlight;
    }
    public String get_Doc_Score(){
        return this.Doc_Score;
    }
}
