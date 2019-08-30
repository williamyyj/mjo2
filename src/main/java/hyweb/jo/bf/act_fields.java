/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.bf;

import hyweb.jo.IJOBiFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOActorObject;
import hyweb.jo.model.JOFieldUtils;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author william
 */
public class act_fields implements IJOBiFunction<List<IJOField>, JOActorObject, Object> {

    @Override
    public List<IJOField> exec(JOActorObject act, Object o) throws Exception {
        List<IJOField> fields = new ArrayList<IJOField>();
        JOMetadata md = act.metadata();
        if (o instanceof String) {
            proc_form_string(act, fields, (String) o);
        } else if (o instanceof Collection) {//  JSONArray , List .... 
            proc_form_collection(act, fields, (Collection) o);
        }
        return fields;
    }

    private void proc_form_string(JOActorObject act, List<IJOField> fields, String text) {
        proc_form_collection(act, fields, Arrays.asList(text.split(",")));
    }

    private void proc_form_collection(JOActorObject act, List<IJOField> fields, Collection items) {
        for (Object o : items) {
            String item = (String) o;
            int ps = item.indexOf(':');
            String id = (ps > 0) ? item.substring(0, ps) : item;
            String args = (ps > 0) ? item.substring(ps + 1) : null;
            IJOField fld = act.metadata().getField(id, args);
            if ("dyn".equals(fld.dt())) {
                proc_field_dyn(act, fields, fld);
            } else {
                fields.add(fld);
            }
        }
    }

    private void proc_field_dyn(JOActorObject act, List<IJOField> fields, IJOField fld) {
        JSONArray jaHead = act.proc().params().optJSONArray(fld.cfg().optString("ehead"));
        JSONArray jaName = act.proc().params().optJSONArray(fld.cfg().optString("ename"));
        if (jaHead != null && jaName != null && jaHead.length() == jaName.length()) {
            for (int i = 0; i < jaHead.length(); i++) {
                JSONObject jo = new JSONObject();
                try {
                    jo.put("label", jaHead.optString(i));
                    jo.put("name", jaName.optString(i));
                    jo.put("id", jaName.optString(i));
                    jo.put("dt", fld.cfg().optString("edt"));
                    jo.put("eval", fld.cfg().optString("eeval"));
                    fields.add(JOFieldUtils.newInstance(jo));
                } catch (Exception ex) {
                    JOLogger.info("Can't create field :" + jo);
                }
            }
        }
    }
}
