package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import static hyweb.jo.JOConst.param_fields;
import static hyweb.jo.JOConst.param_sql;
import hyweb.jo.JOProcConst;
import hyweb.jo.JOProcObject;
import hyweb.jo.db.DB;
import hyweb.jo.fun.util.FJORMWhitespace;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import hyweb.jo.util.TextUtils;
import java.util.List;

/**
 * @author william page 第幾分頁 numPage 每頁幾筆
 */
public class wp_page implements IJOFunction<List<JSONObject>, JOWPObject> {

    @Override
    public List<JSONObject> exec(JOWPObject wp) throws Exception {
        JOProcObject proc = wp.proc();
        //JOLogger.debug("===== before "+wp.p());
        wp.reset(new FJORMWhitespace().exec(wp.p()));
        //JOLogger.debug("===== after   "+wp.p());
        JSONObject jq = wp.p();
        int page = jq.optInt("page", 1);  // 
        int numPage = jq.optInt("numPage", 10);
        String orderby = wp.act().optString(JOProcConst.orderby);
        JSONObject op = (JSONObject) jq.clone();  // 查詢字串
        JOFunctional.exec("wp_before", wp);
        JSONObject mq = wp.mq();
        int rowCount = rowCount(proc.db(), mq);
        proc.set(JOProcObject.p_request, "total", rowCount);
        proc.set(JOProcObject.p_request, "page", page);
        proc.set(JOProcObject.p_request, "numPage", numPage);
        op.remove("page");  //移除查詢字串
        op.remove("numPage"); //移除查詢字串
        JOLogger.debug("===== mq : " + mq.toString(4));
        proc.set(JOProcObject.p_request, "ejo", JOTools.encode(op.toString())); // 查詢參數
        List<JSONObject> data = rows(proc.db(), mq, jq, numPage, page, orderby);
        String rid = wp.act().optString("rid", "$data");
        proc.put(rid, data);
        JOFunctional.exec("wp_after", wp);
        return data;
    }

    private void do_before(JOWPObject wp) throws Exception {

        JOFunctional.exec("wp_before", wp);
    }

    private List<JSONObject> rows(DB db, JSONObject mq, JSONObject jq, int num, int pageId, String orderby) throws Exception {
        String sql = mq.optString(param_sql);
        Object[] params = JOTools.to_marray(mq, param_fields, "value");
        String order_by = TextUtils.render_value(jq, orderby);
        StringBuilder sb = new StringBuilder();
        sb.append(" select t.* from ");
        sb.append(" ( select ROW_NUMBER() OVER (ORDER BY ").append(order_by).append(" )  rowid , ");
        sb.append("  c.* from ( ");
        sb.append(sql);
        sb.append(" ) c ) t ");
        sb.append(" where rowid > ").append(+(pageId - 1) * num);
        sb.append(" and rowid <= ").append((pageId) * num);
        JOLogger.debug("===== page sql --> \r\n" + sb.toString());
        JOLogger.debug("===== page params --> \r\n" + mq.opt(param_fields));
        return db.rows(sb.toString(), params);
    }

    protected int rowCount(DB db, JSONObject mq) {
        try {
            String sql = mq.optString(param_sql);
            sql = "select count(*) from ( " + sql + " ) t ";
            Object[] values = JOTools.to_marray(mq, param_fields, "value");
            JOLogger.debug("===== count sql --> \r\n" + sql);
            JOLogger.debug("===== count params --> \r\n" + mq.opt(param_fields));
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

}
