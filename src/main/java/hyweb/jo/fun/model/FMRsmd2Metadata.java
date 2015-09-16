/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.model;

import hyweb.jo.IJOType;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOTypes;
import hyweb.jo.util.JOFunction;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author william
 */
public class FMRsmd2Metadata extends JOFunction<List<JSONObject>> {

    @Override
    public List<JSONObject> exec(Object[] args) throws Exception {
        List<JSONObject> metadata = new ArrayList();
        JOTypes types = (JOTypes) args[0];
        ResultSet rs = (ResultSet) args[1];
        ResultSetMetaData rsmd = rs.getMetaData();
        int len = rsmd.getColumnCount();
        for (int i = 0; i < len; i++) {
            int idx = i + 1;
            JSONObject meta = new JSONObject();
            String name = rsmd.getColumnName(idx);
            int dt = rsmd.getColumnType(idx);
            IJOType type = types.type(dt);
            meta.put("name", name);
            meta.put("dt", type.dt());
            meta.put("type", type);
            metadata.add(meta);
        }
        return metadata;
    }
}
