/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import java.io.File;

/**
 *
 * @author william
 */
public class FileUtils {
    public static void safe_dir(String path){
        File f = new File(path);
        if(!f.exists()){
            f.mkdirs();
        }
    }
}
