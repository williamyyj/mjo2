package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.DateUtil;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.TextUtils;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.mvel2.MVEL;

/**
 *
 * @author william
 */
public class wp_setting implements  IJOFunction<JSONObject, JOWPObject>{

    @Override
    public JSONObject exec(JOWPObject wp) throws Exception {
        JSONObject row = new JSONObject();
        JSONArray arr = wp.act().optJSONArray("$mvel");
        if(arr!=null){
            Map<String,Object> m = eval_pool(wp);
            for(int i=0; i<arr.length();i++){
                m.put("$i", i);
                String[] items = arr.optString(i).split(":");
                if(items.length==2){
                    Object value = MVEL.eval(items[1],m);
                    JOLogger.debug(items[0]+":"+value);
                    wp.p().put(items[0],value);
                    row.put(items[0], value);
                }
            }
        }
        return row ;
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
        m.put("$tu", TextUtils.class);
        m.put("$ff", wp.proc().opt("$ff"));
        m.put("$now", new Date());
        return m;
    }
    
}
