/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.data;

import com.google.common.io.BaseEncoding;
import com.ning.compress.lzf.LZFDecoder;
import hyweb.jo.IJOFunction;


/**
 *
 * @author William
 */
public class JODecoder implements IJOFunction<String,String>{

    @Override
    public String exec(String p) throws Exception {
        return decoder(p);
    }

    public static String decoder(byte[] buf) throws Exception {
        byte[] ret = LZFDecoder.safeDecode(buf);
        return new String(ret,"UTF-8");
    }

    public static String decoder(String text) throws Exception {
        return decoder(BaseEncoding.base64Url().decode(text));
    }
    
}
