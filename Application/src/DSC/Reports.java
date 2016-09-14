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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Aliens_Keanu
 */
public class Reports {

    private String routeNumber = "";
    private static String driverName = "";
    private String route = "Route: ";
    private static XSSFWorkbook workbook = new XSSFWorkbook();
    private XSSFSheet spreadsheet;
    private XSSFRow row;
    private XSSFCell cell;
    private static FileOutputStream out;
    private int counter = 1;
    private ArrayList<DriverReportData> driverData = new ArrayList<>();
    private String driverReportHead[] = new String[3];
    private CellStyle cs;
    private static int count = 1;
    private Cell cellObject;
    private Row rowObject;

    public void createDriverReport() {

        tableRef = DBClass.getInstance().child("Drivers");// Go to specific Table

        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                for (DataSnapshot Data : ds.getChildren()) {// entire database

                    driverName = (String) Data.child("DriverName").getValue();

                }

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "ERROR: " + fe);
            }
        });

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
                data.put("0", new Object[]{"Name", "Surname", "Contact", "Date Paid", "Mon", "Tue", "Wed", "Thurs", "Fri", "Address", "AdditionalInfo"});

                for (DataSnapshot Data : ds.getChildren()) {
                    routeNumber = Data.getKey();
                    if (routeNumber != "0") {

                        for (DataSnapshot Data2 : Data.getChildren()) {
                            for (DataSnapshot Data3 : Data2.getChildren()) {
                                if (!Data2.getKey().equals("Suburbs")) {

                                    for (int i = 0; i < driverData.size(); i++) {
                                        data.put((i + 1) + "", new Object[]{driverData.get(i).getName(), driverData.get(i).getSurname(), driverData.get(i).getContactNumber(), "", "", "", "", "", "", driverData.get(i).getAddress(), driverData.get(i).getAdditionalInfo()});
                                    }

                                    Set<String> keyset = data.keySet();
                                    int rownum = 1;

                                    for (String key : keyset) {

                                        rowObject = spreadsheet.createRow(rownum++);
                                        Object[] objArr = data.get(key);
                                        int cellnum = 0;
                                        for (Object obj : objArr) {

                                            cellObject = rowObject.createCell(cellnum++);
                                            if (obj instanceof String) {
                                                cellObject.setCellValue((String) obj);

                                            } else if (obj instanceof Integer) {
                                                cellObject.setCellValue((Integer) obj);
                                            }
                                        }

                                        for (short i = 0; i < 14; i++) {
                                            spreadsheet.autoSizeColumn(i);
                                        }

                                    }

                                }
                            }

                        }

                        rowObject = spreadsheet.createRow(0);
                        for (int i = 0; i < driverReportHead.length; i++) {
                            switch (i) {
                                case 0:
                                    driverReportHead[i] = "Route: " + routeNumber;
                                    break;
                                case 1:
                                    driverReportHead[i] = "Driver: " + driverName;
                                    break;
                                default:
                                    driverReportHead[i] = "Week: " + counter;
                                    break;
                            }
                            cellObject = rowObject.createCell(i);
                            cellObject.setCellValue(driverReportHead[i]);

                        }

                        createExcelSheets();
                        count++;

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

    public static void createExcelSheets() {

        try {
            out = new FileOutputStream(new File("DriverReport Route - " + count + ".xlsx"));
            workbook.write(out);
            out.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Reports.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
