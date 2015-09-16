/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.data;

import com.ning.compress.lzf.LZFDecoder;
import com.ning.compress.lzf.LZFEncoder;
import org.junit.Test;

/**
 *
 * @author William
 */
public class ENDETest {
    
     @Test
    public void test_ende() throws Exception {
        String test = "中文測試";
        byte[] en = LZFEncoder.encode(test.getBytes("UTF-8"));
        byte[] de = LZFDecoder.decode(en);
        System.out.println(new String(de,"UTF-8"));
    }
    
}
