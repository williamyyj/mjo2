package hyweb.jo.fun;

import hyweb.jo.JOConst;
import hyweb.jo.JOProcConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DBCmd;
import hyweb.jo.db.DBCmdItem;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;


/**
 *
 * @author william
 */
public abstract class fb {

    protected JSONObject mq(JOProcObject proc, JSONObject cm, String act) throws Exception {
        return mq(proc,cm,act,proc.params());
    }
    
    protected JSONObject mq(JOWPObject wp) throws Exception {
        return mq(wp.proc(), wp.act(), wp.act().optString(JOConst.act_row),wp.p());
    }
    

    protected JSONObject mq(JOProcObject proc, JSONObject cm, String act, JSONObject params) throws Exception {
        if (params == null) {
            params = new JSONObject();
        }
        JSONObject jq = new JSONObject(params.m());
        String cmd = DBCmdItem.get_command(cm, JOConst.cmd);
        jq.put(JOProcConst.cmd, cmd);
        jq.put(JOProcConst.act, act);
        return DBCmd.parser_cmd(proc.db(), jq);
    }

    protected Object act(Object[] args) throws Exception {
        JOProcObject proc = (JOProcObject) args[0];
        String metaId = (String) args[1];
        String actId = (String) args[2];
        JSONObject params = (args.length > 3) ? (JSONObject) args[3] : proc.params();
        JOMetadata metadata = proc.metadata(metaId);
        JSONObject acfg = proc.optJSONObject(actId);
        int ps = actId.lastIndexOf("_");
        String act = (ps > 0) ? actId.substring(ps + 1) : actId;
        JSONObject mq = mq(proc, acfg, act, params);
        return proc.db().action(mq);
    }

}
