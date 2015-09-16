package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public class act extends fbase implements IJOFunction<Object,Object[]>{

    @Override
    public Object exec(Object[] args) throws Exception {
        JOProcObject proc = (JOProcObject) args[0];
        String metaId = (String) args[1];
        String actId = (String) args[2];
        JSONObject ref = (JSONObject) ((args.length>3) ? args[3] : new JSONObject());
        String rt = (String) ((args.length>4) ? args[4] : "json" );
        JOMetadata metadata = proc.metadata(metaId);
        JSONObject acfg = proc.optJSONObject(actId);
         int ps = actId.lastIndexOf("_");
        String act = (ps > 0) ? actId.substring(ps + 1) : actId;
        JSONObject mq = mq(proc, acfg, act);
        return proc.db().action(mq);
    }
    
}
