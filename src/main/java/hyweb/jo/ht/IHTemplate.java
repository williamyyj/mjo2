package hyweb.jo.ht;


import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public interface IHTemplate {

    public void render(JOProcObject proc, String cmdLine);

    public String context();

    public JSONObject cfg();
}
