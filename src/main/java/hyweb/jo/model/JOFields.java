package hyweb.jo.model;

import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOUtils;
import java.util.ArrayList;


/**
 * @author william
 */
public class JOFields extends ArrayList<IJOField> {
    
    //public void add(IJOField ref)
    
    public void addFormString(JOMetadata md, String line){
        
    }
    
    public void add(String line, IJOField ref) throws Exception {
         JSONObject cfg = new JSONObject(ref.cfg());
         super.add(JOFieldUtils.newInstance(cfg));
    }

    public void add(JSONObject child, IJOField ref) throws Exception {
       JSONObject cfg = JOUtils.mix(child, ref.cfg());
       super.add(JOFieldUtils.newInstance(cfg));
    }

}
