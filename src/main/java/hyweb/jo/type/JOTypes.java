/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.type;

import java.sql.Types;
import java.util.Date;
import java.util.HashMap;
import hyweb.jo.IJOInit;
import hyweb.jo.IJOType;



/**
 *
 * @author william
 */
public class JOTypes extends HashMap<Object, IJOType<?>> {
    
    public final static String pkg = "hyweb.jo.type";
    protected IJOType<Object> var_type = new JOVarType();
    protected IJOType<Integer> int_type = new JOIntType();
    protected IJOType<Long> long_type = new JOLongType();
    protected IJOType<Double> double_type = new JODoubleType();
    protected IJOType<Date> date_type = new JODateType();
    protected IJOType<String> string_type = new JOStringType();
    protected IJOType<byte[]> blob_type = new JOBlobType();
    protected IJOType<String> clob_type = new JOClobType();

    public JOTypes(){
        init_commons();
    }
    
    public JOTypes(String database) {
        this();
        try {
            IJOInit init = (IJOInit) Class.forName(pkg+"."+database+"_init").newInstance();
            init.__init__(this);
        } catch (Exception ex) {
            System.out.println("Can't find org.cc.type."+database+"_init");
        }
    }

    private void init_commons() {
        put(IJOType.dt_int, int_type);
        put(IJOType.dt_long, long_type);
        put(IJOType.dt_double, double_type);
        put(IJOType.dt_string, string_type);
        put(IJOType.dt_date, date_type);
        // jdbc
        put(Types.INTEGER, int_type);
        put(Types.DECIMAL, long_type);
        put(Types.BIGINT, long_type);
        put(Types.DOUBLE, double_type);
        put(Types.NUMERIC, double_type);
        put(Types.VARCHAR, string_type);
        put(Types.CHAR, string_type);
    }


    
    public IJOType<?> type(Object dt){
        IJOType<?> type = get(dt);
        return (type!=null) ? type : var_type;
    }
    
    public IJOType<?> type(int dt){
        IJOType<?> type = get(dt);
        return (type!=null) ? type : new JOVarType(dt);
    }
   
    public IJOType<?> var_type(){
        return var_type;
    }
    
   
    
    
}
