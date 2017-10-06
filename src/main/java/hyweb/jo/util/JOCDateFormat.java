/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import hyweb.jo.log.JOLogger;
import java.text.DecimalFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author william
 */
public class JOCDateFormat {

    public static Date check(Object o) {
        if (o instanceof Date) {
            return (Date) o;
        } else if (o instanceof String) {
            String line = (String) o;
            String[] items = line.split("[\\/|\\-]");
            if (items.length == 3) {
                return check_split_format(items);
            } else if (items.length == 1) {
                return check_num_format(items);
            }
        }
        return null;
    }

    private static int num(String text) {
        try {
            return Integer.parseInt(text);
        } catch (Exception e) {
            return 0; // 不可為0 
        }
    }

    private static Date check_split_format(String[] items) {
        if (items[0].length() != 3 || items[1].length() > 2 || items[2].length() > 2) {
            return null;
        }
        int year = num(items[0]);
        int month = num(items[1]);
        int date = num(items[2]);
        if (year * month * date == 0) {
            return null;
        }
        int num = (year + 1911) * 10000 + month * 100 + date;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        try {
            sdf.setLenient(false);
            return sdf.parse(String.valueOf(num));
        } catch (Exception e) {
            JOLogger.debug("Can't convert cdate : " + Arrays.toString(items));
            return null;
        }

    }

    private static Date check_num_format(String[] items) {
        String line = items[0];
        int num = num(line);
        if (num > 9991231 || num < 10101) {
            return null;
        }
        int d = (num / 10000 + 1911) * 10000 + (num % 10000);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        sdf.setLenient(false);
        try {
            return sdf.parse(String.valueOf(d));
        } catch (Exception e) {
            JOLogger.debug("Can't convert cdate : " + line);
            return null;
        }
    }

    public static String format(Object fv) {
        if (fv instanceof Date) {
            Date d = (Date) fv;
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            int yy = cal.get(Calendar.YEAR) - 1911;
            int mm = cal.get(Calendar.MONTH) + 1;
            int dd = cal.get(Calendar.DATE);
            int v = yy * 10000 + mm * 100 + dd;
            NumberFormat nf = new DecimalFormat("0000000");
            String row = nf.format(v);
            MessageFormat mf = new MessageFormat("{0}/{1}/{2}");
            String[] rows = {row.substring(0, 3),
                row.substring(3, 5),
                row.substring(5)};
            return mf.format(rows);
        } else {
            return (fv != null) ? fv.toString() : "";
        }
    }

    public static void main(String[] args) {
        System.out.println(JOCDateFormat.check("-1/01/01"));
        System.out.println(JOCDateFormat.check("106/2/29"));
        System.out.println(JOCDateFormat.check("105/0/32"));
        System.out.println(JOCDateFormat.check("105/13/32"));
        System.out.println(JOCDateFormat.format(new Date()));
        System.out.println(JOCDateFormat.format("106/02/29"));
    }

}
