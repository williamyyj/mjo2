package org.cc.fun.proc;

import hyweb.jo.JOConst;
import static hyweb.jo.JOConst.param_sql;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DBCmd;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;
import java.util.List;
import org.cc.module.ICCDBF;
import org.cc.module.ICCPF;

/**
 *
 * @author william
 */
public class rows implements ICCPF<List<JSONObject>> {

    private ICCDBF<List<JSONObject>> dbf_rows = new org.cc.fun.dbf.rows();

    public rows() {

    }

    @Override
    public List<JSONObject> apply(JOProcObject proc, JSONObject evt, JSONObject pp) throws Exception {
        //  不再使用  act   
        JSONObject mq = DBCmd.parser_cmd(proc.db(), evt);
        JOLogger.debug(mq);
        String sql = mq.optString(param_sql);
        Object[] values = JOTools.to_marray(mq, JOConst.param_fields, "value");
        return dbf_rows.apply(proc,sql, values);
    }

}
