package hyweb.jo.db;

import hyweb.jo.JOTest;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;
import hyweb.jo.util.TextUtils;
import java.util.List;
import junit.framework.Assert;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOMSPageTest extends JOTest {

	@Test
	public void page_test() throws Exception {

		JSONObject jq = JOTools.toJSONObject("{mem_id:'',dp1:'2015/07/01',dp2:'2015/09/30'}");
		TextUtils.cast_date(jq, "dp1", "yyyy/MM/dd", 0);
		TextUtils.cast_date(jq, "dp2", "yyyy/MM/dd", 1);
		System.out.println(jq.opt("dp1").getClass());
		DB db = new DB(base);
		try {

			JOMSPage page = new JOMSPage(db, "pos", "pos_list", jq, 10);
			Assert.assertEquals(true, page.rowCount() > 0);
			List<JSONObject> rows = page.rows(1, "mem_store_id");
			Assert.assertNotNull(rows);
			Assert.assertEquals(true, rows.size() > 0);

		} finally {
			db.close();
		}
	}
}
