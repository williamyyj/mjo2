package hyweb.jo.mvel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;
import org.mvel2.Macro;
import org.mvel2.MacroProcessor;
import org.mvel2.templates.TemplateRuntime;

/**
 *
 * @author william
 */
public class MvelTest {

    
    @Test
    public void test_macro() {
        Map<String, Macro> myMacros = new HashMap<String, Macro>();
        Macro modifyMacro = new Macro() {
            @Override
            public String doMacro() {
                return "@Modify with eeeeeeeeeeeeeeeeeeeeeeeeeee";
            }

        };

        myMacros.put("modify", modifyMacro);
        
        MacroProcessor macroProcessor = new MacroProcessor();

        macroProcessor.setMacros(myMacros);

// Now we pre-parse our expression
        String parsedExpression = macroProcessor.parse("modify(obj) { value = 'foo' }");
        
        System.out.println(parsedExpression);

    }

    public void test_code(){
        SimpleDateFormat sdf  = new SimpleDateFormat("");
        sdf.format(new java.util.Date());
        String code = "@code{ fmt = new java.text.SimpleDateFormat('yyyy-MM-dd') }\r\n@{fmt.format(new java.util.Date())}@{1+1}";
        Map<String,Object> m = new  HashMap<String,Object>();
        System.out.println(m);
        Object ret = TemplateRuntime.eval(code,m);
           System.out.println(ret);
        
    }
    
    
    

}
