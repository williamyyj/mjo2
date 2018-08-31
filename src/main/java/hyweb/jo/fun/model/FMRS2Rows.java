package hyweb.jo.fun.model;


import hyweb.jo.annotation.IAParams;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunction;
import hyweb.jo.util.JOFunctional;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author william
 */
@IAParams(
        method = "exec",
        paramTypes = {List.class, ResultSet.class}
)
public class FMRS2Rows extends JOFunction<List<JSONObject>> {

    public List<JSONObject> exec(List<JSONObject> metadata, ResultSet rs) throws Exception {
        List<JSONObject> rows = new ArrayList<JSONObject>();
        Object[] p = new Object[]{metadata,rs};
        while(rs.next()){
            JSONObject row = (JSONObject) JOFunctional.exec("model.FMRS2Row", p);
            rows.add(row);
        }
        return rows;
    }
}
