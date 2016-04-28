package hyweb.jo.util;

import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONException;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.org.json.XML;
import static hyweb.jo.org.json.XML.escape;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Iterator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author william
 */
public class XMLUtils {

    public static <T> T load(File f, Class... args) throws Exception {
        return load(new FileInputStream(f), args);
    }

    public static <T> T load(InputStream is, Class... args) throws Exception {
        JAXBContext jaxb = JAXBContext.newInstance(args);
        Unmarshaller unmarshaller = jaxb.createUnmarshaller();
        return (T) unmarshaller.unmarshal(is);
    }

    public static void print(Class cls, Object o) throws Exception {
        JAXBContext jaxb = JAXBContext.newInstance(cls);
        Marshaller marshaller = jaxb.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        //  com.sun.xml.internal.bind.marshaller.CharacterEscapeHandler
        //  com.sun.xml.internal.bind.CharacterEscapeHandler
        //  CharacterEscapeHandler.class.getName();
        //  不同版本支援不一樣      
        marshaller.marshal(o, System.out);
    }

    /**
     * Convert a JSONObject into a well-formed, element-normal XML string.
     *
     * @param object A JSONObject.
     * @return A string.
     * @throws JSONException
     */
    public static String toString(Object object) throws JSONException {
        return toString(object, null);
    }

    /**
     * Convert a JSONObject into a well-formed, element-normal XML string.
     *
     * @param sch xml 定義檔
     * @param jo 資料檔
     * @return A string.
     * @throws JSONException
     */
    public static String toString(JSONObject sch, JSONObject jo) {
        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
        proc_element(sb, sch, jo);
        return sb.toString();
    }

    private static void proc_element(StringBuilder sb, Object sch, Object data) {
        String name = getTagName(sch);
        Object o = getData(sch, data);
        if (o instanceof JSONObject) {
            jo_element(sb, name, sch, o);
        } else if (o instanceof JSONArray) {
            ja_element(sb, name, sch, (JSONArray) o);
        } else {
            text_element(sb, name, data);
        }
    }

    private static void ja_element(StringBuilder sb, String name, Object sch, JSONArray data) {
        for (Object o : data) {
            jo_element(sb, name, sch, o);
        }
    }

    private static void jo_element(StringBuilder sb, String name, Object sch, Object data) {
        sb.append('<').append(name);
        proc_attributes(sb, sch, data);
        sb.append('>');
        proc_child(sb, sch, data);
        sb.append("</").append(name).append('>');
    }

    private static void text_element(StringBuilder sb, String name, Object o) {
        if (o != null) {
            sb.append('<').append(name).append('>');
            sb.append(XML.escape(o.toString())); // #
            sb.append("</").append(name).append('>');
        } else {
            sb.append('<').append(name).append("/>");
        }
    }

    

    private static void proc_child(StringBuilder sb, Object sch, Object data) {
        if (sch instanceof JSONObject) {
            JSONObject jsch = (JSONObject) sch;
            JSONArray schs = jsch.optJSONArray("$c");
            if (data instanceof JSONObject) {
                for (Object c : schs) {
                    Object cdata = getData(c, data);
                    proc_element(sb, c, cdata);
                }
            } else if (data != null) {
                sb.append(XML.escape(data.toString()));
            }
        }

    }

    private static void proc_attributes(StringBuilder sb, Object sch, Object data) {

    }

    private static String getTagName(Object sch) {
        if (sch instanceof String) {
            return ((String) sch).split("\\:")[0];
        } else if (sch instanceof JSONObject) {
            return ((JSONObject) sch).optString("$n").split("\\:")[0];
        }
        return "error";
    }

    private static Object getData(Object sch, Object data) {
        if (sch instanceof String) {
            String ssch = (String) sch;
            return getData(ssch.split("\\:"), data);
        } else if (sch instanceof JSONObject) {
            JSONObject jsch = (JSONObject) sch;
            return getData(jsch.optString("$n").split("\\:"), data);
        }
        return null;
    }

    private static Object getData(String[] alias, Object data) {
        if (data instanceof JSONObject) {
            JSONObject jo = (JSONObject) data;
            Object o = jo.opt(alias[0]);
            if (o == null && alias.length > 1) {
                o = jo.opt(alias[1]);
            }
            return o;
        } else {
            return data;
        }
    }

    /**
     * Convert a JSONObject into a well-formed, element-normal XML string.
     *
     * @param object A JSONObject.
     * @param tagName The optional name of the enclosing tag.
     * @return A string.
     * @throws JSONException
     */
    public static String toString(Object object, String tagName)
            throws JSONException {
        StringBuilder sb = new StringBuilder();
        int i;
        JSONArray ja;
        JSONObject jo;
        String key;
        Iterator<String> keys;
        int length;
        String string;
        Object value;
        if (object instanceof JSONObject) {

// Emit <tagName>
            if (tagName != null) {
                sb.append('<');
                sb.append(tagName);
                sb.append('>');
            }

// Loop thru the keys.
            jo = (JSONObject) object;
            keys = jo.keys();
            while (keys.hasNext()) {
                key = keys.next();
                value = jo.opt(key);
                if (value == null) {
                    value = "";
                }
                string = value instanceof String ? (String) value : null;

// Emit content in body
                if ("content".equals(key)) {
                    if (value instanceof JSONArray) {
                        ja = (JSONArray) value;
                        length = ja.length();
                        for (i = 0; i < length; i += 1) {
                            if (i > 0) {
                                sb.append('\n');
                            }
                            sb.append(escape(ja.get(i).toString()));
                        }
                    } else {
                        sb.append(escape(value.toString()));
                    }

// Emit an array of similar keys
                } else if (value instanceof JSONArray) {
                    ja = (JSONArray) value;
                    length = ja.length();
                    for (i = 0; i < length; i += 1) {
                        value = ja.get(i);
                        if (value instanceof JSONArray) {
                            sb.append('<');
                            sb.append(key);
                            sb.append('>');
                            sb.append(toString(value));
                            sb.append("</");
                            sb.append(key);
                            sb.append('>');
                        } else {
                            sb.append(toString(value, key));
                        }
                    }
                } else if ("".equals(value)) {
                    sb.append('<');
                    sb.append(key);
                    sb.append("/>");

// Emit a new tag <k>
                } else {
                    sb.append(toString(value, key));
                }
            }
            if (tagName != null) {

// Emit the </tagname> close tag
                sb.append("</");
                sb.append(tagName);
                sb.append('>');
            }
            return sb.toString();

// XML does not have good support for arrays. If an array appears in a place
// where XML is lacking, synthesize an <array> element.
        } else {
            if (object.getClass().isArray()) {
                object = new JSONArray(object);
            }
            if (object instanceof JSONArray) {
                ja = (JSONArray) object;
                length = ja.length();
                for (i = 0; i < length; i += 1) {
                    sb.append(toString(ja.opt(i), tagName == null ? "array" : tagName));
                }
                return sb.toString();
            } else {
                string = (object == null) ? "null" : escape(object.toString());
                return (tagName == null) ? "\"" + string + "\""
                        : (string.length() == 0) ? "<" + tagName + "/>"
                                : "<" + tagName + ">" + string + "</" + tagName + ">";
            }
        }
    }

}
