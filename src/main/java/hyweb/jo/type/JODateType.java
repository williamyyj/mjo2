package hyweb.jo.type;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author William
 */

public class JODateType extends JOType<Date> {
    
    @Override
    public String dt() {
        return dt_date;
    }

    @Override
    public Date check(Object o, Date dv) {
        if (o instanceof Date) {
            return (Date) o;
        } else if (o instanceof String) {
            String str = (String) o;
            try {
                if (str.length() == 8) {
                    return sfmt().parse(str);
                } else if (str.length() == 14) {
                    return lfmt().parse(str);
                } else {
                    log.debug("Can't cast date : " + o);
                    return dv;
                }
            } catch (Exception e) {
                log.debug("Can't cast date : " + o);
                return dv;
            }
        }
        return dv;
    }

    public Date check(Object o, String fmt) {
        if (o instanceof Date) {
            return ((Date) o);
        } else if (o instanceof String) {
            try {
                String text = o.toString().trim();
                SimpleDateFormat sdf = new SimpleDateFormat(fmt);
                return sdf.parse(text);
            } catch (ParseException ex) {
                log.debug("Can't cast date : " + o);
            }
        }
        return null;
    }

    @Override
    public Date loadRS(ResultSet rs, String name) throws SQLException {
        java.sql.Timestamp ts = rs.getTimestamp(name);
        if (ts != null) {
            return new Date(ts.getTime());
        }
        return null;
    }

    @Override
    public void setPS(PreparedStatement ps, int idx, Object value) throws SQLException {
        Date d = check(value);
        if (value == null) {
            ps.setNull(idx, Types.TIMESTAMP);
        } else {
            java.sql.Timestamp ts = new java.sql.Timestamp(d.getTime());
            ps.setTimestamp(idx, ts);
        }
    }

    public Class<?> nativeClass() {
        return Date.class;
    }

    @Override
    public int jdbc() {
        return Types.TIMESTAMP;
    }

    protected SimpleDateFormat sfmt() {
        return new SimpleDateFormat(fmt_date);
    }

    protected SimpleDateFormat lfmt() {
        return new SimpleDateFormat(fmt_datetime);
    }

  

   
}
