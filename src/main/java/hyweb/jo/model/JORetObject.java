package hyweb.jo.model;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import java.util.List;

/**
 *  回傳值  停用改用JOWorkObject
 * @author william
 */
public class JORetObject extends JSONObject {
    
    public Object getObject(JOProcObject proc){
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
