package hyweb.jo.ht;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import org.junit.Test;

/**
 * @author william
 */
public class HTGridTest {

 
    public void barcode() throws Exception {
        JOProcObject proc = new JOProcObject(JOTest.base);
        try {
            String html = new HTGrid("{id:rows, meta:barcode_t , act:query}").render(proc);
            System.out.println(html);
        } finally {
            proc.release();
        }
    }

    public void crop() throws Exception {
        JOProcObject proc = new JOProcObject(JOTest.base);
        try {
            String html = new HTGrid("{id:rows,meta:crop_t , act:query}").render(proc);
            System.out.println(html);
        } finally {
            proc.release();
        }
    }


    public void pest() throws Exception {
        JOProcObject proc = new JOProcObject(JOTest.base);
        proc.params().put("dp", "2017-01-19");
        try {
            String html = new HTGrid("{id:rows, meta:pest_t , act:query}").render(proc);
            System.out.println(html);
        } finally {
            proc.release();
        }
    }

    public void relaction() throws Exception {
        JOProcObject proc = new JOProcObject(JOTest.base);
        try {
            String html = new HTGrid("{id:rows, meta:pestcroprelation_t , act:query}").render(proc);
            System.out.println(html);
        } finally {
            proc.release();
        }
    }

    public void lic() throws Exception {
        JOProcObject proc = new JOProcObject(JOTest.base);
        try {
            String html = new HTGrid("{id:rows, meta:pesticidelic_t , act:query}").render(proc);
            System.out.println(html);
        } finally {
            proc.release();
        }
    }

}
