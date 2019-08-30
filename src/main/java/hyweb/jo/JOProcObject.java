package hyweb.jo;

import hyweb.jo.db.DB;
import hyweb.jo.ff.IJOFF;
import hyweb.jo.ff.JOFF;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;
import hyweb.jo.util.JOTools;
import java.io.File;
import java.util.List;

/**
 * $fp 表單參數 (表單參數)
 *
 * @author william
 */
public class JOProcObject extends JSONObject {

    public final static int p_self = 0;
    public final static int p_params = 1;
    public final static int p_request = 2;
    public final static int p_session = 3;
    public final static int p_app = 4;
    private JSONObject cfg;
    private JOWorkMeta wMeta;
    private JOWorkData wData; // 資料 in -->  out 
    private JOWorkStatus wStatus;  // 異常及物件狀態

    public JOProcObject(String base) {
        put(JOProcConst.base, base);
        if (base != null && base.length() > 0) {
            String upload = cfg().optString("uploadPath") + cfg().optString("pid");
            put(JOProcConst.w_upload, upload);
        }
    }

    /**
     *   支援多個Database
     * @param base
     * @param oId 
     */
    public JOProcObject(String base, String oId) {
        this(base);
          put(JOProcConst.db, new DB(base(),null,oId));
    }

    public String base() {
        return optString(JOProcConst.base);
    }

    public JSONObject params() {
        if (!has(JOProcConst.wp)) {
            put(JOProcConst.wp, new JSONObject());
        }
        return optJSONObject(JOProcConst.wp);
    }

    /*
     *   monk object 
     */
    public Object get(int fld, String name, Object dv) {
        switch (fld) {
            case p_self:
                return (has(name)) ? get(name) : dv;
            case p_params:
                return params().has(name) ? params().opt(name) : dv;
            case p_request:
                return has("$req_" + name) ? opt("$req_" + name) : dv;
            case p_session:
                return has("$sess_" + name) ? opt("$sess_" + name) : dv;
            case p_app:
                return has("$app_" + name) ? opt("$app_" + name) : dv;
        }
        return dv;
    }

    public Object set(int fld, String name, Object value) {
        switch (fld) {
            case p_self:
                return put(name, value);
            case p_params:
                return params().put(name, value);
            case p_request:
                return m().put("$req_" + name, value);
            case p_session:
                return m().put("$sess_" + name, value);
            case p_app:
                return m().put("$app_" + name, value);
        }
        return null;
    }

    public DB db() {
        if (!has(JOProcConst.db)) {
            put(JOProcConst.db, new DB(base()));
        }
        return (DB) opt(JOProcConst.db);
    }

    /**
     * @param mid
     * @param scope
     * @return
     * @deprecated 請改用 JOMetaUtils
     */
    @Deprecated
    public List<IJOField> getFields(String mid, String scope) {
        JOMetadata metadata = new JOMetadata(base(), mid);
        return metadata.getFieldsByScope(scope);
    }

    /**
     * @param mid
     * @return
     * @deprecated 請改用 wMeta() ( JOWorkMeta）
     */
    @Deprecated
    public JOMetadata metadata(String mid) {
        return wMeta().metadata(mid);
    }

    public void release() {
        if (db() != null) {
            db().close();
        }
    }

    public Object find(String name, Object dv) {
        if (has(name)) {
            return opt(name);
        }
        Object o = get(p_params, name, null);
        if (o != null) {
            return o;
        }
        o = get(p_request, name, null);
        if (o != null) {
            return o;
        }
        o = get(p_session, name, null);
        if (o != null) {
            return o;
        }
        o = get(p_app, name, null);
        return (o != null) ? o : dv;
    }

    public String ap_root() {
        String root = System.getProperty("ap_root", new File(".").toString());
        return System.getProperty("catalina.base", root);
    }

    public String scope() {
        return (String) get(p_app, JOProcConst.w_scope, "dev");
    }

    public String pid() {
        return (String) get(p_app, JOProcConst.w_pid, "");
    }

    public JSONObject cfg() {
        if (cfg == null) {
            cfg = JOCache.load(base(), "cfg");
        }
        return cfg;
    }

    public void add_params(String text) {
        JSONObject jo = JOTools.loadString(text);
        if (jo != null) {
            JSONArray names = jo.names();
            for (int i = 0; i < names.length(); i++) {
                String name = names.optString(i);
                this.params().put(name, jo.opt(name));
            }
        }
    }

    public JOWorkData wData() {
        if (wData == null) {
            wData = new JOWorkData(this);
        }
        return wData;
    }

    public JOWorkStatus wStatus() {
        if (wStatus == null) {
            wStatus = new JOWorkStatus(this);
        }
        return wStatus;
    }

    public JOWorkMeta wMeta() {
        if (wMeta == null) {
            wMeta = new JOWorkMeta(this);
        }
        return wMeta;
    }

    public Object ff(String id, Object fv) {
        IJOFF ff = (IJOFF) get(JOProcObject.p_request, JOFF.pffId(id), null);
        return (ff != null) ? ff.cast(fv) : "";
    }

    public JSONObject fp() {
        if (!has(JOProcConst.fp)) {
            put(JOProcConst.fp, new JSONObject());
        }
        return optJSONObject(JOProcConst.fp);
    }

}
