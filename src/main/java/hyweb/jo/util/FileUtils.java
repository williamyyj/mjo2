package hyweb.jo.util;

import java.io.File;

/**
 * @author william
 */
public class FileUtils {
    /**
     * 
     * @param path
     * @deprecated  move to hyweb.jo.data.JOFileUtils
     */
    @Deprecated
    public static void safe_dir(String path){
        File f = new File(path);
        if(!f.exists()){
            f.mkdirs();
        }
    }
}
