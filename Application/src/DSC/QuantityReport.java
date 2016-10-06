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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Aliens_Keanu
 */
public class QuantityReport {

    // final variables
    private static final String STANDARD = "Standard";
    private static final String LOW_CARB = "Low Carb";
    private static final String KIDDIES = "Kiddies";

    private static QuantityReportData quantityReportData;
    private static XSSFWorkbook workbook;
    private static QuantityReportData quantityObj = new QuantityReportData();
    private static int totalIndividuals = 0;
    private static int totalClients = 0;
    private static int totalSingle = 0;
    private static int totalCouple = 0;
    private static int totalThree = 0;
    private static int totalFour = 0;
    private static int totalFive = 0;
    private static int totalSix = 0;
    private static int totalExtra = 0;

    public static void getActiveClients() {
        Firebase newRef = DBClass.getInstance().child("Orders");
        newRef.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot levelOne : ds.getChildren()) {

                    Calendar start = null;

                    start = Calendar.getInstance();
                    start.setTimeInMillis(levelOne.child("StartingDate").getValue(long.class));

                    if (start.getTimeInMillis() > DriverReport.returnWeekMili()) {
                        continue;
                    }

                    boolean activeCheck = levelOne.child("Active").getValue(boolean.class).equals(true);
                    if (activeCheck) {
                        quantityObj.incrementActiveClientCount();
                        int familySizeCheck;
                        int quantity;
                        familySizeCheck = levelOne.child("FamilySize").getValue(int.class);// gets family size from database
                        totalIndividuals += levelOne.child("FamilySize").getValue(int.class);
                        for (DataSnapshot levelTwo : levelOne.getChildren()) {

                            for (DataSnapshot levelThree : levelTwo.getChildren()) {
                                String mealType = levelThree.child("MealType").getValue(String.class);
                                // Standard Totals
                                if (familySizeCheck == 1 && mealType.equals(STANDARD)) {
                                    quantityObj.incrementCountFamSize1_Standard();
                                } else if (familySizeCheck == 2 && mealType.equals(STANDARD)) {
                                    quantityObj.incrementCountFamSize2_Standard();
                                } else if (familySizeCheck == 3 && mealType.equals(STANDARD)) {
                                    quantityObj.incrementCountFamSize3_Standard();
                                } else if (familySizeCheck == 4 && mealType.equals(STANDARD)) {
                                    quantityObj.incrementCountFamSize4_Standard();
                                } else if (familySizeCheck == 5 && mealType.equals(STANDARD)) {
                                    quantityObj.incrementCountFamSize5_Standard();
                                } else if (familySizeCheck == 6 && mealType.equals(STANDARD)) {
                                    quantityObj.incrementCountFamSize6_Standard();
                                } else if (familySizeCheck > 6 && mealType.equals(STANDARD)) {
                                    quantityObj.incrementCountFamilySizeMoreThanSix_Standard();
                                }

                                // Low Carb Totals
                                if (familySizeCheck == 1 && mealType.equals(LOW_CARB)) {
                                    quantityObj.incrementCountFamSize1_LC();
                                } else if (familySizeCheck == 2 && mealType.equals(LOW_CARB)) {
                                    quantityObj.incrementCountFamSize2_LC();
                                } else if (familySizeCheck == 3 && mealType.equals(LOW_CARB)) {
                                    quantityObj.incrementCountFamSize3_LC();
                                } else if (familySizeCheck == 4 && mealType.equals(LOW_CARB)) {
                                    quantityObj.incrementCountFamSize4_LC();
                                } else if (familySizeCheck == 5 && mealType.equals(LOW_CARB)) {
                                    quantityObj.incrementCountFamSize5_LC();
                                } else if (familySizeCheck == 6 && mealType.equals(LOW_CARB)) {
                                    quantityObj.incrementCountFamSize6_LC();
                                } else if (familySizeCheck > 6 && mealType.equals(LOW_CARB)) {
                                    quantityObj.incrementCountFamilySizeMoreThanSix_LC();
                                }

                                // Kiddies Totals
                                if (familySizeCheck == 1 && mealType.equals(KIDDIES)) {
                                    quantityObj.incrementCountFamSize1_KD();
                                } else if (familySizeCheck == 2 && mealType.equals(KIDDIES)) {
                                    quantityObj.incrementCountFamSize2_KD();
                                } else if (familySizeCheck == 3 && mealType.equals(KIDDIES)) {
                                    quantityObj.incrementCountFamSize3_KD();
                                } else if (familySizeCheck == 4 && mealType.equals(KIDDIES)) {
                                    quantityObj.incrementCountFamSize4_KD();
                                } else if (familySizeCheck == 5 && mealType.equals(KIDDIES)) {
                                    quantityObj.incrementCountFamSize5_KD();
                                } else if (familySizeCheck == 6 && mealType.equals(KIDDIES)) {
                                    quantityObj.incrementCountFamSize6_KD();
                                } else if (familySizeCheck > 6 && mealType.equals(KIDDIES)) {
                                    quantityObj.incrementCountFamilySizeMoreThanSix_KD();
                                }
                                quantity = levelThree.child("Quantity").getValue(int.class);

                                if (mealType.equals(STANDARD)) {
                                    quantityObj.incrementCountStandardActive(quantity);
                                } else if (mealType.equals(LOW_CARB)) {
                                    quantityObj.incrementCountLowCarbActive(quantity);
                                } else if (mealType.equals(KIDDIES)) {
                                    quantityObj.incrementCountKiddiesActive(quantity);
                                }

                                // Quantity Totals
                                if (quantity == 1) {
                                    quantityObj.incrementQuantityFamSize1();
                                } else if (quantity == 2) {
                                    quantityObj.incrementQuantityFamSize2();
                                } else if (quantity == 3) {
                                    quantityObj.incrementQuantityFamSize3();
                                } else if (quantity == 4) {
                                    quantityObj.incrementQuantityFamSize4();
                                } else if (quantity == 5) {
                                    quantityObj.incrementQuantityFamSize5();
                                } else if (quantity == 6) {
                                    quantityObj.incrementQuantityFamSize6();
                                } else if (quantity > 6) {
                                    quantityObj.incrementQuantityFamSizeMoreThanSix();
                                }

                                ///////////////
                                if (activeCheck == true && familySizeCheck == 1 && quantity == 1) {
                                    quantityObj.incrementCountFamilySize_1();
                                } else if (activeCheck == true && familySizeCheck == 2 && quantity == 1) {
                                    quantityObj.incrementCountFamilySize_2();
                                } else if (activeCheck == true && familySizeCheck == 3 && quantity == 2) {
                                    quantityObj.incrementCountFamilySize_3();
                                } else if (activeCheck == true && familySizeCheck == 4 && quantity == 3) {
                                    quantityObj.incrementCountFamilySize_4();
                                } else if (activeCheck == true && familySizeCheck == 5 && quantity == 4) {
                                    quantityObj.incrementCountFamilySize_5();
                                } else if (activeCheck == true && familySizeCheck == 6 && quantity == 5) {
                                    quantityObj.incrementCountFamilySize_6();
                                } else if (activeCheck == true && familySizeCheck > 6 && quantity > 6) {
                                    quantityObj.incrementCountFamilySizeMoreThanSix();
                                }
                            }
                        }
                    }
                }

