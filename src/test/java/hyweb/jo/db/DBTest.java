/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.db;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import hyweb.jo.log.JOLogger;
import org.junit.Test;

/**
 *
 * @author william
 */
public class DBTest {

    @Test
    public void db_cfg_test() {
        String base = JOTest.base;
        JOLogger.debug(base);
        JOProcObject proc = new JOProcObject(base,"dbWebPos");
        try {
            System.out.println(proc.db().cfg().toString(4));
        } finally {
            proc.release();
        }
    }

}
