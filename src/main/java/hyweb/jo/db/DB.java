package hyweb.jo.db;

import hyweb.jo.JOConst;
import java.sql.Connection;
import static hyweb.jo.JOConst.*;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOTypes;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.lang.management.ManagementFactory;
import java.sql.SQLException;
import javax.management.MBeanServer;
import javax.management.ObjectName;

/**
 * @author William
 */
public class DB extends DBBase<JSONObject> {

    public DB(String base) {
        super(base);
        init_jmx();
    }

    public DB(String base, Connection conn) {
        super(base, conn);
        init_jmx();
    }

    public DB(String base, String oid) {
        super(base, null, oid);
        init_jmx();
    }

    public DB(String base, String fid, String oid) {
        super(base, fid, oid);
        init_jmx();
    }

    public DB(String base, JSONObject cfg) {
        super(base, cfg);
        init_jmx();
    }

    @Override
    protected void init_components() {
        super.init_components();
        try {
            types = new JOTypes(database());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public JOTypes types() {
        return types;
    }

    @Override
    public String to_alias(String text) {
        try {
            return (String) JOFunctional.exec(cfg.optString("@alias"), text);
        } catch (Exception ex) {
            JOLogger.error("to_alias error", ex);
        }
        return text;
    }

    @Override
    public String to_short(String text) {
        try {
            return (String) JOFunctional.exec(cfg.optString("@short"), text);
        } catch (Exception ex) {
            JOLogger.error("to_short error", ex);
        }
        return text;
    }

    @Override
    public Object action(JSONObject mq) throws Exception {
        String act_id = mq.optString(act);
        String sql = mq.optString(param_sql, null);
        Object[] values = JOTools.to_marray(mq, JOConst.param_fields, "value");
        if (act_add.equals(act_id)) {
            return execute(sql, values);
        } else if (act_edit.equals(act_id) || act_delete.equals(act_id)) {
            return executeUpdate(sql, values);
        } else if (act_row.equals(act_id)) {
            return row(sql, values);
        } else if (act_fun.equals(act_id)) {
            return fun(sql, values);
        } else if (act_list.equals(act_id) || act_rows.equals(act_id) || act_all.equals(act_id)) {
            return rows(sql, values);
        }
        return null;
    }

    private void init_jmx() {
        try {
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            String objId = "hyweb.jo.db:name=" + this.toString();
            ObjectName name = new ObjectName(objId);
            mbs.registerMBean(this, name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        try {
            super.close();
            MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();
            String objId = "hyweb.jo.db:name=" + this.toString();
            ObjectName name = new ObjectName(objId);
            mbs.unregisterMBean(name);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
