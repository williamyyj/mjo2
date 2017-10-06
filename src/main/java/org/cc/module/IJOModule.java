package org.cc.module;

/**
 * @author william
 */
public interface IJOModule {

    public void init() ; 
        
    public void request();
    
    public void process();
    
    public void response();
  
    public void expextion();
   
    public void release();

}
