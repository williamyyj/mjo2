package hyweb.jo.ht;

import static hyweb.jo.JOTest.base;
import hyweb.jo.data.JOFileUtils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.mvel2.templates.TemplateRuntime;

/**
 *
 * @author william
 */
public class HTMvelTest {

    @Test
    public void mvel_test() throws IOException {
        System.out.println("----------xxxxxxxxxxxxxxxx-----------------");
        HTThemeRegister register = new HTThemeRegister();
        register.loadTemplate(base, "front","person", "fld_test");
        Map<String,Object> m = new HashMap();
        m.put("name", "will");
        m.put("age", 20);
        Object ret = TemplateRuntime.eval("@includeNamed{'person'}:::@includeNamed{'person'}", m, register);
        System.out.println("---->"+ret);
    }
}
