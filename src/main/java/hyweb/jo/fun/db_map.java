package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import java.util.List;


/**
 *
 * @author william
 */
public class db_map implements IJOFunction<JSONObject, JOProcObject>{

    @Override
    public JSONObject exec(JOProcObject proc) throws Exception {
        JSONObject m = new JSONObject();
        JSONObject params = proc.params();
        String fmt = "select  %s as l , %s as v from %s ";
        String sql = String.format(fmt, params.opt("$label"), params.opt("$value") , params.opt("$table"));
        List<JSONObject> rows = proc.db().rows(sql);
        for(JSONObject row : rows){
            m.put(row.optString("l"), row.optString("v"));
        }
        return m;
    }
    
}
