package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DBCmd;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.List;

/**
 * @author william mjo 共用 update
 */
public class mjo_update extends MJOBase implements IJOFunction<Boolean, JSONObject> {

    @Override
    public Boolean exec(JSONObject wp) throws Exception {
        JOProcObject proc = MJOBase.proc(wp);

        List<IJOField> dbField = MJOBase.metadata(wp).getFieldsByScope("db");
        JSONObject jq = new JSONObject(proc.params().m());
        jq.put(JOProcConst.act, "edit");
        jq.put(JOProcConst.cmd, JOFunctional.exec("model.FSQLUpdate", dbField));
        JSONObject mq = DBCmd.parser_cmd(proc.db(), jq);
        try {
            proc.db().action(mq);
            return true ; 
        } catch (Exception e) {
            JOLogger.error(mq);
            return false ;
        }
    }

}
