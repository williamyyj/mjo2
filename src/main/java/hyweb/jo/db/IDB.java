package hyweb.jo.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import hyweb.jo.type.JOTypes;
import hyweb.jo.IJOResource;
import hyweb.jo.org.json.JSONObject;


/**
 * @author william
 * @param <M> 使用的model
 */
public interface IDB<M> extends IJOResource {

    public Object action(M mq) throws Exception;

    public Connection connection() throws SQLException;

    public M row(String sql, Object... params) throws Exception;

    public List<M> rows(String sql, Object... params) throws Exception;

    public Object fun(String sql, Object... params) throws Exception;

    public long execute(String sql, Object... params) throws Exception;

    public int[] batch(String sql, List<Object[]> data) throws Exception;

    public String catalog();

    public String schema();

    public String database();

    public JOTypes types();

    public String to_alias(String text);

    public String to_short(String text);

    public String base();

    public void shutdown() throws Exception;

    public String status();
    
    public JSONObject cfg();
             
}
