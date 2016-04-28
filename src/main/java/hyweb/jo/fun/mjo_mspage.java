/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.JOProcConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DBCmd;
import hyweb.jo.fun.util.FJORMWhitespace;
import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.util.List;

/**
 *
 * @author william
 */
public class mjo_mspage extends MJOMSPage {

    @Override
    protected List<JSONObject> do_process(JSONObject wp) throws Exception {
        JOProcObject proc = MJOBase.proc(wp);
        JSONObject act = MJOBase.act(wp);
        JSONObject p = new FJORMWhitespace().exec(wp.optJSONObject("$"));
        int page = p.optInt("page", 1);
        int numPage = p.optInt("numPage", 10);
        List<IJOField> qFields = MJOBase.metadata(wp).getFields(act.optString("cmd"));
        String cmd = (String) JOFunctional.exec("model.FSQLQuery", qFields);
        String order = MJOBase.act(wp).optString("order");
        JSONObject jq = new JSONObject(p.m());
        jq.put(JOProcConst.cmd, cmd);
        jq.put(JOProcConst.act, "rows");
        JSONObject mq = DBCmd.parser_cmd(MJOBase.db(wp), jq);
        System.out.println(mq.toString(4));
        int rowCount = rowCount(MJOBase.proc(wp).db(), mq);
        proc.set(JOProcObject.p_request, "total", rowCount);
        proc.set(JOProcObject.p_request, "page", page);
        proc.set(JOProcObject.p_request, "numPage", numPage);
        p.remove("page");
        p.remove("numPage");
        String ejo = JOTools.encode(p.toString());
        proc.set(JOProcObject.p_request, "ejo", ejo);
        System.out.println(mq);
        List<JSONObject> rows = rows(proc.db(), mq, numPage, page, order);
        List<IJOField> eFields = MJOBase.eval_fields(wp);
        for (JSONObject row : rows) {
            JOFunctional.exec2("meval", MJOBase.proc(wp), eFields, row, wp.opt("$$"));
        }
        return rows;
    }

}
