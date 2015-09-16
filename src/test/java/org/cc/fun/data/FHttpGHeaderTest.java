/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.cc.fun.data;

import hyweb.jo.org.json.JSONObject;
import org.junit.Test;


/**
 *
 * @author William
 */
public class FHttpGHeaderTest {
    
    public FHttpGHeaderTest() {
    }

    /**
     * Test of exec method, of class FHttpGHeader.
     */
    @Test
    public void testExec() throws Exception {
        System.out.println("exec");
        String url_string = "http://gaz.tycg.gov.tw/ty_gazFront/images/h1.jpg";
        FHttpGHeader instance = new FHttpGHeader();
        JSONObject expResult = null;
        JSONObject result = instance.exec(url_string);
        System.out.println(result);
    }
    
}
