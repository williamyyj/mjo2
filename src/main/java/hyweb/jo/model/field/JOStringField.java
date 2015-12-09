package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOStringType;

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

}
