package org.cc.ff.sch;

import hyweb.jo.org.json.JSONObject;
import org.cc.module.JOFF;

/**
 *
 * @author william
 */
public class type extends JOFF<String> {

    public type() {

    }

    @Override
    public String apply(JSONObject row) throws Exception {
        if (row != null && row.has("jdbc")) {
            String jdbc = row.optString("jdbc").toLowerCase();
            if (jdbc.startsWith("var") || jdbc.startsWith("nvar") ||  jdbc.startsWith("char")) {
                jdbc = jdbc + "(" + row.optString("size") + ")";
                set("$sch_", "", row, jdbc);
                return jdbc;
            } else {
                set("$sch_", "", row, jdbc);
                return jdbc;
            }
        }
        return "";
    }

    @Override
    public String apply(String fid, JSONObject row) throws Exception {
        return apply(row);
    }

}
