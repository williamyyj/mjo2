package hyweb.jo.convert;

import hyweb.jo.log.JOLogger;

/**
 * @author William
 */
public class FConvertInt extends FConvert<Integer,Object,Integer> {

    @Override
    public Integer exec(Object o,Integer dv ) throws Exception {
        try {
            if (o instanceof Number) {
                return ((Number) o).intValue();
            } else if (o instanceof String) {
                String str = ((String) o).trim();
                return str.length() > 0 ? Integer.parseInt(str) : null;
            }
        } catch (Exception e) {
            //log.warn("Can't check value : " + o);
            JOLogger.warn("Can't convert " + o + " to int.");
        }
        return  dv;
    }

}
