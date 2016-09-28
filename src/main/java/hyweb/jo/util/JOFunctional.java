package hyweb.jo.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import hyweb.jo.IJOFunction;
import hyweb.jo.JOConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.fun.MJOBase;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author william
 */
public class JOFunctional {

    private static final String prefix = "hyweb.jo.fun";

    private static LoadingCache<String, IJOFunction> _cache;

    private static String key(String id) {
        if (id != null && !id.startsWith(prefix)) {
            return prefix + "." + id;
        } else {
            return id;
        }
    }

    private static LoadingCache<String, IJOFunction> cache() {
        if (_cache == null) {
            _cache = CacheBuilder
              .newBuilder()
              .maximumSize(1000) // 記憶體中最多保留 1000 筆資料
              .expireAfterWrite(120, TimeUnit.MINUTES) // 資料生命週期為寫入後 120 分鐘
              .build(new JOFunctionCacheLoader());
        }
        return _cache;
    }

    public static Object exec(JOWPObject wp) throws Exception {
        JSONObject act = wp.act();
        return exec(act.optString(JOConst.classId), wp);
    }

    public static Object exec(String id, Object p) throws Exception {
        IJOFunction fun = cache().get(key(id));
        if (fun != null) {
            return fun.exec(p);
        } else {
            return null;
        }
    }

    public static Object mjo(JSONObject wp) throws Exception {
        IJOFunction fun = cache().get(key(MJOBase.act_classId(wp)));
        if (fun != null) {
            return fun.exec(wp);
        } else {
            return null;
        }
    }

    /**
     * 多變數
     *
     * @param id
     * @param args
     * @return
     * @throws Exception
     */
    public static Object exec2(String id, Object... args) throws Exception {
        return exec(id, args);
    }

    public static boolean in(Set fs, Object o) {
        if (fs != null && o != null) {
            return fs.contains(o);
        }
        return false;
    }

    /*
     集合中如果存在  true  不存在新增到集合
     */
    public static boolean ina(Set fs, Object o) {
        if (fs == null) {
            return false;
        }
        if (fs.contains(o)) {
            return true;
        } else {
            fs.add(o);
            return false;
        }
    }

    public static String md5(Object... args) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Object o : args) {
            if (o != null) {
                sb.append(o);
            }
        }
        byte[] buf = sb.toString().getBytes("UTF-8");
        return ENDE.md5(buf);
    }

    public static String sha1(String text) {
        String ret = "";
        if (text != null) {
            return ENDE.sha1(text.getBytes());
        }
        return ret;
    }

    public static Set<Object> load_set(JOProcObject proc, int field, String name, String sql, Object... params) throws Exception {
        List<JSONObject> rows = proc.db().rows(sql, params);
        Set<Object> ret = new HashSet<Object>();
        for (JSONObject row : rows) {
            ret.add(row.opt(name));
        }
        proc.set(field, "s_" + name, ret);
        return ret;
    }

    public static JSONObject json(Map<String, Object> m) {
        return new JSONObject(m);
    }

    public static boolean rdate(Date d, Date a, Date b) {
        if (a == null || b == null || d == null) {
            return false;
        }
        long v = d.getTime();
        return a.getTime() < v && v < b.getTime();
    }

    public static boolean rcdate(Object o, Date a, Date b) {
        Date d = JOCDateFormat.check(o);
        if (a == null || b == null || d == null) {
            return false;
        }
        long v = d.getTime();
        return a.getTime() < v && v < b.getTime();
    }

    public static int cd_comp(Object d1, Object d2) {
        Date a = JOCDateFormat.check(d1);
        Date b = JOCDateFormat.check(d2);
        if (a == null || b == null) {
            return -2;
        }
       return a.compareTo(b);
    }

    public static Number num(Object v) {
        if (v instanceof Number) {
            return (Number) v;
        } else if (v instanceof String) {
            try {
                return Integer.parseInt((String) v);
            } catch (Exception e) {
                return Integer.MIN_VALUE;
            }
        }
        return Integer.MIN_VALUE;
    }

    public static String df(String fmt, Date d) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        return sdf.format(d);
    }

    public static String daf(String fmt, Date d, int field, int value) {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(field, value);
        return sdf.format(c.getTime());
    }

    public static Date cdate(String text) {
        return new Date();
    }

    public static String format(String fmt, Object... args) {
        return String.format(fmt, args);
    }

    public static String encode(String text) {
        try {
            return JOTools.encode(text);
        } catch (Exception ex) {
            return "";
        }
    }

    public static void dmax(JSONObject row, String id, IJOField fld) {
        Object fv = fld.getFieldValue(row);
        if (fv instanceof String) {
            Date d = DateUtil.to_date((String) fv);
            if (d != null) {
                d = DateUtil.date_max(d);
                row.put(id, d);
            }
        }
    }
    
    public static String replace(Object o , String regex , String replace){
        
        return (o!=null) ? ((String)o).replaceAll(regex,replace) :"";
    }

}
