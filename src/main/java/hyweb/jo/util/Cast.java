package hyweb.jo.util;

public class Cast {

    public static int asInt(Object o, int dv) {
        if (o instanceof Number) {
            return ((Number) o).intValue();
        } else if (o instanceof String) {
            try {
                String text = (String) o;
                return Integer.parseInt(text.trim());
            } catch (Exception e) {
                return dv;
            }
        }
        return dv;
    }

    public static int asInt(Object[] params, int idx, int dv) {
        Object o = (params != null && params.length > idx) ? params[idx] : null;

        return asInt(o, dv);
    }

    public static String asString(Object o, String dv) {
        if (o instanceof String) {
            return ((String) o).trim();
        } else if (o != null) {
            return o.toString();
        }
        return dv;
    }

    public static String asString(Object[] params, int idx, String dv) {
        Object o = (params != null && params.length > idx) ? params[idx] : null;
        return asString(o, dv);
    }

}
