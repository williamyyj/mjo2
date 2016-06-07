package hyweb.jo.model.field;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import static hyweb.jo.JOTest.base;
import hyweb.jo.fun.MJOBase;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOFieldUtils;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.DateUtil;
import hyweb.jo.util.JOTools;
import java.util.Calendar;
import java.util.Date;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author william
 */
public class v_intTest extends JOTest {
    @Test
    public void testExec() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        String fld_src = " {\"id\":\"v_purchaseqty\",\"dt\":\"int\",\"label\":\"銷售量期異常\"}";
        String p_src = "{}";
        JSONObject p = JOTools.loadString(p_src);
        p.put("$err", new JSONObject());
        p.put("$msg", new JSONObject());
        p.put("$srow", new JSONObject());
        JSONObject wp = MJOBase.wp(proc, "ps_saless_log", null, p);
        JSONObject fld_cfg = JOTools.loadString(fld_src);
        IJOField fld = JOFieldUtils.newInstance(fld_cfg);
        JSONObject ref = wp.optJSONObject("$$");


        p.put("$err", new JSONObject());
        p.put("$msg", new JSONObject());
        p.put("$srow", new JSONObject());
        p.put("purchaseqty", 5);

        System.out.println(p);
        try {
           boolean ret = fld.valid(wp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(p);

    }
}
