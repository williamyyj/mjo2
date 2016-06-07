package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOStringType;
import hyweb.jo.util.TextUtils;
import java.io.UnsupportedEncodingException;


/**
 * @author William
 */
@IAProxyClass(id = "field.string")
public class JOStringField extends JOBaseField<String> {

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new JOStringType();
    }

    @Override
    public String convert(Object o) {
        return (o != null) ? o.toString().trim() : null;
        //if (text != null) {
        //    return (text.length() <= this.size()) ? text : text.substring(0, size());
        //}
        //return text;
    }

    @Override
    public boolean valid(JSONObject wp) throws Exception {
        JSONObject row = wp.optJSONObject("$");
        JSONObject ref = wp.optJSONObject("$$");
        Object o = this.getFieldValue(row);
        if (o instanceof String && size() > 0) {
            try {
                //  處理資料過大問題
                TextUtils.fixStringSize(this, row);
            } catch (UnsupportedEncodingException ex) {
                setFieldValue(row, null); // 保證資料可匯入db
            }
        }
        if (super.valid(wp)) {
            return true;
        }
        setErrData(row, null);
        return false;
    }

}
