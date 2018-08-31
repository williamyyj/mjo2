/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.dao;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;

/**
 * @author william
 *  回傳Id （非實體資料） 
 */
public class wp_row_edit extends WPDao implements IJOFunction<JSONObject, JOWPObject> {

    @Override
    public JSONObject exec(JOWPObject wp) throws Exception {
         return (JSONObject) proc_dao_cmd(wp, "model.FSQLPKEdit", "row");
    }
    
}