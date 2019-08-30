/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import java.util.regex.Pattern;
import org.junit.Test;

/**
 *
 * @author william
 */
public class RegexTest {

    @Test
    public void test_orderby() {
        Pattern p = Pattern.compile("(\\s+order\\s+by)");
        System.out.println(match(p, " select * from xxx order by abc"));
        System.out.println(match(p, "  select * from xxx orderby abc"));
        System.out.println(match(p, "   order    by   xx"));
        System.out.println(match(p, "   order      xx"));
    }

    private boolean match(Pattern p, String line) {
        return p.matcher(line).find();
    }
}
