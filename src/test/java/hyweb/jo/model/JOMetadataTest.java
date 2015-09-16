package hyweb.jo.model;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import hyweb.jo.util.JOFunctional;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

/**
 * @author william
 */
public class JOMetadataTest extends JOTest {

    @Test
    public void test_metadata() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        try {
            JOMetadata metadata = new JOMetadata(base, "ps_store");
            List<IJOField> fields = metadata.getFieldsByScope("db");
            System.out.println("----------------------------------------------------");
            String sql = (String) JOFunctional.exec("model.FSQLInsert", fields);
            System.out.println(sql.indexOf("psStore"));
            Assert.assertEquals(true, sql.indexOf("psStore")>0);
            System.out.println("----------------------------------------------------");
            System.out.println(sql);
        } finally {
            proc.release();
        }
    }

}
