package org.cc.module;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import hyweb.jo.org.json.JSONObject;
import org.cc.ff.CCFF;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOModuleTest  {

    @Test
    public void test_module() throws Exception {
        JOModule m = new JOModule(JOTest.project, "baphiq", "db_schema");
        JSONObject row = new JSONObject();
        row.put("ct", "P");
        row.put("jdbc", "char");
        row.put("size", "1");
        try {
            //  JSTL   $ff_astatus.apply(row)   
            JOProcObject proc = m.proc();
            System.out.println(CCFF.ff(m, "key").apply(row));
            System.out.println(CCFF.ff(m, "type").apply(row));
            System.out.println(CCFF.ff(m, "isnull").apply(row));
            System.out.println(row);
            CCFF.ff(m, "out").apply("jdbc", row);
        } finally {
            m.release();
        }

    }

}
