package DSC;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aliens_Amina
 */
public class DSC_ViewOrder extends javax.swing.JFrame {

    int tbcounter = 30;
    int count = 0;
    
    boolean search = true;
    boolean orderEdited = false;
    boolean editClicked = false;
    boolean orderSelected = false;

    ArrayList<Client> allclients = new ArrayList<>();
    ArrayList<Order> allorders = new ArrayList<>();
    ArrayList<Order> orders1 = new ArrayList<>();

    ArrayList<String> suburbs = new ArrayList<>();
    ArrayList<String> activeSuburbs = new ArrayList<>();

    /**
     * Creates new form DSC_Main
     */
    public DSC_ViewOrder() {
        initComponents();

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        txfClientID.setEnabled(false);
        txfOrderID.setEnabled(false);
        txfOrderRouteID.setEnabled(false);
        cmbSuburbs.setEnabled(false);

        disableFieldsClient();
        disableFieldsOrder();

        btnSave.setEnabled(false);
        cmbVeiw.setSelectedItem("Active");
        cmbSuburbs.removeAllItems();
        cmbSuburbs.addItem("Collection");
        btnDeactivate.setVisible(true);

        saveClient();
        saveOrder();
        callActiveSuburbs();
        callAllSuburbs();
        setTextFields();

        Date datenow = Calendar.getInstance().getTime();
        SpinnerDateModel smb = new SpinnerDateModel(datenow, null, null, Calendar.HOUR_OF_DAY);
        spnStartDate.setModel(smb);
        spnEndDate.setModel(smb);
        JSpinner.DateEditor sd = new JSpinner.DateEditor(spnStartDate, "dd-MM-yyyy");
        JSpinner.DateEditor ed = new JSpinner.DateEditor(spnEndDate, "dd-MM-yyyy");
        spnStartDate.setEditor(sd);
        spnEndDate.setEditor(ed);

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
        orderSelected = false;
    }
    

    public final void enableFieldsOrder() {
        spnOrderFamilySize.setEnabled(true);
        cmbDuration.setEnabled(true);
        spnEndDate.setEnabled(true);
        spnStartDate.setEnabled(true);
        txfOrderRouteID.setEnabled(true);
        btnAddMeal.setEnabled(true);
        btnRemoveMeal.setEnabled(true);
        btnDeactivate.setEnabled(true);
    }

    public final void disableFieldsOrder() {
        spnOrderFamilySize.setEnabled(false);
        spnStartDate.setEnabled(false);
        txfOrderRouteID.setEnabled(false);
        cmbDuration.setEnabled(false);
        spnEndDate.setEnabled(false);
        btnAddMeal.setEnabled(false);
        btnRemoveMeal.setEnabled(false);
        btnDeactivate.setEnabled(false);

    }

    public final void clearFieldsOrder() {
        txfOrderID.setText(null);
        spnOrderFamilySize.setValue(0);
        //spnOrderStartingDate.setValue("");
        txfOrderRouteID.setText(null);
        cmbDuration.setSelectedIndex(0);
        //spnEndDate.setValue("");
        DefaultTableModel model = (DefaultTableModel) tblOrderTable.getModel();
        model.setRowCount(0);
        boolean orderSelected = false;
    }
    

    private boolean checkEmpty() {
        boolean empty = false;

        if (txfClientName.getText().isEmpty() || txfClientSurname.getText().isEmpty() || txfClientContactNo.getText().isEmpty()
                || txfClientAddress.getText().isEmpty() || txfClientContactNo.getText().isEmpty() || txfClientEmail.getText().isEmpty()) {
            empty = true;
        }

        return empty;
    }
    

