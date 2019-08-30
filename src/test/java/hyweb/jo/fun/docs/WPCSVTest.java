package hyweb.jo.fun.docs;

import hyweb.jo.JOProcObject;
import static hyweb.jo.JOTest.base;
import hyweb.jo.fun.util.FJORMWhitespace;
import hyweb.jo.model.JOActorObject;
import hyweb.jo.model.JOWPObject;
import hyweb.jo.org.json.JSONArray;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.DateUtil;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import hyweb.jo.util.JOUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author william
 */
public class WPCSVTest {

    @Test
    public void testSomeMethod() throws Exception {
        JOProcObject proc = new JOProcObject(base);
        String ejo = "H4sIAAAAAAAAAFWRS2pCQRBFtyJNhgVW9fsHhIAgjtyAOHj4eiCY-IuDEDLPIlyGExdk1pHup1e8s3uaou-h6tu9hGloO_c6d16tvl7Oan-nXycP9IwZY85YMJaMFWPN2BCaMrKV9VYLifqz9j1AX-0-oh4hQ8gRCoQSoUKoEZp7MEXAz-ZT7dtuvPmIW3PzW-1ipIPNfnAjT5QR5UQFUUlUEdVEzTOZEpGL9S4uCU9WYd0dnpQFtgJRgaNAT2AmkBL4CFQEFgIBQXcsbpefsXN3DPuvSMtVWtpE1VfpDN3WIqbhodqwv1639Xgyn55-_gHTAQHqpAIAAA==";
        JSONObject p = JOTools.decode_jo(ejo);
        proc.params().putAll(p);
        try {
            JOActorObject act = new JOActorObject(proc, JOUtils.toJA("rpt_supload,export,docs.wp_csv"));

            act.put("$usedData", true);
            System.out.println(act.toString(4));
            JOWPObject wp = new JOWPObject(act);
            Date d1 = (Date) proc.db().fun("select top 1 ct  from psReportLog where procId='批發未回傳名單' order by ct desc  ");
            String statDate = DateUtil.convert("yyyy/MM/dd", d1);
            String dp1 = proc.params().optString("dp1").substring(0, 7);
            String dp2 = proc.params().optString("dp2").substring(0, 7);
            String cond = dp1 + "~" + dp2;
            List<String> data = new ArrayList<String>();
            proc.put("$csv", data);
            StringBuilder sb = new StringBuilder();
            sb.append("統計截止日期,").append(statDate).append(",查詢日期區間,=\"").append(cond).append('"');
            data.add(sb.toString());
            getCSVData(proc);
            JOFunctional.exec("docs.wp_csv", wp);
            for (String item : data) {
                System.out.println(item);
            }
        } finally {
            proc.release();
        }
    }

    private void getCSVData(JOProcObject proc) throws Exception {
        JSONObject p = new FJORMWhitespace().exec(proc.params());
        JOWPObject wp1 = new JOWPObject(proc, "rpt_supload", "query", p, null);
        List<JSONObject> rows = (List<JSONObject>) JOFunctional.exec("wp_rows", wp1);
        JOWPObject wp2 = new JOWPObject(proc, "rpt_supload", "cquery", p, null);
        List<JSONObject> crows = (List<JSONObject>) JOFunctional.exec("wp_rows", wp2);
        Map<String, JSONObject> hm = new HashMap<String, JSONObject>();
        JSONArray eName = proc.params().getJSONArray("$eName");
        if (rows != null && eName != null) {
            if (crows != null) {
                for (JSONObject row : crows) {
                    hm.put(row.optString("baphiqId") + row.optString("yymm"), row);
                }
            }

            for (JSONObject row : rows) {
                for (int i = 0; i < eName.length(); i++) {
                    String id = row.optString("baphiqId") + eName.optString(i);
                    JSONObject c = hm.get(id);
                    if (c != null) {
                        row.put("D" + eName.optString(i), "\\n"+DateUtil.convert("yyyy/MM/dd HH:mm", c.optDate("createDate")));
                    }
                }
            }
        }
        System.out.println(rows);
        proc.put("$data",rows);
    }

}
