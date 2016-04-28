package hyweb.jo.fun.ende;

import de.idyl.winzipaes.AesZipFileDecrypter;
import de.idyl.winzipaes.impl.AESDecrypterBC;
import de.idyl.winzipaes.impl.ExtZipEntry;
import hyweb.jo.IJOFunction;
import hyweb.jo.data.JOFileUtils;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 *
 * @author william
 */
public class decode_asezip implements IJOFunction<Boolean, JOWPObject> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        JSONObject p = wp.p();
        JSONObject pp = wp.pp();
        String fileName = p.optString("fileName");
        String path = p.optString("path");
        byte[] fileStream = (byte[]) p.opt("fileStream");
        InputStream in = new ByteArrayInputStream(fileStream);
        AesZipFileDecrypter decoder = null;
        File f = new File(path,fileName+".zip");
        try {
            JOFileUtils.save(in,f);
            String password = pp.optString("ende_pwd");
            decoder = new AesZipFileDecrypter(f, new AESDecrypterBC());
            ExtZipEntry entry = decoder.getEntry(fileName);
            File target = new File(path,fileName);
            JOLogger.debug(target.getCanonicalFile());
            decoder.extractEntryWithTmpFile(entry,target , password);
            return true ;
        } catch(Exception e){
            e.printStackTrace();
            return false ;
        } finally {
            if (decoder != null) {
                decoder.close();
            }
            if (f.exists()) {
                f.delete();
            }
        }
       
    }

}
