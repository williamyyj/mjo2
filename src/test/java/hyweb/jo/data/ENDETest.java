/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.data;

import com.ning.compress.lzf.LZFDecoder;
import com.ning.compress.lzf.LZFEncoder;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;
import org.junit.Test;

/**
 *
 * @author William
 */
public class ENDETest {
    

    public void test_ende() throws Exception {
        String test = "中文測試";
        byte[] en = LZFEncoder.encode(test.getBytes("UTF-8"));
        byte[] de = LZFDecoder.decode(en);
        System.out.println(new String(de,"UTF-8"));
    }
    
    @Test
    public void test_ejo(){
        String line ="H4sIAAAAAAAAAKtWSikwVLJSMjIwtNA3MAQiJR2QUHyZkhVIzMAQxDWCqzCDqTCCqzDTUXJIzMnJLwcqMgDKJSaXAFmFpalFlUowKUOwXC0Aipu1km4AAAA=";
        JSONObject ejo = JOTools.decode_jo(line);
        System.out.println(ejo);
    }
    
}
