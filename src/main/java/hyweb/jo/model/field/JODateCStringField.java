/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOStringType;
import hyweb.jo.util.JOCDateFormat;
import hyweb.jo.util.TextUtils;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 *
 * @author william
 */
@IAProxyClass(id = "field.strcdate")
public class JODateCStringField extends JOBaseField<String> {

    @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new JOStringType();
    }

    @Override
    public String convert(Object o) {
        return (o != null) ? o.toString().trim() : null;
        //if (text != null) {
        //    return (text.length() <= this.size()) ? text : text.substring(0, size());
        //}
        //return text;
    }

    @Override
    public boolean valid(JSONObject wp) throws Exception {

        JSONObject row = wp.optJSONObject("$");
        JSONObject ref = wp.optJSONObject("$$");
        Object o = this.getFieldValue(row);
        // 處理日期格式
        if (o instanceof String && size() > 0) {

            String line = (String) o;
            if (!line.contains("/")) {
                StringBuilder sb = new StringBuilder();
                int idx = 0;
                char[] cbuf = line.toCharArray();
                for (char c : cbuf) {
                    if (idx == 3 || idx == 5) {
                        sb.append("/");
                    }
                    sb.append(c);
                    idx++;
                }
                this.setFieldValue(row, sb.toString());
            } else {
                Date d = JOCDateFormat.check(line);
                if (d != null) {
                    this.setFieldValue(row, JOCDateFormat.format(d));
                }
            }
            try {
                //  處理資料過大問題
                TextUtils.fixStringSize(this, row);
            } catch (UnsupportedEncodingException ex) {
                setFieldValue(row, null); // 保證資料可匯入db
            }
        }

        if (o instanceof Date) {
            String cdate = JOCDateFormat.format((Date) o);
            this.setFieldValue(row, cdate);
        }

        if (super.valid(wp)) {
            return true;
        }

        setErrData(row, null);
        System.out.println(row);
        return false;
    }
    
        /**
     *
     * @return
     */
    @Override
    public String gdt() {
        return cfg.optString("gdt", "date");
    }
}
