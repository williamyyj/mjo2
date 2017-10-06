package hyweb.jo.convert;

import hyweb.jo.IJOInit;
import hyweb.jo.JOProcObject;
import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 * @param <E>
 * @param <F>
 */
public interface IJOConvert<E, F> extends IJOInit<JSONObject> {

    public void render(JOProcObject proc) throws Exception;

    public F convert(E value, F dv) throws Exception;

    public F convert(String pattern, E value, F dv) throws Exception;

    public F convert(IJOField fld, JSONObject row, F dv) throws Exception;

}
