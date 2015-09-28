
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.org.json.zip.BitOutputStream;
import hyweb.jo.org.json.zip.Zipper;
import hyweb.jo.util.JOTools;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author William
 */
public class MyTest {
    public static void main(String[] args) throws IOException{
      Properties prop = System.getProperties();
	  Set<Map.Entry<Object,Object>>  entry = prop.entrySet();
	  for(Map.Entry e : entry){
		  System.out.println(e.getKey()+"---->"+e.getValue());
	  }
    }
}
