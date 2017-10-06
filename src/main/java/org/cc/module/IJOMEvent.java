package org.cc.module;

import hyweb.jo.JOProcObject;

/**
 * @author william
 */

public interface IJOMEvent {
    
    public void request(JOProcObject proc) throws Exception ;
    
    public void response(JOProcObject proc) throws Exception ; 
    
}
