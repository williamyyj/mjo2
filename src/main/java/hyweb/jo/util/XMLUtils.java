package hyweb.jo.util;



import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
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
}
