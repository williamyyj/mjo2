package hyweb.jo;

import hyweb.jo.org.json.JSONArray;
import hyweb.jo.util.JOTools;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author william
 */
public class MYTest {
    public static void main(String[] args) throws IOException{
        //JSONArray arr = JOTools.loadJA("['=','covert(date,updatetime)',date,dp]");
        //System.out.println(arr);
        String line ="1q2￥";
        Pattern p = Pattern.compile("^[0-9a-zA-Z_]{3,}$");
        Matcher m = p.matcher(line);
        System.out.println(m.find());
    }
}
