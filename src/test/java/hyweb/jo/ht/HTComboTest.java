package hyweb.jo.ht;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import hyweb.jo.util.JOPackages;
import hyweb.jo.util.JOTools;
import org.junit.Test;

/**
 *
 * @author william
 */
public class HTComboTest {

  
    public void barcode() throws Exception {
        JOProcObject proc = new JOProcObject(JOTest.base);
        try {

            String line = "{id:isDelete,rows:['N:N','Y:Y'],class:'combo',ejo:'{meta:barcode,licType:${licType},licNo:${licNo}}'}";
            IHTCell combo = (IHTCell) JOPackages.newInstance("ht.combo", line);
            combo.render(proc);
            String item = combo.display(JOTools.loadJSON("{licType:10,licNo:55555,isDelete:Y}"));
            System.out.println(item);
        } finally {
            proc.release();
        }
    }

    public void test_combo() throws Exception {
        JOProcObject proc = new JOProcObject(JOTest.base);
        try {

            IHTCell combo = new HTComboCell("{id:stype,$ext:':全部,C:批發,D:零售'}");
            combo.render(proc);
            System.out.println(combo.getHtml());
            String item = combo.display("C");
            System.out.println(item);
        } finally {
            proc.release();
        }
    }

      @Test
    public void test_combo_table() throws Exception {
        JOProcObject proc = new JOProcObject(JOTest.base);
        try {
            proc.params().put("sysId", "G3");
            IHTCell combo = new HTComboCell("{id:sysId,dp:ht.sysId}");
            combo.render(proc);
            System.out.println(combo.getHtml());

        } finally {
            proc.release();
        }
    }

}
