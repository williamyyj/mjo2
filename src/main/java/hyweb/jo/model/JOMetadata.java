package hyweb.jo.model;

import hyweb.jo.model.field.JOTBField;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author william
 */
public class JOMetadata extends ArrayList<IJOField> {

    private final static String prefix = "/dp/metadata";
    private String base;
    public JSONObject cfg;
    private Map<String, IJOField> fm = new HashMap<String, IJOField>();

    public JOMetadata(String base, String id) {
        super();
        try {
            this.base = base;
            this.cfg = JOCache.load(base + prefix, id);
            String tb_id = cfg.optString("id");
            String tb_name = cfg.optString("name");
            JOTBField tb = new JOTBField(tb_id, tb_name);
            add(tb);
            fm.put(tb_id, tb);
            init_ref_fields(cfg);
            init_fields(cfg.optJSONArray("meta"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void init_ref_fields(JSONObject cfg) throws Exception {
        String[] items = cfg.optString("ref").split(",");
        for (String ref : items) {
            if (ref.length() > 0) {
                JSONObject mcfg = JOCache.load(base + prefix, ref);
                if (mcfg != null && mcfg.has("meta")) {
                    init_fields(mcfg.optJSONArray("meta"));
                }
            }
        }
    }

    private void init_fields(JSONArray metadata) throws Exception {

        if (metadata != null) {
            for (int i = 0; i < metadata.length(); i++) {
                JSONObject meta = metadata.optJSONObject(i);
                IJOField fld = JOFieldFactory.newField(meta.optString("dt"));
                if (fld != null) {
                    fld.__init__(meta);
                    add(fld);
                    fm.put(fld.id(), fld);
                }
            }
        }
    }

    public String name() {
        return cfg.optString("name");
    }

    public JSONObject cfg() {
        return this.cfg;
    }

    public String[] scope(String id) {
        JSONArray scope = cfg.optJSONArray("scope");
        for (int i = 0; i < scope.length(); i++) {
            String s = scope.optString(i);
            if (s.startsWith(id)) {
                return s.split(",");
            }
        }
        return null;
    }

    public List<IJOField> getFieldsByScope(String id) {
        String[] items = scope(id);
        List<IJOField> ret = new ArrayList<IJOField>();
        for (String s : items) {
            IJOField fld = fm.get(s);
            if (fld != null) {
                ret.add(fld);
            }
        }
        return ret;
    }

    public List<IJOField> getFields(String line) {
        List<IJOField> ret = new ArrayList<IJOField>();
        String[] items = line.split(",");
        for (String item : items) {
            int ps = item.indexOf(':');
            String id = (ps > 0) ? item.substring(0, ps) : item;
            String args = (ps > 0) ? item.substring(ps + 1) : null;
            IJOField fld = getField(id, args);
            if (fld != null) {
                ret.add(fld);
            }
        }
        return ret;
    }

    public IJOField getField(String id, String args) {
        IJOField fld = fm.get(id);
        if (fld != null && args != null) {
            fld.cfg().put("args", args);
        }
        return fld;
    }

}
