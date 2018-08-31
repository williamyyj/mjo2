/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.ff;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.DateUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * line $id:df1,$ff:fmt,$pattern:'yyyy/MM/dd HH:mm:ss' json { "$id":"df1" .....
 * } $ff(df , $ , 'mem_ct')
 *
 * @author william
 */
public class fmt extends JOFFBase<String> {

    private SimpleDateFormat sdf;

    @Override
    public void __init_proc(JOProcObject proc) {
        sdf = new SimpleDateFormat(cfg.optString("$pattern"));
    }

    @Override
    protected String apply(JSONObject row, String id, Object fv) {
        return cast(fv);
    }

    @Override
    public String cast(Object fv) {
        if (fv instanceof Date) {
            return sdf.format((Date) fv);
        } else if (fv instanceof String){
            Date d  = DateUtil.to_date(((String)fv).trim());
            return (d!=null) ? sdf.format(d) : "";            
        }
        return "";
    }

}
