/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.model;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import static hyweb.jo.JOTest.base;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.util.List;
import org.junit.Test;

/**
 * @author william
 */

public class JOCmdActionTest extends JOTest {

    @Test
    public void test_action() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        //JSONObject p = JOTools.loadString("{user:thomas,password:111111}");
        //JSONObject p = JOTools.loadString("{cid:09400098}");
        JSONObject p = JOTools.loadString("{cid:'S-004159', cityId:''}");
        try {
            JOWPObject wp = new JOWPObject(proc,"ps_paper","query",p,null);
            List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("wp_page", wp );
            for(JSONObject row : rows){
                System.out.println(row);
            }
        } finally {
            proc.release();
        }
    }
    
}
