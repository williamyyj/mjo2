package hyweb.jo.util;

import hyweb.jo.log.JOLogger;
import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONObject;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 *
 * @author william
 */
public class DateUtil {

    public static Date to_date(String fmt, String text) {
        try {
            return new SimpleDateFormat(fmt).parse(text);
        } catch (ParseException ex) {
            JOLogger.info("Can't parser date : " + text);
        }
        return null;
    }

    public static Date to_date(String text) {
        try {
            String sfmt = "yyyyMMdd";
            String lfmt = "yyyyMMddHHmmss";
            SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
            if (text.contains("CST ")) {
                return sdf.parse(text);
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
        } catch (ParseException ex) {
            JOLogger.info("Can't parser Date : "+text);
        }
        return null;
    }

    public static Date add_date(Date src, int field, int value) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(src);
        cal.add(field, value);
        return cal.getTime();
    }

    public static boolean range(Date ts, Date te, Date curr) {
        if (ts == null || te == null || curr == null) {
            return false;
        }
        long t1 = ts.getTime();
        long t2 = te.getTime();
        long t = curr.getTime();
        return (t1 < t && t < t2);
    }

    public static long diff(Date a, Date b) {
        return (a.getTime() - b.getTime()) / 1000;
    }

    public static long diff(Object b) {
        return diff(new Date(), (Date) b);
    }

    public static boolean op_gte(Date a, Date b) {
        if (a == null || b == null) {
            return false;
        }
        long t1 = a.getTime();
        long t2 = b.getTime();
        return (t1 <= t2);
    }

    public static boolean op_lt(Date a, Date b) {
        if (a == null || b == null) {
            return false;
        }
        long t1 = a.getTime();
        long t2 = b.getTime();
        return (t1 < t2);
    }

    public static boolean op_gt(Date a, Date b) {
        if (a == null || b == null) {
            return false;
        }
        long t1 = a.getTime();
        long t2 = b.getTime();
        return (t1 > t2);
    }

