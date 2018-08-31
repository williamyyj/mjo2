package hyweb.jo.model;

import hyweb.jo.JOConst;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.field.JOTBField;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;
import hyweb.jo.util.JOTools;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author william 支援 import tag meta 欄位定義檔 $rem_ 注解 $屬性 act 事件 $dbFields --->
 * dao
 */
public class JOMetadata extends HashMap<String, IJOField> {

    private final static String default_path = "/dp/metadata";
    private String base;
    private JSONObject cfg;
    private String use_path;

    public JOMetadata(String base, String id) {
        this(base, default_path, id);
    }

    public JOMetadata(String base, String path, String id) {
        super();
        try {
            this.base = base;
            this.use_path = path;
            //System.out.println(base + use_path + "/" + id);
            String pid = id.replace(".", "/");
            this.cfg = new JSONObject(JOCache.load(base + use_path, pid));
            init_tb_field();
            init_import();
            init_imp_meta(cfg.optString("@imp_meta"));
            init_fields(cfg.optJSONArray("meta"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void init_import() throws Exception {
        if (cfg.has("import")) {
            String[] items = cfg.optString("import").split(",");
            for (String item : items) {
                init_import_item(item);
            }
        }
    }

    private void init_imp_meta(String item) throws Exception {
        File f = new File(base + use_path, item.replaceAll("\\.", "/") + ".json");
        if (f.exists()) {
            JSONObject child = JOTools.load(f);
            if (child.has("meta")) {
                init_item_meta(child);
            }
        }
    }

    private void init_import_item(String item) throws Exception {
        File f = new File(base + use_path + "/inc", item + ".json");
        JSONObject child = JOTools.load(f);
        if (child != null) {
            if ("meta".equals(child.opt("classId")) || child.has("meta")) {
                init_item_meta(child);
            } else {
                init_item_cmd(child);
            }
        }
    }

    private void init_item_meta(JSONObject child) {
        init_fields(child.optJSONArray("meta"));
    }

    private void init_item_cmd(JSONObject child) {
        Set<String> names = child.m().keySet();
        for (String n : names) {
            if (!cfg.has(n) && n.charAt(0) != '$') {
                cfg.put(n, child.opt(n));
            } else {
                //System.out.println("===== meta " + cfg.opt(n));
                //System.out.println("===== act " + child.opt(n));
            }
        }
    }

    private void init_tb_field() {
        String tb_id = cfg.optString("id");
        String tb_name = cfg.optString("name");
        JOTBField tb = new JOTBField(tb_id, tb_name);
        this.put(tb_id, tb);
    }

    private void init_fields(JSONArray meta) {
        if (meta != null) {
            for (int i = 0; i < meta.length(); i++) {
                JSONObject item = meta.optJSONObject(i);
                try {
                    IJOField fld = JOFieldUtils.newInstance(item);
                    if (fld != null) {
                        IJOField old = put(fld.id(), fld);
                    }
                } catch (Exception e) {
                    JOLogger.error("not field " + item);
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

    @Deprecated
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

    /**
     * metadata 文件統一使用 ·$dbFields 標示
     *
     * @param id
     * @return
     * @deprecated
     */
    @Deprecated
    public List<IJOField> getFieldsByScope(String id) {
        String[] items = scope(id);
        List<IJOField> ret = new ArrayList<IJOField>();
        for (String s : items) {
            IJOField fld = get(s);
            if (fld != null) {
                ret.add(fld);
            }
        }
        return ret;
    }

    public List<IJOField> getFields(String line) {
        if ("db".equals(line) || "table".equals(line) || line == null || line.trim().length() == 0) {
            line = cfg.optString(JOConst.meta_fields);
        }
        String[] items = line.split(",");
        return getFields(items);
    }

    /**
     * 取得預設欄位
     *
     * @return
     */
    public List<IJOField> getFields() {
        String line = cfg.optString(JOConst.meta_fields);
        String[] items = line.split(",");
        return getFields(items);
    }

    public List<IJOField> getFields(String[] items) {
        List<IJOField> ret = new ArrayList<IJOField>();
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
        IJOField fld = get(id);
        if (fld != null && args != null) {
            try {
                // fld.cfg().put("args", args);
                JSONObject n_cfg = new JSONObject(fld.cfg());
                n_cfg.put("args", args);
                return JOFieldUtils.newInstance(n_cfg);
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return fld;
    }

}
