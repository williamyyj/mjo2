/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.List;

/**
 * @author william
 */

public class wp_rows  implements IJOFunction<List<JSONObject>, JOWPObject> {

    @Override
    public List<JSONObject> exec(JOWPObject wp) throws Exception {
        JOFunctional.exec("wp_eval", wp);
        List<JSONObject> rows =  (List<JSONObject>) wp.proc().db().action(wp.mq());
        wp.put("$act","rows");
        wp.put("$data",rows);
        return rows ;
    }
    
}