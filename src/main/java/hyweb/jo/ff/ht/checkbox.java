package hyweb.jo.ff.ht;

import hyweb.jo.JOProcObject;
import hyweb.jo.ff.JOFFBase;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 *
 * @author william
 */
public class checkbox extends JOFFBase<String> {

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
    
    

    private void init_content(JOProcObject proc) {
        Set<String> dv = getDefaultValue(this.getRowValue(proc.params(), null));
        System.out.println(dv);
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
                    content.append("<input type=\"checkbox\" id=\"").append(id)
                      .append("\" name=\"").append(name)
                      .append("\" value=\"").append(value).append("\"");
                    if (dv.contains(value)) {
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
