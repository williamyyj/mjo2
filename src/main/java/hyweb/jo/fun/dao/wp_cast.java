package hyweb.jo.fun.dao;

import hyweb.jo.IJOFunction;
import hyweb.jo.ff.IJOFF;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.util.Collection;

/**
 * @author william
 *    使用 IJOFF 處理資料 ， 好像與 wp_eval 重複
 */
public class wp_cast implements IJOFunction<Boolean, JOWPObject> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        JSONObject act = (wp.has("$act")) ? wp.optJSONObject("$act") : wp.act();
        JSONArray arr = act.optJSONArray("$fields");

        for (int i = 0; i < arr.length(); i++) {
            String[] ffs = arr.optString(i).split(":");
            IJOFF ff = (IJOFF) wp.proc().optJSONObject("$ff").opt(ffs[0]);
            if (ff != null) {
                Object data = getData(wp, act);
                System.out.println("data:" + data.getClass());
                if (data instanceof JSONObject) {
                    proc_ffs(wp, ff, ffs[1].split(","), (JSONObject) data);
                } else if (data instanceof Collection) {
                    proc_ffs(wp, ff, ffs[1].split(","), (Collection) data);
                }
            }
        }
        return true;
    }

    private void proc_ffs(JOWPObject wp, IJOFF ff, String[] fields, JSONArray rows) {
        for (int i = 0; i < rows.length(); i++) {
            System.out.println(rows.opt(i));
            proc_ffs(wp, ff, fields, rows.optJSONObject(i));
        }
    }

    private void proc_ffs(JOWPObject wp, IJOFF ff, String[] fields, Collection rows) {
        for (Object row : rows) {
            proc_ffs(wp, ff, fields, (JSONObject) row);
        }
    }

    private void proc_ffs(JOWPObject wp, IJOFF ff, String[] fields, JSONObject row) {
        for (String fid : fields) {
            IJOField fld = wp.metadata().get(fid);
            if (fld != null) {
                proc_item(ff, fld, row);
            }
        }
    }

    private void proc_item(IJOFF ff, IJOField fld, JSONObject row) {
        if (ff != null) {
            Object fv = fld.getFieldValue(row);
            if (fv != null) {
                Object v = ff.cast(fv);
                fld.setFieldValue(row, v);
            }
        }
    }

    private Object getData(JOWPObject wp, JSONObject act) {
        String dataId = act.optString("$dataId");
        if (wp.has(dataId)) {
            return wp.opt(dataId);
        } else {
            return wp.proc().opt(dataId);
        }
    }

}
