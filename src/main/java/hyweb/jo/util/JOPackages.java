package hyweb.jo.util;

import com.google.common.reflect.ClassPath;
import hyweb.jo.IJOInit;
import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.log.JOLogger;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author william
 */
public class JOPackages {

    private static Map<String, Class> _cache;

    private static Map<String, Class> cache() {
        if (_cache == null) {
            _cache = new HashMap<String, Class>(32);
            scanPackage(_cache, "hyweb.jo.model.ht");
        }
        return _cache;
    }

    public static void scanPackage(Map<String, Class> c, String pkg) {
        try {
            ClassPath classpath = ClassPath.from(Thread.currentThread().getContextClassLoader());
            for (ClassPath.ClassInfo classInfo : classpath.getTopLevelClasses(pkg)) {
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



    public static Object newInstance(String id , Object cfg) {
        System.out.println(id);
        Class cls = cache().get(id);
        try{
            cls = (cls!=null) ? cls :  Class.forName(id);
            IJOInit o = (IJOInit) cls.newInstance();
           o.__init__(cfg);
           return o;
        } catch(Exception e){
            e.printStackTrace();
            JOLogger.info("Can't new "+id);
            return null;
        }
    }

    public static File[] getPackageContent(String packageName) throws IOException {
        ArrayList<File> list = new ArrayList<File>();
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(packageName);
        while (urls.hasMoreElements()) {
            URL url = urls.nextElement();
            File dir = new File(url.getFile());
            for (File f : dir.listFiles()) {
                list.add(f);
            }
        }
        return list.toArray(new File[]{});
    }

    public static void main(String[] args) throws IOException {
        File[] list = getPackageContent("hyweb.jo");
        for (File f : list) {
            System.out.println(f);
        }
    }

}
