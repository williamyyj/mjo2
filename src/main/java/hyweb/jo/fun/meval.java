package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.JOProcObject;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mvel2.MVEL;

/**
 *
 * @author william
 */
public class meval implements IJOFunction<Object, Object[]> {

    public Boolean exec(Object[] args) throws Exception {
        JOProcObject proc = (JOProcObject) args[0];
        List<IJOField> fields = (List<IJOField>) args[1];
        JSONObject ret = (JSONObject) args[2];
        JSONObject ref = (JSONObject) ((args.length > 3) ? args[3] : new JSONObject());
        Map<String, Object> m = new HashMap<String, Object>();
        Map<String, Object> msg = new HashMap<String, Object>();
        ret.m().put("_err", msg);
        m.put("$", ret.m());
        m.put("$$", ref.m());
        m.put("$p", proc);
        m.put("$proc", proc.m());
        m.put("$db", proc.db());
        m.put("$now", new Date());
        m.put("$f", JOFunctional.class);

        boolean flag = true;
         msg.put("ret", flag);
        for (IJOField fld : fields) {
            try {
                m.put("$fld", fld);
                if (fld.eval() != null) {
                    //Serializable compiled = MVEL.compileExpression(fld.eval(), ctx);
                   // Object o = MVEL.executeExpression(compiled, m);
                    Object o = MVEL.eval(fld.eval(), m);
                    if (o instanceof Boolean) {
                        boolean valided = (Boolean) o;
                        flag = flag && valided;
                        if (!valided) {
                            String id = fld.name().toLowerCase();
                            msg.put(id, false);
                        }
                        msg.put("ret", flag);
                    }
                }
            } catch (Exception e) {
                JOLogger.info("Can't eval  " + e.getMessage() + "\n" + fld.id() + "--->" + fld.eval());
            }
        }      
         ret.remove("_err");
        return flag;
    }


}
