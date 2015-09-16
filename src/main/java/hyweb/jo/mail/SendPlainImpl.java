package hyweb.jo.mail;

import hyweb.jo.db.DB;
import hyweb.jo.org.json.JSONObject;
import java.io.File;
import java.util.Date;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.event.ConnectionEvent;
import javax.mail.event.ConnectionListener;
import javax.mail.event.TransportEvent;
import javax.mail.event.TransportListener;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


public class SendPlainImpl implements IMailSender, ConnectionListener, TransportListener {

    private IMailBean mb;

    protected void check_error(DB db, JSONObject mlog) throws Exception {
        if (mb.hasError()) {
            //mlog.put("LogStatus","E");
            //mlog.put("LogResend","Y");
            //mlog.put("logrt",new Date());
            //db.action(mlog);
            //throw new Exception("MAIL_BEAN_ERROR:"+mb.error());
        }
    }

    public void proc_before(DB db, JSONObject mlog) throws Exception {
        /**
        mb = new MailBean();
        mb.setFrom(mlog.v("LogFrom").toS());
        mb.setTOS(mlog.v("LogTos").toS());
        StringBuffer sb = new StringBuffer();
        sb.append("<!--").append(mlog.v("batchId").toS()).
                append(':').append(mlog.v("LogId")).append("-->\r\n");
        sb.append(mlog.v("LogContent"));
        mb.setText(sb.toString());
        mb.setSubject(mlog.v("LogSubject").toS());
        check_error(db, mlog);
        **/
    }

    public void proc_after(DB db, JSONObject mlog) throws Exception {
        /**
        check_error(db, mlog);
        mlog.v("LogStatus", "S");
        mlog.v("logrt", new Date());
        db.update(mlog);
        **/
    }


    public void process(DB db, Session session, JSONObject mlog) throws Exception {
        proc_before(db, mlog);
        process(session, mb);
        proc_after(db, mlog);
    }

    public void process(Session session, IMailBean bean) throws Exception {
        if (session == null || bean == null) {
            return;
        }
        MimeMessage msg = new MimeMessage(session);
        msg.setFrom(bean.from());
		// If you wish to add all addresses at once you should use setRecipients 
		// or addRecipients and provide it with an array of addresses. 
        msg.addRecipients(Message.RecipientType.TO, bean.tos());
        msg.addRecipients(Message.RecipientType.CC, bean.ccs());
        msg.addRecipients(Message.RecipientType.BCC, bean.bccs());
        msg.setSubject(bean.subject());
        Multipart mp = new MimeMultipart();
        process_main_part(mp, bean);
        if (bean.files() != null) {
            for (File f : bean.files()) {
                process_attachment_part(mp, f);
            }
        }
        msg.setSentDate(new Date());
        msg.setContent(mp);
        process_transport(session, msg, bean);
    }

    public void process_main_part(Multipart mp, IMailBean bean) throws Exception {
        MimeBodyPart mbp = new MimeBodyPart();
        mbp.setText(bean.text());
        mp.addBodyPart(mbp);
    }

    public void process_attachment_part(Multipart mp, File f) throws Exception {
        MimeBodyPart part = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(f);
        part.setDataHandler(new DataHandler(fds));
        part.setFileName(f.getName());
        mp.addBodyPart(part);
    }

    public void process_transport(Session session, Message msg, IMailBean bean) {
        Transport trans = null;
        try {
            trans = session.getTransport();
            trans.addConnectionListener(this);
            trans.addTransportListener(this);
            trans.connect();
            trans.sendMessage(msg, bean.tos());
            trans.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
//			try {
//				Thread.sleep(500);
//			} catch (InterruptedException e1) {
//				e1.printStackTrace();
//			}
//		      Exception ex = e;
//		      do {
//		        if (ex instanceof SendFailedException) {
//		          SendFailedException sfex = (SendFailedException) ex;
//		          Address[] invalid = sfex.getInvalidAddresses();
//		          if (invalid != null) {
//		            System.out.println("    ** Invalid Addresses");
//		            if (invalid != null) {
//		              for (int i = 0; i < invalid.length; i++)
//		                System.out.println("         " + invalid[i]);
//		            }
//		          }
//		          Address[] validUnsent = sfex.getValidUnsentAddresses();
//		          if (validUnsent != null) {
//		            System.out.println("    ** ValidUnsent Addresses");
//		            if (validUnsent != null) {
//		              for (int i = 0; i < validUnsent.length; i++)
//		                System.out.println("         " + validUnsent[i]);
//		            }
//		          }
//		          Address[] validSent = sfex.getValidSentAddresses();
//		          if (validSent != null) {
//		            System.out.println("    ** ValidSent Addresses");
//		            if (validSent != null) {
//		              for (int i = 0; i < validSent.length; i++)
//		                System.out.println("         " + validSent[i]);
//		            }
//		          }
//		        }
//		        System.out.println();
//		        if (ex instanceof MessagingException)
//		          ex = ((MessagingException) ex).getNextException();
//		        else
//		          ex = null;
//		      } while (ex != null);		
        }

    }

    public void messageDelivered(TransportEvent event) {
        System.out.print(">>> TransportListener.messageDelivered().");
        System.out.println(" Success Addresses:");
        Address[] valid = event.getValidSentAddresses();
        if (valid != null) {
            for (int i = 0; i < valid.length; i++) {
                System.out.println("    " + valid[i]);
            }
        }
    }

    public void messageNotDelivered(TransportEvent event) {
        System.out.print(">>> TransportListener.messageNotDelivered().");
        System.out.println(" Fail Addresses:");
        Address[] valid = event.getInvalidAddresses();
        if (valid != null) {
            for (int i = 0; i < valid.length; i++) {
                System.out.println("    " + valid[i]);
            }
            mb.setTOS("not delivered : " + valid[0]);
        }

    }

    public void messagePartiallyDelivered(TransportEvent event) {
        System.out.print(">>> TransportListener.messagePartiallyDelivered().");
        Address[] valid = event.getValidSentAddresses();
        System.out.println(" Success Addresses:");
        if (valid != null) {
            for (int i = 0; i < valid.length; i++) {
                System.out.println("    " + valid[i]);
            }
        }
        valid = event.getValidUnsentAddresses();
        System.out.println(" Fail Addresses:");
        if (valid != null) {
            for (int i = 0; i < valid.length; i++) {
                System.out.println("    " + valid[i]);
            }
        }
    }

    public void closed(ConnectionEvent event) {
    }

    public void disconnected(ConnectionEvent arg0) {
    }

    public void opened(ConnectionEvent arg0) {
    }

  
}
