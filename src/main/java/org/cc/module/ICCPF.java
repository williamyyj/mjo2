package org.cc.module;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 * @param <R>
 */
public interface ICCPF<R> {
    public R apply(JOProcObject proc, JSONObject event , JSONObject cfg) throws Exception ;
}
