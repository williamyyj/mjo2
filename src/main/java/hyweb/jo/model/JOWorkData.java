package hyweb.jo.model;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOPath;

/**
 * 
 *   $ 和 JOWPObject 不同意義 是指 row data 不一定是 input data 
 *   $  input   
 *   $cfg  外部設定
*    $check  運算結果檢查
*    $data 暫存資料 --->output 
 * @author william
 */
public class JOWorkData extends JSONObject {

    public JOWorkData() {
        super();
    }

    public JOMetadata metadata(String base, String mId) {
        String mdPath = "$metadata:" + mId;
        Object metadata = JOPath.path(this, mdPath);
        if (metadata == null) {
            metadata =  new JOMetadata(base,mId);
            JOPath.set(this, mdPath,metadata);
        }
        return (JOMetadata) metadata;
    }

    public JSONObject act(String base, String mId, String actId) {
        JOMetadata md = metadata(base, mId);
        return md.cfg().optJSONObject(actId);
    }
    
  
    
    public void reset(JSONObject row){
        put("$",row);
    }
    
    public JSONObject p(){
        if(!has("$")){
           put("$",new JSONObject());
        }
        return this.optJSONObject("$");
    }
    
    
}
