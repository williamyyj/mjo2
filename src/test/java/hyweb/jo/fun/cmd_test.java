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

public class cmd_test extends JOTest {


    public void cmd_rows_text() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        List<JSONObject> rows = null;
        try {
            // proc.params().put("baphiqid", "UP0000208509");
            proc.params().put("baphiqId", "UP0000208509");
            rows = (List<JSONObject>) JOFunctional.exec2("cmd_rows", proc, "ps_file", "push_main_rows");
            for (JSONObject row : rows) {
                System.out.println(row);
            }

        } finally {
            proc.release();
        }
    }

    @Test
    public void cmd_row_text() throws Exception {
        JOProcObject proc = new JOProcObject(base);

        try {
            // proc.params().put("baphiqid", "UP0000208509");
            proc.params().put("rid", "4");
            JSONObject row = (JSONObject) JOFunctional.exec2("cmd_row", proc, "ps_file", "edit_row");
            System.out.println("---------------  cmd_row  test");
            System.out.println(row);

        } finally {
            proc.release();
        }
    }

}
