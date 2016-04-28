package hyweb.jo.model;

import hyweb.jo.org.json.JSONObject;
import java.util.List;

/**
 *  回傳值
 * @author william
 */
public class JORetObject extends JSONObject {
    
    public Object getObject(){
        return opt("$data");
    }
    
    public JSONObject getJSONOject(){
        return optJSONObject("$data");
    }
    
    public List<JSONObject> getListObject(){
        return (List<JSONObject>) opt("$data");
    }
    
    public String status(){
        return optString("$status","N");
    }
    
    public String errorCode(){
        return optString("$errorCode");
    }
    
    public String errorMessage(){
         return optString("$errorMessage");
    }
    
}
