/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.db;

import hyweb.jo.JOTest;
import org.junit.Test;

/**
 *
 * @author william
 */
public class DBCallTest {

    @Test
    public void db_cfg_test() throws Exception {
        DB db = new DB(JOTest.base);
        DBCall call = new DBCall();
        try {
            call.execute(db, "{call dbo.usp_updatesp ? }", 81);
        } finally {
            db.close();
        }
    }
}
