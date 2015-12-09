package hyweb.jo;

import java.io.File;

/**
 *
 * @author william
 */
public class JOTest {

    public static String root = "D:\\will\\work\\nb\\mwork\\src\\main\\webapp";
    public static String base = root + "\\WEB-INF\\prj\\baphiq";
    public static String ap_root = "D:\\will\\resources\\web\\tomcat7";
    public static String res_root = "D:\\Users\\william\\Dropbox\\resources";

    static {

        if ("Mac OS X".equals(System.getProperty("os.name"))) {
            root = "/Users/william/Dropbox/resources";
            base = root + "/prj/baphiq";
        } else {
            File f = new File(root);
            if (!f.exists()) {
                root = "D:\\Users\\william\\Dropbox\\resources\\";
                base = root + "\\prj\\baphiq";
            }
        }

    }

}
