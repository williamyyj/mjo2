package hyweb.jo.fun.tml;

import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.MVELTemplate;
import hyweb.jo.util.TextUtils;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author william
 */
public class wp_sendsms implements IJOFunction<Boolean, JOWPObject> {

    @Override
    public Boolean exec(JOWPObject wp) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        JSONObject act = wp.act();
        JSONObject pool = new JSONObject(wp.p());
        String mobile = TextUtils.toNumString(pool.optString("mobile"));
        pool.put("mobile", mobile);
        pool.put("email", mobile);
        pool.put("ctype", "2");
        pool.put("apId", act.optString("apId"));
        JOWPObject wpSMS = new JOWPObject(wp.proc(), "psMailLog", null, pool, null);

        if (pool.optString("mobile").length() >= 10) { // 手機一定是10碼
            pool.put("$now", sdf.format(new Date()));
            JOLogger.debug("sms model : " + pool.toString(4));
            MVELTemplate tmpl = new MVELTemplate(wp.proc().base(), act.optString("$sms_tml"));
            String context = tmpl.processTemplate(pool);
            pool.put("context", context);
            SMSBase sms = new SMSBase(wp.proc().base(), act.optString("$sms_cfg"));
            String urlString = sms.to_url(pool);
            JOLogger.debug("send sms : \r\n" + urlString);
            try {
                String ret = sms.send(pool);
                pool.put("context", urlString);
                pool.put("status", "1");
                JOFunctional.exec("dao.wp_add", wpSMS);
                JOLogger.info(pool.optString("newId") + "發送成功:::" + ret);
            } catch (Exception e) {
                pool.put("context", urlString);
                pool.put("status", "2");  // 發送失敗
                JOFunctional.exec("dao.wp_add", wpSMS);
                JOLogger.info(pool.optString("newId") + "發送失敗" + ":::" + e.getMessage());
            }
        }
        return true;
    }

}
