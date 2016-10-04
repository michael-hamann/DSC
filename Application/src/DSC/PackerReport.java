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
        packerLoadingObj = new DSC_ReportLoading();
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
                    
                    if (start.getTimeInMillis()>DriverReport.returnWeekMili()) {
                        continue;
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
        XSSFWorkbook workbook = new XSSFWorkbook();
        for (Route route : routeList) {
            XSSFSheet sheet = workbook.createSheet("PackerReports Route - " + route.getID());

            Map<String, String[]> data = new TreeMap<>();
            data.put("1", new String[]{"Doorstep Chef Packer Sheet  Week: " + DriverReport.returnWeekInt(), "", "", route.getDrivers().get(0).getDriver().getDriverName().split(" ")[0] + " - " + route.getDrivers().get(0).getDriver().getContactNumber(), "", "Route: " + route.getID()});
            data.put("2", new String[]{"", "", "", "", "", ""});
            data.put("3", new String[]{"Customer", "FamSize", "MealType", "Qty", "Allergies", "Exclutions"});

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
                    }
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
                            if (i != 8) {
                                borderStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
                            } else {
                                borderStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
                            }

                            if (i == 6 && ((String) arr[i]).contains("X")) {
                                cell.setCellValue("");
                                borderStyle.setFillPattern(XSSFCellStyle.FINE_DOTS);
                                borderStyle.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                                borderStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
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
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 2));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 4));

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

        }

        try {
            workbook.write(excelOut);
            excelOut.close();
            System.out.println("Done - Packer");
            packerLoadingObj.setVisible(false);
            packerLoadingObj.dispose();
            JOptionPane.showMessageDialog(null, "PackerReport Succesfully Generated", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException io) {
            packerLoadingObj.setVisible(false);
            packerLoadingObj.dispose();
            JOptionPane.showMessageDialog(null, "An error occured\nCould not create PackerReport", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error - Could not create new PackerReport: ");
            io.printStackTrace();
        }

    }

}
