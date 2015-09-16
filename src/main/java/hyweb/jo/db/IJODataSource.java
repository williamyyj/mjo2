package hyweb.jo.db;

import hyweb.jo.org.json.JSONObject;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author William
 * @param <E>
 */

public interface IJODataSource<E> {
    public void init(JSONObject cfg) ; 
    public String id();
    public E getDataSource();
    public Connection getConnection()  throws SQLException ;
    public void close()   throws Exception  ;
    public String info();
}
