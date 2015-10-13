package hyweb.jo.fun.util;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public class FJORMWhitespace implements IJOFunction<JSONObject, JSONObject> {

    @Override
    public JSONObject exec(JSONObject src) throws Exception {
        JSONObject ret = new JSONObject();
        if (src != null) {
            JSONArray names = src.names();
            for (int i = 0; i < names.length(); i++) {
                String name = names.optString(i);
                Object o = src.opt(name);
                if (o instanceof String) {
                    String v = (String) o;
                    if (v != null && v.length() > 0) {
                        ret.put(name, v);
                    }
                } else {
                    ret.put(name, o);
                }

            }
        }
        return ret;
    }

}
