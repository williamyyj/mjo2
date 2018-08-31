package hyweb.jo.ff;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;

/**
 * 整合 HTComboCell , HTMappingCell
 *
 * @author william
 */
public class combo extends JOFFBase<String> {

    private StringBuilder content;

    private String fmt_select = "<select id=\"%s\" name=\"%s\" class=\"%s\" ejo=\"%s\" >";

    @Override
    public void __init_proc(JOProcObject proc) {
        content = new StringBuilder();
        init_default(proc);
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

    private void init_default(JOProcObject proc) {
        String dv = (String) this.getRowValue(proc.params(), "");
        String act = proc.params().optString("act");
        JSONArray names = cfg.optJSONArray("$ord");
        if (names != null) {
            for (int i = 0; i < names.length(); i++) {
                String value = names.optString(i);
                String display = cfg.optString(value);           
                if("$dv".equals(value) && "edit".equals(act)){
                    continue;
                }
                if ("$dv".equals(value)) {
                    value = "";  // 預設值為空
                }
                select_item(content, value, display, dv);
            }
        }
        
        

        proc.set(JOProcObject.p_request, "cb_" + ffId, content.toString());
    }

    private void select_item(StringBuilder sb, String value, String display, String dv) {
        if (value.equals(dv)) {
            sb.append(String.format("\r\n<option value=\"%s\" selected>%s</option>", value, display));
        } else {
            sb.append(String.format("\r\n<option value=\"%s\">%s</option>", value, display));
        }
    }

}
