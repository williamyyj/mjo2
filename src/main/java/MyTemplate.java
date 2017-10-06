
import java.util.HashMap;
import java.util.Map;
import org.mvel2.MVEL;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author william
 */
public class MyTemplate {
    public static void main(String[] args){
        Map<String,Object> m = new HashMap<String,Object>();
        m.put("abc", "This is my book.");
        Object o = MVEL.eval("abc.replace('book', 'apple')",m);
        System.out.println( o );
    }
}
