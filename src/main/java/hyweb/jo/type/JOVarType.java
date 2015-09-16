/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import hyweb.jo.IJOType;


public class JOVarType extends JOType<Object> {
    
    private int dt_sql = Types.JAVA_OBJECT ; 
    
    public JOVarType(int dt_sql) {
        this.dt_sql = dt_sql;
    }
    
    public JOVarType(){
        
    }
    
    @Override
    public String dt() {
        return IJOType.dt_var;
    }

    @Override
    public Object check(Object o, Object dv) {
        return (o!=null) ? o : dv;
    }

    @Override
    public Object loadRS(ResultSet rs, String name) throws SQLException {
        return rs.getObject(name);
    }

    @Override
    public void setPS(PreparedStatement ps, int idx, Object value) throws SQLException {
        if(value==null){
            ps.setNull(idx, Types.VARCHAR);
        } else {
            ps.setObject(idx, value);
        }
    }
    
    @Override
    public int jdbc(){
        return dt_sql;
    }

 
}
