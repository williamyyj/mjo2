package hyweb.jo.data;

import java.io.File;
import java.io.FileInputStream;

public class JOFile extends JOData {

    public JOFile() {

    }

    public byte[] loadData(String path) throws Exception {
        return loadData(new FileInputStream(new File(path)));
    }

    public void close() throws Exception {

    }

}
