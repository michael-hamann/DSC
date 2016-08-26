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
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Aliens_Keanu
 */
public class ChefReport {

//    XSSFWorkbook workbook = new XSSFWorkbook();
//    XSSFSheet spreadsheet = workbook.createSheet(name);
//        
//    try {
//        FileOutputStream out = new FileOutputStream(new File(name + ".xlsx"));
//        workbook.write(out);
//    } catch (IOException e) {
//        JOptionPane.showMessageDialog(null, e.getMessage());
//    }
    
    public static void getChefData_Ordertb() {

        Firebase tableRef = ref.child("Orders");// Go to specific Table
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                try {

                    PrintWriter pw = new PrintWriter(new FileWriter("ChefReport.txt"));

                    for (DataSnapshot Data : ds.getChildren()) {//entire database

                        for (DataSnapshot Data2 : Data.getChildren()) {//children of database 

                            for (DataSnapshot Data3 : Data2.getChildren()) {//children of database table

                                pw.println("OrderID: " + Data.getKey());
                                pw.println("Quantity: " + Data3.child("Quantity").getValue());
                                pw.println("Exclusions: " + Data3.child("Exclutions").getValue());
                                pw.println("Allergy: " + Data3.child("Allergy").getValue());
                                pw.println("");

                            }

                        }

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
