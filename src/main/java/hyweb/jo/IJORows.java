/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo;

import java.util.List;

/**
 *
 * @author William
 * @param <M>
 */
public interface IJORows<M> {
    public List<M> rows(int pageId);
    public int rowCount();
    public int pages();
}
