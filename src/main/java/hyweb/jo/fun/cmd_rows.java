package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.JOProcObject;
import java.util.List;

/**
 * 客制化取資料
 *
 * @author william
 */
public class cmd_rows extends fbase implements IJOFunction<List<JSONObject>, Object[]> {

    @Override
    public List<JSONObject> exec(Object[] args) throws Exception {
        JOProcObject proc = (JOProcObject) args[0];
        String metaId = (String) args[1];
        String actId = (String) args[2];
        return exec(proc, metaId, actId);
    }

    public List<JSONObject> exec(JOProcObject proc, String metaId, String actId) throws Exception {
        JOMetadata metadata = proc.metadata(metaId);
        JSONObject act = metadata.cfg().optJSONObject(actId);
        int ps = actId.lastIndexOf("_");
        String rt = (ps > 0) ? actId.substring(ps + 1) : actId;
        JSONObject mq = mq(proc, act,rt);
        List<JSONObject> rows = (List<JSONObject>) proc.db().action(mq);
        if (act.has("eval") && rows != null) {
            List<IJOField> vFields = metadata.getFields(act.optString("eval"));
            for (JSONObject row : rows) {
                JOFunctional.exec2("meval", proc, vFields, row);
            }
        }
        return rows;
    }

    public void proc_mapping() {

    }

}
