/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.ht;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import org.junit.Test;

/**
 *
 * @author william
 */
public class HTAutoCompleteTest {
    
    @Test
    public void test_DomManufNameData(){
        JOProcObject proc = new JOProcObject(JOTest.base);
        try {
            String html = new HTAutoComplete("{id:pesticideName, table:pesticidelic_t , column:pesticideName}").render(proc);
            System.out.println(html);
        } finally {
            proc.release();
        }
    }
}
