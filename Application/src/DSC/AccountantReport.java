/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Aliens_Ross
 */
public class AccountantReport {
    
    private static ArrayList<Client> clients;
    private static int clientCount;
    
    public static void getAccountantData(){
        clients = new ArrayList<>();
        Firebase ref = DBClass.getInstance();
        ref.child("Orders").orderByChild("Active").equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    clients.add(new Client(dataSnapshot.child("ClientID").getValue(String.class)));
                }
                
                for (int i = 0; i<clients.size(); i++) {
                    getClient(clients.get(i), i);
                }
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.println("Error: " + fe.getMessage());
            }
        });
    }
    
    private static void getClient(Client client, int index){
        Firebase ref = DBClass.getInstance().child("Clients/" + client.getID());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                clients.get(index).setData(
                        ds.child("Name").getValue(String.class),
                        ds.child("Surname").getValue(String.class),
                        ds.child("ContactNum").getValue(String.class),
                        ds.child("AlternativeNumber").getValue(String.class),
                        ds.child("Email").getValue(String.class),
                        ds.child("Suburb").getValue(String.class),
                        ds.child("Address").getValue(String.class),
                        ds.child("AdditionalInfo").getValue(String.class)
                );
                System.out.println(clients.get(index));
                clientCount++;
                if (clientCount == clients.size()) {
                    
                }
                
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.println("Error: " + fe.getMessage());
            }
        });
        
    }
    
    private static void createExcelReport(){
        XSSFWorkbook workbook = new XSSFWorkbook();
        Map<String, Object[]> data = new TreeMap<>();
        for (Client client : clients) {
            
        }
        
    }
    
}
