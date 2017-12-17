package I_Recovery;
import org.jsoup.Jsoup;
import org.apache.lucene.analysis.Analyzer;


public class MyClass {
    public MyClass(){
        Analyzer ex = new Analyzer() {
            @Override
            protected TokenStreamComponents createComponents(String s) {
                return null;
            }
        };
    }
}
