/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.model;

import hyweb.jo.model.IJOField;
import java.util.List;

/**
 *
 * @author william
 */
public class FSQLPK  extends FSQLBase {
    @Override
    public String exec(List<IJOField> fields) throws Exception {
        IJOField tb = fields.get(0);
        if (!"table".equals(tb.dt())) {
            throw new RuntimeException("JOField must tb field : " + tb);
        }
        StringBuilder sql = new StringBuilder();
        sql.append("select * from ").append(tb.name());
        sql.append("\nwhere 1=1 ");
        proc_cols_cond(sql, fields);
        return sql.toString();
    }
    
    private void proc_cols_cond(StringBuilder sql, List<IJOField> fields) {
        for (IJOField fld : fields ) {
            if ("P".equals(fld.ct()) || "U".equals(fld.ct())) {              
                sql.append("${=,").append(fld.name())
                        .append(',').append(fld.dt())
                        .append(',').append(fld.id())
                        .append("}");
            }
        }
    }
}
