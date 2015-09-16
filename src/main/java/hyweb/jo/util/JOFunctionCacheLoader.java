package hyweb.jo.util;

import com.google.common.cache.CacheLoader;
import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;

public class JOFunctionCacheLoader extends CacheLoader<String, IJOFunction> {
            
    @Override
    public IJOFunction load(String classId) throws Exception {
        try {
            Class<?> cls = Class.forName(classId);
            JOLogger.debug("Load function  " + classId);
            return (IJOFunction) cls.newInstance();
        } catch (Exception e) {
            JOLogger.error("Can't find classId : " + classId);
            return null;
        }
    }

}
