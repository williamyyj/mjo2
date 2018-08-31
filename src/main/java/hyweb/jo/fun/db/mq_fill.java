package hyweb.jo.fun.db;

import hyweb.jo.IJOFunction;
import hyweb.jo.IJOType;
import hyweb.jo.JOConst;
import hyweb.jo.db.IDB;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 *
 * @author william
 */
public class mq_fill implements IJOFunction<Boolean, JSONObject> {

    @Override
    public Boolean exec(JSONObject mq) throws Exception {
        PreparedStatement ps = JOTools.obj(PreparedStatement.class, mq, JOConst.param_ctrl);
        IDB db = JOTools.obj(IDB.class, mq, JOConst.param_dp);
        try {
            proc_fill_all(db, ps, mq.optJSONArray(JOConst.param_fields));
            return true;
        } catch (Exception e) {
            JOLogger.error("mq_fill fail : "+mq);
            return false;
        }

    }

    private void proc_fill_all(IDB db, PreparedStatement ps, JSONArray fields) throws SQLException {
        for (int i = 0; i < fields.length(); i++) {
            JSONObject fld = fields.optJSONObject(i);
            Object dt = fld.opt("dt");
            Object value = fld.opt("value");
            IJOType<?> type = db.types().type(dt);
            type.setPS(ps, i + 1, value);
        }
    }

}
