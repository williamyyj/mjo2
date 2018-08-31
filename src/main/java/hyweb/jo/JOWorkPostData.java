package hyweb.jo;

import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;
import java.util.Date;

/**
 *
 * @author william
 */
public class JOWorkPostData implements IJOPostData<JSONObject> {

    protected JSONObject data;
    protected String metadataId;
    protected String actorId;

    public JOWorkPostData() {
        data = new JSONObject();
    }

    public JOWorkPostData(String metadataId, String actorId) {
        this();
        this.metadataId = metadataId;
        this.actorId = actorId;
    }

    public JOWorkPostData(JSONObject data, String metadataId, String actorId) {
        this.data = new JSONObject(data);
        data.remove("meta"); // form field meta
        data.remove("act");  //  form field act 
        this.metadataId = metadataId;
        this.actorId = actorId;
    }

    @Override
    public String asString(String key, String dv) {
        return data.optString(key, dv);
    }

    @Override
    public int asInt(String key, int dv) {
        return data.optInt(key, dv);
    }

    @Override
    public long asLong(String key, long dv) {
        return data.optLong(key, dv);
    }

    @Override
    public double asDouble(String key, double dv) {
        return data.optDouble(key, dv);
    }

    @Override
    public byte[] asByte(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Date asDate(String key) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getMetadataId() {
        return this.metadataId;
    }

    @Override
    public String actorId() {
        return this.actorId;
    }

    @Override
    public String encode(String exclude) {
        exclude = (exclude == null) ? "" : exclude;
        String[] items = exclude.split(",");
        JSONObject p = new JSONObject(data);
        for (String item : items) {
            p.remove(item);
        }
        try {
            return JOTools.encode(p.toString());
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Object get(String key) {
        return data.get(key);
    }

    @Override
    public JSONObject params() {
        return data;
    }

}
