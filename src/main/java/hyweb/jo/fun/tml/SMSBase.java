package hyweb.jo.fun.tml;

import hyweb.jo.JOConfig;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author william SMSBase 使用 URLConnextion var $ --> @ 可改成MVEL attr @ --> $
 */
public class SMSBase {

    private Pattern p = Pattern.compile("\\@\\{([^\\}]+)\\}");
    private JSONObject cfg;

    public SMSBase(String base, String cfgId) {
        this.cfg = new JOConfig(base, cfgId).params();
    }

    public String to_url(JSONObject jo) throws Exception {
        StringBuilder sb = new StringBuilder();
        String enc = cfg.optString("$enc", "UTF-8");
        sb.append(cfg.optString("$url"));
        sb.append('?');
        JSONArray arr = cfg.optJSONArray("$fields");
        for (int i = 0; i < arr.length(); i++) {
            String fid = arr.optString(i);
            sb.append(fid);
            sb.append('=');
            sb.append(URLEncoder.encode(to_value(jo, fid), enc));
            sb.append('&');
        }
        sb.setLength(sb.length() - 1);
        return sb.toString();
    }

    public String to_value(JSONObject jo, String fid) {
        String value = cfg.optString(fid);
        StringBuffer sb = new StringBuffer();
        Matcher m = p.matcher(value);
        while (m.find()) {
            String re = jo.optString(m.group(1));
            re = (re == null) ? "" : re;
            m.appendReplacement(sb, re);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public String send(JSONObject jo) throws Exception {
        StringBuilder sb = new StringBuilder();
        String url = to_url(jo);
        HttpURLConnection uc = (HttpURLConnection) new URL(url).openConnection();
        uc.setConnectTimeout(60000);
        uc.setReadTimeout(30000);
        BufferedReader br = null;
        try {
            uc.setDoInput(true);
            uc.setDoOutput(true);
            uc.connect();
            br = new BufferedReader(new InputStreamReader(uc.getInputStream(), cfg.optString("encoding", "Big5")));
            String line;

            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } finally {
            if (br != null) {
                br.close();
            }
            if (uc != null) {
                uc.disconnect();
            }
        }

        return sb.toString();
    }

 

    public JSONObject cfg() {
        return cfg;
    }

}
