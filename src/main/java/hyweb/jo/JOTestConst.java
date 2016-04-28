/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo;

/**
 *
 * @author william
 */
public class JOTestConst {

    public static String base(String pid) {
        String path = "D:\\Users\\william\\Dropbox\\resources" + "\\" + pid;
        path = path.replaceAll("[\\\\|.]", "/");
        return path;
    }
}
