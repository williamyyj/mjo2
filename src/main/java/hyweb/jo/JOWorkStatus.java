/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo;

import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public class JOWorkStatus extends JSONObject {

    private JOProcObject proc;

    public JOWorkStatus(JOProcObject proc) {
        this.proc = proc;
    }
}
