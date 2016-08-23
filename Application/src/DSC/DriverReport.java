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
public class DriverReport {

    public static Firebase tableRef;
    public static PrintWriter pw;
    public static String formatStr = "%-20s %-15s %n";
    public static String OrderID = "";
    public static String FamilySize = "";

    public static void getDriverData_Clientstb() {

        tableRef = ref.child("Clients");// Go to specific Table]\

        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                try {

                    pw = new PrintWriter(new FileWriter("DriverReport.txt"));

                    for (DataSnapshot Data : ds.getChildren()) {// entire database

                        pw.println("Name: " + Data.child("Name").getValue());
                        pw.println("Surname: " + Data.child("Surname").getValue());
                        pw.println("Cellphone Number: " + Data.child("ContactNum").getValue());
                        pw.println("Alternative Number: " + Data.child("Alternative Number").getValue());
                        pw.println("Address: " + Data.child("Address").getValue());
                        pw.println("Address Information: " + Data.child("AdditionalInfo").getValue());
                        pw.println("");
                        getDriverData_Orderstb();

                    }

                } catch (IOException ex) {
                    Logger.getLogger(DriverReport.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "ERROR: " + fe);
            }
        });
    }

    public static void getDriverData_Orderstb() {

        tableRef = ref.child("Orders");// Go to specific Table]\

        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot Data : ds.getChildren()) {// entire database

                    for (DataSnapshot Data2 : Data.getChildren()) {

                        OrderID = "OrderID: " + Data.getKey();
                        FamilySize = "Family Size: " + Data.child("FamilySize").getValue();
                        pw.print(String.format(OrderID, FamilySize));                                           
                        

                    }

                }
                pw.close();

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "ERROR: " + fe);
            }
        });
    }

}
