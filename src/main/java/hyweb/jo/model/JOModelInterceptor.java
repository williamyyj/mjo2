package hyweb.jo.model;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

/**
 *
 * @author william
 */
public class JOModelInterceptor implements MethodInterceptor {
    
    
    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
       System.out.println("===== before method : "+ method);
       Object ret = proxy.invokeSuper(obj, args);
       System.out.println("===== before method : "+ method);
       return ret ;
    }
    
}
