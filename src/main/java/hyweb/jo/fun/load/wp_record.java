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
public class wp_record implements IJOFunction<List<JSONObject>, JOWPObject> {

    @Override
    public List<JSONObject> exec(JOWPObject wp) throws Exception {
        List<JSONObject> rows = new ArrayList<JSONObject>();
        JSONObject act = wp.act();
        if (act != null) {
            File fp = (File) wp.p().opt("fp");
            System.out.println(fp);
            String enc = act.optString("encoding");
            String lineSplit = act.optString("lineSplit", "\\r\\n");
            int min = act.optInt("min");
            String text = JOFileUtils.loadString(fp, enc);
            List<IJOField> eFields = wp.metadata().getFields(act.optString("fields"));
            proc_fields(eFields);
            String[] items = text.split(lineSplit);
            for (String item : items) {
                JSONObject row = proc_record(eFields, item, min);
                if (row != null) {
                    rows.add(row);
                }
            }
        }
        return rows;
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
                proc_field_value(row,fld,item);
            }
            return row ; 
        }
        return null;
    }

    private void proc_field_value(JSONObject row, IJOField fld,String item) {
        int ps = fld.cfg().optInt("offset");
        int pe = ps+ fld.cfg().optInt("len");
        if(item.length()>=pe){
            row.put(fld.id(), fld.convert(item.substring(ps,pe)));
        }
    }

}
