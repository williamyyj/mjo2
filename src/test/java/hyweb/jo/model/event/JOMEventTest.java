package hyweb.jo.model.event;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTestConst;
import hyweb.jo.util.JOTools;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOMEventTest {

    @Test
    public void test_query(){
        String base = JOTestConst.base("prj.baphiq");
        JOProcObject proc = new JOProcObject(base);
        try{
            proc.params().putAll(JOTools.loadString("{$metaId:posm_upload,$eventId:mail,license:1400000008}"));
            IJOMEvent event = new JOMEventQuery();
            event.execute(proc);
        } finally{
           proc.release();
        }
    }
}
