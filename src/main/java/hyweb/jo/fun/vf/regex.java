/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.vf;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONObject;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author william
 */
public class regex implements IJOFunction<Boolean, JSONObject> {

    @Override
    public Boolean exec(JSONObject pool) throws Exception {
        JSONObject fld = pool.optJSONObject("$fld");
        String regex = fld.optString("pattern");
        String fv = pool.optString("$fv");
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(fv);
        System.out.println(fv);
        return m.find();
    }

}
