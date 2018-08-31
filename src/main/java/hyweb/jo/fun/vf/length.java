/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.vf;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public class length  implements IJOFunction<Boolean, JSONObject>{

    @Override
    public Boolean exec(JSONObject pool) throws Exception {
        String fv = pool.optString("$fv");
        JSONObject fld = pool.optJSONObject("$fld");
        if(fld!=null){
            int size = fld.optInt("len",fld.optInt("size"));
            return fv.length()<=size;
        }
        return false ;        
    }
    
}
