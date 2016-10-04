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
public class AccountantReport {

    private static ArrayList<Client> clients;
    private static int clientCount;
    private static File file;
    private static FileOutputStream excelOut;
    private static DSC_ReportLoading accountLoadObj;
    protected static boolean open = false;

    public static void getAccountantReport() {
        accountLoadObj = new DSC_ReportLoading();
        boolean fileFound = false;
        try {
            file = new File("AccountReport (" + DriverReport.returnWeekString() + ").xlsx");
            if (!file.exists()) {
                file.createNewFile();
            }
            excelOut = new FileOutputStream(file);
            fileFound = true;
        } catch (FileNotFoundException ex) {
            accountLoadObj.setVisible(false);
            accountLoadObj.dispose();
            JOptionPane.showMessageDialog(null, "Please close the excel file before using generating.", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error - Could not create new AccountReport: File currently in use.");
        } catch (IOException io) {
            accountLoadObj.setVisible(false);
            accountLoadObj.dispose();
            JOptionPane.showMessageDialog(null, "An error occured\nCould not create AccountReport", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error - Could not create new AccountReport: ");
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
                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    Client client = new Client(dataSnapshot.child("ClientID").getValue(String.class));
                    client.setAdditionalInfo(dataSnapshot.child("FamilySize").getValue(String.class));

                    Calendar start = null;

                    start = Calendar.getInstance();
                    start.setTimeInMillis(dataSnapshot.child("StartingDate").getValue(long.class));

                    if (start.getTimeInMillis() > DriverReport.returnWeekMili()) {
                        continue;
                    }

                    clients.add(client);
                }

                for (int i = 0; i < clients.size(); i++) {
                    getClient(clients.get(i), i);
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
                if (clientCount == clients.size()) {
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

        int lastIndex = 0;
        for (int letter = 0; letter < 26; letter++) {
            XSSFSheet sheet = workbook.createSheet("AccountReport " + (char) (65 + letter));
            Map<String, Object[]> data = new TreeMap<>();
            data.put("1", new Object[]{"Doorstep Chef Accountant Sheet", "", "", "", "", "", "", "Week: " + DriverReport.returnWeekInt(), "", ""});
            data.put("2", new Object[]{"", "", "", "", "", "", "", "", "", ""});
            data.put("3", new Object[]{"Customer", "Contact", "Fam", "4Day", "Mthly", "EFT", "Cash", "Date Paid", "Stay", "Comments"});

            int reduction = 0;
            for (int i = 0; i < clients.size(); i++) {
                Client client = clients.get(i);
                if (client.getSurname().toUpperCase().charAt(0) == (char) (65 + letter)) {
                    data.put((i + 4 - reduction) + "", new Object[]{
                        client.getName() + " " + client.getSurname(),
                        client.getContactNumber(),
                        client.getAdditionalInfo(),
                        "",
                        "",
                        "",
                        "",
                        "",
                        "",
                        ""
                    });
                } else {
                    reduction++;
                }
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
                            if (i != 9) {
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
                        if (i == 7) {
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
                if (key == 1) {
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
                    sheet.addMergedRegion(new CellRangeAddress(0, 0, 7, 9));
                }

            }
            sheet.setColumnWidth(0, (longestCustomer + 1) * 240);
            sheet.setColumnWidth(1, 11 * 240);
            sheet.setColumnWidth(2, 5 * 240);
            sheet.setColumnWidth(3, 5 * 240);
            sheet.setColumnWidth(4, 5 * 240);
            sheet.setColumnWidth(5, 5 * 240);
            sheet.setColumnWidth(6, 5 * 240);
            sheet.setColumnWidth(7, 10 * 240);
            sheet.setColumnWidth(8, 5 * 240);
            for (int i = 0; i < 9; i++) {
                totalSize -= sheet.getColumnWidth(i);
            }
            sheet.setColumnWidth(9, totalSize);

        }

        try {
            workbook.write(excelOut);
            excelOut.close();
            accountLoadObj.setVisible(false);
            accountLoadObj.dispose();
            if (!(DSC_Main.generateAllReports)) {
                accountLoadObj.setVisible(false);
                accountLoadObj.dispose();
                JOptionPane.showMessageDialog(null, "AccountReports Succesfully Generated", "Success", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Done - Accountant");
            } else {
                JOptionPane.showMessageDialog(null, "All Reports Generated");
                open = true;
            }

        } catch (IOException io) {
            accountLoadObj.setVisible(false);
            accountLoadObj.dispose();
            JOptionPane.showMessageDialog(null, "An error occured\nCould not create AccountReport", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error - Could not create new AccountReport: ");
            io.printStackTrace();
        }
    }
}
