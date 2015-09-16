package hyweb.jo.fun;

import hyweb.jo.JOProcConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DBCmd;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.TextUtils;

/**
 *
 * @author william
 */
public abstract class fbase {

    /**
     * 建立查詢模型
     *
     * @param proc
     * @param act
     * @param rt
     * @return
     * @throws Exception
     */
    protected JSONObject mq(JOProcObject proc, JSONObject act, String rt) throws Exception {
        String cmd = TextUtils.ja2string(act.optJSONArray("cmd"), "\n");
        JSONObject jq = new JSONObject(proc.params().m());
        jq.put(JOProcConst.cmd, cmd);
        jq.put(JOProcConst.act, rt);
        return DBCmd.parser_cmd(proc.db(), jq);
    }
    
    /**
     *
     * @param args
     * @return
     * @throws Exception
     */
    protected Object act(Object[] args) throws Exception {
        JOProcObject proc = (JOProcObject) args[0];
        String metaId = (String) args[1];
        String actId = (String) args[2];
        JOMetadata metadata = proc.metadata(metaId);
        JSONObject acfg = proc.optJSONObject(actId);
         int ps = actId.lastIndexOf("_");
        String act = (ps > 0) ? actId.substring(ps + 1) : actId;
        JSONObject mq = mq(proc, acfg, act);
        return proc.db().action(mq);
    }
    
}
