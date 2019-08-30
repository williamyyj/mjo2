/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo;

import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOPath;
import java.util.Collection;
import java.util.Date;

/**
 *
 * @author william
 */
public class JOProcParams extends JSONObject implements IProcParams {
    
    public JOProcParams(){
        super();
    }

    @Override
    public Date asDate(String key) {
        Object fv = this.get(key);
        try {
            if (fv instanceof Date) {
                return (Date) fv;
            } else if (fv instanceof String) {
                return (Date) JOFunctional.exec("case.date", fv);
            } else if (fv instanceof Long) {
                return new Date((Long) fv);
            }
        } catch (Exception e) {
            JOLogger.debug("Can't asDate : " + fv, e);
        }
        return null;
    }

    @Override
    public String asArrayToString(String key) {
        Object o = get(key);
        StringBuilder sb = new StringBuilder();
        if( o instanceof Collection ){
            Collection items = (Collection) o;
            for(Object item : items){
                sb.append(item).append(",");
            }
            if(items.size()>0){
                sb.setLength(sb.length()-1);
            }
        }
        return sb.toString();
    }

    @Override
    public Object getParam(String jp) {
        return JOPath.path(this, jp);
    }

    @Override
    public void setParam(String jp, Object v) {
        JOPath.set(this, jp, v);
    }

}