    public static Date yesterday_min() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date yesterday_max() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -1);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static Date tomorrow() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date schedule(JSONObject sch) {
        Calendar cal = Calendar.getInstance();
        Date d = cal.getTime();
        String m = sch.optString("sch");
        if ("m1".equals(m)) {
            int sch1 = sch.optInt("sch1");
            int month = cal.get(Calendar.MONTH);
            System.out.println(month);
            if (sch1 == 1 && (month % 2) != 0) {
                cal.add(Calendar.MONTH, 1);
            }
            if (sch1 == 2 && (month % 2) != 1) {
                cal.add(Calendar.MONTH, 1);
            }
            cal.set(Calendar.DAY_OF_MONTH, sch.optInt("sch2"));
        } else if ("m2".equals(m)) {
            cal.set(Calendar.DAY_OF_MONTH, sch.optInt("sch3"));
        } else if ("m3".equals(m)) {
            int week = cal.get(Calendar.WEEK_OF_YEAR);
            System.out.println(week);
            if ((week % 2) == 0) {
                cal.add(Calendar.WEEK_OF_YEAR, 1);
            }
            cal.set(Calendar.DAY_OF_WEEK, sch.optInt("sch4"));
        } else if ("m4".equals(m)) {
            cal.set(Calendar.DAY_OF_WEEK, sch.optInt("sch5"));
        }
        cal.set(Calendar.HOUR_OF_DAY, sch.optInt("hour"));
        cal.set(Calendar.MINUTE, sch.optInt("min"));
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date date_min(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    public static Date date_max(IJOField fld, JSONObject row) {
        Object o = fld.getFieldValue(row);
        if (o instanceof Date) {
            return date_max((Date) o);
        } else if (o instanceof String) {
            Date d = to_date(fld.ft(), (String) o);
            return date_max(d);
        }
        return null;
    }

    public static Date date_max(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static Date date_next(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE, 1);
        return cal.getTime();
    }

    public static String date_next(String fmt, String text) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat(fmt);
        Date d = sdf.parse(text);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.add(Calendar.DATE, 1);
        return sdf.format(cal.getTime());
    }

    public static int year(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.get(Calendar.YEAR);
    }

    public static int month(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.get(Calendar.MONTH) + 1;
    }

    public static int date(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        return cal.get(Calendar.DATE);
    }

    public static String convert(String fmt, String d) {
        SimpleDateFormat tfmt = new SimpleDateFormat(fmt);
        String sfmt = "yyyyMMddHHmmss";
        try {
            if (d != null && d.length() == 8) {
                sfmt = "yyyyMMdd";
            }
            return tfmt.format(to_date(sfmt, d));
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String convert(String fmt, Date d) {
        if (d instanceof Date) {
            SimpleDateFormat sdf = new SimpleDateFormat(fmt);
            return sdf.format(d);
        }
        return "";
    }

    public static Date lastDayOfMonth(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.SECOND, 59);
        return cal.getTime();
    }

    public static Date firstDayOfMonth(Date d) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date getFirstDayOfSeason(int offset) {
        return getFirstDayOfSeason(new Date(), offset);
    }

    public static Date getFirstDayOfSeason(Date current, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(current);
        cal.add(Calendar.MONTH, offset * 3);
        int month = cal.get(Calendar.MONTH);
        int mm = 0;
        switch (month) {
            case 0:
            case 1:
            case 2:
                mm = 0;
                break;
            case 3:
            case 4:
            case 5:
                mm = 3;
                break;
            case 6:
            case 7:
            case 8:
                mm = 6;
                break;
            case 9:
            case 10:
            case 11:
                mm = 9;
                break;
        }
        cal.set(Calendar.MONTH, mm);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date getFirstDayOfMonth2(int offset) {
        return getFirstDayOfSeason(new Date(), offset);
    }

    public static Date getFirstDayOfMonth2(Date current, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(current);
        cal.add(Calendar.MONTH, offset * 2);
        int month = cal.get(Calendar.MONTH);
        int mm = 0;
        switch (month) {
            case 0:
            case 1:
                mm = 0;
                break;
            case 2:
            case 3:
                mm = 2;
                break;
            case 4:
            case 5:
                mm = 4;
                break;
            case 6:
            case 7:
                mm = 6;
                break;
            case 8:
            case 9:
                mm = 8;
                break;
            case 10:
            case 11:
                mm = 10;
                break;
        }
        cal.set(Calendar.MONTH, mm);
        cal.set(Calendar.DATE, 1);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date nextMonth(Date d, int day) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.MONTH, cal.get(Calendar.MONTH) + 1);
        cal.set(Calendar.DATE, day);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        return cal.getTime();
    }

    public static Date cdate(Object text) {
        try {
            NumberFormat nf = new DecimalFormat("0000000");
            int dv = nf.parse(text.toString()).intValue();
            int year = (dv / 10000 + 1911) * 10000 + (dv % 10000);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
            sdf.setLenient(false);
            return sdf.parse(String.valueOf(year));
        } catch (Exception ex) {
            JOLogger.debug("Can't convet cdate : " + text);
            return null;
        }

    }

    public static Date newInstance(int year, int month, int date) {
        return newInstance(year, month, date, 0, 0, 0);
    }

    public static Date newInstance(int year, int month, int date, int hourOfDay, int minute, int second) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, date, hourOfDay, minute, second);
        return cal.getTime();
    }

    public static Date offset(int fld, Date d, int offset) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(fld, cal.get(fld) + offset);
        return cal.getTime();
    }

    public static Date monthOfDate(Date d, int i) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        cal.set(Calendar.DATE, i);
        return cal.getTime();
    }

    public static void main(String[] args) throws ParseException {
        JSONObject sch = JOTools.loadString("{min:'40',sch3:9,sch2:8,sch1:1,sch:'m5',hour:18,sch5:7,sch4:1}");
        System.out.println(DateUtil.schedule(sch));
        System.out.println(DateUtil.getFirstDayOfSeason(0));
        System.out.println(DateUtil.getFirstDayOfSeason(-1));
        System.out.println(DateUtil.cdate("0990101"));
        Date d = new Date();
        String d_str = d.toString();

    }

}
