package hyweb.jo.fun;

import hyweb.jo.IJOInit;
import hyweb.jo.JOProcConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DB;
import hyweb.jo.db.DBCmd;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.model.JOValidFields;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.TextUtils;
import java.util.List;

/**
 *
 * @author william
 */
public class MJOBase {

    public static JSONObject wp(Object... args) {
        JSONObject p = new JSONObject();
        JOProcObject proc = (JOProcObject) args[0];
        String metaId = (String) args[1];
        String actId = (args.length > 2) ? (String) args[2] : null;
        JSONObject row = (JSONObject) ((args.length > 3) ? args[3] : proc.params());
        JSONObject ref = (JSONObject) ((args.length > 4) ? args[4] : new JSONObject());
        JOMetadata metadata = proc.metadata(metaId);
        JSONObject act = new JSONObject(metadata.cfg().optJSONObject(actId));
        if (act.isEmpty()) {
            JOLogger.info("WP act  is null.");
        }
        p.put("$p", proc);
        p.put("$db", proc.db());
        p.put("$", row);
        p.put("$$", ref);
        p.put("$metadata", metadata);
        p.put("$act", act);
        return p;
    }

    public static String act_cmd(JSONObject wp) {
        Object o = wp.optJSONObject("$act").opt("cmd");
        if (o instanceof String) {
            return (String) o;
        } else if (o instanceof JSONArray) {
            return TextUtils.ja2string((JSONArray) o, "\n");
        }
        return null;
    }

    public static String act_act(JSONObject wp) {
        //   act = act(wp).optString("act",null) ; 
        String act = wp.optJSONObject("$act").optString("act", null);
        if (act == null) {
            String classId = wp.optJSONObject("$act").optString("classId").toLowerCase();
            int ps = classId.lastIndexOf("_");
            act = (ps > 0) ? classId.substring(ps + 1) : classId;
        }
        return act;
    }

    public static String act_classId(JSONObject wp) {
        return act(wp).optString("classId");
    }

    public static DB db(JSONObject wp) {
        return (DB) wp.opt("$db");
    }

    public static JOProcObject proc(JSONObject wp) {
        return (JOProcObject) wp.opt("$p");
    }

    public static JOMetadata metadata(JSONObject wp) {
        return (JOMetadata) wp.opt("$metadata");
    }

    public static JSONObject mq(JSONObject wp) throws Exception {
        String cmd = act_cmd(wp);
        String act = act_act(wp);
        JSONObject jq = new JSONObject(wp.optJSONObject("$").m()); // -- 非破壞注入
        jq.put(JOProcConst.cmd, cmd);
        jq.put(JOProcConst.act, act);
        return DBCmd.parser_cmd(db(wp), jq);
    }

    public static List<IJOField> eval_fields(JSONObject wp) {
        return metadata(wp).getFields(act(wp).optString("eval"));
    }

    public static JSONObject act(JSONObject wp) {
        return wp.optJSONObject("$act");
    }

    public static void caseInsensitive(JSONObject row) {
        if (row != null) {
            JSONArray name = row.names();

        }
    }

    public static Object act_vf(JSONObject wp) {
        String vf = wp.optJSONObject("$act").optString("vf");
        JOProcObject proc = MJOBase.proc(wp);
        return JOValidFields.vf(vf,proc.base());
    }

    public static List<IJOField> before(JSONObject wp) {
        String before = wp.optJSONObject("$act").optString("before");
        return metadata(wp).getFields(before);
    }

}
