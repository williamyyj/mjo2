package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.log.JOLogger;
import hyweb.jo.mail.IMailSender;
import hyweb.jo.mail.MailBase;
import hyweb.jo.mail.MailBean;
import hyweb.jo.mail.MailCfg;
import hyweb.jo.mail.SendHtmlImpl;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.MVELTemplate;
import java.io.File;

/**
 * @author william
 */
public class mjoc_mail implements IJOFunction<Boolean, JSONObject> {

    @Override
    public Boolean exec(JSONObject wp) throws Exception {
        JOProcObject proc = MJOBase.proc(wp);
        JSONObject act = MJOBase.act(wp);
        MailBean bean = null;
        try {    
            wp.put("$$", act);
            JOMetadata metadata = MJOBase.metadata(wp);
            JOFunctional.exec("mjo_eval", wp);
            JSONObject m = wp.optJSONObject("$");
            JOLogger.debug(m.toString(4));
            JSONObject cfg = JOCache.load(new File(proc.base(), "mail.json"));
            JSONObject mcfg = cfg.optJSONObject("mail");
            MailBase mb = new MailBase(new MailCfg(mcfg));
            IMailSender sender = new SendHtmlImpl();
            String cp = (String) proc.get(JOProcObject.p_request, "$cp", "/BAPHIQ");
            String url = cfg.optString("host") + cp + m.opt("url");
            if (m.has("url_params")) {
                url = url + "?ejo=" + m.opt("url_params");
            }
            m.put("url_actived", url);
            MVELTemplate tmpl = new MVELTemplate(proc.base(), act.optString("template"));
            String context = tmpl.processTemplate(m);
            System.out.println(context);
            bean = new MailBean();
            bean.setTOS(m.optString("email"));
            bean.setSubject(act.optString("subject"));
            bean.setText(context);
            bean.setFrom(mcfg.optString("from"), mcfg.optString("disp"));
            mb.sendMessage(sender, bean);
            return true;
        } catch (Exception e) {
            JOLogger.info("Can't send mail : "+act, e);
            return false;
        }

    }

}
