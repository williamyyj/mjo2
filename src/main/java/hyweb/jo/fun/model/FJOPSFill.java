package hyweb.jo.fun.model;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import hyweb.jo.IMProcess;
import hyweb.jo.IJOType;
import hyweb.jo.JOConst;
import hyweb.jo.db.IDB;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;

/**
 * @author William
 */
public class FJOPSFill implements IMProcess<JSONObject> {

    @Override
    public void proc(JSONObject m) throws Exception {
        System.out.println(m);
        if (m.has(JOConst.param_fields)) {
            PreparedStatement ps = JOTools.obj(PreparedStatement.class, m, JOConst.param_ctrl);
            IDB db = JOTools.obj(IDB.class, m, JOConst.param_dp);
            proc_fill_all(db, ps, m.optJSONArray(JOConst.param_fields));
        }
    }

    private void proc_fill_all(IDB db, PreparedStatement ps, JSONArray fields) throws SQLException {
        int len = fields.length();
        for (int i = 0; i < len; i++) {
            JSONObject fld = fields.optJSONObject(i);
            System.out.println(fld);
            Object dt = fld.opt("dt");
            Object value = fld.opt("value");
            IJOType<?> type = db.types().type(dt);
            type.setPS(ps, i + 1, value);
        }
    }

}
