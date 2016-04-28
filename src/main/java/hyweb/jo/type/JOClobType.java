/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hyweb.jo.type;

import java.sql.Clob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

/**
 *
 * @author William
 */
public class JOClobType extends JOStringType {

    @Override
    public String dt() {
        return "clob";
    }

    @Override
    public int jdbc() {
        return Types.CLOB;
     }


}
