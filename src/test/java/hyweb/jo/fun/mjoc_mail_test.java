package hyweb.jo.fun;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.util.Map;
import java.util.Set;
import org.junit.Test;

/**
 * @author william
 */
public class mjoc_mail_test extends JOTest {

    @Test
    public void send_mail() throws Exception {
        String jo_src = " {\"newId\":\"00208509\",\"passwd\":\"1q2w3e4r\",\"oldId\":\"00208509\",\"status\":\"2\",\"dataId\":\"00208509\",\"mt\":\"Tue Sep 22 11:32:00 CST 2015\",\"ct\":\"Mon Sep 21 14:00:05 CST 2015\",\"baphiqId\":\"UP0000208509\",\"stock\":\"1\",\"email\":\"williamyyj@gmail.com\",\"stype\":4,\"telephone\":\"02-123456#\",\"cname\":\"英明貿易股份有限公司\",\"mobile\":\"12345\"}";
        JSONObject row = JOTools.loadString(jo_src);
        JOProcObject proc = new JOProcObject(base);
        JSONObject wp = MJOBase.wp(proc, "ps_store", "forgot_mail", row);
        JOMetadata metadata = MJOBase.metadata(wp);
        Set<Map.Entry<String,IJOField>> data = metadata.entrySet();        
        try{
            JOFunctional.exec("mjoc_mail", wp);
        } finally{
            proc.release();
        }
     

    }

}
