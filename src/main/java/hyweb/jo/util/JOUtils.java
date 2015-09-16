package hyweb.jo.util;


import hyweb.jo.org.json.JSONObject;


/**
 *
 * @author william
 */
public class JOUtils {

    public static void rm(JSONObject jo, String n_old, String n_new) {
        if (jo.has(n_old)) {
            Object o = jo.remove(n_old);
            jo.put(n_new, o);
        }
    }

 

}
