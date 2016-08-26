/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import static DSC.DBClass.ref;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 * @author Amina
 */
public class OrderData extends Orders {

    static Orders o;
    static ArrayList<Orders> allOrders;

    public static void getOrders() {
        allOrders = new ArrayList<>();
        Firebase orderref = ref.child("Orders");
        orderref.addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot orderds, String string) {
                Map<String, String> orderMap = orderds.getValue(Map.class);
                o = new Orders();
                o.setOrderid(orderds.getKey());
                o.setActive(orderMap.get("Active"));
                o.setStartingDate(orderMap.get("StartingDate"));
                o.setDuration(orderMap.get("Duration"));
                o.setFamilySize(orderMap.get("FamilySize"));
//                o.setMealid(orderMap.get(""));
//                o.setMealtype(orderMap.get("MealType"));
//                o.setQuantity(orderMap.get("Quantity"));
//                o.setAllergy(orderMap.get("Allergies"));
//                o.setExclusions(orderMap.get("Exclusions"));

                allOrders.add(o);
            }

            @Override
            public void onChildChanged(DataSnapshot orderds, String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onChildRemoved(DataSnapshot orderds) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onChildMoved(DataSnapshot orderds, String string) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
    }
}
