package hyweb.jo.model;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import hyweb.jo.util.JOFunctional;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author william
 */
public class JOMetadataTest extends JOTest {

    @Test
    public void test_metadata() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        try {
            JOMetadata metadata = new JOMetadata(base, "ps_saless_log");
            List<IJOField> fields = metadata.getFieldsByScope("db");
            String sql = (String) JOFunctional.exec("model.FSQLInsert", fields);
            Assert.assertEquals(true, sql != null && sql.contains("psSaleSSLog"));
            Assert.assertEquals(true, metadata.cfg().has("do_rows"));
        } finally {
            proc.release();
        }
    }

}
