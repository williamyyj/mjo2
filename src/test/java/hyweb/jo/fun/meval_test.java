/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.fun;

import hyweb.jo.JOProcObject;
import hyweb.jo.JOTest;
import hyweb.jo.model.IJOField;
import hyweb.jo.model.JOMetadata;
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOFunctional;
import hyweb.jo.util.JOTools;
import java.util.List;
import org.junit.Test;

/**
 *
 * @author william
 */
public class meval_test extends JOTest {


    public void test_0001() throws Exception {
        String row_src = " {\"passwd\":\"1qaz2wsx\",\"VerifyCode\":\"2102\",\"uname\":\"王10\",\"tel_ext\":\"1234\",\"cid\":\"1030125\",\"tel_num\":\"12345678\",\"baphiqid\":\"UP0000000010\",\"email\":\"williamyyj@gmail.com\",\"stype\":\"[\",\"mid\":\"0\",\"passwd1\":\"1qaz2wsx\",\"act\":\"add\",\"dataid\":\"10\",\"tel_area\":\"02\",\"cname\":\"海博生技股份有限公司\",\"mobile\":\"\"}";
        JSONObject row = JOTools.loadString(row_src);
        JOProcObject proc = new JOProcObject(base);
        try {
            String metaId = "ps_store";
            JOMetadata metadata = proc.metadata(metaId);
            List<IJOField> eFields = metadata.getFieldsByScope("add");
            List<IJOField> dbFields = metadata.getFieldsByScope("db");
            JOFunctional.exec2("meval",proc, eFields,row);
            System.out.println(row.toString(4));
             JOFunctional.exec2("model.FDOInsert", proc, "ps_store", row);
        } finally {
            proc.release();
        }
    }
}
