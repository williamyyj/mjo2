package hyweb.jo.convert;

import hyweb.jo.JOProcObject;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author william
 */
public class JOMapConvert extends JOConvert<String, Object> {

    private Map<Object, JSONObject> m;
    
    public JOMapConvert(){
        m = new HashMap<Object,JSONObject>();
    }

    public JOMapConvert(String line) {
        this();
        try {
            cfg = JOTools.loadJSON(line);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void render(JOProcObject proc) throws Exception {
        if (cfg != null) {
            String[] key = cfg.optString("key").split(",");
            String cols = cfg.optString("cols", "*");
            String cond = cfg.optString("cond", "*");
            String sql = to_sql(key, cols, cond);
            System.out.println("===== to_sql : " + sql);
            List<JSONObject> rows = proc.db().rows(sql);
            for (JSONObject row : rows) {
                proc_item(m, key, row);
            }
        }
        proc.set(JOProcObject.p_request, "$cvf_"+cfg.optString("id"), this);
    }

    private String to_sql(String[] key, String cols, String cond) {
        StringBuilder sb = new StringBuilder();
        sb.append("select ");
        int kid = 0;
        for (String k : key) {
            sb.append(k).append(" as _k").append(kid++).append(", ");
        }
        sb.append(cols).append(" from ").append(cond);
        return sb.toString();
    }

    private void proc_item(Map<Object, JSONObject> m, String[] key, JSONObject row) {
        int kid = 0;
        for (String k : key) {
            String kk = "_k" + kid++;
            m.put(row.opt(kk), row);
        }
    }

    @Override
    public Object convert(String value, Object dv) throws Exception {
        return convert(cfg.optString("pattern"), value, dv);
    }

    @Override
    public Object convert(String pattern, String value, Object dv) throws Exception {
        JSONObject row = m.get(value);
        if (row != null) {
            return row.opt(pattern);
        }
        return dv;
    }

}
