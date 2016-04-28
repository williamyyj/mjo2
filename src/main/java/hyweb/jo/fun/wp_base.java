package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.List;

/**
 *
 * @author william
 * @param <E>
 */
public abstract class wp_base<E> implements IJOFunction<E,JOWPObject> {

    protected void eval_row(JOProcObject proc, List<IJOField> eFields, JSONObject row, JSONObject ref) throws Exception {
       // JOLogger.debug("===== eval_row before : "+row);
        JOFunctional.exec2("fb_eval", proc,eFields,row,ref);
       // JOLogger.debug("===== eval_row after   : "+row);
    }

    protected void eval_rows(JOProcObject proc, List<IJOField> eFields, List<JSONObject> rows, JSONObject ref) throws Exception {
        for(JSONObject row : rows){
            eval_row(proc,eFields,row,ref);
        }
    }
}
