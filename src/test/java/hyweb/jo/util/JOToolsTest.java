/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author william
 */
public class JOToolsTest {

    public static void main(String[] args) throws UnsupportedEncodingException, Exception {
        String line = "hello world 尹燕家!";
        String ret = Base64.b64_encode(line.getBytes("UTF-8"));
        System.out.println(ret);
        //   aGVsbG8gd29ybGQg5bC554eV5a62
        //   aGVsbG8gd29ybGQgpKi/UK5h
        //  aGVsbG8gd29ybGQg5bC554eV5a62 
        String encode = JOTools.encode("hello world 尹燕家!");

        System.out.println(encode);

        ByteArrayOutputStream bo = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        gzip = new GZIPOutputStream(bo);
        gzip.write(line.getBytes("UTF-8"));
        gzip.close();
        for (byte b : bo.toByteArray()) {
            System.out.print(b & 0xFF);
            System.out.print(",");
        }
        System.out.println();

        String decode = JOTools.decode("H4sIAAAAAAAA_8tIzcnJVyjPL8pJUXi6Yefz9qlP121TBADF1ouzFgAAAA==");
         System.out.println(decode);

    }
}
