package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.JOWPObject;


/**
 *
 * @author william
 */
public class wp_metadata implements IJOFunction<Boolean, JOWPObject>{

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        String[] items =  wp.act().optString("$metadata").split(",");
        for(String item : items){
            String[] cmd = item.split("\\:");
            JOWPObject wp_child = new JOWPObject(wp.proc(),cmd[1]);
            wp.proc().put(cmd[0], wp_child);
        }
        return true ;
    }


    
}
