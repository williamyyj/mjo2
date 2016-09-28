package hyweb.jo.fun;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import org.junit.Test;

/**
 * @author william
 */
public class loadm_test {
    
    @Test
    public void exec_text() throws Exception {
        String base = "D:\\Users\\william\\Dropbox\\resources\\prj\\baphiq";
        JOProcObject proc = new JOProcObject(base);
        proc.params().put("baphiqid", "UP0000208509");
        try {
            JSONObject m = (JSONObject) JOFunctional.exec2("loadm", proc, "ret_status");
            System.out.println(m.toString(4));
        } finally {
            proc.release();
        }
    }
    
}
