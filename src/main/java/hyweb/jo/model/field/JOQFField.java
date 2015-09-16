package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOStringType;

/**
 *
 * @author william
 * 查詢欄位
 */
@IAProxyClass(id = "field.qf")
public class JOQFField extends JOBaseField<String>{

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new JOStringType();
    }
}
