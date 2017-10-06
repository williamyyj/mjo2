package org.cc.ff;

import org.cc.module.JOFF;
import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.util.List;
import org.cc.module.CCFunc;

/**
 *
 * @author william
 */
public class combo extends JOFF<String> {

    public combo() {

    }

    @Override
    public void init(JOProcObject proc) throws Exception {
        if (cfg.has("$cmd")) {
            List<JSONObject> rows = (List<JSONObject>) CCFunc.proc("rows", proc, cfg,null);
            if (rows != null) {
                JSONArray ord = new JSONArray();
                for (JSONObject row : rows) {
                    String key = row.optString("k");
                    ord.put(key);
                    cfg.put(key, row.optString("l"));
                }
                cfg.put("$ord", ord);
            }
        }
    }

    @Override
    public String apply(JSONObject row) throws Exception {
        String key = this.getRowValue(row, null);
        String value = (key != null) ? cfg.optString(key) : "";
        setText(row,value);
        return value;
    }

    @Override
    public String apply(String fid, JSONObject row) throws Exception {
        String key = row.optString(fid);
        String value = (key != null) ? cfg.optString(key) : "";
        setText(row,value);
        return value;
    }

}
