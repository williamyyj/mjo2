package hyweb.jo.fun.dao;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import hyweb.jo.ff.IJOFF;
import hyweb.jo.ff.JOFF;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.util.List;


/**
 * @author william
 */
public class WPDaoTest {

    public void test_valid() throws Exception {

        JOProcObject proc = new JOProcObject(JOTest.base);
        //proc.params().put("licenseNo", "1070002");
        //proc.params().put("uploadCityId", "00");

        //JSONObject row = (JSONObject) JOFunctional.exec("dao.wp_row_pk", wp);
        String line = "{\"act\":\"do_add\",\"address\":\"\",\"id\":\"a123456789\",\"birthday\":\"0550403\",\"cName\":\"\",\"cellphone\":\"\",\"city\":[\"01\",\"03\",\"04\"],\"email\":\"\",\"level\":[\"1\",\"4\"],\"licenseno\":\"1070001\",\"name\":\"王大明\",\"note\":\"新增測試\",\"sex\":\"M\",\"status\":\"1\",\"submit\":\"儲　存\",\"telphone\":\"\"}";
        JSONObject p = JOTools.loadJSON(line);
        proc.params().putAll(p);
        //p.put("level", "1,2,A");
        //p.put("birthday", "800101");
        JOWPObject wp = new JOWPObject(proc, "psSprayPerson", "save");
        try {
            IJOFF ff = JOFF.create(proc, "chk_spray_level");
            System.out.println("===> level cast " + ff.cast(p.opt("level")));
            System.out.println("===> level cast " + ff.cast("1"));
            JOFunctional.exec("dao.wp_eval", wp);
            System.out.println(proc.params());
            JSONObject valid = (JSONObject) JOFunctional.exec("dao.wp_valid", wp);
            System.out.println(valid.toString(4));
            JSONObject old = (JSONObject) JOFunctional.exec("dao.wp_row_edit", wp);
            wp.reset(old);
            JOFunctional.exec("dao.wp_eval", wp);
            //System.out.println(old.toString(4));
        } finally {
            proc.release();
        }
    }

}
