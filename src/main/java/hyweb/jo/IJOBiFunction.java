/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo;

/**
 *
 * @author William
 * @param <RET>  回傳 結果
 * @param <P>  物件一
 * @param <Q> 物件二
 */
public interface IJOBiFunction<RET,P,Q> {
    public RET exec(P p , Q q) throws Exception ; 
}
