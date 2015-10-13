/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import static hyweb.jo.fun.MJOBase.db;
import static hyweb.jo.fun.MJOBase.mq;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;


/**
 *
 * @author william
 */
public class mjoc_update extends MJOBase implements IJOFunction<Boolean, JSONObject> {

    @Override
    public Boolean exec(JSONObject wp) throws Exception {
        JSONObject mq = mq(wp);
        JOLogger.debug(mq);
        try{
          db(wp).action(mq);
          return true ; 
        } catch(Exception e){
            e.printStackTrace();
            return false ; 
        }

    }

}
