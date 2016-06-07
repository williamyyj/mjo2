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

    public final static String node_form = "$frm";
    public final static String node_error = "$err";

    public String name();

    public String id();

    public String label();

    public String label(String lang);

    public String description();

    public int size();

    /**
     * constranint type , P : 主鍵值 , M : 不能空 , Ｆ: 外部連結 Q：查詢欄位 (移除） I : index
     *
     * @return
     */
    public String ct();

    /**
     * data type 資料型別
     *
     * @return
     */
    public String dt();

    /**
     * fromat type 格式化型別
     *
     * @return
     */
    public String ft();

    /**
     * group type 同類型的資料型別 如 日期字串 日期 民國日期
     *
     * @return
     */
    public String gdt();

    public String eval();

    public String args();

    public JSONObject cfg();

    public IJOType<E> type();

    @Deprecated
    public boolean valid(JSONObject wp) throws Exception;
   
    public Object getFieldValue(JSONObject row);

    public String getFieldText(JSONObject row);

    public String getFieldName();

    public void setFieldValue(JSONObject row, Object value);

    public void setErrData(JSONObject row, String message);

    public E convert(Object o);

}
