package hyweb.jo.fun.model;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOWPObjectTest extends JOTest {
    
    @Test
    public void test_scope_db(){
        JOProcObject proc = new JOProcObject(base);
        try{
            JOWPObject wp = new JOWPObject(proc,"ps_salesp_log","do_edit");
            List<IJOField> eFields = wp.fields();
            for(IJOField fld : eFields){
                System.out.println(fld);
            }
        } finally{
            proc.release();
        }
    }
    
}
