/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOConst;
import hyweb.jo.JOProcConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DBCmd;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.List;
import java.util.regex.Pattern;

/**
 * @author william
 */
public class wp_rows implements IJOFunction<List<JSONObject>, JOWPObject> {

    Pattern p = Pattern.compile("(\\s+order\\s+by)");

    @Override
    public List<JSONObject> exec(JOWPObject wp) throws Exception { // 這樣使用可能會有問題
        if (!wp.act().optBoolean("$disableEval")) {
            JOFunctional.exec("wp_eval", wp);
        }
        List<JSONObject> rows = (List<JSONObject>) wp.proc().db().action(mq(wp));
        wp.put("$act", "rows");  // 不再使用了
        wp.put("$data", rows);  //  不再使用了
        wp.proc().set(JOProcObject.p_request, wp.act().optString("$dataId", "$data"), rows);
        wp.proc().put(wp.act().optString("$dataId", "$data"), rows);
        return rows;
    }

    private boolean match(Pattern p, String line) {
        return p.matcher(line).find();
    }

    public JSONObject mq(JOWPObject wp) throws Exception {
        JSONObject jq = new JSONObject(wp.p().m());
        String cmd = wp.ref_string(JOConst.cmd);
        String orderby = wp.ref_string("$orderby");
        //System.out.println("===== orderby:[" + orderby + "]");
        //System.out.println("===== cmd:[" + cmd + "]");
        if (orderby!=null && orderby.length()>0  && !match(p, cmd)) {
            cmd = cmd + " order by " + orderby;
        }
        jq.put(JOProcConst.cmd, cmd);
        jq.put(JOProcConst.act, wp.act().optString(JOConst.act));
        return DBCmd.parser_cmd(wp.proc().db(), jq);
    }

}
