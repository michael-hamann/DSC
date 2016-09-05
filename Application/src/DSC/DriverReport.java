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
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
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
   // public static ArrayList<String> getDriverReportData = new ArrayList();

    public static ArrayList<String> getDriverData_Clientstb(ArrayList<String> getDriverReportData) {

        tableRef = DBClass.getInstance().child("Clients");// Go to specific Table]\

        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                for (DataSnapshot Data : ds.getChildren()) {// entire database

                    getDriverReportData.add("Name: " + Data.child("Name").getValue());
                    getDriverReportData.add("Surname: " + Data.child("Surname").getValue());
                    getDriverReportData.add("Cellphone Number: " + Data.child("ContactNum").getValue());
                    getDriverReportData.add("Alternative Number: " + Data.child("Alternative Number").getValue());
                    getDriverReportData.add("Address: " + Data.child("Address").getValue());
                    getDriverReportData.add("Address Information: " + Data.child("AdditionalInfo").getValue());

                }
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "ERROR: " + fe);
            }
        });

        return getDriverReportData;
    }

    public static void getDriverData_Orderstb() {

        tableRef = DBClass.getInstance().child("Orders");// Go to specific Table]\

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
