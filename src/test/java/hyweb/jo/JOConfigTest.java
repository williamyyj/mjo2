package hyweb.jo;

import hyweb.jo.org.json.JSONObject;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOConfigTest {

    @Test
    public void loadTest() {
        JOConfig cfg = new JOConfig(JOTest.base, "db");
        JSONObject params = cfg.params();
        System.out.println(params.toString(4));
        System.out.println(cfg.pcfg().toString(4));
    }
    
}
