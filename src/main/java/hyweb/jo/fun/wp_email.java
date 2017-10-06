package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOConfig;
import hyweb.jo.log.JOLogger;
import hyweb.jo.mail.IMailSender;
import hyweb.jo.mail.MailBase;
import hyweb.jo.mail.MailBean;
import hyweb.jo.mail.MailCfg;
import hyweb.jo.mail.SendHtmlImpl;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public class wp_email implements IJOFunction<Boolean, JOWPObject> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        long ts = System.nanoTime();
        MailBean bean = new MailBean();
        JSONObject p = wp.p();
        JSONObject pp = new JOConfig(wp.proc().base(), "email").params();
        try {
            MailBase mb = new MailBase(new MailCfg(pp));
            IMailSender sender = new SendHtmlImpl();
            bean.setTOS(p.optString("email"));
            bean.setCCS(pp.optString("cc"));
            bean.setSubject(p.optString("subject"));
            bean.setText(p.optString("context"));
            bean.setFrom(pp.optString("from"), pp.optString("disp"));
            mb.sendMessage(sender, bean);

            if (pp.has("sleeping")) {
                Thread.sleep(pp.optLong("sleeping"));
            }
            JOLogger.debug("------------------------------------------------------");
            JOLogger.debug("from: " + pp.optString("from")+" disp:"+ pp.optString("disp") + " to:" + p.optString("email") + " subject:" + p.optString("subject"));
            JOLogger.debug("time: "+(System.nanoTime()-ts)/1E9);
            return true;
        } catch (Exception e) {
            // 有debiug mode 可查 email 
            JOLogger.info("Can't send mail ---> " + bean.toJSON(), e);
            return false;
        }

    }

}
