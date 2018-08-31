package hyweb.jo.fun;

import hyweb.jo.model.JOWPObject;
import java.util.HashMap;
import java.util.Map;
import org.mvel2.MVEL;

/**
 *
 * @author william
 */
public class mvel_before extends wp_base<Object> {

    @Override
    public Object exec(JOWPObject wp) throws Exception {
        String expression = wp.act().optString("$before");
        Map<String,Object> ret = (Map<String,Object>) MVEL.eval(expression, varPools(wp));
        wp.p().putAll(ret);
        return ret;
    }

    private Map<String, Object> varPools(JOWPObject wp) {
        HashMap<String, Object> m = new HashMap<String, Object>();
        m.put("$", wp.p());
        m.put("$$", wp.pp());
        m.put("$ff", m);
        return m;
    }

}
