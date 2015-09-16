package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.Date;

/**
 * @author william
 * @param <E>
 */
public class mjo extends mjo_base implements IJOFunction<Object, Object[]> {
    
    @Override
    public Object exec(Object[] args) throws Exception {  
        JSONObject wp = wp(args);
        String classId = wp.optJSONObject("$act").optString("classId");
        return JOFunctional.exec(classId, wp);
    }

}
