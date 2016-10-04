/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import static DSC.ChefReport.allRoutes;
import static DSC.ChefReport.creatSheet;
import static DSC.ChefReport.currentWeek;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import static java.util.Collections.list;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Aliens_Keanu
 */
public class QuantityReport {
    // all active meals counter's
    private static int countStandardActive;
    private static int countKiddiesActive;
    private static int countLowCarbActive;
    // total active cliens/orders
    private static int activeClientCount;
    // total of each family size
    private static int countFamilySize_1;
    private static int countFamilySize_2;
    private static int countFamilySize_3;
    private static int countFamilySize_4;
    private static int countFamilySize_5;
    private static int countFamilySize_6;
    private static int countFamilySizeMoreThanSix;
    // count family size per standard meal type
    private static int countFamSize1_Standard;
    private static int countFamSize2_Standard;
    private static int countFamSize3_Standard;
    private static int countFamSize4_Standard;
    private static int countFamSize5_Standard;
    private static int countFamSize6_Standard;
    private static int countFamilySizeMoreThanSix_Standard;
    // count family size per low carb meal type
    private static int countFamSize1_LC;
    private static int countFamSize2_LC;
    private static int countFamSize3_LC;
    private static int countFamSize4_LC;
    private static int countFamSize5_LC;
    private static int countFamSize6_LC;
    private static int countFamilySizeMoreThanSix_LC;
    // count family size per kiddies meal type
    private static int countFamSize1_KD;
    private static int countFamSize2_KD;
    private static int countFamSize3_KD;
    private static int countFamSize4_KD;
    private static int countFamSize5_KD;
    private static int countFamSize6_KD;
    private static int countFamilySizeMoreThanSix_KD;
    // final variables
    private static final String STANDARD = "Standard";
    private static final String LOW_CARB = "Low Carb";
    private static final String KIDDIES = "Kiddies";

    private static QuantityReportData quantityReportData;
    private static XSSFWorkbook workbook;

