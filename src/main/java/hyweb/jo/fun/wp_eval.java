package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOValidFields;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.DateUtil;
import hyweb.jo.util.JOFunctional;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.mvel2.MVEL;

/**
 * @author william 直接使用 valid 和 cala 功能
 */
public class wp_eval implements IJOFunction<Boolean, JOWPObject> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        Map<String, Object> m = eval_pool(wp);
        JSONObject reval = new JSONObject();
        m.put("$eval", reval.m());
        for (IJOField fld : wp.fields()) {
            m.put("$fld", fld);
            m.put("$fv", fld.getFieldValue(wp.p()));
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

        return true;
    }

    private Map<String, Object> eval_pool(JOWPObject wp) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("$", wp.p().m());
        m.put("$$", wp.pp());
        m.put("$wp", wp);
        m.put("$proc", wp.proc());
        m.put("$db", wp.proc().db());
        m.put("$now", new Date());
        m.put("$f", JOFunctional.class);
        m.put("$du", DateUtil.class);
        if (wp.act().has("vf")) {
            m.put("$vf", JOValidFields.vf(wp.act().optString("vf"), wp.proc().base()));
        }
        return m;
    }

}
