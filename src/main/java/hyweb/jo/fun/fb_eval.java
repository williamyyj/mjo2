package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.JOProcObject;
import hyweb.jo.util.DateUtil;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mvel2.MVEL;

/**
 *
 * @author william
 */
public class fb_eval implements IJOFunction<Boolean, Object[]> {

    @Override
    public Boolean exec(Object[] p) throws Exception {
        JOProcObject proc = (JOProcObject) p[0];
        List<IJOField> eFields = (List<IJOField>) p[1];
        JSONObject row = (JSONObject) p[2];
        JSONObject ref = (JSONObject) ((p.length > 3) ? p[3] : new JSONObject());
        return exec(proc, eFields, row, ref);
    }

    public Boolean exec(JOProcObject proc, List<IJOField> eFields, JSONObject row, JSONObject ref) throws Exception {
        Map<String, Object> m = eval_pool(proc, row, ref);
        JSONObject reval = new JSONObject();
        m.put("$eval", reval.m());
        for (IJOField fld : eFields) {
            m.put("$fld", fld);
            try {
                Object o = MVEL.eval(fld.eval(), m);
                if (o instanceof Boolean) {
                    boolean ret = (Boolean) o;
                    if (!ret) {
                        reval.put(fld.id(), false);
                    }
                }
            } catch (Exception e) {
                JOLogger.error("Can't eval : " + fld.id() + "\n" + m, e);
            }
        }
        row.put("$eval", reval);
        return true;
    }

    private Map<String, Object> eval_pool(JOProcObject proc, JSONObject row, JSONObject ref) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("$", row);
        m.put("$$", ref);
        m.put("$proc", proc);
        m.put("$db", proc.db());
        m.put("$now", new Date());
        m.put("$f", JOFunctional.class);
        m.put("$du", DateUtil.class);
        return m;
    }

}
