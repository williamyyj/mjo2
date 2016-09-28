package hyweb.jo.util;

import hyweb.jo.org.owasp.esapi.codecs.CSSCodec;
import hyweb.jo.org.owasp.esapi.codecs.HTMLEntityCodec;
import hyweb.jo.org.owasp.esapi.codecs.JavaScriptCodec;
import hyweb.jo.org.owasp.esapi.codecs.PercentCodec;
import hyweb.jo.org.owasp.esapi.codecs.XMLEntityCodec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Pattern;

public class JOCodec {

    private static HTMLEntityCodec html = new HTMLEntityCodec();
    private static XMLEntityCodec xml = new XMLEntityCodec();
    private static PercentCodec percent = new PercentCodec();
    private static JavaScriptCodec js = new JavaScriptCodec();
    private static CSSCodec css = new CSSCodec();
    private final static char[] IMMUNE_HTML = {',', '.', '-', '_', ' '};
    private final static char[] IMMUNE_HTMLATTR = {',', '.', '-', '_'};
    private final static char[] IMMUNE_CSS = {};
    private final static char[] IMMUNE_JAVASCRIPT = {',', '.', '_'};
    private final static char[] IMMUNE_XML = {',', '.', '-', '_', ' '};
    private final static char[] IMMUNE_XMLATTR = {',', '.', '-', '_'};
    private final static char[] IMMUNE_XPATH = {',', '.', '-', '_', ' '};
    private final static String enc = "UTF-8";

    //------------------------------------------------------------------------
    public static String encodeForHTML(String input) {
        if (input == null) {
            return null;
        }
        return html.encode(IMMUNE_HTML, input);
    }

    public static String decodeForHTML(String input) {

        if (input == null) {
            return null;
        }
        return html.decode(input);
    }

    public static String encodeForHTMLAttribute(String input) {
        if (input == null) {
            return null;
        }
        return html.encode(IMMUNE_HTMLATTR, input);
    }

    public static String encodeForCSS(String input) {
        if (input == null) {
            return null;
        }
        return css.encode(IMMUNE_CSS, input);
    }

    public static String encodeForJavaScript(String input) {
        if (input == null) {
            return null;
        }
        return js.encode(IMMUNE_JAVASCRIPT, input);
    }

    public static String decodeFormJavaScript(String input) throws Exception {
        if (input == null) {
            return null;
        }
        return js.decode(input);
    }

    public static String encodeForXPath(String input) {
        if (input == null) {
            return null;
        }
        return html.encode(IMMUNE_XPATH, input);
    }

    public static String encodeForXML(String input) {
        if (input == null) {
            return null;
        }
        return xml.encode(IMMUNE_XML, input);
    }

    public static String encodeForXMLAttribute(String input) {
        if (input == null) {
            return null;
        }
        return xml.encode(IMMUNE_XMLATTR, input);
    }

    public static String encodeForURL(String input) throws Exception {
        if (input == null) {
            return null;
        }
        return URLEncoder.encode(input, enc);
    }

    public static String decodeFromURL(String input) throws Exception {
        if (input == null) {
            return null;
        }
        return URLDecoder.decode(input, enc);
    }

    public static void main(String[] args) throws Exception {
        Pattern p = Pattern.compile("<[^>]*>");
        String test = "請輸入課程--><sCrIpT>alert(66244)</sCrIpT>";
        String ret = test.replaceAll("<[^>]*>", "");
        System.out.println(ret);

        String attr = "%22onmouseover%3D%22%5Bwindow%5B%27location%27%5D%3D%27%5Cx6a%5Cx61%5Cx76%5Cx61%5Cx73%5Cx63%5Cx72%5Cx69%5Cx70%5Cx74%5Cx3a%5Cx61%5Cx6c%5Cx65%5Cx72%5Cx74%5Cx28/acer/%5Cx29%27%5D%22";
        ret = JOCodec.encodeForHTMLAttribute(attr);
        System.out.println(ret);

    }

}
