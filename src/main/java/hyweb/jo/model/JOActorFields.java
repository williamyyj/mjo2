package hyweb.jo.model;

import hyweb.jo.JOProcObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * 處理全部欄位
 *
 * @author william
 *
 */
public class JOActorFields extends HashMap<String, IJOField> {

    private HashSet<String> names = new HashSet<String>();

    public JOActorFields() {

    }
    
    public void addFromMetadata(JOProcObject proc, String metaId, String alias){
        JOMetadata md = proc.wMeta().metadata(metaId);
        String id = md.cfg().optString("id",metaId);
        List<IJOField> fields = md.getFields();
        for(IJOField fld : fields){
           
        }
    }

    public void addField(IJOField fld, String id, String alias) {
        String name = fld.id(); 
        IJOField ofld = put(name,fld);
        
    }
}
