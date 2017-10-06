package org.cc.fun.proc;

import hyweb.jo.JOConst;
import static hyweb.jo.JOConst.param_sql;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DBCmd;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;
import org.cc.module.ICCDBF;
import org.cc.module.ICCPF;

/**
 *
 * @author william
 */
public class row implements ICCPF<JSONObject> {

    private ICCDBF<JSONObject> dbf_row = new org.cc.fun.dbf.row();

    public row() {

    }

    @Override
    public JSONObject apply(JOProcObject proc, JSONObject evt, JSONObject cfg) throws Exception {
        //  不再使用  act   
        JSONObject mq = DBCmd.parser_cmd(proc.db(), evt);
        JOLogger.debug(mq);
        String sql = mq.optString(param_sql);
        Object[] values = JOTools.to_marray(mq, JOConst.param_fields, "value");
        return dbf_row.apply(proc,sql, values);
    }

}
