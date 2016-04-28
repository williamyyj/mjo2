package hyweb.jo.fun;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
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
public class mjo_test extends JOTest {

    public void test_mjoc_rows() throws Exception {
        String jo_src = " {\"passwd\":\"1qaz2wsx\",\"VerifyCode\":\"2102\",\"uname\":\"王10\",\"tel_ext\":\"1234\",\"cid\":\"1030125\",\"tel_num\":\"12345678\",\"baphiqid\":\"UP0000000010\",\"email\":\"williamyyj@gmail.com\",\"stype\":\"[\",\"mid\":\"0\",\"passwd1\":\"1qaz2wsx\",\"act\":\"add\",\"dataid\":\"10\",\"tel_area\":\"02\",\"cname\":\"海博生技股份有限公司\",\"mobile\":\"\"}";
        JSONObject jo = JOTools.loadString(jo_src);
        JOProcObject proc = new JOProcObject(base);
        proc.params().put("baphiqId", "UP0000208509");
        try {
            List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec2("mjo", proc, "ps_file", "main_rows");
            for (JSONObject row : rows) {
                System.out.println(row);
            }
        } finally {
            proc.release();
        }
    }

    public void test_mspage() throws Exception {
        String jo_src = "{ct2:'2015/12/10'}";
        JSONObject p = JOTools.loadString(jo_src);
        JOProcObject proc = new JOProcObject(base);
        proc.params().put("baphiqId", "UP0000208509");
        try {
            JSONObject wp = MJOBase.wp(proc, "ws_file", "query", p);
            List<JSONObject> rows = (List<JSONObject>) JOFunctional.mjo(wp);
            for (JSONObject row : rows) {
                System.out.println(row);
            }
        } finally {
            proc.release();
        }
    }


}
