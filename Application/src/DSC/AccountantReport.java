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

    public static void getAccountantData() {
        clients = new ArrayList<>();
        Firebase ref = DBClass.getInstance();
        ref.child("Orders").orderByChild("Active").equalTo(true).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    Client client = new Client(dataSnapshot.child("ClientID").getValue(String.class));

                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child("Drivers").getChildren()) {
                        if (dataSnapshot1.child("EndDate").getValue(String.class).equals("-")) {
                            client.setAdditionalInfo(dataSnapshot1.child("DriverID").getValue(String.class));
                        }
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
        XSSFSheet sheet = workbook.createSheet("AccountReport - Week " + DriverReport.returnWeekInt());
        Map<String, Object[]> data = new TreeMap<>();
        data.put("1", new Object[]{"Doorstep Chef Accountant Sheet", "", "", "", "", "Week: " + DriverReport.returnWeekString(), "", ""});
        data.put("2", new Object[]{"", "", "", "", "", "", ""});
        data.put("3", new Object[]{"Name", "Surname", "Contact", "Driver", "EFT", "Cash", "Date Paid", "Stay"});

        clients.sort(new Comparator<Client>() {
            @Override
            public int compare(Client o1, Client o2) {
                return (o1.getSurname() + " " + o1.getName()).compareTo(o2.getSurname() + " " + o2.getName());
            }
        });

        for (int i = 0; i < clients.size(); i++) {
            Client client = clients.get(i);
            data.put(i + 4 + "", new Object[]{
                client.getName(),
                client.getSurname(),
                client.getContactNumber(),
                client.getAdditionalInfo(),
                "",
                "",
                "",
                ""
            });
        }
        Set<String> keySet = data.keySet();
        int longestDriverName = 0;
        int totalSize = 23873;
        
        for (int key = 1; key < keySet.size() + 1; key++) {
            Row row = sheet.createRow(key - 1);
            Object[] arr = data.get(key + "");
            
            for (int i = 0; i < arr.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue((String) arr[i]);
                XSSFCellStyle borderStyle = workbook.createCellStyle();

                System.out.println(longestDriverName + "--");
                System.out.println(i);
                if (i == 3 && longestDriverName< ((String)arr[i]).length()) {
                    longestDriverName = ((String)arr[i]).length();
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

                        if ((Integer.parseInt((key + ""))) != keySet.size()) {
                            borderStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
                        } else {
                            borderStyle.setBorderBottom(XSSFCellStyle.BORDER_MEDIUM);
                        }
                        borderStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);

                    }
                    if ((i == 7 || i == 8) && !(key + "").equals("3")) {
                        borderStyle.setAlignment(XSSFCellStyle.ALIGN_JUSTIFY);
                        borderStyle.setVerticalAlignment(XSSFCellStyle.VERTICAL_JUSTIFY);
                    }
                } else {
                    if (i == 4) {
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
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 3));
                sheet.addMergedRegion(new CellRangeAddress(0, 0, 4, 7));
            }

        }
        
        sheet.setColumnWidth(2, 11 * 240);
        sheet.setColumnWidth(3, (longestDriverName + 1) * 240);
        sheet.setColumnWidth(4, 5 * 240);
        sheet.setColumnWidth(5, 5 * 240);
        sheet.setColumnWidth(6, 13 * 240);
        sheet.setColumnWidth(7, 5 * 240);
        totalSize = (totalSize - (11 * 240 + 5 * 240 + 5 * 240 + 13 * 240 + 5 * 240 + (longestDriverName + 1) * 240)) / 2;
        sheet.setColumnWidth(0, totalSize);
        sheet.setColumnWidth(1, totalSize);
        
        try {
            File file = new File("AccountReport (" + DriverReport.returnWeekString() + ").xlsx");
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream excelOut = new FileOutputStream(file);
            workbook.write(excelOut);
            excelOut.close();
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "An error occured\nCould not find AccountReport", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error - Could not create new AccountReport: ");
            ex.printStackTrace();
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "An error occured\nCould not create AccountReport", "Error", JOptionPane.ERROR_MESSAGE);
            System.err.println("Error - Could not create new AccountReport: ");
            io.printStackTrace();
        }
        JOptionPane.showMessageDialog(null, "AccountReports Succesfully Generated", "Success", JOptionPane.INFORMATION_MESSAGE);

    }

}
