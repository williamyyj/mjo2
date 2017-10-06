package org.cc.module;

import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;

/**
 * @author william
 */
public class JOModel implements ICCModel {

    private JSONObject cfg;

    private String root;

    private String prjId;

    private String base;

    public JOModel(String root, String prjId, String id) {
        this.root = root;
        this.prjId = prjId;
        init(root + "/" + prjId, id);
    }

    private void init(String base, String id) {
        this.base = base;
        cfg = JOCache.load(base + "/module/model", id);
        cfg = (cfg!=null) ? cfg : new JSONObject();
    }

    @Override
    public JSONObject cfg() {
        return cfg;
    }

}
