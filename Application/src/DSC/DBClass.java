
package DSC;

import DSC.TokenGeneration.TokenGenerator;
import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 * @author Rossouw Binedell
 */
public class DBClass {

    private static Firebase ref;
    /**
     * Returns whether the database is connected or not
     */
    public static boolean connected = false;

    /**
     * Creates an instance of the database
     */
    public static Firebase getInstance() {
        if (ref == null) {
            ref = new Firebase("https://dsc-database.firebaseio.com/");
            ref.authWithCustomToken(genToken("Desktop"), new Firebase.AuthResultHandler() {
                @Override
                public void onAuthenticated(AuthData ad) {
                    System.out.println("Database succesfully connected!");
                    connected = true;
                }

                @Override
                public void onAuthenticationError(FirebaseError fe) {
                    JOptionPane.showMessageDialog(null, "Error: Could not Authenticate to Database.\nError : " + fe, "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(1);
                }
            });
        }

        return ref;
    }

    private static String genToken(String uid) {
        Map<String, Object> payload = new HashMap<String, Object>();
        payload.put("uid", uid);
        TokenGenerator tokenGenerator = new TokenGenerator("9zeiksdYSEyETtuUDSlXiTRAo23KTQBv2mIfrMyp");
        String token = tokenGenerator.createToken(payload);
        return token;
    }
}
