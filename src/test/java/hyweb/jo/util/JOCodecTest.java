package hyweb.jo.util;

import org.junit.Test;

/**
 * @author william
 */
public class JOCodecTest {

    @Test
    public void case_01() throws Exception {
        String inp = "%22onmouseover%3D%22%5Bwindow%5B%27location%27%5D%3D%27%5Cx6a%5Cx61%5Cx76%5Cx61%5Cx73%5Cx63%5Cx72%5Cx69%5Cx70%5Cx74%5Cx3a%5Cx61%5Cx6c%5Cx65%5Cx72%5Cx74%5Cx28/acer/%5Cx29%27%5D%22";
        String ret = JOCodec.decodeFromURL(inp);
        System.out.println(ret);
        ret = JOCodec.encodeForHTMLAttribute(ret);
        System.out.println(ret);
        ret = JOCodec.encodeForJavaScript(ret);
        System.out.println(ret);

        ret = JOCodec.encodeForJavaScript("這是一個測驗");
        System.out.println(ret);
        System.out.println(JOCodec.encodeForHTML("\""));
        String uri = "/mPosCService/__data__/2016/20161214000000pestCropRelation.zip";
        System.out.println(uri.replace("/mPosCService/__data__/", "D:\\ftproot"));
        
       System.out.println( Base64.b64_encode("4438-DC02".getBytes("UTF-8")));

  
    }

}
