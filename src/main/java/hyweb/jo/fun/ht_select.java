package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;
import hyweb.jo.util.JOFunctional;
import java.util.List;

/**
 * @author william
 *
 * 取代 FJOComboBox 用
 */
public class ht_select implements IJOFunction<String, Object[]> {

    @Override
    public String exec(Object[] p) throws Exception {
        JOProcObject proc = (JOProcObject) p[0];
        String mapId = (String) p[1];
        JSONObject params = (p.length > 2) ? (JSONObject) p[2] : new JSONObject();
        JSONObject cmd = JOCache.load(proc.base() + "/dp/ht", mapId);
        return exec(proc, cmd, params);
    }

    private String exec(JOProcObject proc, JSONObject combo, JSONObject params) throws Exception {
        StringBuilder sb = new StringBuilder();
        init_default(sb, combo, params);
        init_cmd(sb, proc, combo, params);
        return sb.toString();
    }

    private void init_default(StringBuilder sb, JSONObject combo, JSONObject params) {
        String dv = params.optString("dv");
        JSONArray ops = combo.optJSONArray("option");
        if (ops != null) {
            for (int i = 0; i < ops.length(); i++) {
                select_item(sb, ops.optJSONObject(i), dv);
            }
        }
    }

    private void init_cmd(StringBuilder sb, JOProcObject proc, JSONObject combo, JSONObject params) {
        String dv = params.optString("dv");
        List<JSONObject> rows = null;
        try {
            rows = (List<JSONObject>) JOFunctional.exec2("fb_rows", proc, combo, params);
            for (JSONObject row : rows) {
                select_item(sb, row, dv);
            }
        } catch (Exception e) {

        }

    }

    private void select_item(StringBuilder sb, JSONObject row, String dv) {
        String value = row.optString("value");
        String display = row.optString("label");
        select_item(sb,value,display,dv);
    }

    private void select_item(StringBuilder sb, String value, String display, String dv) {
        if (value.equals(dv)) {
            sb.append(String.format("<option value=\"%s\" selected>%s</option>\r\n", value, display));
        } else {
            sb.append(String.format("<option value=\"%s\">%s</option>\r\n", value, display));
        }
    }

}
