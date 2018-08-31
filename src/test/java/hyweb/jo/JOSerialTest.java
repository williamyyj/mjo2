package hyweb.jo;

import hyweb.jo.org.json.JOSerial;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOSerialTest {

    @Test
    public void test_all() {
        JOSerial js = new JOSerial();
        js.add("1,2,5,4,9").add("").add("5");
        System.out.println(js);
    }
    
}
