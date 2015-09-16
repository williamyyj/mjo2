package hyweb.jo.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;
import hyweb.jo.IJOFunction;
import hyweb.jo.fun.util.FldComboText;
import hyweb.jo.fun.util.FldComboValue;
import hyweb.jo.fun.util.FldDateFormat;
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

}
