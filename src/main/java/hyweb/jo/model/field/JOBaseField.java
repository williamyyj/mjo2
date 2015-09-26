package hyweb.jo.model.field;

import hyweb.jo.model.IJOField;
import hyweb.jo.IJOType;
import hyweb.jo.JOProcObject;
import hyweb.jo.fun.MJOBase;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.HashMap;
import java.util.Map;
import org.mvel2.MVEL;

/**
 * @author William
 * @param <E>
 */
public class JOBaseField<E> implements IJOField<E> {

    protected JSONObject cfg;
    protected IJOType<E> type;

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        this.cfg = cfg;
    }

    @Override
    public String name() {
        return cfg.optString("name");
    }

    @Override
    public String id() {
        return cfg.optString("id");
    }

    @Override
    public String dt() {
        return cfg.optString("dt");
    }

    @Override
    public String label() {
        return cfg.optString("label");
    }

    @Override
    public int size() {
        return cfg.optInt("size", 0);
    }

    @Override
    public String label(String lang) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String description() {
        return cfg.optString("description");
    }

    @Override
    public String ct() {
        return cfg.optString("ct", null);
    }

    @Override
    public String ft() {
        return cfg.optString("ft", null);
    }

    public String cast() {
        return cfg.optString("cast", null);
    }

    @Override
    public IJOType<E> type() {
        return this.type;
    }

    /*
     取代cast 
     */
    @Override
    public String eval() {
        return cfg.optString("eval", null);
    }

    public JSONObject cfg() {
        return cfg;
    }

    @Override
    public String toString() {
        return cfg.toString();
    }

    @Override
    public String args() {
        return cfg.optString("args", null);
    }

    @Override
    public boolean valid(JSONObject wp) throws Exception {
        Map<String, Object> m = new HashMap<String, Object>();
        JSONObject src = wp.optJSONObject("$");
        JSONObject ref = wp.optJSONObject("$$");
        JOProcObject proc =  MJOBase.proc(wp);
        m.put("$", src.m());
        m.put("$$", ref.m());
        m.put("$f", JOFunctional.class);
        m.put("$fv", getFieldValue(src));
        m.put("$vf", proc.opt("$vf"));
        m.put("$fld", this);
        m.put("$wp", wp);
        return MVEL.eval(eval(), m, Boolean.class);
    }
    
       public String getFieldName() {
        if (args() != null && args().length() > 0) {
            return args();
        } else if (name() != null && name().length() > 0) {
            return name();
        } else {
            String[] items = id().split("_");
            return (items.length > 1) ? items[1] : items[0];
        }
    }

    @Override   
    public void setErrData(JSONObject row, String message) {
        JSONObject err = row.optJSONObject("$err");
        JSONObject msg = row.optJSONObject("$msg");
        err.put(id(), false);
        if (message != null) {
            msg.put(id(), message);
        } else {
            msg.put(id(),label());
        }
    }

    @Override
    public Object getFieldValue(JSONObject row) {
        return row.opt(getFieldName());
    }

    @Override
    public  void setFieldValue(JSONObject row, Object value) {
        row.put(getFieldName(), value);
    }

}
