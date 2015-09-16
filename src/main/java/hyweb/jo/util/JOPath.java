package hyweb.jo.util;

import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;

/**
 * @author William
 */

public class JOPath {
    
     private static Object opt(Object m,String k){            
        if ( m instanceof JSONObject){
            return ((JSONObject)m).opt(k);
        } else if (m instanceof JSONArray){
            return  ((JSONArray)m).opt(Integer.parseInt(k.trim()));
        }
        return null  ; 
    };
    
    public static Object path(JSONObject jo, String jopath){
        String[] path = jopath.split(":");
        return path(jo,path);
    }

    private static Object path(JSONObject jo, String[] path) {
        Object p = jo;
        for (String key : path) {
            p = opt(p, key);
            if(p==null) {
                break ; 
            }
        }
        return p;
    }
    
}
