package hyweb.jo.ff;

import hyweb.jo.IJOInit;
import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOPath;

/**
 *
 * @author william
 * @param <E>
 */
public abstract class JOFFBase<E> implements IJOFF<E>, IJOInit<JSONObject> {

    protected JSONObject cfg;
    protected String ffId;
    protected String alias;
    protected String pattern;

    public JOFFBase() {

    }

    public JOFFBase(JSONObject cfg) {
        try {
            __init__(cfg);
        } catch (Exception e) {

        }
    }

    public abstract void __init_proc(JOProcObject proc);
    
    @Override
    public E cast(Object fv, E dv){
        E ret = cast(fv);
        return (ret != null) ? ret : dv ;  
    }

    @Override
    public void init(JOProcObject proc) throws Exception {
        __init_proc(proc);
    }

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        this.cfg = cfg;
        this.ffId = cfg.optString("$id");
        String[] items = ffId.split("_");
        this.alias = cfg.has("$alias") ? cfg.optString("$alias") : (items.length > 1) ? items[1] : ffId;
        this.pattern = cfg.optString("pattern");
    }

    protected Object getRowValue(JSONObject row, Object dv) {
        String name = (String) JOPath.path(cfg, "$fld:name");
        Object ret = null;
        if (row.has(ffId)) {
            ret = row.opt(ffId);
        } else if (row.has(name)) {
            ret = row.opt(name);
        } else if (row.has(alias)) {
            ret = row.opt(alias);
        } else {
            ret = dv;
        }
        return (E) ret;
    }

    protected void set(String prefix, String suffix, JSONObject row, Object v) {
        String id = prefix + alias + suffix;
        row.put(id, v);
    }

    protected void setText(JSONObject row, Object v) throws Exception {
        set("$", "Text", row, v);
    }

    @Override
    public E apply(JSONObject row) {
        Object o = this.getRowValue(row, null);
        return apply(row, ffId, o);
    }

    @Override
    public E as(JSONObject row, String id) {
        Object o = row.opt(id);
        return apply(row, id, o);
    }

    @Override
    public JSONObject cfg() {
        return this.cfg;
    }

    protected abstract E apply(JSONObject row, String id, Object o);

    @Override
    public String getContent() {
        return "";
    }

}