                processQuantityReport();
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
        Map<String, String[]> data = new TreeMap<>();
        data.put(0 + "", new String[]{"Doorstep Chef - Quantity Report " + currentWeek(), "", "", "Meal Type : " + "" + " " + " "});
        data.put(1 + "", new String[]{"", "", "", "", "", "", ""});
        data.put(2 + "", new String[]{"Total Of ", "Single", "Couple", "Three", "Four", "Five", "Six", "Extra"});

        XSSFSheet sheet = workbook.createSheet("Quantity Report " + sheetNumber);
        int rowNum = 0;
        int cellNum = 0;

        for (int i = 3; i < 19; i++) {
            if (i == 3) {
                data.put(i + "", new String[]{"Orders", quantityObj.getCountFamilySize_1() + "", quantityObj.getCountFamilySize_2() + "", quantityObj.getCountFamilySize_3() + "",
                    quantityObj.getCountFamilySize_4() + "", quantityObj.getCountFamilySize_5() + "", quantityObj.getCountFamilySize_6() + "", quantityObj.getCountFamilySizeMoreThanSix() + ""});
            } else if (i == 4) {
                data.put(i + "", new String[]{"", "", "", "", "", "", "", ""});
            } else if (i == 5) {
                data.put(i + "", new String[]{"Meals", quantityObj.getQuantityFamSize1() + "", quantityObj.getQuantityFamSize2() + "", quantityObj.getQuantityFamSize3() + "",
                    quantityObj.getQuantityFamSize4() + "", quantityObj.getQuantityFamSize5() + "", quantityObj.getQuantityFamSize6() + "", quantityObj.getQuantityFamSizeMoreThanSix() + ""});
            } else if (i == 6) {
                data.put(i + "", new String[]{"", "", "", "", "", "", "", ""});
            } else if (i == 7) {
                data.put(i + "", new String[]{"Standard Meals", quantityObj.getCountFamSize1_Standard() + "", quantityObj.getCountFamSize2_Standard() + "", quantityObj.getCountFamSize3_Standard() + "",
                    quantityObj.getCountFamSize4_Standard() + "", quantityObj.getCountFamSize5_Standard() + "", quantityObj.getCountFamSize6_Standard() + "", quantityObj.getCountFamilySizeMoreThanSix_Standard() + ""});
            } else if (i == 8) {
                data.put(i + "", new String[]{"Low Carb Meals", quantityObj.getCountFamSize1_LC() + "", quantityObj.getCountFamSize2_LC() + "", quantityObj.getCountFamSize3_LC() + "",
                    quantityObj.getCountFamSize4_LC() + "", quantityObj.getCountFamSize5_LC() + "", quantityObj.getCountFamSize6_LC() + "", quantityObj.getCountFamilySizeMoreThanSix_LC() + ""});
            } else if (i == 9) {
                data.put(i + "", new String[]{"Kiddies Meals", quantityObj.getCountFamSize1_KD() + "", quantityObj.getCountFamSize2_KD() + "", quantityObj.getCountFamSize3_KD() + "",
                    quantityObj.getCountFamSize4_KD() + "", quantityObj.getCountFamSize5_KD() + "", quantityObj.getCountFamSize6_KD() + "", quantityObj.getCountFamilySizeMoreThanSix_KD() + ""});
            } else if (i == 10) {
                data.put(i + "", new String[]{"", "", "", "", "", "", "", ""});
            } else if (i == 11) {
                totalSingle = quantityObj.getCountFamSize1_Standard() + quantityObj.getCountFamSize1_LC() + quantityObj.getCountFamSize1_KD();
                totalCouple = quantityObj.getCountFamSize2_Standard() + quantityObj.getCountFamSize2_LC() + quantityObj.getCountFamSize2_KD();
                totalThree = quantityObj.getCountFamSize3_Standard() + quantityObj.getCountFamSize3_LC() + quantityObj.getCountFamSize3_KD();
                totalFour = quantityObj.getCountFamSize4_Standard() + quantityObj.getCountFamSize4_LC() + quantityObj.getCountFamSize4_KD();
                totalFive = quantityObj.getCountFamSize5_Standard() + quantityObj.getCountFamSize5_LC() + quantityObj.getCountFamSize5_KD();
                totalSix = quantityObj.getCountFamSize6_Standard() + quantityObj.getCountFamSize6_LC() + quantityObj.getCountFamSize6_KD();
                totalExtra = quantityObj.getCountFamilySizeMoreThanSix_Standard() + quantityObj.getCountFamilySizeMoreThanSix_LC() + quantityObj.getCountFamilySizeMoreThanSix_KD();
                data.put(i + "", new String[]{"Totals", totalSingle + "", totalCouple + "", totalThree + "", totalFour + "", totalFive + "", totalSix + "", totalExtra + ""});
            } else if (i == 12) {
                data.put(i + "", new String[]{"", "", "", "", "", "", "", ""});
            } else if (i == 13) {
                data.put(i + "", new String[]{"Standard", "", "", "", "", "", "", quantityObj.getCountStandardActive() + ""});
            } else if (i == 14) {
                data.put(i + "", new String[]{"Low Carb", "", "", "", "", "", "", quantityObj.getCountLowCarbActive() + ""});
            } else if (i == 15) {
                data.put(i + "", new String[]{"Kiddies", "", "", "", "", "", "", quantityObj.getCountKiddiesActive() + ""});
            } else if (i == 16) {
                data.put(i + "", new String[]{"", "", "", "", "", "", "", ""});
            } else if (i == 17) {
                totalClients = quantityObj.getCountKiddiesActive() + quantityObj.getCountLowCarbActive() + quantityObj.getCountStandardActive();
                data.put(i + "", new String[]{"Total Clients", "", "", "", "", "", "", totalClients + ""});
            } else if (i == 18) {
                data.put(i + "", new String[]{"Total Individuals", "", "", "", "", "", "", totalIndividuals + ""});
            }
        }

