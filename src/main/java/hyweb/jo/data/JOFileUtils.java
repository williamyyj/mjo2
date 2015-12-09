/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.data;


import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 *
 * @author William
 */
public class JOFileUtils {

    private static byte[] loadData(InputStream is) throws IOException {
        BufferedInputStream bis = null;
        byte[] data = null;
        byte[] tmp = new byte[1024];
        int num = 0;
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
        return data;
    }

    public static String loadString(File f, String enc) throws IOException {
        FileInputStream fis = new FileInputStream(f);
        try {
            return new String(loadData(fis), enc);
        } finally {
            fis.close();
        }
    }

    public static void saveString(File f, String enc, String text) throws IOException {
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(new FileOutputStream(f), enc);
            osw.write(text);
            osw.flush();
        } finally {
            if (osw != null) {
                osw.close();
            }
        }
    }

    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int length = 0;
        while ((length = in.read(buffer)) > 0) {
            out.write(buffer, 0, length);
        }
    }

    public static void copy(String src, String dest) throws IOException {
        copy(new File(src), new File(dest));
    }

    public static void copy(File src, File dest) throws IOException {
        if (src.isDirectory()) {
            if (!dest.exists()) {
                dest.mkdirs();
            }
            String files[] = src.list();
            for (String file : files) {
                //construct the src and dest file structure
                File srcFile = new File(src, file);
                File destFile = new File(dest, file);
                //recursive copy
                copy(srcFile, destFile);
            }
        } else {
            InputStream in = new FileInputStream(src);
            OutputStream out = new FileOutputStream(dest);
            try {
                copy(in, out);
            } finally {
                in.close();
                out.close();
            }
        }
    }

}
