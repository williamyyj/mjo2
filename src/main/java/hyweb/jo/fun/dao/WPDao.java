package hyweb.jo.fun.dao;

import hyweb.jo.JOProcConst;
import hyweb.jo.db.DBCmd;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.List;

/**
 *
 * @author william
 */
public class WPDao {

    protected Object proc_dao_cmd(JOWPObject wp, String cmdId, String actId) throws Exception {
        List<IJOField> tbFields = wp.metadata().getFields();
        String cmd = (String) JOFunctional.exec(cmdId, tbFields);
        JSONObject jq = new JSONObject(wp.p());
        jq.put(JOProcConst.cmd, cmd);
        jq.put(JOProcConst.act, actId);
        JSONObject mq = DBCmd.parser_cmd(wp.proc().db(), jq);
        return wp.proc().db().action(mq); 
    }

}
