package hyweb.jo.db;

import java.sql.CallableStatement;
import java.sql.SQLException;

/**
 *
 * @author william
 */
public class DBCall implements IDBCommand<Object> {

    @Override
    public Object execute(IDB db, String cmd, Object... params) throws Exception {
        CallableStatement cs = null;
        int ret = 0;
        try {
            cs = db.connection().prepareCall(cmd);
            fill(cs,params);
            ret = cs.executeUpdate();
            return ret;
        } finally {
            if (cs != null) {
                cs.close();
            }
        }
    }

    private void fill(CallableStatement cs, Object[] params) throws SQLException {
        int idx =1 ;
        for(Object o : params){
            cs.setObject(idx++, o);
        }
    }

}
