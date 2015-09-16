package hyweb.jo.util;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import hyweb.jo.data.JOFile;
import java.io.File;
import java.io.IOException;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.org.json.JSONTokener;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author william
 */
public class JOCache {

    protected static LoadingCache<String, IJOCacheItem> _cache;

    public static LoadingCache<String, IJOCacheItem> cache() {
        if (_cache == null) {
            _cache = CacheBuilder.newBuilder()
                    .maximumSize(1000) // 記憶體中最多保留 1000 筆資料
                    .expireAfterAccess(30, TimeUnit.MINUTES)
                    .build(new CacheLoader<String, IJOCacheItem>() {
                        @Override
                        public IJOCacheItem load(String k) throws Exception {
                            throw new RuntimeException("Using get(key, new  Callable<>{} ... ");
                        }
                    });
        }
        return _cache;
    }

    public static JSONObject load(File f) {
        if (f.exists()) {
            try {
                String fname = f.getAbsolutePath();
                IJOCacheItem<JSONObject> item = cache().get(fname, new JOCacheFileItem(fname));
                return item.load();
            } catch (Exception ex) {
                JOLogger.error("Can't load jo " + f);
                ex.printStackTrace();
            }
        }
        return null;
    }

    public static List<JSONObject> loadCombo(String base, String comboId, Object... params) {
        try {
            File bf = new File(base);
            StringBuilder sb = new StringBuilder();
            sb.append("combo").append("::")
                    .append(bf.toURI()).append("::")
                    .append(comboId);
            String urn = sb.toString();
            IJOCacheItem<List<JSONObject>> item = cache().get(urn, new JOCacheCombo(urn));
            return item.load();
        } catch (Exception ex) {
            return null;
        }
    }

    public static JSONObject loadXML(File f) {
        try {
            JOFile jf = new JOFile();
            return jf.xml(f, "UTF-8");
        } catch (Exception e) {
            JOLogger.error("load xml error : " + f, e);
        }
        return null;
    }

    public static JSONObject load(String base, String id) {
        File f = new File(base, id + ".json");
        try {
            JSONObject ret = load(f);
            ret.put("$parent", f.getParent());
            return ret;
        } catch (Exception ex) {
            ex.printStackTrace();
            JOLogger.error("Can't load json [" + f + "]");
        }
        return null;
    }

    public static JSONObject loadXML(String base, String id) {
        return loadXML(new File(base, id + ".xml"));
    }

    private static class CacheItem {

        long lastModified;
        JSONObject jo;
    }

    public static JSONObject loadJSON(File f) throws IOException {
        return loadJSON(new InputStreamReader(new FileInputStream(f), "UTF-8"));
    }

    public static JSONObject loadJSON(String text) throws IOException {
        return loadJSON(new StringReader(text));
    }

    public static JSONObject loadJSON(Reader reader) throws IOException {
        try {
            JSONTokener tk = new JSONTokener(reader);
            return new JSONObject(tk);
        } finally {
            reader.close();
        }
    }

}
