package hyweb.jo.fun.model;

import hyweb.jo.model.IJOField;
import java.util.List;

public class FSQLInsert extends FSQLBase {

    @Override
    public String exec(List<IJOField> fields) throws Exception {
        IJOField tb = fields.get(0);
        if (!"table".equals(tb.dt())) {
            throw new RuntimeException("JOField must tb field : " + tb);
        }
        StringBuilder sql = new StringBuilder();
        sql.append(" insert into ").append(tb.name());
        proc_cols_name(sql, fields);
        sql.append("\r\n values ");
        proc_cols_value(sql, fields);
        proc_scope(sql,fields.get(1));
        return sql.toString();
    }

    private void proc_cols_name(StringBuilder sql, List<IJOField> fields) {
        sql.append(" (");
        for (IJOField field : fields) {
            if (!"table".equals(field.dt())  && !"auto".equals(field.ft())) {
                sql.append(' ').append(field.name()).append(",");
            }
        }
        sql.setCharAt(sql.length() - 1, ')');
    }

    private void proc_cols_value(StringBuilder sql, List<IJOField> fields) {
        sql.append(" (");
        for (IJOField field : fields) {
            if (!"table".equals(field.dt()) && !"auto".equals(field.ft())) {
                sql.append(" ${");
                sql.append(field.name()).append(',');
                sql.append(field.dt()).append(',');
                sql.append(field.id());
                sql.append("},");
            }
        }
        sql.setCharAt(sql.length() - 1, ')');
    }

    private void proc_scope(StringBuilder sql, IJOField fld) {
        String jdbc = fld.cfg().optString("jdbc");
        if(jdbc.contains("identity")){
            sql.append(";SELECT SCOPE_IDENTITY()");
        }
    }

}
