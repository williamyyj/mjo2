package hyweb.jo.model.field;


import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JODateType;
import java.util.Date;

@IAProxyClass(id="field.date")
public class JODateField extends JOBaseField<Date> {
    
@Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
         type = new JODateType();
    }
    
}
