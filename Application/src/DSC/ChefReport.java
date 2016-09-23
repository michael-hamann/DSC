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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Aliens_Keanu
 */
public class ChefReport {

    private static ArrayList<Chef> allOrders = new ArrayList();
    private static ArrayList standardOrders = new ArrayList();
    private static ArrayList lowCarbOrders = new ArrayList();
    private static ArrayList kiddiesOrders = new ArrayList();
    private static int counter = 0;
    private static int counterName = 0;
    private static XSSFWorkbook workbook = new XSSFWorkbook();
    private static String r = "";
    //private static Map<String, String[]> data;

    public static void getQuanity() {
        Firebase ref = DBClass.getInstance().child("Orders");
        ref.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                for (DataSnapshot levelOne : ds.getChildren()) {
                    for (DataSnapshot levelTwo : levelOne.getChildren()) {
                        for (DataSnapshot levelThree : levelTwo.getChildren()) {

                            if (levelOne.child("Active").getValue(boolean.class).equals(true)) {

                                allOrders.add(new Chef(levelThree.child("Quantity").getValue(String.class),
                                        levelThree.child("Allergies").getValue(String.class),
                                        levelThree.child("Exclusions").getValue(String.class),
                                        levelOne.child("RouteID").getValue(String.class),
                                        levelThree.child("MealType").getValue(String.class)
                                ));

                            }
                        }
                    }

                }
                Map<String, String[]> data = new TreeMap<>();
                Map<String, String[]> data2 = new TreeMap<>();
                Map<String, String[]> data3 = new TreeMap<>();
                for (Chef allOrder : allOrders) {
                    String arr[] = new String[100];
                    arr[counterName] = allOrder.getRoute();
                    if (allOrder.getRoute().equals("0") || allOrder.getRoute().equals("1") || allOrder.getRoute().equals("2") && allOrder.getMealType().equals("Standard")) {
                        standardOrders.add(allOrder.getMealType());
                        data.put(counter + "", new String[]{allOrder.getQuantity(), allOrder.getAllergies(), allOrder.getExclusions(), allOrder.getRoute()});
                        counter++;
                    }

                    if (allOrder.getRoute().equals("0") || allOrder.getRoute().equals("1") || allOrder.getRoute().equals("2") && allOrder.getMealType().equals("Low Carb")) {
                        lowCarbOrders.add(allOrder.getMealType());
                        data2.put(counter + "", new String[]{allOrder.getQuantity(), allOrder.getAllergies(), allOrder.getExclusions(), allOrder.getRoute()});
                        counter++;
                    }

                    if (allOrder.getRoute().equals("0") || allOrder.getRoute().equals("1") || allOrder.getRoute().equals("2") && allOrder.getMealType().equals("Kiddies")) {
                        kiddiesOrders.add(allOrder.getMealType());
                        data3.put(counter + "", new String[]{allOrder.getQuantity(), allOrder.getAllergies(), allOrder.getExclusions(), allOrder.getRoute()});
                        counter++;
                    }
                }

                Object[] maps = {data, data2, data3};
                for (int count = 0; count < 3; count++) {

                    XSSFSheet sheet = workbook.createSheet("ChefReports Route - " + counterName);
                    int rowNum = 0;
                    int cellNum = 0;
                    Set<String> keySet = ((Map) maps[count]).keySet();

                    for (String key : keySet) {
                        Row row = sheet.createRow(rowNum);
                        Object[] arr = (String[]) ((Map) maps[count]).get(key);
                        for (int i = 0; i < arr.length; i++) {
                            Cell cell = row.createCell(i);
                            cell.setCellValue((String) arr[i]);
                        }

                        rowNum++;
                        cellNum++;
                    }
                    counterName++;
                }

                try {
                    creatSheet(r);
                } catch (IOException ex) {
                    Logger.getLogger(ChefReport.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            @Override
            public void onCancelled(FirebaseError fe
            ) {
                System.err.print("Error: Could not get Clients: " + fe.getMessage());
            }

        }
        );

    }

    public static void creatSheet(String r) throws IOException {
        FileOutputStream excelOut = null;
        try {
            File file = new File("ChefReports Route - " + r + ".xlsx");
            excelOut = new FileOutputStream(file);
            workbook.write(excelOut);
            excelOut.close();
            System.out.println("DONE");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChefReport.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                excelOut.close();
            } catch (IOException ex) {
                Logger.getLogger(ChefReport.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
