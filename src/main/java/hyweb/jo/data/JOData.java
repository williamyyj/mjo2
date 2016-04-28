package hyweb.jo.data;

import hyweb.jo.org.json.JSONObject;
import hyweb.jo.org.json.XML;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public abstract class JOData {

    public final static int buf_size = 8192;

    public byte[] loadData(InputStream is) throws Exception {
        BufferedInputStream bis = null;
        byte[] data = null;
        byte[] tmp = new byte[buf_size];
        int num = 0;
        try {
            bis = new BufferedInputStream(is);
            while ((num = bis.read(tmp)) > 0) {
                if (data == null) {
                    data = new byte[num];
                    System.arraycopy(tmp, 0, data, 0, num);
                } else {
                    byte[] old = data;
                    data = new byte[old.length + num];
                    System.arraycopy(old, 0, data, 0, old.length);
                    System.arraycopy(tmp, 0, data, old.length, num);
                }
            }
        } finally {
            bis.close();
        }
        return data;
    }

    public String loadString(File f, String enc) throws Exception {
        byte[] buf = loadData(new FileInputStream(f));
        return (buf != null) ? new String(buf, enc) : null;
    }
    
      public List<String> loadList(File f, String enc) throws Exception {
          List<String> ret = new ArrayList<String>();
          BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(f),enc));
          try{
              String line = null ; 
              while((line=br.readLine())!=null){
                  ret.add(line);
              }
          } finally{
              br.close();
          }
          return ret ; 
    }

    public void saveText(String text, File f, String enc) throws Exception {
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(new FileOutputStream(f), enc);
            osw.write(text);
            osw.flush();
        } finally {
            if (osw != null) {
                osw.close();
            }
        }
    }

    public void saveJSON(JSONObject jo, File f) throws Exception {
        saveText(jo.toString(2), f, "UTF-8");
    }

    public void saveData(byte[] data, OutputStream os) throws Exception {
        try {
            if (data != null && data.length > 0) {
                os.write(data);
                os.flush();
            }
        } finally {
            if (os != null) {
                os.close();
            }
        }
    }

    public String toString(byte[] data, String enc) throws Exception {
        // FIX UTF-8 BOM 
        //System.out.println( data[0]+":" +data[1] + ":" +data[2]);
        if (data[0] == -17 && data[1] == -69 && data[2] == -65) {
            //if(data[0]==0xEF && data[1]==0xBB && data[2]==0xBF){
            byte[] old = data;
            data = new byte[data.length - 3];
            System.arraycopy(old, 3, data, 0, data.length);
            old = null; // gc
        }
        return new String(data, enc);
    }

    public String toString(byte[] data) {
        return new String(data);
    }

    public JSONObject xml(File f, String enc) throws Exception {
        String text = loadString(f, enc);
        return XML.toJSONObject(text);
    }

    public JSONObject xml(String text) throws Exception {
        return XML.toJSONObject(text);
    }

    public JSONObject jo(File f, String enc) throws Exception {
        String text = loadString(f, enc);
        return new JSONObject(text);
    }

    public abstract byte[] loadData(String path) throws Exception;

    public abstract void close() throws Exception;
}
