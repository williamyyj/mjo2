package hyweb.jo.model;

import hyweb.jo.JOConst;
import hyweb.jo.JOProcConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DBCmd;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.util.List;

public class JOWPObject extends JSONObject {

    private JOProcObject proc;
    private JOMetadata metadata;
    private JSONObject act;
    private String scope;
    private Object vf;

    public JOWPObject(JOProcObject proc, JSONObject params, JSONObject ref) {
        super();
        this.proc = proc;
        params = (params == null) ? proc.params() : params;
        ref = (ref == null) ? new JSONObject() : ref;
        put("$", params);
        put("$$", ref);
    }

    public JOWPObject(JOProcObject proc, String metaId, String actId, JSONObject params, JSONObject ref) {
        this(proc, proc.metadata(metaId), actId, params, ref);
    }

    public JOWPObject(JOProcObject proc, String metaId) {
        this(proc, proc.metadata(metaId), null, null, null);
    }

    public JOWPObject(JOProcObject proc, String metaId, String actId) {
        this(proc, proc.metadata(metaId), actId, null, null);
    }

    public JOWPObject(JOProcObject proc, JOMetadata metadata, String actId, JSONObject params, JSONObject ref) {
        super();
        this.proc = proc;
        this.metadata = metadata;
        init_act(actId);
        params = (params == null) ? proc.params() : params;
        ref = (ref == null) ? new JSONObject() : ref;
        this.put("$", params);
        this.put("$$", ref);
        this.scope = JOConst.eval;
    }

    private void init_act(String actId) {
        if (actId != null) {
            act = metadata.cfg().optJSONObject(actId);
        }
    }

    public JOProcObject proc() {
        return this.proc;
    }

    public JSONObject p() {
        return optJSONObject("$");
    }

    public JSONObject reset(JSONObject p) {
        return put("$", p);
    }

    public JSONObject pp() {
        return optJSONObject("$$");
    }

    public JOMetadata metadata() {
        return this.metadata;
    }

    public JSONObject act() {
        return act;
    }

    @Deprecated
    public JSONObject act(String actId) {
        /*
            新增 ref_string  可整合 meta內全部 act 
         */
        init_act(actId);
        return act;
    }

    public void reset(String actId, JSONObject p, JSONObject pp) {
        init_act(actId);
        if (p != null) {
            this.put("$", p);
        }
        if (pp != null) {
            this.put("$", pp);
        }
    }

    @Deprecated
    public void reset(String actId) {
        /*
            新增 ref_string  可整合 meta內全部 act 
         */
        init_act(actId);
    }

    public List<IJOField> fields() {
        //  會隋設定scope 改變
        return metadata().getFields(ref_string(scope));
    }

    @Deprecated
    public List<IJOField> fields(String scope) {
        return metadata().getFields(ref_string(scope));
    }

    @Deprecated
    public JOWPObject wp(String actId, JSONObject p, JSONObject pp) {
        return new JOWPObject(proc, metadata, actId, p, pp);
    }

    public JSONObject mq() throws Exception {
        JSONObject jq = new JSONObject(p().m());
        String cmd = ref_string(JOConst.cmd);
        jq.put(JOProcConst.cmd, cmd);
        jq.put(JOProcConst.act, act().optString(JOConst.act));
        return DBCmd.parser_cmd(proc.db(), jq);
    }

    public Object ref(String id) {
        Object o = act.opt(id);
        if (o != null) {
            return o;
        }
        String rid = id.replaceAll("\\$", "\\$ref_");
        String ractId = act.optString(rid);
        JSONObject ract = metadata.cfg().optJSONObject(ractId);
        return (ract != null) ? ract.opt(id) : null;
    }

    public String ref_string(String id) {
        Object o = ref(id);
        if (o instanceof String) {
            return (String) o;
        } else if (o instanceof JSONArray) {
            JSONArray arr = (JSONArray) o;
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < arr.length(); i++) {
                sb.append(arr.opt(i)).append(System.getProperty("line.separator"));
            }
            return sb.toString();
        }
        return null;
    }

}
