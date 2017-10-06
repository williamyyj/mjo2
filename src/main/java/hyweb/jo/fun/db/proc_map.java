package hyweb.jo.fun.db;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import java.util.List;


/**
 *      params.put("$cols", "newId k, dbo.ufn_baphiqId(dataId) v , * ");
 *      params.put("$table", "psRetail");
 *      params.put("$cond", " where len(newId)=6");
 * @author william
 */
public class proc_map implements IJOFunction<JSONObject, JOProcObject> {


    @Override
    public JSONObject exec(JOProcObject proc) throws Exception {
        JSONObject m = new JSONObject();
        JSONObject params = proc.params();     
        String fmt = "select  %s from %s %s ";
        String sql = String.format(fmt, params.optString("$cols"), params.optString("$table") , params.optString("$cond"));
        List<JSONObject> rows = proc.db().rows(sql);
        for(JSONObject row : rows){
            m.put(row.optString("k"), row);
        }
        return m;
    }
    
}
