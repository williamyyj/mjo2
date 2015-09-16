/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import java.util.List;

/**
 * @author William
 */

public class Console {
    public static void each(List list){
        for(Object o : list){
            System.out.println(o);
        }
    }
}
