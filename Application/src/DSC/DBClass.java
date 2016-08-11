
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
