package hyweb.jo.fun;

import hyweb.jo.JOConfig;
import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import static hyweb.jo.JOTest.base;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import org.junit.Test;

/**
 *
 * @author william
 */
public class TMLMailTest extends JOTest {

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

    public void test_tml_sendmail() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        JSONObject p = JOTools.loadJSON("{\"fbname\":\"大頭頁\",\"fbkey\":\"F\",\"VerifyCode\":\"2372\",\"fbphone\":\"12345678\",\"fbtitle\":\"大頭頁測試\",\"fbcontext\":\"大頭頁測試（內容）\",\"fbtype\":\"1\",\"act\":\"add\",\"fbct\":\"Mon Dec 25 11:14:14 CST 2017\",\"fbemail\":\"williamyyj@gmail.com\"}");
        p.put("serial", 27);
        p.put("email", p.optString("fbemail"));
        JSONObject pp = new JOConfig(base, "email","dev").params();
        try {
            JOWPObject wp = new JOWPObject(proc, "feedback", "sendmail", p, pp);
            JOFunctional.exec("tml.wp_sendmail", wp);
        } finally {
            proc.release();
        }
    }
}
