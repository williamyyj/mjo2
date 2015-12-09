package hyweb.jo.db;

import hyweb.jo.JOProcObject;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;

/**
 *
 * @author william
 */
public class JOPageInfo {

    private int pageId;   //  nowPage
    private JSONObject params;
    private String url;
    private int pageNum;  // 每頁顯示幾筆
    private int total; // 資料總數
    private int navSize = 10;

    public JOPageInfo(String url, JSONObject params, int pageId, int pageNum, int total) {
        __init__(url, params, pageId, pageNum, total);
    }

    public JOPageInfo(JOProcObject proc, String url, int total) {
        JSONObject p = proc.params();
        int pId = p.optInt("pageId", 1);
        int pNum = p.optInt("pageNum", 10);
        __init__(url, p, pId, pNum, total);
    }

    private void __init__(String url, JSONObject params, int pageId, int pageNum, int total) {
        this.url = url;
        this.pageId = pageId;
        this.params = params;
        this.pageNum = pageNum;
        this.total = total;
    }

    public int getPageId() {
        return pageId;
    }

    public boolean isFirstPage() {
        return (pageId <= 1);
    }

    public boolean isLastPage() {
        return (pageId >= getPageLength());
    }

    public int getPageLength() {
        int length = total / pageNum;
        return (total % pageNum == 0) ? length : length + 1;
    }

    public int getNavStart() {
        int ps = (pageId / navSize) * navSize;
        return (pageId == ps) ? ps - navSize : ps;
    }

    public int getNavEnd() {
        int len = getPageLength();
        int pe = getNavStart() + 10;
        return (pe > len) ? len : pe;
    }

    public String getUrl(int idx) {
        params.put("pageId", idx);
        params.put("pageNum", pageNum);
        String ejo = "";
        try {
            ejo = JOTools.encode(params.toString());
        } catch (Exception ex) {

        }
        return url + "?ejo=" + ejo;
    }

    public String getFirstUrl() {
        return getUrl(1);
    }

    public String getPriorUrl() {
        return getUrl(pageId - 1);
    }

    public String getNextUrl() {
        return getUrl(pageId + 1);
    }

    public String getLastUrl() {
        return getUrl(this.getPageLength());
    }

}
