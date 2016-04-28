package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import java.util.Arrays;
import java.util.List;

/**
 *
 * @author william
 */
public class wp_csv2row implements IJOFunction<JSONObject,JOWPObject> {

    @Override
    public JSONObject exec(JOWPObject wp) throws Exception {
       JSONObject row = new JSONObject();
       JSONObject act = wp.act();
       String scope = act.optString("$scope");
       String split = act.optString("$split");
       String line = act.optString("$line");
       String[] items = wp.p().optString(line).split(split,-1);
       System.out.println(wp.p().optString(line));
       System.out.println(Arrays.toString(items));
       List<IJOField> sFields = wp.metadata().getFields(scope);
       int idx = 0;
       for(IJOField fld : sFields){
          exec_field(items[idx++],row,fld);
       }
       return row ;
    }
    
    private void exec_field(Object item , JSONObject target , IJOField fld ){
       Object o =  fld.type().check(item);
       System.out.println("====== "+ fld.id()+":"+o);
       target.put(fld.id(), o);
    }
    
    
    
}
