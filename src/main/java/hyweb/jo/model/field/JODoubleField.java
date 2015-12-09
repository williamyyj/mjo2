/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JODoubleType;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author William
 */
@IAProxyClass(id = "field.double")
public class JODoubleField extends JOBaseField<Double> {

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new JODoubleType();
    }

    @Override
    public boolean valid(JSONObject wp) throws Exception {
        JSONObject row = wp.optJSONObject("$");
        Object v = getFieldValue(row);
        Number num = check(v);
        if (num != null) {
            setFieldValue(row, num);
            if (super.valid(wp)) {
                return true;
            }
        }
        setErrData(row, null);
        return false;
    }

    public Number check(Object v) {
        Number num = null;
        JODoubleType dt = (JODoubleType) type;
        if (ft() != null) {
            num = dt.check(v, ft());
        }
        if (num == null) {
            num = dt.check(v);
        }
        return num;
    }

    @Override
    public String getFieldText(JSONObject row) {
        JODoubleType dt = (JODoubleType) type;
        Object v = getFieldValue(row);
        if (ft() != null) {       
            try {
                NumberFormat nf = new DecimalFormat(ft());
                return nf.format(v);
            } catch (Exception e) {
                  JOLogger.warn(id()+" can't cast [ "+ft()+"+]:" + v);
            }
        }
        return (v != null) ? v.toString() : "";
    }
    
    
    @Override
    public Double convert(Object o) {
        JODoubleType dt = (JODoubleType) type;
        return (ft() != null) ? dt.check(o, ft()) : dt.check(o);
    }

}
