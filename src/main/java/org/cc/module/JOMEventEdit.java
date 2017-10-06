package org.cc.module;

import hyweb.jo.JOProcObject;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;

/**
 *
 * @author william
 */
public class JOMEventEdit extends JOMEvent {

    @Override
    public void request(JOProcObject proc) throws Exception {
        before(proc);
        String metaId = metaId(proc);
        JOWPObject wp = new JOWPObject(proc, metaId, "edit");
        fetch_row(wp);
        JSONObject row = (JSONObject) proc.get(JOProcObject.p_request,"row",null);
        after(proc);

    }

    private void fetch_row(JOWPObject wp) throws Exception {
        fetch_row_from_cmd(wp);
    }

    private void fetch_row_from_cmd(JOWPObject wp) throws Exception {
       wp.proc().set(JOProcObject.p_request, "row", JOFunctional.exec("wp_row", wp));
    }

    @Override
    public void response(JOProcObject proc) throws Exception {

    }

}
