/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.JOProcObject;
import hyweb.jo.util.JOFunctional;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author william
 */
public class loadm_test {


    public void exec_text() throws Exception {
        String root = "D:\\will\\work\\nb\\mwork\\target\\mwork-1.0-SNAPSHOT";
        String upload_root = root + "\\public";
        String base = root + "\\WEB-INF\\prj\\baphiq";
        JOProcObject proc = new JOProcObject(base);
        proc.params().put("baphiqid", "UP0000208509");
        try {

          Map<String,Object> m = (Map<String,Object>) JOFunctional.exec2("loadm", proc, "m_upload","map");
            System.out.println(m);
        } finally {
            proc.release();
        }
    }

}
