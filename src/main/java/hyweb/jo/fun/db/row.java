package hyweb.jo.fun.db;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOConst;
import hyweb.jo.db.IDB;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 *
 * @author william
 */
public class row extends base implements IJOFunction<JSONObject, JOWPObject> {

    @Override
    public JSONObject exec(JOWPObject wp) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        IDB db = wp.proc().db();
        JSONObject mq = wp.mq();
        try {
            ps = db.connection().prepareStatement(mq.optString(JOConst.param_sql));
            mq.put(JOConst.param_ctrl, ps);
            mq.put(JOConst.param_dp, wp.proc().db());
            JOFunctional.exec("db.mq_fill", mq);
            rs = ps.executeQuery();
            List<JSONObject> metadata = (List<JSONObject>) JOFunctional.exec2("model.FMRsmd2Metadata", db.types(), rs);
            return (rs.next()) ? (JSONObject) JOFunctional.exec2("model.FMRS2Row", metadata, rs) : null;
        } finally {
            release(rs);
            release(ps);
        }
    }

}
