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
import hyweb.jo.util.JOPath;
import hyweb.jo.util.XMLUtils;
import java.util.List;

/**
 *
 * @author william
 */
public class wp_xml implements IJOFunction<Boolean, JOWPObject> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        JSONObject act = wp.act();
        List<JSONObject> rows = (List<JSONObject>) wp.opt("$data");
        JSONObject xsd = act.optJSONObject("$xsd");        
        String jp = xsd.optString("$jp");
        JSONObject data = new JSONObject();
        JOPath.set(data, jp, new JSONArray(rows.toArray()));
        String ret = XMLUtils.toString(xsd, data);
        wp.put("$data", ret);
        wp.put("$type", "text:xml");
        return true;
    }

}
