package hyweb.jo.fun;

import hyweb.jo.IJOFunction;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.FJO2Map;
import hyweb.jo.util.JOCache;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.JOProcObject;

/**
 * @author william 整合combo
 */
public class mapping implements IJOFunction<Boolean, Object[]> {

    @Override
    public Boolean exec(Object[] args) throws Exception {
        JOProcObject proc = (JOProcObject) args[0];
        String mapId = (String) args[1];
        String id = (String) args[2];
        int field = (Integer) (args.length > 3 ? args[3] : 0);
        String  rt = (String) (args.length > 4 ? args[4] : "json");
        JSONObject m = JOCache.load(proc.base() + "/combo", mapId);
        String type = m.optString("$type");
        if ("db".equals(type)) {
            proc_db_map(proc,field,id,m,rt);
        } else {
            proc_static_map(proc,field, id, m,rt);
        }
        return true;
    }

    private void proc_db_map(JOProcObject proc, int field, String id, JSONObject m,String rt) throws Exception {

    }

    private void proc_static_map(JOProcObject proc, int field, String id, JSONObject m,String rt) throws Exception {
       if("json".equals(rt)){
           proc.set(field, id, m.opt("$data"));
       } else if ("map".equals(rt)){
           proc.set(field, id, FJO2Map.toMap(m.optJSONObject("$data")));
       }
    }

    public static void main(String[] args) throws Exception {
        String base = "D:\\will\\work\\nb\\mwork\\src\\main\\webapp\\WEB-INF\\prj\\baphiq";
        JOProcObject proc = new JOProcObject(base);
        try {
            JOFunctional.exec2("mapping", proc, "m_upload","$m",JOProcObject.RequestValue);
            JSONObject jo = (JSONObject) proc.get(JOProcObject.RequestValue,"$m",new JSONObject());
            System.out.println(jo.toString(4));
        } finally {
            proc.release();
        }

    }
}
