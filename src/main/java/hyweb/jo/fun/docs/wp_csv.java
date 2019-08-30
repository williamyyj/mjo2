package hyweb.jo.fun.docs;

import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOActorObject;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOExecute;
import hyweb.jo.util.JOFunctional;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author william
 */
public class wp_csv implements IJOFunction<Object, JOWPObject> {

    @Override
    public Object exec(JOWPObject wp) throws Exception {
        JOLogger.debug(wp.act());
        String style = wp.act().optString("style");
        if ("col".equals(style)) {
            return JOFunctional.exec("docs.wp_csv_col", wp);
        } else {
            return JOFunctional.exec("docs.wp_csv_row", wp);
        }
    }

    protected List<String> get_pcsv(JOWPObject wp) {
        List<String> data = (List<String>) wp.proc().opt("$csv");
        if (data == null) {
            data = new ArrayList<String>();
            wp.proc().put("$csv", data);
        }
        return data;
    }

    protected List<IJOField> getFields(JOWPObject wp, String fid) throws Exception {
        Object o = wp.act(); 
        if(o instanceof JOActorObject){
            JOActorObject  act = (JOActorObject) o;
            return (List<IJOField>) JOExecute.bfun("act_fields",act , act.opt(fid));
        } else {
            JSONObject act =  (JSONObject) o;
            return wp.metadata().getFields(act.optString(fid));
        }
       
    }

}
