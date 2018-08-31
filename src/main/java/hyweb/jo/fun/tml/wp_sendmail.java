package hyweb.jo.fun.tml;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOConfig;
import hyweb.jo.ff.IJOFF;
import hyweb.jo.ff.JOFF;
import hyweb.jo.ff.ff_safe;
import hyweb.jo.log.JOLogger;
import hyweb.jo.mail.IMailSender;
import hyweb.jo.mail.MailBase;
import hyweb.jo.mail.MailBean;
import hyweb.jo.mail.MailCfg;
import hyweb.jo.mail.SendHtmlImpl;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.MVELTemplate;
import java.util.Map;

/**
 * metaId.actor : { title : '' , subject:'' , $tml:'' ｝ p : row data { email :
 * '' ..... } pp : mail.conf
 *
 * @author william
 */
public class wp_sendmail implements IJOFunction<Boolean, JOWPObject> {
    
    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        JSONObject act = wp.act();
        JSONObject row = new JSONObject(act);
        if (wp.pp().isEmpty()) {
            wp.put("$$", new JOConfig(wp.proc().base(), "email").params());
        }
        row.put("$", wp.p());
        row.put("$$", wp.pp());
        Map<String, IJOFF> mff = JOFF.init_ff(wp.proc(), wp.metadata());
        mff.put("safe", new ff_safe());
        row.put("$ff", mff);
        MVELTemplate tmpl = new MVELTemplate(wp.proc().base(), act.optString("$tml"));
        String context = tmpl.processTemplate(row);
        row.put("context", context);
        row.put("email", wp.p().optString("email")); // 一般在row data 中
        sendMail(row, wp.pp());
        return true;
    }
    
    private void sendMail(JSONObject p, JSONObject pp) throws Exception {
        try {
            MailBean bean = new MailBean();
            MailBase mb = new MailBase(new MailCfg(pp));
            IMailSender sender = new SendHtmlImpl();
            bean.setTOS(p.optString("email"));
            bean.setCCS(pp.optString("cc"));
            bean.setSubject(p.optString("subject"));
            bean.setText(p.optString("context"));
            bean.setFrom(pp.optString("from"), pp.optString("disp"));
            JOLogger.debug(bean.toJSON());
            mb.sendMessage(sender, bean);            
        } finally {// 一定要執行
            if (pp.has("sleeping")) {
                Thread.sleep(pp.optLong("sleeping"));
            }
        }
    }
    
}
