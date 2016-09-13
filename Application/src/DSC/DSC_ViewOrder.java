package DSC;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aliens_Amina
 */
public class DSC_ViewOrder extends javax.swing.JFrame {
    boolean orderEdited = false;
    boolean editClicked = false;
    ArrayList<Client> allclients = new ArrayList<>();
    ArrayList<Order> allorders = new ArrayList<>();
    ArrayList<Order> orders1 = new ArrayList<>();

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
        cmbVeiw.setSelectedItem("Active");
        callSuburbs();
        setOrders();
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
        spnEndDate.setEnabled(true);
        btnEndDateAdd.setEnabled(true);
        btnEndDateRemove.setEnabled(true);
    }

    public final void disableFieldsOrder() {
        spnOrderFamilySize.setEnabled(false);
        spnOrderStartingDate.setEnabled(false);
        txfOrderRouteID.setEnabled(false);
        txfOrderDuration.setEnabled(false);
        btnOrderDateAdd.setEnabled(false);
        btnRemove.setEnabled(false);
        spnEndDate.setEnabled(false);
        btnEndDateAdd.setEnabled(false);
        btnEndDateRemove.setEnabled(false);

    }

    public final void clearFieldsOrder() {
        txfOrderID.setText(null);
        spnOrderFamilySize.setValue(0);
        spnOrderStartingDate.setValue("");
        txfOrderRouteID.setText(null);
        txfOrderDuration.setText(null);
        spnEndDate.setValue("");
    }

    private boolean checkEmpty() {
        boolean empty = false;

        if (txfClientName.getText().isEmpty() || txfClientSurname.getText().isEmpty() || txfClientContactNo.getText().isEmpty()
                || txfClientAddress.getText().isEmpty() || txfAddInfo.getText().isEmpty()
                || txfClientContactNo.getText().isEmpty() || txfAltNum.getText().isEmpty() || txfClientEmail.getText().isEmpty()
                ) {//|| cmbSuburbs.getSelectedIndex() == 0
            empty = true;
        }

        return empty;
    }

    public void setClients() {

        Firebase tableRef = DBClass.getInstance().child("Clients");// Go to specific Table
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot Data : ds.getChildren()) {
                    Client c = new Client();
                    c.setID(Data.getKey());
                    c.setName((String) Data.child("Name").getValue());
                    c.setSurname((String) Data.child("Surname").getValue());
                    c.setContactNumber((String) Data.child("ContactNum").getValue());
                    c.setEmail((String) Data.child("Email").getValue());
                    c.setSuburb((String) Data.child("Suburb").getValue());
                    c.setAdditionalInfo((String) Data.child("AdditionalInfo").getValue());
                    c.setAddress((String) Data.child("Address").getValue());
                    c.setAlternativeNumber((String) Data.child("AlternativeNumber").getValue());
                    allclients.add(c);

                }

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "ERROR: " + fe);
            }
        });
    }

    public void setOrders() {
        Firebase tr = DBClass.getInstance().child("Orders");// Go to specific Table
        tr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot Data : ds.getChildren()) {
                    ArrayList<Meal> allmeals = new ArrayList<>();
                    Calendar end;

//               SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-ddEhh:mm:ss.SSSZ");
//                try {
//                    Date date = sdf.parse(Data.child("StartingDate").getValue(String.class));
//                } catch (ParseException ex) {
//                    Logger.getLogger(DSC_ViewOrder.class.getName()).log(Level.SEVERE, null, ex);
//                }
//                 
//                Calendar start = sdf.getCalendar();
                    if (Data.child("EndDate").getValue().equals("-")) {
                        end = null;
                    } else {
                        end = (Calendar) Data.child("EndDate").getValue();
                    }

                    for (DataSnapshot Data2 : Data.getChildren()) {
                        for (DataSnapshot Data3 : Data2.getChildren()) {
                            Meal m = new Meal(Data3.child("Quantity").getValue(int.class), Data3.child("MealType").getValue(String.class),
                                    Data3.child("Allergies").getValue(String.class), Data3.child("Exclusions").getValue(String.class));
                            allmeals.add(m);
                        }
                    }
                    Order o = new Order(Data.getKey(), Data.child("Active").getValue(boolean.class), allclients.get(0),
                            Data.child("Duration").getValue(String.class), null, end, Data.child("RouteID").getValue(String.class),
                            allmeals, 0);
                    o.setFamilySize(Data.child("FamilySize").getValue(long.class));
                    
                    allorders.add(o);
                    String clientID = Data.child("ClientID").getValue(String.class);
                    for (Client c : allclients) {
                        if (c.getID().equals(clientID)) {
                            o.setClient(c);
                            break;
                        }
                    }

                }
                DefaultTableModel model = (DefaultTableModel) tblOrderTable.getModel();
                for (Order o : allorders) {
                    if (o.isActive()) {
                        Object[] row = {o.getClient().getName(), o.getClient().getSurname(), o.getClient().getContactNumber(),
                            o.getClient().getEmail(), o.getClient().getSuburb(), o.isActive(), o.getDuration(), o.getFamilySize()};
                        model.addRow(row);
                        model.fireTableDataChanged();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "ERROR: " + fe);
            }
        });

    }

    public void callSuburbs() {

    }

    public void setOrderTB(String col, String find, Order order, DefaultTableModel d) {

        orders1.add(order);
        Object[] row = {order.getClient().getName(), order.getClient().getSurname(), order.getClient().getContactNumber(),
            order.getClient().getEmail(), order.getClient().getSuburb(), order.isActive(), order.getDuration(), order.getFamilySize()};
        d.addRow(row);
        d.fireTableDataChanged();

    }

    public void setTextFields() {

        DefaultTableModel mealmodel = (DefaultTableModel) tblMeals.getModel();

        tblOrderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mealmodel.setRowCount(0);

                int row = tblOrderTable.rowAtPoint(evt.getPoint());

                txfClientID.setText(orders1.get(row).getClient().getID());
                txfClientName.setText(orders1.get(row).getClient().getName());
                txfClientSurname.setText(orders1.get(row).getClient().getSurname());
                txfClientAddress.setText(orders1.get(row).getClient().getAddress());
                txfAddInfo.setText(orders1.get(row).getClient().getAdditionalInfo());
                txfClientContactNo.setText(orders1.get(row).getClient().getContactNumber());
                txfAltNum.setText(orders1.get(row).getClient().getAlternativeNumber());
                txfClientEmail.setText(orders1.get(row).getClient().getEmail());
                cmbSuburbs.setSelectedItem(orders1.get(row).getClient().getSuburb());
                txfOrderID.setText(orders1.get(row).getID());
                txfOrderDuration.setText(orders1.get(row).getDuration());
                spnOrderFamilySize.setValue(orders1.get(row).getFamilySize());
                //spnOrderStartingDate.setValue(orders.getStartingDate());
                txfOrderRouteID.setText(orders1.get(row).getRoute());

                for (Meal meals : orders1.get(row).getMeals()) {
                    mealmodel.addRow(meals.returnObj());
                }
                mealmodel.fireTableDataChanged();
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
        lblSearchBy = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        cmbVeiw = new javax.swing.JComboBox<>();
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
        spnEndDate = new javax.swing.JSpinner();
        btnEndDateAdd = new javax.swing.JButton();
        btnEndDateRemove = new javax.swing.JButton();
        lblEndDate = new javax.swing.JLabel();
        btnDeactivate = new javax.swing.JButton();
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
        txfSearch.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
            public void propertyChange(java.beans.PropertyChangeEvent evt) {
                txfSearchPropertyChange(evt);
            }
        });

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
        setClients();

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Bin.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblSearchBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSearchBy.setText("Search by :");
        lblSearchBy.setMaximumSize(new java.awt.Dimension(62, 23));

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/search.png"))); // NOI18N
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        cmbVeiw.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Active", "Inactive" }));

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(pnlTableLayout.createSequentialGroup()
                        .addComponent(lblSearchBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbSearchColumn, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbVeiw, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(btnSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(cmbSearchColumn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSearchBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txfSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDelete)
                        .addComponent(cmbVeiw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 166, Short.MAX_VALUE)
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

        spnEndDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        spnEndDate.setModel(new javax.swing.SpinnerDateModel(new java.util.Date(1470311210147L), null, null, java.util.Calendar.DAY_OF_WEEK));

        btnEndDateAdd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEndDateAdd.setText("Add");
        btnEndDateAdd.setName(""); // NOI18N
        btnEndDateAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEndDateAddActionPerformed(evt);
            }
        });

        btnEndDateRemove.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEndDateRemove.setText("Remove");
        btnEndDateRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEndDateRemoveActionPerformed(evt);
            }
        });

        lblEndDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEndDate.setText("End Date:");

        btnDeactivate.setText("Deactivate");
        btnDeactivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeactivateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDetailsLayout = new javax.swing.GroupLayout(pnlDetails);
        pnlDetails.setLayout(pnlDetailsLayout);
        pnlDetailsLayout.setHorizontalGroup(
            pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDetailsLayout.createSequentialGroup()
                        .addComponent(lblOrdersDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(89, 89, 89))
                    .addGroup(pnlDetailsLayout.createSequentialGroup()
                        .addComponent(btnEditOrder)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDeactivate))
                    .addGroup(pnlDetailsLayout.createSequentialGroup()
                        .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlDetailsLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(lblRouteID, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pnlDetailsLayout.createSequentialGroup()
                                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(lblDuration, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblStartingDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblFamilySize, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblOrderID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDetailsLayout.createSequentialGroup()
                                .addComponent(spnEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnEndDateAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEndDateRemove))
                            .addGroup(pnlDetailsLayout.createSequentialGroup()
                                .addComponent(spnOrderStartingDate, javax.swing.GroupLayout.PREFERRED_SIZE, 95, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnOrderDateAdd, javax.swing.GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRemove))
                            .addComponent(txfOrderID)
                            .addComponent(spnOrderFamilySize)
                            .addComponent(txfOrderRouteID, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txfOrderDuration))))
                .addContainerGap())
        );
        pnlDetailsLayout.setVerticalGroup(
            pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailsLayout.createSequentialGroup()
                .addComponent(lblOrdersDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
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
                    .addComponent(spnEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEndDateAdd, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEndDateRemove)
                    .addComponent(lblEndDate))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfOrderRouteID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRouteID))
                .addGap(18, 18, 18)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDuration)
                    .addComponent(txfOrderDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditOrder)
                    .addComponent(btnDeactivate))
                .addContainerGap())
        );

        tblMeals.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Quantity", "Meal type", "Allergy", "Exclusions"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
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
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
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
        btnDelete.setEnabled(false);
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
                Firebase del = DBClass.getInstance().child("Clients/" + allclients.get(tblOrderTable.getSelectedRow()).getID() + "/");

                break;

            case JOptionPane.NO_OPTION:
                JOptionPane.showMessageDialog(this, name + " will not be deleted", "Delete Notification", JOptionPane.INFORMATION_MESSAGE);
                break;

            case JOptionPane.CANCEL_OPTION:

                break;
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        
        Firebase upd = DBClass.getInstance().child("Clients/" + allclients.get(tblOrderTable.getSelectedRow()).getID());
        boolean empty = checkEmpty();

        if (empty) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            Map<String, Object> clientinfo = new HashMap();
            clientinfo.put("Name", txfClientName.getText().trim());
            clientinfo.put("Surname", txfClientSurname.getText().trim());
            clientinfo.put("AdditionalInfo", txfAddInfo.getText().trim());
            clientinfo.put("AlternativeNumber", txfClientContactNo.getText().trim());
            clientinfo.put("ContactNum", txfAltNum.getText().trim());
            clientinfo.put("Address", txfClientAddress.getText().trim());
            clientinfo.put("Email", txfClientEmail.getText().trim());
            clientinfo.put("Suburb", (String) cmbSuburbs.getSelectedItem());
           
            upd.updateChildren(clientinfo);

