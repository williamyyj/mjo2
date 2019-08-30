package hyweb.jo.fun;


import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.DateUtil;
import hyweb.jo.util.JOFunctional;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mvel2.MVEL;

/**
 *
 * @author william
 */
public class proc_eval implements IJOFunction<Integer, Object[]> {

    @Override
    public Integer exec(Object[] p) throws Exception {
        JOProcObject proc = (JOProcObject) p[0];
        List<IJOField> eFields = (List<IJOField>) p[1];
        JSONObject row = (JSONObject) p[2];
        JSONObject ref = (JSONObject) ((p.length > 3) ? p[3] : new JSONObject());
        return exec(proc, eFields, row, ref);
    }

    private Integer exec(JOProcObject proc, List<IJOField> eFields, JSONObject row, JSONObject ref) {
        Map<String, Object> m = eval_pool(proc, row, ref);

        for (IJOField fld : eFields) {
            m.put("$fld", fld);
            m.put("$fv", fld.getFieldValue(row));
            m.put("$fn", fld.id());
            try {
                Object o = MVEL.eval(fld.eval(), m);
            } catch (Exception e) {
                JOLogger.error("Can't eval : " + fld.id() +":::"+fld.eval()+":::"+ fld.getFieldValue(row));
            }
        }
        return 0;
    }

    private Map<String, Object> eval_pool(JOProcObject proc, JSONObject row, JSONObject ref) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("$", row);
        m.put("$$", ref);
        m.put("$proc", proc);
        m.put("$db", proc.db());
        m.put("$now", new Date());
        m.put("$f", JOFunctional.class);
        m.put("$du", DateUtil.class);
        return m;
    }

}
