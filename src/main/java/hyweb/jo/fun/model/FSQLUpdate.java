package hyweb.jo.fun.model;

import hyweb.jo.model.IJOField;
import java.util.List;


/**
 * @author william
 */
public class FSQLUpdate extends FSQLBase {

    @Override
    public String exec(List<IJOField> fields) throws Exception {
        IJOField tb = fields.get(0);
        if (!"table".equals(tb.dt())) {
            throw new RuntimeException("JOField must tb field : " + tb);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("update ").append(tb.name()).append(" set");
        proc_cols_name(sql, fields);
        sql.append("\nwhere");
        proc_cols_cond(sql, fields);
        return sql.toString();
    }

    /*
     ${$set,mem_passwd,string,mempasswd}
     */
    private void proc_cols_name(StringBuilder sql, List<IJOField> fields) {
        for (IJOField fld : fields) {
            if (!"P".equals(fld.ct()) && !"table".equals(fld.dt())) {
                sql.append('\n').append("${$set")
                        .append(',').append(fld.name())
                        .append(',').append(fld.dt())
                        .append(',').append(fld.id())
                        .append('}');
            }
        }
        sql.append("${$rm}");
    }

    private void proc_cols_cond(StringBuilder sql, List<IJOField> fields) {
        int idx = 0;
        for (IJOField fld : fields) {
            if ("P".equals(fld.ct())) {
                sql.append(' ').append(fld.name())
                        .append("=${").append(fld.name())
                        .append(',').append(fld.dt())
                        .append(',').append(fld.id())
                        .append("} and");
                idx++;
            }
        }
        if (idx > 0) {
            sql.setLength(sql.length() - 3); // remvoe and 
        }
    }

}
