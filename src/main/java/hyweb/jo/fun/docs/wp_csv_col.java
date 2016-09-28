package hyweb.jo.fun.docs;

import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.List;

/**
 * @author william name1 , value1 name2 , value2 name3 , value3
 */
public class wp_csv_col extends wp_csv {

    @Override
    public Object exec(JOWPObject wp) throws Exception {
        JSONObject act = wp.act();
        List<IJOField> mFields = wp.metadata().getFields(act.optString("$fields"));
        List<IJOField> bFields = wp.metadata().getFields(act.optString("$before"));
        List<JSONObject> data = (List<JSONObject>) JOFunctional.exec("wp_rows", wp);
        proc_csv_head(wp, mFields);
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
        String split = wp.act().optString("split", ",");
        List<String> csv = get_pcsv(wp);
        int idx = 0;
        for (IJOField fld : hFields) {
            String line = csv.get(idx);
            Object o = fld.getFieldValue(row);
            o = (o != null) ? o : "";
            csv.set(idx, line + split + o);
            idx++;
        }
    }

    private void proc_csv_head(JOWPObject wp, List<IJOField> hFields) {
        List<String> csv = get_pcsv(wp);
        for (IJOField fld : hFields) {
            csv.add(fld.label());
        }
    }
}
