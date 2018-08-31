package hyweb.jo;

import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;

/**
 *
 * @author william
 */
public class JOConfig {

    private JSONObject pcfg; // public config
    private JSONObject cfg;
    private String base;
    private String oid;   // object id 

    public JOConfig(String base, String id, String scope) {
        this.oid = id;
        this.base = base;
        pcfg = JOCache.load(base, "cfg");
        String path = pcfg.optString("config_path", base + "/config") + "/" + scope;
        cfg = JOCache.load(path, oid);
    }

    public JOConfig(String base, String id) {
        init(base, id);
    }

    private void init(String base, String id) {
        this.base = base;
        this.oid = id;
        pcfg = JOCache.load(base, "cfg");
        if (pcfg != null) {
            int version = pcfg.optInt("version");
            switch (version) {
                case 1:
                    init_version01(pcfg);
                    break;
                default:
                    init_version00(pcfg);
            }

        }
        init_params();
    }

    /**
     * 相容舊版設定方式
     */
    private void init_version00(JSONObject pcfg) {
        cfg = JOCache.load(base + "/config", oid);
        String scope = pcfg.optString("scope");
        cfg = (cfg != null) ? cfg.optJSONObject(scope) : null;
    }

    /**
     * 相容舊版設定方式
     */
    private void init_version01(JSONObject pcfg) {
        String scope = pcfg.optString("scope");
        if ("".equals(scope)) {
            scope = System.getProperty("scope"); // 測試用或未來系統設定
        }
        String path = pcfg.optString("config_path", base + "/config") + "/" + scope;
        cfg = JOCache.load(path, oid);
    }

    protected void init_params() {
        //  
    }

    public JSONObject params() {
        return this.cfg;
    }

    public JSONObject pcfg() {
        return pcfg;
    }

}
