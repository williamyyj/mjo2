package hyweb.jo.util;

import hyweb.jo.org.json.JSONObject;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOPathTest {

    @Test
    public void test_set() {
        JSONObject jo = new JSONObject();
        JOPath.set(jo, "$fv:v_barcode", "xx");
        JOPath.set(jo, "$fv:m_barcode", "訊息");
        System.out.println(jo);
    }
}
