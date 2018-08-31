/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.vf;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;

/**
 * @author william
 * 身份證號檢 
 */
public class twid implements IJOFunction<Boolean, JSONObject> {

    @Override
    public Boolean exec(JSONObject pool) throws Exception {
        return (Boolean) JOFunctional.exec("valid.twid",pool.optString("$fv"));
    }

}
