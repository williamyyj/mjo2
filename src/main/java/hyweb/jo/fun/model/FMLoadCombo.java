package hyweb.jo.fun.model;

import hyweb.jo.IJOFunction;
import hyweb.jo.db.DB;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;
import hyweb.jo.util.JOFunctional;
import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * @author william dp::uri::fid::id?city
 */
public class FMLoadCombo implements IJOFunction<List<JSONObject>, String> {

    /**
     *
     * @param urn
     * @return
     * @throws Exception
     */
    @Override
    public List<JSONObject> exec(String urn) throws Exception {
        if (urn == null) {
            return null;
        }
        String[] items = urn.split("::");
        File base = new File(new URI(items[1]));
        String comboId = items[2];
        return load_combo(base, comboId);
    }

    private List<JSONObject> load_combo(File base, String comboId) throws Exception {
        List<JSONObject> ret = new ArrayList<JSONObject>();
        DB db = new DB(base.getAbsolutePath());
        try {
            JSONObject jq = JOCache.load(db.base() + "/dp/combo", comboId);
            JSONArray ops = jq.optJSONArray("option");
            // inject 
            if (ops != null) {
                for (int i = 0; i < ops.length(); i++) {
                    JSONObject item = ops.optJSONObject(i);
                    ret.add(item);
                }
            }
            String sql = (String) JOFunctional.exec("util.FJA2String", jq.opt("$sql"));
            List<JSONObject> rows = db.rows(sql);
            if (rows != null) {
                for (JSONObject row : rows) {
                    ret.add(row);
                }
            }

        } finally {
            db.close();
        }
        return ret;
    }
    
}
