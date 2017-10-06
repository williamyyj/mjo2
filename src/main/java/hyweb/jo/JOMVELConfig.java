package hyweb.jo;

import hyweb.jo.log.JOLogger;

/**
 *
 * @author william
 */
public class JOMVELConfig extends JOConfig {

    public JOMVELConfig(String base, String id) {
        super(base, id);
    }

    @Override
    protected void init_params() {
        eval();
    }

    public void eval() {
        JOLogger.debug("===== mvel config  eval ");
    }

}
