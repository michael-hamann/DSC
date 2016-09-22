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
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import javax.swing.JOptionPane;
import org.apache.poi.hssf.util.HSSFColor;
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
public class DriverReport {

    private static ArrayList<Order> orderList;
    private static ArrayList<Route> routeList;
    private static int clientCounter = 0;
    private static int driverCounter = 0;

    public static void getClients() {
        routeList = new ArrayList<>();
        orderList = new ArrayList<>();
        Firebase ref = DBClass.getInstance().child("Orders");
        ref.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    Calendar start = Calendar.getInstance();
                    Calendar end = Calendar.getInstance();
                    if (dataSnapshot.child("EndDate").getValue(String.class).equals("-")) {
                        end = null;
                    } else {
                        end.setTimeInMillis(dataSnapshot.child("EndDate").getValue(long.class));
                    }

                    if (dataSnapshot.child("StartingDate").getValue(String.class).equals("-")) {
                        start = null;
                    } else {
                        start.setTimeInMillis(dataSnapshot.child("StartingDate").getValue(long.class));
                    }

                    if (!(returnWeekMili() >= start.getTimeInMillis())) {
                        continue;
                    }

                    ArrayList<Meal> meals = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : ds.child("Meals").getChildren()) {
                        meals.add(new Meal(dataSnapshot1.child("Quantity").getValue(int.class),
                                dataSnapshot1.child("MealType").getValue(String.class),
                                dataSnapshot1.child("Allergies").getValue(String.class),
                                dataSnapshot1.child("Exclusions").getValue(String.class)
                        ));
                    }
                    orderList.add(new Order(
                            dataSnapshot.getKey(),
                            true,
                            dataSnapshot.child("ClientID").getValue(String.class),
                            dataSnapshot.child("Duration").getValue(String.class),
                            start,
                            end,
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

                System.out.println("Success!!!");
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.println("Error: " + fe.getMessage());
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
                System.err.println("Error: Could not retrieve specified driver: " + fe.getMessage());
            }
        });
    }

    private static void createSpreadsheets() {
        XSSFWorkbook workbook = new XSSFWorkbook();
        for (Route route : routeList) {

            XSSFSheet sheet = workbook.createSheet("DriverReports Route - " + route.getID());

            Map<String, Object[]> data = new TreeMap<>();
            data.put("1", new Object[]{"Doorstep Chef Driver Sheet", "", "Week: " + returnWeekString(), "", "", "", "", "Driver: " + route.getDrivers().get(0).getDriver().getDriverName().split(" ")[0] + " - " + route.getDrivers().get(0).getDriver().getContactNumber(), "Route: " + route.getID()});
            data.put("2", new Object[]{"", "", "", "", "", "", "", "", ""});
            data.put("3", new Object[]{"Customer", "Contact", "Mon", "Tue", "Wed", "Thu", "Fri", "Address", "AdditionalInfo"});

            int counter = 4;
            for (Order order : orderList) {
                if (order.getRoute().equals(route.getID())) {
                    Client client = order.getClient();
                    data.put("" + counter, new Object[]{client.getName() + " " + client.getSurname(), client.getContactNumber(), "", "", "", "", "", client.getAddress().replaceAll("\n", ", "), client.getAdditionalInfo()});
                    counter++;
                }
            }
            Set<String> keySet = data.keySet();
            int rowNum = 0;
            int longestCustomer = 0;
            int totalWidth = 33800;
            for (String key : keySet) {

                Row row = sheet.createRow(rowNum);
                Object[] arr = data.get(key);

                for (int i = 0; i < arr.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue((String) arr[i]);
                    if (i == 0 && !key.equals("1") && longestCustomer < ((String) arr[i]).length()) {
                        longestCustomer = ((String) arr[i]).length();
                    }
                    XSSFCellStyle borderStyle = workbook.createCellStyle();

                    if (!(key.equals("1") || key.equals("2"))) {
                        borderStyle.setBottomBorderColor(IndexedColors.BLACK.getIndex());
                        borderStyle.setLeftBorderColor(IndexedColors.BLACK.getIndex());
                        borderStyle.setTopBorderColor(IndexedColors.BLACK.getIndex());
                        borderStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
                        if (key == "3") {
                            borderStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
                            borderStyle.setBorderLeft(XSSFCellStyle.BORDER_MEDIUM);
                            borderStyle.setBorderTop(XSSFCellStyle.BORDER_MEDIUM);
                            borderStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
                            borderStyle.setAlignment(HorizontalAlignment.CENTER);
                            borderStyle.setFillForegroundColor(HSSFColor.GREY_50_PERCENT.index);
                            borderStyle.setFillPattern(XSSFCellStyle.FINE_DOTS);
                            borderStyle.setFillBackgroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
                            XSSFFont font = workbook.createFont();
                            font.setColor(HSSFColor.WHITE.index);
                            font.setFontName("Calibri");
                            font.setFontHeightInPoints((short) 11);
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

                            if ((Integer.parseInt(key)) != keySet.size()) {
                                borderStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
                            } else {
                                borderStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
                            }
                            borderStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);

                        }
                        if ((i == 7 || i == 8) && !key.equals("3")) {
                            borderStyle.setAlignment(XSSFCellStyle.ALIGN_JUSTIFY);
                            borderStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_JUSTIFY);
                        }
                    } else {
                        if (i == 2 || i == 7) {
                            borderStyle.setAlignment(HorizontalAlignment.CENTER);
                        } else if (i == 8) {
                            borderStyle.setAlignment(HorizontalAlignment.RIGHT);
                        }

                        XSSFFont font = workbook.createFont();
                        font.setBold(true);
                        font.setFontHeightInPoints((short) 14);
                        borderStyle.setFont(font);
                    }

                    cell.setCellStyle(borderStyle);
                }
                rowNum++;
            }

            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 1));
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 2, 6));

            if (((longestCustomer + 1) * 240) > 4560) {
                sheet.setColumnWidth(0, (longestCustomer + 1) * 240);
            } else {
                sheet.setColumnWidth(0, 4560);
                longestCustomer = 18;
            }

            sheet.setColumnWidth(1, 12 * 240);
            for (int i = 0; i < 5; i++) {
                sheet.setColumnWidth(i + 2, 1000);
            }
            totalWidth = (totalWidth - (720 * 5 + 11 * 240 + (longestCustomer + 1) * 240)) / 3;
            sheet.setColumnWidth(7, totalWidth * 2);
            sheet.setColumnWidth(8, totalWidth);

        }
        
            try {
                File file = new File("DriverReports Route (" + returnWeekString() + ").xlsx");
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream excelOut = new FileOutputStream(file);
                workbook.write(excelOut);
                excelOut.close();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null, "An error occured\nCould not find Driver Roport", "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error - Could not create new Driver Report: ");
                ex.printStackTrace();
            } catch (IOException io) {
                JOptionPane.showMessageDialog(null, "An error occured\nCould not create Driver Roport", "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error - Could not create new Driver Report: ");
                io.printStackTrace();
            }
        JOptionPane.showMessageDialog(null, "DriverReports Succesfully Generated", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private static String returnWeekString() {
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
        firstWeek.setTimeInMillis(1440799200000l);
        int weeks = ((weekDate.get(Calendar.YEAR) - firstWeek.get(Calendar.YEAR)) * 52 + weekDate.get(Calendar.WEEK_OF_YEAR)) - firstWeek.get(Calendar.WEEK_OF_YEAR);
        return weeks;
    }

    public static long returnWeekMili() {
        Calendar weekDate = Calendar.getInstance();
        while (weekDate.get(Calendar.DAY_OF_WEEK) != 2) {
            weekDate.add(Calendar.DAY_OF_WEEK, -1);
        }
        weekDate.add(7, Calendar.DAY_OF_WEEK);
        return weekDate.getTimeInMillis();
    }
}
