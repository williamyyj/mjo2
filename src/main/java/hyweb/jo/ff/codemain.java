/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.ff;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOPath;
import java.util.List;

/**
 *
 * @author william
 */
public class codemain extends combo {

    @Override
    public void __init_proc(JOProcObject proc) {
        content = new StringBuilder();
        init_maincode(proc);
        init_default(proc);
    }

    private void init_maincode(JOProcObject proc) {
        try {
            String codeId = cfg.optString("$codeId",cfg.optString("$id"));
            String sql = "select codeValue [value] , codeShow [label] from baphiqCodeMain where codeId = ? order by codeSort";
            List<JSONObject> rows = proc.db().rows(sql, codeId);
            if(rows!=null){
                for(JSONObject row:rows){
                    String name = row.optString("value");
                    JOPath.setJA(cfg, "$ord", name);
                    cfg.put(name,row.optString("label"));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
}
