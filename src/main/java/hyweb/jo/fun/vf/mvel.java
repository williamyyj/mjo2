/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.vf;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONObject;
import org.mvel2.MVEL;

/**
 *
 * @author william
 */
public class mvel implements IJOFunction<Boolean, JSONObject> {

    @Override
    public Boolean exec(JSONObject pool) throws Exception {
        JSONObject field = pool.optJSONObject("$fld");
        try {
            return MVEL.evalToBoolean(field.optString("valid"), pool);
        } catch (Exception e) {
            return false;
        }
    }

}
