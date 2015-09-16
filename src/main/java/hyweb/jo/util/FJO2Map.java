/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author William
 */
public class FJO2Map implements IJOFunction<Map<String, Object>, JSONObject> {

    public Map<String, Object> exec(JSONObject p) throws Exception {
           return toMap(p);
     }

    public static Map<String, Object> toMap(JSONObject jo) {
        HashMap<String, Object> m = new HashMap<String, Object>();
        if (jo != null) {
            JSONArray names = jo.names();
            if (names != null) {
                for (int i = 0; i < names.length(); i++) {
                    String name = names.optString(i);
                    Object o = jo.opt(name);
                    if (o instanceof JSONObject) {
                        m.put(name, toMap((JSONObject) o));
                    } else if (o instanceof JSONArray) {
                        m.put(name, FJA2List.toList((JSONArray) o));
                    } else {
                        m.put(name, o);
                    }
                }
            }
        }
        return m;
    }

}
