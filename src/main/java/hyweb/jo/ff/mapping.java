package hyweb.jo.ff;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public class mapping  extends JOFFBase<String> {
    
    @Override
    public void __init_proc(JOProcObject proc) {

    }

    @Override
    protected String apply(JSONObject row, String id, Object fv) {
        if(fv !=null){
            String key = fv.toString().trim();
            return cfg.optString(key);
        }
        return "";
    }

    @Override
    public String cast(Object fv) {
         if(fv !=null){
            String key = fv.toString().trim();
            return cfg.optString(key);
        }
        return "";
    }
    
}
