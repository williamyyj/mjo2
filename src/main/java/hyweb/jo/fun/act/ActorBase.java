package hyweb.jo.fun.act;

import hyweb.jo.model.JOActorObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOPath;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author william
 *
 * $paramId
 */
public class ActorBase {

    public int foreach(List<JOActorObject> actors) {
        for (JOActorObject act : actors) {
            try {
                if (((Integer) JOFunctional.exec("act.execute", act)) < 0) {
                    return -1;
                }
            } catch (Exception ex) {
                JOPath.set(act.proc(), "$exception", act.toString() + ":::" + ex.getMessage());
                return -1;
            }
        }
        return 0; // 
    }

    public void proc_act_before(JOActorObject ao) {
        proc_item(ao, ao.getInParams());
    }

    public void proc_act_after(JOActorObject ao) {
        proc_item(ao, ao.getData());
    }

    protected void proc_item(JOActorObject ao, Object o) {
        if (o instanceof JSONObject) {
            proc_item_row(ao, (JSONObject) o);
        } else if (o instanceof Collection) {
            proc_item_collection(ao, (Collection) o);
        }
    }

    protected void proc_item_row(JOActorObject ao, JSONObject row) {

    }

    protected void proc_item_collection(JOActorObject ao, Collection rows) {

    }

}
