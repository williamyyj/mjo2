package hyweb.jo.ff;

import hyweb.jo.JOProcObject;
import hyweb.jo.ff.JOFFBase;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import java.text.SimpleDateFormat;

/**
 *
 * @author william
 */
public class fmt_date extends JOFFBase<String> {

    private SimpleDateFormat sdf;

    @Override
    public void __init_proc(JOProcObject proc) {
        sdf = new SimpleDateFormat(cfg.optString("$pattern"));
    }

    @Override
    protected String apply(JSONObject row, String ffId, Object dv) {
        return cast(dv);
    }

    @Override
    public String cast(Object fv) {
        try {
            return sdf.format(JOFunctional.exec("cast.date", fv));
        } catch (Exception ex) {
            JOLogger.warn("Can't cast date " + fv);
            return "";
        }
    }

}
