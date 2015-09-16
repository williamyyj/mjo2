package hyweb.jo.util;

import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class JOHttp {

    public static String text(String url_base, JSONObject jq) throws Exception {
        HttpURLConnection uc = null;
        BufferedReader br = null;
        StringBuilder content = new StringBuilder();
        if (jq != null) {
            try {
                StringBuilder us = new StringBuilder();
                us.append(url_base).append("?");
                JSONArray names = jq.names();
                if (names != null) {
                    for (int i = 0; i < names.length(); i++) {
                        String name = names.optString(i);
                        us.append("&");
                        us.append(name);
                        us.append("=");
                        us.append(URLEncoder.encode(jq.optString(name), "UTF-8"));
                    }
                }
                URL url = new URL(us.toString());
                System.out.println("===== url : " + url.toString());
                uc = (HttpURLConnection) url.openConnection();
                br = new BufferedReader(new InputStreamReader(uc.getInputStream()));
                String line = null;
                while ((line = br.readLine()) != null) {
                    content.append(line).append("\r\n");
                }
                return content.toString();
            } finally {
                br.close();
                uc.disconnect();
            }

        }
        return "";
    }

    public static JSONObject get(String url_base, JSONObject jq) throws Exception {
        JSONObject jo = new JSONObject();
        jo.put("ejo", JOTools.encode(jq.toString()));
        String text = text(url_base, jo);
        return JOTools.decode_jo(text);
    }

    public static JSONObject ejo(String url_base, JSONObject jq) throws Exception {
        return get(url_base, jq);
    }

}
