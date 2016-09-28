package hyweb.jo.db;

import java.util.HashMap;
import java.util.Map;
import static hyweb.jo.JOConst.*;
import hyweb.jo.IJOType;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;


/**
 * @author William 動態SQL用
 *
 */
public class DBCmdItem {

    private static Map<String, String> op;

    private static Map<String, String> op() {
        if (op == null) {
            op = new HashMap<String, String>();
            op.put("=", "=");
            op.put(">", ">");
            op.put(">=", ">=");
            op.put("<", "<");
            op.put("<=", "<=");
            op.put("$like", "like"); //   xxx%
            op.put("$all", "like");   //   %xxxx%
            op.put("$range", "");  //     fld  betten a and b 
            op.put("$set", "="); // for update 
            op.put("$rm", "");  // 移除最後 ","   
            op.put("@","");  //    for table query or const 
        }
        return op;
    }

    public static String get_command(JSONObject meta, String id) {
        Object o = meta.opt(id);
        if (o instanceof String) {
            return (String) o;
        } else if (o instanceof JSONArray) {
            JSONArray arr = meta.optJSONArray(id);
            StringBuilder sb = new StringBuilder();
            if (arr != null) {
                for (int i = 0; i < arr.length(); i++) {
                    sb.append(arr.opt(i));
                }
            }
            return sb.toString();
        }
        return null;
    }

    public static void process_item(IDB dp, StringBuffer sb, JSONObject mq, JSONObject row, String item) throws Exception {
        String[] args = item.split(",");
        String name = args[0];
        
         if (name.charAt(0)=='@'){
            process_const(dp, sb, mq, row, args);
        } else if (op().containsKey(name)) {
            process_op_item(dp, sb, mq, row, args);
        } else {
            process_var_item(dp, sb, mq, row, args);
        }

    }

    private static void process_op_item(IDB dp, StringBuffer sb, JSONObject mq, JSONObject row, String[] args) {
        String name = args[0];
        if ("=".equals(name) || ">".equals(name) || ">=".equals(name)
                || "<".equals(name) || "<=".equals(name)) {
            process_op2(dp, sb, mq, row, args);
        } else if ("$like".equals(name)) {
            process_like(dp, sb, mq, row, args);
        } else if ("$all".equals(name)) {
            process_all(dp, sb, mq, row, args);
        } else if ("$range".equals(name)) {
            process_range(dp, sb, mq, row, args);
        } else if ("$set".equals(name)) {
            process_set(dp, sb, mq, row, args);
        } else if ("$rm".equals(name)) {
            process_rm(dp, sb, mq, row, args);
        } 
    }

    private static void set_field(JSONArray fields, IDB dp, JSONObject row, String name, String dt, String id, Object v) {
        JSONObject fld = new JSONObject();
        fld.put("name", name);
        fld.put("id", id);
        fld.put("dt", dt);
        fld.put("value", v);
        fields.put(fld);
    }

    private static void process_var_item(IDB dp, StringBuffer sb, JSONObject model, JSONObject row, String[] args) {
        //    ${field,dt}  |   ${field,dt,alias} 
        //System.out.println(Arrays.toString(args));
        String name = args[0];
        String dt = args[1];
        String alias = (args.length > 2) ? args[2] : null;
        JSONArray fields = model.optJSONArray(param_fields);
        Object v = get_value(dp, row, name, dt, alias);
        set_field(fields, dp, row, name, dt, alias, v);
        sb.append("?");
    }

    private static Object get_value(IDB dp, JSONObject row, String name, String dt, String alias) {
        //System.out.println("===== debug row " + row);
        //System.out.println("===== debug name " + name);
        //System.out.println("===== debug alias " + alias);
        IJOType<?> type = dp.types().type(dt);
        Object value = null;
        if (row.has(name)) {
            value = type.check(row.opt(name));
        }
        if (value == null &&  alias!=null && row.has(alias)) {
            value = type.check(row.opt(alias));
        }
        //JOLogger.debug("==== check value --> "+row.has(name) +","+ name+","+dt+","+alias+":["+value+"]");
        return value;
    }

    private static void process_op2(IDB dp, StringBuffer sb, JSONObject mq, JSONObject row, String[] args) {
        String op_name = args[0];
        String field = args[1];
        String dt = args[2];
        String alias = (args.length > 3) ? args[3] : null;
        Object v = get_value(dp, row, field, dt, alias);
        if (v != null) {
            JSONArray fields = mq.optJSONArray(param_fields);
            set_field(fields, dp, row, field, dt, alias, v);
            sb.append(" and ").append(field).append(' ').append(op_name).append(" ?");
        }
    }

    private static void process_like(IDB dp, StringBuffer sb, JSONObject model, JSONObject row, String[] args) {
        String op_name = args[0];
        String field = args[1];
        String dt = args[2];
        String alias = (args.length > 3) ? args[3] : null;
        Object v = get_value(dp, row, field, dt, alias);
        if (v != null) {
            JSONArray fields = model.optJSONArray(param_fields);
            set_field(fields, dp, row, field, dt, alias, v + "%");
            sb.append(" and ").append(field).append(" like ").append(" ?");
        }
    }

    private static void process_all(IDB dp, StringBuffer sb, JSONObject model, JSONObject row, String[] args) {
        String op_name = args[0];
        String field = args[1];
        String dt = args[2];
        String alias = (args.length > 3) ? args[3] : null;
        Object v = get_value(dp, row, field, dt, alias);
        if (v != null) {
            JSONArray fields = model.optJSONArray(param_fields);
            set_field(fields, dp, row, field, dt, alias, "%" + v + "%");
            sb.append(" and ").append(field).append(" like ").append(" ?");
        }
    }

    private static void process_range(IDB dp, StringBuffer sb, JSONObject model, JSONObject row, String[] args) {
        String op_name = args[0];
        String field = args[1];
        String dt = args[2];
        String alias = (args.length > 3) ? args[3] : null;
        JSONArray fields = model.optJSONArray(param_fields);
        Object v1 = get_value(dp, row, field + "_1", dt, alias + "_1");
        set_field(fields, dp, row, field + "_1", dt, alias + "_1", v1);
        Object v2 = get_value(dp, row, field + "_2", dt, alias + "_2");
        set_field(fields, dp, row, field + "_2", dt, alias + "_2", v2);
        sb.append(" and").append(field).append(" beteen ? and ?");
    }

    private static void process_set(IDB dp, StringBuffer sb, JSONObject model, JSONObject row, String[] args) {
        String op_name = args[0]; // set 
        String field = args[1]; // 
        String dt = args[2]; // 
        String alias = (args.length > 3) ? args[3] : null;
        JSONArray fields = model.optJSONArray(param_fields);
        Object v = get_value(dp, row, field, dt, alias);
        if (v != null) {
            set_field(fields, dp, row, field, dt, alias, v);
            sb.append(' ').append(field).append(" = ").append(" ? ,");
        }
    }

    private static void process_rm(IDB dp, StringBuffer sb, JSONObject model, JSONObject row, String[] args) {
        String op_name = args[0];
        int ps = sb.lastIndexOf(",");
        if (ps > 0) {
            sb.setLength(ps - 1);
        }
    }

    private static void process_const(IDB dp, StringBuffer sb, JSONObject model, JSONObject row, String[] args) {
       // 這是必填 or 有預設值
        String id = args[0];
        String v =  row.optString(id,null);
        if(v!=null){
            sb.append(v);
        }
    }





}
