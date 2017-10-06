package org.cc.ff;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;

/**
 * 欄位定義使用FUNCTION
 *
 * scope : request $ff_${id} 物件 $ff_${id}_contents 產出內容 $ff_ejo($, .....)
 *
 * @author william
 * @param <E>
 *
 *
 */
public interface ICCFF<E> {

    public void init(JOProcObject proc) throws Exception;

    /**
     * 客制化函數使用
     *
     * @param row
     * @return
     * @throws Exception
     */
    public E apply(JSONObject row) throws Exception;
    
    /**
     * 共用的函數使用
     *
     * @param row
     * @param fid
     * @return
     * @throws Exception
     */
    public E apply(String fid, JSONObject row) throws Exception;

    public JSONObject cfg();
    


}
