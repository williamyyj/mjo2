package hyweb.jo;

import hyweb.jo.db.DB;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import java.util.List;

public class JOProcObject extends JSONObject {

    public final static int ParameterValue = 0;
    public final static int RequestValue = 1;
    public final static int SessionValue = 2;
    public final static int ApplicationValue = 3;

    public JOProcObject(String base) {
        put(JOProcConst.base, base);
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
            case ParameterValue:
                return params().has(name) ? params().opt(name) : dv;
            case RequestValue:
                return has("$req_" + name) ? opt("$req_" + name) : dv;
            case SessionValue:
                return has("$sess_" + name) ? opt("$sess_" + name) : dv;
            case ApplicationValue:
                return has("$app_" + name) ? opt("$app_" + name) : dv;
        }
        return dv;
    }

    public Object set(int fld, String name, Object value) {
        switch (fld) {
            case ParameterValue:
                return params().put(name, value);
            case RequestValue:
                return m().put("$req_" + name, value);
            case SessionValue:
                return m().put("$sess_" + name, value);
            case ApplicationValue:
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

    public List<IJOField> getFields(String mid, String scope) {
        JOMetadata metadata = new JOMetadata(base(), mid);
        return metadata.getFieldsByScope(scope);
    }

    public JOMetadata metadata(String mid) {
        return new JOMetadata(base(), mid);
    }

    public void release() {
        if (db() != null) {
            db().close();
        }
    }

    public Object find(String name, Object dv) {
        Object o = get(ParameterValue, name, null);
        if (o != null) {
            return o;
        }
        o = get(RequestValue, name, null);
        if (o != null) {
            return o;
        }
        o = get(SessionValue, name, null);
        if (o != null) {
            return o;
        }
        o = get(ApplicationValue, name, null);
        return (o!=null) ? o : dv ; 
    }

}
