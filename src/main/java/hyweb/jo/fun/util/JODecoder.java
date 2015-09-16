package hyweb.jo.fun.util;

import com.ning.compress.lzf.LZFDecoder;
import hyweb.jo.IJOFunction;
import hyweb.jo.util.Base64;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.zip.GZIPInputStream;

/**
 *
 * @author William
 */
public class JODecoder implements IJOFunction<String, String> {

    private String enc;

    public JODecoder() {
        this("UTF-8");
    }

    public JODecoder(String enc) {
        this.enc = enc;
    }

    @Override
    public String exec(String p) throws Exception {
        return exec_lzf(p);
    }

    public String exec_gzip(String text) throws Exception {
        if (text == null || text.length() == 0) {
            return null;
        }
        byte[] buf = Base64.u64_decode(text);
        GZIPInputStream gis = new GZIPInputStream(new ByteArrayInputStream(buf));
        BufferedReader br = new BufferedReader(new InputStreamReader(gis, "UTF-8"));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        return sb.toString();
    }

    public String exec_lzf(String text) throws Exception {
        if (text == null || text.length() == 0) {
            return null;
        }
        byte[] buf = Base64.u64_decode(text);
        return new String(LZFDecoder.decode(buf),enc);
    }

}
