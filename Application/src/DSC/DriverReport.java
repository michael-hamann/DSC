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
import java.util.Calendar;

/**
 *
 * @author Aliens_Keanu
 */
public class DriverReport {

    private static ArrayList<Client> clientList = new ArrayList<>();
    private static ArrayList<Order> orderList = new ArrayList<>();

    public static void getClients() {
        Firebase ref = DBClass.getInstance().child("Orders");
        ref.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    Calendar start = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    end.setTimeInMillis(dataSnapshot.child("EndDate").getValue(long.class));
                    start.setTimeInMillis(dataSnapshot.child("StartingDate").getValue(long.class));
                    ArrayList<Meal> meals = new ArrayList<>();
                    for (String string[] : dataSnapshot.child("Meals").getValue(String[][].class)) {
                        
                    }
                    orderList.add(new Order(dataSnapshot.getKey(),
                            true, null, dataSnapshot.child("Duration").getValue(String.class),
                            start,
                            end,
                            dataSnapshot.child("RouteID").getValue(String.class),
                            meals,
                            dataSnapshot.child("FamilySize").getValue(long.class)
                    ));

                }

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.print("Error: Could not get Clients: " + fe.getMessage());
            }
        });

    }

}
