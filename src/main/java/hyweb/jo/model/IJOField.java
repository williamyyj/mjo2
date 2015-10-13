package hyweb.jo.model;

import hyweb.jo.IJOInit;
import hyweb.jo.IJOType;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author William
 * @param <E>
 *
 */
public interface IJOField<E> extends IJOInit<JSONObject> {

    public String name();

    public String id();

    public String label();

    public String label(String lang);

    public String description();

    public int size();

    /**
     * P : 主鍵值 , M : 不能空 , Ｆ: 外部連結 Q：查詢欄位
     *
     * @return
     */
    public String ct();

    public String dt();

    public String ft();

    public String eval();

    public String args();

    public JSONObject cfg();

    public IJOType<E> type();

    public boolean valid(JSONObject wp) throws Exception;

    public Object getFieldValue(JSONObject row);
    
    public String getFieldText(JSONObject row);

    public void setFieldValue(JSONObject row, Object value);

    public void setErrData(JSONObject row, String message);

}
