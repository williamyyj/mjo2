package hyweb.jo.ff.ht;

import hyweb.jo.JOProcObject;
import hyweb.jo.ff.JOFFBase;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.util.Arrays;
import java.util.Collection;

import java.util.LinkedHashSet;
import java.util.Set;

/**
 *          <input type="checkbox" id="level_1" name="level" value="1"/><label for="level_1">
 * -->   <input type="checkbox" id="level_1" name="level_1" value="1"/><label for="level_1">
 * @author william
 */
public class checkbox extends JOFFBase<String> {

    protected StringBuilder content;

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
        StringBuilder sb = new StringBuilder();
        if (fv != null) {
            Set items = getDefaultValue(fv);
           
            for (Object item : items.toArray()) {
                String label = cfg.optString(item.toString());
                if (label.length() == 0) {
                    return "";
                }
                sb.append(label).append(",");
            }
            if (items.size() > 0) {
                sb.setLength(sb.length() - 1);
            }
            return sb.toString();
        }
        return "";
    }

    @Override
    public String getContent() {
        return content.toString();
    }

    private Set<String> getDefaultValue(Object dv) {
        Set ret = new LinkedHashSet();
        if (dv instanceof String) {
            String[] item = dv.toString().split(",");
            ret.addAll(Arrays.asList(item));
        } else if (dv instanceof Collection){
            ret = new LinkedHashSet((Collection)dv);
        }
        return ret;
    }
    
    

    protected void init_content(JOProcObject proc) {
        JSONObject p = proc.params();

        String act = proc.params().optString("act");
        JSONArray names = cfg.optJSONArray("$ord");
        String prefix = cfg.optString("$alias",cfg.optString("$id"));
        if (names != null) {
            for (int i = 0; i < names.length(); i++) {
                String value = names.optString(i);
                String label = cfg.optString(value);
                if ("$br".equals(value)) {
                    content.append("<br/>");
                } else {
                    String fid= prefix + "_" + value;
                    content.append("<input type=\"checkbox\" id=\"").append(fid)
                      .append("\" name=\"").append(fid)
                      .append("\" value=\"").append(value).append("\"");
                    if (p.has(fid)) {
                        content.append(" checked ");
                    }
                    content.append("/>");
                    content.append("<label for=\"").append(fid).append("\">").append(label).append("</label>");
                }
            }
        }

        proc.set(JOProcObject.p_request, "ht_" + ffId, content.toString());
    }

}
