package hyweb.jo.fun.dao;

import hyweb.jo.IJOFunction;
import hyweb.jo.IJOType;
import hyweb.jo.JOConst;
import hyweb.jo.JOProcConst;
import hyweb.jo.db.DB;
import hyweb.jo.db.DBCmd;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author william
 */
public class md_add implements IJOFunction<Integer, JOWPObject> {

    private boolean hasAutoField(List<IJOField> flds) {
        for (IJOField fld : flds) {
            if ("auto".equals(fld.ft()) && "P".equals(fld.ct())) {
                return true;
            }
        }
        return false;
    }

    private JSONObject mq(JOWPObject wp) throws Exception {
        List<IJOField> tbFields = wp.metadata().getFields();
        String cmd = (String) JOFunctional.exec("model.FSQLInsert", tbFields);
        if (!cmd.contains("SCOPE_IDENTITY") && hasAutoField(tbFields)) {
            cmd = cmd + ";SELECT SCOPE_IDENTITY()";
        }       
        JSONObject jq = new JSONObject(wp.p());
        jq.put(JOProcConst.cmd, cmd);
        jq.put(JOProcConst.act, "add");
        return  DBCmd.parser_cmd(wp.proc().db(), jq);

    }
    
    private void fill_by_fields(DB db, PreparedStatement ps, JSONArray fields) throws SQLException {
        int len = fields.length();
        for (int i = 0; i < len; i++) {
            JSONObject fld = fields.optJSONObject(i);
            Object dt = fld.opt("dt");
            Object value = fld.opt("value");
            IJOType<?> type = db.types().type(dt);
            type.setPS(ps, i + 1, value);
        }
    }

    @Override
    public Integer exec(JOWPObject wp) throws Exception {
        PreparedStatement ps = null;
        try {
            JSONObject mq = mq(wp);
            ps = wp.proc().db().connection().prepareStatement(mq.optString(JOConst.param_sql));
            fill_by_fields(wp.proc().db(), ps,  mq.optJSONArray(JOConst.param_fields));
            return ps.executeUpdate();
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

}
