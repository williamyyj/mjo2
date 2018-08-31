package hyweb.jo.fun.data;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.DateUtil;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.io.File;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author william
 */
public class file_scan implements IJOFunction<JSONArray, Object> {

    @Override
    public JSONArray exec(Object o) throws Exception {
        JSONArray arr = new JSONArray();
        JSONObject jo = load(o);
        File path = new File(jo.optString("path"));
        Date ds = DateUtil.to_date(jo.optString("start"));
        Date de = DateUtil.to_date(jo.optString("end"));
        long ts = (ds != null) ? ds.getTime() : -1;
        long te = (de != null) ? de.getTime() : new Date().getTime();
        String pstr = jo.optString("pattern", "([^\\s]+(\\.(?i)(class|jsp|jar))$)");
        Pattern p = Pattern.compile(pstr);
        scan(arr, path, ts, te, p);
        return arr;
    }

    private void scan(JSONArray arr, File f, long ts, long te, Pattern p) {
        if (f.isDirectory()) {
            File[] list = f.listFiles();
            for (File item : list) {
                scan(arr, item, ts, te, p);
            }
        } else if (f.exists() && p.matcher(f.getAbsolutePath()).find()
          && ((f.lastModified() > ts || ts == -1) && te > f.lastModified())) {
            arr.add(f.getAbsoluteFile());
        }
    }

    private JSONObject load(Object o) {
        return (o instanceof JSONObject) ? (JSONObject) o : JOTools.toJSONObject(o.toString());
    }

    public static void main(String[] args) throws Exception {
        String base = "D:\\will\\work\\nb\\mwork\\target\\mwork-1.0-SNAPSHOT";
        JSONObject jo = new JSONObject();
        jo.put("path", base);
        // jo.put("start","20171001");
        // jo.put("end","20180125000000");
        jo.put("start", "20180125");
        jo.put("end", "20180225000000");
        JSONArray arr = (JSONArray) JOFunctional.exec("data.file_scan", jo);
        for (Object o : arr) {
            System.out.println(o);
        }
    }

}
