package hyweb.jo.db;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import hyweb.jo.JOConst;
import static hyweb.jo.JOConst.*;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOCache;
import java.util.Stack;

/**
 * @author William 整合DBJO ${base}/dp/xxxx.dao 統一使用 支援同文件資料靜態載入 ${ rem ,
 * xxxxxxxxxxxxxxxxxxxxxx} 註解 ${var,dt} 變數模式 ${ op:fld_name:dt} 動態欄位 fld_name op
 * map(fld_name) ${range:fld_name:dt} fld_name beteen map(fld_name_1) and
 * map(fld_name_2) ${like: fld_name} only 字串欄位 會利用 IDB 的 base() 取到 base 位置
 */
public class DBCmd {

    private final static Pattern p = Pattern.compile("\\$\\{([^\\}]+)\\}");
    private final static String dao_src = "dp";

    public static JSONObject parser_fid(IDB db, String fid, String actId, JSONObject row) throws Exception {
        File f = new File(db.base() + "/" + dao_src, fid + ".dao");
        JSONObject jq = new JSONObject(row.m());
        JSONObject dao_meta = JOCache.load(f);
        if (dao_meta != null) {
            // set_init_mq(mq, actId);
            String cmd = DBCmdItem.get_command(dao_meta, actId);
            if (cmd == null) {
                throw new RuntimeException("Can't find " + f + " in " + actId);
            }
            jq.put(JOConst.act, actId);
            jq.put(JOConst.cmd, cmd);
            return parser_cmd(db, jq);
        }
        return null;
    }

    public static JSONObject parser_cmd(IDB db, JSONObject jq) throws Exception {
        Stack<StringBuffer> stack = new Stack<StringBuffer>();
        JSONObject mq = new JSONObject();
        String actId = jq.optString(JOConst.act);
        String cmd = jq.optString(JOConst.cmd);
        set_init_mq(mq, actId);
        Matcher match = p.matcher(cmd);
        StringBuffer sql = new StringBuffer();
        while (match.find()) {
            String item = match.group(1);
            match.appendReplacement(sql, ""); // 直接清空
            if ("or".equals(item)) {
                stack.push(sql);
                sql = new StringBuffer();
            } else if ("end".equals(item)) {
                if (sql.length() > 0) {
                    String child = sql.toString().replaceFirst("and", "");
                    child = child.replaceAll("and", "or");
                    sql = stack.pop();
                    sql.append(" and (").append(child).append(" )");
                } else {
                    sql = stack.pop();
                }
            } else if (!item.startsWith("rem")) {
                DBCmdItem.process_item(db, sql, mq, jq, item);
            }
        }
        match.appendTail(sql);
        mq.put(param_sql, sql);
        return mq;
    }

    private static void set_init_mq(JSONObject mq, String actId) {
        mq.put(param_fields, new JSONArray());
        int ps = actId.lastIndexOf("_");
        String act = (ps > 0) ? actId.substring(ps + 1) : actId;
        mq.put(JOConst.act, act);
    }

    public static Object action(IDB db, String fid, JSONObject jq) throws Exception {
        return action(db, fid, jq.optString(JOConst.act), jq);
    }

    public static JSONObject row(IDB db, String fid, JSONObject params) throws Exception {
        String act_id = (params.has(JOConst.act)) ? params.optString(JOConst.act) : "row";
        return (JSONObject) action(db, fid, act_id, params);
    }

    public static Object fun(IDB db, String fid, JSONObject jq) throws Exception {
        String act_id = jq.optString(JOConst.act);
        JSONObject mq = parser_fid(db, fid, act_id, jq);
        return db.action(mq);
    }

    public static Object action(IDB db, String fid, String act, JSONObject params) throws Exception {
        return db.action(parser_fid(db, fid, act, params));
    }

}
