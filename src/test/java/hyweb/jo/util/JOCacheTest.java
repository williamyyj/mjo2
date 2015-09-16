/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import hyweb.jo.org.json.JSONObject;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOCacheTest {

    /**
     * Test of load method, of class JOCache.
     *
     * @throws java.lang.Exception
     */
    public void testLoad() throws Exception {
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            String base = "D:\\Dropbox\\resources\\prj\\baphiq";

            @Override
            public void run() {
                JSONObject jo = JOCache.load(base, "cfg");
                System.out.println(jo.opt("test"));
            }
        };
        timer.schedule(task, 0, 10000);
        Thread.sleep(60000);
        timer.cancel();
    }

    @Test
    public void testLoadCombo() {
        String base = "D:\\Dropbox\\resources\\prj\\baphiq";
        List<JSONObject> rows = JOCache.loadCombo(base, "city");
        for (JSONObject row : rows) {
            System.out.println(row);
        }
    }

}
