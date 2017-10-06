package hyweb.jo.batch;

import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public interface IJOBatch {

    public void __init__();

    public void __before();

    public void execute(JSONObject p);
    
    public void __after();
    
     public void __error(Exception e);
    
    public void __release();
}
