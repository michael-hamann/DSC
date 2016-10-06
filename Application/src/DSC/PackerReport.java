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
import java.util.Calendar;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JOptionPane;
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
public class PackerReport {

    private static ArrayList<Order> orderList;
    private static ArrayList<Route> routeList;
    private static int driverCounter;
    private static int clientCounter;
    private static File file;
    private static FileOutputStream excelOut;
    private static DSC_ReportLoading packerLoadingObj;

    public static void getPackerData() {
        if (!DSC_Main.generateAllReports) {
            packerLoadingObj = new DSC_ReportLoading();
        }
        boolean fileFound = false;
        try {
            file = new File("PackerReports (" + DriverReport.returnWeekString() + ").xlsx");
            if (!file.exists()) {
                file.createNewFile();
            }
            excelOut = new FileOutputStream(file);
            fileFound = true;
        } catch (FileNotFoundException ex) {
            packerLoadingObj.setVisible(false);
            packerLoadingObj.dispose();
            JOptionPane.showMessageDialog(null, "Please close the excel file before using generating.", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error - Could not create new PackerReports: File currently in use.");
        } catch (IOException io) {
            packerLoadingObj.setVisible(false);
            packerLoadingObj.dispose();
            JOptionPane.showMessageDialog(null, "An error occured\nCould not create Packer Reports", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error - Could not create new Packer Reports");
        }
        if (fileFound) {
            routeList = new ArrayList<>();
            orderList = new ArrayList<>();
            getActiveOrders();
        }
    }

    private static void getActiveOrders() {
        Firebase ref = DBClass.getInstance().child("Orders");
        ref.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    Calendar start = null;

                    start = Calendar.getInstance();
                    start.setTimeInMillis(dataSnapshot.child("StartingDate").getValue(long.class));

                    if (start.getTimeInMillis() > DriverReport.returnWeekMili()) {
                        //continue;
                    }

                    ArrayList<Meal> meals = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        for (DataSnapshot dataSnapshot2 : dataSnapshot1.getChildren()) {
                            meals.add(new Meal(
                                    dataSnapshot2.child("Quantity").getValue(int.class),
                                    dataSnapshot2.child("MealType").getValue(String.class),
                                    dataSnapshot2.child("Allergies").getValue(String.class),
                                    dataSnapshot2.child("Exclusions").getValue(String.class)
                            ));
                        }
                    }
                    orderList.add(new Order(
                            dataSnapshot.getKey(),
                            true,
                            dataSnapshot.child("ClientID").getValue(String.class),
                            dataSnapshot.child("Duration").getValue(String.class),
                            start,
                            null,
                            dataSnapshot.child("RouteID").getValue(String.class),
                            meals,
                            dataSnapshot.child("FamilySize").getValue(int.class)
                    ));
                }
                clientCounter = 0;
                for (Order order : orderList) {
                    getClient(order.getClientID(), orderList.indexOf(order));
                }

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.print("Error: Could not get Clients: " + fe.getMessage());
            }
        });
    }

    private static void getClient(String id, int index) {
        Firebase ref = DBClass.getInstance().child("Clients/" + id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                orderList.get(index).setClient(new Client(
                        id,
                        ds.child("Name").getValue(String.class),
                        ds.child("Surname").getValue(String.class),
                        ds.child("ContactNum").getValue(String.class),
                        ds.child("AlternativeNumber").getValue(String.class),
                        ds.child("Email").getValue(String.class),
                        ds.child("Suburb").getValue(String.class),
                        ds.child("Address").getValue(String.class),
                        ds.child("AdditionalInfo").getValue(String.class)
                ));
                clientCounter++;
                if (clientCounter == orderList.size()) {
                    driverCounter = 0;
                    getAllRoutes();
                }
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.println("Error: Could not add Client " + id);
            }
        });
    }

    private static void getAllRoutes() {
        Firebase ref = DBClass.getInstance().child("Routes");
        ref.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                int driverCount = 0;
                String driverID = "";
                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    Calendar start = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();

                    if (dataSnapshot.child("StartingDate").getValue(String.class).equals("-")) {
                        start = null;
                    } else {
                        start.setTimeInMillis(dataSnapshot.child("StartingDate").getValue(long.class));
                    }

                    if (dataSnapshot.child("EndDate").getValue(String.class).equals("-")) {
                        end = null;
                    } else {
                        end.setTimeInMillis(dataSnapshot.child("EndDate").getValue(long.class));
                    }

                    String[] suburbs = dataSnapshot.child("Suburbs").getValue(String[].class);
                    ArrayList<String> suburbList = new ArrayList<>();
                    for (String suburb : suburbs) {
                        suburbList.add(suburb);
                    }

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child("Drivers").getChildren()) {
                        if (dataSnapshot1.child("EndDate").getValue(String.class).equals("-")) {
                            driverID = dataSnapshot1.child("DriverID").getValue(String.class);
                        }
                    }

                    routeList.add(driverCount, new Route(
                            dataSnapshot.getKey(),
                            true,
                            dataSnapshot.child("TimeFrame").getValue(String.class),
                            suburbList,
                            null,
                            start,
                            end
                    ));

                    getDriverDetails(driverCount, driverID);
                    driverCount++;
                }
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println("Error: " + fe.getMessage());
            }
        });
    }

    private static void getDriverDetails(int listIndex, String routeID) {
        Firebase ref = DBClass.getInstance().child("Drivers/" + routeID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                String number = ds.child("ContactNumber").getValue(String.class);
                if (number.length() == 10) {
                    number = number.substring(0, 3) + " " + number.substring(3, 6) + " " + number.substring(6, 10);
                }
                Driver driver = new Driver("",
                        number,
                        ds.child("DriverName").getValue(String.class),
                        ds.child("Vehicle").getValue(String.class)
                );
                ArrayList<RouteDrivers> drivers = new ArrayList<>();
                drivers.add(new RouteDrivers(driver, null, null));
                routeList.get(listIndex).setDrivers(drivers);
                driverCounter++;
                if (driverCounter == routeList.size()) {
                    createSpreadsheets();
                }

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println("Error: Could not retrieve specified driver: " + fe.getMessage());
            }
        });
    }

    private static void createSpreadsheets() {
        orderList.sort(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                int result;
                if (o1.getFamilySize() < o2.getFamilySize()) {
                    result = -1;
                } else if (o1.getFamilySize() == o2.getFamilySize()) {
                    result = 0;
                } else {
                    result = 1;
                }
                return result;
            }
        });

        XSSFWorkbook workbook = new XSSFWorkbook();
        for (Route route : routeList) {
            XSSFSheet sheet = workbook.createSheet("PackerReports Route - " + route.getID());

            Map<String, String[]> data = new TreeMap<>();
            data.put("1", new String[]{"Doorstep Chef Packer Sheet", "", "", route.getDrivers().get(0).getDriver().getDriverName().split(" ")[0] + " - " + route.getDrivers().get(0).getDriver().getContactNumber(), "", " Week: " + DriverReport.returnWeekInt() + " Route: " + route.getID()});
            data.put("2", new String[]{"", "", "", "", "", ""});
            data.put("3", new String[]{"Customer", "FamSize", "MealType", "Qty", "Allergies", "Exclutions"});

            int[] totals = new int[11];

            int counter = 4;
            for (Order order : orderList) {
                if (order.getRoute().equals(route.getID())) {

                    Client client = order.getClient();
                    String customer = client.getName() + " " + client.getSurname();
                    String famSize = order.getFamilySize() + "";
                    for (Meal meal : order.getMeals()) {
                        data.put(counter + "", new String[]{customer, famSize, meal.getMealType(), meal.getQuantity() + "", meal.getAllergies(), meal.getExclusions()});
                        customer = "";
                        famSize = "";
                        counter++;
                        if (meal.getMealType().equals("Standard")) {
                            totals[1] += meal.getQuantity();
                        } else if (meal.getMealType().equals("Low Carb")) {
                            totals[2] += meal.getQuantity();
                        } else if (meal.getMealType().equals("Kiddies")) {
                            totals[3] += meal.getQuantity();
                        }

                        switch (meal.getQuantity()) {
                            case 1:
                                totals[4]++;
                                break;
                            case 2:
                                totals[5]++;
                                break;
                            case 3:
                                totals[6]++;
                                break;
                            case 4:
                                totals[7]++;
                                break;
                            case 5:
                                totals[8]++;
                                break;
                            case 6:
                                totals[9]++;
                                break;
                            default:
                                if (meal.getQuantity() > 6) {
                                    totals[10]++;
                                }
                        }
                    }
                    totals[0]++;
                }
            }

            Set<String> keySet = data.keySet();
            int totalSize = 22000;
            int longestCustomer = 0;
            for (int key = 1; key < keySet.size() + 1; key++) {
                Row row = sheet.createRow(key - 1);
                String[] arr = data.get(key + "");

                for (int i = 0; i < arr.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue(arr[i]);
                    XSSFCellStyle borderStyle = workbook.createCellStyle();

                    if (i == 0 && !(key + "").equals("1") && longestCustomer < ((String) arr[i]).length()) {
                        longestCustomer = ((String) arr[i]).length();
                    }

                    if (!((key + "").equals("1") || (key + "").equals("2"))) {
                        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
                        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
                        if ((key + "").equals("3")) {
                            borderStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
                            borderStyle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
                            borderStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
                            borderStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
                            borderStyle.setAlignment(HorizontalAlignment.CENTER);
                            borderStyle.setFillPattern(XSSFCellStyle.LESS_DOTS);
                            borderStyle.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                            XSSFFont font = workbook.createFont();
                            font.setColor(IndexedColors.WHITE.getIndex());
                            font.setBold(true);
                            borderStyle.setFont(font);

                        } else {
                            if (i != 0) {
                                borderStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
                            } else {
                                borderStyle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
                            }
                            if (i != 5) {
                                borderStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
                            } else {
                                borderStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
                            }

                            if (i == 5 || i == 4) {
                                borderStyle.setAlignment(XSSFCellStyle.ALIGN_JUSTIFY);
                                borderStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_JUSTIFY);
                            }

                            if ((Integer.parseInt((key + ""))) != keySet.size()) {
                                borderStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
                            } else {
                                borderStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
                            }
                            borderStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);

                        }
                        if (i == 3 || i == 1) {
                            borderStyle.setAlignment(HorizontalAlignment.CENTER);
                        }
                    } else {
                        if (key != 3 && (i == 4 || i == 5)) {
                            borderStyle.setAlignment(XSSFCellStyle.ALIGN_JUSTIFY);
                            borderStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_JUSTIFY);
                        }
                        if (i == 3) {
                            borderStyle.setAlignment(HorizontalAlignment.CENTER);
                        } else if (i == 5) {
                            borderStyle.setAlignment(HorizontalAlignment.RIGHT);
                        }
                        XSSFFont font = workbook.createFont();
                        font.setFontName("Calibri");
                        font.setFontHeightInPoints((short) 13);
                        font.setBold(true);
                        borderStyle.setFont(font);
                    }

                    cell.setCellStyle(borderStyle);
                }
            }

            //<editor-fold defaultstate="collapsed" desc="Add Totals">
            
            
            Row row = sheet.createRow(keySet.size());
            Cell cell1 = row.createCell(0);
            cell1.setCellValue("Clients: " + totals[0]);
            XSSFCellStyle cellStyle1 = workbook.createCellStyle();
            XSSFFont font = workbook.createFont();
            font.setBold(true);
            cellStyle1.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
            cellStyle1.setFont(font);
            cell1.setCellStyle(cellStyle1);

            
            
            Cell cell2 = row.createCell(1);
            cell2.setCellValue("Standard: " + totals[1]);
            XSSFCellStyle cellStyle2 = workbook.createCellStyle();
            font.setBold(true);
            cellStyle2.setFont(font);
            cell2.setCellStyle(cellStyle2);

            
            
            Cell cell3 = row.createCell(4);
            cell3.setCellValue("Low Carb:  " + totals[2]);
            XSSFCellStyle cellStyle3 = workbook.createCellStyle();
            font.setBold(true);
            cellStyle3.setFont(font);
            cell3.setCellStyle(cellStyle3);
            
            
            
            Cell cell4 = row.createCell(5);
            cell4.setCellValue("Kiddies: " + totals[3]);
            XSSFCellStyle cellStyle4 = workbook.createCellStyle();
            font.setBold(true);
            cellStyle4.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
            cellStyle4.setFont(font);
            cell4.setCellStyle(cellStyle4);

            
            
            row = sheet.createRow(keySet.size() + 1);
            
            Cell holder = row.createCell(0);
            XSSFCellStyle border1 = workbook.createCellStyle();
            border1.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
            holder.setCellStyle(border1);
            
            
            cell2 = row.createCell(1);
            cell2.setCellValue("Single: " + totals[4]);
            XSSFCellStyle cellStyle6 = workbook.createCellStyle();
            font.setBold(true);
            cellStyle6.setFont(font);
            cell2.setCellStyle(cellStyle6);

            
            
            cell3 = row.createCell(4);
            cell3.setCellValue("Couple:  " + totals[5]);
            XSSFCellStyle cellStyle7 = workbook.createCellStyle();
            font.setBold(true);
            cellStyle7.setFont(font);
            cell3.setCellStyle(cellStyle7);

            
            
            cell4 = row.createCell(5);
            cell4.setCellValue("Small(3): " + totals[6]);
            XSSFCellStyle cellStyle8 = workbook.createCellStyle();
            font.setBold(true);
            cellStyle8.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
            cellStyle8.setFont(font);
            cell4.setCellStyle(cellStyle8);

            
            
            row = sheet.createRow(keySet.size() + 2);
            
            Cell holder2 = row.createCell(0);
            XSSFCellStyle border2 = workbook.createCellStyle();
            border2.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
            border2.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
            holder2.setCellStyle(border2);
            
            cell2 = row.createCell(1);
            cell2.setCellValue("Medium(4): " + totals[7]);
            XSSFCellStyle cellStyle9 = workbook.createCellStyle();
            font.setBold(true);
            cellStyle9.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
            cellStyle9.setFont(font);
            cell2.setCellStyle(cellStyle9);

            Cell holder3 = row.createCell(2);
            XSSFCellStyle border3 = workbook.createCellStyle();
            border3.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
            holder3.setCellStyle(border3);
            
            Cell holder4 = row.createCell(3);
            XSSFCellStyle border4 = workbook.createCellStyle();
            border4.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
            holder4.setCellStyle(border4);
            
            cell3 = row.createCell(4);
            cell3.setCellValue("Large(5):  " + totals[8]);
            XSSFCellStyle cellStyle10 = workbook.createCellStyle();
            font.setBold(true);
            cellStyle10.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
            cellStyle10.setFont(font);
            cell3.setCellStyle(cellStyle10);

            
            
            cell4 = row.createCell(5);
            cell4.setCellValue("XLarge(6): " + totals[9]);
            XSSFCellStyle cellStyle11 = workbook.createCellStyle();
            font.setBold(true);
            cellStyle11.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
            cellStyle11.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
            cellStyle11.setFont(font);
            cell4.setCellStyle(cellStyle11);
            
