package hyweb.jo.util;

import hyweb.jo.log.JOLogger;
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

    public static void rename(JSONObject jo, String n_old, String n_new) {
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
        if (ja != null) {
            for (int i = 0; i < ja.length(); i++) {
                JSONObject row = ja.optJSONObject(i);
                if (row != null) {
                    if (value.equalsIgnoreCase(row.optString("name"))) {
                        return row;
                    }
                }
            }
        }
        return null;
    }

    public static JSONObject line(Object line) {
        if (line instanceof JSONObject) {
            return (JSONObject) line;
        } else if (line instanceof String) {
            String text = (String) line;
            text = (text.charAt(0) == '{' ? text : "{" + text + "}");
            return JOTools.toJSONObject(text);
        }
        return null;
    }

    public static JSONArray toJA(Object o) {
        if (o instanceof JSONArray) {
            return (JSONArray) o;
        } else if (o instanceof String) {
            String text = (String) o;
            text = (text.charAt(0) == '[' ? text : "[" + text + "]");
            JOLogger.debug("text:"+text);
            return JOTools.toJSONArray(text);
        }
        return null;
    }

    public static String md5(JSONObject row, String[] items) {
        try {

            StringBuilder sb = new StringBuilder();
            for (String item : items) {
                sb.append(row.optString(item)).append(":::");
            }
            JOLogger.debug(sb);
            return ENDE.md5(sb.toString().getBytes("UTF-8"));
        } catch (Exception e) {
            return null;
        }
    }

    public static String md5(JSONObject row, String line) {
        return md5(row, line.split("[,|:]"));
    }

}
