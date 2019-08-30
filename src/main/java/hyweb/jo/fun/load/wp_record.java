package hyweb.jo.fun.load;

import hyweb.jo.IJOFunction;
import hyweb.jo.data.JOFileUtils;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * p.fp : 檔案名稱 ( File) p.fp : String 新增字串格式
 *
 * @author william
 */
public class wp_record implements IJOFunction<List<JSONObject>, JOWPObject> {

    @Override
    public List<JSONObject> exec(JOWPObject wp) throws Exception {
        JSONObject p = wp.p();
        List<JSONObject> rows = new ArrayList<JSONObject>();
        JSONObject act = wp.act();
        if (act != null) {
            String enc = act.optString("encoding");
            String lineSplit = act.optString("lineSplit", "\\r\\n");
            int min = act.optInt("min");
            String text = loadData(wp);
            List<IJOField> eFields = wp.metadata().getFields(act.optString("fields"));
            proc_fields(eFields);
            String[] items = text.split(lineSplit);
            for (String item : items) {
                try {
                    JSONObject row = proc_record(eFields, item, min);
                    row.put("rid", p.opt("rid"));
                    row.put("sysId", p.opt("sysId"));
                    rows.add(row);
                } catch (Exception e) {
                    JOLogger.info("Fail row : [" + item + "]");
                }
            }
        }
        return rows;
    }

    private String loadData(JOWPObject wp) throws Exception {
        Object fp = wp.p().opt("fp");
        if (fp instanceof File) {
            return loadDataFromFile(wp);
        } else if (fp instanceof String) {
            return (String) fp;
        }
        return null;
    }

    private String loadDataFromFile(JOWPObject wp) throws Exception {
        File fp = (File) wp.p().opt("fp");
        String enc = wp.act().optString("encoding");
        return JOFileUtils.loadString(fp, enc);
    }

    private void proc_fields(List<IJOField> eFields) {
        int offset = 0;
        for (IJOField fld : eFields) {
            int size = fld.cfg().optInt("args");
            fld.cfg().put("offset", offset);
            fld.cfg().put("len", size);
            offset += size;
        }
    }

    private JSONObject proc_record(List<IJOField> eFields, String item, int min) {
        if (item.length() >= min) {
            JSONObject row = new JSONObject();
            for (IJOField fld : eFields) {
                proc_field_value(row, fld, item);
            }
            return row;
        }
        return null;
    }

    /**
     * @changelog 2017-04-06 修正處理缺少欄位問題（資料長度不足）
     * @param row
     * @param fld
     * @param item
     */
    private void proc_field_value(JSONObject row, IJOField fld, String item) {
        int ps = fld.cfg().optInt("offset");
        int pe = ps + fld.cfg().optInt("len");
        int size = item.length();
        if (size > ps) {  // 處理缺少欄位問題
            pe = (size >= pe) ? pe : size;
        }
        if (item.length() >= pe) {
            String text = item.substring(ps, pe);
            row.put(fld.id(), fld.convert(text));
        }
    }

}
