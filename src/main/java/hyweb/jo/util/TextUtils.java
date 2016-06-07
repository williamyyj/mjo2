/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;

import hyweb.jo.model.IJOField;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.org.json.JSONTokener;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author William
 */
public class TextUtils {

    private static final Pattern p = Pattern.compile("\\$\\{([^\\}]+)\\}");
    //           (\S+)=["']?((?:.(?!["']?\s+(?:\S+)=|[>"']))+.)["']?
    private static final Pattern p_attr = Pattern.compile("([\\w\\:\\-]+)(?:\\s*=\\s*(?:(?:\"((?:\\\\.|[^\"])*)\")|(?:'((?:\\\\.|[^'])*)')|([^>\\s]+)))?");
    public final static String d_cst_fmt = "EEE MMM dd HH:mm:ss zzz yyyy";
    public final static String d_long_fmt = "yyyyMMddHHmmss";
    public final static String d_short_fmt = "yyyyMMdd";

    public static void mask(JSONObject jo, String name, int ps, int pe) {
        String ret = jo.optString(name, "");
        char[] buf = ret.toCharArray();
        for (int i = ps; i < pe; i++) {
            if (buf.length > i) {
                buf[i] = '*';
            }
        }
        jo.put(name, new String(buf));
    }

    public static String mask_tail(String line, char mask) {
        StringBuilder sb = new StringBuilder();
        if (line != null && line.length() > 0) {
            sb.append(line.charAt(0));
            for (int i = 1; i < line.length(); i++) {
                sb.append(mask);
            }
        }
        return sb.toString();
    }

