package org.cc.ff.sch;

import hyweb.jo.org.json.JSONObject;
import org.cc.module.JOFF;

/**
 *
 * @author william
 */
public class isnull extends JOFF<String> {

    @Override
    public String apply(JSONObject row) throws Exception {
        if (row.has("ct")) {
            String ct = row.optString("ct").toLowerCase();
            if ("m".equals(ct) || "p".equals(ct) || "I".equals(ct) || "f".equals(ct)) {
                ct = "NN";
                set("$sch_", "", row, ct);
                return ct;
            }
        }
        return "";
    }

    @Override
    public String apply(String fid, JSONObject row) throws Exception {
        return apply(row);
    }

}
