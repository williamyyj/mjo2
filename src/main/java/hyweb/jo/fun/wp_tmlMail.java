package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.ff.IJOFF;
import hyweb.jo.ff.JOFF;
import hyweb.jo.ff.ff_safe;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.MVELTemplate;
import java.util.Map;

/**
 *
 * @author william
 */
public class wp_tmlMail implements IJOFunction<Boolean, JOWPObject> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        JSONObject act = wp.act();
        JSONObject row = new JSONObject(wp.p());
        Map<String, IJOFF> mff = JOFF.init_ff(wp.proc(), wp.metadata());
        mff.put("safe", new ff_safe());
        row.put("$ff", mff);
        Object data = JOFunctional.exec(act.optString("$funId"), wp);
        data = (data==null) ? wp.p() : data ;
        row.put("data", data);
        MVELTemplate tmpl = new MVELTemplate(wp.proc().base(), act.optString("$tml"));
        String context = tmpl.processTemplate(row);
        row.put("context", context);
        wp.reset(row);
        JOFunctional.exec("wp_email",wp);
        return true;
    }

}
