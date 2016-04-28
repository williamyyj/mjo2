package hyweb.jo.fun;

import hyweb.jo.JOConst;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import java.util.List;

/**
 * @author william 資料檢查 預設檢查 request parameter
 */
public class wp_before extends wp_base<Boolean> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        JSONObject act = wp.act();
        if (act.has(JOConst.before)) {
            List<IJOField> aFields = wp.metadata().getFields(wp.ref_string(JOConst.before));
            eval_row(wp.proc(), aFields, wp.p(), wp.pp());
        }
        return true; // 
    }

}
