/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import java.util.ArrayList;

/**
 *
 * @author Aliens_Keanu
 */
public class ChefReport {
//    public static ArrayList<String> getDriverReportData = new ArrayList();
//    public static void main(String[] args) {
//        for (int i = 0; i < 10; i++) {
//            getDriverReportData = DriverReport.getDriverData_Clientstb(getDriverReportData);
//            System.out.println(getDriverReportData.get(i));
//        }
//        
//    }
//    XSSFWorkbook workbook = new XSSFWorkbook();
//    XSSFSheet spreadsheet = workbook.createSheet(name);
//        
//    try {
//        FileOutputStream out = new FileOutputStream(new File(name + ".xlsx"));
//        workbook.write(out);
//    } catch (IOException e) {
//        JOptionPane.showMessageDialog(null, e.getMessage());
//    }
    
//    public static void getChefData_Ordertb() {

//        Firebase tableRef = DBClass.getInstance().child("Orders");// Go to specific Table
//        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot ds) {
//                try {
//
//                    PrintWriter pw = new PrintWriter(new FileWriter("ChefReport.txt"));
//
//                    for (DataSnapshot Data : ds.getChildren()) {//entire database
//
//                        for (DataSnapshot Data2 : Data.getChildren()) {//children of database 
//
//                            for (DataSnapshot Data3 : Data2.getChildren()) {//children of database table
//
//                                pw.println("OrderID: " + Data.getKey());
//                                pw.println("Quantity: " + Data3.child("Quantity").getValue());
//                                pw.println("Exclusions: " + Data3.child("Exclutions").getValue());
//                                pw.println("Allergy: " + Data3.child("Allergy").getValue());
//                                pw.println("");
//
//                            }
//
//                        }
//
//                    }
//                    pw.close();
//                } catch (IOException ex) {
//                    Logger.getLogger(ChefReport.class.getName()).log(Level.SEVERE, null, ex);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(FirebaseError fe) {
//                JOptionPane.showMessageDialog(null, "ERROR: " + fe);
//            }
//        });
//    }
}