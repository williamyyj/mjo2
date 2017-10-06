package hyweb.jo.ff;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;
import java.util.Date;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOFFTest {

    @Test
    public void test_barcode() throws Exception {
        JOProcObject proc = new JOProcObject(JOTest.base);
        try {
            test_mapping(proc);
        } finally {
            proc.release();
        }
    }

    private void test_fmt(JOProcObject proc) {
        JOMetadata md = proc.metadata("ps_salesp_log");
        String line = "$id:df1,$ff:fmt,$pattern:'yyyy/MM/dd HH:mm:ss'";
        IJOFF ff = JOFF.ff_create(proc, md, line);
        JSONObject row = new JSONObject();
        row.put("mem_ct", new Date());
        Object ret = ff.as(row, "mem_ct");
        System.out.println(ret);
        System.out.println(proc);
    }

    private void test_mapping(JOProcObject proc) {
        JOMetadata md = proc.metadata("ps_salesp_log");
        String line = "$id:posm_auditing,$ff:mapping,1:待審核,2:審核通過,3:申請失效,4:寄送光碟,5:結案";
        IJOFF ff = JOFF.ff_create(proc, md, line);
        JSONObject row = new JSONObject();
        row.put("auditing", 4);
        Object ret = ff.as(row, "auditing");
        System.out.println(ret);
        System.out.println(proc);
    }

    private void test_mvel(JOProcObject proc) {
        JOMetadata md = proc.metadata("ps_salesp_log");
        String line = "$id:barcode,$ff:mvel,$cmd:'barcode=\\'=\"\\'+$fv+\\'\"\\''";
        IJOFF ff = JOFF.ff_create(proc, md, line);
        JSONObject row = JOTools.loadString("{barcode:1234567890ABC}");
        ff.apply(row);
        System.out.println(row.opt("barcode"));
    }
}
