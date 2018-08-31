package hyweb.jo.ht;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;


/**
 *
 * @author william
 */
public class HTMappingCell extends HTCell {

    public HTMappingCell(JSONObject cfg){
        super(cfg);
    }
    
    public HTMappingCell(String line) {
        super(line);
    }

    @Override
    public String render(JOProcObject proc) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String display(Object p) {
        return display(p,"");
    }

    @Override
    public String display(Object p, Object dv) {
        String value = (dv!=null) ? dv.toString().trim() : "";
        return (p!=null) ? cfg.optString(p.toString().trim()): value ;
    }

}
