/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo;

/**
 * @author William
 * @param <S>
 * @param <T>
 */

public interface IJOProcedure<S,T> {
    public void exec(S src, T target) throws Exception ; 
}
