/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONObject;
import java.util.List;

/**
 *
 * @author william
 */
public class db_rows  implements IJOFunction<List<JSONObject>, Object[]>{

    public List<JSONObject> exec(Object[] args) throws Exception {
        JSONObject proc = (JSONObject) args[0];
        return null;
    }
    
}
