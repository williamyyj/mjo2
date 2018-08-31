/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.vf;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.DateUtil;
import java.util.Date;

/**
 *
 * @author william
 */
public class date implements IJOFunction<Boolean, JSONObject> {

    @Override
    public Boolean exec(JSONObject pool) throws Exception {
        String fv = pool.optString("$fv");
        Date d = DateUtil.to_date(fv);
        return d != null ;
    }

}
