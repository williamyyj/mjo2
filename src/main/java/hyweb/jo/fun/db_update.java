/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.db.DBCmd;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.JOProcConst;
import hyweb.jo.JOProcObject;
import java.util.List;

/**
 *
 * @author william
 */
public class db_update implements IJOFunction<Boolean, Object[]> {

    public Boolean exec(Object[] args) throws Exception {
        JOProcObject proc = (JOProcObject) args[0];
        List<IJOField> fields = (List<IJOField>) args[1];
        JSONObject row = (JSONObject) args[2];
        try {
            JSONObject jq = new JSONObject(row.m());
            jq.put(JOProcConst.act, "edit");
            jq.put(JOProcConst.cmd, JOFunctional.exec("model.FSQLUpdate", fields));
            JSONObject mq = DBCmd.parser_cmd(proc.db(), jq);
            JOLogger.debug(mq);
            proc.db().action(mq);
            return true;
        } catch (Exception e) {
            JOLogger.error(e);
            return false;
        }
    }

}
