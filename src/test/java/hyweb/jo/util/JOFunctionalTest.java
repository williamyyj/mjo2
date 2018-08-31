package hyweb.jo.util;

import org.junit.Test;

/**
 *
 * @author william
 */
public class JOFunctionalTest {

    @Test
    public void test_num(){
        String text ="５";
        Number num = JOFunctional.num(text);
        System.out.println(text.equals("5"));
        System.out.println(5+":::"+num);
        System.out.println(Integer.parseInt(text));
    }
 
}
