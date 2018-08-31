package hyweb.jo;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author William
 * @param <E>
 */
public interface IJOType<E> {

    public final static String dt_var = "var";
    public final static String dt_bool = "bool";
    public final static String dt_int = "int";
    public final static String dt_long = "long";
    public final static String dt_double = "double";
    public final static String dt_date = "date";
    public final static String dt_string = "string";
    public final static String dt_nvarchar = "nvarchar";
    public final static String dt_blob = "blob";
    public final static String dt_clob = "clob";
    public final static String dt_array = "array";
    public final static String dt_object = "object";
    public final static String fmt_datetime = "yyyy-MM-dd HH:mm:ss";
    public final static String fmt_date = "yyyy-MM-dd";

    public String dt();

    public int jdbc();

    public E loadRS(ResultSet rs, String name) throws SQLException;

    public void setPS(PreparedStatement ps, int idx, Object value) throws SQLException;

    public E check(Object o);

    public E check(Object o, E dv);

    public String sql_string(Object o, String fmt);

    public String json_string(Object o, String fmt);

    public String xml_string(Object o, String fmt);

}
