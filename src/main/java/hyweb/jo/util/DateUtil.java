/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import hyweb.jo.log.JOLogger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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

    public static Date add_date(Date src, int field, int value) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(src);
        cal.add(field, value);
        return cal.getTime();
    }

}
