/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.List;

/**
 *
 * @author william
 */
public class cmd_row extends fbase implements IJOFunction<JSONObject, Object[]> {

    @Override
    public JSONObject exec(Object[] args) throws Exception {
        JOProcObject proc = (JOProcObject) args[0];
        String metaId = (String) args[1];
        String actId = (String) args[2];
        return exec(proc, metaId, actId);
    }

    public JSONObject exec(JOProcObject proc, String metaId, String actId) throws Exception {
        JOMetadata metadata = proc.metadata(metaId);
        JSONObject act = metadata.cfg().optJSONObject(actId);
        int ps = actId.lastIndexOf("_");
        String rt = (ps > 0) ? actId.substring(ps + 1) : actId;
        JSONObject mq = mq(proc, act, rt);
        System.out.println(mq);
        JSONObject row =(JSONObject) proc.db().action(mq);
        if (act.has("eval") && row != null) {
            List<IJOField> vFields = metadata.getFields(act.optString("eval"));
            JOFunctional.exec2("meval", proc, vFields, row);
        }
        return row;
    }
}
