package hyweb.jo;

import hyweb.jo.org.json.JSONArray;
import hyweb.jo.util.JOTools;
import java.io.IOException;

/**
 *
 * @author william
 */
public class MYTest {
    public static void main(String[] args) throws IOException{
        JSONArray arr = JOTools.loadJA("['=','covert(date,updatetime)',date,dp]");
        System.out.println(arr);
    }
}
