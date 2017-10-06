package hyweb.jo.ht;

import hyweb.jo.JOProcObject;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import java.util.List;

/**
 * using : {id:xxx table:xxxx column: }
 *
 * @author william
 */
public class HTAutoComplete extends HTCell {

    private String qfmt = "select distinct %s from %s";

    public HTAutoComplete(String line) {
        super(line);
    }

    @Override
    public String render(JOProcObject proc) {
        html = new StringBuilder();
        try {
            String table = cfg.optString("table");
            String column = cfg.optString("column");
            String query = String.format(qfmt, column, table);
            JOLogger.debug(query);
            List<JSONObject> rows = proc.db().rows(query);
            JSONArray data = new JSONArray();
            for (JSONObject row : rows) {
                data.put(row.optString(column));
            }
            proc.set(JOProcObject.p_request, cfg.optString("id"), rows);
            proc.set(JOProcObject.p_request, cfg.optString("id") + "_data", data.toString());
            return data.toString();
        } catch (Exception ex) {
            JOLogger.error(ex);
            html.append("查無資料");
        }

        return html.toString();
    }

    @Override
    public String display(Object p, Object dv) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
