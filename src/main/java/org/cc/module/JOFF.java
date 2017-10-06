package org.cc.module;

import org.cc.ff.ICCFF;
import hyweb.jo.IJOInit;
import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOPath;

/**
 *
 * @author william
 * @param <E>
 */
public abstract class JOFF<E> implements ICCFF<E>, IJOInit<JSONObject> {

    protected String ffId;

    protected JSONObject cfg;

    protected String context;

    protected String alias;

    public JOFF() {

    }

    public JOFF(JSONObject cfg) {
        try {
            __init__(cfg);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

        @Override
    public void init(JOProcObject proc) throws Exception {

    }
    
    @Override
    public void __init__(JSONObject cfg) throws Exception {
        this.cfg = cfg;
        this.ffId = cfg.optString("$id");
        String[] items = ffId.split("_");
        this.alias = (items.length > 1) ? items[1] : ffId;
        System.out.println("===== alias : " + alias);
    }

    @Override
    public JSONObject cfg() {
        return this.cfg;
    }

    protected E getRowValue(JSONObject row, E dv) throws Exception {
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

    protected void set(String prefix , String suffix , JSONObject row, Object v){
        String id = prefix + alias + suffix ;
          row.put(id, v);
    }
    
    protected void setText(JSONObject row, Object v) throws Exception {
        set("$","Text",row,v);
    }

  

}
