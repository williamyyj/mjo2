package hyweb.jo.util;

import hyweb.jo.log.JOLogger;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.org.json.JSONTokener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.concurrent.Callable;

public class JOCacheFileItem implements IJOCacheItem<JSONObject>, Callable<IJOCacheItem<JSONObject>> {

    private JSONObject data;
    private final String id;
    private long lastModified;

    public JOCacheFileItem(String id) {
        this.id = id;
        File f = new File(id);
        if (f.exists()) {
            this.lastModified = f.lastModified();
        }
    }

    @Override
    public long lastModified() {
        return this.lastModified;
    }

    @Override
    public JSONObject load() throws Exception {
        File f = new File(id);
        if (f.exists()) {
            if (data == null) {
                JOLogger.debug("Load file : " + f);
                data = loadJSON(f);
            } else if (f.lastModified() > this.lastModified) {
                JOLogger.debug("Reload file : " + f);
                data = loadJSON(f);
            } 
        } else {
            JOLogger.debug("Can't find file : " + f);
        }
        return data;
    }

    @Override
    public void unload() {
        data = null;
    }

    private JSONObject loadJSON(File f) throws Exception {
        Reader reader = new InputStreamReader(new FileInputStream(f), "UTF-8");
        try {
            this.lastModified = f.lastModified();
            JSONTokener tk = new JSONTokener(reader);
            return new JSONObject(tk);
        } finally {
            reader.close();
        }
    }

    @Override
    public IJOCacheItem call() throws Exception {
        return this;
    }

}
