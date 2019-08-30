package hyweb.jo;

import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;

/**
 *
 * @author william
 */
public class JOCfg {

    private JSONObject cfg;

    public JOCfg(String base) {
        cfg = JOCache.load(base, "cfg");
        cfg.put("$base", base);
    }
    
    public String scopeAsString(JSONObject row, String id){
        String scope = cfg.optString("scope");
        String key = scope+"_"+id ;
        return row.optString(key,row.optString(id));
    }
}
