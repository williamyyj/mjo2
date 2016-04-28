package hyweb.jo.fun.model;


import hyweb.jo.IJOBiFunction;
import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONObject;
import java.util.List;

/**
 *  命令列匯入SQL 
 * @author william
 */
public class F2SQLInsert implements IJOBiFunction<String,List<IJOField>,JSONObject>{

  @Override
    public String exec(List<IJOField> fields,JSONObject row) throws Exception {
        IJOField tb = fields.get(0);
        if (!"table".equals(tb.dt())) {
            throw new RuntimeException("JOField must tb field : " + tb);
        }
        StringBuilder sql = new StringBuilder();
        sql.append(" insert into ").append(tb.name());
        proc_cols_name(sql, fields);
        sql.append("\r\n values ");
        proc_cols_value(sql, fields,row);
        sql.append(';');
        return sql.toString();
    }

    private void proc_cols_name(StringBuilder sql, List<IJOField> fields) {
        sql.append(" (");
        for (IJOField field : fields) {
            if (!"table".equals(field.dt()) && !"auto".equals(field.ft())) {
                sql.append(' ').append(field.name()).append(",");
            }
        }
        sql.setCharAt(sql.length() - 1, ')');
    }

    private void proc_cols_value(StringBuilder sql, List<IJOField> fields,JSONObject row) {
        sql.append(" (");
        for (IJOField fld : fields) {
            if (!"table".equals(fld.dt()) && !"auto".equals(fld.ft())) {
                Object o = fld.getFieldValue(row);
                sql.append(fld.type().sql_string(o,fld.ft())).append(',');
            }
        }
        sql.setCharAt(sql.length() - 1, ')');
    }
    
}
