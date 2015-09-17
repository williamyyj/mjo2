package hyweb.jo.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.fun.MJOBase;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import java.io.UnsupportedEncodingException;
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

    public static boolean ina(Set fs, Object o) {
        boolean ret = false;
        if (fs != null && o != null) {
            ret = fs.contains(o);
        }
        if(!ret){
            fs.add(o);
        }
        return ret ; 
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

    public static Set<Object> load_set(JOProcObject proc, int field, String name, String sql, Object... params) throws Exception {
        List<JSONObject> rows = proc.db().rows(sql, params);
        Set<Object> ret = new HashSet<Object>();
        for (JSONObject row : rows) {
            ret.add(row.opt(name));
        }
        proc.set(field, "s_" + name, ret);
        return ret ; 
    }
    
    public static JSONObject json(Map<String,Object> m){
        return new JSONObject(m);
    }
    
   
}
