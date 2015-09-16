package hyweb.jo;

import hyweb.jo.db.DB;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import java.util.List;

public class JOProcObject extends JSONObject {

    public final static int p_self = 0;
    public final static int p_params = 1;
    public final static int p_request = 2;
    public final static int p_session = 3;
    public final static int p_app = 4;
    
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
            case p_self :
                    return (has(name))? get(name) : dv ; 
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
            case p_self  : 
                return put(name,value);
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
        if(has(name)){
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
    
    
    

}
