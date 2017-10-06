package hyweb.jo.ht;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;
import hyweb.jo.util.JOFunctional;
import java.util.List;

/**
 *
 * @author william { id : xxxx , dt : combo , label : xxx , map : [ xxx:yyy ,
 * ..... ]
 */
public class HTComboCell extends HTCell {

    public HTComboCell(String line) {
        super(line);
    }

    @Override
    public String render(JOProcObject proc) {
        html.setLength(0); // clean html 
        if (cfg.has("dp")) {
            String dp = cfg.optString("dp").replaceAll("\\.", "/");
            JSONObject combo = JOCache.load(proc.base() + "/dp", dp);
            init_default(html, combo, proc.params());
            init_cmd(html, proc, combo, proc.params());
        } else {
            String dv = proc.params().optString(this.getId());
            String items[] = cfg.optString("$ext").split(",");
            JSONObject m = new JSONObject();
            int idx = 0;
            for (String item : items) {
                String[] child = item.split("\\:", 0);
                if (idx == 0) {
                    m.put("$default", child[1]);
                } else {
                    m.put(child[0], child[1]);
                }
                select_item(html, child[0], child[1], dv);
                idx++;
            }
            cfg.put("$m", m);
        }
        proc.set(JOProcObject.p_request, "$ht_" + getId(), this); // register self 
        return html.toString();
    }

    private void init_default(StringBuilder sb, JSONObject combo, JSONObject params) {
        String dv = params.optString(this.getId());
        JSONArray ops = combo.optJSONArray("option");
        if (ops != null) {
            for (int i = 0; i < ops.length(); i++) {
                select_item(sb, ops.optJSONObject(i), dv);
            }
        }
    }

    private void init_cmd(StringBuilder sb, JOProcObject proc, JSONObject combo, JSONObject params) {
        String dv = params.optString(this.getId());
        List<JSONObject> rows = null;
        try {
            rows = (List<JSONObject>) JOFunctional.exec2("fb_rows", proc, combo, params);
            for (JSONObject row : rows) {
                select_item(sb, row, dv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void select_item(StringBuilder sb, JSONObject row, String dv) {
        String value = row.optString("value");
        String display = row.optString("label");
        select_item(sb, value, display, dv);
    }

    private void select_item(StringBuilder sb, String value, String display, String dv) {
        if (value.equals(dv)) {
            sb.append(String.format("<option value=\"%s\" selected>%s</option>\r\n", value, display));
        } else {
            sb.append(String.format("<option value=\"%s\">%s</option>\r\n", value, display));
        }
    }

    @Override
    public String display(Object o) {
        return display(o, cfg.optJSONObject("$m").optString("$default"));

    }

    @Override
    public String display(Object p, Object dv) {
        Object v = cfg.optJSONObject("$m").get(p);
        return (String) ((v != null) ? v : dv);
    }

}
