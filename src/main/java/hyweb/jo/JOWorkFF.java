package hyweb.jo;

import hyweb.jo.ff.IJOFF;
import hyweb.jo.ff.JOFF;
import hyweb.jo.org.json.JSONObject;

/**
 * @author william
 */
public class JOWorkFF extends JSONObject {

    private JOProcObject proc;

    public JOWorkFF(JOProcObject proc) {
        this.proc = proc;
    }
    
    public Object cast(String id, Object fv){
       IJOFF ff = (IJOFF) proc.get(JOProcObject.p_request, JOFF.pffId(id), null);
       return (ff!=null) ? ff.cast(fv) :"";
    }
    
}
