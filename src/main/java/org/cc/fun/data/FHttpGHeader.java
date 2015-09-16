/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.data;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author William
 *      
 *   http://balusc.blogspot.tw/2009/02/fileservlet-supporting-resume-and.html   
 *  ETag 
 *  Accept-Ranges
 *  Last-Modified
 *  -------------------------------------------------------------------------------------
 *   If-None-Match
 *   If-Modified-Since
 *  Expires 
 * Whenever the If-None-Match or If-Modified-Since conditions are positive, 
 *  the server should send a 304 "Not Modified" response back without any content. 
 *  If this happens, then the client is allowed to use the content which is already available in the client side cache.
 * 
 *  Further on you can use the Expires response header to inform the client how long to keep the content 
 * in the client side cache without firing any request about that, even no HEAD requests. 
 *  
 */

public class FHttpGHeader implements IJOFunction<JSONObject, String> {

    @Override
    public JSONObject exec(String url_string) throws Exception {
        URL obj = new URL(url_string);
        HttpURLConnection conn = (HttpURLConnection) obj.openConnection();
        JSONObject ret = new JSONObject();
        Map<String, List<String>> map = conn.getHeaderFields();
        
        for (Map.Entry<String, List<String>> entry : map.entrySet()) {
            String key = entry.getKey() ; 
            if("null".equals(key)  || key==null){
                ret.put("$status", entry.getValue().get(0) );
            } else {
                List<String> item =  entry.getValue() ; 
                if (item!=null && item.size()>0){
                    Object value = (item.size()==1) ? item.get(0) : new JSONArray(item) ;
                    ret.put(key, value);
                }
            }
        }
        conn.disconnect();
        return ret ; 
    }

}