    public static void getActiveClients() {
        Firebase newRef = DBClass.getInstance().child("Orders");
        newRef.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot levelOne : ds.getChildren()) {
                    boolean activeCheck = levelOne.child("Active").getValue(boolean.class).equals(true);
                    if (activeCheck) {
                        activeClientCount++;
                        int familySizeCheck;
                        for (DataSnapshot levelTwo : levelOne.getChildren()) {
                            familySizeCheck = levelTwo.child("FamilySize").getValue(int.class);// gets family size from database
                            if (familySizeCheck == 1) {
                                countFamilySize_1++;
                            }
                            if (familySizeCheck == 2) {
                                countFamilySize_2++;
                            }
                            if (familySizeCheck == 3) {
                                countFamilySize_3++;
                            }
                            if (familySizeCheck == 4) {
                                countFamilySize_4++;
                            }
                            if (familySizeCheck == 5) {
                                countFamilySize_5++;
                            }
                            if (familySizeCheck == 6) {
                                countFamilySize_6++;
                            }

                            if (activeCheck == true && familySizeCheck > 6) {
                                countFamilySizeMoreThanSix++;
                            }

                            for (DataSnapshot levelThree : levelTwo.getChildren()) {
                                String mealType = levelThree.child("MealType").getValue(String.class);
                                if (activeCheck == true && mealType.equals(STANDARD)) {
                                    countStandardActive++;
                                }
                                if (activeCheck == true && mealType.equals(LOW_CARB)) {
                                    countLowCarbActive++;
                                }
                                if (activeCheck == true && mealType.equals(KIDDIES)) {
                                    countKiddiesActive++;
                                }

                                if (familySizeCheck == 1 && mealType.equals(STANDARD)) {
                                    countFamSize1_Standard++;
                                }

                                if (familySizeCheck == 2 && mealType.equals(STANDARD)) {
                                    countFamSize2_Standard++;
                                }

                                if (familySizeCheck == 3 && mealType.equals(STANDARD)) {
                                    countFamSize3_Standard++;
                                }

                                if (familySizeCheck == 4 && mealType.equals(STANDARD)) {
                                    countFamSize4_Standard++;
                                }

                                if (familySizeCheck == 5 && mealType.equals(STANDARD)) {
                                    countFamSize5_Standard++;
                                }

                                if (familySizeCheck == 6 && mealType.equals(STANDARD)) {
                                    countFamSize6_Standard++;
                                }

                                if (familySizeCheck > 6 && mealType.equals(STANDARD)) {
                                    countFamilySizeMoreThanSix_Standard++;
                                }

                                // Low Carb
                                
                                if (familySizeCheck == 1 && mealType.equals(LOW_CARB)) {
                                    countFamSize1_LC++;
                                }

                                if (familySizeCheck == 2 && mealType.equals(LOW_CARB)) {
                                    countFamSize2_LC++;
                                }

                                if (familySizeCheck == 3 && mealType.equals(LOW_CARB)) {
                                    countFamSize3_LC++;
                                }

                                if (familySizeCheck == 4 && mealType.equals(LOW_CARB)) {
                                    countFamSize4_LC++;
                                }

                                if (familySizeCheck == 5 && mealType.equals(LOW_CARB)) {
                                    countFamSize5_LC++;
                                }

                                if (familySizeCheck == 6 && mealType.equals(LOW_CARB)) {
                                    countFamSize6_LC++;
                                }

                                if (familySizeCheck > 6 && mealType.equals(LOW_CARB)) {
                                    countFamilySizeMoreThanSix_LC++;
                                }
                                
                                // Kiddies
                                
                                if (familySizeCheck == 1 && mealType.equals(KIDDIES)) {
                                    countFamSize1_KD++;
                                }

                                if (familySizeCheck == 2 && mealType.equals(KIDDIES)) {
                                    countFamSize2_KD++;
                                }

                                if (familySizeCheck == 3 && mealType.equals(KIDDIES)) {
                                    countFamSize3_KD++;
                                }

                                if (familySizeCheck == 4 && mealType.equals(KIDDIES)) {
                                    countFamSize4_KD++;
                                }

                                if (familySizeCheck == 5 && mealType.equals(KIDDIES)) {
                                    countFamSize5_KD++;
                                }

                                if (familySizeCheck == 6 && mealType.equals(KIDDIES)) {
                                    countFamSize6_KD++;
                                }

                                if (familySizeCheck > 6 && mealType.equals(KIDDIES)) {
                                   countFamilySizeMoreThanSix_KD++;
                                }
                                
                                

                            }
                        }
                    }
                }

            }
            
            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.print("Error: Could not get Orders: " + fe.getMessage());
            }
        }
        );
    }

    public static void processQuantityReport() {
        int excelNumber = 0;
        int sheetNumber = 0;
        workbook = new XSSFWorkbook();
        Map<String, Object[]> data = new TreeMap<>();
        data.put(0 + "", new String[]{"Doorstep Chef - Chef Report " + currentWeek(), "", "", "Meal Type : " + "" + " " + " " + "Route: " + sheetNumber});
        data.put(1 + "", new String[]{"", "", "", "", "", "", ""});
        data.put(2 + "", new String[]{"Family Size", "Quantity", "Allergies", "Exclusions"});

        XSSFSheet sheet = workbook.createSheet("ChefReports Route - " + sheetNumber);
        int rowNum = 0;
        int cellNum = 0;

        data.put("", new String[]{"" + " Normal *", "" + "", "", ""});
        Set<String> keySet = data.keySet();
        Object[] keys = data.keySet().toArray();
        Arrays.sort(keys);
        ArrayList<Object> keyList = new ArrayList();
        int totalWidth = 50000;

        for (Object key : keys) {
            keyList.add(data.get(key));
        }

        for (int keyIterate = 0; keyIterate < keySet.size(); keyIterate++) {
            Row row = sheet.createRow(rowNum);
            Object[] arr = data.get(keyIterate + "");

            for (int i = 0; i < arr.length; i++) {

                Cell cell = row.createCell(i);
                cell.setCellValue((String) arr[i]);

                rowNum++;
                cellNum++;

            }
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));

            for (int i = 0; i < 5; i++) {
                if (i == 1) {
                    sheet.setColumnWidth(i, 3000);
                } else if (i == 2) {
                    sheet.setColumnWidth(i, 8000);
                } else {
                    sheet.setColumnWidth(i, 4000);
                }

                if (i == 3) {
                    sheet.setColumnWidth(i, 8000);
                }

            }

            sheetNumber++;
        }

        try {
            creatSheet("", workbook);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "File Could Not Be Found.");
        }

        excelNumber++;

    }

}
