package hyweb.jo.fun.dao;

import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import java.util.List;
import org.mvel2.MVEL;

/**
 * @author william 
 *   正規化資料
 */
public class wp_eval extends WPBase implements IJOFunction<Boolean, JOWPObject> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        List<IJOField> flds = getField(wp, "$eval");
        JSONObject pool = getVarPool(wp);
        for (IJOField fld : flds) {
            try {
                Object fv = fld.getFieldValue(wp.p());
                fv = fv == null ? "" : fv ;  //   防呆這個機制可能有問題最好 field 
                pool.put("$fv", fv);
                pool.put("$fld", fld.cfg());
                String eval = fld.cfg().optString("eval");
                if (eval.startsWith(":=")) {
                    Object v = MVEL.eval(eval.substring(2), pool);
                    fld.setFieldValue(wp.p(), v);
                } else if (eval.length() > 0) {
                    MVEL.eval(eval, pool);
                }
            } catch (Exception e) {
                JOLogger.debug("wp_eval fail : " + fld.cfg(), e);
            }
        }
        return true;
    }

}
