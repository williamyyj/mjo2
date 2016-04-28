package hyweb.jo;

/**
 * @author william
 */
public class JOInitConst {

    public static String root() {
        return "D:\\Users\\william\\Dropbox\\resources";
    }
    
    public static String base(String pid){
        return root()+"\\"+pid;
    }

    public static String upload(String prefix) {
        return null ; 
    }
}
