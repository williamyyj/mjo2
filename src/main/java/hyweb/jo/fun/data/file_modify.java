package hyweb.jo.fun.data;

import hyweb.jo.IJOFunction;
import hyweb.jo.data.JOFileUtils;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.DateUtil;
import hyweb.jo.util.JOFunctional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author william
 */
public class file_modify implements IJOFunction<Boolean, JSONObject> {

    @Override
    public Boolean exec(JSONObject jo) throws Exception {
        Date now = new Date();
        JSONArray arr = (JSONArray) JOFunctional.exec("data.file_scan", jo);
        SimpleDateFormat sdf1 = new SimpleDateFormat("'mt_'yyyyMMdd'.json'");
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
        File report = new File(jo.optString("report"), sdf1.format(now));
        
        JSONArray data = new JSONArray();
        long mt = DateUtil.to_date(jo.optString("end")).getTime();
        for (Object o : arr) {
            File f = new File(o.toString());
            JSONObject item = new JSONObject();
            item.put("file", f.getAbsolutePath());
            item.put("lastModified", f.lastModified());
            data.add(item);
            System.out.println(f + ":::" + f.lastModified());
            //  f.setLastModified(mt);
        }
        jo.put("data", data);
        JOFileUtils.saveString(report, "UTF-8", jo.toString(4));
        return true;
    }

    public static void main(String[] args) throws Exception {
        String base = "D:\\will\\work\\nb\\mwork\\target\\mwork-1.0-SNAPSHOT";
        JSONObject jo = new JSONObject();
        jo.put("path", base);
        jo.put("start", "20171001");
        jo.put("end", "20180108180000");
        jo.put("report", "d:\\will\\report");
        JOFunctional.exec("data.file_modify", jo);

    }

}
