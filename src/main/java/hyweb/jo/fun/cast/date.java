package hyweb.jo.fun.cast;

import hyweb.jo.IJOFunction;
import static hyweb.jo.util.DateUtil.cdate;
import static hyweb.jo.util.DateUtil.to_date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Locale;

/**
 *
 * @author william
 */
public class date implements IJOFunction<Date, Object> {

    @Override
    public Date exec(Object o) throws Exception {
        if (o instanceof Date) {
            return (Date) o;
        } else if (o instanceof String) {
            String text = ((String) o).trim();
            String sfmt = "yyyyMMdd";
            String lfmt = "yyyyMMddHHmmss";

            if (text.contains("CST")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                try {                  
                    return sdf.parse(text);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }

            if (text.contains("GMT")) {
                SimpleDateFormat sdf = new SimpleDateFormat("EEE','ddMMMyyyyHH:mm:ss'GMT'", Locale.US);
                try {
                    return sdf.parse(text.replaceAll("\\s", ""));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }

            String str = text.replaceAll("[^0-9\\.]+", "");
            int len = str.length();
            switch (len) {
                case 7:
                    return cdate(str);
                case 8:
                    return to_date(sfmt, str);
                case 14:
                    return to_date(lfmt, str);
            }
        }
        return null;

    }

}
