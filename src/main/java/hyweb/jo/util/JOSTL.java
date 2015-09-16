/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;

/**
 * @author William
 */

public class JOSTL {
    public  static void each(JSONArray arr , IJOFunction<Object,JSONObject> fun) throws Exception {
        if(arr!=null){
            for(int i=0; i<arr.length(); i++){
                JSONObject row = arr.optJSONObject(i);
                fun.exec(row);
            }
        }
    }
}
