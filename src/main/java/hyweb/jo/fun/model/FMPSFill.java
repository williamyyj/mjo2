package hyweb.jo.fun.model;

import hyweb.jo.JOStatus;
import hyweb.jo.log.JOLogger;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.Date;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunction;

/**
 *
 * @author william
 */
public class FMPSFill extends JOFunction<JOStatus> {

    private volatile boolean pmdKnownBroken = false;

    @Override
    public JOStatus exec(Object[] args) throws Exception {
        JOStatus status = new JOStatus();
        PreparedStatement ps = (PreparedStatement) args[0];
        Object[] params = (Object[]) args[1];
        ParameterMetaData pmd = null;

        // nothing to do here
        if (params == null) {
            return status;
        }

        if (!pmdKnownBroken) {
            pmd = ps.getParameterMetaData();
            int stmtCount = pmd.getParameterCount();
            int paramsCount = params.length;

            if (stmtCount != paramsCount) {
                throw new SQLException("Wrong number of parameters: expected "
                  + stmtCount + ", was given " + paramsCount);
            }
        }

        for (int i = 0; i < params.length; i++) {

            if (params[i] != null) {
                Object value = params[i];
                // JOLogger.debug("===== value : " + value);
                if (value instanceof Date) {
                    ps.setTimestamp(i + 1, new Timestamp(((Date) value).getTime()));
                } else if (value instanceof JSONArray) {
                    value = ((JSONArray) value).toString();
                    ps.setObject(i + 1, value);
                } else if (value instanceof JSONObject) {
                    value = ((JSONObject) value).toString();
                    ps.setObject(i + 1, value);
                } else {
                    ps.setObject(i + 1, value);
                }
            } else {
                int sqlType = Types.VARCHAR;
                if (!pmdKnownBroken) {
                    try {
                        //System.out.println("===== chk type name : " + pmd.getParameterTypeName(i + 1));
                        //System.out.println("===== chk type code : " + pmd.getParameterType(i + 1));
                        sqlType = pmd.getParameterType(i + 1);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        pmdKnownBroken = true;
                    }
                }
                ps.setNull(i + 1, sqlType);
            }
        }
        return null;
    }

    private void fill_pmd(PreparedStatement ps, ParameterMetaData pmd, Object[] params) {

    }

}
