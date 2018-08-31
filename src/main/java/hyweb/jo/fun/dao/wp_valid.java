package hyweb.jo.fun.dao;

import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOPath;
import java.util.List;

/**
 * @author william
 */
public class wp_valid extends WPBase implements IJOFunction<JSONObject, JOWPObject> {

    @Override
    public JSONObject exec(JOWPObject wp) throws Exception {
        JSONObject valid = new JSONObject();
        List<IJOField> flds = getField(wp,"$valid");
        JSONObject pool = getVarPool(wp);
        valid.put("$ret", true);
        for (IJOField fld : flds) {
            try {

                if (!proc_field_valid(fld, pool)) {
                    JOPath.serial(valid, fld.getFieldName()+ "_msg",  fld.cfg().opt("msg"));
                    valid.put("$ret", false);
                }
            } catch (Exception e) { // 非正常執行跳出
                JOLogger.debug("wp_valid fail : " + fld.cfg(), e);
                valid.put("$exception", e.getMessage());
                valid.put("$ret", false);
                break;
            }
        }
        return valid;
    }

    private Boolean proc_field_valid(IJOField fld, JSONObject pool) throws Exception {
        Object fv = fld.getFieldValue(pool.optJSONObject("$")); // field value 
        pool.put("$fv", fv);
        pool.put("$fld", fld.cfg());
        try {
            String valid = fld.cfg().optString("valid");
            String funId = valid.charAt(0) != '@' ? "vf.mvel" : valid.substring(1);
            return (Boolean) JOFunctional.exec(funId, pool);   
        } finally {
            pool.remove("$fv");
            pool.remove("$fld");
        }
    }
    
}
