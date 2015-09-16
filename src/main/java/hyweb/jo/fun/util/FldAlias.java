package hyweb.jo.fun.util;

import hyweb.jo.IJOFunction;

/**
 *
 * @author william
 */
public class FldAlias implements IJOFunction<String, String> {

    @Override
    public String exec(String text) throws Exception {
        if (text == null || text.length() == 0) {
            return null;
        }
        char[] buf = text.toLowerCase().toCharArray();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < buf.length; i++) {
            char c = buf[i];
            if (c == '.' || c == '-' || c == '_') {
                continue;
            }
            sb.append(c);
        }
        return sb.toString();
    }

}
