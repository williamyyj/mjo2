package hyweb.jo.util;

import hyweb.jo.fun.model.FMLoadCombo;
import hyweb.jo.org.json.JSONObject;
import java.util.List;
import java.util.concurrent.Callable;


/**
 * @author william
 */

public class JOCacheCombo implements IJOCacheItem<List<JSONObject>>, Callable<IJOCacheItem<List<JSONObject>>> {

    private final String urn ; 
    private long lastModified;
    private List<JSONObject> data;
    private final FMLoadCombo f_combo ; 
    

    
    public JOCacheCombo(String urn) {
       this.f_combo = new FMLoadCombo();
       this.urn = urn ; 
    }

    @Override
    public long lastModified() {
        return this.lastModified;
    }

    @Override
    public List<JSONObject> load() throws Exception {
        if(urn!=null && data==null){
          data = f_combo.exec(urn);
        }
        return data ; 
    }

    @Override
    public void unload() {
        data = null ; 
    }

    @Override
    public IJOCacheItem<List<JSONObject>> call() throws Exception {
        return this ;
    }

}
