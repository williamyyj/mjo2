/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.util;


import org.cc.org.apache.commons.text.StringEscapeUtils;
import org.junit.Test;

/**
 *
 * @author william
 */
public class JOEscapeTest {

    @Test
    public void test_xmlEscape() {
        System.out.println(StringEscapeUtils.escapeXml11("491{!!}興農好樂肥1號{!!}"));
    }
}
