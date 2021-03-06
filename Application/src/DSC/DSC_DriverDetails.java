package DSC;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 * @author Michael Hamann
 */
public class DSC_DriverDetails extends javax.swing.JFrame {

    boolean editClicked = false;
    boolean infoChanged;
    boolean driverChanged = false;
    String driverName;
    ArrayList<Driver> allDrivers = new ArrayList<>();
    ArrayList<Route> allRoutes = new ArrayList<>();
    ArrayList<String> suburbs = new ArrayList<>();
    ArrayList<RouteDrivers> drivers = new ArrayList<>();

    /**
     * Creates new form DSC_DriverDetails
     */
    public DSC_DriverDetails() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        disableFields();
        btnEdit.setVisible(true);
        btnSave.setVisible(false);
        setRoutes();
    }

    public final void enableFields() {
        cmbDriverName.setEnabled(true);
        txfContactNo.setEditable(true);
        txfAddress.setEditable(true);
        txfVehicleReg.setEditable(true);
    }

    public final void disableFields() {
        txfRouteID.setEditable(false);
        txfDriverID.setEditable(false);
        cmbDriverName.setEnabled(false);
        txfContactNo.setEditable(false);
        txfAddress.setEditable(false);
        txfVehicleReg.setEditable(false);
    }

    private boolean checkEmpty() {
        boolean empty = false;

        if (txfContactNo.getText().isEmpty() && txfAddress.getText().isEmpty()
                && txfVehicleReg.getText().isEmpty()) {
            empty = true;
        }

        return empty;
    }
