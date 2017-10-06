package org.cc.module;

import hyweb.jo.JOProcObject;

/**
 * @author william
 * @param <RET>
 */
public interface ICCDBF<RET> {

    public RET apply(JOProcObject proc , String cmd, Object[] params) throws Exception;
    
}
