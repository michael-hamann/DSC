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
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Aliens_Keanu
 */
public class Reports {

    private static String routeNumber = "";
    private static String route = "Route: ";
    private static XSSFWorkbook workbook = new XSSFWorkbook();
    private static XSSFSheet spreadsheet;
    private static XSSFRow row;
    private static XSSFCell cell;
    private static FileOutputStream out;
    private static int counter = 1;
    private static ArrayList<String> setDriverName = new ArrayList();
    private static ArrayList<String> setRouteNumber = new ArrayList();
    private static ArrayList<String> setWeekNumber = new ArrayList();
    private static ArrayList<String> getDriverReportData = new ArrayList();
    private static ArrayList<DriverReportData> driverData = new ArrayList<>();
    private static String standardSheet[] = {"Yes/No", "Name", "Surname", "Contact", "EFT", "Cash", "Date Paid", "Mon", "Tue", "Wed", "Thurs", "Fri", "Address", "AdditionalInfo"};
    private static int iterate = 0;
    private static CellStyle cs;
    private static int count = 0;

    public static void createDriverReport() {

        tableRef = DBClass.getInstance().child("Clients");// Go to specific Table]\

        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                for (DataSnapshot Data : ds.getChildren()) {// entire database

                    DriverReportData driverObj = new DriverReportData();
                    driverObj.setName((String) Data.child("Name").getValue());
                    driverObj.setSurname((String) Data.child("Surname").getValue());
                    driverObj.setContactNumber((String) Data.child("ContactNum").getValue());
                    driverObj.setAddress((String) Data.child("Address").getValue());
                    driverObj.setAdditionalInfo((String) Data.child("AdditionalInfo").getValue());
                    driverData.add(driverObj);

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
                Map<String, Object[]> data = new TreeMap<String, Object[]>();
                data.put("0", new Object[]{"Y/N", "Name", "Surname", "Contact", "EFT", "Cash", "Date Paid", "Mon", "Tue", "Wed", "Thurs", "Fri", "Address", "AdditionalInfo"});
                for (DataSnapshot Data : ds.getChildren()) {
                    routeNumber = Data.getKey();
                    if (routeNumber != "0") {
                        for (DataSnapshot Data2 : Data.getChildren()) {
                            for (DataSnapshot Data3 : Data2.getChildren()) {
                                if (!Data2.getKey().equals("Suburbs")) {
                                    try {
                                        
                                        cs = workbook.createCellStyle();
                                        cs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
                                        cs.setBorderTop(XSSFCellStyle.BORDER_THIN);
                                        
                                        
           
                                        for (int i = 0; i < driverData.size(); i++) {
                                            data.put((i + 1) + "", new Object[]{"", driverData.get(i).getName(), driverData.get(i).getSurname(), driverData.get(i).getContactNumber(), "", "", "", "", "", "", "", "", driverData.get(i).getAddress(), driverData.get(i).getAdditionalInfo()});
                                        }
                                        Set<String> keyset = data.keySet();
                                        int rownum = 0;
                                        for (String key : keyset) {
                                            Row row = spreadsheet.createRow(rownum++);
                                            Object[] objArr = data.get(key);
                                            int cellnum = 0;
                                            for (Object obj : objArr) {

                                                Cell cell = row.createCell(cellnum++);
                                                if (obj instanceof String) {
                                                    cell.setCellValue((String) obj);
                                                } else if (obj instanceof Integer) {
                                                    cell.setCellValue((Integer) obj);
                                                }
                                            }

                                            for (short i = 0; i < 14; i++) {
                                                spreadsheet.autoSizeColumn(i);
                                            }

                                        }

                                    } catch (Exception e) {

                                    }
                                }
                            }
                        }
                        try {
                            FileOutputStream out = new FileOutputStream(new File("DriverReport .xlsx"));
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

            @Override
            public void onCancelled(FirebaseError fe
            ) {
                throw new UnsupportedOperationException("Error: " + fe.getMessage()); //To change body of generated methods, choose Tools | Templates.
            }
        }
        );

    }

}
