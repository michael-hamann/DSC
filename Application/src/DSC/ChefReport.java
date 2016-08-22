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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Aliens_Keanu
 */
public class ChefReport {

    public static void getData() {
        Firebase tableRef = ref.child("Orders");// Go to specific Table
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                try {

                    PrintWriter pw = new PrintWriter(new FileWriter("ChefReport.txt"));
                    
                    for (DataSnapshot Data : ds.getChildren()) {

                        for (DataSnapshot Data2 : Data.getChildren()) {

                            for (DataSnapshot Data3 : Data2.getChildren()) {
                                
                                pw.println(Data3.getKey());
                                pw.println("Quantity: " + Data3.child("Quantity").getValue());
                                pw.println("Exclusions: " + Data3.child("Exclutions").getValue());
                                
                                System.out.println(Data3.getKey() + "\n" + "Quantity: " + Data3.child("Quantity").getValue() + "\n" + "Exclusions: " + Data3.child("Exclutions").getValue() + "\n\n");

                            }

                        }
                        System.out.println("\n\n");

                    }
                    pw.close();
                } catch (IOException ex) {
                    Logger.getLogger(ChefReport.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "ERROR: " + fe);
            }
        });
    }

}
