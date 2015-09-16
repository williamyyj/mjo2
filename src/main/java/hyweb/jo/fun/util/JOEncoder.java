package hyweb.jo.fun.util;

import com.ning.compress.lzf.LZFEncoder;
import hyweb.jo.IJOFunction;
import hyweb.jo.util.Base64;
import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author William
 */
public class JOEncoder implements IJOFunction<String, String> {

    private String enc;
    public JOEncoder() {
        this("UTF-8");
        
    }

    public JOEncoder(String enc) {
        this.enc = enc;
    }

    @Override
    public String exec(String p) throws Exception {
        return exec_lzf(p);
    }

    public String exec_gzip(String p) throws Exception {
        if (p == null || p.length() == 0) {
            return null;
        }
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        GZIPOutputStream gzip = null;
        gzip = new GZIPOutputStream(out);
        gzip.write(p.getBytes(enc));
        gzip.close();
        return Base64.u64_encode(out.toByteArray());
    }

    public String exec_lzf(String p) throws Exception {
        if (p == null || p.length() == 0) {
            return null;
        }      
        return Base64.u64_encode(LZFEncoder.encode(p.getBytes(enc)));
    }

}
