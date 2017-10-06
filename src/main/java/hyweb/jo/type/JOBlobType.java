package hyweb.jo.type;

import hyweb.jo.data.JOFileUtils;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author William
 */
public class JOBlobType extends JOType<byte[]> {

    @Override
    public String dt() {
        return "blob";
    }

    @Override
    public int jdbc() {
        return Types.VARBINARY;
    }

    @Override
    public byte[] loadRS(ResultSet rs, String name) throws SQLException {
        try {
            InputStream is = rs.getBinaryStream(name);
            return JOFileUtils.loadData(is);
        } catch (IOException ex) {
            return null;
        }
    }

    @Override
    public void setPS(PreparedStatement ps, int idx, Object value) throws SQLException {
        if (value instanceof InputStream) {
            // 外部傳入要外部close 
            InputStream is = (InputStream) value;
            ps.setBinaryStream(idx, is);
        } else if (value instanceof String) {
            setPS(ps, idx, (String) value);
        } else if (value instanceof byte[]) {
            setPS(ps, idx, (byte[]) value);
        }  else if (value == null) {
            ps.setNull(idx, jdbc());
        }
    }

    private void setPS(PreparedStatement ps, int idx, String value) throws SQLException {
        if (value != null) {
            try {
                setPS(ps, idx, value.getBytes("UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void setPS(PreparedStatement ps, int idx, byte[] data) throws SQLException {
        ByteArrayInputStream bis = new ByteArrayInputStream(data);
        try {
            ps.setBinaryStream(idx, bis, data.length);
        } finally {
            try {
                bis.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public byte[] check(Object o, byte[] dv) {
        if (o != null) {
            try {
                if(o instanceof byte[]){
                    return (byte[]) o ;
                } else if (o instanceof String){
                    return o.toString().getBytes("UTF-8");
                }
                throw new RuntimeException("Can't support  "+ o.getClass()+"cast to byte[]" );
            } catch (Exception ex) {
                ex.printStackTrace();
                return null;
            }
        }
        return null;
    }

}
