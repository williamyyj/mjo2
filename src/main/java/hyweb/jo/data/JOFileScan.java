package hyweb.jo.data;

import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;
import java.io.File;

/**
 *
 * @author william
 */
public class JOFileScan {
    
    private JSONObject cfg ; 
    
    public JOFileScan(String base, String fid){
        cfg = JOTools.load(new File(base,fid+".json"));
        
    }
    public static void main(String[] args){

        
        
    }
}
