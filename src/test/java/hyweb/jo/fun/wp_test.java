package hyweb.jo.fun;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import static hyweb.jo.JOTest.base;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.util.List;


/**
 *
 * @author william
 */
public class wp_test extends JOTest {

    public void cmd_rows_text() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        List<JSONObject> rows = null;

        try {
            JOWPObject wp = new JOWPObject(proc, "ps_paper", "query");
            // proc.params().put("baphiqid", "UP0000208509");
            //proc.params().put("sell", "00208509");
            rows = (List<JSONObject>) JOFunctional.exec("wp_page", wp);
            for (JSONObject row : rows) {
                System.out.println(row);
            }

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

}
