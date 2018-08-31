package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;

/**
 *
 * @author william $act $cmd $eval
 */
public class wp_row implements IJOFunction<JSONObject, JOWPObject> {

    @Override
    public JSONObject exec(JOWPObject wp) throws Exception {
        JOFunctional.exec("wp_eval", wp);
        JSONObject mq = wp.mq();
        JSONObject data = (JSONObject) wp.proc().db().action(wp.mq());
        wp.proc().put( wp.act().optString("$dataId","$data"), data);
        return data;
    }

}
