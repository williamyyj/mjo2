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
import hyweb.jo.util.DateUtil;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.MVELTemplate;
import hyweb.jo.util.TextUtils;
import java.util.Map;

/**
 * metaId.actor : { title : '' , subject:'' , $tml:'' ｝ p : pool data { email : '' ..... } pp : mail.conf 增加客制化cc
 *  metadata add  $scope_cc  , $scope_email  解決客制化問題
 * @author william
 */
public class wp_sendmail implements IJOFunction<Boolean, JOWPObject> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        JSONObject act = wp.act();
        JSONObject pool = new JSONObject(act);
        initPool(wp,pool);
        //  proc template  
        wp.reset(pool);
        JOFunctional.exec("dao.wp_eval", wp);
        JOWPObject wpMail = new JOWPObject(wp.proc(), "psMailLog", null, pool, null);
        pool.put("ctype","1");// 設定是發送email 
        try {
            sendMail(pool, wp.pp());
            pool.put("status", "1");
            JOFunctional.exec("dao.wp_add", wpMail);
        } catch (Exception e) {
            JOLogger.error(e.getMessage(), e);
            pool.put("status", "2");
            pool.put("exception", e.getMessage());
            JOFunctional.exec("dao.wp_add", wpMail);
        }
        return true;
    }
    
    private void initPool(JOWPObject wp, JSONObject pool) throws Exception{
        JOConfig cfg = new JOConfig(wp.proc().base(), "email");
        if (wp.pp().isEmpty()) {
            wp.put("$$", cfg.params());
        }
        pool.put("$", wp.p()); //  
        pool.put("$$", wp.pp());
        Map<String, IJOFF> mff = JOFF.init_ff(wp.proc(), wp.metadata());
        mff.put("safe", new ff_safe());
        pool.put("$ff", mff);
        pool.put("$F", JOFunctional.class);
        pool.put("$du", DateUtil.class);
        pool.put("$tu", TextUtils.class);
        pool.put("cc",cfg.scopeAsString(pool, "cc"));
        if(wp.p().has("email")){//外部參數為主
             pool.put("email", wp.p().optString("email")); // 預設email位置
        } else {
             pool.put("email",cfg.scopeAsString(pool, "email"));
        }
        MVELTemplate tmpl = new MVELTemplate(wp.proc().base(), pool.optString("$tml"));
        String context = tmpl.processTemplate(pool);
        pool.put("context", context);
    }

    private void sendMail(JSONObject p, JSONObject pp) throws Exception {
        try {
            MailBean bean = new MailBean();
            MailBase mb = new MailBase(new MailCfg(pp));
            IMailSender sender = new SendHtmlImpl();
            bean.setTOS(p.optString("email"));
            set_cc(bean, p, pp);
            bean.setSubject(p.optString("subject"));
            bean.setText(p.optString("context"));
            bean.setFrom(pp.optString("from"), pp.optString("disp"));
            JOLogger.debug("---------------------------------------\r\n"+ bean.toJSON().toString(4));
            JOLogger.debug("---------------------------------------\r\n"+p.optString("context") );
            mb.sendMessage(sender, bean);
        } finally {// 一定要執行
            if (pp.has("sleeping")) {
                Thread.sleep(pp.optLong("sleeping"));
            }
        }
    }

    private void set_cc(MailBean bean, JSONObject p, JSONObject pp) {
        StringBuilder sb = new StringBuilder();
        sb.append(p.optString("cc"));
        if (sb.length() > 0) {
            sb.append(',');
        }
        sb.append(pp.optString("cc"));
        bean.setCCS(sb.toString());
    }

}
