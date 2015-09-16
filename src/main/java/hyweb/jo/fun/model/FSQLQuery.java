package hyweb.jo.fun.model;

import hyweb.jo.JOConst;
import hyweb.jo.db.DBCmd;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.JOProcObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author william 統一使用 eval
 */
public class FSQLQuery extends FSQLBase {

    @Override
    public String exec(List<IJOField> fields) throws Exception {
        StringBuilder sql = new StringBuilder();
        IJOField fld = fields.get(0); // 一定是 table 欄位
        sql.append(" select ");
        proc_qf(sql, fields);
        sql.append("\nfrom ").append(fld.eval());
        sql.append("\nwhere 1=1 ");
        proc_qc(sql, fields);
        return sql.toString();
    }

    private void proc_qf(StringBuilder sql, List<IJOField> fields) {
        int idx = 0;
        for (IJOField fld : fields) {
            if ("qf".equals(fld.dt())) {
                sql.append(fld.eval()).append(',');
                idx++;
            }
        }
        if (idx == 0) {
            sql.append(" * ");
        } else if (idx > 0) {
            sql.setLength(sql.length() - 1); //skip ,
        }
    }

    private void proc_qc(StringBuilder sql, List<IJOField> fields) {
        for (IJOField fld : fields) {
            if ("qc".equals(fld.dt())) {
                sql.append('\n').append(fld.eval());
            }
        }
    }

}
