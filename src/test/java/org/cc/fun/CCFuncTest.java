package org.cc.fun;

import org.cc.module.ICCPF;
import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import hyweb.jo.org.json.JSONObject;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author william
 */
public class CCFuncTest {

    private JOProcObject proc;

    @Before
    public void doBefore() {
        proc = new JOProcObject(JOTest.prj_base("baphiq"));
    }

    @After
    public void doAfter() {
        System.out.println("...... proc.release() !!!! ");
        proc.release();
    }

    @Test
    public void test_proc_row() throws Exception {
        ICCPF<JSONObject> f = new org.cc.fun.proc.row();
        JSONObject event = new JSONObject();
        proc.params().put("$cmd", "select top 1 * from psRefStore ");
        JSONObject row = f.apply(proc,proc.params(),null);
        System.out.println(row);
    }

    @Test
    public void test_proc_rows() throws Exception {
        ICCPF<List<JSONObject>> f = new org.cc.fun.proc.rows();
        JSONObject evt = new JSONObject();
        evt.put("cityId", "01");
        evt.put("$cmd", "select * from psRefStore where cityId=${cityId,String} ");
        List<JSONObject> rows = f.apply(proc,evt,null);
        for (JSONObject row : rows) {
            System.out.println(row);
        }
    }

}
