package hyweb.jo.model;

import hyweb.jo.IJOInit;
import hyweb.jo.log.JOLogger;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author william
 */
public class JOValidFields {
    
    private static Map<String,Object> mcache;
    
    public static Object vf(String classId, String base){
        if(mcache==null){
            mcache = new HashMap<String,Object>();
        }
        Object c = mcache.get(classId);
        if(c==null){
            try{
              IJOInit<String>  o = (IJOInit<String>) Class.forName(classId).newInstance();
              o.__init__(base);
              mcache.put(classId, o);
              return o;
            } catch(Exception e){
                JOLogger.info("Can't instanceof "+ classId, e);
            }
        }
        return c ;
    }
}
