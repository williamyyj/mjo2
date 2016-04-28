package hyweb.jo.model;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 *
 * @author william
 */
public class JOLoggerInterceptor implements MethodInterceptor {

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        
       Object ret = proxy.invokeSuper(obj, args);
        
       return ret ;
    }
    
}
