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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
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
public class ChefReport {

    private ArrayList<Chef> allOrders = new ArrayList();
    public static ArrayList<String> allRoutes = new ArrayList();
    private String list[] = {"Standard", "Low Carb", "Kiddies"};
    private String familySizes[] = {"1", "2", "3", "4", "5", "6"};
    private String familysize = "";
    private XSSFWorkbook workbook;
    public static boolean completeReport = false;
    private static int booksCounter = 0;
    
    
    public void getChefReport() {

        booksCounter = 0;
        Firebase newRef = DBClass.getInstance().child("Routes");
        newRef.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                for (DataSnapshot levelOne : ds.getChildren()) {
                    String RoutID = levelOne.getKey();
                    allRoutes.add(RoutID);

                }
            }

            @Override
            public void onCancelled(FirebaseError fe
            ) {
                System.err.print("Error: Could not get Routes: " + fe.getMessage());
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

                            String quantity = levelThree.child("Quantity").getValue(String.class);
                            String Allergies = levelThree.child("Allergies").getValue(String.class);
                            String Exclusions = levelThree.child("Exclusions").getValue(String.class);
                            String RouteID = levelOne.child("RouteID").getValue(String.class);
                            String MealType = levelThree.child("MealType").getValue(String.class);
                            String FamilySize = levelOne.child("FamilySize").getValue(String.class);

                            if (levelOne.child("Active").getValue(boolean.class).equals(true)) {
                                allOrders.add(new Chef(quantity,
                                        Allergies,
                                        Exclusions,
                                        RouteID,
                                        MealType,
                                        FamilySize
                                ));
                            }
                        }
                    }
                }

                int excelNumber = 0;

                for (int numberOfRoutes = 0; numberOfRoutes < allRoutes.size(); numberOfRoutes++) {
                    int sheetNumber = 0;
                    workbook = new XSSFWorkbook();
                    for (String currRoute : allRoutes) {
                        int bulkCount = 0;
                        Map<String, Object[]> data = new TreeMap<>();
                        data.put(0 + "", new String[]{"Doorstep Chef - Chef Report " + currentWeek(), "", "", "Meal Type : " + list[numberOfRoutes] + " " + " " + "Route: " + sheetNumber});
                        data.put(1 + "", new String[]{"", "", "", "", "", "", ""});
                        data.put(2 + "", new String[]{"Family Size", "Quantity", "Allergies", "Exclusions"});
                        int counter = 3;
                        XSSFSheet sheet = workbook.createSheet("ChefReports Route - " + sheetNumber);
                        int rowNum = 0;
                        int cellNum = 0;
                        for (int i = 0; i < familySizes.length; i++) {
                            for (int ordersCount = 0; ordersCount < allOrders.size(); ordersCount++) {

                                if (allOrders.get(ordersCount).getRoute().equals(currRoute) && allOrders.get(ordersCount).getMealType().equals(list[numberOfRoutes]) && allOrders.get(ordersCount).getFamilySize().equals(familySizes[i])) {
                                    if (allOrders.get(ordersCount).getAllergies().equals("-") && allOrders.get(ordersCount).getAllergies().equals("-") || allOrders.get(ordersCount).getAllergies().equals("") && allOrders.get(ordersCount).getAllergies().equals("")) {
                                        bulkCount++;
                                    } else {
                                        switch (allOrders.get(ordersCount).getFamilySize()) {
                                            case "1":
                                                familysize = "Single Meal";
                                                break;
                                            case "2":
                                                familysize = "Couple Meal";
                                                break;
                                            case "3":
                                                familysize = "Three Meal";
                                                break;
                                            case "4":
                                                familysize = "Four Meal";
                                                break;
                                            case "5":
                                                familysize = "Five Meal";
                                                break;
                                            case "6":
                                                familysize = "Six Meal";
                                                break;
                                            default:
                                                familysize = "Extra Meal";
                                        }

                                        data.put(counter + "", new String[]{familysize, allOrders.get(ordersCount).getQuantity(), allOrders.get(ordersCount).getAllergies(),
                                            allOrders.get(ordersCount).getExclusions()});
                                        counter++;
                                    }

                                }

                            }

                            switch (i + 1) {
                                case 1:
                                    familysize = "Single";
                                    break;
                                case 2:
                                    familysize = "Couple";
                                    break;
                                case 3:
                                    familysize = "Three";
                                    break;
                                case 4:
                                    familysize = "Four";
                                    break;
                                case 5:
                                    familysize = "Five";
                                    break;
                                case 6:
                                    familysize = "Six";
                                    break;
                                default:
                                    familysize = "Extra";
                            }

                            data.put(counter + "", new String[]{familysize + " Normal *", bulkCount + "", "", ""});
                            counter++;
                        }

                        Set<String> keySet = data.keySet();
                        Object[] keys = data.keySet().toArray();
                        Arrays.sort(keys);
                        ArrayList<Object> keyList = new ArrayList();
                        int longestCustomer = 5;
                        int totalWidth = 50000;
                        boolean isBulk = false;

                        for (Object key : keys) {
                            keyList.add(data.get(key));
                        }

                        for (int keyIterate = 0; keyIterate < keySet.size(); keyIterate++) {
                            Row row = sheet.createRow(rowNum);
                            Object[] arr = data.get(keyIterate + "");

                            for (int i = 0; i < arr.length; i++) {
                                XSSFFont font = workbook.createFont();
                                Cell cell = row.createCell(i);
                                cell.setCellValue((String) arr[i]);
                                XSSFCellStyle borderStyle = workbook.createCellStyle();

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

                                } else {
                                    if ((arr[i] + "").contains("Bulk")) {
                                        isBulk = true;
                                    }
                                    if (i == 1 && isBulk) {
                                        font.setBold(true);
                                        font.setFontHeightInPoints((short) 13);
                                        borderStyle.setFont(font);
                                        isBulk = false;
                                    }
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
                        creatSheet(excelNumber + "", list[numberOfRoutes], workbook);
                    } catch (FileNotFoundException ex) {
                        JOptionPane.showMessageDialog(null, "File is Currently being Used. Please Close the File.");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "File COuld Not Be Found.");
                    }
                    excelNumber++;

                }

            }

            @Override

            public void onCancelled(FirebaseError fe
            ) {
                System.err.print("Error: Could not get Orders: " + fe.getMessage());
            }

        }
        );

    }

    public static void creatSheet(String excelNumber, String mealType, XSSFWorkbook workbook) throws IOException {
        FileOutputStream excelOut = null;
        try {
            String path = "\\Reports\\" + "DSC_ChefReport - " + currentWeek() + " Week -  " + returnWeekInt() + "\\";
            File f = new File(path);
            f.mkdir();
            File file = new File(path + "ChefReports Route - " + excelNumber + " ( " + mealType + " )" + ".xlsx");
            excelOut = new FileOutputStream(file);
            workbook.write(excelOut);
            excelOut.close();
            booksCounter++;
            
            if (booksCounter == 3) {
                System.out.println("Done - Chef");
                DSC_Main.reportsDone++;
                if (DSC_Main.reportsDone == 4) {
                    DSC_Main.reportsDone(null);
                }
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "File is Currently being Used. Please Close the File.");
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
