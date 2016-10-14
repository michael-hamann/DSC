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
import java.util.Calendar;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
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
    private static QuantityReportData quantityObj;
    private static DSC_ReportLoading quantityLoadingObj;

    public static void getActiveClients() {
        if (!DSC_Main.generateAllReports) {
            quantityLoadingObj = new DSC_ReportLoading();
        }
        boolean fileFound = false;
        quantityObj = new QuantityReportData();
        Firebase newRef = DBClass.getInstance().child("Orders");
        newRef.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot levelOne : ds.getChildren()) {

//                    Calendar start = null;
//
//                    start = Calendar.getInstance();
//                    start.setTimeInMillis(levelOne.child("StartingDate").getValue(long.class));
//
//                    if (start.getTimeInMillis() > DriverReport.returnWeekMili()) {
//                        continue;
//                    }
                    boolean activeCheck = levelOne.child("Active").getValue(boolean.class).equals(true);
                    if (activeCheck) {
                        quantityObj.incrementActiveClientCount();
                        int familySizeCheck;
                        int quantity;
                        familySizeCheck = levelOne.child("FamilySize").getValue(int.class);// gets family size from database

                        // increment orders per family size
                        if (familySizeCheck == 1) {
                            quantityObj.incrementCountFamilySize_1();// Orders Count - Perfect
                        } else if (familySizeCheck == 2) {
                            quantityObj.incrementCountFamilySize_2();
                        } else if (familySizeCheck == 3) {
                            quantityObj.incrementCountFamilySize_3();
                        } else if (familySizeCheck == 4) {
                            quantityObj.incrementCountFamilySize_4();
                        } else if (familySizeCheck == 5) {
                            quantityObj.incrementCountFamilySize_5();
                        } else if (familySizeCheck == 6) {
                            quantityObj.incrementCountFamilySize_6();
                        } else if (familySizeCheck > 6) {
                            quantityObj.incrementCountFamilySizeMoreThanSix();
                        }

                        for (DataSnapshot levelTwo : levelOne.getChildren()) {

                            for (DataSnapshot levelThree : levelTwo.getChildren()) {
                                quantity = levelThree.child("Quantity").getValue(int.class);
                                String mealType = levelThree.child("MealType").getValue(String.class);

                                // Standard Totals
                                if (mealType.equals(STANDARD) && quantity == 1) { // Meal Count Per Family Size
                                    quantityObj.incrementCountFamSize1_Standard();
                                } else if (mealType.equals(STANDARD) && quantity == 2) {
                                    quantityObj.incrementCountFamSize2_Standard();
                                } else if (mealType.equals(STANDARD) && quantity == 3) {
                                    quantityObj.incrementCountFamSize3_Standard();
                                } else if (mealType.equals(STANDARD) && quantity == 4) {
                                    quantityObj.incrementCountFamSize4_Standard();
                                } else if (mealType.equals(STANDARD) && quantity == 5) {
                                    quantityObj.incrementCountFamSize5_Standard();
                                } else if (mealType.equals(STANDARD) && quantity == 6) {
                                    quantityObj.incrementCountFamSize6_Standard();
                                } else if (mealType.equals(STANDARD) && quantity > 6) {
                                    quantityObj.incrementCountFamilySizeMoreThanSix_Standard();
                                    quantityObj.incrementTotalQuantityFamSizeMoreThanSix_Standard(quantity);
                                }

                                // Low Carb Totals
                                if (mealType.equals(LOW_CARB) && quantity == 1) {
                                    quantityObj.incrementCountFamSize1_LC();
                                } else if (mealType.equals(LOW_CARB) && quantity == 2) {
                                    quantityObj.incrementCountFamSize2_LC();
                                } else if (mealType.equals(LOW_CARB) && quantity == 3) {
                                    quantityObj.incrementCountFamSize3_LC();
                                } else if (mealType.equals(LOW_CARB) && quantity == 4) {
                                    quantityObj.incrementCountFamSize4_LC();
                                } else if (mealType.equals(LOW_CARB) && quantity == 5) {
                                    quantityObj.incrementCountFamSize5_LC();
                                } else if (mealType.equals(LOW_CARB) && quantity == 6) {
                                    quantityObj.incrementCountFamSize6_LC();
                                } else if (mealType.equals(LOW_CARB) && quantity > 6) {
                                    quantityObj.incrementCountFamilySizeMoreThanSix_LC();
                                    quantityObj.incrementTotalQuantityFamSizeMoreThanSix_LC(quantity);
                                }

                                // Kiddies Totals
                                if (mealType.equals(KIDDIES) && quantity == 1) {
                                    quantityObj.incrementCountFamSize1_KD();
                                } else if (mealType.equals(KIDDIES) && quantity == 2) {
                                    quantityObj.incrementCountFamSize2_KD();
                                } else if (mealType.equals(KIDDIES) && quantity == 3) {
                                    quantityObj.incrementCountFamSize3_KD();
                                } else if (mealType.equals(KIDDIES) && quantity == 4) {
                                    quantityObj.incrementCountFamSize4_KD();
                                } else if (mealType.equals(KIDDIES) && quantity == 5) {
                                    quantityObj.incrementCountFamSize5_KD();
                                } else if (mealType.equals(KIDDIES) && quantity == 6) {
                                    quantityObj.incrementCountFamSize6_KD();
                                } else if (mealType.equals(KIDDIES) && quantity > 6) {
                                    quantityObj.incrementCountFamilySizeMoreThanSix_KD();
                                    quantityObj.incrementTotalQuantityFamSizeMoreThanSix_KD(quantity);
                                }

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

    private static void processQuantityReport() {

        int excelNumber = 0;
        int sheetNumber = 0;
        workbook = new XSSFWorkbook();
        Map<String, String[]> data = new TreeMap<>();
        data.put(0 + "", new String[]{"Doorstep Chef - Quantity Report " + currentWeek(), "", "", "Week : " + returnWeekInt() + " " + " "});
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
                data.put(i + "", new String[]{"Totals", quantityObj.totalSingleMeals() + "", quantityObj.totalCoupleMeals() + "", quantityObj.totalThreeMeals() + "",
                    quantityObj.totalFourMeals() + "", quantityObj.totalFiveMeals() + "", quantityObj.totalSixMeals() + "", quantityObj.totalExtraMeals() + ""});
            } else if (i == 12) {
                data.put(i + "", new String[]{"", "", "", "", "", "", "", ""});
            } else if (i == 13) {
                data.put(i + "", new String[]{"Standard Individuals", "", "", "", "", "", "", quantityObj.returnTotalStandardMeals() + ""});
            } else if (i == 14) {
                data.put(i + "", new String[]{"Low Carb Individuals", "", "", "", "", "", "", quantityObj.returnTotalLowCarbMeals() + ""});
            } else if (i == 15) {
                data.put(i + "", new String[]{"Kiddies Individuals", "", "", "", "", "", "", quantityObj.returnTotalKiddiesMeals() + ""});
            } else if (i == 16) {
                data.put(i + "", new String[]{"", "", "", "", "", "", "", ""});
            } else if (i == 17) {
                data.put(i + "", new String[]{"Total Clients", "", "", "", "", "", "", quantityObj.returnTotalClients() + ""});
            } else if (i == 18) {
                
                int totalIndividuals = quantityObj.returnTotalStandardMeals() + quantityObj.returnTotalLowCarbMeals() + quantityObj.returnTotalKiddiesMeals();
                
                data.put(i + "", new String[]{"Total Individuals", "", "", "", "", "", "", totalIndividuals + ""});
            }
        }

        Set<String> keySet = data.keySet();

        for (int keyIterate = 0; keyIterate < keySet.size(); keyIterate++) {
            Row row = sheet.createRow(rowNum);
            Object[] arr = data.get(keyIterate + "");
            XSSFCellStyle borderStyle = workbook.createCellStyle();
            XSSFCellStyle borderStyle2 = workbook.createCellStyle();
            for (int i = 0; i < arr.length; i++) {
                XSSFFont font = workbook.createFont();
                Cell cell = row.createCell(i);
                cell.setCellValue((String) arr[i]);

                if ((keyIterate + "").equals("0") || (keyIterate + "").equals("1")) {

                    font.setFontName("Calibri");
                    font.setFontHeightInPoints((short) 13);
                    font.setBold(true);
                    borderStyle.setFont(font);
                    borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                    borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                    borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
                    borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
                    borderStyle.setAlignment(HorizontalAlignment.LEFT);

                } else if (keyIterate > 1 && keyIterate < 12) {
                    borderStyle.setBorderBottom(BorderStyle.THIN);
                    borderStyle.setBorderTop(BorderStyle.THIN);
                    borderStyle.setBorderLeft(BorderStyle.THIN);
                    borderStyle.setBorderRight(BorderStyle.THIN);

                }

                if ((keyIterate + "").equals("2")) {
                    borderStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
                    borderStyle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
                    borderStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
                    borderStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
                    borderStyle.setAlignment(HorizontalAlignment.CENTER);
                    borderStyle.setFillPattern(XSSFCellStyle.LESS_DOTS);
                    borderStyle.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                    XSSFFont font2 = workbook.createFont();
                    font2.setColor(IndexedColors.WHITE.getIndex());
                    borderStyle.setFont(font2);

                }

                cell.setCellStyle(borderStyle);
            }

            rowNum++;
            cellNum++;
            sheetNumber++;
        }

        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

        for (int i = 0; i < 8; i++) {
            if (i == 0) {
                sheet.setColumnWidth(i, 5000);
            } else {
                sheet.setColumnWidth(i, 2000);

            }

        }

        try {
            creatSheet(sheetNumber + "", workbook);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "File Could Not Be Found.");
        }

        excelNumber++;

    }

    private static void creatSheet(String mealType, XSSFWorkbook workbook) throws IOException {

        FileOutputStream excelOut = null;
        try {

            Path path = Paths.get("Reports\\Week " + DriverReport.returnWeekInt() + " (" + DriverReport.returnWeekString() + ")");
            Files.createDirectories(path);

            File file = path.resolve("Quantity Report Week - " + returnWeekInt() + ".xlsx").toFile();
            if (!file.exists()) {
                file.createNewFile();
            }
            excelOut = new FileOutputStream(file);
            workbook.write(excelOut);
            excelOut.close();

            System.out.println("Done - Quantity");
            if (DSC_Main.generateAllReports) {
                DSC_Main.reportsDone++;
                if (DSC_Main.reportsDone == DSC_Main.TOTAL_REPORTS) {
                    DSC_Main.reportsDone();
                }
            } else {
                quantityLoadingObj.setVisible(false);
                quantityLoadingObj.dispose();
                JOptionPane.showMessageDialog(null, "QuantityReport Succesfully Generated", "Success", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "File is Currently being Used. Please Close the File.");

        }
    }

    private static String currentWeek() {
        Calendar weekDate = Calendar.getInstance();
        while (weekDate.get(Calendar.DAY_OF_WEEK) != 2) {
            weekDate.add(Calendar.DAY_OF_WEEK, -1);
        }
        return new SimpleDateFormat("dd MMM yyyy").format(weekDate.getTime());
    }

    private static int returnWeekInt() {
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
