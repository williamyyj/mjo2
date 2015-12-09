/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.mjo.db;

import hyweb.jo.db.DB;
import hyweb.jo.org.json.JSONObject;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author William
 */
public class DBTest {


    public void db_test() throws Exception {
        String base = "D:\\Dropbox\\resources\\prj\\baphiq";
        DB db = new DB(base);

        try {
            List< JSONObject> rows = db.rows("select  top 5  * from store_t where cityid=?","10");
            for (JSONObject row : rows) {
                System.out.println(row);
            }
        } finally {
            db.close();
        }

    }

}
