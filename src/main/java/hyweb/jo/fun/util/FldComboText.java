package hyweb.jo.fun.util;

import hyweb.jo.IJOFunction;
import hyweb.jo.fun.model.FMLoadCombo;
import hyweb.jo.org.json.JSONObject;
import java.util.List;

/**
 *
 * @author william
 */
public class FldComboText implements IJOFunction<String, Object[]> {

    private final FMLoadCombo f_loadCombo = new FMLoadCombo();

    @Override
    public String exec(Object[] args) throws Exception {
        String urn = (String) args[0];
        String o = (String) args[1];
        List<JSONObject> items = f_loadCombo.exec(urn);
        if (items != null) {
            for (JSONObject item : items) {
                if (item.opt("value").equals(o)) {
                    return item.optString("label");
                }
            }
        }
        return "";
    }
}
