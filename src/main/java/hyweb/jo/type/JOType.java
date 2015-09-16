/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.type;

import hyweb.jo.IJOType;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.apache.log4j.Logger;

/**
 *
 * @author William
 */
public abstract class JOType<E> implements IJOType<E> {

    protected Logger log = JOLogger.getLogger(JOType.class);

    @Override
    public E check(Object o) {
        return check(o, null);
    }

    @Override
    public String sql_string(Object o) {
        E v = check(o);
        return (v != null) ? String.valueOf(v) : "null";
    }

    @Override
    public String json_string(Object o) {
        E v = check(o);
        return (v != null) ? String.valueOf(v) : "null";
    }

    @Override
    public String xml_string(Object value) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
