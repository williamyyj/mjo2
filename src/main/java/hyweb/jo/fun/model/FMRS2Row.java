/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.model;


import hyweb.jo.IJOType;
import hyweb.jo.annotation.IAParams;
import java.sql.ResultSet;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunction;
import java.util.List;


@IAParams(
method = "exec" ,
paramTypes = {List.class,ResultSet.class}
)
public class FMRS2Row extends JOFunction<JSONObject> {

    public JSONObject exec(List metadata,  ResultSet rs) throws Exception {
        JSONObject jo = new JSONObject();
        for(Object o : metadata){
            JSONObject meta = (JSONObject) o;
            IJOType type = (IJOType) meta.opt("type");
            String name = meta.optString("name");
            jo.put(name, type.loadRS(rs, name));
        }
        return jo;
    }

 

}
