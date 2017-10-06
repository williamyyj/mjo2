package hyweb.jo.fun.db;

import hyweb.jo.JOProcObject;
import static hyweb.jo.JOTest.base;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.util.JOFunctional;

/**
 *
 * @author william
 */
public class test {

    public void test_wp_call() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        try {
            JOWPObject wp = new JOWPObject(proc,"bat_prepared","call01");
            JOFunctional.exec("db.wp_call", wp);
        } finally {
            proc.release();
        }
    }
}
