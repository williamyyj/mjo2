package hyweb.jo.model;

import hyweb.jo.JOProcObject;
import hyweb.jo.ff.IJOFF;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOPath;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author william
 */
public class JOActor {

    public static Object exec(JOProcObject proc, String metadataId, String line) throws Exception {
        JSONObject ap = new JSONObject();
        if (line != null) {
            String[] items = line.split(",");
            ap.put("$actId", items[0]);
            ap.put("$dataId", (items.length > 1) ? items[1] : null);
        }
        return exec(proc, metadataId, ap);
    }

    public static Object exec(JOProcObject proc, String metadataId, JSONObject ap) throws Exception {
        JOWPObject wp = new JOWPObject(proc, metadataId, ap.optString("$actId", null));
        JSONObject act = new JSONObject(wp.act());
        act.putAll(ap);
        System.out.println(act);
        wp.put("$act", act);
        String funId = act.optString("$funId");
        Object ret = JOFunctional.exec(funId, wp);
        wp.remove("$act");
        return ret;
    }

    public static List<JSONObject> query(JOProcObject proc, String metadataId, JSONObject pp) throws Exception {
        proc.set(JOProcObject.p_request, "$fp", new JSONObject(proc.params()));
        ff(proc, metadataId, "encrypt,crypt,$");
        String actId = proc.params().optString("act", "query");
        JOWPObject wp = new JOWPObject(proc, metadataId, actId, proc.params(), pp);
        String dataId = wp.act().optString("$dataId", "$rows");
        String funId = wp.act().optString("$funId", "wp_page");
        List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec(funId, wp);
        proc.put(dataId, rows);
        proc.set(JOProcObject.p_request, dataId, rows);
        ff(proc, metadataId, "decrypt,crypt," + dataId);
        return rows;
    }

    public static List<JSONObject> edit(JOProcObject proc, String metadataId, JSONObject pp) throws Exception {
        JOWPObject wp = new JOWPObject(proc, "psSprayPerson", "save");
        proc.set(JOProcObject.p_request, "$fp", JOFunctional.exec("dao.wp_row_edit", wp));

        //JOFF.create(web, "chk_spray_level");
        //JOFF.create(web, "chk_city");
        //JOFF.create(web, "radio_sex");
        return null;
    }

    public static Object ff(JOProcObject proc, String metadataId, String line) {
        JOMetadata md = proc.wMeta().metadata(metadataId); // init ff 
        String[] part = line.split(",");
        String ffId = part.length > 0 ? part[0] : "";
        String jp = part.length > 1 ? part[1] : "";
        String dataId = part.length > 2 ? part[2] : "$";
        IJOFF ff = (IJOFF) JOPath.path(proc, "$ff:" + ffId);
        JOLogger.debug(ffId + ":::" + ff);
        if (ff != null) {
            Object data = JOPath.path(proc, dataId);
            if (data == null && dataId.equals("$")) { // 
                data = proc.params();
            }
            List<IJOField> fields = md.getFields(JOPath.asString(md.cfg(), jp));
            if (data instanceof JSONObject) {
                ff_fields(ff, fields, (JSONObject) data);
            } else if (data instanceof Collection) {
                ff_fields(ff, fields, (Collection) data);
            }
        }
        return null;
    }

    private static void ff_fields(IJOFF ff, List<IJOField> fields, Collection data) {
        for (Object row : data) {
            ff_fields(ff, fields, (JSONObject) row);
        }
    }

    private static void ff_fields(IJOFF ff, List<IJOField> fields, JSONObject row) {
        for (IJOField fld : fields) {
            ff_item(ff, fld, row);
        }
    }

    private static void ff_item(IJOFF ff, IJOField fld, JSONObject row) {
        //  wp_eval 要處理預設值
        Object fv = fld.getFieldValue(row);
        if (fv != null) {
            Object v = ff.cast(fv);
            fld.setFieldValue(row, v);
        }
    }
}
