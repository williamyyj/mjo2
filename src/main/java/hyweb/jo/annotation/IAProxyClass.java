package hyweb.jo.annotation;

import static java.lang.annotation.ElementType.TYPE;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

/**
 *
 * @author william
 */
@Retention(RUNTIME)
@Target(TYPE)
public @interface  IAProxyClass {
    public String id() ;
}
