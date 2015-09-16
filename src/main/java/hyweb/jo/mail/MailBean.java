package hyweb.jo.mail;

import hyweb.jo.org.json.JSONObject;
import java.io.File;
import java.io.UnsupportedEncodingException;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeUtility;


public class MailBean implements IMailBean {

    private String subject;
    private String text;
    private InternetAddress from;
    private InternetAddress[] tos;
    private InternetAddress[] ccs;
    private InternetAddress[] bccs;
    private File[] files;
    private String error;
    private String name;
    private String enc = "UTF-8";

    public MailBean() {
    }

    public MailBean(String from, String to, String subject, String text) throws AddressException {
        this.from = new InternetAddress(from);
        setTOS(to);
        this.subject = subject;
        this.text = text;

    }

    public InternetAddress from() {
        return from;
    }

    public InternetAddress[] tos() {
        return tos;
    }

    public InternetAddress[] ccs() {
        return ccs;
    }

    public InternetAddress[] bccs() {
        return bccs;
    }

    public String subject() {
        return subject;
    }

    public String text() {
        return text;
    }

    public File[] files() {
        return files;
    }

    public String name() {
        return name;
    }

    public void setSubject(String subject) {
        try {
            this.subject = MimeUtility.encodeText( subject,enc,"B");
        } catch (UnsupportedEncodingException ex) {
           ex.printStackTrace();
        }
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setFrom(String from) {
        try {
            this.from = new InternetAddress(from);
        } catch (AddressException e) {
            error = "from:" + from;
        }
    }

    public void setFrom(String from, String disp) {
        try {
            this.from = new InternetAddress(from, disp,"UTF-8");
        } catch (UnsupportedEncodingException ex) {
            error = "form:" + from + "," + disp;
        }
    }

    public void setTOS(String toStrings) {
        try {
            tos = InternetAddress.parse(toStrings);
        } catch (AddressException e) {
            error = "tos:" + toStrings;
        }
    }

    public void setCCS(String ccStrings) {
        try {
            ccs = InternetAddress.parse(ccStrings);
        } catch (AddressException e) {
            error = "ccs:" + ccStrings;
        }
    }

    public void setBCCS(String bccStrings) {
        try {
            bccs = InternetAddress.parse(bccStrings);
        } catch (AddressException e) {
            error = "bccs:" + bccStrings;
        }
    }

    public void setFiles(String fileStrings) {
        //files = $file.parser(fileStrings);
    }

    public boolean hasError() {
        return (error != null) ? true : false;
    }

    public String error() {
        return error;
    }; 

    public void setError(String error) {
        this.error = error;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void loadJSON(JSONObject jo) {
        if (jo.has("from")) {
            setFrom(jo.optString("from"));
        }
        if (jo.has("subject")) {
            setSubject(jo.optString("subject"));
        }
        if (jo.has("text")) {
            setText(jo.optString("text"));
        }
        if (jo.has("tos")) {
            setTOS(jo.optString("tos"));
        }
        if (jo.has("ccs")) {
            setCCS(jo.optString("ccs"));
        }
        if (jo.has("bccs")) {
            setBCCS(jo.optString("bccs"));
        }
        if (jo.has("files")) {
            setFiles(jo.optString("files"));
        }
        if (jo.has("error")) {
            setError(jo.optString("error"));
        }
        if (jo.has("name")) {
            setName(jo.optString("Name"));
        }
    }

    public JSONObject toJSON()  {
        JSONObject jo = new JSONObject();
        if (from != null) {
            jo.put("from", InternetAddress.toString(new InternetAddress[]{from}));
        }
        if (subject != null) {
            jo.put("subject", this.subject);
        }
        if (text != null) {
            jo.put("text", this.text);
        }
        if (tos != null) {
            jo.put("tos", InternetAddress.toString(tos));
        }
        if (ccs != null) {
            jo.put("ccs", InternetAddress.toString(ccs));
        }
        if (bccs != null) {
            jo.put("bccs", InternetAddress.toString(bccs));
        }
        //if(files!=null) jo.put("form", $file.toSting(files));
        if (error != null) {
            jo.put("error", error);
        }
        if (name != null) {
            jo.put("name", name);
        }
        return jo;
    }
}
