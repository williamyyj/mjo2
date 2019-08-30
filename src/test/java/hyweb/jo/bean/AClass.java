/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.bean;

import java.util.Date;

/**
 *
 * @author william
 */
public class AClass {
    private int a;
    private String b;
    private Date c;

    @Override
    public String toString() {
        return "AClass{" + "a=" + a + ", b=" + b + ", c=" + c + '}';
    }

    public int getA() {
        return a;
    }

    public void setA(int a) {
        this.a = a;
    }

    public String getB() {
        return b;
    }

    public void setB(String b) {
        this.b = b;
    }

    public Date getC() {
        return c;
    }

    public void setC(Date c) {
        this.c = c;
    }
    
}
