package org.cc.module;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import hyweb.jo.JOProcObject;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author william
 */
public class CCFunc {

    public static String prefix = "$ff";

    public static String pkg = "org.cc.fun";

    private static LoadingCache<String, ICCPF> map_pf;

    private static LoadingCache<String, ICCPF> pf() {
        if (map_pf == null) {
            map_pf = CacheBuilder
              .newBuilder()
              .maximumSize(10000)
              .expireAfterWrite(120, TimeUnit.MINUTES)
              .build(new CacheLoader<String, ICCPF>() {
                  @Override
                  public ICCPF load(String key) throws Exception {
                      String classId = pkg + ".proc." + key;
                      Class<?> cls = Class.forName(classId);
                      JOLogger.debug("Load function  " + classId);
                      return (ICCPF) cls.newInstance();
                  }
              });
        }
        return map_pf;
    }

  
    public static Object dbf(String id, String proc, String cmd , Object[] params) throws Exception {
        return null ;
    }

    public static Object proc(String id, JOProcObject proc, JSONObject evt , JSONObject cfg) throws Exception {
        ICCPF pf = pf().get(id);
        if (pf != null) {
            return pf.apply(proc, evt,  cfg);
        }
        return null;
    }
    
    public static Object event(JOModule m, String eventId )  throws Exception {
        return null;
    }
}
