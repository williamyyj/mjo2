package hyweb.jo.fun.db;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOConst;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author william
 */
public class wp_execute extends base implements IJOFunction<Object, JOWPObject> {

    @Override
    public Object exec(JOWPObject wp) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object ret = null;
        try {
            JSONObject mq = wp.mq();
            JOLogger.debug(mq.opt(JOConst.param_sql));
            ps = wp.proc().db().connection().prepareStatement(mq.optString(JOConst.param_sql));
            mq.put(JOConst.param_ctrl, ps);
            mq.put(JOConst.param_dp, wp.proc().db());
            JOFunctional.exec("db.mq_fill", mq);
            boolean isResultSet = ps.execute();
            if (isResultSet) {
                while (ps.getMoreResults() || (ps.getUpdateCount() != -1)) {
                    // 查到第一個ResultSet 
                }
                rs = ps.getResultSet();
                if (rs != null && rs.next()) {
                    ret = rs.getLong(1);
                }
            }

        } finally {
            release(rs);
            release(ps);
        }
        return ret;
    }

    public Object exec_old_method(JOWPObject wp) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Object ret = null;
        try {
            JSONObject mq = wp.mq();
            JOLogger.debug(mq.opt(JOConst.param_sql));
            ps = wp.proc().db().connection().prepareStatement(mq.optString(JOConst.param_sql));
            mq.put(JOConst.param_ctrl, ps);
            mq.put(JOConst.param_dp, wp.proc().db());
            JOFunctional.exec("db.mq_fill", mq);
            boolean isResultSet = ps.execute();
            if (ps.getMoreResults()) {
                rs = ps.getResultSet();
                if (rs != null && rs.next()) {
                    ret = rs.getLong(1);
                }
            }

        } finally {
            release(rs);
            release(ps);
        }
        return ret;
    }

}
