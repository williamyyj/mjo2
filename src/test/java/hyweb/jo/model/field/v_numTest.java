/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.model.field;

import hyweb.jo.JOProcObject;
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
public class v_numTest {
       @Test
    public void f_num_test() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        String fld_src = "{\"id\":\"v_purchaseqty\",\"dt\":\"string\",\"eval\":\"$f.num($fv)>0\",\"label\":\"銷售數量異常\",\"size\":50},";
        String p_src = "{}";
        JSONObject p = JOTools.loadString(p_src);
        p.put("$err", new JSONObject());
        p.put("$msg", new JSONObject());
        JSONObject wp = MJOBase.wp(proc, "ps_saless_log", null, p);
        JSONObject fld_cfg = JOTools.loadString(fld_src);
        IJOField fld = JOFieldUtils.newInstance(fld_cfg);
        JSONObject ref = wp.optJSONObject("$$");
        System.out.println(p);
        boolean ret = fld.valid(wp);

        Assert.assertEquals(false, ret);
        Assert.assertEquals(fld.label(), p.optJSONObject("$msg").opt(fld.id()));
        Assert.assertEquals(false, p.optJSONObject("$err").optBoolean(fld.id()));
        System.out.println(p);

        p.put("$err", new JSONObject());
        p.put("$msg", new JSONObject());
        p.put("purchaseqty", "1000");
        System.out.println(p);
        try {
            ret = fld.valid(wp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(p);

        p.put("$err", new JSONObject());
        p.put("$msg", new JSONObject());
        p.put("purchaseqty", "A123456789B123456789C123456789D123456789E123456789F123456789");

        System.out.println(p);
        try {
            ret = fld.valid(wp);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(p);

    }
}
