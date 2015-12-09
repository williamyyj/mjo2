package hyweb.jo.model;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import org.junit.Test;

/**
 * @author william
 */
public class JOMetadataTest extends JOTest {

    @Test
    public void test_metadata() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        try {
            JOMetadata metadata = new JOMetadata(base, "ps_return_log");
            System.out.println("===== fields size : " + metadata.size());
            System.out.println("===== fields size : " + metadata.get("v_retdt"));

        } finally {
            proc.release();
        }
    }

}