//            if(orderEdited){
//                 Firebase updorder = DBClass.getInstance().child("Orders/" + allclients.get(tblOrderTable.getSelectedRow()).getID());
//            }
//
//            allclients.clear();
//            allorders.clear();
//            setClients();
//            setOrders();
            
            disableFieldsClient();
            btnSave.setVisible(false);
            btnEditOrder.setEnabled(true);
            btnEditClient.setEnabled(true);
            editClicked = false;
            btnDelete.setEnabled(true);
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        if (editClicked) {
            int ans = JOptionPane.showConfirmDialog(this, "Do you wish to discard unsaved changes?");
            switch (ans) {
                case JOptionPane.YES_OPTION:
                    disableFieldsClient();
                    disableFieldsOrder();
                    btnEditClient.setEnabled(true);
                    btnEditOrder.setEnabled(true);
                    btnDelete.setEnabled(true);
                    editClicked = false;
                    break;
                case JOptionPane.NO_OPTION:
                    btnSave.setEnabled(true);
                    break;
                default:
                    btnSave.setEnabled(true);
                    break;
            }
        } else {
            this.dispose();
            new DSC_Main().setVisible(true);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        String column = (String) cmbSearchColumn.getSelectedItem();
        String searchFor = txfSearch.getText();
        String veiw = (String) cmbVeiw.getSelectedItem();

        DefaultTableModel model = (DefaultTableModel) tblOrderTable.getModel();
        model.setRowCount(0);

        if (txfSearch.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please enter search value!");

        } else {
            switch (veiw) {
                case "All":
                    model.setRowCount(0);
                    switch (column) {
                        case "Name":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getName())) {
                                    setOrderTB(column, searchFor, orders, model);
                                }
                            }
                            break;
                        case "Surname":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getSurname())) {
                                    setOrderTB(column, searchFor, orders, model);
                                }
                            }
                            break;
                        case "Contact Number":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getContactNumber())) {
                                    setOrderTB(column, searchFor, orders, model);
                                }
                            }
                            break;
                        case "Email":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getEmail())) {
                                    setOrderTB(column, searchFor, orders, model);
                                }
                            }
                            break;
                        case "Suburb":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getSuburb())) {
                                    setOrderTB(column, searchFor, orders, model);
                                }
                            }
                            break;
                        case "Duration":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase((orders.getDuration()))) {
                                    setOrderTB(column, searchFor, orders, model);

                                }
                            }
                            break;
                        case "Family Size":
                            for (Order orders : allorders) {
                                if ((Integer.parseInt(searchFor)) == orders.getFamilySize()) {
                                    setOrderTB(column, searchFor, orders, model);
                                }
                            }
                            break;
                        default:
                            JOptionPane.showMessageDialog(rootPane, "'" + searchFor + "' not found!");
                    }

                    break;
                case "Active":
                    btnDeactivate.setEnabled(true);
                    switch (column) {
                        case "Name":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getName())) {
                                    if (orders.isActive()) {
                                        setOrderTB(column, searchFor, orders, model);
                                    }
                                }
                            }
                            break;
                        case "Surname":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getSurname())) {
                                    if (orders.isActive()) {
                                        setOrderTB(column, searchFor, orders, model);
                                    }
                                }
                            }
                            break;
                        case "Contact Number":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getContactNumber())) {
                                    if (orders.isActive()) {
                                        setOrderTB(column, searchFor, orders, model);
                                    }
                                }
                            }
                            break;
                        case "Email":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getEmail())) {
                                    if (orders.isActive()) {
                                        setOrderTB(column, searchFor, orders, model);
                                    }
                                }
                            }
                            break;
                        case "Suburb":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getSuburb())) {
                                    if (orders.isActive()) {
                                        setOrderTB(column, searchFor, orders, model);
                                    }
                                }
                            }
                            break;
                        case "Duration":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase((orders.getDuration()))) {
                                    if (orders.isActive()) {
                                        setOrderTB(column, searchFor, orders, model);
                                    }
                                }
                            }
                            break;
                        case "Family Size":
                            for (Order orders : allorders) {
                                if ((Integer.parseInt(searchFor)) == orders.getFamilySize()) {
                                    if (orders.isActive()) {
                                        setOrderTB(column, searchFor, orders, model);
                                    }
                                }
                            }
                            break;
                        default:
                            JOptionPane.showMessageDialog(rootPane, "'" + searchFor + "' not found!");
                    }

                    break;
                case "Inactive":
                    model.setRowCount(0);
                    btnDeactivate.setEnabled(false);
                    switch (column) {
                        case "Name":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getName())) {
                                    if (orders.isActive()) {
                                    } else {
                                        setOrderTB(column, searchFor, orders, model);
                                    }
                                }
                            }
                            break;
                        case "Surname":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getSurname())) {
                                    if (orders.isActive()) {
                                    } else {
                                        setOrderTB(column, searchFor, orders, model);
                                    }
                                }
                            }
                            break;
                        case "Contact Number":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getContactNumber())) {
                                    if (orders.isActive()) {
                                    } else {
                                        setOrderTB(column, searchFor, orders, model);
                                    }
                                }
                            }
                            break;
                        case "Email":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getEmail())) {
                                    if (orders.isActive()) {
                                    } else {
                                        setOrderTB(column, searchFor, orders, model);
                                    }
                                }
                            }
                            break;
                        case "Suburb":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getSuburb())) {
                                    if (orders.isActive()) {
                                    } else {
                                        setOrderTB(column, searchFor, orders, model);
                                    }
                                }
                            }
                            break;
                        case "Duration":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase((orders.getDuration()))) {
                                    if (orders.isActive()) {
                                    } else {
                                        setOrderTB(column, searchFor, orders, model);
                                    }
                                }
                            }
                            break;
                        case "Family Size":
                            for (Order orders : allorders) {
                                if ((Integer.parseInt(searchFor)) == orders.getFamilySize()) {
                                    if (orders.isActive()) {
                                    } else {
                                        setOrderTB(column, searchFor, orders, model);
                                    }
                                }
                            }
                            break;

                    }
            }
        }
        setTextFields();
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
        btnDelete.setEnabled(false);
        enableFieldsOrder();
        btnEditOrder.setEnabled(false);
        btnSave.setEnabled(true);
        editClicked = true;
        orderEdited = true;
    }//GEN-LAST:event_btnEditOrderActionPerformed

    private void btnEndDateAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEndDateAddActionPerformed
        Date date = (Date) spnOrderStartingDate.getValue();
        date.setDate(date.getDate() + 7);
        spnOrderStartingDate.setValue(date);
    }//GEN-LAST:event_btnEndDateAddActionPerformed

    private void btnEndDateRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEndDateRemoveActionPerformed
        Date date = (Date) spnOrderStartingDate.getValue();
        date.setDate(date.getDate() - 7);
        spnOrderStartingDate.setValue(date);
    }//GEN-LAST:event_btnEndDateRemoveActionPerformed

    private void txfSearchPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_txfSearchPropertyChange
        // TODO add your handling code here:
    }//GEN-LAST:event_txfSearchPropertyChange

    private void btnDeactivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeactivateActionPerformed
        
        Firebase updorder = DBClass.getInstance().child("Orders/" + allorders.get(tblOrderTable.getSelectedRow()).getID());
        
            Map<String, Object> orderinfo = new HashMap();
            orderinfo.put("Active", false);
            
            updorder.updateChildren(orderinfo);

    }//GEN-LAST:event_btnDeactivateActionPerformed

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
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDeactivate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEditClient;
    private javax.swing.JButton btnEditOrder;
    private javax.swing.JButton btnEndDateAdd;
    private javax.swing.JButton btnEndDateRemove;
    private javax.swing.JButton btnOrderDateAdd;
    private javax.swing.JButton btnRemove;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbSearchColumn;
    private javax.swing.JComboBox<String> cmbSuburbs;
    private javax.swing.JComboBox<String> cmbVeiw;
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
    private javax.swing.JLabel lblEndDate;
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
    private javax.swing.JSpinner spnEndDate;
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
