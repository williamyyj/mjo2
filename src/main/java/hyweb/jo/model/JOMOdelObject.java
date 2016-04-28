/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.model;

import hyweb.jo.org.json.JSONObject;
import hyweb.jo.org.json.JSONTokener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 *
 * @author william
 */
public class JOMOdelObject extends JSONObject {

    protected JSONObject loadJSON(File f) throws Exception {
        Reader reader = new InputStreamReader(new FileInputStream(f), "UTF-8");
        try {
            JSONTokener tk = new JSONTokener(reader);
            return new JSONObject(tk);
        } finally {
            reader.close();
        }
    }
    
}
