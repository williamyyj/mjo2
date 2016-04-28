/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.JOProcObject;
import static hyweb.jo.JOTest.base;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import org.junit.Test;

/**
 *
 * @author william
 */
public class combo_test {

    @Test
    public void test_mspage() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        try {
            JSONObject wp = MJOBase.wp(proc, "ws_file", "query", new JSONObject());
            JOFunctional.exec("combo", wp);
            System.out.println(proc.opt("$mtype"));
        } finally {
            proc.release();
        }
    }
    
}
