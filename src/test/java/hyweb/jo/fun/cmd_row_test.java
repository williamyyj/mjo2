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
import java.util.List;
import org.junit.Test;


public class cmd_row_test extends JOTest {
    @Test
    public void exec_text() throws Exception{
        JOProcObject proc = new JOProcObject(base);
       List<JSONObject> rows = null;
        try {
          // proc.params().put("baphiqid", "UP0000208509");
           proc.params().put("baphiqId", "UP0000208509");
           rows = (List<JSONObject>) JOFunctional.exec2("cmd_rows", proc,"ps_file","push_main_rows");
           for(JSONObject row : rows){
               System.out.println(row);
           }
           

        } finally {
            proc.release();
        }
    }
}
