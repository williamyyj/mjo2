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
public class JOIntType extends JOType<Integer> {

    @Override
    public String dt() {
        return IJOType.dt_int;
    }

    @Override
    public int jdbc() {
        return Types.INTEGER;
    }

    @Override
    public Integer loadRS(ResultSet rs, String name) throws SQLException {
        return rs.getInt(name);
    }

    @Override
    public void setPS(PreparedStatement ps, int idx, Object value) throws SQLException {
        if (value == null) {
            ps.setNull(idx, Types.INTEGER);
        } else {
            ps.setInt(idx, check(value, 0));
        }
    }

    @Override
    public Integer check(Object o, Integer dv) {
        try {
            if (o instanceof Number) {
                return ((Number) o).intValue();
            } else if (o instanceof String) {
                String str = ((String) o).trim();
                return str.length() > 0 ? Integer.parseInt(str) : dv;
            }
        } catch (Exception e) {
            log.warn("Can't check value : " + o);
        }
        return dv;
    }
    
}
