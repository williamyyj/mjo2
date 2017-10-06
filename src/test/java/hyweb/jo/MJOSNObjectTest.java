package hyweb.jo;

import org.junit.Test;

/**
 *
 * @author william
 */
public class MJOSNObjectTest {

    @Test
    public void test_string() {
        MJSONObject m = new MJSONObject();
        m.put("xxx", "好人");
        m.put("yyy", "老頭子");
        m.put("x", "@{xxx}----@{yyy}");
        System.out.println(m.opt("x"));
    }
}
