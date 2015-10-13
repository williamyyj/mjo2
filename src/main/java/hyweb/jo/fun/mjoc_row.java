package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.List;

/**
 * @author william
 */
public class mjoc_row extends MJOBase implements IJOFunction<JSONObject, JSONObject> {

    @Override
    public JSONObject exec(JSONObject wp) throws Exception {
        JSONObject mq = mq(wp);
        JSONObject row = (JSONObject) db(wp).action(mq);
        if (row != null) {
            List<IJOField> eFields = eval_fields(wp);
            JOFunctional.exec2("meval", proc(wp), eFields, row, wp.opt("$$"));
        }
        return row;
    }

}