    public void callActiveSuburbs() {
        Firebase ref = DBClass.getInstance().child("Routes");
        ref.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    String subList[] = dataSnapshot.child("Suburbs").getValue(String[].class);
                    if (subList[0].equals("Collection")) {
                        continue;
                    } else {
                        for (int i = 0; i < subList.length; i++) {
                            activeSuburbs.add(subList[i]);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "Could not fetch suburbs.\nCheck logs for error report.", "Suburb Error", JOptionPane.ERROR_MESSAGE);
                System.err.print("Database connection error (Suburb): " + fe);
            }
        });
        activeSuburbs.add("Collection");
    }

    public void callAllSuburbs() {
        Firebase ref = DBClass.getInstance().child("Routes");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    String subArr[] = dataSnapshot.child("Suburbs").getValue(String[].class);
                    if (subArr[0].equals("Collection")) {
                        continue;
                    } else {
                        for (int i = 0; i < subArr.length; i++) {
                            suburbs.add(subArr[i]);
                            cmbSuburbs.addItem(subArr[i]);
                        }
                    }
                }

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "Could not fetch suburbs.\nCheck logs for error report.", "Suburb Error", JOptionPane.ERROR_MESSAGE);
                System.err.print("Database connection error (Suburb): " + fe);
            }
        });

    }
    

    public void setOrderTB(Order order, DefaultTableModel d) {
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
                orderSelected = true;
                if (orderEdited == false || editClicked == false) {
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
                    cmbSuburbs.setSelectedItem(orders1.get(row).getClient().getSuburb().trim());
                    txfOrderID.setText(orders1.get(row).getID());
                    cmbDuration.setSelectedItem(orders1.get(row).getDuration());

                    Date date = orders1.get(row).getStartingDate().getTime();
                    SpinnerDateModel smb = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
                    spnStartDate.setModel(smb);
                    JSpinner.DateEditor d = new JSpinner.DateEditor(spnStartDate, "dd-MM-yyyy");
                    spnStartDate.setEditor(d);

                    spnOrderFamilySize.setValue(orders1.get(row).getFamilySize());
                    if (orders1.get(row).getEndDate() == null) {
                    } else {
                        Date enddate = orders1.get(row).getEndDate().getTime();
                        SpinnerDateModel endD = new SpinnerDateModel(enddate, null, null, Calendar.HOUR_OF_DAY);
                        spnEndDate.setModel(endD);
                        JSpinner.DateEditor endDE = new JSpinner.DateEditor(spnEndDate, "dd-MM-yyyy");
                        spnStartDate.setEditor(endDE);
                    }
                    txfOrderRouteID.setText(orders1.get(row).getRoute());

                    for (Meal meals : orders1.get(row).getMeals()) {
                        mealmodel.addRow(meals.returnObj());
                    }
                    mealmodel.fireTableDataChanged();
                }
            }
        });

    }
    

    public void saveOrder() {
        Firebase ord = DBClass.getInstance().child("Orders");// Go to specific Table

        ord.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot ds, String previousChildKey) {
                ArrayList<Meal> allmeals = new ArrayList<>();
                Calendar endDate;

                Calendar startingDate = Calendar.getInstance();
                startingDate.setTimeInMillis(ds.child("StartingDate").getValue(long.class));

                if (ds.child("EndDate").getValue(String.class).equals("-")) {
                    endDate = null;
                } else {
                    endDate = Calendar.getInstance();
                    endDate.setTimeInMillis(ds.child("EndDate").getValue(long.class));
                }

                for (DataSnapshot Data2 : ds.getChildren()) {
                    for (DataSnapshot Data3 : Data2.getChildren()) {
                        Meal m = new Meal(Data3.child("Quantity").getValue(int.class), Data3.child("MealType").getValue(String.class),
                                Data3.child("Allergies").getValue(String.class), Data3.child("Exclusions").getValue(String.class));
                        allmeals.add(m);
                    }
                }
                Order o = new Order(ds.getKey(), ds.child("Active").getValue(boolean.class), allclients.get(0),
                        ds.child("Duration").getValue(String.class), startingDate, endDate, ds.child("RouteID").getValue(String.class),
                        allmeals, 0);
                o.setFamilySize(ds.child("FamilySize").getValue(int.class));

                String clientID = ds.child("ClientID").getValue(String.class);
                for (Client c : allclients) {
                    if (c.getID().equals(clientID)) {
                        o.setClient(c);
                        break;
                    }
                }

                allorders.add(o);
                DefaultTableModel model = (DefaultTableModel) tblOrderTable.getModel();

                if (o.isActive() && count < tbcounter) {
                    setOrderTB(o, model);
                    count++;
                }

            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                String id = ds.getKey();
                for (int i = 0; i < allorders.size(); i++) {
                    if (allorders.get(i).getID().equals(id)) {
                        Order o = allorders.get(i);
                        o.setActive(ds.child("Active").getValue(boolean.class));
                        o.setDuration(ds.child("Duration").getValue(String.class));
                        o.setRoute(ds.child("RouteID").getValue(String.class));
                        o.setFamilySize(ds.child("FamilySize").getValue(int.class));
                        Calendar startingDate = Calendar.getInstance();
                        Calendar endDate = Calendar.getInstance();
                        startingDate.setTimeInMillis(ds.child("StartingDate").getValue(long.class));
                        o.setStartingDate(startingDate);

                        if (ds.child("EndDate").getValue(String.class).equals("-")) {
                            endDate = null;
                        } else {
                            endDate = Calendar.getInstance();
                            endDate.setTimeInMillis(ds.child("EndDate").getValue(long.class));
                        }
                        o.setEndDate(endDate);
                        ArrayList<Meal> m = new ArrayList();

                        for (DataSnapshot key : ds.child("Meals").getChildren()) {

                            Meal meal = new Meal(key.child("Quantity").getValue(int.class), key.child("MealType").getValue(String.class),
                                    key.child("Allergies").getValue(String.class), key.child("Exclusions").getValue(String.class));
                            m.add(meal);
                        }

                        o.setMeals(m);
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
                String id = ds.getKey();
                for (Order order : allorders) {
                    if (order.getID().equals(id)) {
                        allorders.remove(order);
                    }
                }
                JOptionPane.showMessageDialog(rootPane, id + " has been deleted", "Delete Notification", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {

            }

            @Override
            public void onCancelled(FirebaseError fe) {

            }
        });
    }

    public void saveClient() {
        Firebase tableRef = DBClass.getInstance().child("Clients");// Go to specific Table

        tableRef.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            @Override
            public void onChildAdded(DataSnapshot ds, String previousChildKey) {
                Client c = new Client();
                c.setID(ds.getKey());
                c.setName((String) ds.child("Name").getValue());
                c.setSurname((String) ds.child("Surname").getValue());
                c.setContactNumber((String) ds.child("ContactNum").getValue());
                c.setEmail((String) ds.child("Email").getValue());
                c.setSuburb((String) ds.child("Suburb").getValue());
                c.setAdditionalInfo((String) ds.child("AdditionalInfo").getValue());
                c.setAddress((String) ds.child("Address").getValue());
                c.setAlternativeNumber((String) ds.child("AlternativeNumber").getValue());
                allclients.add(c);
            }

            @Override
            public void onChildChanged(DataSnapshot ds, String string) {
                for (Client client : allclients) {
                    if (ds.getKey().equals(client.getID())) {
                        client.setID(ds.getKey());
                        client.setName(ds.child("Name").getValue(String.class));
                        System.out.println(ds.child("Name").getValue(String.class));
                        client.setSurname(ds.child("Surname").getValue(String.class));
                        client.setContactNumber((String) ds.child("ContactNum").getValue(String.class));
                        client.setEmail(ds.child("Email").getValue(String.class));
                        client.setSuburb(ds.child("Suburb").getValue(String.class));
                        client.setAdditionalInfo(ds.child("AdditionalInfo").getValue(String.class));
                        client.setAddress(ds.child("Address").getValue(String.class));
                        client.setAlternativeNumber(ds.child("AlternativeNumber").getValue(String.class));
                    }
                }

            }

            @Override
            public void onChildMoved(DataSnapshot ds, String string) {

            }

            @Override
            public void onCancelled(FirebaseError fe) {

            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
                for (Client c : allclients) {
                    if (ds.getKey().equals(c.getID())) {
                        allclients.remove(c);
                    }
                }
            }
        });
    }

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
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
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
        spnOrderFamilySize = new javax.swing.JSpinner();
        btnEditOrder = new javax.swing.JButton();
        lblEndDate = new javax.swing.JLabel();
        btnDeactivate = new javax.swing.JButton();
        cmbDuration = new javax.swing.JComboBox<>();
        spnStartDate = new javax.swing.JSpinner();
        spnEndDate = new javax.swing.JSpinner();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblMeals = new javax.swing.JTable();
        btnBack = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnAddMeal = new javax.swing.JButton();
        btnRemoveMeal = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("View Order");
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
        cmbSearchColumn.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Name", "Surname", "Contact Number", "Email", "Suburb", "Duration", "Family Size" }));
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

        btnDelete.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Bin.png"))); // NOI18N
        btnDelete.setText("Delete");
        btnDelete.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });

        lblSearchBy.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSearchBy.setText("Search by :");
        lblSearchBy.setMaximumSize(new java.awt.Dimension(62, 23));

        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/search.png"))); // NOI18N
        btnSearch.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });

        cmbVeiw.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Active", "Inactive" }));

        btnPrevious.setText("<");
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnNext.setText(">");
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
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
                        .addComponent(btnPrevious)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnDelete, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        pnlTableLayout.setVerticalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cmbSearchColumn, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblSearchBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txfSearch, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnDelete)
                        .addComponent(cmbVeiw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnPrevious))
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(11, 11, 11)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
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
        btnEditClient.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
                    .addGroup(pnlDetailsClientLayout.createSequentialGroup()
                        .addComponent(lblClientDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEditClient))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetailsClientLayout.createSequentialGroup()
                        .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSuburb, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE))
                        .addGap(0, 31, Short.MAX_VALUE)
                        .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txfClientEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(cmbSuburbs, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClientDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditClient))
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
                .addContainerGap(32, Short.MAX_VALUE))
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

        spnOrderFamilySize.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        spnOrderFamilySize.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        btnEditOrder.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEditOrder.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Edit 2.png"))); // NOI18N
        btnEditOrder.setText(" Edit");
        btnEditOrder.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditOrder.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditOrderActionPerformed(evt);
            }
        });

        lblEndDate.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEndDate.setText("End Date:");

        btnDeactivate.setText("Deactivate");
        btnDeactivate.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeactivate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeactivateActionPerformed(evt);
            }
        });

        cmbDuration.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Monday - Thursday", "Monday - Friday" }));

        javax.swing.GroupLayout pnlDetailsLayout = new javax.swing.GroupLayout(pnlDetails);
        pnlDetails.setLayout(pnlDetailsLayout);
        pnlDetailsLayout.setHorizontalGroup(
            pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlDetailsLayout.createSequentialGroup()
                        .addComponent(lblOrdersDetails, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditOrder))
                    .addGroup(pnlDetailsLayout.createSequentialGroup()
                        .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlDetailsLayout.createSequentialGroup()
                                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(lblDuration, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblStartingDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblFamilySize, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(lblOrderID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(lblRouteID, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfOrderID)
                            .addComponent(spnOrderFamilySize)
                            .addComponent(txfOrderRouteID, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(cmbDuration, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(spnStartDate)
                            .addComponent(spnEndDate)))
                    .addGroup(pnlDetailsLayout.createSequentialGroup()
                        .addComponent(btnDeactivate)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlDetailsLayout.setVerticalGroup(
            pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailsLayout.createSequentialGroup()
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblOrdersDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditOrder))
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
                    .addComponent(spnStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEndDate)
                    .addComponent(spnEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfOrderRouteID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRouteID))
                .addGap(18, 18, 18)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDuration)
                    .addComponent(cmbDuration, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnDeactivate)
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
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Save 2.png"))); // NOI18N
        btnSave.setText(" Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnAddMeal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Add.png"))); // NOI18N
        btnAddMeal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddMeal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMealActionPerformed(evt);
            }
        });

        btnRemoveMeal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Bin.png"))); // NOI18N
        btnRemoveMeal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnRemoveMeal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRemoveMealActionPerformed(evt);
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
                        .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE)
                            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                                .addComponent(btnAddMeal, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRemoveMeal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSave)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnBack)))))
                .addContainerGap())
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addComponent(pnlHeading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlTable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnSave)
                            .addComponent(btnBack)
                            .addComponent(btnAddMeal)
                            .addComponent(btnRemoveMeal)))
                    .addComponent(pnlDetailsClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
        if (orderSelected) {
            btnDelete.setEnabled(false);
            btnSave.setEnabled(true);
            enableFieldsClient();
            btnEditClient.setEnabled(false);
            editClicked = true;
            orderEdited = true;
        } else {
            JOptionPane.showMessageDialog(rootPane, "No order has been selected to edit!");
        }
    }//GEN-LAST:event_btnEditClientActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        if (orderSelected) {
            String name = txfClientName.getText() + " " + txfClientSurname.getText();

            String message = "Are you sure you want to delete an order of " + name + "?";
            int answer = JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.INFORMATION_MESSAGE);

            switch (answer) {
                case JOptionPane.YES_OPTION:
                    //removes node from database
                    Firebase del = DBClass.getInstance().child("Orders/" + txfOrderID.getText());
                    del.removeValue();
                    clearFieldsClient();
                    clearFieldsOrder();
                    DefaultTableModel m = (DefaultTableModel) tblMeals.getModel();
                    m.setRowCount(0);
                    //clear arraylist and repopulate with updated data
                    break;

                case JOptionPane.NO_OPTION:
                    break;

                case JOptionPane.CANCEL_OPTION:

                    break;
            }
        }else{
            JOptionPane.showMessageDialog(rootPane, "No order has been selected to delete!");
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        //path to specific node in database
        Firebase upd = DBClass.getInstance().child("Clients/" + txfClientID.getText());

        //method checks if all fields are filled
        boolean empty = checkEmpty();

        if (empty) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
            //displays message to user if all fields have not been filled in
        } else {

            boolean subActive = false; //variable used to track whether suburbs edited are active

            //loops through all active suburbs and checks whether the suburb changed is active
            for (String s : activeSuburbs) {
                if (s.equals(cmbSuburbs.getSelectedItem().toString())) {
                    if (editClicked) {
                        editClicked = false;
                        //updates all values in database
                        upd.child("Suburb").setValue(cmbSuburbs.getSelectedItem().toString());
                        upd.child("Name").setValue(txfClientName.getText().trim());
                        upd.child("Surname").setValue(txfClientSurname.getText().trim());
                        upd.child("Address").setValue(txfClientAddress.getText().trim());
                        upd.child("ContactNum").setValue(txfClientContactNo.getText().trim());
                        upd.child("AdditionalInfo").setValue(txfAddInfo.getText().trim());
                        upd.child("AlternativeNumber").setValue(txfAltNum.getText().trim());
                        upd.child("Email").setValue(txfClientEmail.getText().trim());
                    }

                    if (orderEdited) {
                        DefaultTableModel meals = (DefaultTableModel) tblMeals.getModel();
                        orderEdited = false;
                        Firebase updO = DBClass.getInstance().child("Orders/" + txfOrderID.getText());
                        updO.child("FamilySize").setValue(spnOrderFamilySize.getValue());
                        updO.child("RouteID").setValue(txfOrderRouteID.getText());
                        updO.child("Duration").setValue(cmbDuration.getSelectedItem());
                        Calendar c = Calendar.getInstance();
                        Date startdate = (Date) spnStartDate.getValue();
                        c.setTime(startdate);
                        c.add(Calendar.HOUR, 0);
                        c.add(Calendar.MINUTE, 0);
                        c.add(Calendar.SECOND, 0);

                        updO.child("StartingDate").setValue(c.getTimeInMillis());
                        if (spnEndDate.isEnabled()) {
                            Date enddate = (Date) spnEndDate.getValue();
                            c.setTime(enddate);
                            c.add(Calendar.HOUR, 0);
                            c.add(Calendar.MINUTE, 0);
                            c.add(Calendar.SECOND, 0);

                            updO.child(c.getTimeInMillis() + "");
                        } else {
                            updO.child("EndDate").setValue("-");
                        }

                        updO.child("Duration").setValue(cmbDuration.getSelectedItem());

                        updO.child("Meals").removeValue();

                        for (int i = 0; i < meals.getRowCount(); i++) {
                            Firebase updmeal = DBClass.getInstance().child("Orders/" + txfOrderID.getText() + "/Meals/" + i);
                            updmeal.push();
                            updmeal = DBClass.getInstance().child("Orders/" + txfOrderID.getText() + "/Meals/" + i + "/Quantity");
                            updmeal.push();
                            updmeal.setValue(meals.getValueAt(i, 0));
                            updmeal = DBClass.getInstance().child("Orders/" + txfOrderID.getText() + "/Meals/" + i + "/MealType");
                            updmeal.push();
                            updmeal.setValue(meals.getValueAt(i, 1));
                            updmeal = DBClass.getInstance().child("Orders/" + txfOrderID.getText() + "/Meals/" + i + "/Allergies");
                            updmeal.push();
                            updmeal.setValue(meals.getValueAt(i, 2));
                            updmeal = DBClass.getInstance().child("Orders/" + txfOrderID.getText() + "/Meals/" + i + "/Exclusions");
                            updmeal.push();
                            updmeal.setValue(meals.getValueAt(i, 3));

                        }

                    }
                    JOptionPane.showMessageDialog(rootPane, "Saved successfully.");

                    //enabling and disabling of relevant components
                    disableFieldsClient();
                    disableFieldsOrder();
                    btnSave.setEnabled(false);
                    btnEditOrder.setEnabled(true);
                    btnEditClient.setEnabled(true);
                    btnDelete.setEnabled(true);

                    editClicked = false;
                    orderEdited = false;

                    subActive = true;
                    break;
                }
            }

            //if suburb is inactive asks user whether the user wants to continue or not
            if (subActive == false) {
                String message = "Set suburb is currently inactive, continue anyway?";
                int answer = JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.INFORMATION_MESSAGE);
                switch (answer) {
                    case JOptionPane.YES_OPTION:

                        if (editClicked) {
                            editClicked = false;
                            //updates all values in database if edit client button pressed
                            upd.child("Suburb").setValue(cmbSuburbs.getSelectedItem().toString());
                            upd.child("Name").setValue(txfClientName.getText().trim());
                            upd.child("Surname").setValue(txfClientSurname.getText().trim());
                            upd.child("Address").setValue(txfClientAddress.getText().trim());
                            upd.child("ContactNum").setValue(txfClientContactNo.getText().trim());
                            upd.child("AdditionalInfo").setValue(txfAddInfo.getText().trim());
                            upd.child("AlternativeNumber").setValue(txfAltNum.getText().trim());
                            upd.child("Email").setValue(txfClientEmail.getText().trim());
                        }

                        if (orderEdited) {
                            orderEdited = false;
                            DefaultTableModel meals = (DefaultTableModel) tblMeals.getModel();
                            //updates all values in database if edit orders button pressed
                            Firebase updO = DBClass.getInstance().child("Orders/" + txfOrderID.getText());
                            updO.child("FamilySize").setValue(spnOrderFamilySize.getValue());
                            updO.child("RouteID").setValue(txfOrderRouteID.getText());
                            updO.child("Duration").setValue(cmbDuration.getSelectedItem());

                            Calendar c = Calendar.getInstance();
                            Date startdate = (Date) spnStartDate.getValue();
                            c.setTime(startdate);
                            c.add(Calendar.HOUR, 0);
                            c.add(Calendar.MINUTE, 0);
                            c.add(Calendar.SECOND, 0);

                            updO.child("StartingDate").setValue(c.getTimeInMillis());
                            if (spnEndDate.isEnabled()) {
                                Date enddate = (Date) spnEndDate.getValue();
                                c.setTime(enddate);
                                c.add(Calendar.HOUR, 0);
                                c.add(Calendar.MINUTE, 0);
                                c.add(Calendar.SECOND, 0);

                                updO.child(c.getTimeInMillis() + "");
                            } else {
                                updO.child("EndDate").setValue("-");
                            }
                            for (int i = 0; i < meals.getRowCount(); i++) {
                                Firebase updmeal = DBClass.getInstance().child("Orders/" + txfOrderID.getText() + "/Meals/" + i);
                                updmeal.push();
                                updmeal = DBClass.getInstance().child("Orders/" + txfOrderID.getText() + "/Meals/" + i + "/Quantity");
                                updmeal.push();
                                updmeal.setValue(meals.getValueAt(i, 0));
                                updmeal = DBClass.getInstance().child("Orders/" + txfOrderID.getText() + "/Meals/" + i + "/MealType");
                                updmeal.push();
                                updmeal.setValue(meals.getValueAt(i, 1));
                                updmeal = DBClass.getInstance().child("Orders/" + txfOrderID.getText() + "/Meals/" + i + "/Allergies");
                                updmeal.push();
                                updmeal.setValue(meals.getValueAt(i, 2));
                                updmeal = DBClass.getInstance().child("Orders/" + txfOrderID.getText() + "/Meals/" + i + "/Exclusions");
                                updmeal.push();
                                updmeal.setValue(meals.getValueAt(i, 3));

                            }
                        }
                        JOptionPane.showMessageDialog(rootPane, "Saved successfully.");

                        //enabling and disabling of relevant components
                        disableFieldsClient();
                        disableFieldsOrder();
                        btnSave.setEnabled(false);
                        btnEditOrder.setEnabled(true);
                        btnEditClient.setEnabled(true);
                        btnDelete.setEnabled(true);

                        editClicked = false;
                        orderEdited = false;

                        break;

                    case JOptionPane.NO_OPTION:

                        break;

                    //no changes are saved if cancel is selected,relevant components are simply enabled and disabled
                    case JOptionPane.CANCEL_OPTION:
                        disableFieldsClient();
                        btnSave.setEnabled(false);
                        btnEditOrder.setEnabled(true);
                        btnEditClient.setEnabled(true);
                        editClicked = false;
                        btnDelete.setEnabled(true);
                        break;
                }
            }

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

        clearFieldsClient();
        clearFieldsOrder();

        orders1.clear();

        if (txfSearch.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please enter search value!");
            search = false;
        } else {
            switch (veiw) {
                case "All":
                    model.setRowCount(0);
                    switch (column) {
                        case "Name":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getName())) {
                                    setOrderTB(orders, model);
                                }
                            }
                            break;
                        case "Surname":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getSurname())) {
                                    setOrderTB(orders, model);
                                }
                            }
                            break;
                        case "Contact Number":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getContactNumber())) {
                                    setOrderTB(orders, model);
                                }
                            }
                            break;
                        case "Email":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getEmail())) {
                                    setOrderTB(orders, model);
                                }
                            }
                            break;
                        case "Suburb":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getSuburb())) {
                                    setOrderTB(orders, model);
                                }
                            }
                            break;
                        case "Duration":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase((orders.getDuration()))) {
                                    setOrderTB(orders, model);

                                }
                            }
                            break;
                        case "Family Size":
                            for (Order orders : allorders) {
                                if ((Integer.parseInt(searchFor)) == orders.getFamilySize()) {
                                    setOrderTB(orders, model);
                                }
                            }
                            break;
                        default:
                            JOptionPane.showMessageDialog(rootPane, "'" + searchFor + "' not found!");
                    }

                    break;
                case "Active":
                    btnDeactivate.setVisible(true);
                    switch (column) {
                        case "Name":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getName())) {
                                    if (orders.isActive()) {
                                        setOrderTB(orders, model);
                                    }
                                }
                            }
                            break;
                        case "Surname":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getSurname())) {
                                    if (orders.isActive()) {
                                        setOrderTB(orders, model);
                                    }
                                }
                            }
                            break;
                        case "Contact Number":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getContactNumber())) {
                                    if (orders.isActive()) {
                                        setOrderTB(orders, model);
                                    }
                                }
                            }
                            break;
                        case "Email":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getEmail())) {
                                    if (orders.isActive()) {
                                        setOrderTB(orders, model);
                                    }
                                }
                            }
                            break;
                        case "Suburb":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getSuburb())) {
                                    if (orders.isActive()) {
                                        setOrderTB(orders, model);
                                    }
                                }
                            }
                            break;
                        case "Duration":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase((orders.getDuration()))) {
                                    if (orders.isActive()) {
                                        setOrderTB(orders, model);
                                    }
                                }
                            }
                            break;
                        case "Family Size":
                            for (Order orders : allorders) {
                                if ((Integer.parseInt(searchFor)) == orders.getFamilySize()) {
                                    if (orders.isActive()) {
                                        setOrderTB(orders, model);
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
                    btnDeactivate.setVisible(false);
                    switch (column) {
                        case "Name":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getName())) {
                                    if (orders.isActive()) {
                                    } else {
                                        setOrderTB(orders, model);
                                    }
                                }
                            }
                            break;
                        case "Surname":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getSurname())) {
                                    if (orders.isActive()) {
                                    } else {
                                        setOrderTB(orders, model);
                                    }
                                }
                            }
                            break;
                        case "Contact Number":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getContactNumber())) {
                                    if (orders.isActive()) {
                                    } else {
                                        setOrderTB(orders, model);
                                    }
                                }
                            }
                            break;
                        case "Email":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getEmail())) {
                                    if (orders.isActive()) {
                                    } else {
                                        setOrderTB(orders, model);
                                    }
                                }
                            }
                            break;
                        case "Suburb":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase(orders.getClient().getSuburb())) {
                                    if (orders.isActive()) {
                                    } else {
                                        setOrderTB(orders, model);
                                    }
                                }
                            }
                            break;
                        case "Duration":
                            for (Order orders : allorders) {
                                if (searchFor.equalsIgnoreCase((orders.getDuration()))) {
                                    if (orders.isActive()) {
                                    } else {
                                        setOrderTB(orders, model);
                                    }
                                }
                            }
                            break;
                        case "Family Size":
                            for (Order orders : allorders) {
                                if ((Integer.parseInt(searchFor)) == orders.getFamilySize()) {
                                    if (orders.isActive()) {
                                    } else {
                                        setOrderTB(orders, model);
                                    }
                                }
                            }
                            break;
                    }
            }
        }
        setTextFields();
        if(tblOrderTable.getRowCount()<1 && search ){
            JOptionPane.showMessageDialog(rootPane, "No orders found for '"+searchFor+"'.");
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnEditOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditOrderActionPerformed
        if (orderSelected) {
            btnDelete.setEnabled(false);
            enableFieldsOrder();
            btnEditOrder.setEnabled(false);
            btnSave.setEnabled(true);
            editClicked = true;
            orderEdited = true;
        } else {
            JOptionPane.showMessageDialog(rootPane, "No order has been selected to edit!");
        }
    }//GEN-LAST:event_btnEditOrderActionPerformed

    private void btnDeactivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeactivateActionPerformed

        Firebase updOrder = DBClass.getInstance().child("Orders/" + txfOrderID.getText());
        updOrder.child("Active").setValue(false);
        for (Order o : allorders) {
            if (txfOrderID.getText().equals(o.getID())) {
                o.setActive(false);
            }
        }
        btnDeactivate.setEnabled(false);

    }//GEN-LAST:event_btnDeactivateActionPerformed

    private void btnRemoveMealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveMealActionPerformed
        DefaultTableModel mealmod = (DefaultTableModel) tblMeals.getModel();
        mealmod.removeRow(tblMeals.getSelectedRow());
    }//GEN-LAST:event_btnRemoveMealActionPerformed

    private void btnAddMealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMealActionPerformed
        DefaultTableModel mealmod = (DefaultTableModel) tblMeals.getModel();
        Object[] row = {"0", "Standard", "-", "-"};
        mealmod.addRow(row);
    }//GEN-LAST:event_btnAddMealActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        orders1.clear();
        DefaultTableModel ordertb = (DefaultTableModel) tblOrderTable.getModel();
        ordertb.setRowCount(0);
        if (allorders.size() > tbcounter) {
            tbcounter = tbcounter + 30;
            while (count < tbcounter) {
                if (allorders.size() > count || allorders.size() == count) {
                    setOrderTB(allorders.get(count), ordertb);
                    count++;
                } else {
                    break;
                }
            } 
        }
    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        orders1.clear();
        DefaultTableModel ordertb = (DefaultTableModel) tblOrderTable.getModel();
        String s = (String) cmbVeiw.getSelectedItem();

        ordertb.setRowCount(0);
        if (tbcounter > 30) {
            tbcounter = tbcounter - 30;
            int j = tbcounter % count;
            count = tbcounter - 30;
            while (count < tbcounter) {
                if (allorders.size() > count || allorders.size() == count) {
                    setOrderTB(allorders.get(count), ordertb);
                    count++;
                } else {
                    break;
                }
            }
        }
    }//GEN-LAST:event_btnPreviousActionPerformed

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
        java.awt.EventQueue.invokeLater(() -> {
            new DSC_ViewOrder().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddMeal;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDeactivate;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnEditClient;
    private javax.swing.JButton btnEditOrder;
    private javax.swing.JButton btnNext;
    private javax.swing.JButton btnPrevious;
    private javax.swing.JButton btnRemoveMeal;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnSearch;
    private javax.swing.JComboBox<String> cmbDuration;
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
    private javax.swing.JSpinner spnStartDate;
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
    private javax.swing.JTextField txfOrderID;
    private javax.swing.JTextField txfOrderRouteID;
    private javax.swing.JTextField txfSearch;
    // End of variables declaration//GEN-END:variables
}
