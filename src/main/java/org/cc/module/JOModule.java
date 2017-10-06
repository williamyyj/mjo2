package org.cc.module;

import hyweb.jo.JOProcObject;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;
import org.cc.ff.CCFF;

/**
 *   metadata 大致都會有
 *   model 不一定有
 * @author william
 */
public class JOModule {

    public static String m_md = "$md";
    public static String m_mm = "$mm";
    public static String m_ff = "$ff";
    private String base;
    private String mId;

    private boolean isInline = true;

    private JSONObject m;

    private JOProcObject proc;
    private String root;
    private String prjId;

    public JOModule(String root, String prjId, String mId) {
        this.root = root;
        this.prjId = prjId;
        __init(root +"/"+ prjId, mId);
    }

    public JOModule(String base, String mId) {
        __init(base, mId);
    }

    public JOModule(JOProcObject proc) {
        this.proc = proc;
        isInline = false;
        __init(base, proc.optString("mId"));
    }

    private void __init(String base, String mId) {
        this.base = base;
        this.mId = mId;
        proc();
        metadata();
        __init_dd();
        __init_ff();
    }

    private void __init_ff() {
        CCFF.init_ffs(proc, this.metadata());
    }

    private void __init_dd() {
        JOMetadata md = metadata();
        JSONObject dd = JOCache.load(base + "/module/dd", module().optString("$dd"));
        JSONObject ddRoot = new JSONObject();
        if (dd != null) {
            JSONArray arr = dd.optJSONArray("meta");
            if (arr != null) {
                for (Object o : arr) {
                    JSONObject item = (JSONObject) o;
                    ddRoot.put(item.optString("id"), item);
                }
            }
        }
        proc.put("$dd", ddRoot);
    }

    public JOProcObject proc() {
        if (proc == null) {
            isInline = true;
            proc = new JOProcObject(base);
        }
        return proc;
    }

    public void release() {
        if (proc != null && isInline) {
            proc.release();
        }
    }

    public JSONObject module() {
        if (m == null) {
            m = JOCache.load(base + "/module", mId);
        }
        return m;
    }

    public JOMetadata metadata(String metaId) {
        String mdId = m_md + "_" + metaId;
        JOMetadata md = (JOMetadata) proc.opt(mdId);
        if (md == null) {
            md = new JOMetadata(proc.base(), "/module/meta", metaId);
            proc.put(mdId, md);
        }
        return md;
    }

    public JOMetadata metadata() {
        return metadata(mId);
    }

    public JSONObject event(String eid) {
        // 相容舊版
        if (module().has(eid)) {
            return m.optJSONObject(eid);
        } else if (metadata().cfg().has(eid)) {
            return metadata().cfg().optJSONObject(eid);
        }
        throw new RuntimeException("Can't find event  : " + eid);
    }

    public  JOModel model(String mmId) {
        return null ;
    }

}
