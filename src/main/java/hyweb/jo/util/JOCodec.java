package hyweb.jo.util;

import hyweb.jo.fun.util.JODecoder;
import hyweb.jo.fun.util.JOEncoder;
import hyweb.jo.IJOFunction;
import hyweb.jo.log.JOLogger;
import hyweb.jo.org.apache.log4j.Logger;
import hyweb.jo.org.owasp.esapi.codecs.CSSCodec;
import hyweb.jo.org.owasp.esapi.codecs.HTMLEntityCodec;
import hyweb.jo.org.owasp.esapi.codecs.JavaScriptCodec;
import hyweb.jo.org.owasp.esapi.codecs.PercentCodec;
import hyweb.jo.org.owasp.esapi.codecs.XMLEntityCodec;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.regex.Pattern;

public class JOCodec {

    private static final Logger logger = JOLogger.getLogger(JOCodec.class);
    private HTMLEntityCodec html;
    private XMLEntityCodec xml;
    private PercentCodec percent;
    private JavaScriptCodec js;
    private CSSCodec css;
    private IJOFunction<String, String> joencoder;
    private IJOFunction<String, String> jodecoder;
    private final static char[] IMMUNE_HTML = {',', '.', '-', '_', ' '};
    private final static char[] IMMUNE_HTMLATTR = {',', '.', '-', '_'};
    private final static char[] IMMUNE_CSS = {};
    private final static char[] IMMUNE_JAVASCRIPT = {',', '.', '_'};
    private final static char[] IMMUNE_XML = {',', '.', '-', '_', ' '};
    private final static char[] IMMUNE_XMLATTR = {',', '.', '-', '_'};
    private final static char[] IMMUNE_XPATH = {',', '.', '-', '_', ' '};
    private final String enc;

    public JOCodec(String enc) {
        this.enc = enc;
        html = new HTMLEntityCodec();
        xml = new XMLEntityCodec();
        percent = new PercentCodec();
        js = new JavaScriptCodec();
        css = new CSSCodec();
        joencoder = new JOEncoder(enc);
        jodecoder = new JODecoder(enc);
    }

    public JOCodec() {
        this("UTF-8");
    }

    //------------------------------------------------------------------------
    public String encodeForHTML(String input) {
        if (input == null) {
            return null;
        }
        return html.encode(IMMUNE_HTML, input);
    }

    public String decodeForHTML(String input) {

        if (input == null) {
            return null;
        }
        return html.decode(input);
    }

    public String encodeForHTMLAttribute(String input) {
        if (input == null) {
            return null;
        }
        return html.encode(IMMUNE_HTMLATTR, input);
    }

    public String encodeForCSS(String input) {
        if (input == null) {
            return null;
        }
        return css.encode(IMMUNE_CSS, input);
    }

    public String encodeForJavaScript(String input) {
        if (input == null) {
            return null;
        }
        return js.encode(IMMUNE_JAVASCRIPT, input);
    }

    public String encodeForXPath(String input) {
        if (input == null) {
            return null;
        }
        return html.encode(IMMUNE_XPATH, input);
    }

    public String encodeForXML(String input) {
        if (input == null) {
            return null;
        }
        return xml.encode(IMMUNE_XML, input);
    }

    public String encodeForXMLAttribute(String input) {
        if (input == null) {
            return null;
        }
        return xml.encode(IMMUNE_XMLATTR, input);
    }

    public String encodeForURL(String input) throws Exception {
        if (input == null) {
            return null;
        }
        return URLEncoder.encode(input, enc);
    }

    public String decodeFromURL(String input) throws Exception {
        if (input == null) {
            return null;
        }
        return URLDecoder.decode(input, enc);
    }

    public String encode(String input) throws Exception {
        return joencoder.exec(input);
    }

    public String decode(String input) throws Exception {
        return jodecoder.exec(input);
    }

    public static void main(String[] args) throws Exception {
        Pattern p = Pattern.compile("<[^>]*>");
        String test = "請輸入課程--><sCrIpT>alert(66244)</sCrIpT>";
        String ret = test.replaceAll("<[^>]*>", "");
        System.out.println(ret);

    }

}
