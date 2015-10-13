package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOStringType;

/**
 *
 * @author william
 */
@IAProxyClass(id = "field.obj")
public class JOOField extends JOBaseField<String> {

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new JOStringType();
    }

    @Override
    public boolean valid(JSONObject wp) throws Exception {
        JSONObject row = wp.optJSONObject("$");
        if (super.valid(wp)) {
            return true;
        }
        if(this.size()>0){
            //  處理資料過大問題
            Object o = getFieldValue(row) ;
            if(o instanceof String){
                String v = (String) o ; 
                v = (v.length()<=this.size()) ? v : v.substring(0,size());
                this.setFieldValue(row, v);
            }
        }
        setErrData(row, null);
        return false;
    }

}
