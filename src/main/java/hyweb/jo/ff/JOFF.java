package hyweb.jo.ff;

import hyweb.jo.IJOInit;
import hyweb.jo.JOProcObject;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;
import hyweb.jo.util.JOPath;
import hyweb.jo.util.JOUtils;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author william
 */
public class JOFF {

    public static String prefix = "$ff";

    public static String pkg = "hyweb.jo.ff";

    public static String pffId(String id) {
        return prefix + "_" + id;
    }

    public static IJOFF ff(JOProcObject proc, String id) {
        return (IJOFF) proc.get(JOProcObject.p_request, pffId(id), null);
    }

    public static void ff(JOProcObject proc, String id, IJOFF ff) {
        proc.set(JOProcObject.p_request, pffId(id), ff);
    }

    public static IJOFF ff_create(JOProcObject proc, String ffId, JSONObject cfg) {
        try {
            String classId = pkg + "." + ffId;
            Class cls = Class.forName(classId);
            Object o = cls.newInstance();
            ((IJOInit) o).__init__(cfg);
            ((IJOFF) o).init(proc);
            JOPath.set(proc, "$ff:" + cfg.optString("$id"), o);
            return (IJOFF) o;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOLogger.error("Can't find " + cfg);
        }
        return null;
    }

    public static IJOFF create(JOProcObject proc, String id) {
        return create(proc, JOCache.load(new File(proc.base() + "/ff", id + ".json")));
    }

    public static IJOFF create(JOProcObject proc, JSONObject cfg) {
        try {
            String classId = pkg + "." + cfg.optString("$ff");
            Class cls = Class.forName(classId);
            Object o = cls.newInstance();
            ((IJOInit) o).__init__(cfg);
            ((IJOFF) o).init(proc);
            proc.set(JOProcObject.p_request, pffId(cfg.optString("$id")), o);
            JOPath.set(proc, "$ff:" + cfg.optString("$id"), o);
            return (IJOFF) o;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOLogger.error("Can't  create " + cfg);
        }
        return null;
    }

    public static IJOFF ff_create(JOProcObject proc, JOMetadata md, Object line) {
        JSONObject cfg = JOFF.config(md, line);
        String classId = cfg.optString("$ff");
        return ff_create(proc, classId, cfg);
    }

    public static JSONObject config(JOMetadata md, Object line) {
        JSONObject ff = JOUtils.line(line);
        String fid = ff.optString("$id");
        IJOField fld = (md != null) ? md.get(fid) : null;
        if (fld != null) {
            ff.put("$fld", fld.cfg());
        }
        return ff;
    }

    public static Map<String, IJOFF> init_ff(JOProcObject proc, JOMetadata md) {
        Map<String, IJOFF> m = new HashMap<String, IJOFF>();
        JSONArray ja = md.cfg().optJSONArray("$ff");
        if (ja != null) {
            for (int i = 0; i < ja.length(); i++) {
                IJOFF ff = ff_create(proc, md, ja.opt(i));
                m.put(ff.cfg().optString("$id"), ff);
            }
        }
        return m;
    }

    public static String dv(Object fv, String dv) {
        return (fv != null) ? fv.toString().trim() : dv;
    }

}
