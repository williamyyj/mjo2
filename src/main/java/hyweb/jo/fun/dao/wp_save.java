package hyweb.jo.fun.dao;

import hyweb.jo.IJOFunction;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;

/**
 *
 * @author william
 */
public class wp_save extends WPDao implements IJOFunction<Long, JOWPObject> {

    @Override
    public Long exec(JOWPObject wp) throws Exception {
        JSONObject row = (JSONObject) JOFunctional.exec("dao.wp_row_pk", wp);
        if (row != null && row.length() > 0) {
            return (Long) JOFunctional.exec("dao.wp_edit", wp);
        } else {
            return (Long) JOFunctional.exec("dao.wp_add", wp);
        }
    }

}
