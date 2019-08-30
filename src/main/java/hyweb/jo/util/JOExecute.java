package hyweb.jo.util;

import hyweb.jo.IJOBiFunction;
import hyweb.jo.log.JOLogger;

/**
 *
 * @author william
 */
public class JOExecute {

    public static String prefix = "hyweb.jo.bf";

    public static Object bfun(String classId, Object p, Object u) throws Exception {
        IJOBiFunction bf = null;
        try {
            bf = (IJOBiFunction) Class.forName(prefix + "." + classId).newInstance();
        } catch (Exception e) {
            JOLogger.info("Can't find : "+classId, e);
        }
        return (bf != null) ? bf.exec(p, u) : null;
    }
}
