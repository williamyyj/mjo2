/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.model.md;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOMDObjectTest {
    

    @Test
    public void testAll() {
        JOProcObject proc = new JOProcObject(JOTest.base);
        try{
            proc.params().put("sid",14);
              proc.params().put("baphiqid","UP000000000000090002");
            JOMDObject mo = new JOMDObject(proc,"psSaleForeign","mrow");
            System.out.println(proc.optJSONObject("$mrow").toString(4));
            
        } catch(Exception e){
            proc.release();
        }
    }
    
}
