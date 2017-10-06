package hyweb.jo.convert;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOConvertTest {

    @Test
    public void test_map() throws Exception {
        JOProcObject proc = new JOProcObject(JOTest.base);
        try {
            JOMapConvert mc = new JOMapConvert("{id:cid,key:'newId,oldId',cond:psRetail}");
            mc.render(proc);
            IJOConvert cid = (IJOConvert) proc.get(JOProcObject.p_request,"$cvf_cid",null);
            String cName = (String) cid.convert("newId", "G-0165", "");
            System.out.println(cName);
        } finally {
            proc.release();
        }
    }

}
