/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import hyweb.jo.data.JOFile;
import hyweb.jo.org.json.JSONObject;
import java.io.File;
import java.util.Map;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateRuntime;

/**
 *
 * @author william
 */
public class MVELTemplate {

    private final String prefix = "mvel";
    private String mvel_context;
    private CompiledTemplate template;
    private JOFile jf = new JOFile();

    public MVELTemplate(File f) {
        try {
            mvel_context = jf.loadString(f, "UTF-8");
            template = TemplateCompiler.compileTemplate(mvel_context);
        } catch (Exception ex) {

        }

    }

    public MVELTemplate(String base, String id) {
        this(new File(base + "/mvel", id + ".html"));
    }

    public String processTemplate(JSONObject jo) throws Exception {
        return (String) TemplateRuntime.execute(template, jo.m());
    }

    public String processTemplate(Map<String, Object> m) throws Exception {
        return (String) TemplateRuntime.execute(template, m);
    }

}
