package hyweb.jo.model.field;

import hyweb.jo.model.IJOField;
import hyweb.jo.IJOType;
import hyweb.jo.JOProcObject;
import hyweb.jo.fun.MJOBase;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.util.Date;
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

    /**
     * 資料庫欄位名
     *
     * @return
     */
    @Override
    public String name() {
        return cfg.optString("name", cfg.optString("id"));
    }

    /**
     * 系統識別值 （唯一)
     *
     * @return
     */
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

    /**
     * DB : P -> pk , F-> FK , I -> index , N -> not null
     *
     * @return
     */
    @Override
    public String ct() {
        return cfg.optString("ct", null);
    }

    /**
     * 格式資訊
     *
     * @return
     */
    @Override
    public String ft() {
        return cfg.optString("ft", null);
    }

    /**
     *
     * @return
     */
    @Override
    public String gdt() {
        return cfg.optString("gdt", null);
    }

    @Deprecated
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

    @Override
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

    /**
     * 目前給 農藥銷售紀錄 使用
     *
     * @param wp
     * @return
     * @throws Exception
     * @deprecated
     */
    @Deprecated
    @Override
    public boolean valid(JSONObject wp) throws Exception {
        Map<String, Object> m = new HashMap<String, Object>();
        JSONObject src = wp.optJSONObject("$");
        JSONObject ref = wp.optJSONObject("$$");
        JOProcObject proc = MJOBase.proc(wp);
        m.put("$", src);
        m.put("$$", ref);
        m.put("$f", JOFunctional.class);
        m.put("$fv", getFieldValue(src));
        m.put("$vf", proc.opt("$vf"));
        m.put("$fld", this);
        m.put("$wp", wp);
        m.put("$now", new Date());
        Object ret = MVEL.eval(eval(), m);
        if (ret instanceof Boolean) {
            return (Boolean) ret;
        } else {
            return true;
        }
    }

    /**
     * 1. args 外部為主 2. name 以名稱為主 3. e_xxxx , v_xxxx , xxxxx
     *
     * @return
     */
    @Override
    public String getFieldName() {
        String name = name();
        String id = id();
        if (args() != null && args().length() > 0) {
            //  inject  
            return args();
        } else if (!id.equals(name)) {
            //  Fix   name : cfg.optString("name", cfg.optString("id")); 
            return name;
        } else {
            String[] items = id.split("\\_");
            return (items.length > 1) ? items[1] : items[0];
        }
    }

    @Override
    public void setErrData(JSONObject row, String message) {
        JSONObject err = row.optJSONObject(IJOField.node_error);
        JSONObject msg = row.optJSONObject("$msg");
        err.put(id(), false);
        if (!msg.has(id())) {
            if (message != null) {
                msg.put(id(), message);
            } else if (cfg().has("msg")) {
                msg.put(id(), cfg().opt("msg"));
            } else {
                msg.put(id(), label());
            }
        }
    }

    @Override
    public Object getFieldValue(JSONObject row) {
        String name = name();
        String id = id();
        Object o = row.opt(getFieldName());
        if (o == null && !id.equals(name)) {
            o = row.opt(name);
        }
        if (o == null) {
            String[] items = id.split("\\_");
            String n = (items.length > 1) ? items[1] : items[0];
            o = row.opt(n);
        }
        return (o != null) ? o : row.opt(id());
    }

    /**
     *
     * @param row 列資料
     * @param value
     */
    @Override
    public void setFieldValue(JSONObject row, Object value) {
        row.put(getFieldName(), value);
    }

    /**
     *
     * @param row
     * @return
     */
    @Override
    public String getFieldText(JSONObject row) {
        return row.optString(getFieldName());
    }

    @Override
    public E convert(Object o) {
        return type.check(o);
    }

}
