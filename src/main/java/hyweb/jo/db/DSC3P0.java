package hyweb.jo.db;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.SQLException;

public class DSC3P0 implements IJODataSource<ComboPooledDataSource> {

    private JSONObject cfg;
    private ComboPooledDataSource ds;

    @Override
    public void init(JSONObject cfg) {
        this.cfg = cfg;
    }

    @Override
    public String id() {
        return (cfg.has("id")) ? cfg.optString("id") : "db";
    }

    @Override
    public ComboPooledDataSource getDataSource() {
        if (ds == null) {
            try {
                ds = new ComboPooledDataSource();
                ds.setUser(cfg.optString("user"));
                ds.setPassword(cfg.optString("password"));
                ds.setDriverClass(cfg.optString("driver"));
                ds.setJdbcUrl(cfg.optString("url"));
                ds.setMaxPoolSize(cfg.optInt("cp30.maxPoolSize", 35));
                ds.setMinPoolSize(cfg.optInt("cp30.minPoolSize", 10));
                ds.setAcquireIncrement(cfg.optInt("cp30.acquireIncrement", 0));
                ds.setMaxIdleTime(cfg.optInt("cp30.maxIdleTime", 300));
                ds.setMaxStatements(cfg.optInt("cp30.maxStatements", 0));
                ds.setIdleConnectionTestPeriod(cfg.optInt("cp30.idleConnectionTestPeriod", 300));
                ds.setMaxIdleTimeExcessConnections(cfg.optInt("cp30.maxIdleTimeExcessConnections", 600));
            } catch (PropertyVetoException ex) {
                JOLogger.error("Can't set c3p0.ComboPooledDataSource : ", ex);
            }
        }
        return ds;
    }

    @Override
    public Connection getConnection() throws SQLException {
        return getDataSource().getConnection();
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (ds != null) {
                ds.close();
            }
        } finally {
            super.finalize();
        }
    }

    @Override
    public void close() throws Exception {
        ds.close();
    }

    @Override
    public String info() {
        return  (ds!=null) ? ds.toString() : "empty" ; 
    }

}
