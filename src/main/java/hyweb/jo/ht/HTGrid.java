package hyweb.jo.ht;

import hyweb.jo.JOProcObject;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOPackages;
import hyweb.jo.util.JOTools;
import java.io.IOException;
import java.util.List;

/**
 *    using   {id:rows, meta:barcode_t , act:query}
 * @author william
 */
public class HTGrid extends HTCell {

    public HTGrid(String line) {
        super(line);
    }

    @Override
    public String render(JOProcObject proc) {
       html = new StringBuilder();
        try {
            JOLogger.debug(cfg);
            JOWPObject wp = new JOWPObject(proc, cfg.optString("meta"), cfg.optString("act"));
            List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec(cfg.optString("fun", "wp_page"), wp);
            html.append("<table id=\"").append(cfg.optString("id")).append("\">\r\n");
            if (rows != null && rows.size() > 0) {
                render_grid(html, wp, rows);
            }
            html.append("</table>\r\n");
            proc.set(JOProcObject.p_request, cfg.optString("id"), rows);
            proc.set(JOProcObject.p_request, "grid_" + cfg.optString("id"), html.toString());
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

    private void render_grid(StringBuilder sb, JOWPObject wp, List<JSONObject> rows) {
         List<IJOField> grid = null;
        if(wp.act().has("$grid")){
            grid = wp.metadata().getFields(wp.act().optString("$grid"));
        } else {
            grid = wp.mdFields();
        }
        render_grid_head(wp.proc(), sb, grid);
        for (JSONObject row : rows) {
            render_grid_body_item(sb, grid, row);
        }

    }

    private void render_grid_head(JOProcObject proc, StringBuilder sb, List<IJOField> flds) {
        sb.append("<tr>");
        for (IJOField fld : flds) {
            if (!"table".equals(fld.dt())) {
                sb.append("\r\n<th>");
                sb.append(fld.name());
                sb.append("</th>");
                init_ht_cell(proc, fld);
            }
        }
        sb.append("\r\n</tr>\r\n");
    }

    private void render_grid_body_item(StringBuilder sb, List<IJOField> flds, JSONObject row) {
        sb.append("<tr>");
        for (IJOField fld : flds) {
            if (!"table".equals(fld.dt())) {
                sb.append("\r\n<td>");
                if (fld.cfg().has("ht")) {
                    IHTCell cell = (IHTCell) fld.cfg().opt("cell");
                    sb.append("\r\n").append(cell.display(row)).append("\r\n");
                } else {
                    sb.append(fld.getFieldText(row));
                }
                sb.append("</td>");
            }
        }
        sb.append("\r\n</tr>\r\n");
    }

    private void init_ht_cell(JOProcObject proc, IJOField fld) {
        if (fld.cfg().has("ht")) {
            try {
                JSONObject cfg = JOTools.loadJSON(fld.cfg().optString("ht"));
                IHTCell combo = (IHTCell) JOPackages.newInstance("ht.combo", cfg);
                combo.render(proc);
                fld.cfg().put("cell", combo);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

}
