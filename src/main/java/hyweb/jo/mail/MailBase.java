package hyweb.jo.mail;

import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;
import java.io.File;
import java.util.Collection;
import java.util.Properties;
import javax.mail.Session;

public class MailBase {

    private MailCfg cfg;

    public MailBase(MailCfg cfg) {
        this.cfg = cfg;
    }

    public Session getMailSession() {
        Session session = null;
        Properties props = new java.util.Properties();
        String protocol = cfg.protocol();
        System.out.println("===== protocol : " + protocol);
        props.put("mail.transport.protocol", protocol);
        props.put("mail.debug", Boolean.toString(cfg.debug()));
        // props.put("mail.smtps.ssl.checkserveridentity", "false");
        //props.put("mail.smtps.ssl.trust", "*");
        props.put("mail.user", cfg.user());
        props.put("mail.password", cfg.passwd());
        if ("smtp".equals(protocol)) {
            props.put("mail.smtp.host", cfg.host());
            props.put("mail.smtp.port", cfg.port());
            props.put("mail.smtp.auth", cfg.auth());
            props.put("mail.smtp.starttls.enable", "true");
        }
        if ("smtps".equals(protocol)) {

            props.put("mail.smtps.host", cfg.host());
            props.put("mail.smtps.port", cfg.port());
            props.put("mail.smtps.auth", cfg.auth());
            // 限制檢查憑證
            //props.put("mail.smtps.ssl.checkserveridentity", "false");
           // props.put("mail.smtps.ssl.trust", "*");
            
            //props.put("mail.smtp.socketFactory.port", cfg.socket_port());
            //props.put("mail.smtp.socketFactory.fallback", cfg.socket_fallback());
            //props.put("mail.smtp.socketFactory.class", cfg.socket_class());
            // MailSSLSocketFactory socketFactory = new MailSSLSocketFactory();
            // socketFactory.setTrustAllHosts(true);
            //prop.put("mail.smtp.ssl.socketFactory", socketFactory);
        }

        if (cfg.auth()) {
            session = Session.getInstance(props, new MailAuthenticator(cfg.user(), cfg.passwd()));
        } else {
            session = Session.getInstance(props, null);
        }
        return session;
    }

    public void sendMessage(IMailSender sender, IMailBean bean) {

        Session session = getMailSession();
        if (sender != null && session != null) {
            try {
                sender.process(session, bean);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        session = null;
    }

    public void sendMessage(IMailSender sender, Collection<IMailBean> beans) {
        Session session = getMailSession();
        if (sender != null && session != null) {
            int idx = 0;
            for (IMailBean bean : beans) {
                idx++;
                System.out.println("==== start send mail " + idx + " ====");
                try {
                    sender.process(session, bean);

                    Thread.sleep(cfg.sleeping());

                } catch (Exception e) {
                    //e.printStackTrace();
                    System.out.println("send mail error[ " + idx + "]");
                    System.out.print(e.getMessage());
                }
                System.out.println("==== end send mail " + idx + " ====");

            }
        }
        session = null;
    }

    public static void main(String[] args) {
        String base = "D:\\will\\resources\\prj\\baphiq";
        JSONObject mcfg = JOCache.load(new File(base, "mail.json"));
        MailCfg cfg = new MailCfg(mcfg.optJSONObject("gmail"));
        MailBase mb = new MailBase(cfg);
        IMailSender sender = new SendPlainImpl();
        IMailBean bean = new MailBean();
        bean.setTOS("williamyyj@tpe.hyweb.com.tw");
        bean.setSubject("測試");
        bean.setText("測試信");
        mb.sendMessage(sender, bean);
    }
}
