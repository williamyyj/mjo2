/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import java.util.List;

/**
 * @author william
 */
public class fb_rows extends fb implements IJOFunction<List<JSONObject>, Object[]> {

    @Override
    public List<JSONObject> exec(Object[] p) throws Exception {
        JOProcObject proc = (JOProcObject) p[0];
        JSONObject cm = (JSONObject) p[1];
        JSONObject params = (p.length > 2) ? (JSONObject) p[2] : proc.params();
        return (List<JSONObject>) proc.db().action(mq(proc,cm,"rows",params));
    }

}
