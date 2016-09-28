/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DBCmd;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.FJO2Map;
import hyweb.jo.util.JOCache;

/**
 *
 * @author william
 */
public class loadm implements IJOFunction<Object, Object[]> {

    /**
     * @param  args   proc , mapId , return type 
     * @return  json (map)
     * @throws Exception 
     */
    @Override
    public Object exec(Object[] args) throws Exception {
        JOProcObject proc = (JOProcObject) args[0];
        String mapId = (String) args[1];
        String rt = (String) (args.length > 2 ? args[2] : "json");
        JSONObject m = JOCache.load(proc.base() + "/combo", mapId);
        String type = m.optString("$type");
        if ("db".equals(type)) {
            return proc_db_map(proc, m, rt);
        } else {
            return proc_static_map(m, rt);
        }
    }



    private Object proc_static_map(JSONObject m, String rt) throws Exception {
        JSONObject data = m.optJSONObject("$data");
        if ("json".equals(rt)) {
            return data;
        } else  {
            return FJO2Map.toMap(data);
        }
    }

    private Object proc_db_map(JOProcObject proc, JSONObject m, String rt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
