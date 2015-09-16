package hyweb.jo.fun.model;



import hyweb.jo.IJOFunction;
import hyweb.jo.JOConst;
import hyweb.jo.db.DBCmd;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.JOProcObject;
import java.util.List;

/**
 * @author william
 */
public class FMLoadRow implements IJOFunction<JSONObject, Object[]> {

    public JSONObject exec(Object[] args) throws Exception {
        JOProcObject proc = (JOProcObject) args[0];
        String mid = (String) args[1];
        String scope = (String) args[2];
        //String id =  (String) ( args.length>2 ? args[2]:"db");
        return exec(proc, mid, scope);
    }

    public JSONObject exec(JOProcObject proc, String mid, String scope) throws Exception {
        List<IJOField> fields = proc.getFields(mid, scope);
        String sql = (String) JOFunctional.exec("model.FSQLQuery", fields);
        JSONObject qr = new JSONObject(proc.params().m());
        qr.put(JOConst.cmd, sql);
        qr.put(JOConst.act, "row");
        JSONObject mq = DBCmd.parser_cmd(proc.db(), qr);
        JSONObject row = (JSONObject) proc.db().action(mq);
        proc.put("$row", row);
        return row;
    }

    public static void main(String[] args) throws Exception {
        String base = "D:\\will\\work\\nb\\mwork\\src\\main\\webapp\\WEB-INF\\prj\\baphiq";
        JOProcObject proc = new JOProcObject(base);
        JOMetadata metadata = new JOMetadata(base,"ps_file");
        proc.params().put("fgroup", "UP0000208509");
        List<IJOField> fields = metadata.getFieldsByScope("list");
        try {
               JSONObject row =  (JSONObject) JOFunctional.exec2("model.FMLoadRow",proc,"ps_file","list");
               System.out.println(row);
        } finally {
            proc.release();
        }
    }

}
