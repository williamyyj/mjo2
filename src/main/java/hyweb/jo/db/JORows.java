/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.db;

import hyweb.jo.IJORows;
import hyweb.jo.org.json.JSONObject;
import java.util.List;

/**
 *
 * @author William
 */
public class JORows implements IJORows<JSONObject>{
    
    private JSONObject cfg ; 

    @Override
    public List<JSONObject> rows(int pageId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int rowCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int pages() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    
}
