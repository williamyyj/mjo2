package hyweb.jo.mail;

import java.io.File;

import javax.mail.internet.InternetAddress;

public interface IMailBean {

    public InternetAddress from();

    public InternetAddress[] tos();

    public InternetAddress[] ccs();

    public InternetAddress[] bccs();

    public File[] files();

    public String subject();

    public String text();

    public String name();

    public void setFrom(String from);

    public void setFrom(String from, String disp);

    public void setSubject(String subject);

    public void setText(String text);

    public void setTOS(String toStrings);

    public void setCCS(String ccStrings);

    public void setBCCS(String bccStrings);

    public void setFiles(String fileStrings);

    public void setError(String error);

    public void setName(String name);

    public boolean hasError();

    public String error();

}
