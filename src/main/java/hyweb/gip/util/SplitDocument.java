package hyweb.gip.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplitDocument {

    private String ori_context;
    /* 文章內文 */
    private String context; // "<x:xxx>" + context + "</x:xxx>";
    private int wordsPerPage = 600;
    /* 一頁幾個字，不含HTML TAG */
    private String baseFileName;
    /* 存檔的檔名，ex:page1為 baseFileName + "_1.txt" */
    private File baseDir;
    /* 存檔的目錄 */
    private int cc;

    private List<HTag> tags; //
    private List<String> pages;
    private List<Integer> sizes;
    private Stack<HTag> ss; // 運算用stack
    private Pattern p_tag = Pattern.compile("<[^>]*>");
    private Pattern p_comment = Pattern.compile("<!--(.*?)-->");
    private static List<String> l_esc = new ArrayList();

    static {
        l_esc.add("&nbsp;");
        l_esc.add("&quot;");
        l_esc.add("&amp;");
        l_esc.add("&lt;");
        l_esc.add("&gt;");
    }

    public SplitDocument(File baseDir, String baseFileName, String context) {
        init(baseDir, baseFileName, context, this.wordsPerPage);
    }

    /**
     *
     * <p>
     * Example : String text =
     * SplitDocument.loadFormFile("src/test/td/test.html", "UTF-8");
     * SplitDocument sd = new SplitDocument(new
     * File("src/test/out"),"test",text,600); sd.process(); for(String page :
     * sd.pages){ System.out.println("======================");
     * System.out.println(page); }
     * </p>
     *
     * @param baseDir 要產出的路徑
     * @param baseFileName 要產出的檔名
     * @param context 要分析的文件
     * @param wordsPerPage 每頁有幾字
     */
    public SplitDocument(File baseDir, String baseFileName, String context,
      int wordsPerPage) {
        init(baseDir, baseFileName, context, wordsPerPage);
    }

    protected void init(File baseDir, String baseFileName, String context,
      int wordsPerPage) {
        this.baseDir = baseDir;
        this.baseFileName = baseFileName;
        this.ori_context = context;
        this.context = "<x:xxx>" + context + "</x:xxx>";
        this.wordsPerPage = wordsPerPage;
        this.tags = new ArrayList<HTag>();
        this.pages = new ArrayList<String>();
        this.sizes = new ArrayList<Integer>();
        this.ss = new Stack<HTag>();
    }

    public static String loadFormFile(String fn, String enc) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(
          new FileInputStream(fn), enc));
        StringBuilder sb = new StringBuilder();
        try {
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line).append("\n");
            }

        } finally {
            if (br != null) {
                br.close();
            }
        }
        return sb.toString();
    }

    public static void saveToFile(File f, String context, String enc)
      throws Exception {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
          new FileOutputStream(f), enc));
        try {
            bw.write(context);
            bw.flush();
        } finally {
            if (bw != null) {
                bw.close();
            }
        }
    }

    public static void saveToFile(String base, String fn, String context,
      String enc) throws Exception {
        saveToFile(new File(base, fn), context, enc);
    }

    public void process() {
        ss.clear();
        tags.clear();
        pages.clear();
        sizes.clear();
        if (context != null && context.trim().length() > 0) {
            process_tag();
            // process_show();
            process_split();
            process_after();
            // process_files();
        }
    }

    public boolean is_spilt_mark(char ch) {
        if (ch == '。') {
            return true;
        }
        return false;
    }

    public void process_show() {
        for (HTag tag : tags) {
            System.out.println(tag);
        }
    }

    public void process_split() {

        HTag prior = null;
        StringBuffer sb = new StringBuffer();
        boolean isEndLine = false;
        cc = 0;
        int idx = 0;
        for (HTag tag : tags) {
            char cs = tag.id.charAt(0);
            char ce = tag.id.charAt(tag.id.length() - 1);
            if (prior != null) {
                int ps = prior.pe;
                int pe = tag.ps;
                if (pe > ps) {
                    this.split_page(sb, ps, pe);
                }
            }
            if (cs != '!' && cs != '/' && ce != '/') {
                // push tag
                if (!"img".equalsIgnoreCase(tag.id)) {
                    ss.push(tag);
                }
            } else if (cs == '/') {
                // drop tag
                HTag last = ss.lastElement();
                String id = tag.id.replaceAll("/", "");
                if (last != null && last.id.equals(id)) {
                    ss.pop();
                }
            }
            // 開始處理tag
            if (!"x:xxx".equals(tag.id) && !"/x:xxx".endsWith(tag.id)) {
                sb.append(context.substring(tag.ps, tag.pe));
            }
            prior = tag;
        }
        // -- 原先修正空白頁改由後處理
        // if (sb.length() > 0 && cc>0) {
        if (sb.length() > 0) {
            this.add_end_tag(sb);
            add_page(sb);
        }
        if (this.pages().size() == 0) {
            pages.add(ori_context);
            sizes.add(0);
        }
    }

    public void process_after() {
        if (sizes.size() > 1) {
            int last = sizes.size() - 1;
            int s = sizes.get(last);
            if (s == 0) {
                String last_data = pages.remove(last);
                sizes.remove(last);
                pages.set(last - 1, pages.get(last - 1) + last_data);

            }
        }
    }

    public void add_page(StringBuffer sb) {
        pages.add(sb.toString());
        sizes.add(cc);
    }

    public void split_page(StringBuffer sb, int ps, int pe) {
        String text = context.substring(ps, pe);
        int len = text.length();
        int idx = 0;

        while (idx < len) {
        
           
            char c = text.charAt(idx);
                System.out.println(idx+"--->" + c);
            // System.out.println("----->  cc : " + cc + " asc : " +(int)c+
            // " : " + c + " idx : " + idx);
            if (c == 9 || c == 10 || c == 13 || c == 32) {
                sb.append(c);
                idx++;
            } else if (c == '&') {
                String tail = text.substring(idx);
                boolean is_esc = false;
                for (String esc : l_esc) {
                    if (tail.startsWith(esc)) {
                        sb.append(esc);
                        cc++;
                        idx = idx + esc.length();
                        is_esc = true;
                        break;
                    }
                }
                if (!is_esc) {
                    idx++;
                    sb.append(c);
                    cc++;
                }
            } else {
                cc++;
                idx++;
                sb.append(c);
            }

            if (cc >= wordsPerPage && is_spilt_mark(c)) {
                // System.out.println("===== check ss : " + ss);
                add_end_tag(sb);
                // pages.add(sb.toString());
                add_page(sb);
                sb.setLength(0);
                add_start_tag(sb);
                cc = 0;
                // break ;
            }
        }
    }

    protected void add_start_tag(StringBuffer sb) {
        if (ss.size() > 0) {
            for (int i = 0; i < ss.size(); i++) {
                HTag tag = ss.get(i);
                if (!"x:xxx".equals(tag.id)) {
                    sb.append(context.substring(tag.ps, tag.pe));
                }
            }
        }
    }

    protected void add_end_tag(StringBuffer sb) {
        if (ss.size() > 0) {
            for (int i = ss.size() - 1; i > -1; i--) {
                HTag tag = ss.get(i);
                String id = "</" + tag.id + ">";
                if (!"x:xxx".equals(tag.id)) {
                    sb.append(id);
                }
            }
        }
    }

    public String fileId(int idx) {
        return this.baseFileName + "_" + idx + ".txt";
    }

    public boolean m(int idx, String text) {
        char[] buf = text.toCharArray();
        for (char c : buf) {
            if (idx >= context.length() || c != context.charAt(idx++)) {
                return false;
            }
        }
        return true;
    }

    public void process_tag() {
        context = context.replaceAll("(?s)<!--(.*?)-->", "");
        Matcher m = p_tag.matcher(context);
        HTag tag = null;

        while (m.find()) {

            int ps = m.start();
            int pe = m.end();
            String id = context.substring(ps + 1, pe - 1).toLowerCase();
            // System.out.println("======= item : " + id);
            String[] items = id.split(" ");

            if (items[0].startsWith("br")) {
                // fix single html tag
                id = "br/";
            } else if (items.length > 1
              && items[items.length - 1].trim().equals("/")) {
                id = items[0] + "/";
            } else {
                id = items[0];
            }

            tag = new HTag(ps, pe, id);
            tags.add(tag);
        }

    }

    public void process_files() {
        if (!baseDir.exists()) {
            baseDir.mkdirs();
        }
        try {
            saveToFile(new File(baseDir, baseFileName + ".txt"), context,
              "UTF-8");
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        int idx = 1;
        for (String p : pages) {
            File f = new File(baseDir, fileId(idx));
            try {
                saveToFile(f, p, "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
            idx++;
        }
    }

    public List<String> pages() {
        return this.pages;
    }

    public static class HTag {

        int ps;
        int pe;
        String id;

        public HTag(int ps, int pe, String id) {
            this.ps = ps;
            this.pe = pe;
            this.id = id;
        }

        public String toString() {
            return ps + "," + pe + "," + id;
        }
    }

    public static class HCTag extends HTag {

        public HCTag(int ps, int pe, String id) {
            super(ps, pe, id);
        }

    }

    public static void main(String[] args) throws Exception {
     String text = SplitDocument.loadFormFile("D:\\Users\\william\\Dropbox\\resources\\prj\\baphiq_data\\utils\\article.html", "UTF-8");
     SplitDocument sd = new SplitDocument(new File("src/test/out"),"test",text,600); sd.process(); 
     for(String page : sd.pages){ 
         System.out.println("======================");
         System.out.println(page); 
     }

    }

}
