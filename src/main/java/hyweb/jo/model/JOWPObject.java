package hyweb.jo.model;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import java.util.List;

public class JOWPObject extends JSONObject {

    private JOProcObject proc;
    private JOMetadata metadata;
    private JSONObject act;
    private List<IJOField> fields;
    private Object vf ; 

    public JOWPObject(JOProcObject proc, String metaId, String actId, JSONObject params, JSONObject ref) {
        this(proc, proc.metadata(metaId), actId, params, ref);
    }

    public JOWPObject(JOProcObject proc, String metaId) {
        this(proc, proc.metadata(metaId), null, null, null);
    }

    public JOWPObject(JOProcObject proc, JOMetadata metadata, String actId, JSONObject params, JSONObject ref) {
        super();
        this.proc = proc;
        this.metadata = metadata;
        init_act(actId);
        if (params == null) {
            params = proc.params();
        }
        if (ref == null) {
            ref = new JSONObject();
        }
        put("$", params);
        put("$$", ref);
    }

    private void init_act(String actId) {
        if (actId != null) {
            act = metadata.cfg().optJSONObject(actId);
            String eval = act.optString("eval");
            fields = metadata.getFields(eval);
        }
    }

    public JOProcObject proc() {
        return this.proc;
    }

    public JSONObject p() {
        return optJSONObject("$");
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

    public List<IJOField> fields() {
        return this.fields;
    }

    public JOWPObject wp(String actId, JSONObject p, JSONObject pp) {
        return new JOWPObject(proc, metadata, actId, p, pp);
    }

}
