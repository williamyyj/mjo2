/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import static hyweb.jo.JOConst.param_fields;
import static hyweb.jo.JOConst.param_sql;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DB;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.DateUtil;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import hyweb.jo.util.TextUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.mvel2.MVEL;

/**
 *
 * @author william
 */
public class MJOMSPage implements IJOFunction<List<JSONObject>, JSONObject> {

    @Override
    public List<JSONObject> exec(JSONObject wp) throws Exception {
        JOFunctional.exec("combo", wp);
        Map<String, Object> m = init_mvel(wp);
        do_before(wp, m);
        JOLogger.debug(wp.opt("$"));
        List<JSONObject> rows = do_process(wp);
        do_after(wp, m, rows);
        return rows;
    }

    protected void do_before(JSONObject wp, Map<String, Object> m) {
        JOMetadata md = MJOBase.metadata(wp);
        JSONObject row = wp.optJSONObject("$");
        String before = MJOBase.act(wp).optString("before");
        List<IJOField> bFields = md.getFields(before);

        if (bFields != null) {
            for (IJOField fld : bFields) {
                Object o = fld.getFieldValue(row);
                Object v = fld.convert(o);
                if (v != null) {
                    if (fld.eval() != null) {
                        m.put("$fv", v);
                        m.put("$fld", fld);
                        v = MVEL.eval(fld.eval(), m);
                    }
                    fld.setFieldValue(row, v);
                }
            }
        }
    }

    protected int rowCount(DB db, JSONObject mq) {
        try {
            String sql = mq.optString(param_sql);
            sql = "select count(*) from ( " + sql + " ) t ";
            Object[] values = JOTools.to_marray(mq, param_fields, "value");
            return (Integer) db.fun(sql, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    protected int pages(DB db, JSONObject mq, int num) {
        int count = rowCount(db, mq);
        int ret = count / num;
        return (ret * num == count) ? ret : ret + 1;
    }

    protected List<JSONObject> rows(DB db, JSONObject mq, int num, int pageId, String orderby) throws Exception {
        String sql = mq.optString(param_sql);
        Object[] params = JOTools.to_marray(mq, param_fields, "value");
        StringBuilder sb = new StringBuilder();
        sb.append(" select t.* from ");
        sb.append(" ( select ROW_NUMBER() OVER (ORDER BY ").append(orderby).append(" )  rowid , ");
        sb.append("  c.* from ( ");
        sb.append(sql);
        sb.append(" ) c ) t ");
        sb.append(" where rowid > ").append(+(pageId - 1) * num);
        sb.append(" and rowid <= ").append((pageId) * num);
        return db.rows(sb.toString(), params);
    }

    protected Map<String, Object> init_mvel(JSONObject wp) {
        Map<String, Object> m = new HashMap<String, Object>();
        m.put("$", wp.optJSONObject("$").m());
        m.put("$$", wp.optJSONObject("$$").m());
        m.put("$f", JOFunctional.class);
        m.put("$du", DateUtil.class);
        m.put("$tu", TextUtils.class);
        m.put("$proc", MJOBase.proc(wp));
        return m;
    }

    protected List<JSONObject> do_process(JSONObject wp) throws Exception {
        return null;
    }

    protected void do_after(JSONObject wp, Map<String, Object> m, List<JSONObject> rows) {
        JOMetadata md = MJOBase.metadata(wp);
        JOProcObject proc = MJOBase.proc(wp);
        String after = MJOBase.act(wp).optString("after");
        List<IJOField> aFields = md.getFields(after);
        if (aFields != null) {
            for (IJOField fld : aFields) {
                if ("combo".equals(fld.dt()) && fld.ct() != null) {
                    fld.cfg().put("$m", proc.opt(fld.ct()));
                }
            }
        }
        for (JSONObject row : rows) {
            do_after_row(m, aFields, row);
        }
    }

    private void do_after_row(Map<String, Object> m, List<IJOField> aFields, JSONObject row) {
        if (aFields != null) {
            for (IJOField fld : aFields) {
                if ("combo".equals(fld.dt()) && fld.ct() != null) {
                    String text = fld.getFieldText(row);
                   row.put(fld.name()+"_text", text);
                }
            }
        }
    }

}
