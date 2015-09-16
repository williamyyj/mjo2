/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.IJOFunction;

import hyweb.jo.util.ENDE;

public class md5 implements IJOFunction<String, Object[]> {

    public String exec(Object[] args) throws Exception {
        StringBuilder sb = new StringBuilder();
        for (Object o : args) {
            if (o != null) {
                sb.append(o);
            }
        }
        byte[] buf = sb.toString().getBytes("UTF-8");
        return ENDE.md5(buf);
    }

}
