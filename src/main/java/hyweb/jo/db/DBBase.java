package hyweb.jo.db;

import hyweb.jo.JOConfig;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOTypes;
import hyweb.jo.util.JOCache;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.util.Set;

/**
 *
 * @author william
 * @param <M>
 */
public abstract class DBBase<M> implements IDB<M>, DBMBean {

    protected static int connCount;

    protected static Map<String, IJODataSource> mds;
    protected String base;
    protected Connection conn;
    protected JSONObject cfg;
    protected boolean is_reference = false;
    //protected IJOBiFunction<Object, PreparedStatement, Object[]> row_fill;
    protected JOTypes types;

    public DBBase(String base) {
        this(base, null, null);
    }

    public DBBase(String base, String cfgId, String oid) {
        this.base = (base == null) ? System.getProperty("base") : base;
        JSONObject root = (cfgId != null) ? JOCache.load(base, cfgId) : JOCache.load(base, "cfg");
        oid = (oid == null) ? "db" : oid;
        cfg = root.optJSONObject(oid);
        if (cfg == null) {
            cfg = new JOConfig(base, oid).params();
            if (cfg == null) {
                cfg = new JSONObject();
            }
        }
        init_components();
    }

    public DBBase(String base, Connection conn) {
        this(base);
        if (conn != null) {
            is_reference = true;
            this.conn = conn;
        }
        init_components();
    }

    public DBBase(String base, JSONObject cfg) {
        this.base = base;
        this.cfg = cfg;
        init_components();
    }

    protected void init_components() {
        cfg.put("base", this.base);
        // will using  JOEval 
        if (cfg.has("url")) {
            String url = cfg.optString("url");
            url = url.replace("${base}", this.base);
            cfg.put("url", url);
        }
        JOTools.set_default(cfg, "@mrow", "model.FMRS2Row");
        JOTools.set_default(cfg, "@mrows", "model.FMRS2Rows");
        JOTools.set_default(cfg, "@mfill", "model.FMPSFill");
        JOTools.set_default(cfg, "@alias", "util.FldAlias");
        JOTools.set_default(cfg, "@short", "util.FldShort");
        JOTools.set_default(cfg, "ds", "hyweb.jo.db.DSC3P0");
    }

    @Override
    public Connection connection() throws SQLException {
        if (conn == null) {
            conn = ds().getConnection();
            connCount++;
        }
        return conn;
    }

    protected String id() {
        return (cfg.has("id")) ? cfg.optString("id") : "db";
    }

    protected IJODataSource ds() {
        String id = id();
        IJODataSource ds = mds().get(id);
        if (ds == null) {
            String classId = cfg.optString("ds");
            JOLogger.info("classId : " + classId);
            try {
                ds = (IJODataSource) Class.forName(classId).newInstance();
                ds.init(cfg);
                mds.put(id, ds);
            } catch (Exception ex) {
                JOLogger.error("", ex);
            }
        }
        return ds;
    }

    protected Map<String, IJODataSource> mds() {
        if (mds == null) {
            mds = new HashMap();
        }
        return mds;
    }

    protected static void __release(ResultSet rs) throws SQLException {
        if (rs != null) {
            rs.close();
            rs = null;
        }
    }

    protected static void __release(PreparedStatement ps) throws SQLException {
        if (ps != null) {
            ps.close();
            ps = null;
        }
    }

    protected void __release(Connection conn) throws SQLException {
        if (conn != null) {
            conn.close();
            connCount--;
            conn = null;
        }
    }

