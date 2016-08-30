/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.JOptionPane;

/**
 *
 * @author Amina
 */
public class ClientData extends Client{
    static private Client c;
    static ArrayList<Client> allclients;
    
    public static void getData(){
            allclients = new ArrayList<>();
            Firebase clientref = DBClass.getInstance().child("Clients");
            clientref.addChildEventListener(new ChildEventListener() {
            
            @Override
            public void onChildAdded(DataSnapshot ds, String string) {
                Map<String,String> clientMap = ds.getValue(Map.class);
                c = new Client();
               
                c.setID(ds.getKey());
                c.setName(clientMap.get("Name"));
                c.setSurname(clientMap.get("Surname"));
                c.setAddress(clientMap.get("Address"));
                c.setAlternativeNumber(clientMap.get("Alternative Number"));
                c.setContactNumber(clientMap.get("ContactNum"));
                c.setEmail(clientMap.get("Email"));
                c.setAdditionalInfo(clientMap.get("Additional Information"));
                c.setSuburb(clientMap.get("Suburb"));
                allclients.add(c);
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "ERROR: " + fe);
            }

                @Override
                public void onChildChanged(DataSnapshot ds, String string) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void onChildRemoved(DataSnapshot ds) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

                @Override
                public void onChildMoved(DataSnapshot ds, String string) {
                    throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                }

        });
       
    }       
}
