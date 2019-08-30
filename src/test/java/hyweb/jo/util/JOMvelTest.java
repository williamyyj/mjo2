package hyweb.jo.util;

import hyweb.jo.org.json.JSONObject;
import java.lang.reflect.Method;
import junit.framework.Assert;
import org.junit.Test;
import org.mvel2.MVEL;

/**
 *
 * @author william
 */
public class JOMvelTest {

    @Test
    public void test_mvel() throws Exception {
        JSONObject pool = new JSONObject();
        JSONObject p = JOTools.loadString("{a:x,b:y}");
        pool.put("$", p);
        pool.put("$proc", new JSONObject());
        JOPath.set(pool, "$proc:@", new JSONObject());
        
        Method fn = JOFunctional.class.getMethod("fn", String.class, Object.class);
        String eval = "  $['name']=5 ; $fn('length',$fv)==0 || $fn('cast.date',$fv) != null ";
        pool.put("$fn", fn);
        pool.put("$f", JOFunctional.class);

        pool.put("$fv", JSONObject.NULL);
        Assert.assertEquals("==== check mevl null fail ", true, MVEL.eval(eval, pool));

        pool.put("$fv", "");
        Assert.assertEquals("==== check mevl empty fail ", true, MVEL.eval(eval, pool));

        pool.put("$fv", "0551231");
        Assert.assertEquals("==== check mevl yyyMMdd fail ", true, MVEL.eval(eval, pool));

        pool.put("$fv", "xxyyzz");
        Assert.assertEquals("==== check mevl yyyyMMdd  fail ", false, MVEL.eval(eval, pool));
        
        System.out.println(pool);
    }
}
