package hyweb.jo.org.json;

import java.util.Collection;
import java.util.LinkedHashSet;

/**
 *
 * @author william
 */
public class JOSerial {

    private LinkedHashSet data;

    public JOSerial() {
        data = new LinkedHashSet();
    }

    public JOSerial add(Object v) {
        if (v instanceof String) {
            return addString((String) v);
        } else if (v instanceof Collection) {
            return addCollection((Collection) v);
        } else if (v!=null){
            data.add(v);
        }
        return this;
    }

    public boolean remove(Object item) {
        return data.remove(item);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Object item : data) {
            sb.append(item).append(",");
        }
        if (data.size() > 0) {
            sb.setLength(sb.length() - 1);
        }
        return sb.toString();
    }

    private JOSerial addString(String text) {
        if(text!=null){
            String[] items = text.split(",");
            for(String item:items){
                if(item.trim().length()>0){
                    data.add(item.trim());
                }
            }
        }
        return this;
    }
    
    public boolean has(Object v){
        return data.contains(v);
    }

    private JOSerial addCollection(Collection arr) {
        if(arr!=null){
            for(Object o : arr){
                if(o!=null){
                    add(o);
                }
            }
        }
        return this ;
    }

}
