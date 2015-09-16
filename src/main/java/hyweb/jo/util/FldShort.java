/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import hyweb.jo.IJOFunction;

/**
 * @author william
 * @deprecated 使用 hyweb.jo.fun.util.FldShort 
 */
@Deprecated
public class FldShort implements IJOFunction<String, String> {

    @Override
    public String exec(String text) throws Exception {
        if (text == null) {
            return null;
        }
        String text1 = text.toLowerCase();
        String[] buf = text1.split("[\\.\\-\\_]");
        StringBuilder sb = new StringBuilder();
        if (buf.length == 1) {
            sb.append(buf[0]);
        } else {
            for (int i = 1; i < buf.length; i++) {
                sb.append(buf[i]);
            }
        }
        return sb.toString();
    }

}
