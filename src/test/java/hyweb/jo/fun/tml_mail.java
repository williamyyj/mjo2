package hyweb.jo.fun;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import static hyweb.jo.JOTest.base;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.util.JOFunctional;
import org.junit.Test;

/**
 *
 * @author william
 */
public class tml_mail extends JOTest {

    @Test
    public void mail() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        proc.params().put("license", "1400000008");
        proc.params().put("subject", "該店代號無法識別販賣業執照號碼");
        proc.params().put("email", "williamyyj@gmail.com");
        try {
            long ts = System.nanoTime();
            for (int i = 0; i < 10; i++) {
                JOWPObject wp = new JOWPObject(proc, "posm_upload", "mail");
                JOFunctional.exec("wp_tmlMail", wp);
            }
            JOLogger.debug("total time: " + (System.nanoTime() - ts) / 1E9);
        } finally {
            proc.release();
        }
    }
}
