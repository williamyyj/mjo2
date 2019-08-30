package hyweb.jo.fun.tml;

import hyweb.jo.JOProcObject;
import static hyweb.jo.JOTest.base;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import org.junit.Test;

/**
 *
 * @author william
 */
public class SendMailTest {

    public void test_foreignApply_mail() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        proc.params().putAll(JOTools.loadString("{sid:16,baphiqid:UP000000000000090002}"));
        try {
            // JSONObject user = (JSONObject) proc.get(JOProcObject.p_session, "$user", null);
            JSONObject user = proc.db().row("select * from psStoreView where baphiqId=?", proc.params().opt("baphiqid"));
            JOWPObject wp = new JOWPObject(proc, "psSaleForeign", "mrow");
            JSONObject mrow = (JSONObject) JOFunctional.exec("wp_row", wp);
            mrow.put("newId", user.opt("newId"));
            JOWPObject wpMail = new JOWPObject(proc, "psSaleForeign", "mailApply", mrow, null);
            JOFunctional.exec("tml.wp_sendmail", wpMail);

        } finally {
            proc.release();
        }
    }

    @Test
    public void test_mail_data() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        proc.params().putAll(JOTools.loadString("{sid:14,baphiqid:UP000000000000090002}"));
        try {
            JOWPObject wp = new JOWPObject(proc, "psSaleForeign", "mailData");
            JSONObject row = (JSONObject) JOFunctional.exec("wp_row", wp);
            JSONObject act = wp.act();

            int status = row.optInt("status");

            String ss = "";
            switch (status) {
                case 2:
                    ss = "agree";
                    break;
                case 3:
                    ss = "disagree";
                    break;
                case -1:
                    ss = "reject";
                    break;
            }
            if(ss.length()>0){
                act.put("$tml", act.optString("$tml")+"_"+ss);
                wp.reset(row);
                JOFunctional.exec("tml.wp_sendmail", wp);
            }

        } finally {
            proc.release();
        }
    }

}
