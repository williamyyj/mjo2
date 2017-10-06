package hyweb.jo.model;

import com.google.common.reflect.ClassPath;
import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOPackages;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JOFieldUtils {

    private static Map<String, Class> _cache;

    private static Object newInstance(String classId) {
        try {
            return Class.forName(classId).newInstance();
        } catch (Exception e) {
            JOLogger.error("Can't newInstance : " + classId);
            return null;
        }
    }

    private static Map<String, Class> cache() {
        if (_cache == null) {
            _cache = new HashMap<String, Class>(32);
            JOPackages.scanPackage(_cache, "hyweb.jo.model.field");
        }
        return _cache;
    }

    private static void scanPackage(Map<String, Class> c, String string) {
        try {
            ClassPath classpath = ClassPath.from(Thread.currentThread().getContextClassLoader());
            for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses("hyweb.jo.model.field")) {
                Class cls = classInfo.load();
                IAProxyClass a = (IAProxyClass) cls.getAnnotation(IAProxyClass.class);
                if (a != null) {
                    c.put(a.id(), cls);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static IJOField newField(String classId) throws Exception {
        Class cls = cache().get("field." + classId);
        cls = (cls == null) ? cache().get("field.obj") : cls;
        return (IJOField) cls.newInstance();
    }

    public static IJOField newInstance(JSONObject jo) throws Exception {
        IJOField fld = newField(jo.optString("dt"));
        if (fld != null) {
            fld.__init__(new JSONObject(jo.m()));
        }
        return fld;
    }
    
 

}
