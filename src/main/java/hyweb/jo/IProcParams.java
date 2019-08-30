package hyweb.jo;

import java.util.Date;

/**
 *
 * @author william
 * @param <M>
 */
public interface IProcParams<M> {

    public boolean optBoolean(String key);

    public boolean optBoolean(String key, boolean dv);

    public double optDouble(String key);

    public double optDouble(String key, double dv);

    public int optInt(String key);

    public int optInt(String key, int dv);

    public long optLong(String key);

    public long optLong(String key, long dv);

    public String optString(String key);

    public String optString(String key, String dv);

    public Date asDate(String key);

    public String asArrayToString(String key);

    public Object getParam(String jp);

    public void setParam(String jp, Object v);

    public M m();

};
