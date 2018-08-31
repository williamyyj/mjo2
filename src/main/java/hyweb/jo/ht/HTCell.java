package hyweb.jo.ht;

import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;

/**
 * @author william
 */
public abstract class HTCell implements IHTCell {

    protected JSONObject cfg;

    protected String scriptable;

    protected String code;

    protected StringBuilder html;

    public HTCell() {
        html = new StringBuilder();
    }

    public HTCell(JSONObject cfg) {
        this.cfg = cfg;
        html = new StringBuilder();
    }

    public HTCell(String line) {
        cfg = JOCache.loadJSON(line);
        html = new StringBuilder();
    }

    @Override
    public void __init__(Object cfg) throws Exception {
        if (cfg instanceof JSONObject) {
            this.cfg = (JSONObject) cfg;
        } else if (cfg != null) {
            this.cfg = JOCache.loadJSON(cfg.toString());
        }
    }

    @Override
    public String getId() {
        return cfg.optString("id");
    }

    @Override
    public String getType() {
        return cfg.optString("dt");
    }

    @Override
    public String getLabel() {
        return cfg.optString("label");
    }

    @Override
    public String getScriptable() {
        return scriptable;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getHtml() {
        return (html != null) ? html.toString() : "";
    }

    public IHTCell newInstance(String line) {
        return null;
    }

    @Override
    public String display(Object p) {
        return display(p, "");
    }

}
