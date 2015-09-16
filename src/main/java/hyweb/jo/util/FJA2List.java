/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author William
 */
public class FJA2List implements IJOFunction<List<Object>, JSONArray> {

    public List<Object> exec(JSONArray p) throws Exception {
        return toList(p);
     }

    public static List<Object> toList(JSONArray ja) {
        ArrayList<Object> list = new ArrayList<Object>();
        if (ja != null) {
            for (int i = 0; i < ja.length(); i++) {
                Object o = ja.opt(i);
                if (o instanceof JSONObject) {
                    list.add(FJO2Map.toMap((JSONObject) o));
                } else if (o instanceof JSONArray) {
                    list.add(toList((JSONArray) o));
                } else {
                    list.add(o);
                }
            }
        }
        return list;
    }

}
