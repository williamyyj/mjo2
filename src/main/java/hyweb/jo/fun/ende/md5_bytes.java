package hyweb.jo.fun.ende;

import hyweb.jo.IJOFunction;
import java.security.MessageDigest;

/**
 * @author william
 */
public class md5_bytes implements IJOFunction<String, byte[]> {

    @Override
    public String exec(byte[] buf) throws Exception {
        MessageDigest algorithm = MessageDigest.getInstance("MD5");
        algorithm.reset();
        algorithm.update(buf);
        byte messageDigest[] = algorithm.digest();
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < messageDigest.length; i++) {
            int b = 0xff & messageDigest[i];
            hexString.append((b > 15) ? Integer.toHexString(0xFF & messageDigest[i]) : '0' + Integer.toHexString(0xFF & messageDigest[i]));
        }
        return hexString.toString();
    }

}
