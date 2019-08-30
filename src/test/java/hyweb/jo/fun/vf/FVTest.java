/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.vf;

import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOUtils;
import java.io.IOException;
import java.util.regex.Pattern;
import junit.framework.Assert;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author william
 */
public class FVTest {
    

    @Test
    public void test_regex() throws IOException, Exception {
        Pattern p = Pattern.compile("^\\d{7}$");
        String fv = "1234567";
        System.out.println(p.matcher(fv).find());
        JSONObject pool = JOUtils.line("$fld:{id:xx,dt:string,valid:'@vf.regex',pattern:'^[0-9]{7}$'}");
        pool.put("$fv", fv);
        System.out.println(pool);
        Assert.assertEquals(true,JOFunctional.exec("vf.regex",pool));
    }
    
}
