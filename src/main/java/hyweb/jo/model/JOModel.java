package hyweb.jo.model;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;

/**
 *
 * @author william
 */
public class JOModel {

    public static JSONObject row(JOProcObject proc, String metaId, String actId) throws Exception {
        JOWPObject wp = new JOWPObject(proc, metaId, actId);
        JSONObject act = wp.act();
        JSONObject row = (JSONObject) JOFunctional.exec("wp_row", wp);
        if (act.has("$valid")) {

        }
        if (act.has("$dataId")) {

        }
        return row;
    }
}
