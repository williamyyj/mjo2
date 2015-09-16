package hyweb.jo.mail;

import javax.mail.Multipart;
import javax.mail.internet.MimeBodyPart;

public class SendHtmlImpl extends SendPlainImpl {

    @Override
    public void process_main_part(Multipart mp, IMailBean bean) throws Exception {
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setContent(bean.text(), "text/html; charset=UTF-8");
        mp.addBodyPart(mbp);
    }
    
}
