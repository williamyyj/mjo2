package hyweb.jo.fun.util;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public class FJONamesNormal implements IJOFunction<JSONObject, JSONObject> {

    @Override
    public JSONObject exec(JSONObject row) throws Exception {
        //  處理第一層
        if (row != null) {
            JSONObject ret = new JSONObject(row);
            JSONArray ja = row.names();
            for(int i=0 ; i< ja.length();i++){
                String name = ja.optString(i);
                String key  = name.toLowerCase();
                if(!key.equals(name)){
                    ret.put(key, row.opt(name));
                }
            }
            return ret;
        }
        return null;
    }
}
