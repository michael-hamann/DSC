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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author Aliens_Keanu
 */
public class DriverReport {

    //private static ArrayList<Client> clientList = new ArrayList<>();
    private static ArrayList<Order> orderList = new ArrayList<>();
    private static ArrayList<Route> routeList = new ArrayList<>();
    private static int clientCounter = 0;

    public static void getClients() {
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
                    ArrayList<RouteDrivers> drivers = new ArrayList<>();
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child("Drivers").getChildren()) {
                        if (dataSnapshot1.child("EndDate").getValue(String.class).equals("-")) {
                            drivers.add(new RouteDrivers(dataSnapshot1.child("DriverName").getValue(String.class)));
                        }
                    }

                    routeList.add(new Route(
                            dataSnapshot.getKey(),
                            true,
                            dataSnapshot.child("TimeFrame").getValue(String.class),
                            suburbList,
                            drivers,
                            start,
                            end
                    ));
                }
                System.out.println("Success!!!");
                createSpreadsheets();
                
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.out.println("Error: " + fe.getMessage());
            }
        });
    }

    private static void createSpreadsheets() {

        for (Route route : routeList) {
            
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("DriverReports Route - " + route.getID());
            
            Map<String, Object[]> data = new TreeMap<>();
            data.put("0", new Object[]{"Route: " + route.getID(),"","Driver: " + route.getDrivers().get(0).getDriver().getDriverName().split(" ")[0] ,"" ,"" ,"" ,"" ,"" ,"" ,"" ,"", "Date: "+returnWeekString(), "" });
            data.put("1", new Object[]{"", "", "", "", "", "", "", "", "", ""});
            data.put("2", new Object[]{"Name", "Surname", "Contact", "Mon", "Tue", "Wed", "Thurs", "Fri", "Address", "AdditionalInfo"});
            
            int counter = 3;
            for (Order order : orderList) {
                if (order.getRoute().equals(route.getID())) {
                    Client client = order.getClient();
                    data.put("" + counter, new Object[]{client.getName(),client.getSurname(), client.getContactNumber() ,"" ,"" ,"" ,"" ,"", client.getAddress(), client.getAdditionalInfo()});
                    counter++;
                }
            }
            Set<String> keySet = data.keySet();
            int rowNum = 0;
            for (String key : keySet) {
                Row row = sheet.createRow(rowNum);
                Object[] arr = data.get(key);
                for (int i = 0; i < arr.length; i++) {
                    Cell cell = row.createCell(i);
                    cell.setCellValue((String)arr[i]);
                }
            }
            
            try {
                File file = new File("DriverReports Route - " + route.getID() + ".xlsx");
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
            } catch (IOException io){
                JOptionPane.showMessageDialog(null, "An error occured\nCould not create Driver Roport", "Error", JOptionPane.ERROR_MESSAGE);
                System.err.println("Error - Could not create new Driver Report: ");
                io.printStackTrace();
            }
        }

    }
    
    private static String returnWeekString(){
        Calendar weekDate = Calendar.getInstance();
        while (weekDate.get(Calendar.DAY_OF_WEEK) != 2) {            
            weekDate.add(Calendar.DAY_OF_WEEK, -1);
        }
        return new SimpleDateFormat("dd MMM yyyy").format(weekDate.getTime());
    }
    


}
