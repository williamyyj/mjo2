package hyweb.jo.fun.model;

import hyweb.jo.org.json.JSONObject;
import java.io.File;
import java.util.List;
import org.junit.Test;


/**
 *
 * @author william
 */
public class FMLoadComboTest {

    /**
     * Test of exec method, of class FMLoadCombo.
     * @throws java.lang.Exception
     */
    @Test
    public void testExec() throws Exception {
        File base = new File("D:\\Dropbox\\resources\\prj\\baphiq");
        StringBuilder sb = new StringBuilder();
        sb.append("combo").append("::")
                .append(base.toURI()).append("::")
                .append("city");
        List<JSONObject> rows = new FMLoadCombo().exec(sb.toString());
        for (JSONObject row : rows) {
            System.out.println(row);
        }
    }

}
