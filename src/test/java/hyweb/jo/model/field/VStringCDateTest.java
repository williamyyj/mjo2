package hyweb.jo.model.field;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
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
public class VStringCDateTest extends JOTest {

    @Test
    public void testExec() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        String fld_src = " {\"id\":\"v_purchasedt\",\"dt\":\"strcdate\",\"eval\":\"$f.rcdate($fv,$$.?pyear,$$.?now)\",\"label\":\"銷售日期異常\",\"size\":50}";
        String p_src = "{}";
        JSONObject p = JOTools.loadString(p_src);
        p.put("$err", new JSONObject());
        p.put("$msg", new JSONObject());
        JSONObject wp = MJOBase.wp(proc, "ps_saless_log", null, p);
        JSONObject fld_cfg = JOTools.loadString(fld_src);
        IJOField fld = JOFieldUtils.newInstance(fld_cfg);
        JSONObject ref = wp.optJSONObject("$$");
        ref.put("now", new Date());
        ref.put("pyear", DateUtil.add_date(new Date(), Calendar.YEAR, -1));
        System.out.println("----- null ");
        System.out.println(p);
        System.out.println(fld.getClass());
        boolean ret = fld.valid(wp);
        Assert.assertEquals(false, ret);
        Assert.assertEquals(fld.label(), p.optJSONObject("$msg").opt(fld.id()));
        Assert.assertEquals(false, p.optJSONObject("$err").optBoolean(fld.id()));
        System.out.println(p);

        System.out.println("----- yyyMMdd");
        p.put("$err", new JSONObject());
        p.put("$msg", new JSONObject());
        p.put("purchasedt", "1050101");
        System.out.println(p);
        try {
            ret = fld.valid(wp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(p);

        System.out.println("----- yyy/MM/dd");
        p.put("$err", new JSONObject());
        p.put("$msg", new JSONObject());
        p.put("purchasedt", "105/01/01");
        System.out.println(p);
        try {
            ret = fld.valid(wp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(p);

        System.out.println("----- date");
        p.put("$err", new JSONObject());
        p.put("$msg", new JSONObject());
        p.put("purchasedt", DateUtil.newInstance(2016, 1, 1));
        System.out.println(p);
        try {
            ret = fld.valid(wp);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(p);

        System.out.println("----- yyy/m/d");
        p.put("$err", new JSONObject());
        p.put("$msg", new JSONObject());
        p.put("purchasedt", "105/5/1");
        System.out.println(p);
        try {
            ret = fld.valid(wp);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(p);
        
          System.out.println("----- yyy/m/d");
        p.put("$err", new JSONObject());
        p.put("$msg", new JSONObject());
        p.put("purchasedt", "105/3/25");
        System.out.println(p);
        try {
            ret = fld.valid(wp);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(p);

        
                  System.out.println("----- yyyy/mm/dd");
        p.put("$err", new JSONObject());
        p.put("$msg", new JSONObject());
        p.put("purchasedt", "2016/05/01");
        System.out.println(p);
        try {
            ret = fld.valid(wp);

        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(p);

    }

}
