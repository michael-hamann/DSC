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
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    
    public static ArrayList<String> allRoutes;
    private static XSSFWorkbook workbook;
    private static DSC_ReportLoading chefLoadingObj;
    private static int booksCounter = 0;
    private static ArrayList<Order> orders;
    private static int mealCounter = 0;

    public static void getChefReport() {
        if (!DSC_Main.generateAllReports) {
            chefLoadingObj = new DSC_ReportLoading();
        }
        allRoutes = new ArrayList();
        orders = new ArrayList();
        getActiveRoutes();
    }

    public static void getActiveRoutes() {
        Firebase newRef = DBClass.getInstance().child("Routes");
        newRef.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot levelOne : ds.getChildren()) {
                    String RoutID = levelOne.getKey();
                    allRoutes.add(RoutID);
                }
                getActiveOrders();
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.print("Error: Could not get Routes: " + fe.getMessage());
            }
        }
        );
    }

    public static void processChefReport() throws IOException {

        boolean firstFamEntry = true;
        String list[] = {"Standard", "Low Carb", "Kiddies"};
        int excelNumber = 0;
        int sheetNumber = 0;

        for (mealCounter = 0; mealCounter < 3; mealCounter++) {
            workbook = new XSSFWorkbook();
            sheetNumber = 0;
            for (String currRoute : allRoutes) {

                Map<String, Object[]> data = new TreeMap<>();
                data.put(0 + "", new String[]{"Doorstep Chef - Chef Report " + DriverReport.returnWeekInt(), "", "", "Meal Type : " + list[mealCounter] + " " + " " + "Route: " + sheetNumber});
                data.put(1 + "", new String[]{"", "", "", "", "", "", ""});
                data.put(2 + "", new String[]{"Family Size", "Quantity", "Allergies", "Exclusions"});
                int counter = 3;
                XSSFSheet sheet = workbook.createSheet("ChefReports Route - " + sheetNumber);
                int rowNum = 0;
                int cellNum = 0;
                String familysize = "";

                ArrayList<Meal> mealList = new ArrayList<>();
                boolean hasValue = false;

                for (Order order : orders) {
                    for (Meal meal : order.getMeals()) {
                        if (meal.getMealType().equals(list[mealCounter]) && order.getRoute().equals(currRoute)) {
                            mealList.add(meal);
                            hasValue = true;
                        }
                    }
                }

                mealList.sort(new Comparator<Meal>() {
                    @Override
                    public int compare(Meal o1, Meal o2) {
                        if (o1.getQuantity() < o2.getQuantity()) {
                            return -1;
                        } else if (o1.getQuantity() > o2.getQuantity()) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                });

                if (hasValue) {
                    int currQuantity = 0;
                    int bulk = 0;
                    boolean firstIterate = true;

                    for (Meal meal : mealList) {

                        if (meal.getQuantity() != currQuantity) {

                            if (!firstIterate && bulk != 0) {
                                data.put(counter + "", new String[]{familysize + " Normal *", bulk + "", "", ""});
                                bulk = 0;
                                counter++;
                            }
                            firstIterate = false;

                            switch (meal.getQuantity()) {
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
                            currQuantity = meal.getQuantity();
                        }

                        if (meal.getAllergies().equals("-") && meal.getExclusions().equals("-")) {
                            bulk++;
                        } else {
                            data.put(counter + "", new String[]{familysize + " Meal", meal.getQuantity() + "", meal.getAllergies(), meal.getExclusions()});
                            counter++;
                        }

                    }
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
                            borderStyle.setBorderBottom(BorderStyle.THIN);
                            borderStyle.setBorderTop(BorderStyle.THIN);
                            borderStyle.setBorderLeft(BorderStyle.THIN);
                            borderStyle.setBorderRight(BorderStyle.THIN);
                            if ((arr[0] + "").contains("*")) {
                                isBulk = true;
                                borderStyle.setBorderBottom(BorderStyle.MEDIUM);
                            }
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
                    switch (i) {
                        case 1:
                            sheet.setColumnWidth(i, 3000);
                            break;
                        case 2:
                            sheet.setColumnWidth(i, 8000);
                            break;
                        default:
                            sheet.setColumnWidth(i, 4000);
                            break;
                    }

                    if (i == 3) {
                        sheet.setColumnWidth(i, 8000);
                    }

                }

                Row rowDate = sheet.createRow(keySet.size() + 1);
                Cell cell = rowDate.createCell(0);
                SimpleDateFormat sf = new SimpleDateFormat("EEE MMM yyyy HH:mm:ss");

                cell.setCellValue(sf.format(Calendar.getInstance().getTime()));
                XSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
                cell.setCellStyle(cellStyle);
                sheet.addMergedRegion(new CellRangeAddress(keySet.size() + 1, keySet.size() + 1, 0, 3));

                sheetNumber++;

                creatSheet(list[mealCounter], workbook);

                excelNumber++;
                if (excelNumber == allRoutes.size()) {
                    if (!(DSC_Main.generateAllReports)) {
                        chefLoadingObj.setVisible(false);
                        chefLoadingObj.dispose();
                        JOptionPane.showMessageDialog(null, "Chef Reports Successfully Generated.");
                    }
                }
            }
        }
    }

    public static void getActiveOrders() {
        Firebase ref = DBClass.getInstance().child("Orders");
        ref.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                try {

                    for (DataSnapshot levelOne : ds.getChildren()) {

                        Calendar start = null;
                        start = Calendar.getInstance();
                        start.setTimeInMillis(levelOne.child("StartingDate").getValue(long.class));

                        if (start.getTimeInMillis() > DriverReport.returnWeekMili()) {
                            continue;
                        }
                        ArrayList<Meal> meals = new ArrayList<>();
                        for (DataSnapshot levelTwo : levelOne.getChildren()) {
                            for (DataSnapshot levelThree : levelTwo.getChildren()) {
                                meals.add(new Meal(
                                        levelThree.child("Quantity").getValue(int.class),
                                        levelThree.child("MealType").getValue(String.class),
                                        levelThree.child("Allergies").getValue(String.class),
                                        levelThree.child("Exclusions").getValue(String.class)
                                ));
                            }
                        }

                        orders.add(new Order(levelOne.getKey(), true,
                                levelOne.child("ClientID").getValue(String.class),
                                null,
                                null,
                                null,
                                levelOne.child("RouteID").getValue(String.class),
                                meals,
                                levelOne.child("FamilySize").getValue(int.class)));

                        processChefReport();
                    }

                } catch (IOException ex) {
                    Logger.getLogger(ChefReport.class.getName()).log(Level.SEVERE, null, ex);
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

    public static void creatSheet(String mealType, XSSFWorkbook workbook) throws IOException {
        FileOutputStream excelOut = null;
        try {

            Path path = Paths.get("Reports\\Week " + DriverReport.returnWeekInt() + " (" + DriverReport.returnWeekString() + ")\\ChefReports\\");
            Files.createDirectories(path);

            File file = path.resolve("ChefReports Week - " + DriverReport.returnWeekInt() + " ( " + mealType + " )" + ".xlsx").toFile();
            if (!file.exists()) {
                file.createNewFile();
            }

            excelOut = new FileOutputStream(file);
            workbook.write(excelOut);
            excelOut.close();
            booksCounter++;

            if (booksCounter == 3) {
                System.out.println("Done - Chef");
                if (DSC_Main.generateAllReports) {
                    DSC_Main.reportsDone++;
                    if (DSC_Main.reportsDone == DSC_Main.TOTAL_REPORTS) {
                        DSC_Main.reportsDone();
                    }
                }
            }
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "File is Currently being Used. Please Close the File.");
            JOptionPane.showMessageDialog(null, "Directory Cannot be Found!");
        }
    }
}
