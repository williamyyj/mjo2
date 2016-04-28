/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.model.field;

/**
 *
 * @author william
 */


import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOClobType;


@IAProxyClass(id = "field.clob")
public class JOClobField extends JOBaseField<String> {

     @Override
    public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new JOClobType();
    }
}
