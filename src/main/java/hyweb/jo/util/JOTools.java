package hyweb.jo.util;

import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONException;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.org.json.JSONTokener;
import hyweb.jo.org.json.XML;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

public class JOTools {

    public static JSONObject jo(File f) throws Exception {
        return JOCache.load(f);
    }

    public static synchronized JSONObject loadString(String text) {
        try {
            return JOCache.loadJSON(text);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static synchronized JSONObject load(File f) {
        return JOCache.load(f);
    }

    public static synchronized JSONObject xml(String text) {
        try {
            return XML.toJSONObject(text);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static JSONObject obj_json(Object ref) throws Exception {
        JSONObject jo = new JSONObject();
        if (ref != null) {
            Field[] flds = ref.getClass().getFields();
            for (Field fld : flds) {
                Class<?> type = fld.getType();
                if (type.equals(String.class)) {
                    jo.put(fld.getName(), fld.get(ref));
                } else {
                    Object o = fld.get(ref);
                    if (o instanceof List) {
                        jo.put(fld.getName(), obj_ja(o));
                    } else if (o instanceof Map) {
                        jo.put(fld.getName(), obj_jo(o));
                    } else {
                        jo.put(fld.getName(), obj_json(o));
                    }
                }
            }
        }
        return jo;
    }

    public static JSONObject obj_jo(Object o) throws Exception {
        JSONObject jo = new JSONObject();
        System.out.println(o.getClass());
        Map m = (Map) o;
        Set<Map.Entry> list = m.entrySet();
        for (Map.Entry e : list) {
            Object v = e.getValue();
            String k = e.getKey().toString();
            if (v instanceof String) {
                jo.put(k, v);
            } else if (v instanceof List) {
                jo.put(k, obj_ja(v));
            } else if (v instanceof Map) {
                jo.put(k, obj_jo(v));
            } else {
                jo.put(k, obj_json(v));
            }
        }
        return jo;
    }

    public static JSONArray obj_ja(Object o) throws Exception {
        JSONArray arr = new JSONArray();
        List list = (List) o;
        for (Object item : list) {
            if (item instanceof String) {
                arr.put(item);
            } else if (item instanceof List) {
                arr.put(obj_ja(item));
            } else if (item instanceof Map) {
                arr.put(obj_jo(item));
            } else {
                arr.put(obj_json(item));
            }
        }
        return arr;
    }

    public static String encode(String text) throws Exception {
        if (text == null || text.length() == 0) {
            return "";
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        gzip = new GZIPOutputStream(out);
        gzip.write(text.getBytes("UTF-8"));
        gzip.close();
        return Base64.u64_encode(out.toByteArray());
    }

    public static String decode(String text) throws Exception {
        if (text == null || text.length() == 0) {
            return "";
        }
        byte[] buf = Base64.u64_decode(text);
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(buf));
        BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public static JSONObject decode_jo(String ejo) {
        try {
            return loadString(decode(ejo));
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public static JSONObject toJSONObject(String text) {
        try {
            if (text == null || text.length() == 0) {
                return null;
            }
            return new JSONObject(new JSONTokener(new StringReader(text)));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Object[] to_array(JSONObject jo, String id) {
        JSONArray ja = jo.optJSONArray(id);
        if (ja != null) {
            return ja.m().toArray();
        }
        return null;
    }

    public static Object[] to_marray(JSONObject jo, String id, String vid) {
        JSONArray ja = jo.optJSONArray(id);
        if (ja != null) {
            Object[] ret = new Object[ja.length()];
            for(int i=0;i<ja.length();i++){
                JSONObject row = ja.optJSONObject(i);
                if(row!=null && row.has(vid)){
                    ret[i] = row.opt(vid);
                }
            }
            return ret ; 
        }
        return null;
    }

    public static <E> E obj(Class<E> c, JSONObject jo, String id) {
        return (E) jo.opt(id);
    }

    public static void set_default(JSONObject cfg, String name, Object value) {
        if (!cfg.has(name)) {
            cfg.put(name, value);
        }
    }
    
   

}