    @Override
    public void close() {
        try {
            if (!is_reference) {
                __release(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject cfg() {
        return cfg;
    }

    @Override
    public String base() {
        return base;
    }

    @Override
    public String catalog() {
        return cfg.optString("catalog");
    }

    @Override
    public String schema() {
        return cfg.optString("schema");
    }

    @Override
    public String database() {
        return (cfg != null) ? cfg.optString("database") : null;
    }

    @Override
    public M row(String sql, Object... params) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = connection().prepareStatement(sql);
            JOFunctional.exec2(cfg.optString("@mfill"), ps, params);
            rs = ps.executeQuery();
            List<JSONObject> metadata = (List<JSONObject>) JOFunctional.exec2("model.FMRsmd2Metadata", types(), rs);
            return (rs.next()) ? (M) JOFunctional.exec2(cfg.optString("@mrow"), metadata, rs) : null;
        } finally {
            __release(rs);
            __release(ps);
        }
    }

    @Override
    public List<M> rows(String sql, Object... params) throws Exception {
        PreparedStatement ps = connection().prepareStatement(sql);
        List<M> list = new ArrayList<M>();
        ResultSet rs = null;
        try {
            JOFunctional.exec2(cfg.optString("@mfill"), ps, params);
            rs = ps.executeQuery();
            List<JSONObject> metadata = (List<JSONObject>) JOFunctional.exec2("model.FMRsmd2Metadata", types(), rs);
            return (List<M>) JOFunctional.exec2(cfg.optString("@mrows"), metadata, rs);
        } finally {
            __release(ps);
            __release(rs);
        }
    }

    @Override
    public Object fun(String sql, Object... params) throws Exception {
        PreparedStatement ps = connection().prepareStatement(sql);
        ResultSet rs = null;
        try {
            JOFunctional.exec2(cfg.optString("@mfill"), ps, params);
            rs = ps.executeQuery();
            return (rs.next()) ? rs.getObject(1) : null;
        } finally {
            __release(ps);
            __release(rs);
        }
    }

    @Override
    public long execute(String sql, Object... params) throws Exception {
        //  多個Statement 一般是回傳mssql 的 identify 
        PreparedStatement ps = connection().prepareStatement(sql);
        ResultSet rs = null;
        try {
            JOFunctional.exec2(cfg.optString("@mfill"), ps, params);
            ps.execute();
            long ret = 0;
            if (ps.getMoreResults()) {
                rs = ps.getResultSet();
                if (rs != null && rs.next()) {
                    ret = rs.getLong(1);
                }
            }
            return ret;
        } finally {
            __release(rs);
            __release(ps);
        }
    }

    public long executeUpdate(String sql, Object... params) throws Exception {
        //  理論上不該使用多個Statement
        PreparedStatement ps = connection().prepareStatement(sql);
        ResultSet rs = null;
        try {
            JOFunctional.exec2(cfg.optString("@mfill"), ps, params);
            long ret = ps.executeUpdate();
            if (ps.getMoreResults()) {
                rs = ps.getResultSet();
                if (rs != null && rs.next()) {
                    ret = rs.getLong(1);
                }
            }
            // rs = ps.getGeneratedKeys();  can't work 
            return ret;
        } finally {
            __release(rs);
            __release(ps);
        }
    }

    @Override
    public int[] batch(String sql, List<Object[]> data) throws Exception {
        if (data == null) {
            return null;
        }
        boolean flag = connection().getAutoCommit();
        PreparedStatement ps = connection().prepareStatement(sql);
        try {
            for (Object[] row : data) {
                ps.clearParameters();
                JOFunctional.exec2(cfg.optString("@mfill"), ps, row);
                ps.addBatch();
            }
            int[] ret = ps.executeBatch();
            connection().commit();
            return ret;
        } catch (SQLException se) {
            try {
                connection().rollback();
            } catch (SQLException ex) {
                JOLogger.error(sql, ex);
            }
        } finally {
            connection().setAutoCommit(flag);
        }
        return null;
    }

    @Override
    public boolean isActived() {
        try {
            return (conn != null && !conn.isClosed());
        } catch (SQLException ex) {
            return false;
        }
    }

    @Override
    public void open() throws Exception {
        connection();
    }

    /**
     *
     */
    public void shutdown() throws Exception {
        if (mds != null) {
            Set<Map.Entry<String, IJODataSource>> entry = mds.entrySet();
            for (Map.Entry<String, IJODataSource> e : entry) {
                e.getValue().close();
            }
        }
    }

    @Override
    public String status() {
        return "{  \"conn\":" + connCount + " \"mds\":" + mds + "\"}";
    }

    @Override
    public String getDbStatus() {
        return "{  \"conn\":" + connCount + " \"mds\":" + mds + "\"}";
    }

    public static String info() {
        return "{  \"conn\":" + connCount + " \"mds\":" + mds + "\"}";
    }

}
