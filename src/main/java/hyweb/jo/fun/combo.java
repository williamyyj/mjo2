/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;

/**
 *
 * @author william
 */
public class combo implements IJOFunction<Boolean, JSONObject> {

    @Override
    public Boolean exec(JSONObject wp) throws Exception {
        JOProcObject proc = MJOBase.proc(wp);
        JSONObject act = MJOBase.act(wp);
        JSONArray arr = act.optJSONArray("combo");
        return proc_combo_list(proc, arr);
    }

    private Boolean proc_combo_list(JOProcObject proc, JSONArray arr) throws Exception {
        if (arr != null) {
            for (int i=0;i<arr.length();i++) {
                String item = arr.optString(i);
                proc_combo(proc, item);
            }
        }
        return true;
    }

    private void proc_combo(JOProcObject proc, String item) throws Exception {
        String[] args = item.split(":");
        String id = args[0];
        String varId = args[1];
        String key = args[2];
        String label = args[3];
        JSONObject m = (JSONObject) JOFunctional.exec2("loadm", proc, id);
        JSONObject var = new JSONObject();
        if (m != null) {
            JSONArray names = m.names();
            for (int i = 0; i < names.length(); i++) {
                String name = names.optString(i);
                JSONObject part = m.optJSONObject(name);
                String k = part.optString(key);
                Object l = part.opt(label);
                var.put(k, l);
            }
        }
        proc.put(varId, var);

    }

}
