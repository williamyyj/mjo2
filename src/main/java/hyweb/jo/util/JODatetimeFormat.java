package hyweb.jo.util;

import hyweb.jo.type.JODateType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class JODatetimeFormat {

    private static final long serialVersionUID = 1;

    private String pattern;
    private String usedPattern;
    private SimpleDateFormat sdf;
    private int e_count = 0;
    protected List<String> patternSegment = new ArrayList<String>();

    public JODatetimeFormat(String pattern) {
        this.pattern = pattern;
        usedPattern = pattern.replace("eee", "yyy");
        sdf = new SimpleDateFormat(usedPattern);
        sdf.setLenient(false);
        e_count = (usedPattern.equals(pattern)) ? 0 : 3;
    }

    public Date parse(Object o) throws ParseException {
        if (o instanceof Date) {
            return (Date) o;
        }
        if (o == null) {
            return null;
        }
        String source = (String) o;
        if (e_count > 0) {
            StringBuilder sb = new StringBuilder();
            char[] cbuf = source.toCharArray();
            int year = 0;
            int idx = e_count;
            for (char c : cbuf) {
                if (this.isDigit(c) && idx > 0) {
                    sb.append(c);
                    year = year * 10 + (c - 48);
                    idx--;
                }
                if (idx <= 0) {
                    break;
                }
            }
            year = year + 1911;
            source = source.replace(sb.toString(), String.valueOf(year));
        }
        return sdf.parse(source);
    }

    public String format(Object o) {
        if (o instanceof Date) {
            if (e_count > 0) {
                Date d = (Date) o;
                Calendar cal = Calendar.getInstance();
                cal.setTime(d);
                int year = cal.get(Calendar.YEAR);
                int cyear = year - 1911;
                if (cyear > 0) {
                    String fmt = sdf.format(d);
                    return fmt.replace(String.valueOf(year), String.valueOf(cyear));
                }
                return sdf.format(d);
            } else {
                Date d = (Date) o;
                return sdf.format(d);
            }
        }
        // 非日期期式
        return (o != null) ? o.toString() : "";
    }

    private boolean isDigit(char c) {
        return c >= '0' && c <= '9';
    }

    public static void main(String[] args) {
        try {
            JODatetimeFormat df = new JODatetimeFormat("eeeMMdd");
            JODateType dt = new JODateType();

            // System.out.println(d);
            System.out.println(df.format("105010112"));

            df = new JODatetimeFormat("yyyyMMddHHmmss");
            System.out.println(df.parse("20160830092557"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
