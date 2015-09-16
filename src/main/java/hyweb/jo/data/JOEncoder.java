/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.data;

import com.google.common.io.BaseEncoding;
import com.ning.compress.lzf.LZFEncoder;
import hyweb.jo.IJOFunction;

/**
 *
 * @author William
 */
public class JOEncoder implements IJOFunction<String, String> {

    @Override
    public String exec(String p) throws Exception {
        return encoder(p);
    }

    public static String encoder(byte[] buf) throws Exception {
        byte[] ret = LZFEncoder.encode(buf);
        return BaseEncoding.base64Url().encode(ret);
    }

    public static String encoder(String text) throws Exception {
        return encoder(text.getBytes("UTF-8"));
    }

}
