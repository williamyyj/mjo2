/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.JOProcConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DB;
import hyweb.jo.db.DBCmd;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.TextUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author william
 */
public class mjo_base {

    public JSONObject wp(Object[] args) {
        JSONObject p = new JSONObject();
        JOProcObject proc = (JOProcObject) args[0];
        String metaId = (String) args[1];
        String actId = (String) args[2];
        JSONObject row = (JSONObject) ((args.length > 3) ? args[3] : proc.params());
        JSONObject ref = (JSONObject) ((args.length > 4) ? args[4] : new JSONObject());
        JOMetadata metadata = proc.metadata(metaId);
        JSONObject act = metadata.cfg().optJSONObject(actId);
        p.put("$p", proc);
        p.put("$db", proc.db());
        p.put("$", row.m());
        p.put("$$", ref.m());
        p.put("$metadata", metadata);
        p.put("$act", act);
        return p;
    }

    public String act_cmd(JSONObject wp) {
        Object o = wp.optJSONObject("$act").opt("cmd");
        if (o instanceof String) {
            return (String) o;
        } else if (o instanceof JSONArray) {
            return TextUtils.ja2string((JSONArray) o, "\n");
        }
        return null;
    }

    public String act_act(JSONObject wp) {
        String act = wp.optJSONObject("$act").optString("act", null);
        if (act == null) {
            String classId = wp.optJSONObject("$act").optString("classId").toLowerCase();
            int ps = classId.lastIndexOf("_");
            act = (ps > 0) ? classId.substring(ps + 1) : classId;
        }
        return act;
    }

    public DB db(JSONObject wp) {
        return (DB) wp.opt("$db");
    }

    public JOProcObject proc(JSONObject wp) {
        return (JOProcObject) wp.opt("$p");
    }

    public JOMetadata metadata(JSONObject wp) {
        return (JOMetadata) wp.opt("$metadata");
    }

    public JSONObject mq(JSONObject wp) throws Exception {
        String cmd = act_cmd(wp);
        String act = act_act(wp);
        JSONObject jq = new JSONObject(wp.optJSONObject("$").m()); // -- 非破壞注入
        jq.put(JOProcConst.cmd, cmd);
        jq.put(JOProcConst.act, act);
        return DBCmd.parser_cmd(db(wp), jq);
    }

    

    public List<IJOField> eval_fields(JSONObject wp) {
        return metadata(wp).getFields(wp.optJSONObject("$act").optString("eval"));
    }

 
}
