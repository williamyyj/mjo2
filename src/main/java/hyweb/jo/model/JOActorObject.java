package hyweb.jo.model;

import hyweb.jo.JOProcObject;

/**
 *  整合全部功能一步到位
 * @author william
 *     JOProcObject 資料共用
 *     metadata  -->  ${base}/dp/metadata 
 *    module  -->  ${base}/dp/module 
 * 
 */
public class JOActorObject {
        
    private JOProcObject proc ;
    private final String moduleId; // 目前等次
 
    public JOActorObject(JOProcObject proc, String moduleId){
        this.proc = proc ; 
        this.moduleId = moduleId ;
    }
    
}