    public static void cast_date(JSONObject jo, String name) {
        try {
            jo.put(name, toDate(jo.opt(name)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void cast_date(JSONObject jo, String name, String fmt, int offset) {
        Object v = jo.opt(name);
        Object ret = toDate(v);
        if (ret == null && v instanceof String) {
            try {
                ret = new SimpleDateFormat(fmt).parse((String) v);
                Calendar cal = Calendar.getInstance();
                cal.setTime((Date) ret);
                cal.add(Calendar.DATE, offset);
                ret = cal.getTime();
            } catch (ParseException ex) {
                ret = v;
            }
        }
        jo.put(name, ret);
    }

    public static void cast_qin(JSONObject jo, String name, String dt) {
        JSONArray ja = jo.optJSONArray(name);
        StringBuilder sb = new StringBuilder();
        if (ja != null) {
            for (int i = 0; i < ja.length(); i++) {
                if ("string".equals(dt)) {
                    sb.append('\'').append(ja.opt(i)).append('\'');
                } else {
                    sb.append(ja.opt(i));
                }
                sb.append(',');
            }
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);
            }
            System.out.println(sb);
            jo.put(name, sb.toString());
        }
    }

    public static Object toDate(Object o) {
        SimpleDateFormat sdf = null;
        try {
            if (o instanceof Date) {
                return o;
            } else if (o instanceof String) {
                String text = ((String) o).trim();
                int len = text.length();
                if (len == d_cst_fmt.length()) {
                    return new SimpleDateFormat(d_cst_fmt, Locale.US).parse(text);
                } else if (len == d_long_fmt.length()) {
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

    public static JSONArray ja(String text) {
        return new JSONArray(new JSONTokener(new StringReader(text)));
    }

    public static List<Object> toList(Object o) {
        if (o instanceof String) {
            try {
                String text = (String) o;
                JSONArray ja = new JSONArray(new JSONTokener(new StringReader(text)));
                return ja.m();
            } catch (Exception e) {
                List ret = new ArrayList();
                ret.add(o);
                return ret;
            }
        } else if (o instanceof JSONArray) {
            return ((JSONArray) o).m();
        } else if (o instanceof List) {
            return (List) o;
        } else if (o instanceof Collection) {
            return new ArrayList((Collection) o);
        }
        return null;
    }

    public static String to_value(JSONObject jo, String fid) {
        StringBuffer sb = new StringBuffer();
        Matcher m = p.matcher(fid);
        while (m.find()) {
            String re = jo.optString(m.group(1));
            re = (re == null) ? "" : re;
            m.appendReplacement(sb, re);
        }
        m.appendTail(sb);
        return sb.toString();
    }

    public static void parser_attr(JSONObject jo, String text) throws Exception {
        Matcher m = p_attr.matcher(text);
        while (m.find()) {
            String name = m.group(1);
            String value = m.group(4);
            jo.put(name, value);
        }
    }

    public static String hash(String Algorithm, String Source_Str) {
        String hashdata = null;
        try {
            MessageDigest hashalgorithm = MessageDigest.getInstance(Algorithm);
            hashalgorithm.update(Source_Str.getBytes());
            byte[] digest = hashalgorithm.digest();
            hashdata = toHex(digest);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashdata;
    }

    private static String toHex(byte[] digest) {
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < digest.length; ++i) {
            byte b = digest[i];
            int value = (b & 0x7F) + (b < 0 ? 128 : 0);
            buffer.append(value < 16 ? "0" : "");
            buffer.append(Integer.toHexString(value));
        }
        return buffer.toString();
    }

    public static String getRandomPassword() {
        int z;
        StringBuilder sb = new StringBuilder();
        int i;
        for (i = 0; i < 8; i++) {
            z = (int) ((Math.random() * 7) % 3);
            switch (z) {
                case 1:
                    // 放數字
                    sb.append((int) ((Math.random() * 10) + 48));
                    break;
                case 2:
                    // 放大寫英文
                    sb.append((char) (((Math.random() * 26) + 65)));
                    break;
                default:
                    // 放小寫英文
                    sb.append(((char) ((Math.random() * 26) + 97)));
                    break;
            }
        }
        return sb.toString();
    }

    public static String md5(byte[] buf) {
        String result = "";
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(buf);
            result = toHex(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static boolean isDigit(String s) {
        char[] cbuf = s.toCharArray();
        for (char c : cbuf) {
            if (c < '0' || c > '9') {
                return false;
            }
        }
        return true;
    }

    public static String fixed(String line, int size) {
        if (line.length() > size) {
            return line;
        } else {
            StringBuilder sb = new StringBuilder(size);
            int offset = size - line.length();
            for (int i = 0; i < size; i++) {
                if (i < offset) {
                    sb.append('0');
                } else {
                    sb.append(line.charAt(i - offset));
                }
            }
            return sb.toString();
        }
    }

    public final static String now(String fmt) {
        return new SimpleDateFormat(fmt).format(new Date());
    }

    public final static String next(int field, int value, String fmt) {
        Calendar cal = Calendar.getInstance();
        cal.add(field, value);
        return new SimpleDateFormat(fmt).format(cal.getTime());
    }

    public final static String ja2string(JSONArray ja, String head) {
        StringBuilder sb = new StringBuilder();
        if (ja != null) {
            sb.append(ja.opt(0));
            for (int i = 1; i < ja.length(); i++) {
                sb.append(head).append(ja.opt(i));
            }
        }
        return sb.toString();
    }

    public static int length(Object fv) {
        return (fv != null) ? fv.toString().trim().length() : 0;
    }

    public static void fixStringSize(IJOField fld, JSONObject row) throws UnsupportedEncodingException {
        if (fld.size() > 0) {
            Object v = fld.getFieldValue(row);
            if (v != null) {
                String text = v.toString();
                int size = fld.size();

                if (fld.cfg().optBoolean("char")) {
                    byte[] buf = text.getBytes("UTF-8");
                    int len = buf.length;
                    len = (size > len) ? len : size;
                    text = new String(buf,0,len,"UTF-8");
                } else {
                    int len = text.length();
                    len = (size > len) ? len : size;
                    text = text.substring(0, len);
                }
                row.put(fld.getFieldName(), text);
            }

        }
    }

}
