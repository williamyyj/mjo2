package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOStringType;

/**
 * @author william 查詢欄位
 *   查詢條件欄位
 *        "eval":"  ${=,name,type,alias} "   -->  動態欄位
 *       "eval":"and ( newId = ${cid,string} or oldId=${cid,string})"   靜態欄位
 */
@IAProxyClass(id = "field.qc")
public class JOQCField extends JOBaseField<String> {

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new JOStringType();
    }
}
