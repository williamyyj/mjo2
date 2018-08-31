/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun.valid;

import hyweb.jo.IJOFunction;

/**
 * @author william
 * 身份證號檢 
 */
public class twid implements IJOFunction<Boolean, String> {

    private final static int p[] = {10, 11, 12, 13, 14, 15, 16, 17, 34, 18, 19, 20, 21, 22, 35, 23, 24, 25, 26, 27, 28, 29, 32, 30, 31, 33};

    @Override
    public Boolean exec(String line) throws Exception {
        if (line != null && line.length() == 10) {
            char[] id = line.toCharArray();
            id[0] = Character.toUpperCase(id[0]);
            if(id[0]<'A' || id[0]>'Z'){
                return false ;
            }
            int a = p[id[0] - 'A'];
            int sum = (a % 10) * 9 + a / 10;
            a = 0;
            for (int i = 1; i < 9; i++) {
                a += (id[i] - '0') * (9 - i);
            }
            sum = (sum + a) % 10;
            a = (sum == 0) ? 0 : 10 - sum;
            return ((id[9] - '0') == a);
        }
        return false;
    }

}
