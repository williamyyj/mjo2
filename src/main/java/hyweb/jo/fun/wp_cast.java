/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import java.util.Date;

/**
 *
 * @author william
 */
public class wp_cast implements IJOFunction<Boolean, JOWPObject> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        JSONObject row = wp.p();
        for (IJOField fld : wp.mdFields()) {
            String alias = fld.cfg().optString("alias");
            try {
                if (alias.length() > 0) {
                    if ("$now".equals(alias)) {
                          row.put(fld.id(), new Date());
                    } else {
                        Object fv = row.opt(alias);
                        Object v = fld.convert(fv);
                        if (v != null) {
                            row.put(fld.id(), v);
                        }
                    }
                }
            } catch (Exception e) {
                JOLogger.error("Can't cast : " + alias + " into " + fld.id(), e);
            }
        }
        return true;
    }

}
