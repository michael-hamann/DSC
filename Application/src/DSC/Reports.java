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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Aliens_Keanu
 */
public class Reports {

    public static String routeNumber = "";
    public static String route = "Route: ";
    public static XSSFWorkbook workbook = new XSSFWorkbook();
    public static XSSFSheet spreadsheet;
    public static XSSFRow row;
    public static XSSFCell cell;
    public static FileOutputStream out;
    public static int counter = 1;
    public static String array[] = new String[10];
    public static String name[] = new String[10];
    public static String week[] = new String[10];
    public static String standardSheet[] = {"Yes/No", "Name&Surname", "Contact", "EFT", "Cash", "Date", "MO", "Te", "We", "TH", "FR"};
    public static int iterate = 0;

    public static void createDriverReport() {

        Firebase tableRef = DBClass.getInstance().child("Routes");
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                spreadsheet = workbook.createSheet("DriverReport " + counter);
                counter++;

                for (DataSnapshot Data : ds.getChildren()) {
                    routeNumber = Data.getKey();
                    if (routeNumber != "0") {
                        for (DataSnapshot Data2 : Data.getChildren()) {
                            for (DataSnapshot Data3 : Data2.getChildren()) {
                                if (!Data2.getKey().equals("Suburbs")) {

                                    try {
                                        for (; iterate < 10; iterate++) {

                                            name[iterate] = "Driver: " + Data3.child("DriverName").getValue() + "";
                                            array[iterate] = route + routeNumber;
                                            week[iterate] = "Week: " + "1";

                                            for (int rows = 0; rows < 10; rows++) {

                                                row = spreadsheet.createRow(rows);

                                                for (int col = 0; col < 10; col++) {

                                                    if (col == 0 && rows == 0) {

                                                        cell = row.createCell(col);
                                                        cell.setCellValue(array[iterate]);

                                                    } else if (col == 3 && rows == 0) {

                                                        cell = row.createCell(col);
                                                        cell.setCellValue(name[iterate]);

                                                    } else if (col == 8 && rows == 0) {

                                                        cell = row.createCell(col);
                                                        cell.setCellValue(week[iterate]);

                                                    } else if (col == 0 && rows == 1) {

                                                        
                                                            for (int b = 0; b < 10; b++) {
                                                                cell = row.createCell(b);
                                                                cell.setCellValue(standardSheet[b]);
                                                            }

                                                        

                                                    }

                                                }
                                            }
                                        }

                                    } catch (Exception e) {

                                    }
                                }
                            }
                        }
                        try {
                            FileOutputStream out = new FileOutputStream(new File("DriverReport " + routeNumber + " " + counter + ".xlsx"));
                            workbook.write(out);
                            out.close();
                        } catch (FileNotFoundException ex) {
                            Logger.getLogger(DSC_Main.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(DSC_Main.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }

                }

            }

            //        
            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Error: " + fe.getMessage()); //To change body of generated methods, choose Tools | Templates.
            }
        });

    }

}
