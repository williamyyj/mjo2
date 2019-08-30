package hyweb.jo.model;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;
import hyweb.jo.util.JOUtils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author william
 *
 *
 */
public class JOFSMObject extends HashMap<String, JOActorObject> {

    private JOProcObject proc;
    private String moduleId;
    private JSONObject cfg;

    public JOFSMObject(JOProcObject proc, String moduleId) {
        this.moduleId = moduleId;
        cfg = JOCache.load(proc.base() + "/module", moduleId);
        load_actors();
    }

    private void load_actors() {
        JSONObject actors = cfg.optJSONObject("$actors");
        JSONArray names= actors.names();
        for(int i=0; i<names.length();i++){
            load_actor(names.optString(i));
        }
    }

    private void load_actor(String name) {
        Object o = cfg.opt(name);
        if (o instanceof String) {
            String line = ((String) o).replaceFirst("@", moduleId);
            put(name, new JOActorObject(proc, JOUtils.toJA(line)));
        } else if (o instanceof JSONObject) {
            JSONObject jo = (JSONObject) o;
            if ("@".equals(jo.optString("$metaId")) || !jo.has("$metaId")) {
                jo.put("$metaId", moduleId);

            }
            put(name, new JOActorObject(proc, jo));
        }
    }

    public JSONObject cfg() {
        return this.cfg;
    }

}
