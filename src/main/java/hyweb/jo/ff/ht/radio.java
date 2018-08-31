package hyweb.jo.ff.ht;

import hyweb.jo.JOProcObject;
import hyweb.jo.ff.JOFFBase;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author william
 */
public class radio extends JOFFBase<String> {

    private StringBuilder content;

    @Override
    public void __init_proc(JOProcObject proc) {
        content = new StringBuilder();
        init_content(proc);
    }

    @Override
    protected String apply(JSONObject row, String id, Object fv) {
        return cast(fv);
    }

    @Override
    public String cast(Object fv) {
        if (fv != null) {
            String key = fv.toString().trim();
            return cfg.optString(key);
        }
        return "";
    }

    @Override
    public String getContent() {
        return content.toString();
    }

    private void init_content(JOProcObject proc) {
        String dv = (String) this.getRowValue(proc.params(), "");
        String act = proc.params().optString("act");
        JSONArray names = cfg.optJSONArray("$ord");
        if (names != null) {
            for (int i = 0; i < names.length(); i++) {
                String value = names.optString(i);
                String label = cfg.optString(value);
                if ("$br".equals(value)) {
                    content.append("<br/>");
                } else {
                    String id = cfg.optString("$id") + "_" + value;
                    String name = cfg.optString("$id");
                    content.append("<input type=\"radio\" id=\"").append(id)
                      .append("\" name=\"").append(name)
                      .append("\" value=\"").append(value).append("\"");
                    if (dv.equals(value)) {
                        content.append(" checked ");
                    }
                    content.append("/>");
                    content.append("<label for=\"").append(id).append("\">").append(label).append("</label>");
                }
            }
        }

        proc.set(JOProcObject.p_request, "ht_" + ffId, content.toString());
    }
}
