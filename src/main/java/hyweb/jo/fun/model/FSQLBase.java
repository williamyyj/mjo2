/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.model;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.IJOField;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author william
 *    ft :   field  選取的欄位
 *    ft:   query 查詢的欄位
 *    ft:   table  table 欄位
 *    
 *    
 */
public class FSQLBase implements IJOFunction<String, List<IJOField>>{
    
    private static Map<String, String> op;

    public static Map<String, String> op() {
        if (op == null) {
            op = new HashMap<String, String>();
            op.put("=", "=");
            op.put(">", ">");
            op.put(">=", ">=");
            op.put("<", "<");
            op.put("<=", "<=");
            op.put("$like", "like"); //   xxx%
            op.put("$all", "like");   //   %xxxx%
            op.put("$range", "");  //     fld  betten a and b 
            op.put("$set", "="); // for update 
            op.put("$rm", ""); // for clean ; 
        }
        return op;
    }
    public String exec(List<IJOField> p) throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
