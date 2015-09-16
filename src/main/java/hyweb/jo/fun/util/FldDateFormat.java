package hyweb.jo.fun.util;

import hyweb.jo.IJOFunction;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author william
 */
public class FldDateFormat implements IJOFunction<String, Object[]> {

    @Override
    public String exec(Object[] args) throws Exception {
        String fmt = (String) args[0];
        Object d =  args[1];
        if (d instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(fmt);
            return sdf.format((Date)d);
        } else {
            return "";
        }
    }

}
