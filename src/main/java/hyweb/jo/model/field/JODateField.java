package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JODateType;
import java.util.Date;

@IAProxyClass(id = "field.date")
public class JODateField extends JOBaseField<Date> {

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new JODateType();
    }

    @Override
    public boolean valid(JSONObject wp) throws Exception {
        JSONObject row = wp.optJSONObject("$");
        JSONObject ref = wp.optJSONObject("$$");
        
        Object v = getFieldValue(row);
        System.out.println(v);
        JODateType dt = (JODateType) type;
        Date d = dt.check(v, ft());
        if (d != null) {
            setFieldValue(row, d);
            if (super.valid(wp)) {
                return true;
            }
        }
        setErrData(row, null);
        return false;
    }
}
