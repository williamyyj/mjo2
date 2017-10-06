package hyweb.jo.model.event;

import hyweb.jo.JOProcObject;

/**
 *
 * @author william
 */
public interface IJOMEvent {

    public void request(JOProcObject proc);

    public void execute(JOProcObject proc);

    public void response(JOProcObject proc);
    
}
