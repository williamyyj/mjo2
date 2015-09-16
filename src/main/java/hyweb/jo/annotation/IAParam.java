package hyweb.jo.annotation;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import java.lang.annotation.Retention;
import static java.lang.annotation.RetentionPolicy.RUNTIME;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target({METHOD, PARAMETER})
public @interface IAParam {

    String name() default "##default";
    
    String alias() default "##default";

    Class type() default Object.class;

}
