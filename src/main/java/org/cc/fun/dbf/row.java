package org.cc.fun.dbf;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import org.cc.module.ICCDBF;
import static org.cc.fun.dbf.DBFBase.__release;

/**
 *
 * @author william
 */
public class row extends DBFBase implements ICCDBF<JSONObject> {

    @Override
    public JSONObject apply(JOProcObject proc, String cmd, Object[] params) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String idRow = proc.db().cfg().optString("@mrow");
        String idFill = proc.db().cfg().optString("@mfill", "model.FMPSFill");
        String idRS2Metadata = "model.FMRsmd2Metadata";
        try {
            ps = proc.db().connection().prepareStatement(cmd);
            JOFunctional.exec2(idFill, ps, params);
            rs = ps.executeQuery();
            List<JSONObject> metadata = (List<JSONObject>) JOFunctional.exec2(idRS2Metadata, proc.db().types(), rs);
            return  (rs.next()) ? (JSONObject) JOFunctional.exec2(idRow, metadata, rs) : null;
        } finally {
            __release(rs);
            __release(ps);
        }
    }

}
