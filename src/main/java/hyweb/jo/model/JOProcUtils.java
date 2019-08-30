package hyweb.jo.model;

import hyweb.jo.JOProcObject;

/**
 *
 * @author william
 */
public class JOProcUtils {

    private static String PX_METADATA = "$metadata_";

   /**
    * 
    * @param proc
    * @param metaId
    * @return
    * @throws Exception 
    */
    public static JOMetadata metadata(JOProcObject proc, String metaId) throws Exception {
        JOMetadata md = (JOMetadata) proc.opt(PX_METADATA+metaId);
        if (md==null){
            md = new JOMetadata(proc.base(),metaId);
            proc.put(PX_METADATA+metaId, md);
        }
        return md;
    }
    
    /**
     *  主要處理欄位檔使用的 IJOFF
     * @param proc
     * @param md
     * @throws Exception 
     */
    private static void __init_fields(JOProcObject proc, JOMetadata md) throws Exception {
        
    }
    


}
