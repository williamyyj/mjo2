package hyweb.jo.model.ht;

import hyweb.jo.JOProcObject;
import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.ht.HTCell;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;
import org.mvel2.templates.TemplateRuntime;

/**
 * { id: , label: , url: , $ext : "{Y:Y,N:N}"
 *
 * @author william
 */
@IAProxyClass(id = "ht.combo")
public class HTCombo extends HTCell {

    private String fmt_select = "<select id=\"%s\" name=\"%s\" class=\"%s\" ejo=\"%s\" >";

    public HTCombo() {

    }

    public HTCombo(String line) {
        super(line);
    }

    @Override
    public String render(JOProcObject proc) {
        JSONArray items = new JSONArray();
        JSONArray rows = cfg.optJSONArray("rows");
        if (rows != null) {
            for (Object row : rows) {
                String[] args = row.toString().split(":");
                JSONObject item = new JSONObject();
                item.put("value", args[0]);
                item.put("display", (args.length > 1) ? args[1] : args[0]);
                items.put(item);
            }
        }
        cfg.put("items", items);
        return "";
    }

    @Override
    public String display(Object p, Object dv) {
        try {
            JSONObject m = (JSONObject) p;
            StringBuilder sb = new StringBuilder();
            String line = (String) TemplateRuntime.eval(cfg.optString("ejo"), m);
            JOLogger.debug(line);
            String ejo = JOTools.encode(line);
            String id = cfg.optString("id");
            sb.append(String.format(fmt_select, id, id, cfg.opt("class"), ejo));
            JSONArray items = cfg.optJSONArray("items");
            for (Object o : items) {
                JSONObject item = (JSONObject) o;
                String value = item.optString("value");
                String display = item.optString("display");
                select_item(sb, value, display, m.optString(cfg.optString("id")));
            }
            sb.append("\r\n</select>");
            return sb.toString();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
    }

    private void select_item(StringBuilder sb, String value, String display, String dv) {
        if (value.equals(dv)) {
            sb.append(String.format("\r\n<option value=\"%s\" selected>%s</option>", value, display));
        } else {
            sb.append(String.format("\r\n<option value=\"%s\">%s</option>", value, display));
        }
    }

}
