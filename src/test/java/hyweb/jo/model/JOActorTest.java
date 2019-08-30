package hyweb.jo.model;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import static hyweb.jo.JOTest.base;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOExecute;
import hyweb.jo.util.JOTools;
import hyweb.jo.util.JOUtils;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOActorTest extends JOTest {

    @Test
    public void test_actor_object() throws Exception {
        JOProcObject proc = new JOProcObject(base);
       // String ejo="H4sIAAAAAAAAAFXQTQrCMBCG4auUweWASdoGEYSCIK68QOkimCwEtT-2CxHv7oR0BrP73kAeQj6wCefgPOxbMErvtkoDrsvIKmVVsmpZFjok5uIegRlRBBFDCBES0IzH_knvgDYB3UEV_VSkMlmVWVVZ1VnZWBDx0y3c_euPR5aRUWQPmUJWiHDXmW6PS5jeVH7QVOuPpU_zg5EjG4--Pzo9p_JdAQAA";
       // JSONObject p = JOTools.decode_jo(ejo);

        try {
            JOActorObject act = new JOActorObject(proc, JOUtils.toJA("psUnPesticide,query"));
            
            System.out.println(act);
            
        } finally {
            proc.release();
        }
    }
    
}
