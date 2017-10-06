package org.cc.ff.sch;

import hyweb.jo.org.json.JSONObject;
import org.cc.module.JOFF;

/**
 *
 * @author william
 */
public class key extends JOFF<String> {

    @Override
    public String apply(JSONObject row) throws Exception {
        if (row.has("ct")) {
            String ct = row.optString("ct").toLowerCase();
            String key = "";
            if ("p".equals(ct)) {
                key = "PK";
            } else if("f".equals(ct)){
                key="FK";
            } else if("i".equals(ct)){
                key="IDX";
            }
            set("$sch_", "", row, ct);
            return key;
        }
        return "";
    }

    @Override
    public String apply(String fid, JSONObject row) throws Exception {
        return apply(row);
    }
}
