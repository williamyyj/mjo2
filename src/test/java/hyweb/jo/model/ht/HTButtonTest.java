/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.model.ht;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import hyweb.jo.ht.IHTCell;
import hyweb.jo.util.JOPackages;
import hyweb.jo.util.JOTools;
import org.junit.Test;

/**
 *
 * @author william
 */
public class HTButtonTest {

    @Test
    public void barcode() throws Exception {
        JOProcObject proc = new JOProcObject(JOTest.base);
        try {

            String line = "{id:doSetting,dt:ht.button,class:'doSetting',label:'設定',ejo:'{meta:pestcroprelation_t,seq:${seq}}',test:'isAllowDate==1 && null != domManufName '}";
            IHTCell combo = (IHTCell) JOPackages.newInstance("ht.button", line);
            combo.render(proc);
            String item = combo.display(JOTools.loadJSON("{seq:5,isAllowDate:1,domManufName:abc}"));
            System.out.println(item);
        } finally {
            proc.release();
        }
    }

}
