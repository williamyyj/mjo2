package hyweb.jo.ff;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;

/**
 * 欄位函數
 *
 * @author william
 * @param <R>
 */
public interface IJOFF<R> {

    public void init(JOProcObject proc) throws Exception;

    /**
     *  標準用法
     * @param row
     * @return 
     */
    public R apply(JSONObject row);

    /**
     *  共用方式指定非預設Id
     * @param row
     * @param id
     * @return 
     */
    public R as(JSONObject row, String id);

    /**
     *   直接數值運算
     * @param fv
     * @return 
     */
    public R cast(Object fv);

    public String getContent();
    
    public JSONObject cfg();

}
