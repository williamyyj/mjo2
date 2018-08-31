package hyweb.jo.fun.db;

import hyweb.jo.JOProcObject;
import static hyweb.jo.JOTest.base;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.List;
import org.junit.Test;

/**
 * @author william
 */
public class test {

    public void test_wp_call() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        try {
            JOWPObject wp = new JOWPObject(proc, "bat_prepared", "call01");
            JOFunctional.exec("db.wp_call", wp);

        } finally {
            proc.release();
        }
    }

    public void testWPExecuteUpdate() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        try {
            JOWPObject wp = new JOWPObject(proc, "ps_salesp", "usp_updatesp");
            //wp.p().put("rid", 8396);
            wp.p().put("rid", 4000001);
            Object o = JOFunctional.exec("db.wp_executeUpdate", wp);
            System.out.println("===== --->" + o);
        } finally {
            proc.release();
        }
    }

    public void test_row() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        try {
            JOWPObject wp = new JOWPObject(proc, "ps_salesp", "query");
            wp.act().put("$cmd", "select * from psRetail ");
            JSONObject row = (JSONObject) JOFunctional.exec("db.row", wp);
            System.out.println(row);
        } finally {
            proc.release();
        }
    }

    @Test
    public void test_rows() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        try {
            JOWPObject wp = new JOWPObject(proc, "ps_salesp", "query");
            System.out.println(wp.act());
            wp.p().put("newId", "V90033");
            wp.act().put("$cmd", "select * from psRetail where newId=${newId,string}");
            System.out.println(wp.act());
            List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("db.rows", wp);
            for (JSONObject row : rows) {
                System.out.println(row);
            }
        } finally {
            proc.release();
        }
    }

}
