package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JODateType;
import java.text.SimpleDateFormat;
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
        JODateType dt = (JODateType) type;
        Date d = (ft() != null) ? dt.check(v, ft()) : dt.check(v);
        if (d != null) {
            setFieldValue(row, d);
            if (super.valid(wp)) {
                return true;
            }
        }
        setErrData(row, null);
        return false;
    }

    @Override
    public String getFieldText(JSONObject row) {
        JODateType dt = (JODateType) type;
        Object v = getFieldValue(row);
        if (ft() != null) {
            SimpleDateFormat sdf = new SimpleDateFormat(ft());
            try {
                return sdf.format(v);
            } catch (Exception e) {
                JOLogger.warn(id() + " can't cast [ " + ft() + "+]:" + v);
            }
        }
        return (v != null) ? v.toString() : "";
    }

    @Override
    public Date convert(Object o) {
        JODateType dt = (JODateType) type;
        return (ft() != null) ? dt.check(o, ft()) : dt.check(o);
    }
}
