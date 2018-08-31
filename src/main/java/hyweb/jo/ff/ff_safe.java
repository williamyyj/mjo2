package hyweb.jo.ff;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public class ff_safe extends JOFFBase<String>  {

    @Override
    public void __init_proc(JOProcObject proc) {

    }

    @Override
    protected String apply(JSONObject row, String id, Object o) {
        return (o==null) ? "" : o.toString().trim();
    }

    @Override
    public String cast(Object fv) {
        return (fv==null) ? "" : fv.toString().trim();
    }


    
}
