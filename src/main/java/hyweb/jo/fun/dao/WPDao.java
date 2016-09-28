package hyweb.jo.fun.dao;

import hyweb.jo.JOConst;
import hyweb.jo.JOProcConst;
import hyweb.jo.db.DBCmd;
import hyweb.jo.log.JOLogger;
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

    private boolean hasAutoField(List<IJOField> flds){
        for(IJOField fld : flds){
            if("auto".equals(fld.ft()) && "P".equals(fld.ct()) ){
                return true;
            }
        }
        return false ;
    } 
    
    protected Object proc_dao_cmd(JOWPObject wp, String cmdId, String actId) throws Exception {
        List<IJOField> tbFields = wp.metadata().getFields();
        String cmd = (String) JOFunctional.exec(cmdId, tbFields);
        if (JOConst.act_add.equals(actId) && !cmd.contains("SCOPE_IDENTITY") && hasAutoField(tbFields)  ) {
            cmd=cmd+";SELECT SCOPE_IDENTITY()";
            JOLogger.debug("===== cmd:"+ cmd);
        }
        JSONObject jq = new JSONObject(wp.p());
        for (IJOField fld : tbFields) {
            if ("M".equals(fld.ct()) && fld.cfg().has("dv")) {
                Object v = fld.getFieldValue(jq);
                if (v == null) {
                    fld.setFieldValue(jq, fld.cfg().opt("dv"));
                }
            }
        }

        jq.put(JOProcConst.cmd, cmd);
        jq.put(JOProcConst.act, actId);
        JSONObject mq = DBCmd.parser_cmd(wp.proc().db(), jq);
        return wp.proc().db().action(mq);
    }

}
