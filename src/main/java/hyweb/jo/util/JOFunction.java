package hyweb.jo.util;

import hyweb.jo.IJOFunction;
import hyweb.jo.annotation.IAParams;
import hyweb.jo.log.JOLogger;
import java.lang.reflect.Method;

/**
 *
 * @author william
 * @param <RET>
 */
public class JOFunction<RET> implements IJOFunction<RET, Object[]> {

    private Method m;
    private IAParams params;

    public JOFunction() {
        params = this.getClass().getAnnotation(IAParams.class);
        if (params != null) {
            try {
                m = this.getClass().getMethod(params.method(), params.paramTypes());
            } catch (Exception ex) {
                JOLogger.debug("Can't find method : " + params.method());
            }
        }
    }

    @Override
    public RET exec(Object[] p) throws Exception {
        return (RET) m.invoke(this, p);
    }



}
