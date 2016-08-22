/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import static DSC.DBClass.ref;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import javax.swing.JOptionPane;

/**
 *
 * @author Aliens_Keanu
 */
public class DriverReport {

    public static void getData() {
        Firebase tableRef = ref.child("");// Go to specific Table
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                Firebase tableRef = ref.child(""); // Go to specific Table
                for (DataSnapshot Data : ds.getChildren()) {
                    System.out.println(Data + "\n");
                    System.out.println("\n\n" + Data.child("").getValue() + "\n\n");
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
