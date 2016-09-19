package DSC;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.sql.ResultSet;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Aliens_Michael
 */
public class DSC_DriverDetails extends javax.swing.JFrame {

    boolean editClicked = false;
    int listRouteIndex = 0;
    int listSuburbIndex = 0;
    ArrayList<Route> allRoutes = new ArrayList<>();
    ArrayList<String> suburbs = new ArrayList<>();

    /**
     * Creates new form DSC_DriverDetails
     */
    public DSC_DriverDetails() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        disableFields();
        btnSave.setText("Save");
        btnSave.setVisible(false);
        txfRouteID.setEnabled(false);
        txfSuburbID.setEnabled(false);
        txfDriverID.setEnabled(false);
        setRoutes();
    }

    public final void enableFields() {
        
        txfContactNo.setEnabled(true);
        txfAddress.setEnabled(true);
        txfVehicleReg.setEnabled(true);
    }

    public final void disableFields() {
        
        txfContactNo.setEnabled(false);
        txfAddress.setEnabled(false);
        txfVehicleReg.setEnabled(false);
    }

    public final void clearFields() {
        txfDriverID.setText(null);
        
        txfContactNo.setText(null);
        txfAddress.setText(null);
        txfVehicleReg.setText(null);
    }

    private boolean checkEmpty() {
        boolean empty = false;

        if (txfContactNo.getText().isEmpty() //and driver name
                && txfAddress.getText().isEmpty() && txfVehicleReg.getText().isEmpty()) {
            empty = true;
        }

        return empty;
    }

    private void setRoutes() {
        Firebase tableRef = DBClass.getInstance().child("Routes");
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {

                for (DataSnapshot data : ds.getChildren()) {
                    if (!data.getKey().equals("0")) {
                        Route r = new Route();
                        r.setID(data.getKey());
                        r.setActive((boolean) data.child("Active").getValue());
                        //r.setDrivers(drivers);
                        //r.setSuburbs(suburbs);
                        r.setTimeFrame((String) data.child("TimeFrame").getValue());
                        allRoutes.add(r);
                    }
                }
                DefaultListModel model = new DefaultListModel();
                for (Route r : allRoutes) {
                    model.addElement(r);
                }
                lstRoutes.setModel(model);
                lstRoutes.setSelectedIndex(0);
                setSuburbs("1");
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
                    if(data.getKey().equals(routeNum)){
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
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    private void setTextFields(){
        Firebase tableRef = DBClass.getInstance().child("Routes");
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }
    
    private void setDrivers(){
        
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
        lstRoutes = new javax.swing.JList<>();
        btnAddRoute = new javax.swing.JButton();
        btnDeleteRoute = new javax.swing.JButton();
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
        lblSuburbID = new javax.swing.JLabel();
        txfSuburbID = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        pnlSuburbs = new javax.swing.JPanel();
        lblSuburbs = new javax.swing.JLabel();
        jScrollPane5 = new javax.swing.JScrollPane();
        lstSuburbs = new javax.swing.JList<>();
        btnAddSuburb = new javax.swing.JButton();
        btnDeleteSuburb = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Driver Details");

        pnlBackground.setBackground(new java.awt.Color(0, 153, 0));

        pnlRoutes.setBackground(new java.awt.Color(0, 204, 51));
        pnlRoutes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblRoutes.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblRoutes.setText("Routes:");

        lstRoutes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lstRoutes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstRoutes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstRoutesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstRoutes);

        btnAddRoute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Add.png"))); // NOI18N
        btnAddRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRouteActionPerformed(evt);
            }
        });

        btnDeleteRoute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Bin.png"))); // NOI18N
        btnDeleteRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteRouteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlRoutesLayout = new javax.swing.GroupLayout(pnlRoutes);
        pnlRoutes.setLayout(pnlRoutesLayout);
        pnlRoutesLayout.setHorizontalGroup(
            pnlRoutesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRoutesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRoutesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlRoutesLayout.createSequentialGroup()
                        .addComponent(lblRoutes, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddRoute)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteRoute)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlRoutesLayout.setVerticalGroup(
            pnlRoutesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRoutesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRoutesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDeleteRoute, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddRoute, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblRoutes, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        pnlDetails.setBackground(new java.awt.Color(0, 204, 51));
        pnlDetails.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblDetails.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
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

        txfContactNo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfContactNo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txfAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfAddress.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txfVehicleReg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfVehicleReg.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

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

        btnEdit.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEdit.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Edit 2.png"))); // NOI18N
        btnEdit.setText(" Edit");
        btnEdit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditActionPerformed(evt);
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

        lblRouteID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRouteID.setText("Route ID:");

        txfRouteID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        lblSuburbID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSuburbID.setText("Suburb ID:");

        txfSuburbID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        jComboBox1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout pnlDetailsLayout = new javax.swing.GroupLayout(pnlDetails);
        pnlDetails.setLayout(pnlDetailsLayout);
        pnlDetailsLayout.setHorizontalGroup(
            pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlDetailsLayout.createSequentialGroup()
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 105, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBack))
                    .addGroup(pnlDetailsLayout.createSequentialGroup()
                        .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblSuburbID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                            .addComponent(txfSuburbID)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSuburbID)
                    .addComponent(txfSuburbID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDriverID)
                    .addComponent(txfDriverID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblDriverName)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 225, Short.MAX_VALUE)
                .addGroup(pnlDetailsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEdit)
                    .addComponent(btnSave)
                    .addComponent(btnBack))
                .addContainerGap())
        );

        pnlSuburbs.setBackground(new java.awt.Color(0, 204, 51));
        pnlSuburbs.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblSuburbs.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSuburbs.setText("Suburbs:");

        lstSuburbs.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lstSuburbs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane5.setViewportView(lstSuburbs);

        btnAddSuburb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Add.png"))); // NOI18N
        btnAddSuburb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSuburbActionPerformed(evt);
            }
        });

        btnDeleteSuburb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Bin.png"))); // NOI18N
        btnDeleteSuburb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSuburbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSuburbsLayout = new javax.swing.GroupLayout(pnlSuburbs);
        pnlSuburbs.setLayout(pnlSuburbsLayout);
        pnlSuburbsLayout.setHorizontalGroup(
            pnlSuburbsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSuburbsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSuburbsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(pnlSuburbsLayout.createSequentialGroup()
                        .addComponent(lblSuburbs, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddSuburb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteSuburb)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlSuburbsLayout.setVerticalGroup(
            pnlSuburbsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSuburbsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSuburbsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(btnAddSuburb, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteSuburb, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 30, Short.MAX_VALUE)
                    .addComponent(lblSuburbs, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlRoutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSuburbs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
        listRouteIndex = lstRoutes.getSelectedIndex();
        enableFields();
        btnEdit.setEnabled(false);
        btnSave.setVisible(true);
        editClicked = true;
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        if (editClicked) {
            int ans = JOptionPane.showConfirmDialog(this, "Do you wish to discard unsaved changes?");
            switch (ans) {
                case JOptionPane.YES_OPTION:
                    btnSave.setVisible(false);
                    btnEdit.setEnabled(true);
                    disableFields();
                    lstRoutes.setSelectedIndex(listRouteIndex);
                    editClicked = false;
                    break;
                case JOptionPane.NO_OPTION:
                    break;
                default:
                    break;
            }
        } else {
            this.dispose();
            new DSC_RouteView().setVisible(true);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        boolean back = false;
        if (btnSave.getText().equals("Save")) {
            //Update existing driver
            
        } else if (btnSave.getText().equals("Add")) {
            //Add new driver
            boolean empty = checkEmpty();
            if (empty) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                
            }
        }

        if (back) {
            disableFields();
            btnSave.setVisible(false);
            btnEdit.setEnabled(true);
            editClicked = false;
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAddRouteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRouteActionPerformed
        clearFields();
        enableFields();
        btnEdit.setEnabled(false);
        btnSave.setText("Add");
        btnSave.setVisible(true);
        editClicked = true;

        String query = "SELECT MAX(DriverID) FROM doorstepchef.driver_tb;";
        ResultSet rs;
        int numRows = 0;

        try {
//            Connection c = DBClass.getConnection();
//            Statement stmt = c.createStatement();
//            rs = stmt.executeQuery(query);
//            rs.next();
//            numRows = rs.getInt(1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        numRows += 1;
        txfDriverID.setText(numRows + "");

    }//GEN-LAST:event_btnAddRouteActionPerformed

    private void btnDeleteRouteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteRouteActionPerformed
        String name = ""; //get from combo box

        int driverID = Integer.parseInt(txfDriverID.getText());
        int elementIndex = lstRoutes.getSelectedIndex();

        String message = "Are you sure you want to delete " + name + "?";
        int answer = JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.INFORMATION_MESSAGE);

        switch (answer) {
            case JOptionPane.YES_OPTION:
                JOptionPane.showMessageDialog(this, name + " will be deleted", "Delete Notification", JOptionPane.INFORMATION_MESSAGE);

                try {
//                    Connection c = DBClass.getConnection();
//                    Statement stmt = c.createStatement();
//
//                    String update = "UPDATE route_tb SET DriverID = 0 WHERE DriverID = '" + driverID + "'";
//                    stmt.executeUpdate(update);
//
//                    String deleteDriver = "DELETE FROM doorstepchef.driver_tb WHERE DriverID LIKE '" + driverID + "'";
//                    stmt.executeUpdate(deleteDriver);
//
//                    JOptionPane.showMessageDialog(this, "Driver has been deleted. \nPlease note removing this driver has affected"
//                            + " \nroute schedules leaving routes without an assigned driver.");
//                    //refresh jList

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
    }//GEN-LAST:event_btnDeleteRouteActionPerformed

    private void btnAddSuburbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSuburbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAddSuburbActionPerformed

    private void btnDeleteSuburbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSuburbActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnDeleteSuburbActionPerformed

    private void lstRoutesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstRoutesValueChanged
        int num = lstRoutes.getSelectedIndex();
        num++;
        String number = num + "";
        setSuburbs(number);
    }//GEN-LAST:event_lstRoutesValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddRoute;
    private javax.swing.JButton btnAddSuburb;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDeleteRoute;
    private javax.swing.JButton btnDeleteSuburb;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblContactNo;
    private javax.swing.JLabel lblDetails;
    private javax.swing.JLabel lblDriverID;
    private javax.swing.JLabel lblDriverName;
    private javax.swing.JLabel lblRouteID;
    private javax.swing.JLabel lblRoutes;
    private javax.swing.JLabel lblSuburbID;
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
    private javax.swing.JTextField txfSuburbID;
    private javax.swing.JTextField txfVehicleReg;
    // End of variables declaration//GEN-END:variables
}
