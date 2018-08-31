/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.model.ht;

import hyweb.jo.JOProcObject;
import hyweb.jo.annotation.IAProxyClass;
import hyweb.jo.ht.HTCell;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;
import org.mvel2.MVEL;
import org.mvel2.templates.TemplateRuntime;

/**
 *
 * @author william
 */
@IAProxyClass(id = "ht.button")
public class HTButton extends HTCell {

    private String fmt = "<button id=\"%s\" name=\"%s\" class=\"%s\" ejo=\"%s\"  type=\"button\">%s</button>";

    public HTButton() {

    }

    public HTButton(String line) {
        super(line);
    }

    @Override
    public String render(JOProcObject proc) {
        return "";
    }

    @Override
    public String display(Object p, Object dv) {
        try {
            JSONObject m = (JSONObject) p;
            StringBuilder sb = new StringBuilder();
            String line = (String) TemplateRuntime.eval(cfg.optString("ejo"), m);
            JOLogger.debug(line);
            String ejo = JOTools.encode(line);
            String id = cfg.optString("id");
            String label = cfg.optString("label");
            sb.append(String.format(fmt, id, id, cfg.opt("class"), ejo, label));
            Boolean ret = (Boolean) MVEL.eval(cfg.optString("test"), m);
            if (ret) {
                return sb.toString();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return "";
    }

}
