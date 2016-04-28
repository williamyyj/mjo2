package hyweb.jo.model;

import java.lang.reflect.Method;
import net.sf.cglib.proxy.Callback;
import net.sf.cglib.proxy.CallbackFilter;
import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.NoOp;

public class Main {

    public static void main(String[] args) {

        Callback[] callbacks
                = new Callback[]{new JOModelInterceptor(), NoOp.INSTANCE};

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(MyClass.class);
        enhancer.setCallbacks(callbacks);
        enhancer.setCallbackFilter(new CallbackFilterImpl());

        MyClass my = (MyClass) enhancer.create();

        my.method();
        my.method2();
    }

    private static class CallbackFilterImpl implements CallbackFilter {

        @Override
        public int accept(Method method) {

            if (method.getName().equals("method2")) {
                return 0;

            } else {
                return 0;
            }
        }
    }

   
}

 class MyClass {
 
	public void method() {
		System.out.println("MyClass.method()");
	}
 
	public void method2() {
		System.out.println("MyClass.method2()");
	}
}
