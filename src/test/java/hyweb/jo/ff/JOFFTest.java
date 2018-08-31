package hyweb.jo.ff;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;
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
            //chk_city(proc);
            test_fmt(proc);
        } finally {
            proc.release();
        }
    }

    private void test_fmt(JOProcObject proc) {
        JOMetadata md = proc.metadata("ps_salesp_log");
        String line = "{$id:df,$ff:fmt_date,$pattern:yyyyMMdd}";
        IJOFF ff = JOFF.ff_create(proc, md, line);
        JSONObject row = new JSONObject();
        row.put("mem_ct", "100/05/02");
        Object ret = ff.as(row, "mem_ct");
        System.out.println("===== ret : "+ret);
        System.out.println(proc);
    }

    private void test_mapping(JOProcObject proc) {
        //  JOMetadata md = proc.metadata("ps_salesp_log");
        String line = "$id:posm_auditing,$ff:mapping,1:待審核,2:審核通過,3:申請失效,4:寄送光碟,5:結案";
        IJOFF ff = JOFF.ff_create(proc, null, line);
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

    private void test_combo1(JOProcObject proc) {
        String line = "$id:retstatus,$alias:retStatus,$ff:combo,$dv:請選擇,A:破損(A),B:盤減(B),C:盤增(C),D:農民退回(D),E:原廠回收(E),"
          + "F:過期退貨(F),G:過期毀損(G),H:資料異常(增加庫存)(H),X:其他(減少庫存)(X),"
          + "$ord:[$dv,A,B,C,D,E,F,G,H,X]";
        proc.params().put("retStatus", "3");
        proc.params().put("act", "edit");
        IJOFF ff = JOFF.ff_create(proc, null, line);
        JSONObject row = new JSONObject();
        row.put("retstatus", "D");

        Object ret = ff.as(row, "retstatus");
        System.out.println(ret);
        System.out.println(ff.cast("A"));
        String content = (String) proc.get(JOProcObject.p_request, "cb_retstatus", "");
        System.out.println("-------------" + content);
        System.out.println("-------------");
    }

    private void test_combo2(JOProcObject proc) {
        proc.params().put("retstatus", "A");
        IJOFF ff = JOFF.create(proc, "cb_retstatus");
        System.out.println(proc.ff("retstatus", "A"));
        String content = (String) proc.get(JOProcObject.p_request, "cb_retstatus", "");
        System.out.println(proc.get(JOProcObject.p_request, "$ff_retstatus", null));
        System.out.println("-------------" + content);
        System.out.println("-------------");
        System.out.println(proc);
    }

    private void test_combo3(JOProcObject proc) {
        IJOFF ff = JOFF.create(proc, "cb_retstatus");
        System.out.println(proc.ff("retstatus", "A"));
        String content = (String) proc.get(JOProcObject.p_request, "cb_retstatus", "");
        System.out.println(proc.get(JOProcObject.p_request, "$ff_retstatus", null));
        System.out.println("-------------" + content + "...............");
        System.out.println(proc);
    }

    private void test_cb_foreign(JOProcObject proc) {
        IJOFF ff = JOFF.create(proc, "cb_foreign");
        System.out.println(proc.ff("status", "2"));
        String content = (String) proc.get(JOProcObject.p_request, "cb_status", "");
        System.out.println(proc.get(JOProcObject.p_request, "$ff_status", null));
        System.out.println("-------------" + content + "...............");
        System.out.println(proc);
    }

    private void chk_city(JOProcObject proc) {
        JSONArray arr = new JSONArray();
        arr.put("01").put("20");
        proc.params().put("city", arr);
       //proc.params().put("city","01,20");
        IJOFF ff = JOFF.create(proc, "chk_city");
        String content = (String) proc.get(JOProcObject.p_request, "ht_city", "");
        content = content.replace("<br/>", "\n");
        System.out.println(proc.get(JOProcObject.p_request, "$ff_city", null));
        System.out.println("-----------------------------");
        System.out.println(content);
        System.out.println(".................................................");
    }
    
    private void radio_sex(JOProcObject proc) {
        proc.params().put("sex", "F");
        IJOFF ff = JOFF.create(proc, "radio_sex");
        String content = (String) proc.get(JOProcObject.p_request, "ht_sex", "");
        content = content.replace("<br/>", "\n");
        System.out.println(proc.get(JOProcObject.p_request, "$ff_sex", null));
        System.out.println("-----------------------------");
        System.out.println(content);
        System.out.println(".................................................");
    }

}
