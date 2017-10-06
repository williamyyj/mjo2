package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;


/**
 *
 * @author william
 */
public class wp_convert implements IJOFunction<Boolean, JOWPObject> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        JSONObject row = wp.p();
         for (IJOField fld : wp.fields()) {
            String args = fld.args();
            try {
               Object fv = fld.getFieldValue(row);
               Object v = fld.convert(fv);
               if(v!=null){
                   row.put(fld.id(), v);
               }              
            } catch (Exception e) {
                JOLogger.error("Can't cast : " + fld.id() + "\n" + args, e);
            }
        }
         return true;
    }
    
}
