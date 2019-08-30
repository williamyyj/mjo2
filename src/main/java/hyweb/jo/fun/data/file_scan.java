package hyweb.jo.fun.data;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.DateUtil;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOPath;
import hyweb.jo.util.JOTools;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author william
 */
public class file_scan implements IJOFunction<JSONObject, Object> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    public JSONObject exec(Object o) throws Exception {
        JSONObject ret = new JSONObject();
        JSONObject jo = load(o);
        File path = new File(jo.optString("path"));
        Date ds = DateUtil.to_date(jo.optString("start"));
        Date de = DateUtil.to_date(jo.optString("end"));
        long ts = (ds != null) ? ds.getTime() : -1;
        long te = (de != null) ? de.getTime() : new Date().getTime();
        String pstr = jo.optString("pattern", "([^\\s]+(\\.(?i)(class|jsp|jar))$)");
        Pattern p = Pattern.compile(pstr);
        scan(ret, path, ts, te, p);
        return ret;
    }

    private void scan(JSONObject jo, File f, long ts, long te, Pattern p) {
        if (f.isDirectory()) {
            File[] list = f.listFiles();
            for (File item : list) {
                scan(jo, item, ts, te, p);
            }
        } else if (f.exists() && p.matcher(f.getAbsolutePath()).find()
          && ((f.lastModified() > ts || ts == -1) && te > f.lastModified())) {
            String dateString = sdf.format(new Date(f.lastModified()));
            JOPath.setJA(jo, dateString, f.getAbsoluteFile());
        }
    }

    private JSONObject load(Object o) {
        return (o instanceof JSONObject) ? (JSONObject) o : JOTools.toJSONObject(o.toString());
    }

    public static void main(String[] args) throws Exception {
        String base = "D:\\will\\work\\nb\\mwork\\target\\mwork-1.0-SNAPSHOT";
       //String base = "D:\\will\\work\\eclipse\\ithSinica\\tais";
        JSONObject jo = new JSONObject();
 
        jo.put("start", "20190101");
       // jo.put("end",  "20181001000000");
        jo.put("seed", new Date().getTime());
        jo.put("path",base);
    //    jo.put("pattern", "([^\\s]+(\\.(?i)(jsp|jar|js))$)");
        String encode = JOTools.encode(jo.toString());
        System.out.println(encode);
       // JSONObject ret = (JSONObject) JOFunctional.exec("data.file_scan", jo);
       // System.out.println(ret.toString(4));
    }

}
