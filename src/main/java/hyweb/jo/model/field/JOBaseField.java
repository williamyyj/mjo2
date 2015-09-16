package hyweb.jo.model.field;

import hyweb.jo.model.IJOField;
import hyweb.jo.IJOType;
import hyweb.jo.org.json.JSONObject;

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

    public String label(String lang) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public String description() {
        return cfg.optString("description");
    }

    public String ct() {
        return cfg.optString("ct", null);
    }

    public String ft() {
        return cfg.optString("ft", null);
    }

    public String cast() {
        return cfg.optString("cast", null);
    }

    public IJOType<E> type() {
        return this.type;
    }
    
    /*
        取代cast 
    */
    public String eval() {
        return cfg.optString("eval",null);
    }

    public JSONObject cfg() {
        return cfg ; 
    }
    
    @Override
    public String toString(){
        return cfg.toString() ; 
    }

    public String args() {
       return cfg.optString("args",null);
    }
 
}
