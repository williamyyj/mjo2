package hyweb.jo.util;

import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.util.Iterator;
import java.util.Map;

/**
 *
 * @author william
 */
public class JOUtils {

    public static void rm(JSONObject jo, String n_old, String n_new) {
        if (jo.has(n_old)) {
            Object o = jo.remove(n_old);
            jo.put(n_new, o);
        }
    }

    public static JSONObject mix(JSONObject child, JSONObject parent) {
        // 保留 child 的值單層不 recursive 
        if (parent == null && child != null) {
            return (JSONObject) child.clone();
        }

        if (parent != null && child == null) {
            return (JSONObject) parent.clone();
        }

        if (child != null && parent != null) {
            JSONObject ret = new JSONObject(parent);
            Iterator<Map.Entry<String, Object>> ite = child.entrySet().iterator();
            while (ite.hasNext()) {
                Map.Entry<String, Object> e = ite.next();
                ret.put(e.getKey(), e.getValue());
            }
            return ret;
        }
        return null;
    }

    public static JSONObject get(JSONArray ja, String name, String value) {
        for (int i = 0; i < ja.length(); i++) {
            JSONObject row = ja.optJSONObject(i);
            if (row != null) {
                if (value.equalsIgnoreCase(row.optString("name"))) {
                    return row;
                }
            }
        }
        return null;
    }

}
