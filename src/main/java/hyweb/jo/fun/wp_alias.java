package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import java.util.List;

/**
 *
 * @author william
 */
public class wp_alias implements IJOFunction<JSONObject, JOWPObject> {

    @Override
    public JSONObject exec(JOWPObject wp) throws Exception {
        JSONObject row = wp.p();
        JSONObject nrow = new JSONObject();
        List<IJOField> flds = wp.metadata().getFields();
        for (IJOField fld : flds) {
            String alias = fld.cfg().optString("alias");
            if (!"".equals(alias)) {
                nrow.put(fld.id(), row.opt(alias));
            }
        }
        return nrow;
    }

}
