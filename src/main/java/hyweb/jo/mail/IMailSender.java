package hyweb.jo.mail;

import hyweb.jo.db.DB;
import hyweb.jo.org.json.JSONObject;
import javax.mail.Session;

public interface IMailSender {

    public void proc_before(DB db, JSONObject jo) throws Exception;

    public void proc_after(DB db, JSONObject jo) throws Exception;

    public void process(DB db, Session session, JSONObject jo) throws Exception;

    public void process(Session session, IMailBean bean) throws Exception;
    
}
