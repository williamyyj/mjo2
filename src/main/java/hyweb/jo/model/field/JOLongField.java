package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOLongType;

/**
 * @author William
 */
@IAProxyClass(id = "field.long")
public class JOLongField extends JOBaseField<Long> {

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new JOLongType();
    }

    @Override
    public boolean valid(JSONObject wp) throws Exception {
        JSONObject row = wp.optJSONObject("$");
        JSONObject ref = wp.optJSONObject("$$");
        Object v = getFieldValue(row);
        Long num = type.check(v, null);
        if (num != null) {
            setFieldValue(row, num);
            if (super.valid(wp)) {
                return true;
            }
        }
        setErrData(row, null);
        setFieldValue(row, num);
        return false;
    }

}
