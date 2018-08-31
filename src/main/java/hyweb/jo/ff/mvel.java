package hyweb.jo.ff;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import java.io.Serializable;
import org.mvel2.MVEL;

/**
 *
 * @author william
 */
public class mvel<E> extends JOFFBase<E> {

    protected Serializable compileExpression;

    public mvel() {

    }

    @Override
    public void __init_proc(JOProcObject proc) {
        compileExpression = MVEL.compileExpression(cfg.optString("$cmd"));
    }

    @Override
    protected E apply(JSONObject row, String id, Object fv) {
        row.put("$fv", fv);
        row.put("$id", id);
        E ret = (E) MVEL.executeExpression(compileExpression, row);
        row.remove("$fv");
        row.remove("$id");
        return ret ;
    }

    @Override
    public E cast(Object fv) {
        return null;
    }
}
