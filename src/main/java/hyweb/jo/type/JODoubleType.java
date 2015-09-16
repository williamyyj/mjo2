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
public class JODoubleType extends JOType<Double> {

    @Override
    public String dt() {
        return IJOType.dt_double;
    }

    @Override
    public int jdbc() {
        return Types.DOUBLE;
    }

    @Override
    public Double loadRS(ResultSet rs, String name) throws SQLException {
        return rs.getDouble(name);
    }

    @Override
    public void setPS(PreparedStatement ps, int idx, Object value) throws SQLException {
        if (value == null) {
            ps.setNull(idx, Types.DOUBLE);
        } else {
            ps.setDouble(idx, check(value, 0.0));
        }
    }

    @Override
    public Double check(Object o, Double dv) {
        try {
            if (o instanceof Number) {
                return ((Number) o).doubleValue();
            } else if (o instanceof String) {
                String str = ((String) o).trim();
                return str.length() > 0 ? Double.parseDouble(str) : dv;
            }
        } catch (Exception e) {
            log.warn("Can't check value : " + o);
        }
        return dv;
    }
}
