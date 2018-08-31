package hyweb.jo.fun;

import hyweb.jo.JOConst;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import java.util.List;

/**
 * @author william 計算 欄位 檢查Data欄位
 */
public class wp_after extends wp_base<Boolean> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        System.out.println("===== wp_after");
        JSONObject act = wp.act();
        Object data = wp.opt("$data");
        data = data != null ? data : wp.proc().opt("$data") ;
        if (act.has(JOConst.after)) {
            List<IJOField> aFields = wp.metadata().getFields(wp.ref_string(JOConst.after));
            if (data instanceof JSONObject) {
                this.eval_row(wp.proc(), aFields, (JSONObject) data, wp.pp());
            } else if (data instanceof List) {
                this.eval_rows(wp.proc(), aFields, (List) data, wp.pp());
            }
        }
        System.out.println(data);
        return true;
    }

}
