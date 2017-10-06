package org.cc.module;

import org.cc.module.JOMEventEdit;
import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOMEvnetTest {

    @Test
    public void test_edit() throws Exception {
        JOProcObject proc = new JOProcObject(JOTest.base);
        proc.params().put("$metaId", "pos_code");
        proc.params().put("sid", "69");
        try {
            JOMEventEdit event = new JOMEventEdit();
            event.request(proc);
        } finally {
            proc.release();
        }

    }
}
