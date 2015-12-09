package hyweb.jo;

import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;

/**
 *
 * @author william
 */
public class JOConfig {

    private JSONObject pcfg; // public config
    private JSONObject cfg;

    public JOConfig(String base, String id) {
        init(base, id);
    }

    private void init(String base, String id) {
        pcfg = JOCache.load(base, "cfg");
        if (pcfg != null) {
            cfg = JOCache.load(base + "/config", id);
            String scope = pcfg.optString("scope");
            cfg = (cfg != null) ? cfg.optJSONObject(scope) : null;
        }
        init_params();
    }

    private void init_params() {
        //  
    }

    public JSONObject params() {
        return this.cfg;
    }

}
