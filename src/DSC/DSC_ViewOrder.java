
package DSC;

import static DSC.DBClass.ref;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aliens_Amina
 */
public class DSC_ViewOrder extends javax.swing.JFrame {

    boolean editClicked = false;
<<<<<<< HEAD
    boolean addClicked = false;
    ArrayList<Client> allclients = new ArrayList<>();
    ArrayList<Orders> allorders= new ArrayList<>();
=======

>>>>>>> origin/master
    /**
     * Creates new form DSC_Main
     */
    public DSC_ViewOrder() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        txfClientID.setEnabled(false);
        txfOrderID.setEnabled(false);
        cmbSuburbs.setEnabled(false);
        disableFieldsClient();
        disableFieldsOrder();
        btnSave.setEnabled(false);
       
    }

    public final void enableFieldsClient() {
        txfClientName.setEnabled(true);
        txfClientSurname.setEnabled(true);
        txfClientContactNo.setEnabled(true);
        txfClientAddress.setEnabled(true);
        txfAddInfo.setEnabled(true);
        txfClientEmail.setEnabled(true);
        txfAltNum.setEnabled(true);
        cmbSuburbs.setEnabled(true);
    }

    public final void disableFieldsClient() {
        txfClientName.setEnabled(false);
        txfClientSurname.setEnabled(false);
        txfClientContactNo.setEnabled(false);
        txfClientAddress.setEnabled(false);
        txfAddInfo.setEnabled(false);
        txfClientEmail.setEnabled(false);
        txfAltNum.setEnabled(false);
        cmbSuburbs.setEnabled(false);
        cmbSuburbs.setEnabled(false);
    }

    public final void clearFieldsClient() {
        txfClientID.setText(null);
        txfClientName.setText(null);
        txfClientSurname.setText(null);
        txfClientContactNo.setText(null);
        txfClientAddress.setText(null);
        txfAddInfo.setText(null);
        txfClientEmail.setText(null);
        txfAltNum.setText(null);
        cmbSuburbs.setSelectedIndex(0);
    }

    public final void enableFieldsOrder() {
        spnOrderFamilySize.setEnabled(true);
        txfOrderDuration.setEnabled(true);
        btnOrderDateAdd.setEnabled(true);
        btnRemove.setEnabled(true);
    }

    public final void disableFieldsOrder() {
        spnOrderFamilySize.setEnabled(false);
        spnOrderStartingDate.setEnabled(false);
        txfOrderRouteID.setEnabled(false);
        txfOrderDuration.setEnabled(false);
        btnOrderDateAdd.setEnabled(false);
        btnRemove.setEnabled(false);
    }

    public final void clearFieldsOrder() {
        txfOrderID.setText(null);
        spnOrderFamilySize.setValue(0);
        spnOrderStartingDate.setValue(null);
        txfOrderRouteID.setText(null);
        txfOrderDuration.setText(null);
    }

    private boolean checkEmpty() {
        boolean empty = false;

        if (txfClientName.getText().isEmpty() && txfClientSurname.getText().isEmpty() && txfClientContactNo.getText().isEmpty()
                && txfClientAddress.getText().isEmpty() && txfAddInfo.getText().isEmpty()
                && txfClientContactNo.getText().isEmpty() && txfAltNum.getText().isEmpty() && txfClientEmail.getText().isEmpty()
                && cmbSuburbs.getSelectedIndex() == 0) {
            empty = true;
        }

        return empty;
    }

    public void populateTable() {
<<<<<<< HEAD
       
        Firebase tableRef = ref.child("Clients");// Go to specific Table
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot ds) {
            for (DataSnapshot Data : ds.getChildren()) {
                Client c = new Client();
                c.setClientId(Data.getKey());
                c.setName((String)Data.child("Name").getValue());
                c.setSurname((String) Data.child("Surname").getValue());
                c.setContactNum((String) Data.child("ContactNum").getValue());
                c.setEmail((String) Data.child("Email").getValue());
                c.setSuburb((String) Data.child("Suburb").getValue());
                allclients.add(c);
                
//                String famSize = "";
//                String duration = "";
//           
//                for (Orders o : allorders) {
//                    if(o.getOrderClientid().equals(c.getClientId())){
//                        famSize = o.getFamilySize();
//                        duration = o.getDuration();
//                        break;
//                    }
//                }
           DefaultTableModel model = (DefaultTableModel) tblOrderTable.getModel();
        
           Object[] row = {c.getName(), c.getSurname(),c.getContactNum(), c.getEmail(), c.getSuburb()};
           model.addRow(row);
           model.fireTableDataChanged();
=======
        ClientData.getData();
        OrderData.getOrders();

        DefaultTableModel model = (DefaultTableModel) tblOrderTable.getModel();
        for (Client c : ClientData.allclients) {
            String clientid = c.getID();
            String name = c.getName();
            String surname = c.getSurname();
            String contactNum = c.getContactNumber();
            String email = c.getEmail();
            String suburb = c.getSuburb();

            String active = "";
            String duration = "";
            String familySize = "";

            for (Orders o : OrderData.allOrders) {
                String id = o.getOrderClientid();
                if (clientid.equals(id)) {
                    duration = o.getDuration();
                    familySize = o.getFamilySize();
                    active = o.isActive();
                }
            }
            Object[] row = {name, surname, contactNum, email, suburb, active, duration, familySize};
            model.addRow(row);
            model.fireTableDataChanged();
>>>>>>> origin/master
        }
            System.out.println(allclients);
    }

    @Override
    public void onCancelled(FirebaseError fe) {
        JOptionPane.showMessageDialog(null, "ERROR: " + fe);
    }
    });
       }
    
   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBackground = new javax.swing.JPanel();
        pnlHeading = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        pnlTable = new javax.swing.JPanel();
        txfSearch = new javax.swing.JTextField();
        cmbSearchColumn = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOrderTable = new javax.swing.JTable();
        btnDelete = new javax.swing.JButton();
        btnAdd = new javax.swing.JButton();
        lblSearchBy = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        pnlDetailsClient = new javax.swing.JPanel();
        lblClientDetails = new javax.swing.JLabel();
        lblClientID = new javax.swing.JLabel();
        lblClientName = new javax.swing.JLabel();
        lblClientSurname = new javax.swing.JLabel();
        lblClientContactNo = new javax.swing.JLabel();
        lblClientAddress = new javax.swing.JLabel();
        lblVehicleReg = new javax.swing.JLabel();
        txfClientID = new javax.swing.JTextField();
        txfClientName = new javax.swing.JTextField();
        txfClientSurname = new javax.swing.JTextField();
        txfClientContactNo = new javax.swing.JTextField();
        txfClientAddress = new javax.swing.JTextField();
        txfAddInfo = new javax.swing.JTextField();
        btnEditClient = new javax.swing.JButton();
        lblAltNum = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txfClientEmail = new javax.swing.JTextField();
        lblSuburb = new javax.swing.JLabel();
        txfAltNum = new javax.swing.JTextField();
        cmbSuburbs = new javax.swing.JComboBox<>();
        pnlDetails = new javax.swing.JPanel();
        lblOrdersDetails = new javax.swing.JLabel();
        lblOrderID = new javax.swing.JLabel();
        lblFamilySize = new javax.swing.JLabel();
        lblStartingDate = new javax.swing.JLabel();
        lblRouteID = new javax.swing.JLabel();
        lblDuration = new javax.swing.JLabel();
        txfOrderID = new javax.swing.JTextField();
        txfOrderRouteID = new javax.swing.JTextField();
        txfOrderDuration = new javax.swing.JTextField();
        spnOrderFamilySize = new javax.swing.JSpinner();
        spnOrderStartingDate = new javax.swing.JSpinner();
        btnEditOrder = new javax.swing.JButton();
        btnOrderDateAdd = new javax.swing.JButton();
        btnRemove = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMeals = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Meals Table");
        setBackground(new java.awt.Color(0, 0, 0));

        pnlBackground.setBackground(new java.awt.Color(0, 153, 0));

        pnlHeading.setBackground(new java.awt.Color(255, 255, 255));

        lblLogo.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        lblLogo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/DSC.png"))); // NOI18N

        lblName.setFont(new java.awt.Font("Tahoma", 1, 36)); // NOI18N
        lblName.setText("DOORSTEP CHEF");

        javax.swing.GroupLayout pnlHeadingLayout = new javax.swing.GroupLayout(pnlHeading);
        pnlHeading.setLayout(pnlHeadingLayout);
        pnlHeadingLayout.setHorizontalGroup(
            pnlHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeadingLayout.createSequentialGroup()
                .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHeadingLayout.setVerticalGroup(
            pnlHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lblLogo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlHeadingLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlTable.setBackground(new java.awt.Color(0, 204, 51));
        pnlTable.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        txfSearch.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfSearch.setMinimumSize(new java.awt.Dimension(6, 23));

        cmbSearchColumn.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbSearchColumn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Surname", "Contact Number", "Email", "Suburb", "Duration", "FamilySize" }));
        cmbSearchColumn.setPreferredSize(new java.awt.Dimension(115, 23));

        tblOrderTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Name", "Surname", "Contact Number", "Email", "Suburb", "Active", "Duration", "Family Size"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Boolean.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(tblOrderTable);
        populateTable();

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Bin.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Add.png"))); // NOI18N
        btnAdd.setText(" Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        lblSearchBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSearchBy.setText("Search by :");
        lblSearchBy.setMaximumSize(new java.awt.Dimension(62, 23));

        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 983, Short.MAX_VALUE)
                    .addGroup(pnlTableLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblSearchBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbSearchColumn, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAdd, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDelete, javax.swing.GroupLayout.DEFAULT_SIZE, 91, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
<<<<<<< HEAD
                    .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSearchColumn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSearchBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txfSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
=======
                    .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbSearchColumn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSearchBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txfSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTableLayout.createSequentialGroup()
                        .addGap(0, 114, Short.MAX_VALUE)
>>>>>>> origin/master
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDelete))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlDetailsClient.setBackground(new java.awt.Color(0, 204, 51));
        pnlDetailsClient.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblClientDetails.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblClientDetails.setText("Client's Details:");

        lblClientID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblClientID.setText("Client ID:");

        lblClientName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblClientName.setText("Client Name:");

        lblClientSurname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblClientSurname.setText("Client Surname:");

        lblClientContactNo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblClientContactNo.setText("Contact Number:");

        lblClientAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblClientAddress.setText("Address:");

        lblVehicleReg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblVehicleReg.setText("Additional Information:");

        txfClientID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfClientID.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txfClientName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfClientName.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txfClientSurname.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfClientSurname.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txfClientContactNo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfClientContactNo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txfClientAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfClientAddress.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txfAddInfo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfAddInfo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        btnEditClient.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEditClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Edit 2.png"))); // NOI18N
        btnEditClient.setText(" Edit");
        btnEditClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditClientActionPerformed(evt);
            }
        });

        lblAltNum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAltNum.setText("Alternative Number:");

        lblEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmail.setText("Email:");

        txfClientEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfClientEmail.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        lblSuburb.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSuburb.setText("Suburb:");

        txfAltNum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfAltNum.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        cmbSuburbs.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbSuburbs.setMaximumRowCount(10000);
        cmbSuburbs.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "None Selected" }));

        javax.swing.GroupLayout pnlDetailsClientLayout = new javax.swing.GroupLayout(pnlDetailsClient);
        pnlDetailsClient.setLayout(pnlDetailsClientLayout);
        pnlDetailsClientLayout.setHorizontalGroup(
            pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailsClientLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClientDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetailsClientLayout.createSequentialGroup()
                        .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSuburb, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                        .addGap(0, 31, Short.MAX_VALUE)
                        .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfClientEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(cmbSuburbs, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetailsClientLayout.createSequentialGroup()
                        .addComponent(btnEditClient)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlDetailsClientLayout.createSequentialGroup()
                        .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblClientSurname)
                            .addComponent(lblClientAddress)
                            .addComponent(lblClientContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(lblVehicleReg, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                                .addComponent(lblAltNum, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(lblClientName)
                            .addComponent(lblClientID))
                        .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDetailsClientLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txfClientContactNo, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txfAltNum)))
                            .addGroup(pnlDetailsClientLayout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(txfAddInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                                    .addComponent(txfClientAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txfClientSurname, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(txfClientID)
                                    .addComponent(txfClientName))))))
                .addContainerGap())
        );
        pnlDetailsClientLayout.setVerticalGroup(
            pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailsClientLayout.createSequentialGroup()
                .addGap(4, 4, 4)
                .addComponent(lblClientDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClientID)
                    .addComponent(txfClientID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClientName)
                    .addComponent(txfClientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClientSurname)
                    .addComponent(txfClientSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClientAddress)
                    .addComponent(txfClientAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVehicleReg)
                    .addComponent(txfAddInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClientContactNo)
                    .addComponent(txfClientContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAltNum)
                    .addComponent(txfAltNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(9, 9, 9)
                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEmail)
                    .addComponent(txfClientEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSuburb)
                    .addComponent(cmbSuburbs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEditClient)
                .addContainerGap())
        );

        pnlDetails.setBackground(new java.awt.Color(0, 204, 51));
        pnlDetails.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblOrdersDetails.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblOrdersDetails.setText("Order's Details:");

        lblOrderID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblOrderID.setText("Order ID:");

        lblFamilySize.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblFamilySize.setText("Family Size:");

        lblStartingDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblStartingDate.setText("Starting Date:");

        lblRouteID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRouteID.setText("Route ID:");

        lblDuration.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDuration.setText("Duration:");

        txfOrderID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfOrderID.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txfOrderRouteID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfOrderRouteID.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txfOrderDuration.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfOrderDuration.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        spnOrderFamilySize.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        spnOrderFamilySize.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        spnOrderStartingDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        spnOrderStartingDate.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1470311210147L), null, null, java.util.Calendar.DAY_OF_WEEK));

        btnEditOrder.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEditOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Edit 2.png"))); // NOI18N
        btnEditOrder.setText(" Edit");
        btnEditOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditOrderActionPerformed(evt);
            }
        });

        btnOrderDateAdd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnOrderDateAdd.setText("Add");
        btnOrderDateAdd.setName(""); // NOI18N
        btnOrderDateAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnOrderDateAddActionPerformed(evt);
            }
        });

        btnRemove.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnRemove.setText("Remove");
        btnRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDetailsLayout = new javax.swing.GroupLayout(pnlDetails);
        pnlDetails.setLayout(pnlDetailsLayout);
        pnlDetailsLayout.setHorizontalGroup(
            pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblOrdersDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlDetailsLayout.createSequentialGroup()
                        .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblDuration, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblRouteID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblStartingDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblFamilySize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblOrderID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pnlDetailsLayout.createSequentialGroup()
                                .addComponent(spnOrderStartingDate, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnOrderDateAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRemove))
                            .addComponent(txfOrderID, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(spnOrderFamilySize, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfOrderRouteID)
                            .addComponent(txfOrderDuration, javax.swing.GroupLayout.Alignment.LEADING)))
                    .addGroup(pnlDetailsLayout.createSequentialGroup()
                        .addComponent(btnEditOrder)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlDetailsLayout.setVerticalGroup(
            pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailsLayout.createSequentialGroup()
                .addComponent(lblOrdersDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrderID)
                    .addComponent(txfOrderID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblFamilySize)
                    .addComponent(spnOrderFamilySize, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblStartingDate)
                    .addComponent(spnOrderStartingDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnRemove)
                    .addComponent(btnOrderDateAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRouteID)
                    .addComponent(txfOrderRouteID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDuration)
                    .addComponent(txfOrderDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnEditOrder)
                .addContainerGap())
        );

        tblMeals.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Meal ID", "Meal type", "Allergy", "Exclusions", "Quantity", "Order ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, true, true, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(tblMeals);

        btnBack.setBackground(new java.awt.Color(255, 0, 0));
        btnBack.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setMnemonic('B');
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Save 2.png"))); // NOI18N
        btnSave.setText(" Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addComponent(pnlDetailsClient, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlDetails, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackgroundLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBack)))
                .addContainerGap())
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addComponent(pnlHeading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlDetails, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDetailsClient, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(11, 11, 11)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnBack)
                    .addComponent(btnSave))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEditClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditClientActionPerformed

        btnSave.setEnabled(true);
        enableFieldsClient();
        btnEditClient.setEnabled(false);
        editClicked = true;


    }//GEN-LAST:event_btnEditClientActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        String name = txfClientName.getText() + " " + txfClientSurname.getText();

        String message = "Are you sure you want to delete " + name + "?";
        int answer = JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.INFORMATION_MESSAGE);

        switch (answer) {
            case JOptionPane.YES_OPTION:
                JOptionPane.showMessageDialog(this, name + " will be deleted", "Delete Notification", JOptionPane.INFORMATION_MESSAGE);

                try {

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
                }
                break;

            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(this, name + " will not be deleted", "Delete Notification", JOptionPane.INFORMATION_MESSAGE);
                break;

            case JOptionPane.CANCEL_OPTION:

                break;
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (editClicked) {
            int ans = JOptionPane.showConfirmDialog(this, "Do you wish to discard unsaved changes?");
            switch (ans) {
                case JOptionPane.YES_OPTION:
                    btnSave.setVisible(false);
                    btnEditOrder.setEnabled(true);
                    disableFieldsClient();
                    disableFieldsOrder();
                    editClicked = false;
                    break;
                case JOptionPane.NO_OPTION:
                    break;
                default:
                    break;
            }
        } else {
            this.dispose();

        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        boolean back = false;
        if (btnSave.getText().equals("Save")) {
            short ID = Short.parseShort(txfClientID.getText().trim());
            String newName = txfClientName.getText().trim();
            String newSurname = txfClientSurname.getText().trim();
            String newAdditionalInfo = txfAddInfo.getText().trim();
            String newContactNumber = txfClientContactNo.getText().trim();
            String newAlternativeNo = txfAltNum.getText().trim();
            String newAddress = txfClientAddress.getText().trim();
            String newEmail = txfClientEmail.getText().trim();
            String newSuburb = (String) cmbSuburbs.getSelectedItem();

            ResultSet rs;
            try {
//                Connection c = DBClass.getConnection();
//                Statement stmt2 = c.createStatement();
//                String findSuburbID = "SELECT SuburbID FROM suburb_tb WHERE Suburb LIKE '"+ newSuburb +"';";
//                rs = stmt2.executeQuery(findSuburbID);
//                rs.next();
//                String newSuburbID = rs.getString(1);
//                
//                PreparedStatement stmt = c.prepareStatement("UPDATE doorstepchef.client_tb SET Name = ?,"
//                        + " Surname = ?,  Address = ?,AdditionalInfo  = ?,ContactNumber = ?, "
//                        + "AlternativeNumber = ?,Email = ?,SuburbID = ? WHERE ClientID = ?;");
//                stmt.setString(1, newName);
//                stmt.setString(2, newSurname);
//                stmt.setString(3, newAddress);
//                stmt.setString(4, newAdditionalInfo);
//                stmt.setString(5, newContactNumber);
//                stmt.setString(6, newAlternativeNo);
//                stmt.setString(7, newEmail);
//                stmt.setString(8, newSuburbID);
//                stmt.setShort(9, ID);
//                stmt.executeUpdate();
//                
//                int orderID =Integer.parseInt(txfOrderID.getText());
//                int newFamilySize = (int) spnOrderFamilySize.getValue();
//                Date newStartingDate = new java.sql.Date((long)spnOrderStartingDate.getValue());
//                String newRouteID = txfOrderRouteID.getText();
//                String newDuration = txfOrderDuration.getText();
//                String newOrderClientID = txfOrderClientID.getText();
//                
//                stmt = c.prepareStatement("UPDATE doorstepchef.order_tb SET FamilySize = ?,"
//                        + " StartingDate = ?,  RouteID = ?,Duration  = ?,Client_ID = ?, "
//                        + " WHERE OrderID = ?;");
//                
//                stmt.setInt(1, newFamilySize);
//                stmt.setDate(2, (java.sql.Date) newStartingDate);
//                stmt.setInt(3 ,Integer.parseInt(newRouteID));
//                stmt.setString(4,newDuration);
//                stmt.setString(5, newOrderClientID );
//                stmt.setInt(6, orderID);
//
//                JOptionPane.showMessageDialog(this, "Changes Saved");
//                //Refresh
//                back = true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
            back = true;
        } else if (btnSave.getText().equals("Add")) {
            //Add to database
            boolean empty = checkEmpty();
            if (false) {
                //empty
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                short newID = Short.parseShort(txfClientID.getText().trim());
                String newName = txfClientName.getText().trim();
                String newSurname = txfClientSurname.getText().trim();
                String newContactNo = txfClientContactNo.getText().trim();
                String newAddress = txfClientAddress.getText().trim();
                String newAddInfo = txfAddInfo.getText().trim();
                String newAltNum = txfAltNum.getText().trim();
                String newEmail = txfClientEmail.getText().trim();

                String query = "INSERT INTO doorstepchef.client_tb (`ClientID`, `Name`, `Surname`, `Address`,`AdditionalInfo`,"
                        + " `ContactNumber`, `AlternativeNumber`, `Email`,`SuburbID`) \n"
                        + "	VALUES (" + newID + ", '" + newName + "', '" + newSurname + "', '" + newAddress + "', '"
                        + newAddInfo + "', '" + newContactNo + "', '" + newAltNum + "', '" + newEmail + "', '0');";
                try {
//                    Connection c = DBClass.getConnection();
//                    Statement stmt = c.createStatement();
//                    stmt.executeUpdate(query);
//                    JOptionPane.showMessageDialog(this, "Saved");
//                    back = true;
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
            btnSave.setText("Save");
        }

        if (back) {
            disableFieldsClient();
            btnSave.setVisible(false);
            btnEditOrder.setEnabled(true);
            btnEditClient.setEnabled(true);
            editClicked = false;
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        if (editClicked) {
            int ans = JOptionPane.showConfirmDialog(this, "Do you wish to discard unsaved changes?");
            switch (ans) {
                case JOptionPane.YES_OPTION:
                    btnSave.setEnabled(false);
                    btnEditOrder.setEnabled(true);
                    disableFieldsClient();
                    disableFieldsOrder();
                    editClicked = false;
                    break;
                case JOptionPane.NO_OPTION:
                    break;
                default:
                    break;
            }
        } else {
            this.dispose();
            new DSC_Main().setVisible(true);
        }
        btnEditClient.setEnabled(true);
        btnEditOrder.setEnabled(true);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String column = (String) cmbSearchColumn.getSelectedItem();
        String searchFor = txfSearch.getText();

        if (txfSearch.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please enter search value!");

        } else {
            switch (column) {
                case "Name":
                    for (Client client : allclients) {
                        if (searchFor.equalsIgnoreCase(client.getName())) {
                            txfClientID.setText(client.getID());
                            txfClientName.setText(client.getName());
                            txfClientSurname.setText(client.getSurname());
                            txfClientAddress.setText(client.getAddress());
                            txfAddInfo.setText(client.getAdditionalInfo());
                            txfClientContactNo.setText(client.getContactNumber());
                            txfAltNum.setText(client.getAlternativeNumber());
                            txfClientEmail.setText(client.getEmail());
                            cmbSuburbs.setSelectedItem(client.getSuburb());
<<<<<<< HEAD
                            String id = client.getClientId();
                            for (Orders orders : allorders) {
=======
                            String id = client.getID();
                            for (Orders orders : OrderData.allOrders) {
>>>>>>> origin/master
                                if (id.equalsIgnoreCase(orders.getOrderClientid())) {
                                    txfOrderID.setText(orders.getOrderid());
                                    txfOrderDuration.setText(orders.getDuration());
                                    spnOrderFamilySize.setValue(orders.getFamilySize());
                                    spnOrderStartingDate.setValue(orders.getStartingDate());
                                    txfOrderRouteID.setText(orders.getRouteId());
                                }
                            }
                        }
                    }
                    break;
                case "Surname":
                    for (Client client : allclients) {
                        if (searchFor.equalsIgnoreCase(client.getSurname())) {
                            txfClientID.setText(client.getID());
                            txfClientName.setText(client.getName());
                            txfClientSurname.setText(client.getSurname());
                            txfClientAddress.setText(client.getAddress());
                            txfAddInfo.setText(client.getAdditionalInfo());
                            txfClientContactNo.setText(client.getContactNumber());
                            txfAltNum.setText(client.getAlternativeNumber());
                            txfClientEmail.setText(client.getEmail());
                            cmbSuburbs.setSelectedItem(client.getSuburb());
<<<<<<< HEAD
                            String id = client.getClientId();
                            for (Orders orders : allorders) {
=======
                            String id = client.getID();
                            for (Orders orders : OrderData.allOrders) {
>>>>>>> origin/master
                                if (id.equalsIgnoreCase(orders.getOrderClientid())) {
                                    txfOrderID.setText(orders.getOrderid());
                                    txfOrderDuration.setText(orders.getDuration());
                                    spnOrderFamilySize.setValue(orders.getFamilySize());
                                    spnOrderStartingDate.setValue(orders.getStartingDate());
                                    txfOrderRouteID.setText(orders.getRouteId());
                                }
                            }
                        }
                    }
                    break;
                case "Contact Number":
<<<<<<< HEAD
                    for (Client client : allclients) {
                        if (searchFor.equalsIgnoreCase(client.getContactNum())) {
                            txfClientID.setText(client.getClientId());
=======
                    for (Client client : ClientData.allclients) {
                        if (searchFor.equalsIgnoreCase(client.getContactNumber())) {
                            txfClientID.setText(client.getID());
>>>>>>> origin/master
                            txfClientName.setText(client.getName());
                            txfClientSurname.setText(client.getSurname());
                            txfClientAddress.setText(client.getAddress());
                            txfAddInfo.setText(client.getAdditionalInfo());
                            txfClientContactNo.setText(client.getContactNumber());
                            txfAltNum.setText(client.getAlternativeNumber());
                            txfClientEmail.setText(client.getEmail());
                            cmbSuburbs.setSelectedItem(client.getSuburb());
<<<<<<< HEAD
                            String id = client.getClientId();
                            for (Orders orders : allorders) {
=======
                            String id = client.getID();
                            for (Orders orders : OrderData.allOrders) {
>>>>>>> origin/master
                                if (id.equalsIgnoreCase(orders.getOrderClientid())) {
                                    txfOrderID.setText(orders.getOrderid());
                                    txfOrderDuration.setText(orders.getDuration());
                                    spnOrderFamilySize.setValue(orders.getFamilySize());
                                    spnOrderStartingDate.setValue(orders.getStartingDate());
                                    txfOrderRouteID.setText(orders.getRouteId());
                                }
                            }
                        }
                    }
                    break;
                case "Email":
                    for (Client client : allclients) {
                        if (searchFor.equalsIgnoreCase(client.getEmail())) {
                            txfClientID.setText(client.getID());
                            txfClientName.setText(client.getName());
                            txfClientSurname.setText(client.getSurname());
                            txfClientAddress.setText(client.getAddress());
                            txfAddInfo.setText(client.getAdditionalInfo());
                            txfClientContactNo.setText(client.getContactNumber());
                            txfAltNum.setText(client.getAlternativeNumber());
                            txfClientEmail.setText(client.getEmail());
                            cmbSuburbs.setSelectedItem(client.getSuburb());
<<<<<<< HEAD
                            String id = client.getClientId();
                            for (Orders orders : allorders) {
=======
                            String id = client.getID();
                            for (Orders orders : OrderData.allOrders) {
>>>>>>> origin/master
                                if (id.equalsIgnoreCase(orders.getOrderClientid())) {
                                    txfOrderID.setText(orders.getOrderid());
                                    txfOrderDuration.setText(orders.getDuration());
                                    spnOrderFamilySize.setValue(orders.getFamilySize());
                                    spnOrderStartingDate.setValue(orders.getStartingDate());
                                    txfOrderRouteID.setText(orders.getRouteId());
                                }
                            }
                        }
                    }
                    break;
                case "Suburb":
                    for (Client client : allclients) {
                        if (searchFor.equalsIgnoreCase(client.getSuburb())) {
                            txfClientID.setText(client.getID());
                            txfClientName.setText(client.getName());
                            txfClientSurname.setText(client.getSurname());
                            txfClientAddress.setText(client.getAddress());
                            txfAddInfo.setText(client.getAdditionalInfo());
                            txfClientContactNo.setText(client.getContactNumber());
                            txfAltNum.setText(client.getAlternativeNumber());
                            txfClientEmail.setText(client.getEmail());
                            cmbSuburbs.setSelectedItem(client.getSuburb());
<<<<<<< HEAD
                            String id = client.getClientId();
                            for (Orders orders : allorders) {
=======
                            String id = client.getID();
                            for (Orders orders : OrderData.allOrders) {
>>>>>>> origin/master
                                if (id.equalsIgnoreCase(orders.getOrderClientid())) {
                                    txfOrderID.setText(orders.getOrderid());
                                    txfOrderDuration.setText(orders.getDuration());
                                    spnOrderFamilySize.setValue(orders.getFamilySize());
                                    spnOrderStartingDate.setValue(orders.getStartingDate());
                                    txfOrderRouteID.setText(orders.getRouteId());
                                }
                            }
                        }
                    }
                    break;
                case "Duration":
                    for (Orders orders : allorders) {
                        if (searchFor.equalsIgnoreCase((orders.getDuration()))) {
                            txfOrderID.setText(orders.getOrderid());
                            txfOrderDuration.setText(orders.getDuration());
                            spnOrderFamilySize.setValue(orders.getFamilySize());
                            spnOrderStartingDate.setValue(orders.getStartingDate());
                            txfOrderRouteID.setText(orders.getRouteId());
                            String id = orders.getOrderClientid();
<<<<<<< HEAD
                            for (Client client :allclients) {
                                if (client.getClientId().equalsIgnoreCase(id)) {
                                    txfClientID.setText(client.getClientId());
=======
                            for (Client client : ClientData.allclients) {
                                if (client.getID().equalsIgnoreCase(id)) {
                                    txfClientID.setText(client.getID());
>>>>>>> origin/master
                                    txfClientName.setText(client.getName());
                                    txfClientSurname.setText(client.getSurname());
                                    txfClientAddress.setText(client.getAddress());
                                    txfAddInfo.setText(client.getAdditionalInfo());
                                    txfClientContactNo.setText(client.getContactNumber());
                                    txfAltNum.setText(client.getAlternativeNumber());
                                    txfClientEmail.setText(client.getEmail());
                                    cmbSuburbs.setSelectedItem(client.getSuburb());
                                }
                            }
                        }
                    }
                    break;
                case "Family Size":
                    for (Orders orders : allorders) {
                        if (searchFor.equalsIgnoreCase((orders.getFamilySize()))) {
                            txfOrderID.setText(orders.getOrderid());
                            txfOrderDuration.setText(orders.getDuration());
                            spnOrderFamilySize.setValue(orders.getFamilySize());
                            spnOrderStartingDate.setValue(orders.getStartingDate());
                            txfOrderRouteID.setText(orders.getRouteId());
                            String id = orders.getOrderClientid();
<<<<<<< HEAD
                            for (Client client : allclients) {
                                if (client.getClientId().equalsIgnoreCase(id)) {
                                    txfClientID.setText(client.getClientId());
=======
                            for (Client client : ClientData.allclients) {
                                if (client.getID().equalsIgnoreCase(id)) {
                                    txfClientID.setText(client.getID());
>>>>>>> origin/master
                                    txfClientName.setText(client.getName());
                                    txfClientSurname.setText(client.getSurname());
                                    txfClientAddress.setText(client.getAddress());
                                    txfAddInfo.setText(client.getAdditionalInfo());
                                    txfClientContactNo.setText(client.getContactNumber());
                                    txfAltNum.setText(client.getAlternativeNumber());
                                    txfClientEmail.setText(client.getEmail());
                                    cmbSuburbs.setSelectedItem(client.getSuburb());
                                }
                            } 
                        }
                    }
                    break;
            }
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveActionPerformed
        Date date = (Date) spnOrderStartingDate.getValue();
        date.setDate(date.getDate() - 7);
        spnOrderStartingDate.setValue(date);
    }//GEN-LAST:event_btnRemoveActionPerformed

    private void btnOrderDateAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOrderDateAddActionPerformed
        Date date = (Date) spnOrderStartingDate.getValue();
        date.setDate(date.getDate() + 7);
        spnOrderStartingDate.setValue(date);
    }//GEN-LAST:event_btnOrderDateAddActionPerformed

    private void btnEditOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditOrderActionPerformed

        enableFieldsOrder();
        btnEditOrder.setEnabled(false);
        btnSave.setEnabled(true);
        editClicked = true;
    }//GEN-LAST:event_btnEditOrderActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(DSC_ViewOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DSC_ViewOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DSC_ViewOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DSC_ViewOrder.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DSC_ViewOrder().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEditClient;
    private javax.swing.JButton btnEditOrder;
    private javax.swing.JButton btnOrderDateAdd;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbSearchColumn;
    private javax.swing.JComboBox<String> cmbSuburbs;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAltNum;
    private javax.swing.JLabel lblClientAddress;
    private javax.swing.JLabel lblClientContactNo;
    private javax.swing.JLabel lblClientDetails;
    private javax.swing.JLabel lblClientID;
    private javax.swing.JLabel lblClientName;
    private javax.swing.JLabel lblClientSurname;
    private javax.swing.JLabel lblDuration;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblFamilySize;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblOrderID;
    private javax.swing.JLabel lblOrdersDetails;
    private javax.swing.JLabel lblRouteID;
    private javax.swing.JLabel lblSearchBy;
    private javax.swing.JLabel lblStartingDate;
    private javax.swing.JLabel lblSuburb;
    private javax.swing.JLabel lblVehicleReg;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JPanel pnlDetailsClient;
    private javax.swing.JPanel pnlHeading;
    private javax.swing.JPanel pnlTable;
    private javax.swing.JSpinner spnOrderFamilySize;
    private javax.swing.JSpinner spnOrderStartingDate;
    private javax.swing.JTable tblMeals;
    private javax.swing.JTable tblOrderTable;
    private javax.swing.JTextField txfAddInfo;
    private javax.swing.JTextField txfAltNum;
    private javax.swing.JTextField txfClientAddress;
    private javax.swing.JTextField txfClientContactNo;
    private javax.swing.JTextField txfClientEmail;
    private javax.swing.JTextField txfClientID;
    private javax.swing.JTextField txfClientName;
    private javax.swing.JTextField txfClientSurname;
    private javax.swing.JTextField txfOrderDuration;
    private javax.swing.JTextField txfOrderID;
    private javax.swing.JTextField txfOrderRouteID;
    private javax.swing.JTextField txfSearch;
    // End of variables declaration//GEN-END:variables
}
