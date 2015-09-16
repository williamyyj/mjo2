package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONArray;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;


/**
 * @author william
 */
public class sum implements IJOFunction<Number, Object> {

    private final static ThreadLocal<NumberFormat> nf = new ThreadLocal<NumberFormat>() {
        @Override
        protected NumberFormat initialValue() {
            return NumberFormat.getInstance();
        }
    };

    public Number exec(Object o) throws Exception {
        if (o instanceof Number) {
            return (Number) o;
        } else if (o instanceof String) {
            String text = (String) o;
            return nf.get().parse(text);
        } else if (o instanceof JSONArray) {
            return sum_ja((JSONArray) o);
        } else if (o instanceof List) {
            return sum_list((List) o);
        }
        return 0;
    }

    private Number sum_ja(JSONArray ja) {
        double ret = 0;
        for (int i = 0; i < ja.length(); i++) {
            ret += ja.optDouble(i);
        }
        return ret;
    }

    private Number sum_list(List list) throws ParseException {
        double ret = 0;
        for (Object o : list) {
            if(o instanceof Number){
                ret += ((Number)o).doubleValue();
            } else {
                ret += (nf.get().parse(o.toString())).doubleValue();
            }
        }
        return ret;
    }

}
