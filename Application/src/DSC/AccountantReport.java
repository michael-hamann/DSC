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
import org.apache.poi.ss.usermodel.Row;
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
                    clients.add(new Client(dataSnapshot.child("ClientID").getValue(String.class)));
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
                        ds.child("AdditionalInfo").getValue(String.class)
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
        data.put("1", new Object[]{"Doorstep Chef Accountant Sheet", "", "", "Week: " + DriverReport.returnWeekString(), "", "", ""});
        data.put("2", new Object[]{"", "", "", "", "", "", ""});
        data.put("3", new Object[]{"Name", "Surname", "Contact", "EFT", "Cash", "Date Paid", "Continue"});

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
                "",
                "",
                "",
                ""
            });
        }
        Set<String> keySet = data.keySet();
        int rowNum = 0;
        for (int i = 0; i < keySet.size(); i++) {
            System.out.println(i);
            Row row = sheet.createRow(i);
            Object[] arr = data.get(i + 1 + "");

            for (int j = 0; j < arr.length; j++) {
                Cell cell = row.createCell(j);
                cell.setCellValue((String) arr[j]);
            }
            rowNum++;
        }

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
