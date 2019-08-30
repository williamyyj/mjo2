package hyweb.jo.model;

import hyweb.jo.JOConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOPath;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author william
 *
 * 取代 JOWPObject 及減少客制化
 */
public class JOActorObject extends JSONObject {

    public final static String ACT_FIELDS = "$fields";
    public final static String ACT_FUNID = "$funId";
    private JOProcObject proc;
    private List<IJOField> fields;

    public JOActorObject(JOProcObject proc, String metaId, String actId) {
        // 標準式
        this.proc = proc;
        put("$metaId", metaId);
        put("$actId", actId);
        __init_act();
        __init_act_fields();
    }

    public JOActorObject(JOProcObject proc, JSONArray ja) { //
        this.proc = proc;
        put("$metaId", ja.optString(0));
        put("$actId", ja.optString(1));
        __init_act(); // 
        __init_act_params(ja.opt(2));
        __init_act_fields();
    }

    public JOActorObject(JOProcObject proc, JSONObject jo) { //
        this.proc = proc;
        putAll(jo);
    }

    private void __init_act() {
        JOMetadata md = proc.wMeta().metadata(metaId());
        if (actId().length() > 0) {
            Object o = md.cfg().get(actId());
            if (o instanceof JSONObject) {
                this.putAll((JSONObject) o);

            } else if (o instanceof String) {
                __init_actAsString((String) o);
            } else if (o instanceof JSONArray) {
                put(ACT_FIELDS, o);
            }
        } else {
            __init_actAsString(md.cfg().optString(JOConst.meta_fields));
        }
    }

    private void __init_act_fields() {
        //   act.$field  -->  params.$fields 
        Object o = opt(ACT_FIELDS);
        if (o instanceof String) {
            JSONArray ja = new JSONArray(((String) o).split(","));
            put(ACT_FIELDS, ja);
        } else if (o instanceof Collection) {
            put(ACT_FIELDS, o);
        } else if (o instanceof JSONArray) {
            put(ACT_FIELDS, o);
        }
    }

    private void __init_actAsString(String line) {
        String[] items = line.split(",");
        put(ACT_FIELDS, items);
    }

    private void __init_act_params(Object o) {
        if (o instanceof String) {
            put(ACT_FUNID, o);
        } else if (o instanceof JSONObject) {
            putAll((JSONObject) o);
        }
    }

    public String metaId() {
        return optString("$metaId");
    }

    public String actId() {
        return optString("$actId");
    }

    public String funId() {
        return optString(ACT_FUNID);
    }

    public JOMetadata metadata() {
        return proc.wMeta().metadata(metaId());
    }

    public List<IJOField> fields() {
        if (fields == null) {
            fields = new ArrayList<IJOField>();
            Object o = opt(ACT_FIELDS);
            JSONArray flds = this.optJSONArray(ACT_FIELDS);
            JOMetadata md = proc.wMeta().metadata(metaId());
            if (flds != null) {
                for (int i = 0; i < flds.size(); i++) {
                    String item = flds.optString(i);
                    int ps = item.indexOf(':');
                    String id = (ps > 0) ? item.substring(0, ps) : item;
                    String args = (ps > 0) ? item.substring(ps + 1) : null;
                    IJOField fld = md.getField(id, args);
                    if ("dyn".equals(fld.dt())) {
                        proc_field_dyn(fld);
                    } else if (fld != null) {
                        fields.add(fld);
                    }
                }
            } else {
                fields.addAll(md.getFields());
            }
        }
        return fields;
    }

    public JOProcObject proc() {
        return this.proc;
    }

    public Object getInParams() {
        // 未指定給預設值S
        String jp = optString("$", null);
        if (jp != null) {
            return JOPath.path(proc, jp);
        }
        return proc.params();
    }

    public Object getData() {
        // 未指定給預設值S
        String jp = optString("$dataId", null);
        if (jp != null) {
            return JOPath.path(proc, jp);
        }
        return proc.opt("$data");
    }

    private void proc_field_dyn(IJOField fld) {
        JSONArray jaHead = proc.params().optJSONArray(fld.cfg().optString("ehead"));
        JSONArray jaName = proc.params().optJSONArray(fld.cfg().optString("ename"));
        System.out.println(fld.cfg().optString("ehead") + ":::" + jaHead);
        if (jaHead != null && jaName != null && jaHead.length() == jaName.length()) {
            for (int i = 0; i < jaHead.length(); i++) {
                JSONObject jo = new JSONObject();
                try {
                    jo.put("label", jaHead.optString(i));
                    jo.put("name", jaName.optString(i));
                    jo.put("id", jaName.optString(i));
                    jo.put("dt", fld.cfg().optString("edt"));
                    fields.add(JOFieldUtils.newInstance(jo));
                } catch (Exception ex) {
                    JOLogger.info("Can't create field :" + jo);
                }
            }
        }
    }

}
