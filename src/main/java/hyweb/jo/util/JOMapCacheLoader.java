package hyweb.jo.util;

import com.google.common.cache.CacheLoader;
import hyweb.jo.org.json.JSONObject;


/**
 *
 * @author william
 */
public class JOMapCacheLoader extends CacheLoader<String, JSONObject>  {

    @Override
    public JSONObject load(String k) throws Exception {
        return  null ; 
    }
    

}
