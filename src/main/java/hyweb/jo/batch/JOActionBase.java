package hyweb.jo.batch;

import hyweb.jo.JOProcObject;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public abstract class JOActionBase implements IJOBatch {

    protected JSONObject params;
    protected JOProcObject proc;
    protected JOWPObject wp;
    private boolean isReference = false;

    public JOActionBase() {

    }

    @Override
    public void __init__() {
        if (params.has("$proc")) {
            proc = (JOProcObject) params.opt("$proc");
        } else {
            isReference = true;
            proc = new JOProcObject(params.optString("$base"));
        }
        wp = new JOWPObject(proc, params.optString("$metaId"), params.optString("$actId"));
    }

    @Override
    public void __before() {
    }

    @Override
    public void execute(JSONObject p) {
        this.params = p;
        try {
            __init__();
            __before();
            proc_main();
            __after();
        } catch (Exception e) {
            __error(e);
        } finally {
            __release();
        }
    }

    @Override
    public void __after() {
    }

    @Override
    public void __error(Exception e) {
        e.printStackTrace();
    }

    @Override
    public void __release() {
        if (!isReference) {
            JOLogger.debug("do release");
            proc.release();
        }
    }

    public abstract  void proc_main() throws Exception ;

}
