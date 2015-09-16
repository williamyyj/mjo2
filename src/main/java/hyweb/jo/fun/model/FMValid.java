package hyweb.jo.fun.model;



import hyweb.jo.IJOFunction;
import hyweb.jo.model.IJOField;
import hyweb.jo.JOProcObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mvel2.MVEL;

/**
 * @author william
 */
public class FMValid implements IJOFunction<Boolean, Object[]> {

    public Boolean exec(Object[] args) throws Exception {
        JOProcObject proc = (JOProcObject) args[0];
        String mid = (String) args[1];
        String scope = (String) args[2];
        List<IJOField> fields = proc.getFields(mid,scope);
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("$db", proc.db());
        m.put("$$",proc.optJSONObject("$row").m() ); //目的
        m.put("$", proc.params().m());  //來源
        return exec(m,fields);
    }

    public boolean exec(Map<String, Object> m, List<IJOField> fields) throws Exception {
        boolean ret = true;
        Map<Object, String> wp = (Map<Object, String>) m.get("$");
        for (IJOField fld : fields) {
            boolean valid = valid(fld, m);
            if (!valid) {
                wp.put("err_" + fld.name(), fld.label());
            }
            ret = ret && valid;
        }
        wp.put("ret", String.valueOf(ret));
        return ret;
    }
    
     public  boolean valid(IJOField fld, Map<String,Object> m) {
        String eval_string = fld.eval();
        if (eval_string != null) {
            return(Boolean) MVEL.eval(eval_string, m);
        }
        return false;
    }

}
