package hyweb.jo.fun.db;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOConst;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.sql.PreparedStatement;

/**
 *
 * @author william
 */
public class wp_executeUpdate extends base implements IJOFunction<Object, JOWPObject> {

    @Override
    public Object exec(JOWPObject wp) throws Exception {
        PreparedStatement ps = null;
        try {
            JSONObject mq = wp.mq();
            JOLogger.debug(mq.opt(JOConst.param_sql));
            ps = wp.proc().db().connection().prepareStatement(mq.optString(JOConst.param_sql));
            mq.put(JOConst.param_ctrl, ps);
            mq.put(JOConst.param_dp, wp.proc().db());
            
            JOFunctional.exec("db.mq_fill",mq);
            return ps.executeUpdate();
        } finally {
            release(ps);
        }
    }

}
