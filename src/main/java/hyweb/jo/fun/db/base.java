/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.db;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author william
 */
public class base {

    public void release(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
            rs = null;
        }
    }

    public void release(PreparedStatement ps) throws SQLException {
        if (ps != null) {
            ps.close();
            ps = null;
        }
    }
    
    
    
}
