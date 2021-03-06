package hyweb.jo.ff;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOPath;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author william
 */
public class vnum extends regex {

    public vnum() {

    }

    @Override
    public void __init_proc(JOProcObject proc) {
        int size = cfg.optInt("size");
        p = Pattern.compile(cfg.optString("pattern", "\\\\d{0" + size + "}"));
    }

    @Override
    protected Boolean apply(JSONObject row, String id, Object o) {
        try {
            int size = cfg.optInt("size");
            boolean nn = cfg.optBoolean("nn", false); // 空值不檢查
            String code = cfg.optString("code");
            JSONArray msg = cfg.optJSONArray("msg");
            String fv = (o != null) ? o.toString().trim() : "";
            if (!nn && fv.length() > 0) {
                if (fv.length() > size) {
                    // 長度異常
                    JOPath.set(row, "$fv:" + id, code + ":" + msg.optString(0));
                    return false;
                }
                Matcher m = p.matcher(fv);
                if (!(m.find() && m.start() == 0)) {
                    // 格式錯誤
                    JOPath.set(row, "$fv:" + id, code + ":" + msg.optString(1));
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
