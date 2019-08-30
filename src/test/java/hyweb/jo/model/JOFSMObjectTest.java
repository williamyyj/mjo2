/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.model;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import static hyweb.jo.JOTest.base;
import hyweb.jo.util.JOUtils;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOFSMObjectTest extends JOTest {

    @Test
    public void test_actor_object() {
        JOProcObject proc = new JOProcObject(base);
        try {
            JOFSMObject fsm = new JOFSMObject(proc, "psSprayPerson");
            System.out.println(fsm.cfg().toString(4));
            System.out.println(fsm);
        } finally {
            proc.release();
        }
    }
}
