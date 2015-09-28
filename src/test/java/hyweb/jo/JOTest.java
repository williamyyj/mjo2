/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo;

import java.io.File;

/**
 *
 * @author william
 */
public class JOTest {

	public static String root = "D:\\will\\work\\nb\\mwork\\target\\mwork-1.0-SNAPSHOT";
	public static String upload_root = root + "\\public";
	public static String base = root + "\\WEB-INF\\prj\\baphiq";

	static {

		if ("Mac OS X".equals(System.getProperty("os.name"))) {
			root = "/Users/william/Dropbox/resources";
			base = root+"/prj/baphiq";
		} else {
			File f = new File(root);
			if (!f.exists()) {
				root = "D:\\Users\\william\\Dropbox\\resources\\";
				base = root + "\\prj\\baphiq";
			}
		}

	}

}
