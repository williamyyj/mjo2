package hyweb.jo.fun.docs;

import hyweb.jo.JOProcObject;
import static hyweb.jo.JOTest.base;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
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
        String line ="H4sIAAAAAAAAAFWPTQuCQBCG_0rsOXBXidJzFy9CFF1l3J1wKd11PzCJ_nsuiLkwp2dm3o8Psa0a6w6eeHakIIzs_6QUW6LROsmlwAo63C4a0K0cSnHDdyTR-KnsH2pBL8mxt-HxnlNK8xkJDTWbQUrZMaGnhIY74EFk8Gim1dkb3oLFi5tiX8OViKIYNcahDbqrA-dt1GTRWxuHIOkmSMZ2aVYc8nnI9wfAEvxzIwEAAA==";
        JSONObject p = JOTools.decode_jo(line);
        proc.params().putAll(p);
        System.out.println(p.toString(4));
        try {
            List<String> csv = new ArrayList<String>();
            proc.put("$csv", csv);
            JOWPObject wp = new JOWPObject(proc, "rpt_retss", "export");
            JOFunctional.exec("docs.wp_csv", wp);
            for(String item : csv){
                System.out.println(item);
            }
        } finally {
            proc.release();
        }
    }

}
