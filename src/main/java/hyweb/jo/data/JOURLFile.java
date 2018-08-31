package hyweb.jo.data;

import hyweb.jo.model.JOWPObject;
import java.io.File;

/**
 *
 * @author william
 *       
 *    file
 *    url 
 *    db 
 * 
 */
public class JOURLFile {

    public static byte[] load(JOWPObject wp) throws Exception {
    
        return null ; 
    }

    public static byte[] loadFromFile(File f) {
        try {
            return f.exists() ? JOFileUtils.loadData(f) : null;
        } catch (Exception e) {
            return null;
        }
    }
    
    
}
