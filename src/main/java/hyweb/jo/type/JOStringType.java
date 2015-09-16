package hyweb.jo.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import hyweb.jo.IJOType;

/**
 * @author William
 */

public class JOStringType extends JOType<String> {

    @Override
    public String dt() {
        return IJOType.dt_string;
    }

    @Override
    public int jdbc() {
        return Types.VARCHAR;
    }

    @Override
    public String loadRS(ResultSet rs, String name) throws SQLException {
        return rs.getString(name);
    }

    @Override
    public void setPS(PreparedStatement ps, int idx, Object value) throws SQLException {
        if (value == null) {
            ps.setNull(idx, Types.VARCHAR);
        } else {
            ps.setString(idx, value.toString());
        }
    }

    @Override
    public String check(Object o, String dv) {
        return (o != null) ? o.toString() : dv;
    }

    public String sql_value(Object o) {
        if (o != null && !"null".equalsIgnoreCase((o.toString()))) {
            String ret = o.toString();
            ret = ret.replaceAll("'", "''");
            return "'" + ret + "'";
        }
        return "NULL";
    }

}
