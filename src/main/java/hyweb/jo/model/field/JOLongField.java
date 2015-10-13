/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOLongType;

/**
 * @author William
 */

@IAProxyClass(id="field.long")
public class JOLongField extends JOBaseField<Long> {

    @Override
     public void __init__(JSONObject cfg) throws Exception  {
        super.__init__(cfg);
        type = new JOLongType();
    }
     
         @Override
    public boolean valid(JSONObject wp) throws Exception {
        JSONObject row = wp.optJSONObject("$");
        Object v = getFieldValue(row);
        Long num = type.check(v, null);
        if (num != null) {
            setFieldValue(row, num);
            if (super.valid(wp)) {
                return true;
            }
        }
        setErrData(row, null);
        return false;
    }
    
}
