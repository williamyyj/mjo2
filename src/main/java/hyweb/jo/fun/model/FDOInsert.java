package hyweb.jo.fun.model;



import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import hyweb.jo.JOProcObject;
import java.util.List;
/**
 * @author william
 */
public class FDOInsert implements IJOFunction<Boolean, Object[]> {

    public Boolean exec(Object[] args) throws Exception {
        JOProcObject proc = (JOProcObject) args[0];
        String item = (String) args[1];
        String[] items = item.split("::");
        String metaId =  items[0] ;
        String db_scope =(items.length>1)? items[1] : "db";
        String eval_scope =(items.length>2)? items[2] : "add";       
        
        JSONObject ret = (JSONObject) args[2];
        JSONObject ref = (args.length > 3) ? (JSONObject) args[3] : new JSONObject();
        JOMetadata metadata = proc.metadata(metaId);
        List<IJOField> eFields = metadata.getFieldsByScope(eval_scope);
        List<IJOField> dbFields = metadata.getFieldsByScope(db_scope);
        JOFunctional.exec2("meval",proc, eFields,ret);
       // prepared(proc.db(), eFields, ret.m(), ref.m());
        JOLogger.debug(ret.toString(4));
        try {
            JOFunctional.exec2("db_insert",proc, dbFields,ret);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static void main(String[] args) throws Exception {
        String base = "D:\\will\\work\\nb\\mwork\\src\\main\\webapp\\WEB-INF\\prj\\baphiq";
        String jo_src = "{\"passwd\":\"1q2w3e4r\",\"VerifyCode\":\"0029\",\"uname\":\"蔡德謄\",\"tel_ext\":\"1234\",\"cid\":\"00208509\",\"tel_num\":\"123456789\",\"baphiqid\":\"UP0000208509\",\"email\":\"williamyyj@tpe.hyweb.com.tw\",\"stype\":\"4\",\"mid\":\"3\",\"passwd1\":\"1q2w3e4r\",\"act\":\"add\",\"dataid\":\"00208509\",\"tel_area\":\"02\",\"cname\":\"英明貿易股份有限公司\",\"mobile\":\"\"}";
        JOProcObject proc = new JOProcObject(base);
        JSONObject jo = JOTools.loadString(jo_src);
        try {
            //JOFunctional.exec2("model.FDOInsert", proc, "ps_file::db::add", jo);
            JOFunctional.exec2("model.FDOInsert", proc, "ps_store", jo);
        } finally {
            proc.release();
        }
    }

}
