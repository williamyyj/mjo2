package hyweb.jo;

import hyweb.jo.type.JOTypes;

/**
 * @author William
 * @param <CONN>
 */

public interface IJODC<CONN> extends IJOResource {

    public CONN connection() throws Exception;

    public JOTypes types();
    
    public String base();
    
}
