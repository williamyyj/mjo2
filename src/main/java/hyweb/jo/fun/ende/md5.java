package hyweb.jo.fun.ende;

import hyweb.jo.IJOFunction;

/**
 *
 * @author william
 */
public class md5 implements IJOFunction<String, String> {
    private  IJOFunction<String, byte[]> fun = new md5_bytes();
    @Override
    public String exec(String text) throws Exception {
        return fun.exec(text.getBytes("UTF-8"));
    }
}
