package hyweb.jo.fun.dao;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public class wp_row_pk extends WPDao implements IJOFunction<JSONObject, JOWPObject> {

    @Override
    public JSONObject exec(JOWPObject wp) throws Exception {
         return (JSONObject) proc_dao_cmd(wp, "model.FSQLPK", "row");
    }
    
}
