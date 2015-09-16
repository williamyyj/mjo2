/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo;

import hyweb.jo.org.json.JSONObject;
import java.util.List;

/**
 * @author William
 */
public interface IJODao<M> {
    public M row(JSONObject jq) throws Exception ; 
    public List<M> rows(JSONObject jq) throws Exception ; 
    public Object fun(JSONObject jq) throws Exception ; 
    public int update(JSONObject jq) throws Exception ; 
    public int[] batch(JSONObject jq) throws Exception ; 
}
