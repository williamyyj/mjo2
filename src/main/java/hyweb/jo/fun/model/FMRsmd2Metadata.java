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
            String label = rsmd.getColumnLabel(idx);
            int dt = rsmd.getColumnType(idx);
            IJOType type = types.type(dt);
            //System.out.println("===== rsmd : "+name+":::"+type.dt()+":::"+dt);
            meta.put("name", name);
            meta.put("dt", type.dt());
            meta.put("type", type);
            meta.put("label", label);
            meta.put("idx", idx);
            metadata.add(meta);
        }
        return metadata;
    }
}
