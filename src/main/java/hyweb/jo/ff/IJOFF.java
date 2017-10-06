package hyweb.jo.ff;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;

/**
 * @author william
 * @param <R>
 */
public interface IJOFF<R> {

    public void init(JOProcObject proc) throws Exception ; 
    
    public R apply(JSONObject row);

    public R as(JSONObject row, String id);
    
    public JSONObject cfg();

}
