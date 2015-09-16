package hyweb.jo.fun.model;



import hyweb.jo.IJOFunction;
import hyweb.jo.db.DB;
import hyweb.jo.db.DBCmd;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import hyweb.jo.JOProcConst;
import hyweb.jo.JOProcObject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author william
 */
public class FDOUpdate implements IJOFunction<Boolean, Object[]> {

    public Boolean exec(Object[] args) throws Exception {
        JOProcObject proc = (JOProcObject) args[0];
        String item = (String) args[1];
        String[] items = item.split("::");
        String metaId = items[0];
        String db_scope = (items.length > 1) ? items[1] : "db";
        String eval_scope = (items.length > 2) ? items[2] : "edit";
        JOMetadata metadata = proc.metadata(metaId);
        List<IJOField> eFields = metadata.getFieldsByScope(eval_scope);
        List<IJOField> dbFields = metadata.getFieldsByScope(db_scope);

        JSONObject ret = (JSONObject) args[2];
        JSONObject ref = (args.length > 3) ? (JSONObject) args[3] : new JSONObject();
        JOFunctional.exec2("meval", proc, eFields, ret,ref);
        try {
            JOFunctional.exec2("db_update",proc, dbFields,ret);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void main(String[] args) throws Exception {
        String base = "D:\\will\\work\\nb\\mwork\\src\\main\\webapp\\WEB-INF\\prj\\baphiq";
        JOProcObject proc = new JOProcObject(base);
        JSONObject row = JOTools.loadString("{\"baphiqId\":\"UP0000208509\",\"email\":\"williamyyj@tpe.hyweb.com.tw\",\"VerifyCode\":\"\",\"tel_ext\":\"2519\",\"mid\":\"3\",\"act\":\"edit\",\"tel_area\":\"02\",\"tel_num\":\"23956966\",\"mobile\":\"\"}");
        row.put("stype", 4);
        row.put("status", '2');
        row.put("mobile", "0916727036");
        JSONObject user = new JSONObject();
        user.put("email", "williamyyj1@tpe.hyweb.com.tw");
        String e1 = row.optString("email");
        String e2 = user.optString("email");
        System.out.println(e1.equals(e2));
        try {
            JOFunctional.exec2("model.FDOUpdate", proc, "ps_store", row, user);
        } finally {
            proc.release();
        }
    }

}
