/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import com.google.common.reflect.ClassPath;
import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.log.JOLogger;
import java.io.IOException;
import java.util.Map;

/**
 *
 * @author william
 */
public class JOPackages {

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

    public static Object newInstance(String classId) {
        try {
            return Class.forName(classId).newInstance();
        } catch (Exception e) {
            JOLogger.error("Can't newInstance : " + classId);
            return null;
        }
    }

}
