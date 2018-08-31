/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.type;

import hyweb.jo.IJOType;
import hyweb.jo.log.JOLogger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author william
 */
public class JONVarcharType extends JOType<String> {

    @Override
    public String dt() {
        return IJOType.dt_nvarchar;
    }

    @Override
    public int jdbc() {
        return Types.NVARCHAR;
    }

    @Override
    public String loadRS(ResultSet rs, String name) throws SQLException {
        return rs.getNString(name);
    }

    @Override
    public void setPS(PreparedStatement ps, int idx, Object value) throws SQLException {
        JOLogger.debug("====== check nvarchar : " + value);
        if (value == null) {
            ps.setNull(idx, Types.VARCHAR);
        } else {
            ps.setNString(idx, value.toString());
        }
    }

    @Override
    public String check(Object o, String dv) {
        String ret = (o != null) ? o.toString() : dv;
        return ("null".equalsIgnoreCase(ret)) ? "" : ret;
    }

}
