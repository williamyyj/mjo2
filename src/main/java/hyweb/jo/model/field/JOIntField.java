/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.model.field;

import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.type.JOIntType;


/**
 *
 * @author William
 */
@IAProxyClass(id="field.int")
public class JOIntField extends JOBaseField<Integer>{

     @Override
     public void __init__(JSONObject cfg) throws Exception {
        super.__init__(cfg);
        type = new JOIntType();
    }


    
}