//</editor-fold>
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 4));
            sheet.addMergedRegion(new CellRangeAddress(keySet.size(), keySet.size(), 1, 3));
            sheet.addMergedRegion(new CellRangeAddress(keySet.size() + 1, keySet.size() + 1, 1, 3));
            sheet.addMergedRegion(new CellRangeAddress(keySet.size() + 2, keySet.size() + 2, 1, 3));

            sheet.setColumnWidth(0, (longestCustomer + 1) * 240);
            sheet.setColumnWidth(1, 8 * 240);
            sheet.setColumnWidth(2, 10 * 240);
            sheet.setColumnWidth(3, 4 * 240);

            int usedSize = 0;
            for (int i = 0; i <= 3; i++) {
                usedSize += sheet.getColumnWidth(i);
            }
            sheet.setColumnWidth(4, (totalSize - usedSize) / 2);
            sheet.setColumnWidth(5, (totalSize - usedSize) / 2);

            Row rowDate = sheet.createRow(keySet.size() + 4);
            Cell cell = rowDate.createCell(0);
            SimpleDateFormat sf = new SimpleDateFormat("EEE MMM yyyy HH:mm:ss");
            
            cell.setCellValue(sf.format(Calendar.getInstance().getTime()));
            XSSFCellStyle cellStyle = workbook.createCellStyle();
            cellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
            cell.setCellStyle(cellStyle);
            sheet.addMergedRegion(new CellRangeAddress(keySet.size() + 4, keySet.size() + 4, 0, 5));
            
        }

        try {
            workbook.write(excelOut);
            excelOut.close();
            System.out.println("Done - Packer");
            if (DSC_Main.generateAllReports) {
                DSC_Main.reportsDone++;
                if (DSC_Main.reportsDone == 4) {
                    DSC_Main.reportsDone();
                }
            } else {
                packerLoadingObj.setVisible(false);
                packerLoadingObj.dispose();
                JOptionPane.showMessageDialog(null, "PackerReport Succesfully Generated", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (IOException io) {
            packerLoadingObj.setVisible(false);
            packerLoadingObj.dispose();
            JOptionPane.showMessageDialog(null, "An error occured\nCould not create PackerReport", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error - Could not create new PackerReport: ");
            io.printStackTrace();
        }

    }
}
