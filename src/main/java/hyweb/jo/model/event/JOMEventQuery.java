package hyweb.jo.model.event;

import hyweb.jo.JOProcObject;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.model.JOWorkData;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOPath;
import java.util.List;
/**
 * @author william
 */
public class JOMEventQuery extends JOMEventBase {

    public JOMEventQuery() {

    }

    @Override
    public void execute(JOProcObject proc) {
        try {
            JSONObject p = proc.params();
            String metaId =p.optString("$metaId");
            String eventId = p.optString("$eventId", "query");
            JSONObject event =  proc.wMeta().event(metaId,eventId);
            String funId = event.optString("$funId","wp_page");
            JOWPObject wp = new JOWPObject(proc,metaId,eventId,p,null);
            JOPath.set(proc.wData(), "$data:ret", JOFunctional.exec(funId,wp));
            proc.wData().put("ret", JOFunctional.exec(funId,wp));
  
        } catch (Exception ex) {

        }
    }

}
