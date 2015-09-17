package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mvel2.MVEL;

/**
 *
 * @author william
 */
public class mjo_eval extends MJOBase implements IJOFunction<Object, JSONObject> {

    @Override
    public Object exec(JSONObject wp) throws Exception {
        List<IJOField> eFields = eval_fields(wp);
        Map<String, Object> m = eval_pool(wp);
        JSONObject reval = new JSONObject();
        m.put("$eval", reval.m());        
        for (IJOField fld : eFields) {
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
        return reval;
    }

    private Map<String, Object> eval_pool(JSONObject wp) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("$", wp.optJSONObject("$").m());
        m.put("$$", wp.optJSONObject("$$").m());
        m.put("$p", proc(wp).m());
        m.put("$proc", proc(wp));
        m.put("$db", db(wp));
        m.put("$now", new Date());
        m.put("$f", JOFunctional.class);
        return m;
    }

}
