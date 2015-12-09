package hyweb.jo.db;

import hyweb.jo.JOConst;
import java.sql.Connection;
import static hyweb.jo.JOConst.*;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOTypes;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.util.Arrays;

/**
 * @author William
 */
public class DB extends DBBase<JSONObject> {

    public DB(String base) {
        super(base);
    }

    public DB(String base, Connection conn) {
        super(base, conn);
    }

    public DB(String base, String fid, String oid) {
        super(base, fid, oid);
    }

    public DB(String base, JSONObject cfg) {
        super(base, cfg);
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
        if (act_add.equals(act_id) || act_edit.equals(act_id) || act_delete.equals(act_id)) {
            return execute(sql, values);
        } else if (act_row.equals(act_id)) {
            return row(sql, values);
        } else if (act_fun.equals(act_id)) {
            return fun(sql, values);
        } else if (act_list.equals(act_id) || act_rows.equals(act_id) || act_all.equals(act_id)) {
            return rows(sql, values);
        }
        return null;
    }

}