        Set<String> keySet = data.keySet();

        for (int keyIterate = 0; keyIterate < keySet.size(); keyIterate++) {
            Row row = sheet.createRow(rowNum);
            Object[] arr = data.get(keyIterate + "");

            for (int i = 0; i < arr.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue((String) arr[i]);

            }

            rowNum++;
            cellNum++;
            sheetNumber++;
        }

        try {
            creatSheet(sheetNumber + "", workbook);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "File Could Not Be Found.");
        }

        excelNumber++;

    }

    public static void creatSheet(String mealType, XSSFWorkbook workbook) throws IOException {

        FileOutputStream excelOut = null;
        try {

            Path path = Paths.get("Reports\\Week " + returnWeekInt() + "\\Quantity Report - " + currentWeek() + " Week -  " + returnWeekInt());
            Files.createDirectories(path);

            File file = path.resolve("Quantity Week - " + returnWeekInt() + " ( " + mealType + " )" + ".xlsx").toFile();
            if (!file.exists()) {
                file.createNewFile();
            }
            excelOut = new FileOutputStream(file);
            workbook.write(excelOut);
            excelOut.close();

            System.out.println("Done - Chef");

            if (DSC_Main.reportsDone == 4) {
                DSC_Main.reportsDone(null);
            }

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "File is Currently being Used. Please Close the File.");
            JOptionPane.showMessageDialog(null, "Directory Cannot be Found!");
        }
    }

    public static String currentWeek() {
        Calendar weekDate = Calendar.getInstance();
        while (weekDate.get(Calendar.DAY_OF_WEEK) != 2) {
            weekDate.add(Calendar.DAY_OF_WEEK, -1);
        }
        return new SimpleDateFormat("dd MMM yyyy").format(weekDate.getTime());
    }

    public static int returnWeekInt() {
        Calendar weekDate = Calendar.getInstance();
        while (weekDate.get(Calendar.DAY_OF_WEEK) != 2) {
            weekDate.add(Calendar.DAY_OF_WEEK, -1);
        }
        Calendar firstWeek = Calendar.getInstance();
        firstWeek.setTimeInMillis(1470002400000l);
        int weeks = ((weekDate.get(Calendar.YEAR) - firstWeek.get(Calendar.YEAR)) * 52 + weekDate.get(Calendar.WEEK_OF_YEAR)) - firstWeek.get(Calendar.WEEK_OF_YEAR);
        return weeks;
    }

}
