package hyweb.jo.fun.load;

import hyweb.jo.IJOFunction;
import hyweb.jo.data.JOFileUtils;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author william
 */
public class wp_csv implements IJOFunction<List<JSONObject>, JOWPObject> {

    @Override
    public List<JSONObject> exec(JOWPObject wp) throws Exception {
        List<JSONObject> rows = new ArrayList<JSONObject>();
        JSONObject act = wp.act();
        if (act != null) {
            File fp = (File) wp.p().opt("fp");
            String enc = act.optString("encoding");
            String lineSplit = act.optString("lineSplit", "\n");
            String colSplit = act.optString("colSplit");
            String text = JOFileUtils.loadString(fp, enc);
            List<IJOField> eFields = wp.metadata().getFields(act.optString("fields"));
            String[] items = text.split(lineSplit);
            for (String item : items) {
                JSONObject row = proc_csv(eFields, item, colSplit);
                if (row != null) {
                    rows.add(row);
                }
            }
        }
        return rows;
    }

    private JSONObject proc_csv(List<IJOField> eFields, String item, String colSplit) {
        String[] cols = item.split(colSplit, eFields.size());
        if (cols.length >= eFields.size()) {
            JSONObject row = new JSONObject();
            int idx = 0;
            for (IJOField fld : eFields) {
                row.put(fld.id(), fld.convert(cols[idx++]));
            }
            return row;
        }
        return null;
    }

}
