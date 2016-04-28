/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.InflaterInputStream;


/**
 * @author william
 *    using hyweb..jo.codecs.JOCocdec
 */

public class ENDE {
  
    public final static int buf_size = 8192;
    public final static String enc = "UTF-8";

    public ENDE() {
    }

    public static String encode(String text) {
        if (text != null && text.length() > 0) {
            try {
                byte[] buf = encode(text.getBytes(enc));
                return Base64.u64_encode(buf);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }

    public static String decode(String text) {
        if (text != null && text.length() > 0) {
            try {
                byte[] buf = Base64.u64_decode(text);
                buf = decode(buf);
                return new String(buf, enc);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return "";
    }

    private static byte[] encode(byte[] data) throws Exception {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        DeflaterOutputStream zOut = null;
        try {
            zOut = new DeflaterOutputStream(out);
            zOut.write(data);
            zOut.finish();
        } finally {
            zOut.close();
        }
        return out.toByteArray();
    }

    private static byte[] decode(byte[] data) throws Exception {
        return loadData(new InflaterInputStream(new ByteArrayInputStream(data)));
    }

    private static byte[] loadData(InputStream is) throws Exception {
        BufferedInputStream bis = null;
        byte[] data = null;
        byte[] tmp = new byte[buf_size];
        int num = 0;
        try {
            bis = new BufferedInputStream(is);
            while ((num = bis.read(tmp)) > 0) {
                if (data == null) {
                    data = new byte[num];
                    System.arraycopy(tmp, 0, data, 0, num);
                } else {
                    byte[] old = data;
                    data = new byte[old.length + num];
                    System.arraycopy(old, 0, data, 0, old.length);
                    System.arraycopy(tmp, 0, data, old.length, num);
                }
            }
        } finally {
            bis.close();
        }
        return data;
    }

    public static String md5(byte[] buf) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(buf);
            result = toHexString(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static String sha1(byte[] buf) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA1");
            md.update(buf);
            result = toHexString(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
    

    private static String toHexString(byte[] in) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < in.length; i++) {
            String hex = Integer.toHexString(0xFF & in[i]);
            if (hex.length() == 1) {
                hexString.append('0');
            }
            hexString.append(hex);
        }
        return hexString.toString();
    }

    public static void main(String[] args) throws Exception {
        String test = "will168xxxxxxxxxxxxxxxxxx這是中xxxxxxxxxxx\r\nxxxxxxxxxxxxxxxxxxxxx                  XXXXXXXXXXXXXXXXXXXXX ";
        String en_string =  ENDE.encode(test);
        System.out.println(en_string);
        String de_string =  ENDE.decode(en_string);
        System.out.println(de_string);
        System.out.println(de_string.equals(test));
    }
}
