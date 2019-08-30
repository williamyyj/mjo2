package hyweb.jo.ht.impl;


import hyweb.jo.JOProcObject;
import hyweb.jo.ht.IHTemplate;
import hyweb.jo.org.json.JSONObject;

/**
 *
 * @author william
 */
public class JQAutoComplete implements IHTemplate {

    private String qfmt = "select distinct %s from %s";
    
    private JSONObject cfg;


    @Override
    public void render(JOProcObject proc, String cmdLine) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String context() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public JSONObject cfg() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


    
    






}
