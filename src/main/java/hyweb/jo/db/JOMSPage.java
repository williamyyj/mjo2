package hyweb.jo.db;

import hyweb.jo.IJORows;
import static hyweb.jo.JOConst.param_fields;
import static hyweb.jo.JOConst.param_sql;
import hyweb.jo.fun.util.FJORMWhitespace;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;
import java.util.List;


/**
 * @author William
 */
public class JOMSPage implements IJORows<JSONObject> {

    private DB db;
    private int num;
    private JSONObject mq;
    private final String fid;
    private final String aid;

    public JOMSPage(DB db, String fid, String aid , JSONObject  params , int num) {
        this.db = db;
        this.num = num;
        this.fid = fid ; 
        this.aid = aid ; 
        try {
           JSONObject jq = new FJORMWhitespace().exec(params);
            this.mq = DBCmd.parser_fid(db, fid, aid, jq);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public int rowCount() {
        try {
            String sql = mq.optString(param_sql);
            sql = "select count(*) from ( " + sql + " ) t ";
            Object[] values = JOTools.to_marray(mq, param_fields,"value");
            return (Integer) db.fun(sql, values);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int pages() {
        int count = rowCount();
        int ret = count / num ;    
        return (ret*num== count) ? ret : ret+1 ; 
    }

    public List<JSONObject> rows(int pageId, String orderby) throws Exception {
        String sql = mq.optString(param_sql);
        Object[] params = JOTools.to_marray(mq, param_fields,"value");
        StringBuilder sb = new StringBuilder();
        sb.append(" select t.* from ");
        sb.append(" ( select ROW_NUMBER() OVER (ORDER BY ").append(orderby).append(" )  rowid , ") ; 
        sb.append("  c.* from ( ");
        sb.append(sql) ; 
        sb.append(" ) c ) t ");
        sb.append(" where rowid > ").append(+ (pageId-1) * num  );
        sb.append(" and rowid <= ").append((pageId)*num);
        System.out.println(sb.toString());       
        return db.rows(sb.toString(), params);
    }

    @Override
    public List<JSONObject> rows(int pageId) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
