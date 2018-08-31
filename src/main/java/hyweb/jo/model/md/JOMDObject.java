package hyweb.jo.model.md;

import hyweb.jo.JOProcObject;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOPath;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author william
 */
public class JOMDObject extends JSONObject {

    protected JOProcObject proc;
    protected String metaId;
    protected String actId;

    public JOMDObject(JOProcObject proc, String metaId, String actId) {
        this.proc = proc;
        this.metaId = metaId;
        this.actId = actId;
        execute(null);
    }

    public JOMDObject(JOProcObject proc, String metaId, String actId, JSONObject p) {
        this(proc, metaId, actId);
        p = (p == null) ? p : proc.params();
        execute(p);
    }

    protected void execute(JSONObject p) {
        try {
            proc.put("$", p); // 參數重置
            JSONObject actor = proc.wMeta().actor(metaId, actId);
            String funId = actor.optString("$funId");
            String dataId = actor.optString("$dataId", "$data");
            JOWPObject wp = new JOWPObject(proc, metaId, actId);
            proc.put(dataId, JOFunctional.exec(funId, wp));
        } catch (Exception ex) {
            JOPath.set(proc,"$status:code",1);
             JOPath.set(proc,"$status:message",ex.getMessage());
        }
    }

}
