package hyweb.jo.batch;

import hyweb.jo.JOConfig;
import hyweb.jo.JOProcObject;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;


/**
 * @author william
 */
public abstract class JOBatch implements IJOBatch {

    protected JOProcObject proc;
    protected String cfgId;
    protected String metaId;
    protected String base;
    protected JSONObject cfg;

    public JOBatch(String base, String metaId, String cfgId) {
        this.base = base;
        this.cfgId = cfgId;
        this.metaId = metaId;
    }

    @Override
    public void __init__() {
        proc = new JOProcObject(base);
        if (cfgId != null) {
            cfg = new JOConfig(base, cfgId).params();
        }
        JOLogger.debug("===== cfg : " + cfg);
    }

    @Override
    public void __before() {

    }

    @Override
    public void execute(JSONObject p) {
        try {
            __init__();  // 系統參數設定
            if (p != null) {
                proc.params().putAll(p);
            }
            __before();
            process(p);
            __after();
        } catch (Exception e) {
            e.printStackTrace();
            __error(e);
        } finally {
            __release();
        }
    }

    @Override
    public void __after() {

    }

    @Override
    public void __release() {
        if (proc != null) {
            proc.release();
        }

    }

    public abstract void process(JSONObject p) throws Exception;

}
