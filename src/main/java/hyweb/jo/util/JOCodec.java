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

    }

}
