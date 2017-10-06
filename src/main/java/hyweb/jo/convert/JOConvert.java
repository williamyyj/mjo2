package hyweb.jo.convert;
import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public abstract class JOConvert<E, F> implements IJOConvert<E, F> {

    protected JSONObject cfg;
    
    public JOConvert() {
        
    }

    @Override
    public F convert(IJOField fld, JSONObject row, F dv) throws Exception {
        return convert((E) fld.getFieldValue(row), dv);
    }

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        this.cfg = cfg;
    }

}
