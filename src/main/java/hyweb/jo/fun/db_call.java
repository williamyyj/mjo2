package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.util.List;

/**
 * @author william 執行預儲
 */
public class db_call extends fb implements IJOFunction<Object, Object[]> {

    @Override
    public Object exec(Object[] args) throws Exception {
        JOProcObject proc = (JOProcObject) args[0];
        String metaId = (String) args[1];
        String actId = (String) args[2];
        return exec(proc, metaId, actId);
    }

    public Object exec(JOProcObject proc, String metaId, String actId) throws Exception {
        JOMetadata metadata = proc.metadata(metaId);
        JSONObject act = metadata.cfg().optJSONObject(actId);
        int ps = actId.lastIndexOf("_");
        String rt = (ps > 0) ? actId.substring(ps + 1) : actId;
        JSONObject mq = this.mq(proc, act,rt);
        return call(proc, mq);
    }

    private List<JSONObject> call(JOProcObject proc, JSONObject mq) throws Exception {
        CallableStatement cs = null;
        ResultSet rs = null;
        try {
            cs = proc.db().connection().prepareCall("{call sp_file_log(?)}");
            fill(cs, mq);
            boolean ret = cs.execute();
            if (ret) {
                rs = cs.getResultSet();
                List<JSONObject> meta = (List<JSONObject>) JOFunctional.exec2("model.FMRsmd2Metadata", proc.db().types(), rs);
                return (List<JSONObject>) JOFunctional.exec2("model.FMRS2Rows", meta, rs);
            }
            return null;
        } finally {
            rs.close();
            cs.close();
        }

    }

    private void fill(CallableStatement cs, JSONObject mq) throws Exception {
        JSONArray fields = mq.optJSONArray(JOConst.param_fields);
        for (int i = 0; i < fields.length(); i++) {
            JSONObject field = fields.optJSONObject(i);
            String name = field.optString("name");
            Object value = field.opt("value");
            cs.setObject(name, value);
        }
    }

}
