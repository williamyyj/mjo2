/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOStringType;


/**
 *
 * @author william
 */
@IAProxyClass(id = "field.combo")
public class JOComboField extends JOBaseField<String> {

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new JOStringType();
    }

    @Override
    public boolean valid(JSONObject wp) throws Exception {
        JSONObject row = wp.optJSONObject("$");
        Object v = getFieldValue(row);
        if (v != null) {
            JSONObject m = cfg().optJSONObject("$m");
            setFieldValue(row, m.opt(v.toString()));
        }
        return true;
    }

    @Override
    public String getFieldText(JSONObject row) {
        Object v = row.opt(getFieldName());
        if (v != null) {
            JSONObject m = cfg().optJSONObject("$m");
            return  m.optString(v.toString());
        }
        return "";
    }

}
