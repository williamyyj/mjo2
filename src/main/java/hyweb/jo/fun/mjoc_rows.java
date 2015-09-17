package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.List;

/**
 * @author william
 */

public class mjoc_rows extends MJOBase   implements IJOFunction<List<JSONObject>, JSONObject> {

    @Override
    public List<JSONObject> exec(JSONObject wp) throws Exception {
        JSONObject mq = mq(wp);
        List<JSONObject> rows = (List<JSONObject>) db(wp).action(mq);
        List<IJOField> eFields = eval_fields(wp);
        for(JSONObject row : rows ){
            JOFunctional.exec2("meval",proc(wp), eFields,row,wp.opt("$$"));
        }
        return rows ; 
    }
    
    
    
}