//
//    private boolean checkChanged() {
//        boolean isChanged = false;
//
//        return isChanged;
//    }

    private boolean checkInfoChanged() {
        String driverID = txfDriverID.getText().trim();
        Firebase ref = DBClass.getInstance().child("Drivers");
        try {
            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    for (DataSnapshot data : ds.getChildren()) {
                        for (DataSnapshot data2 : data.getChildren()) {
                            if (data.getKey().equals(driverID)) {
                                String address = data.child("Address").getValue(String.class);
                                String contactNum = data.child("ContactNumber").getValue(String.class);
                                String vehicleReg = data.child("VehicleReg").getValue(String.class);

                                if (!txfAddress.getText().trim().equals(address)) {
                                    infoChanged = true;
                                }
                                if (!txfContactNo.getText().trim().equals(contactNum)) {
                                    infoChanged = true;
                                }
                                if (!txfVehicleReg.getText().trim().equals(vehicleReg)) {
                                    infoChanged = true;
                                }

                                System.out.println("db:" + address);
                                System.out.println("txf:" + txfAddress.getText());
                                System.out.println("db:" + contactNum);
                                System.out.println("txf:" + txfContactNo.getText());
                                System.out.println("db:" + vehicleReg);
                                System.out.println("txf:" + txfVehicleReg.getText());
                                System.out.println("infoChanged in if:" + infoChanged);
                                break;
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(FirebaseError fe) {
                    JOptionPane.showMessageDialog(null, fe.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        } finally {
            System.out.println("infoChanged out of loop:" + infoChanged);
            return infoChanged;
        }
    }

    private boolean checkDriverChanged() {
        Firebase ref = DBClass.getInstance().child("Routes/" + getSelectedRoute());
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot data : ds.getChildren()) {
                    //fill in
                }
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, fe.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return driverChanged;
    }

    private void setRoutes() {
        Firebase tableRef = DBClass.getInstance().child("Routes");
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                allRoutes.clear();
                for (DataSnapshot data : ds.getChildren()) {
                    if (!data.getKey().equals("0")) {
                        Route r = new Route();
                        r.setID(data.getKey());
                        r.setActive(data.child("Active").getValue(boolean.class));

                        ArrayList<RouteDrivers> routeDrivers = new ArrayList<>();
                        for (DataSnapshot data2 : data.child("Drivers").getChildren()) {
                            routeDrivers.add(new RouteDrivers(data2.child("driverID").getValue(String.class), data2.child("endDate").getValue(String.class), data2.child("startDate").getValue(String.class)));
                        }

                        r.setDrivers(routeDrivers);
                        r.setTimeFrame(data.child("TimeFrame").getValue(String.class));
                        allRoutes.add(r);
                    }
                }
                DefaultListModel model = new DefaultListModel();
                for (Route r : allRoutes) {
                    model.addElement(r.toString());
                }
                lstRoutes.setModel(model);
                lstRoutes.setSelectedIndex(0);
                setSuburbs(getSelectedRoute());
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "Error: " + fe.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });

    }

    private void setSuburbs(String routeNum) {
        Firebase tableRef = DBClass.getInstance().child("Routes");
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                suburbs.clear();
                for (DataSnapshot data : ds.getChildren()) {
                    if (data.getKey().equals(routeNum)) {
                        String subArr[] = data.child("Suburbs").getValue(String[].class);
                        for (int i = 0; i < subArr.length; i++) {
                            suburbs.add(subArr[i]);
                        }
                    }
                }
                DefaultListModel model = new DefaultListModel();
                for (String s : suburbs) {
                    model.addElement(s);
                }
                lstSuburbs.setModel(model);
                lstSuburbs.setSelectedIndex(0);
                setDrivers();
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "Error: " + fe.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private String getSelectedRoute() {
        String curr = "";
        try {
            curr = lstRoutes.getSelectedValue();
            curr = curr.charAt(curr.length() - 1) + "";
            curr = curr.trim();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return curr;
    }

    private void setTextFields() {
        String routeID = getSelectedRoute();
        txfRouteID.setText(routeID);
        String suburbID = lstSuburbs.getSelectedIndex() + "";
        getRouteDriver(routeID);

        updateFields();

//        for (Driver d : allDrivers) {
//            if (d.getDriverName().equalsIgnoreCase(cmbDriverName.getSelectedItem().toString())) {
//                txfDriverID.setText(d.getID());
//                txfContactNo.setText(d.getContactNumber());
//                txfAddress.setText(d.getAddress());
//                txfVehicleReg.setText(d.getVehicleRegistration());
//            }
//        }
        cmbDriverName.setSelectedItem(driverName);
        for (Driver d : allDrivers) {
            if (cmbDriverName.getSelectedItem().toString().contains("(*)")) {
                if (d.getDriverName().equalsIgnoreCase(cmbDriverName.getSelectedItem().toString().substring(0, cmbDriverName.getSelectedItem().toString().indexOf("(")))) {
                    txfDriverID.setText(d.getID());
                    txfContactNo.setText(d.getContactNumber());
                    txfAddress.setText(d.getAddress());
                    txfVehicleReg.setText(d.getVehicleRegistration());
                }
            } else if (d.getDriverName().equalsIgnoreCase(cmbDriverName.getSelectedItem().toString())) {
                txfDriverID.setText(d.getID());
                txfContactNo.setText(d.getContactNumber());
                txfAddress.setText(d.getAddress());
                txfVehicleReg.setText(d.getVehicleRegistration());
            }
        }
    }

    private String getRouteDriver(String routeNum) {
        Firebase ref = DBClass.getInstance().child("Routes/" + routeNum);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot data : ds.getChildren()) {
                    for (DataSnapshot data2 : data.getChildren()) {
                        if (data.getKey().equalsIgnoreCase("Drivers")) {
                            if (data2.child("endDate").getValue(String.class).equalsIgnoreCase("-")) {
                                String currDriverID = data2.child("DriverID").getValue(String.class);
                                driverName = getDriverName(currDriverID);
                            }
                        }
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "Error: " + fe.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return driverName;
    }

    private String getDriverName(String id) {
        Firebase ref = DBClass.getInstance().child("Drivers/" + id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                driverName = ds.child("DriverName").getValue(String.class);
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "Error: " + fe.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return driverName;
    }

    protected void setDrivers() {
        Firebase ref = DBClass.getInstance().child("Drivers");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                DefaultComboBoxModel comboModel = new DefaultComboBoxModel();
                allDrivers.clear();
                for (DataSnapshot data : ds.getChildren()) {
                    if (!data.getKey().equalsIgnoreCase("0")) {
                        Driver d = new Driver();
                        d.setID(data.getKey());
                        d.setActive(data.child("Active").getValue(boolean.class));
                        d.setDriverName(data.child("DriverName").getValue(String.class));
                        d.setAddress(data.child("Address").getValue(String.class));
                        d.setContactNumber(data.child("ContactNumber").getValue(String.class));
                        d.setVehicleRegistration(data.child("VehicleReg").getValue(String.class));
                        allDrivers.add(d);
                        if (d.isActive()) {
                            comboModel.addElement(d.getDriverName());
                        } else {
                            comboModel.addElement(d.getDriverName());
                        }
                    }
                }
                //cmbDriverName.setModel(comboModel);
                setTextFields();
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "Error: " + fe.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void deleteDriver(String driverName) {
        DBClass.getInstance().child("Drivers/" + txfDriverID.getText()).setValue(null);
//        Firebase ref = DBClass.getInstance().child("Drivers");
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot ds) {
//                for (DataSnapshot data : ds.getChildren()) {
//                    for (DataSnapshot data2 : ds.getChildren()) {
//                        if (data2.child("DriverName").getValue(String.class).equals(driverName)) {
//                            ref.child(data2.getKey()).child("Active").setValue(false);
//                            break;
//                        }
//                    }
//                }
//            }
//
//            @Override
//            public void onCancelled(FirebaseError fe) {
//                JOptionPane.showMessageDialog(null, fe.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
//            }
//        });
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
        pnlRoutes = new javax.swing.JPanel();
        lblRoutes = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstRoutes = new javax.swing.JList<String>();
        pnlDetails = new javax.swing.JPanel();
        lblDetails = new javax.swing.JLabel();
        lblDriverID = new javax.swing.JLabel();
        lblDriverName = new javax.swing.JLabel();
        lblContactNo = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblVehicleReg = new javax.swing.JLabel();
        txfDriverID = new javax.swing.JTextField();
        txfContactNo = new javax.swing.JTextField();
        txfAddress = new javax.swing.JTextField();
        txfVehicleReg = new javax.swing.JTextField();
        btnBack = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        lblRouteID = new javax.swing.JLabel();
        txfRouteID = new javax.swing.JTextField();
        cmbDriverName = new javax.swing.JComboBox<String>();
        btnAddDriver = new javax.swing.JButton();
        btnDeleteDriver = new javax.swing.JButton();
        pnlSuburbs = new javax.swing.JPanel();
        lblSuburbs = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        lstSuburbs = new javax.swing.JList<String>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Driver Details");

        pnlBackground.setBackground(new java.awt.Color(0, 153, 0));

        pnlRoutes.setBackground(new java.awt.Color(0, 204, 51));
        pnlRoutes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblRoutes.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblRoutes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRoutes.setText("Routes:");

        lstRoutes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lstRoutes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstRoutes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lstRoutes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstRoutesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstRoutes);

        javax.swing.GroupLayout pnlRoutesLayout = new javax.swing.GroupLayout(pnlRoutes);
        pnlRoutes.setLayout(pnlRoutesLayout);
        pnlRoutesLayout.setHorizontalGroup(
            pnlRoutesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRoutesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRoutesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblRoutes, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlRoutesLayout.setVerticalGroup(
            pnlRoutesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRoutesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblRoutes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addContainerGap())
        );

        pnlDetails.setBackground(new java.awt.Color(0, 204, 51));
        pnlDetails.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblDetails.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblDetails.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblDetails.setText("Details:");

        lblDriverID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDriverID.setText("Driver ID:");

        lblDriverName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblDriverName.setText("Driver Name:");

        lblContactNo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblContactNo.setText("Contact Number:");

        lblAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAddress.setText("Address:");

        lblVehicleReg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblVehicleReg.setText("Vehicle Registration:");

        txfDriverID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfDriverID.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfDriverID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txfContactNo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfContactNo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfContactNo.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txfAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfAddress.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfAddress.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txfVehicleReg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfVehicleReg.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfVehicleReg.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Edit 2.png"))); // NOI18N
        btnEdit.setText(" Edit");
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
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

        lblRouteID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRouteID.setText("Route ID:");

        txfRouteID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfRouteID.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfRouteID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        cmbDriverName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        cmbDriverName.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Driver Name" }));
        cmbDriverName.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        cmbDriverName.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbDriverNameItemStateChanged(evt);
            }
        });

        btnAddDriver.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAddDriver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/driver.gif"))); // NOI18N
        btnAddDriver.setText("Add Driver");
        btnAddDriver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddDriverActionPerformed(evt);
            }
        });

        btnDeleteDriver.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDeleteDriver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Bin.png"))); // NOI18N
        btnDeleteDriver.setText("Delete Driver");
        btnDeleteDriver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteDriverActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDetailsLayout = new javax.swing.GroupLayout(pnlDetails);
        pnlDetails.setLayout(pnlDetailsLayout);
        pnlDetailsLayout.setHorizontalGroup(
            pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlDetailsLayout.createSequentialGroup()
                        .addComponent(btnAddDriver)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteDriver)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBack))
                    .addGroup(pnlDetailsLayout.createSequentialGroup()
                        .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblRouteID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblVehicleReg, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblAddress, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblContactNo, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDriverName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblDriverID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfDriverID)
                            .addComponent(txfContactNo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txfAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txfVehicleReg, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txfRouteID)
                            .addComponent(cmbDriverName, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        pnlDetailsLayout.setVerticalGroup(
            pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRouteID)
                    .addComponent(txfRouteID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(37, 37, 37)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDriverID)
                    .addComponent(txfDriverID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDriverName)
                    .addComponent(cmbDriverName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContactNo)
                    .addComponent(txfContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAddress)
                    .addComponent(txfAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVehicleReg)
                    .addComponent(txfVehicleReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 231, Short.MAX_VALUE)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnBack)
                    .addComponent(btnAddDriver)
                    .addComponent(btnEdit)
                    .addComponent(btnDeleteDriver))
                .addContainerGap())
        );

        pnlSuburbs.setBackground(new java.awt.Color(0, 204, 51));
        pnlSuburbs.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblSuburbs.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblSuburbs.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSuburbs.setText("Suburbs:");

        lstSuburbs.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lstSuburbs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstSuburbs.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        lstSuburbs.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstSuburbsValueChanged(evt);
            }
        });
        jScrollPane5.setViewportView(lstSuburbs);

        javax.swing.GroupLayout pnlSuburbsLayout = new javax.swing.GroupLayout(pnlSuburbs);
        pnlSuburbs.setLayout(pnlSuburbsLayout);
        pnlSuburbsLayout.setHorizontalGroup(
            pnlSuburbsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSuburbsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSuburbsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblSuburbs, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSuburbsLayout.setVerticalGroup(
            pnlSuburbsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSuburbsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSuburbs, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 336, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlRoutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlSuburbs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlRoutes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSuburbs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        enableFields();
        btnEdit.setVisible(false);
        btnSave.setVisible(true);
        editClicked = true;
        txfContactNo.requestFocusInWindow();
        txfAddress.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        txfContactNo.setCursor(new Cursor(Cursor.TEXT_CURSOR));
        txfVehicleReg.setCursor(new Cursor(Cursor.TEXT_CURSOR));
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        if (editClicked) {
            int ans = JOptionPane.showConfirmDialog(this, "Do you wish to discard unsaved changes?");
            switch (ans) {
                case JOptionPane.YES_OPTION:
                    this.dispose();
                    new DSC_RouteView().setVisible(true);
            }
        } else {
            this.dispose();
            new DSC_RouteView().setVisible(true);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        Firebase ref = DBClass.getInstance().child("Drivers/" + txfDriverID.getText());
        ref.child("Address").setValue(txfAddress.getText());
        ref.child("ContactNumber").setValue(txfContactNo.getText());
        ref.child("VehicleReg").setValue(txfVehicleReg.getText());

        ref = DBClass.getInstance().child("Routes/" + txfRouteID.getText() + "/Drivers");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot children : ds.getChildren()) {
                    if (children.child("endDate").getValue(String.class).equals("-")) {
                        if (!children.child("driverID").getValue(String.class).equals(txfDriverID.getText())) {
                            int key = Integer.parseInt(children.getKey()) + 1;
                            DBClass.getInstance().child("Routes/" + txfRouteID.getText() + "/Drivers/" + children.getKey() + "/endDate").setValue(DriverReport.returnWeekMili());
                            DBClass.getInstance().child("Routes/" + txfRouteID.getText() + "/Drivers/" + (key) + "/endDate").setValue("-");
                            DBClass.getInstance().child("Routes/" + txfRouteID.getText() + "/Drivers/" + (key) + "/startDate").setValue(DriverReport.returnWeekMili());
                            DBClass.getInstance().child("Routes/" + txfRouteID.getText() + "/Drivers/" + (key) + "/driverID").setValue(txfDriverID.getText());
                        }
                    }
                }
                disableFields();
                JOptionPane.showMessageDialog(null, "Driver Info Succesfully changed");
            }

            @Override
            public void onCancelled(FirebaseError fe) {
            }
        });

        boolean changedInfo = checkInfoChanged();
        boolean changedDriver = checkDriverChanged();
        if (changedInfo) {
            //update driver information
            JOptionPane.showMessageDialog(null, "Something has been changed");
        } else {
            JOptionPane.showMessageDialog(null, "Nothing has been changed");
        }
        disableFields();
        txfAddress.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        txfContactNo.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        txfVehicleReg.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        editClicked = false;
        btnSave.setVisible(false);
        btnEdit.setVisible(true);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void lstRoutesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstRoutesValueChanged
        setSuburbs(getSelectedRoute());
    }//GEN-LAST:event_lstRoutesValueChanged

    private void btnAddDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddDriverActionPerformed
        new DSC_NewDriver(this).setVisible(true);
    }//GEN-LAST:event_btnAddDriverActionPerformed

    private void lstSuburbsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstSuburbsValueChanged
        setTextFields();
    }//GEN-LAST:event_lstSuburbsValueChanged

    private void btnDeleteDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteDriverActionPerformed
        JComboBox jcb = cmbDriverName;
        jcb.setEnabled(true);
        jcb.setSelectedIndex(-1);
        JOptionPane.showMessageDialog(null, jcb, "Select a driver to delete", JOptionPane.QUESTION_MESSAGE);
        if (jcb.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(null, "No driver selected", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            String selected = jcb.getSelectedItem().toString();
            int ans = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete " + selected + "?", "Please confirm", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            switch (ans) {
                case JOptionPane.YES_OPTION:
                    deleteDriver(selected);
                    setDrivers();
                    break;
                case JOptionPane.NO_OPTION:
                    break;
            }
        }
    }//GEN-LAST:event_btnDeleteDriverActionPerformed

    private void cmbDriverNameItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbDriverNameItemStateChanged
        switch (evt.getStateChange()) {
            case ItemEvent.SELECTED:
                driverName = cmbDriverName.getSelectedItem().toString();
                setTextFields();
                break;
            case ItemEvent.DESELECTED:
                break;
        }
        String driverID = "";
        
        for (Driver allDriver : allDrivers) {
            if (allDriver.getDriverName().equals(cmbDriverName.getSelectedItem())) {
                driverID = allDriver.getID();
            }

        }

        for (Driver allDriver : allDrivers) {
            if (allDriver.getID().equals(driverID)) {
                txfAddress.setText(allDriver.getAddress());
                txfContactNo.setText(allDriver.getContactNumber());
                txfDriverID.setText(allDriver.getID());
                txfVehicleReg.setText(allDriver.getVehicleRegistration());
            }
        }
    }//GEN-LAST:event_cmbDriverNameItemStateChanged

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddDriver;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDeleteDriver;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cmbDriverName;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblContactNo;
    private javax.swing.JLabel lblDetails;
    private javax.swing.JLabel lblDriverID;
    private javax.swing.JLabel lblDriverName;
    private javax.swing.JLabel lblRouteID;
    private javax.swing.JLabel lblRoutes;
    private javax.swing.JLabel lblSuburbs;
    private javax.swing.JLabel lblVehicleReg;
    private javax.swing.JList<String> lstRoutes;
    private javax.swing.JList<String> lstSuburbs;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlDetails;
    private javax.swing.JPanel pnlRoutes;
    private javax.swing.JPanel pnlSuburbs;
    private javax.swing.JTextField txfAddress;
    private javax.swing.JTextField txfContactNo;
    private javax.swing.JTextField txfDriverID;
    private javax.swing.JTextField txfRouteID;
    private javax.swing.JTextField txfVehicleReg;
    // End of variables declaration//GEN-END:variables

    private void cmbListener() {

        cmbDriverName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("James");
                String previous = getRouteDriver(getSelectedRoute());
                String selected = cmbDriverName.getSelectedItem().toString();
                int ans = JOptionPane.showConfirmDialog(null, "Do you want to replace " + previous + " with " + selected + "?");
            }
        });
    }

    private void updateFields() {
        String driverID = "";
        for (Route allRoute : allRoutes) {
            if (allRoute.getID().equals(getSelectedRoute())) {
                ArrayList<RouteDrivers> currentDrivers = allRoute.getDrivers();
                for (int i = 0; i < currentDrivers.size(); i++) {
                    RouteDrivers routething = currentDrivers.get(i);
                    if (currentDrivers.get(i).getEndDate().equals("-")) {
                        driverID = allRoute.getDrivers().get(i).getDriverID();
                    }
                }
            }
        }

        String[] driverNames = new String[allDrivers.size()];
        int i = 0;
        for (Driver allDriver : allDrivers) {
            driverNames[i] = allDriver.getDriverName();
            i++;
        }

        DefaultComboBoxModel model = new DefaultComboBoxModel(driverNames);
        cmbDriverName.setModel(model);

        int counter = 0;
        for (Driver allDriver : allDrivers) {
            if (allDriver.getID().equals(driverID)) {
                txfAddress.setText(allDriver.getAddress());
                txfContactNo.setText(allDriver.getContactNumber());
                txfDriverID.setText(allDriver.getID());
                txfVehicleReg.setText(allDriver.getVehicleRegistration());
                cmbDriverName.setSelectedIndex(counter);
            }
            counter++;
        }

    }

}
