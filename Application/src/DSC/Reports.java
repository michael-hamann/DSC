/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import static DSC.DriverReport.tableRef;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.CellStyle;
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
    public static ArrayList<String> setDriverName = new ArrayList();
    public static ArrayList<String> setRouteNumber = new ArrayList();
    public static ArrayList<String> setWeekNumber = new ArrayList();
    public static ArrayList<String> getDriverReportData = new ArrayList();
    ArrayList<DriverReportData> driverData = new ArrayList<>();
    public static String standardSheet[] = {"Yes/No", "Name", "Surname", "Contact", "EFT", "Cash", "Date", "Mon", "Tue", "Wed", "Thurs", "Fri", "Address", "AdditionalInfo"};
    public static int iterate = 0;
    public static CellStyle cs;

    public static void createDriverReport() {

        tableRef = DBClass.getInstance().child("Clients");// Go to specific Table]\

        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                for (DataSnapshot Data : ds.getChildren()) {// entire database
                    
                    DriverReportData driverData =  new DriverReportData();
                    driverData.setName((String) Data.child("Name").getValue());
                    driverData.setSurname((String) Data.child("Surname").getValue());
                    driverData.setContactNumber((String) Data.child("Address").getValue());
                    driverData.setAddress((String) Data.child("Address").getValue());
                    driverData.setAdditionalInfo((String) Data.child("AdditionalInfo").getValue());
                    
                }

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "ERROR: " + fe);
            }
        });

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

                                            setDriverName.add("Driver: " + Data3.child("DriverName").getValue() + "");
                                            setRouteNumber.add(route + routeNumber);
                                            setWeekNumber.add("Week: " + "1");

                                            for (int rows = 0; rows < 10; rows++) {
                                                row = spreadsheet.createRow(rows);
                                                for (int col = 0; col < 10; col++) {
                                                    if (col == 0 && rows == 0) {
                                                        cell = row.createCell(col);
                                                        cell.setCellValue(setRouteNumber.get(iterate));
                                                    } else if (col == 3 && rows == 0) {
                                                        cell = row.createCell(col);
                                                        cell.setCellValue(setDriverName.get(iterate));
                                                    } else if (col == 8 && rows == 0) {
                                                        cell = row.createCell(col);
                                                        cell.setCellValue(setWeekNumber.get(iterate));
                                                    } else if (col == 0 && rows == 1) {
                                                        for (int b = 0; b < standardSheet.length; b++) {
                                                            cell = row.createCell(b);
                                                            cell.setCellValue(standardSheet[b]);
                                                        }
                                                    } else if (col > 2 && rows > 2) {

                                                        cs = workbook.createCellStyle();
                                                        cs.setWrapText(true);

                                                        cell.setCellStyle(cs);

                                                        row.setHeightInPoints((2 * spreadsheet.getDefaultRowHeightInPoints()));

                                                        spreadsheet.autoSizeColumn((short) 2);
                                                        int count = 1;
                                                        for (int arrayListCount = 0; arrayListCount < getDriverReportData.size(); arrayListCount++) {
                                                            String values = getDriverReportData.get(arrayListCount);

                                                            System.out.println(values);
                                                            cell = row.createCell(count);
                                                            cell.setCellValue(values);

                                                            if (count > 4) {
                                                                cell.setCellValue("\n");
                                                            }

                                                            count++;

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
