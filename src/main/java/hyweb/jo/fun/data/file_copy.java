package hyweb.jo.fun.data;

import hyweb.jo.IJOFunction;
import hyweb.jo.data.JOFileUtils;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.DateUtil;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

/**
 *
 * @author william
 */
public class file_copy implements IJOFunction<JSONObject, Object> {

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

    @Override
    public JSONObject exec(Object o) throws Exception {
        JSONObject ret = new JSONObject();
        JSONObject jo = load(o);
        File path = new File(jo.optString("path"));
        File target = new File(jo.optString("target"));
        JOFileUtils.safe_dir(target);
        Date ds = DateUtil.to_date(jo.optString("start"));
        Date de = DateUtil.to_date(jo.optString("end"));
        long ts = (ds != null) ? ds.getTime() : -1;
        long te = (de != null) ? de.getTime() : new Date().getTime();
        String pstr = jo.optString("pattern", "([^\\s]+(\\.(?i)(class|jsp|jar))$)");
        Pattern p = Pattern.compile(pstr);
        scan(path, target, ts, te, p);
        return ret;
    }

    private void scan(File src, File target, long ts, long te, Pattern p) throws IOException {
        if (src.isDirectory()) {
            File nt = new File(target, src.getName());
            File[] list = src.listFiles();
            for (File item : list) {
                scan(item, nt, ts, te, p);
            }
        } else if (src.exists() && src.isFile() && p.matcher(src.getAbsolutePath()).find()
          && ((src.lastModified() > ts || ts == -1) && te > src.lastModified())) {
            JOFileUtils.safe_dir(target);
            File dest = new File(target, src.getName());
            System.out.println(src + "-->" + dest);
            JOFileUtils.copy(src, dest);
        }
    }

    private JSONObject load(Object o) {
        return (o instanceof JSONObject) ? (JSONObject) o : JOTools.toJSONObject(o.toString());
    }

    public static void main(String[] args) throws Exception {
         //String base = "D:\\will\\work\\nb\\mwork\\target\\mwork-1.0-SNAPSHOT";
         String base ="D:\\will\\work\\nb\\mwork\\src\\main";
        //String base = "D:\\will\\work\\eclipse\\ithSinica\\tais";
       // String base ="D:\\will\\work\\eclipse\\ithSinica\\rarebooks";
        String target = "D:\\will\\maintenance\\cp\\20181217";
        JSONObject jo = new JSONObject();

        //jo.put("start", "20180801");
       // jo.put("end", "20181001000000");
        jo.put("seed", new Date().getTime());
        jo.put("path", base);
        jo.put("target", target);
      //  jo.put("pattern", "([^\\s]+(\\.(?i)(jsp|jar|js|json))$)");
       jo.put("pattern", "([^\\s]+(\\.(?i)(jsp|java))$)");
        String encode = JOTools.encode(jo.toString());
        System.out.println(encode);
        JSONObject ret = (JSONObject) JOFunctional.exec("data.file_copy", jo);
        System.out.println(ret.toString(4));
    }
}
