package DSC;

import DSC.TokenGeneration.TokenGenerator;
import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.shaded.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Aliens_Michael
 */
public class DBClass {

    public static Firebase ref;

    public static void main(String[] args) {
        makeConn();
    }

    static private Connection connection;

    public static Connection getConnection() throws Exception {
        if (connection == null) {
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://22.153.255.61:3306/doorstepchef?zeroDateTimeBehavior=convertToNull", "root", "root");
        }
        return connection;
    }

    public static String genToken(String uid) {
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("uid", uid);
        TokenGenerator tokenGenerator = new TokenGenerator("9zeiksdYSEyETtuUDSlXiTRAo23KTQBv2mIfrMyp");
        String token = tokenGenerator.createToken(payload);
        return token;
    }

    public static void makeConn() {
        ref = new Firebase("https://dsc-database.firebaseio.com/");
        Firebase newref = ref.child("Routes");
        Firebase.AuthResultHandler authHandler = new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData ad) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onAuthenticationError(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        System.out.println("Here");
        ref.authWithCustomToken(genToken("Website"), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticationError(FirebaseError error) {
                System.err.println("Login Failed! " + error.getMessage());
            }

            @Override
            public void onAuthenticated(AuthData authData) {
                System.out.println("Login Succeeded!");
            }
        });
    }

    public static void getData() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                System.out.println(ds + "\n");
                for (DataSnapshot Data : ds.getChildren()) {
                    System.out.println(Data + "\n");
                    System.out.println("\n\n" + Data.child("StartingDate").getValue() + "\n\n");
                    for (DataSnapshot Data2 : Data.getChildren()) {
                        System.out.println(Data2);
                        
                    }
                    System.out.println("\n\n");
                }
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "ERROR: " + fe);
            }
        });
    }

}
