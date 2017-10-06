package org.cc.ff;

import hyweb.jo.IJOInit;
import hyweb.jo.JOProcObject;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOUtils;
import org.cc.module.JOModule;
import static org.cc.module.JOModule.m_ff;

/**
 *
 * @author william
 */
public class CCFF {

    public static String prefix = "$ff";

    public static String pkg = "org.cc.ff";

    public static ICCFF ff(JOModule m, String id) {
        return (ICCFF) m.proc().get(JOProcObject.p_request, JOModule.m_ff + "_" + id, null);
    }

    public static ICCFF ff_create(JOProcObject proc, String ffId, JSONObject cfg) throws Exception {
        try {
            String classId = pkg + "." + ffId;
            //System.out.println("Initial class " + classId);
            Class cls = Class.forName(classId);
            Object o = cls.newInstance();
            ((IJOInit) o).__init__(cfg);
            ((ICCFF) o).init(proc);
            return (ICCFF) o;
        } catch (Exception ex) {
            JOLogger.info("Can't find " + cfg);
        }
        return null;
    }

    public static JSONObject config(JOMetadata md, Object line) {
        JSONObject ff = JOUtils.line(line);
        String fid = ff.optString("$id");
        IJOField fld = md.get(fid);
        JSONObject ddField = null ; 
        if (fld != null) {
            JSONObject cfg = (ddField != null) ? JOUtils.mix(fld.cfg(), ddField) : fld.cfg();
            ff.put("$fld", cfg);
        } else if (ddField != null) {
            ff.put("$fld", ddField);
        }
        return ff;
    }

    public static void init_ffs(JOProcObject proc, JOMetadata md) {
        JSONArray ffs = md.cfg().optJSONArray(m_ff);
        for (Object ffData : ffs) {
            JSONObject cfg = CCFF.config(md, ffData);
            String ffId = m_ff + "_" + cfg.optString("$id");
            String cId = cfg.optString(m_ff);
            try {
                proc.set(JOProcObject.p_request, ffId, CCFF.ff_create(proc, cId, cfg));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

}
