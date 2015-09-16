
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.org.json.zip.BitOutputStream;
import hyweb.jo.org.json.zip.Zipper;
import hyweb.jo.util.JOTools;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

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
        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        Zipper zip = new Zipper(new BitOutputStream(bao));
        try{
            JSONObject jo  = JOTools.loadString("{ $url:'https://pest.baphiq.gov.tw', x:xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx }");
            zip.encode(jo);
            System.out.println(bao.toString());
        } finally{
            bao.close();
        }
    }
}
