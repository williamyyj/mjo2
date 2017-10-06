package hyweb.jo.fun.docs;

import hyweb.jo.JOProcObject;
import static hyweb.jo.JOTest.base;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.util.JOFunctional;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author william
 */
public class WPCSVTest {

    @Test
    public void testSomeMethod() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        try {
            List<String> csv = new ArrayList<String>();
            proc.put("$csv", csv);
            JOWPObject wp = new JOWPObject(proc, "rpt_retss", "export");
            JOFunctional.exec("docs.wp_csv", wp);
            for(String line : csv){
                System.out.println(line);
            }
        } finally {
            proc.release();
        }
    }

}
