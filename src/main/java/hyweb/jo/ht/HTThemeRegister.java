package hyweb.jo.ht;

import hyweb.jo.data.JOFileUtils;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.mvel2.templates.CompiledTemplate;
import org.mvel2.templates.TemplateCompiler;
import org.mvel2.templates.TemplateError;
import org.mvel2.templates.TemplateRegistry;

/**
 *
 * @author william
 */
public class HTThemeRegister implements TemplateRegistry {

    private Map<String, CompiledTemplate> cache = new HashMap<String, CompiledTemplate>();

    @Override
    public Iterator iterator() {
        return cache.keySet().iterator();
    }

    @Override
    public Set<String> getNames() {
        return cache.keySet();
    }

    @Override
    public boolean contains(String name) {
        return cache.containsKey(name);
    }

    @Override
    public void addNamedTemplate(String name, CompiledTemplate template) {
        cache.put(name, template);
    }

    @Override
    public CompiledTemplate getNamedTemplate(String name) {
        CompiledTemplate t = cache.get(name);
        if (t == null) {
            throw new TemplateError("no named template exists '" + name + "'");
        }
        return t;
    }

    public void loadTemplate(String base, String theme, String name, String fid) throws IOException {
        String template = JOFileUtils.loadString(new File(base + "/mvel/theme/" + theme, fid + ".htm"), "UTF-8");
        CompiledTemplate ct = TemplateCompiler.compileTemplate(template);
        cache.put(name, ct);
    }


}
