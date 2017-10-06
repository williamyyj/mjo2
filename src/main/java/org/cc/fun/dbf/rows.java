package org.cc.fun.dbf;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import org.cc.module.ICCDBF;

/**
 *
 * @author william
 */
public class rows extends DBFBase implements ICCDBF<List<JSONObject>> {

    @Override
    public List<JSONObject> apply(JOProcObject proc, String cmd, Object[] params) throws Exception {
        PreparedStatement ps = null;
        ResultSet rs = null;
        String idRows = proc.db().cfg().optString("@mrows", "model.FMRS2Rows");
        String idFill = proc.db().cfg().optString("@mfill", "model.FMPSFill");
        String idRS2Metadata = "model.FMRsmd2Metadata";
        try {
            ps = proc.db().connection().prepareStatement(cmd);
            JOFunctional.exec2(idFill, ps, params);
            rs = ps.executeQuery();
            List<JSONObject> metadata = (List<JSONObject>) JOFunctional.exec2(idRS2Metadata, proc.db().types(), rs);
            return (List<JSONObject>) JOFunctional.exec2(idRows, metadata, rs);
        } finally {
            __release(ps);
            __release(rs);
        }

    }

}
