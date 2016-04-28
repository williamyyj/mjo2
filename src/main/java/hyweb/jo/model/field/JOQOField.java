package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOStringType;

/**
 * @author william
 *    給分頁使用 決定排序 
 *   MS-SQL  要設定 order by 
 * 
 */    
@IAProxyClass(id = "field.qo")
public class JOQOField extends JOBaseField<String> {

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new JOStringType();
    }
    
}
