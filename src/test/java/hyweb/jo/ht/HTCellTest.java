package hyweb.jo.ht;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import java.util.Date;
import org.junit.Test;

/**
 *
 * @author william
 */
public class HTCellTest {

    public void combo_test() {
        JOProcObject proc = new JOProcObject(JOTest.base);
        try {
            String combo = new HTComboCell("{id:gstatus,$ext:':請選擇,1:通過,2:不通過'}").render(proc);
            System.out.println(combo);
        } finally {
            proc.release();
        }
    }

    public void combo_ht_test() {
        JOProcObject proc = new JOProcObject(JOTest.base);
        try {
            String combo = new HTComboCell("{id:cityId,dp:ht.pcity}").render(proc);
            System.out.println(combo);
        } finally {
            proc.release();
        }
    }

    @Test
    public void mapping_test() {
        IHTCell cell = new HTMappingCell("{id:gstatus,1:通過,2:不通過}");
        System.out.println(cell.display("1"));
        System.out.println(cell.display("2"));
        System.out.println(cell.display("A"));
    }

    @Test
    public void date_test() {
        IHTCell cell = new HTDateCell("{id:gstatus,fmt:'yyyy/MM/dd HH:mm:ss'}");
        cell.render(null);
        System.out.println(cell.display(new Date()));
        System.out.println(cell.display(null));
    }

}
