/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;

/**
 * @author william 處理新增修改刪除查詢
 */
public class wp_exec implements IJOFunction<Object, JOWPObject> {

    @Override
    public Object exec(JOWPObject wp) throws Exception {
        JOFunctional.exec("wp_eval", wp);
        JSONObject mq = wp.mq();
        return wp.proc().db().action(wp.mq());
    }

}
