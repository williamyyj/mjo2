/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.util;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONArray;

/**
 *
 * @author william
 */
public class FJA2String implements IJOFunction<String, Object>{

    @Override
    public String exec(Object o) throws Exception {
       StringBuilder sb = new StringBuilder();
       if( o instanceof JSONArray){
           JSONArray ja = (JSONArray)o ; 
           for(int i=0; i<ja.length();i++){
               sb.append(ja.opt(i)).append(' ');
           }
       } else {
           sb.append(o);
       }
       return sb.toString();
    }
    
}
