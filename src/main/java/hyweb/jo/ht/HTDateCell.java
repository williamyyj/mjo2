package hyweb.jo.ht;

import hyweb.jo.JOProcObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 *
 * @author william
 */
public class HTDateCell extends HTCell {

    private final static String d_cst_fmt = "EEE MMM dd HH:mm:ss zzz yyyy";
    private final static String d_long_fmt = "yyyyMMddHHmmss";
    private final static String d_short_fmt = "yyyyMMdd";

    private SimpleDateFormat sdf;

    public HTDateCell(String line) {
        super(line);
    }

    @Override
    public String render(JOProcObject proc) {
        // 非視覺化物件
        sdf = new SimpleDateFormat(cfg.optString("fmt"));
        if (proc != null) {
            proc.set(JOProcObject.p_request, cfg.optString("id"), this);
        }

        return "";
    }

    @Override
    public String display(Object p) {
        Date d = toDate(p);
        if (p != null) {
            return sdf.format(d);
        }
        return "";
    }

    @Override
    public String display(Object p, Object dv) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Date toDate(Object o) {
        try {
            if (o instanceof Date) {
                return (Date) o;
            }
            if (o instanceof String) {
                String text = ((String) o).trim();
                int len = text.length();
                if (len == d_cst_fmt.length()) {
                    return new SimpleDateFormat(d_cst_fmt, Locale.US).parse(text);
                }
                text = dateTrim(text);
                len = text.length();
                if (len == d_long_fmt.length()) {
                    return new SimpleDateFormat(d_long_fmt).parse(text);
                } else if (len == d_short_fmt.length()) {
                    return new SimpleDateFormat(d_short_fmt).parse(text);
                }
            }
            return null;
        } catch (Exception e) {
            System.err.println("Can't convert date : " + o);
            return null;
        }
    }

    public String dateTrim(String text) {
        // 移除 非數字
        return Pattern.compile("[^0-9]").matcher(text).replaceAll("");
    }

}
