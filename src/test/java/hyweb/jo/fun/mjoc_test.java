/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author william
 */
public class mjoc_test extends JOTest {

    @Test
    public void test_mjoc_rows() throws Exception {
        String src = "{baphiqid:'UP0000208509',ftype1:'D2015'}";
        JSONObject params = JOTools.loadString(src);
        JOProcObject proc = new JOProcObject(base);
        JSONObject wp = MJOBase.wp(proc, "ps_file", "push_main_rows", params);
        List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("mjoc_mspage", wp);
        try {

            for (JSONObject row : rows) {
                System.out.println(row);
            }

            System.out.println(proc.toString(4));

            String ejo = (String) proc.get(JOProcObject.p_request, "ejo", "");
            JSONObject jo = JOTools.decode_jo(ejo);

            System.out.println(jo.toString(4));
        } finally {
            proc.release();
        }
    }
}
