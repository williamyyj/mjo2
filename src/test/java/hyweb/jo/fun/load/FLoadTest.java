package hyweb.jo.fun.load;

import hyweb.jo.JOProcObject;
import static hyweb.jo.JOTest.base;

import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;

import hyweb.jo.util.JOFunctional;

import java.io.File;

import java.util.List;
import org.junit.Test;

/**
 *
 * @author william
 */
public class FLoadTest {

    @Test
    public void test_wp_record() throws Exception {
        JOProcObject proc = new JOProcObject(base);

        String fp = "D:\\Users\\william\\Dropbox\\resources\\prj\\baphiq_data\\test\\uploadData\\F00238_001.txt";
        proc.params().put("fp", new File(fp));

        try {
            JOWPObject wp = new JOWPObject(proc, "ps_salesp", "load");
            List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("load.wp_record", wp);

            for (JSONObject row : rows) {
                System.out.println(row);
            }
        } finally {
            proc.release();
        }
    }
}
