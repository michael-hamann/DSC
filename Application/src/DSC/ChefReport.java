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
    private static ArrayList<String> allRoutes = new ArrayList();
    private static int counter = 0;
    private static XSSFWorkbook workbook;
    //private static Map<String, String[]> data;

    public static void getQuanity() {
        Firebase newRef = DBClass.getInstance().child("Routes");
        newRef.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                for (DataSnapshot levelOne : ds.getChildren()) {
                    allRoutes.add(levelOne.getKey());

                }

            }

            @Override
            public void onCancelled(FirebaseError fe
            ) {
                System.err.print("Error: Could not get Clients: " + fe.getMessage());
            }

        }
        );

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
                                        levelThree.child("MealType").getValue(String.class),
                                        levelOne.child("FamilySize").getValue(String.class)
                                ));

                            }
                        }
                    }

                }

                String list[] = {"Standard", "Low Carb", "Kiddies"};
                String familySizes[] = {"1", "2", "3", "4", "5", "6"};

                int excelNumber = 0;

                for (int numberOfRoutes = 0; numberOfRoutes < allRoutes.size(); numberOfRoutes++) {
                    int sheetNumber = 0;

                    workbook = new XSSFWorkbook();
                    for (String currRoute : allRoutes) {
                        int bulkCount = 0; //
                        Map<String, String[]> data = new TreeMap<>();
                        Map<String, String[]> bulk = new TreeMap<>();
                        XSSFSheet sheet = workbook.createSheet("ChefReports Route - " + sheetNumber);
                        int rowNum = 0;
                        int cellNum = 0;
                        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// Sort According Family Size !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
                        //  for (Chef allOrder : allOrders) {
                        for (int ordersCount = 0; ordersCount < allOrders.size(); ordersCount++) {
                            for (int famCount = 0; famCount < 6; famCount++) { /////// temporary loop /  figure it out :)

                                if (allOrders.get(ordersCount).getRoute().equals(currRoute) && allOrders.get(ordersCount).getMealType().equals(list[numberOfRoutes]) && allOrders.get(ordersCount).getFamilySize().equals(familySizes[famCount])) {
                                    if (allOrders.get(ordersCount).getAllergies().equals("-") && allOrders.get(ordersCount).getAllergies().equals("-") && allOrders.get(ordersCount).getFamilySize().equals(familySizes[famCount])) {
                                        bulkCount++;
                                    } else {
                                        data.put(counter + "", new String[]{"FS:" + allOrders.get(ordersCount).getFamilySize(), allOrders.get(ordersCount).getQuantity(), allOrders.get(ordersCount).getAllergies(), allOrders.get(ordersCount).getExclusions(), allOrders.get(ordersCount).getRoute(), allOrders.get(ordersCount).getMealType()});
                                        counter++;
                                    }

                                }

                                if (ordersCount == allOrders.size() - 1) {
                                    data.put(counter++ + "", new String[]{bulkCount + "", "BULK", "BULK"});
                                }
                            }

                        }

                        // }
                        Set<String> keySet = data.keySet();
                        ArrayList<String> keyList = new ArrayList();
                        for (String keys : keySet) {
                            keyList.add(keys);
                        }

                        for (int keyIterate = 0; keyIterate < keySet.size(); keyIterate++) {

                            Row row = sheet.createRow(rowNum);
                            Object[] arr = data.get(keyList.get(keyIterate));
                            for (int i = 0; i < arr.length; i++) {
                                Cell cell = row.createCell(i);
                                cell.setCellValue((String) arr[i]);
                            }

                            rowNum++;
                            cellNum++;

                        }

                        sheetNumber++;
                    }
                    try {
                        creatSheet(excelNumber + "", list[numberOfRoutes]);
                    } catch (IOException ex) {
                        Logger.getLogger(ChefReport.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    excelNumber++;
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

    public static void creatSheet(String excelNumber, String mealType) throws IOException {
        FileOutputStream excelOut = null;
        try {
            File file = new File("ChefReports Route - " + excelNumber + " ( " + mealType + " )" + ".xlsx");
            excelOut = new FileOutputStream(file);
            workbook.write(excelOut);
            excelOut.close();
            System.out.println("DONE");

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ChefReport.class
                    .getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                excelOut.close();

            } catch (IOException ex) {
                Logger.getLogger(ChefReport.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}
