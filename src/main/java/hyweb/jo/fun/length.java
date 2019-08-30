package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import java.util.Collection;
import java.util.Map;

/**
 *
 * @author william
 */
public class length implements IJOFunction<Integer, Object> {

    @Override
    public Integer exec(Object o) throws Exception {
        if(o==null){
            return  0;
        } else if (o instanceof Collection){
            return ((Collection)o).size();
        } else if (o instanceof Map){
             return ((Map)o).size();
        } else if(o instanceof String) {
            return ((String)o).trim().length();
        }
        return 0;
    }
    
}
