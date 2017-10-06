package hyweb.jo;

import hyweb.jo.ff.JOFF;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOPath;

/**
 *
 * @author william
 */
public class JOWorkMeta extends JSONObject {

    private JOProcObject proc;

    public JOWorkMeta(JOProcObject proc) {
        this.proc = proc;
    }

    public JOMetadata metadata(String mid) {
        return metadata(proc.base(), mid);
    }

    public JOMetadata metadata(String base, String mId) {
        String mdPath = "$metadata:" + mId;
        Object md = JOPath.path(this, mdPath);
        if (md == null) {
            md = new JOMetadata(base, mId);
            JOPath.set(this, mdPath, md);
            JOFF.init_ff(proc, (JOMetadata) md);
        }
        return (JOMetadata) md;
    }
    
    public JSONObject event(String base, String mId , String eventId){
        return metadata(base,mId).cfg().optJSONObject(eventId);
    }

    public JSONObject event(String mId, String eventId) {
        return event(proc.base(),mId,eventId);
    }
    
}
