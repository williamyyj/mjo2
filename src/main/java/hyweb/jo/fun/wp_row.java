package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;

/**
 *
 * @author william
 * $act 
 * $cmd 
 * $eval 
 */
public class wp_row  implements IJOFunction<JSONObject, JOWPObject> {

    @Override
    public JSONObject exec(JOWPObject wp) throws Exception {
        JOFunctional.exec("wp_eval", wp);
        JSONObject mq = wp.mq();
        return (JSONObject) wp.proc().db().action(wp.mq());
    }
    
}