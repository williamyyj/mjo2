package hyweb.jo.fun.vf;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public class empty  implements IJOFunction<Boolean, JSONObject>{

    @Override
    public Boolean exec(JSONObject pool) throws Exception {
        String fv = pool.optString("$fv");
        return !(fv.length()==0) ;
    }
    
}
