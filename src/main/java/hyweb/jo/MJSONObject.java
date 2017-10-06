package hyweb.jo;

import hyweb.jo.org.json.JSONException;
import hyweb.jo.org.json.JSONObject;
import org.mvel2.templates.TemplateRuntime;

/**
 *
 * @author william
 */
public class MJSONObject extends JSONObject {

    @Override
    public String optString(String key, String defaultValue) {
        String ret = super.optString(key, defaultValue);
        Object o = TemplateRuntime.eval(ret, this);
        return (o != null) ? o.toString() : defaultValue;
    }

    @Override
    public Object get(String key) {
        Object ret = super.get(key);
        if (ret instanceof String) {
            ret = TemplateRuntime.eval((String) ret, this);
        }
        return ret;
    }

    @Override
    public Object opt(String key) {
        return key == null ? null : get(key);
    }
    

}
