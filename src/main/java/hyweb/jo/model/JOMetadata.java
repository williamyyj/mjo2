package hyweb.jo.model;

import hyweb.jo.log.JOLogger;
import hyweb.jo.model.field.JOTBField;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.org.json.JSONTokener;
import hyweb.jo.util.JOCache;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author william 支援 import tag
 */
public class JOMetadata extends HashMap<String, IJOField> {

    private  String prefix = "/dp/metadata";
    private String base;
    private JSONObject cfg;

    public JOMetadata(String base, String id) {
        super();
        try {
            this.base = base;
            this.cfg = new JSONObject( JOCache.load(base + prefix, id).m());
            init_tb_field();
            init_import();
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

    private void init_import_item(String item) throws Exception {
        File f = new File(base + prefix + "/inc",item+".json");
        JSONObject child = loadJSON(f) ; 
        if (child != null) {
            if ("meta".equals(child.opt("classId")) || child.has("meta")) {
                init_item_meta(child);
            } else  {
                init_item_cmd(child);
            }
        }
    }

    private void init_item_meta(JSONObject child) {
         init_fields(child.optJSONArray("meta"));
    }

    private void init_item_cmd(JSONObject child) {
        Set<String> names = child.m().keySet();
        for(String n : names){
            if(!cfg.has(n) && n.charAt(0)!='$'){
                cfg.put(n, child.opt(n));
            } else {
                System.out.println("===== meta "+cfg.opt(n));
                System.out.println("===== act "+child.opt(n));
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
                       IJOField old =  put(fld.id(), fld);
                       if(old!=null){
                             JOLogger.warn("dup field " + fld.id());
                       }
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
            IJOField fld = get(s);
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
        IJOField fld = get(id);
        if (fld != null && args != null) {
            fld.cfg().put("args", args);
        }
        return fld;
    }
    
      private JSONObject loadJSON(File f) throws Exception {
        Reader reader = new InputStreamReader(new FileInputStream(f), "UTF-8");
        try {
            JSONTokener tk = new JSONTokener(reader);
            return new JSONObject(tk);
        } finally {
            reader.close();
        }
    }

}
