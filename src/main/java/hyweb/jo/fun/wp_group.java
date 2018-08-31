/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;


/**
 *
 * @author william
 */
public class wp_group implements IJOFunction<Boolean, JOWPObject> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        JSONObject act = wp.act();
        JSONArray ja = act.optJSONArray("$group");
        if(ja!=null){
            for(int i=0;i<ja.length();i++){
                try{
                    proc_item(wp,ja.optJSONObject(i));
                } catch(Exception e){
                    
                }
            }
        }
        return true ;
    }

    private void proc_item(JOWPObject wp, JSONObject item) throws Exception {
        JSONArray arr = item.optJSONArray("actors");
        if(arr!=null){
            for(int i=0; i<arr.length();i++){
                JOWPObject wpActor = new JOWPObject(wp.proc(),item.optString("$md"),arr.optString(i));
                JSONObject act = wpActor.act();
                JOFunctional.exec(act.optString("$funId"), wpActor);
            }
        }
    }
    
}
