package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOStringType;

/**
 * @author william 視表 or 資料表欄位
 */
@IAProxyClass(id = "field.table")
public class JOTBField extends JOBaseField<String> {

    public JOTBField() {

    }

    public JOTBField(String id, String name) {
        cfg = new JSONObject();
        cfg.put("dt", "table");
        cfg.put("id", id);
        cfg.put("name", name);
        type = new JOStringType();
    }

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new JOStringType();
    }

}
