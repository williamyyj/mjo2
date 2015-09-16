/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo;

/**
 *
 * @author William
 */
public interface IJOResource {
    public void open() throws Exception ; 
    public boolean isActived() ; 
    public void close() throws Exception ; 
}
