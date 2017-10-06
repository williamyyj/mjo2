package org.cc.module;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public class JOProcUtils {

    public static void procData(JOProcObject proc, JSONObject cfg) {
        if (cfg.has("$proc")) {
            JSONObject p = proc.params();  //  未來使用 stack 
            
        }
    }

}
