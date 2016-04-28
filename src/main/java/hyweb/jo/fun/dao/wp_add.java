/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.dao;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.JOWPObject;


/**
 *
 * @author william
 */
public class wp_add extends WPDao implements IJOFunction<Long,JOWPObject>{

    @Override
    public Long exec(JOWPObject wp) throws Exception {
        return (Long) proc_dao_cmd(wp, "model.FSQLInsert", "add");
    }
    
}
