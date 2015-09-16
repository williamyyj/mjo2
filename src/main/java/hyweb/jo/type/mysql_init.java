/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.type;

import hyweb.jo.IJOInit;
import java.sql.Types;



/**
 *
 * @author william
 *  這部份改成annotation
 *
 */
public class mysql_init implements IJOInit<JOTypes> {

    public void __init__(JOTypes self) throws Exception {
        self.put(Types.DATE,new JODateType());
        self.put(Types.TIME,new JODateType());
        self.put(Types.TIMESTAMP,new JODateType());
    }
    
}
