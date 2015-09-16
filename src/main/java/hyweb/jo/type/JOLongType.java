/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import hyweb.jo.IJOType;


/**
 *
 * @author William
 */
public class JOLongType extends JOType<Long> {

    @Override
    public String dt() {
        return IJOType.dt_long;
    }

    @Override
    public int jdbc() {
        return Types.DECIMAL;
    }

    @Override
    public Long loadRS(ResultSet rs, String name) throws SQLException {
        return rs.getLong(name);
    }

    @Override
    public void setPS(PreparedStatement ps, int idx, Object value) throws SQLException {
        if (value == null) {
            ps.setNull(idx, Types.DECIMAL);
        } else {
            ps.setLong(idx, check(value, 0L));
        }
    }

    @Override
    public Long check(Object o, Long dv) {
        try {
            if (o instanceof Number) {
                return ((Number) o).longValue();
            } else if (o instanceof String) {
                String str = ((String) o).trim();
                return str.length() > 0 ? Long.parseLong(str) : dv;
            }
        } catch (Exception e) {
            log.warn("Can't check value : " + o);
        }
        return dv;
    }
}
