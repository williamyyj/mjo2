package hyweb.jo;

import hyweb.jo.data.JOFileUtils;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 *
 * @author william
 */
public class MYTest {
    public static void gen_css() throws IOException, Exception{
        String fb = "D:\\will\\maintenance\\期刊\\css\\";
        String fp = fb+"大陸期刊篇目重建刊名索引.txt";
        List<String> items =  JOFileUtils.loadList( new File(fp), "MS950");
        for(String item:items){
            if(item.trim().length()>0){
                String tb = item.substring(0,4);
                String tp = item.substring(4,8);
                String data = item.substring(9);
                JOFileUtils.safe_dir(fb+tb);
                JOFileUtils.saveString(new File(fb+tb,tp),"UTF-8" ,data);
                System.out.println(tb+":"+tp+":"+data);
            }
        }
    }
    
    public static void main(String[] args) throws IOException{
       String text=  JOFileUtils.loadString(new File("D:\\will\\maintenance\\期刊\\sort_award_year_new.txt"), "UTF-8");
              String[] s = text.split("<ti>");
        String[] data = new String[s.length];
        
        for(String item:s){
            System.out.println(item);
        }
        
        for (int i = 0; i < s.length; i++) {
          if (i == 0) {
            data[0] = s[i].substring(0, s[i].indexOf("</yr>"));
          } else {
            data[i] = s[i].substring(0, s[i].indexOf("("));
          }
          System.out.println(i+"::::"+data[i]);
        }
    }
}
