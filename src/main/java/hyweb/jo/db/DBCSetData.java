/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.db;


import hyweb.jo.log.JOLogger;
import hyweb.jo.util.JOFunctional;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import static hyweb.jo.db.DBBase.__release;
import java.util.LinkedHashSet;


/**
 *   產出一個
 * @author william
 */
public class DBCSetData implements IDBCommand<Collection<? extends Object>> {

    @Override
    public Collection<? extends Object> execute(IDB db, String cmd, Object... params) {
        Collection list = new LinkedHashSet();
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            ps = db.connection().prepareStatement(cmd);
            JOFunctional.exec2(db.cfg().optString("@mfill"), ps, params);
            rs = ps.executeQuery();
            while(rs.next()){
                list.add(rs.getObject(1));
            }
        } catch (Exception ex) {
            JOLogger.error("Can't get data : " + cmd, ex);
        } finally {
            try {
                __release(ps);
                __release(rs);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

}
