package hyweb.jo;

import java.util.Date;

/**
 * @author william B/S 架構接收表單參數
 * @param <M>
 */

public interface IJOPostData<M> {

    public String asString(String key, String dv);

    public int asInt(String key, int dv);

    public long asLong(String key, long dv);

    public double asDouble(String key, double dv);

    public byte[] asByte(String key);

    public Date asDate(String key);

    public Object get(String key);
    
    public String getMetadataId();
    
    public String actorId();
    
    public String encode(String exclude);
    
    public M params();
}
