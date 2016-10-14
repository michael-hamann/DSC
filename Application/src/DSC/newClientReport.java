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
import java.util.Calendar;
import java.util.Comparator;
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
 * @author Aliens_Ross
 */
public class NewClientReport {

    private static int clientCount;
    private static DSC_ReportLoading newClientLoadObj;
    private static File file;
    private static FileOutputStream excelOut;
    private static ArrayList<Client> clients;
    private static int driverCount;

    public static void getNewClientsReport() {
        clientCount = 0;
        driverCount = 0;
        if (!DSC_Main.generateAllReports) {
            newClientLoadObj = new DSC_ReportLoading();
        }
        boolean fileFound = false;
        try {
            Path path = Paths.get("Reports\\Week " + DriverReport.returnWeekInt() + " (" + DriverReport.returnWeekString() + ")");
            Files.createDirectories(path);
            
            file = path.resolve("NewClientsReport Week - " + DriverReport.returnWeekInt() + ".xlsx").toFile();
            if (!file.exists()) {
                file.createNewFile();
            }
            excelOut = new FileOutputStream(file);
            fileFound = true;
        } catch (FileNotFoundException ex) {
            newClientLoadObj.setVisible(false);
            newClientLoadObj.dispose();
            JOptionPane.showMessageDialog(null, "Please close the excel file before using generating.", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error - Could not create new NewClientsReport: File currently in use.");
        } catch (IOException io) {
            newClientLoadObj.setVisible(false);
            newClientLoadObj.dispose();
            JOptionPane.showMessageDialog(null, "An error occured\nCould not create NewClientsReport", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error - Could not create new NewClientsReport: ");
            io.printStackTrace();
        }

        if (fileFound) {
            getActiveOrders();
        }
    }

    private static void getActiveOrders() {
        clients = new ArrayList<>();
        Firebase ref = DBClass.getInstance();
        ref.child("Orders").orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                boolean hasValues = false;
                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    Client client = new Client(dataSnapshot.child("ClientID").getValue(String.class));
                    client.setAdditionalInfo(dataSnapshot.child("FamilySize").getValue(String.class));
                    client.setAlternativeNumber(dataSnapshot.child("RouteID").getValue(String.class));
                    
                    Calendar start = null;

                    start = Calendar.getInstance();
                    start.setTimeInMillis(dataSnapshot.child("StartingDate").getValue(long.class));

                    if (start.getTimeInMillis() != DriverReport.returnWeekMili()) {
                        continue;
                    }
                    hasValues = true;

                    clients.add(client);
                }
                
                if (hasValues) {
                    for (int i = 0; i < clients.size(); i++) {
                        getClient(clients.get(i), i);
                        getDriverID(clients.get(i),i);
                    }
                } else {
                    createExcelReport();
                }

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.println("Error: " + fe.getMessage());
            }
        });
    }
    
    private static void getDriverID(Client client, int index){
        Firebase ref = DBClass.getInstance().child("Routes/" + client.getAlternativeNumber() + "/Drivers/");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot dsLevelOne : ds.child(ds.getChildrenCount()-1+"").getChildren()) {
                    client.setAdditionalInfo(dsLevelOne.child("RouteID").getValue(String.class));
                    client.setAlternativeNumber(dsLevelOne.child("driverID").getValue(String.class));
                }
                clients.set(index, client);
                
                getDriverName(client, index);
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.println("Error: " + fe.getMessage());
            }
        });
    }

    private static void getDriverName(Client client, int index){
        Firebase ref = DBClass.getInstance().child("Drivers/" + client.getAlternativeNumber());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                client.setAlternativeNumber(ds.child("DriverName").getValue(String.class));
                
                driverCount++;
                if (clientCount == clients.size() && driverCount == clients.size()) {
                    createExcelReport();
                }
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.println("Error: " + fe.getMessage());
            }
        });
    }
    
    private static void getClient(Client client, int index) {
        Firebase ref = DBClass.getInstance().child("Clients/" + client.getID());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                clients.get(index).setData(
                        ds.child("Name").getValue(String.class),
                        ds.child("Surname").getValue(String.class),
                        ds.child("ContactNum").getValue(String.class),
                        ds.child("AlternativeNumber").getValue(String.class),
                        ds.child("Email").getValue(String.class),
                        ds.child("Suburb").getValue(String.class),
                        ds.child("Address").getValue(String.class),
                        clients.get(index).getAdditionalInfo()
                );
                clientCount++;
                if (clientCount == clients.size() && driverCount == clients.size()) {
                    createExcelReport();
                }

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.println("Error: " + fe.getMessage());
            }
        });

    }

    private static void createExcelReport() {

        XSSFWorkbook workbook = new XSSFWorkbook();
        clients.sort(new Comparator<Client>() {
            @Override
            public int compare(Client o1, Client o2) {
                return (o1.getSurname() + " " + o1.getName()).compareTo(o2.getSurname() + " " + o2.getName());
            }
        });

        XSSFSheet sheet = workbook.createSheet("NewClient Report");

        Map<String, Object[]> data = new TreeMap<>();
        data.put("1", new Object[]{"Doorstep Chef NewClient Sheet", "", "", "Week: " + DriverReport.returnWeekInt(), ""});
        data.put("2", new Object[]{"", "", "", "", ""});
        data.put("3", new Object[]{"Customer", "Contact", "DriverName","R.ID", "Email", "Address"});

        int counter = 4;
        for (Client client : clients) {
            data.put(counter + "", new Object[]{client.getName() + " " + client.getSurname(), client.getContactNumber().substring(0, 3) + " " + client.getContactNumber().substring(3, 6) + " " + client.getContactNumber().substring(6, 10), client.getAlternativeNumber(), client.getAdditionalInfo(), client.getEmail(), client.getAddress()});
            counter++;
        }

        Set<String> keySet = data.keySet();
        int totalSize = 34900;
        int longestCustomer = 0;

        for (int key = 1; key < keySet.size() + 1; key++) {
            Row row = sheet.createRow(key - 1);
            Object[] arr = data.get(key + "");
            for (int i = 0; i < arr.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue((String) arr[i]);

                if (i == 0 && !(key + "").equals("1") && longestCustomer < ((String) arr[i]).length()) {
                    longestCustomer = ((String) arr[i]).length();
                }
                XSSFCellStyle borderStyle = workbook.createCellStyle();

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
                        if (i != 4) {
                            borderStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
                        } else {
                            borderStyle.setBorderRight(XSSFCellStyle.BORDER_MEDIUM);
                        }

                        if ((Integer.parseInt((key + ""))) != keySet.size()) {
                            borderStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
                        } else {
                            borderStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
                        }
                        borderStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);

                    }
                } else {
                    if (i == 3) {
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
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 3, 5));

        sheet.setColumnWidth(0, (longestCustomer + 2) * 240);
        sheet.setColumnWidth(1, 13 * 240);
        sheet.setColumnWidth(2, 11 * 240);
        sheet.setColumnWidth(3, 5 * 240);
        
        for (int i = 0; i < 4; i++) {
            totalSize -= sheet.getColumnWidth(i);
        }
        sheet.setColumnWidth(4, totalSize / 2);
        sheet.setColumnWidth(5, totalSize / 2);

        Row rowDate = sheet.createRow(keySet.size() + 1);
        Cell cell = rowDate.createCell(0);
        SimpleDateFormat sf = new SimpleDateFormat("EEE MMM yyyy HH:mm:ss");

        cell.setCellValue(sf.format(Calendar.getInstance().getTime()));
        XSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(XSSFCellStyle.ALIGN_RIGHT);
        cell.setCellStyle(cellStyle);
        sheet.addMergedRegion(new CellRangeAddress(keySet.size() + 1, keySet.size() + 1, 0, 5));

        try {
            workbook.write(excelOut);
            excelOut.close();
            System.out.println("Done - New Client");
            if (!(DSC_Main.generateAllReports)) {
                newClientLoadObj.setVisible(false);
                newClientLoadObj.dispose();
                JOptionPane.showMessageDialog(null, "NewClientReports Succesfully Generated", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                DSC_Main.reportsDone++;
                if (DSC_Main.reportsDone == 5) {
                    DSC_Main.reportsDone();
                }
            }

        } catch (IOException io) {
            newClientLoadObj.setVisible(false);
            newClientLoadObj.dispose();
            JOptionPane.showMessageDialog(null, "An error occured\nCould not create NewClientReports", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error - Could not create new NewClientReports: ");
            io.printStackTrace();
        }

    }

}
