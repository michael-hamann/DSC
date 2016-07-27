/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author Aliens_Michael
 */
public class DBClass {
    
    static private Connection connection;
    
    public static Connection getConnection() throws Exception{
        if (connection == null) {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://22.153.255.61:3306/doorstepchef?zeroDateTimeBehavior=convertToNull", "root", "root");
        }
        return connection;
    }
    
}
