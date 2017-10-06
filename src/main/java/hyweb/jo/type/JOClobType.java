package hyweb.jo.type;


import java.sql.Types;

/**
 *
 * @author William
 */
public class JOClobType extends JOStringType {

    @Override
    public String dt() {
        return "clob";
    }

    @Override
    public int jdbc() {
        return Types.CLOB;
     }


}
