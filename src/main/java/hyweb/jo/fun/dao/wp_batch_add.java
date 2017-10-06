/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.dao;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOConst;
import hyweb.jo.JOProcConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DBCmd;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author william
 */
public class wp_batch_add implements IJOFunction<int[], JOWPObject> {

    @Override
    public int[] exec(JOWPObject wp) throws Exception {
         int[] ret = null;
        JOProcObject proc = wp.proc();
        boolean autoCommit = proc.db().connection().getAutoCommit();
        PreparedStatement ps = null;
        try {
            proc.db().connection().setAutoCommit(false);
            List<JSONObject> rows = (List<JSONObject>) wp.proc().opt("$rows");
            if (rows != null && rows.size() > 0) {
                String cmd = (String) JOFunctional.exec("model.FSQLInsert", wp.metadata().getFields());
                JOLogger.debug("===== cmd : " + cmd);
                ps = ps(wp, cmd);
                for (JSONObject row : rows) {
                    JSONObject mq = mq(wp, cmd, row);
                    Object[] values = JOTools.to_marray(mq, JOConst.param_fields, "value");
                    JOFunctional.exec2("model.FMPSFill", ps, values);
                    ps.addBatch();
                    ps.clearParameters();
                }
                ret =  ps.executeBatch();
            }
            proc.db().connection().commit();
        } catch (SQLException e) {
            e.printStackTrace();
            proc.db().connection().rollback();
        } finally {
            if (ps != null) {
                ps.close();
            }
            proc.db().connection().setAutoCommit(autoCommit);
        }

        return ret;
    }

    private PreparedStatement ps(JOWPObject wp, String cmd) throws Exception {
        JSONObject jq = new JSONObject();
        jq.put(JOProcConst.cmd, cmd);
        jq.put(JOProcConst.act, "add");
        JSONObject mq = DBCmd.parser_cmd(wp.proc().db(), jq);
        return wp.proc().db().connection().prepareStatement(mq.optString(JOConst.param_sql));
    }

    private JSONObject mq(JOWPObject wp, String cmd, JSONObject row) throws Exception {
        JSONObject jq = new JSONObject(row);
        jq.put(JOProcConst.cmd, cmd);
        jq.put(JOProcConst.act, "add");
        return DBCmd.parser_cmd(wp.proc().db(), jq);
    }

}
