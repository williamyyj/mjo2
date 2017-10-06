package org.cc.module;

import hyweb.jo.JOProcObject;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public abstract class JOMEvent implements IJOMEvent {

    private static String e_meta = "$meta_";
    private static String e_fp = "$fp";

    public JOMetadata metadata(JOProcObject proc, String metaId) {
        JOMetadata md = (JOMetadata) proc.opt(e_meta + metaId);
        if (md == null) {
            md = new JOMetadata(proc.base(), metaId);
            proc.put(e_meta + metaId, md);
        }
        return md;
    }

    public JSONObject fp(JOProcObject proc) {
        JSONObject fp = (JSONObject) proc.get(JOProcObject.p_request, e_fp, null);
        if (fp == null) {
            fp = new JSONObject();
            proc.set(JOProcObject.p_request, e_fp, fp);
        }
        return fp;
    }

    public String actId(JOProcObject proc) {
        return (String) proc.get(JOProcObject.p_params, "act", "");
    }

    public String metaId(JOProcObject proc) {
        return (String) proc.find("$metaId", "");
    }

    public void before(JOProcObject proc) {

    }

    public void after(JOProcObject proc) {

    }

}
