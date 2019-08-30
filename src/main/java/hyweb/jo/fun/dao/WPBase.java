/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.dao;

import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.lang.reflect.Method;
import java.util.List;
import org.mvel2.MVEL;

/**
 *
 * @author william
 */
public class WPBase {
    
    protected List<IJOField> getField(JOWPObject wp, String prefix) {
        if (wp.act().has(prefix)) {// 使用客制化
            String line = wp.ref_string(prefix);
            return wp.metadata().getFields(line);
        }
        return wp.metadata().getFields();
    }
    
    protected JSONObject getVarPool(JOWPObject wp) {    
        JSONObject jo = new JSONObject();
        jo.put("$", wp.optJSONObject("$"));
        jo.put("$$", wp.optJSONObject("$$"));
        jo.put("$proc", wp.proc());
        jo.put("$ff", wp.proc().optJSONObject("$ff"));
        jo.put("$f", JOFunctional.class);
        try {
            Method fn = JOFunctional.class.getMethod("fn", String.class, Object.class);
            jo.put("$fn", fn);
        } catch (Exception e) {
            JOLogger.info("Can't find JOFunctional.fn(Sring,Object) method");
        }
        jo.put("$this", jo);
        return jo;
    }
    
}
