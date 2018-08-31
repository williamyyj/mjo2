package hyweb.jo;

/**
 *
 * @author William
 */
public class JOConst {

    public final static String act = "$act";
    public final static String cmd = "$cmd";
    public final static String classId = "$classId";
    public final static String beforeClassId = "$beforeClassId";
    public final static String afterClassId = "$afterClassId";
    public final static String out = "$out";
    public final static String data = "$data";
    public final static String root = "$root";
    public final static String base = "$base";
    public final static String meta_fields = "$tbFields";
    // scope 
    public final static String before = "$before";
    public final static String after = "$after";
    public final static String eval = "$eval";
    
    public final static String orderby = "$orderby"; // mssql 用  order by 

    public final static String db = "$db";
    public final static String sql = "$sql"; //
    public final static String rs = "$rs";  // ResultSet      
    public final static String stmt = "$stmt"; // Statment 
    public final static String ps = "$ps";  // Preparedstatment 
    public final static String conn = "$conn"; // Connection

    public final static String wp = "$wp";  // input parameter 
    public final static String wpref = "$wref"; 
    public final static String fp = "$fp";
    public final static String p = "$";
    public final static String pp = "$$";
    

    public final static String act_add = "add";
    public final static String act_edit = "edit";
    public final static String act_save = "save";
    public final static String act_delete = "delete";
    public final static String act_row = "row";
    public final static String act_rows = "rows";
    public final static String act_list = "list";
    public final static String act_all = "all";
    public final static String act_fun = "fun";
    public final static String act_batch = "batch";
    public final static String param_sql = "$p_sql";
    public final static String param_fields = "$p_fields";
    public final static String param_ctrl = "$p_ctrl";
    public final static String param_dp = "$p_dp";

    public static String upload_path(String prefix){
        String path =  System.getProperty("catalina.base");
        path = (path!=null) ? path :  System.getProperty("upload") ;
        return  (path==null) ? prefix : path+ prefix ;
                
    }
    
}
