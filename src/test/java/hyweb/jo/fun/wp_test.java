package hyweb.jo.fun;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import static hyweb.jo.JOTest.base;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author william
 */
public class wp_test extends JOTest {

    public void cmd_rows_text() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        List<JSONObject> rows = null;
        try {
            JOWPObject wp = new JOWPObject(proc, "baphiq2.ui_common", "mp");
            JOFunctional.exec("wp_group", wp);
            System.out.println(proc.get(JOProcObject.p_request, "uiImageMenuRows", ""));
        } finally {
            proc.release();
        }
    }

    public void wp_row_csv_test() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        String p_src = "{\"MESSAGE\":\"H221598175|彭春鴛|臺北市|松山區|| || |台北市松山區東市里15鄰南京東路四段67號四樓|1|正常        |0|0|0|\",\"SUCCESS\":\"Y\",\"CONTENT\":\"符合補助資格\"}";
        JSONObject params = JOTools.loadString(p_src);
        try {
            JOWPObject wp = new JOWPObject(proc, "wc_afa", "pollFarmerInfo", params, null);
            JSONObject row = (JSONObject) JOFunctional.exec("wp_csv2row", wp);
            wp.reset(null, row, null);
            Object ret = JOFunctional.exec("dao.wp_save", wp);
            System.out.println(ret);
        } finally {
            proc.release();
        }
    }

    public void wp_page_test() throws Exception {
        String jo_src = "{\"ct1\":\"2015/12/09\",\"status\":\"\",\"ct2\":\"2016/03/08\",\"button2\":\"送　　出\",\"mid\":\"5\",\"ftype\":\"\",\"act\":\"query\",\"baphiqid\":\"UP000000000016541333\"}";
        JSONObject jo = JOTools.loadString(jo_src);
        JOProcObject proc = new JOProcObject(base);
        try {
            JOWPObject wp = new JOWPObject(proc, "ws_file", "query", jo, null);
            List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("wp_page", wp);
            for (JSONObject row : rows) {
                System.out.println(row);
            }
        } finally {
            proc.release();
        }
    }

    public void doc_csv_test() throws Exception {

    }

    public void test_wp_mvel() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        String line = "{\"image\":\"\",\"classendDT\":\"Wed, 26 Sep 2012 00:00:00 GMT\",\"link\":\"http://pesticide.baphiq.gov.tw/web/ClassData.aspx?CA_NO=810\",\"classstartDT\":\"Wed, 26 Sep 2012 00:00:00 GMT\",\"description\":\"農藥販賣業者管理人員在職充實安全用藥教育訓練\",\"guid\":810,\"title\":\"農藥販賣業者管理人員訓練講習\",\"pubDate\":\"Tue, 11 Sep 2012 08:58:11 GMT\"}";
        JSONObject row = JOTools.loadString(line);
        System.out.println(row.toString(4));
        try {
            JOWPObject wp = new JOWPObject(proc, "joContent", "aims_class");
            row.put("ckey", "B");
            row.put("ctype", "1");
            row.put("cstatus", "1");
            row.put("copenwin", "2");
            row.put("csource", "農藥資訊服務網");
            wp.reset(row);
            JSONObject ret = (JSONObject) JOFunctional.exec("wp_setting", wp);
            JOFunctional.exec("dao.wp_save", wp);

        } finally {
            proc.release();
        }
    }

    public void test_nvarchar() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        String line = "{\"dataid\":\"6983\",\"cid\":\"\",\"cName\":\"國豐農業資材行\",\"cAddr\":\"花蓮縣玉里鎮興國路一段193號\",\"city\":\"花蓮縣\",\"zip\":\"O\",\"uName\":\"王𤦌蕾\",\"oldId\":\"O00073\",\"newId\":\"O00073\",\"comptel\":\"038881330\",\"updateDt\":\"20161220\",\"status\":\"1\",\"Trandatb\":\"\",\"SellType\":\"D\"}";
        JSONObject row = JOTools.loadString(line);
        System.out.println(row.toString(4));
        try {
            JOWPObject wp = new JOWPObject(proc, "psRetail", null);
            wp.reset(row);
            JOFunctional.exec("dao.wp_save", wp);

        } finally {
            proc.release();
        }
    }

    public void test_queryCheckbox() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        String line = "{level:[1,2]}";
        JSONObject row = JOTools.loadString(line);
        System.out.println(row.toString(4));
        try {
            JOWPObject wp = new JOWPObject(proc, "psSprayPerson", "query", row, null);

            List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("wp_page", wp);
            if (rows != null) {
                for (JSONObject crow : rows) {
                    System.out.println(crow);
                }
            }
        } finally {
            proc.release();
        }
    }

    @Test
    public void test_wp_rows_orderby() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        String ejo = "H4sIAAAAAAAAAKtWUkn1SE1MUbKKVjIyMLR8unOLgcmzOR1KOnCuKSrXDJVrDuLG6gCN8UvMTYUZY2ACVWJgCmOYwRjmIOUOhc75eUBblaIhymNtDRTyixQgPFMUnhkKzxzEUwIZ4JaZmpNSjGSEDky3DkyjDkwPUENicglQbWFpalElkJdSYAjkgWT1DUz0DQzBQkZwIXOIUHFJflFqSWUB0F9Kjkq1ANui22stAQAA";
        JSONObject p = JOTools.decode_jo(ejo);
        try {
            JOWPObject wp = new JOWPObject(proc, "rpt_pupload", "query",p,null);
            List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("wp_rows", wp);
            for (JSONObject row : rows) {
                System.out.println(row);
            }
        } finally {
            proc.release();
        }
    }

}
