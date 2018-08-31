package hyweb.jo.fun.docs;

import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.List;

/**
 * @author william name1 , name2, name3 value1 , value2 , value3
 */
public class wp_csv_row extends wp_csv {

    @Override
    public Object exec(JOWPObject wp) throws Exception {
        JSONObject act = wp.act();
        List<IJOField> mFields = wp.metadata().getFields(act.optString("$fields"));
        List<IJOField> bFields = wp.metadata().getFields(act.optString("$before"));
        proc_csv_head(wp, mFields);
        List<JSONObject> data = (List<JSONObject>) wp.proc().db().action(wp.mq_orderby());
        int idx = 0;
        for (JSONObject row : data) {
            row.put("$i", idx);
            JOFunctional.exec2("fb_eval", wp.proc(), bFields, row, wp.p());
            proc_csv_row(wp, mFields, row);
            idx++;
        }
        return null;
    }

    private void proc_csv_row(JOWPObject wp, List<IJOField> hFields, JSONObject row) throws Exception {
        StringBuilder sb = new StringBuilder();
        String split = wp.act().optString("split", ",");
        for (IJOField fld : hFields) {
            Object o = fld.getFieldValue(row);
            if (o != null) {
                if (o instanceof String) {
                    String text = (String) o;
                    if (text.contains(",") && text.contains("\"")) {
                        text = text.replaceAll("\"", "\"\"");
                        sb.append("\"").append(text).append('"');
                    } else if (text.contains(",")) {
                        sb.append("\"").append(text).append('"');
                    } else if (text.contains("\r") || text.contains("\n")) {
                        text = text.replaceAll("\"", "\"\"");
                        sb.append("\"").append(text).append('"');
                    } else {
                        sb.append(text);
                    }
                } else {
                    sb.append(o);
                }
            }
            sb.append(split);
        }
        if (hFields.size() > 0) {
            sb.setLength(sb.length() - split.length());
        }
        get_pcsv(wp).add(sb.toString());
    }

    private void proc_csv_head(JOWPObject wp, List<IJOField> hFields) {
        StringBuilder sb = new StringBuilder();
        String split = wp.act().optString("split", ",");
        for (IJOField fld : hFields) {
            sb.append(fld.label()).append(split);
        }
        if (hFields.size() > 0) {
            sb.setLength(sb.length() - split.length());
        }
        get_pcsv(wp).add(sb.toString());
    }

}
