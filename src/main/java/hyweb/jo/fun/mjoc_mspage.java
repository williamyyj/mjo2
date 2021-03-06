/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;


import hyweb.jo.JOProcConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DBCmd;
import static hyweb.jo.fun.MJOBase.*;
import hyweb.jo.fun.util.FJORMWhitespace;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.util.List;

/**
 *
 * @author william
 *
 * <%@attribute name="nowPage" %><!-- 從1開始 -->
 * <%@attribute name="total" %><!-- 資料總數 -->
 * <%@attribute name="numPage" %><!-- 每頁顯示幾筆 -->
 * <%@attribute name="size" %><!-- 顯示多少頁 -->
 * <%@attribute name="url" %><!-- 請求路徑 -->
 * <%@attribute name="args" %><!-- 其它請求參數 -->
 *   
 */
public class mjoc_mspage extends MJOMSPage {

    @Override
    public List<JSONObject> exec(JSONObject wp) throws Exception {
        JOProcObject proc = MJOBase.proc(wp);
        JSONObject p = new FJORMWhitespace().exec(wp.optJSONObject("$"));
        int page = p.optInt("page", 1);
        int numPage = p.optInt("numPage", 10);
        String cmd = MJOBase.act_cmd(wp);
        String act = MJOBase.act_act(wp);
        String order = MJOBase.act(wp).optString("order");
        JSONObject jq = new JSONObject(p.m());
        jq.put(JOProcConst.cmd, cmd);
        jq.put(JOProcConst.act, act);
       JOLogger.debug(jq);
        JSONObject mq = DBCmd.parser_cmd(MJOBase.db(wp), jq);
        int rowCount = rowCount(MJOBase.proc(wp).db(), mq);
        proc.set(JOProcObject.p_request, "total", rowCount);
        proc.set(JOProcObject.p_request, "page", page);
        proc.set(JOProcObject.p_request, "numPage", numPage);
        p.remove("page");
        p.remove("numPage");
        String ejo = JOTools.encode(p.toString());
        proc.set(JOProcObject.p_request, "ejo", ejo);
        List<JSONObject> rows = rows(proc.db(), mq, numPage, page, order);
        List<IJOField> eFields = eval_fields(wp);
        for (JSONObject row : rows) {
            JOFunctional.exec2("meval", proc(wp), eFields, row, wp.opt("$$"));
        }
        return rows;
    }

 
}
