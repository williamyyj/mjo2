package hyweb.jo.util;


import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;


/**
 * @author William
 */
public class JOPath {

    private static Object opt(Object m, String k) {
        if (m instanceof JSONObject) {
            return ((JSONObject) m).opt(k);
        } else if (m instanceof JSONArray) {
            return ((JSONArray) m).get(Integer.parseInt(k.trim()));
        }
        return null;
    }

    
    
    public static Object path(JSONObject jo, String jopath) {
        String[] path = jopath.split(":");
        return path(jo, path);
    }

    private static Object path(JSONObject jo, String[] path) {
        Object p = jo;
        for (String key : path) {
            p = opt(p, key);
            if (p == null) {
                break;
            }
        }
        return p;
    }

    public static void set(JSONObject target, String path, Object o) {
        String[] items = path.split(":");
        set(target, items, 0, o);
    }

    private static void set(JSONObject parent, String[] items, int level, Object o) {
        if (level >= (items.length - 1)) {
            parent.put(items[level], o);
        } else {
            String key = items[level];
            JSONObject p = null;
            if (parent.has(key)) {
                p = parent.optJSONObject(key);
            } else {
                p = new JSONObject();
                parent.put(key, p);
            }
            set(p, items, level + 1, o);
        }
    }
    
    public static void setJA(JSONObject jo, String path , Object o) {
        Object item = path(jo,path);
        if(item instanceof JSONArray){
            ((JSONArray)item).put(o);
        } else if ( item == null){
            JSONArray ja = new JSONArray();
            set(jo,path, ja);
            ja.put(o);
        }
    }
    
  

}
