package org.cc.ff;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import java.io.Serializable;
import org.cc.module.JOFF;
import org.mvel2.MVEL;

/**
 *
 * @author william
 */
public class mvel extends JOFF<Object> {

    private Serializable compileExpression;

    @Override
    public void init(JOProcObject proc) throws Exception {
        compileExpression = MVEL.compileExpression(cfg.optString("$cmd"));
    }

    @Override
    public Object apply(JSONObject row) throws Exception {
        row.put("$fv", this.getRowValue(row, null));
        return MVEL.executeExpression(compileExpression, row);
    }

    @Override
    public Object apply(String fid, JSONObject row) throws Exception {
        row.put("$fv", row.opt(fid));
        return MVEL.executeExpression(compileExpression, row);
    }

}
