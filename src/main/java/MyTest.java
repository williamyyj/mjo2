
import hyweb.jo.org.json.JSONObject;
import hyweb.jo.util.JOTools;
import java.io.IOException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author William
 */
public class MyTest {

    public static void main(String[] args) throws IOException {
        String ejo_s = "H4sIAAAAAAAAAGWQwUrDMBjHXyUEjz18aZqm61VPXhxsPkCadmtxnV2bHMYYeBFUBCfuIoi-gOziRU--jO3wLUzaDQaGQJL_98s__3wLPNV5X4wTHBLm4EoJpSscYoodnCuzGaYanUmFgCCXhBCELqDjwRC5QJhhKh3lmaIG_PneNOuvenPf3KxM4SivxjhcLB0cyanIjT_ePr42H0_b9Vtzd9W83P4-r-rr9_rh09CRKOVlbCGPE3ABGAEgNkRSlrFQYmclijSbZbHhzvtghgsBg57NKi6SeJ_3VE8QJQggbOc-r7fj2vv2DJwSIEYtugY4uOxqgdV0KVNRda4DPUWDpECE_nO1XchjZiBGfTHymABJuc-8HiccegmJuR9watYD05mam_dccLCQ1l-m2cTWR2pe2C6c7Iwj2QY6-GjVKQwv_wCiJehuuwEAAA==";
        JSONObject jo = JOTools.decode_jo(ejo_s);
        System.out.println(jo.toString(4));
    }
}
