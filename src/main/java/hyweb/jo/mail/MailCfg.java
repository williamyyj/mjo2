package hyweb.jo.mail;

import hyweb.jo.org.json.JSONObject;



public class MailCfg extends JSONObject {

    public MailCfg(JSONObject jo) {
        super(jo.m());
    }

    public String protocol() {
        return optString("protocol", "smtp");
    }

    public String user() {
        return optString("user");
    }

    public String passwd() {
        return optString("passwd");
    }

    public boolean debug() {
        return optBoolean("debug");
    }

    public boolean auth() {
        if (has("user") && has("passwd") && opt("auth") == null) {
            return true;
        } else {
            return optBoolean("auth");
        }
    }

    public int port() {
        return optInt("port", -1);
    }

    public String host() {
        return optString("host");
    }

    public int socket_port() {
        return optInt("socket_port", -1);
    }

    public boolean socket_fallback() {
        return optBoolean("socket_fallback");
    }

    public String socket_class() {
        return optString("socket_class", "javax.net.ssl.SSLSocketFactory");
    }

    public int sleeping() {
        return optInt("sleeping", 2000);
    }

    public int batch_size() {
        return optInt("batch_size", 100);
    }

    public String notification() {
        return optString("notification", "williamyyj@gmail.com");
    }

    public String from() {
        return optString("from", "williamyyj@tpe.hyweb.com.tw");
    }

    public String disp() {
        return optString("disp");
    }
}
