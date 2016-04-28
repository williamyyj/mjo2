/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.ende;

import de.idyl.winzipaes.AesZipFileEncrypter;
import de.idyl.winzipaes.impl.AESEncrypter;
import de.idyl.winzipaes.impl.AESEncrypterBC;
import hyweb.jo.IJOFunction;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 *
 * @author william
 */
public class encode_asezip implements IJOFunction<Boolean, JOWPObject> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        boolean ret = false ;
        JSONObject p = wp.p();
        JSONObject pp = wp.pp();
        String data = wp.optString("$data");
        String path = pp.optString("$path") + "/" + pp.optString("$fname") + ".zip";
        System.out.println("====== path : " + path);
        AESEncrypter encrypter = new AESEncrypterBC();
        AesZipFileEncrypter enc = new AesZipFileEncrypter(path, encrypter);// 儲存路徑檔名
        InputStream in = new ByteArrayInputStream(data.getBytes("UTF-8"));
        try {
            // add(壓縮後顯示名稱,資料流,密碼)enc.add(name, is, password)
            // enc.add(new String("農藥時程.xlsx".getBytes("big5"), "iso-8859-1"),
            // in, "password");//壓縮後顯示名稱,使用MyEclipse做中文測試ok
            enc.add(pp.optString("$fname") + ".xml", in, pp.optString("ende_pwd"));
            // enc.add(file, password);
            // enc.add(file, pathForEntry, password)
            ret = true ;
        } finally {
            enc.close();
            in.close();
        }
        return ret ;
    }

}
