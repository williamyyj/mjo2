package hyweb.jo.fun.db;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.sql.CallableStatement;

/**
 *
 * @author william
 */
public class wp_call implements IJOFunction<Object, JOWPObject> {

    @Override
    public Object exec(JOWPObject wp) throws Exception {
        return proc_call(wp);
    }

    private Object proc_call(JOWPObject wp) throws Exception {

        CallableStatement cs = null;
        JOProcObject proc = wp.proc();
        int ret = 0;
        try {
            long ts = System.currentTimeMillis();
            JSONObject mq = wp.mq();
            System.out.println(wp.mq());
            cs = proc.db().connection().prepareCall(mq.optString(JOConst.param_sql));
            fill(cs, mq);
            ret = cs.executeUpdate();
            long te = System.currentTimeMillis();
            JOLogger.info(String.format("\r\nExecuted successfully in %.1f s, %s rows affected.\r\n%s",
              ((te - ts) / 1000.0), ret, mq.optString(JOConst.param_sql)));
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
        long te = System.currentTimeMillis();

        return null;
    }

    private void fill(CallableStatement cs, JSONObject mq) throws Exception {
        // 這部份有問題 ...... 
        JSONArray fields = mq.optJSONArray(JOConst.param_fields);
        for (int i = 0; i < fields.length(); i++) {
            JSONObject field = fields.optJSONObject(i);
            String name = field.optString("name");
            Object value = field.opt("value");
            cs.setObject(name, value);
        }
    }

}
