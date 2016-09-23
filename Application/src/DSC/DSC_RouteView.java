package DSC;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 *
 * @author Aliens_Michael
 */
public class DSC_RouteView extends javax.swing.JFrame {

    ArrayList<Route> allRoutes = new ArrayList<>();
    ArrayList<String> suburbs = new ArrayList<>();

    /**
     * Creates new form DSC_Main
     */
    public DSC_RouteView() {
        initComponents();
        btnShowOther.setText("Show inactive");
        disableChecks();
        btnSave.setVisible(false);
        pnlNew.setVisible(false);
        setRoutesList("Active");
        lstRoutes.setSelectedIndex(0);
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void setRoutesList(String active) {
        //Get children of routes
        Firebase tableRef = DBClass.getInstance().child("Routes");
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                allRoutes.clear();
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
                if (active.equalsIgnoreCase("Active")) {
                    DefaultListModel model = new DefaultListModel();
                    for (Route r : allRoutes) {
                        if (r.isActive()) {
                            model.addElement(r);
                        }
                    }
                    lstRoutes.setModel(model);
                    lstRoutes.setSelectedIndex(0);
                    String curr = getSelectedRoute();
                    setSuburbsList(curr, "Active");
                } else if (active.equalsIgnoreCase("Inactive")) {
                    DefaultListModel model = new DefaultListModel();
                    for (Route r : allRoutes) {
                        if (!r.isActive()) {
                            model.addElement(r);
                        }
                    }
                    lstRoutes.setModel(model);
                    lstRoutes.setSelectedIndex(0);
                    if (lstRoutes.getSelectedIndex() <= 0) {
                        JOptionPane.showMessageDialog(null, "There are no inactive routes");
                    } else {
                        String curr = getSelectedRoute();
                        setSuburbsList(curr, "Inactive");
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

    }

    private void setSuburbsList(String routeNum, String active) {
        //Get children of routes > suburbs
        Firebase tableRef = DBClass.getInstance().child("Routes");
        tableRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                suburbs.clear();
                for (DataSnapshot data : ds.getChildren()) {
                    if (!data.getKey().equals("0")) {
                        if (data.getKey().equals(routeNum)) {
                            String subArr[] = data.child("Suburbs").getValue(String[].class);
                            for (int i = 0; i < subArr.length; i++) {
                                suburbs.add(subArr[i]);
                            }
                        }
                    }
                }
                if (active.equalsIgnoreCase("Active")) {
                    DefaultListModel model = new DefaultListModel();
                    for (String s : suburbs) {
//                        if () {
                        model.addElement(s);
//                        }
                    }
                    lstSuburbs.setModel(model);
                    lstSuburbs.setSelectedIndex(0);
                    
                } else if (active.equalsIgnoreCase("Inactive")) {

                }
                setTextFields();
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });

    }

    private void disableChecks() {
        chbAfternoon.setEnabled(false);
        chbLateAfternoon.setEnabled(false);
        chbEvening.setEnabled(false);
    }
    
    private void enableChecks(){
        chbAfternoon.setEnabled(true);
        chbLateAfternoon.setEnabled(true);
        chbEvening.setEnabled(true);
    }
    
    private String getSelectedRoute(){
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
    
    private void setTextFields(){
        String routeID = getSelectedRoute();
        txfRouteID.setText(routeID);
        String suburbID = lstSuburbs.getSelectedIndex()+"";
        txfSuburbID.setText(suburbID);
        String suburb = lstSuburbs.getSelectedValue();
        txfSuburbName.setText(suburb);
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
        pnlRoutes = new javax.swing.JPanel();
        lblRoutes = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstRoutes = new javax.swing.JList<>();
        btnAddRoute = new javax.swing.JButton();
        btnDeleteRoute = new javax.swing.JButton();
        pnlFields = new javax.swing.JPanel();
        btnBack = new javax.swing.JButton();
        btnEdit = new javax.swing.JButton();
        lblInformation = new javax.swing.JLabel();
        pnlInfo = new javax.swing.JPanel();
        lblRouteID = new javax.swing.JLabel();
        lblCurrDriver = new javax.swing.JLabel();
        lblSubName = new javax.swing.JLabel();
        lblTimes = new javax.swing.JLabel();
        txfRouteID = new javax.swing.JTextField();
        txfCurrDriver = new javax.swing.JTextField();
        txfSuburbName = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        txfSuburbID = new javax.swing.JTextField();
        chbAfternoon = new javax.swing.JCheckBox();
        chbLateAfternoon = new javax.swing.JCheckBox();
        chbEvening = new javax.swing.JCheckBox();
        pnlNew = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        btnChangeDriver = new javax.swing.JButton();
        btnShowOther = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        pnlSuburbs = new javax.swing.JPanel();
        lblSuburbs = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstSuburbs = new javax.swing.JList<>();
        btnDeleteSuburb = new javax.swing.JButton();
        btnAddSuburb = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Route View");
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlHeadingLayout.setVerticalGroup(
            pnlHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeadingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlRoutes.setBackground(new java.awt.Color(0, 204, 51));
        pnlRoutes.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblRoutes.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblRoutes.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblRoutes.setText("Routes");

        lstRoutes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lstRoutes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstRoutes.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstRoutesValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstRoutes);

        btnAddRoute.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAddRoute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Add.png"))); // NOI18N
        btnAddRoute.setText("Add");
        btnAddRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRouteActionPerformed(evt);
            }
        });

        btnDeleteRoute.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDeleteRoute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Bin.png"))); // NOI18N
        btnDeleteRoute.setText("Delete");

        javax.swing.GroupLayout pnlRoutesLayout = new javax.swing.GroupLayout(pnlRoutes);
        pnlRoutes.setLayout(pnlRoutesLayout);
        pnlRoutesLayout.setHorizontalGroup(
            pnlRoutesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRoutesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRoutesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRoutes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addGroup(pnlRoutesLayout.createSequentialGroup()
                        .addComponent(btnAddRoute)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDeleteRoute)))
                .addContainerGap())
        );
        pnlRoutesLayout.setVerticalGroup(
            pnlRoutesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlRoutesLayout.createSequentialGroup()
                .addComponent(lblRoutes, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlRoutesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddRoute)
                    .addComponent(btnDeleteRoute))
                .addContainerGap())
        );

        pnlFields.setBackground(new java.awt.Color(0, 204, 51));
        pnlFields.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnBack.setBackground(new java.awt.Color(255, 0, 0));
        btnBack.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setMnemonic('B');
        btnBack.setText("Back");
        btnBack.setMaximumSize(new java.awt.Dimension(71, 23));
        btnBack.setMinimumSize(new java.awt.Dimension(71, 23));
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

        lblInformation.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblInformation.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblInformation.setText("Information");

        pnlInfo.setBackground(new java.awt.Color(0, 204, 51));
        pnlInfo.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblRouteID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblRouteID.setText("Route ID:");

        lblCurrDriver.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblCurrDriver.setText("Current Driver:");

        lblSubName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSubName.setText("Suburb Name:");

        lblTimes.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblTimes.setText("Times:");

        txfRouteID.setEditable(false);
        txfRouteID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfRouteID.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txfCurrDriver.setEditable(false);
        txfCurrDriver.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfCurrDriver.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txfSuburbName.setEditable(false);
        txfSuburbName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfSuburbName.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel1.setText("Suburb ID:");

        txfSuburbID.setEditable(false);
        txfSuburbID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfSuburbID.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        chbAfternoon.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chbAfternoon.setText("Afternoon");

        chbLateAfternoon.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chbLateAfternoon.setText("Late Afternoon");

        chbEvening.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        chbEvening.setText("Evening");

        pnlNew.setBackground(new java.awt.Color(0, 204, 51));
        pnlNew.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "New Thing", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Name:");

        jTextField1.setText("jTextField1");

        javax.swing.GroupLayout pnlNewLayout = new javax.swing.GroupLayout(pnlNew);
        pnlNew.setLayout(pnlNewLayout);
        pnlNewLayout.setHorizontalGroup(
            pnlNewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTextField1)
                .addContainerGap())
        );
        pnlNewLayout.setVerticalGroup(
            pnlNewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlInfoLayout = new javax.swing.GroupLayout(pnlInfo);
        pnlInfo.setLayout(pnlInfoLayout);
        pnlInfoLayout.setHorizontalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblTimes, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblSubName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblCurrDriver, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addComponent(lblRouteID, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfCurrDriver)
                            .addComponent(txfRouteID)
                            .addComponent(txfSuburbName)
                            .addComponent(txfSuburbID, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlInfoLayout.createSequentialGroup()
                                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(chbEvening)
                                    .addComponent(chbLateAfternoon)
                                    .addComponent(chbAfternoon))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        pnlInfoLayout.setVerticalGroup(
            pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRouteID)
                    .addComponent(txfRouteID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(txfSuburbID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblCurrDriver)
                    .addComponent(txfCurrDriver, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSubName)
                    .addComponent(txfSuburbName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblTimes)
                    .addComponent(chbAfternoon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chbLateAfternoon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chbEvening)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlNew, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnChangeDriver.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnChangeDriver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/driver.gif"))); // NOI18N
        btnChangeDriver.setText("Change Driver");
        btnChangeDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeDriverActionPerformed(evt);
            }
        });

        btnShowOther.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnShowOther.setText("Show inactive");
        btnShowOther.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowOtherActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Save 2.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlFieldsLayout = new javax.swing.GroupLayout(pnlFields);
        pnlFields.setLayout(pnlFieldsLayout);
        pnlFieldsLayout.setHorizontalGroup(
            pnlFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlFieldsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFieldsLayout.createSequentialGroup()
                        .addComponent(btnShowOther)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnChangeDriver)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblInformation, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlFieldsLayout.setVerticalGroup(
            pnlFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFieldsLayout.createSequentialGroup()
                .addComponent(lblInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnBack, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnChangeDriver)
                        .addComponent(btnShowOther)
                        .addComponent(btnSave)
                        .addComponent(btnEdit)))
                .addContainerGap())
        );

        pnlSuburbs.setBackground(new java.awt.Color(0, 204, 51));
        pnlSuburbs.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblSuburbs.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        lblSuburbs.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSuburbs.setText("Suburbs");

        lstSuburbs.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lstSuburbs.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstSuburbs.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstSuburbsValueChanged(evt);
            }
        });
        jScrollPane2.setViewportView(lstSuburbs);

        btnDeleteSuburb.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDeleteSuburb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Bin.png"))); // NOI18N
        btnDeleteSuburb.setText("Delete");

        btnAddSuburb.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAddSuburb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Add.png"))); // NOI18N
        btnAddSuburb.setText("Add");
        btnAddSuburb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddSuburbActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlSuburbsLayout = new javax.swing.GroupLayout(pnlSuburbs);
        pnlSuburbs.setLayout(pnlSuburbsLayout);
        pnlSuburbsLayout.setHorizontalGroup(
            pnlSuburbsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSuburbsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlSuburbsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(lblSuburbs, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlSuburbsLayout.createSequentialGroup()
                        .addComponent(btnAddSuburb)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnDeleteSuburb)))
                .addContainerGap())
        );
        pnlSuburbsLayout.setVerticalGroup(
            pnlSuburbsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlSuburbsLayout.createSequentialGroup()
                .addComponent(lblSuburbs, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlSuburbsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddSuburb)
                    .addComponent(btnDeleteSuburb))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlRoutes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlSuburbs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlFields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addComponent(pnlHeading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlRoutes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlFields, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        this.dispose();
        new DSC_Main().setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        enableChecks();
        txfSuburbName.setEnabled(true);
        btnEdit.setVisible(false);
        btnSave.setVisible(true);
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnShowOtherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowOtherActionPerformed
        lstRoutes.setModel(new DefaultListModel<>());
        lstSuburbs.setModel(new DefaultListModel<>());
        if (btnShowOther.getText().equalsIgnoreCase("Show inactive")) {
            //show inactive routes
            setRoutesList("Inactive");
            btnShowOther.setText("Show active");
        } else {
            //show active routes
            setRoutesList("Active");
            btnShowOther.setText("Show inactive");
        }
    }//GEN-LAST:event_btnShowOtherActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed
        if (pnlNew.isVisible()) {
            //save to db
        } else {
            //save changes of times
        }
        disableChecks();
        btnSave.setVisible(false);
        btnEdit.setVisible(true);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAddRouteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRouteActionPerformed
        pnlNew.setBorder(new TitledBorder(new EtchedBorder(), "New Route"));
        pnlNew.setVisible(true);
        btnEdit.setVisible(false);
        btnSave.setVisible(true);
    }//GEN-LAST:event_btnAddRouteActionPerformed

    private void btnAddSuburbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSuburbActionPerformed
        pnlNew.setBorder(new TitledBorder(new EtchedBorder(), "New Suburb"));
        pnlNew.setVisible(true);
        btnEdit.setVisible(false);
        btnSave.setVisible(true);
    }//GEN-LAST:event_btnAddSuburbActionPerformed

    private void btnChangeDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeDriverActionPerformed
        this.dispose();
        new DSC_DriverDetails().setVisible(true);
    }//GEN-LAST:event_btnChangeDriverActionPerformed

    private void lstRoutesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstRoutesValueChanged
        String curr = lstRoutes.getSelectedIndex() + 1 + "";
        txfRouteID.setText(curr);
        String active = btnShowOther.getText();
        if (active.equalsIgnoreCase("Show inactive")) {
            active = "Active";
        } else {
            active = "Inactive";
        }
        setSuburbsList(curr, active);
    }//GEN-LAST:event_lstRoutesValueChanged

    private void lstSuburbsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstSuburbsValueChanged
        setTextFields();
    }//GEN-LAST:event_lstSuburbsValueChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddRoute;
    private javax.swing.JButton btnAddSuburb;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnChangeDriver;
    private javax.swing.JButton btnDeleteRoute;
    private javax.swing.JButton btnDeleteSuburb;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnShowOther;
    private javax.swing.JCheckBox chbAfternoon;
    private javax.swing.JCheckBox chbEvening;
    private javax.swing.JCheckBox chbLateAfternoon;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel lblCurrDriver;
    private javax.swing.JLabel lblInformation;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblRouteID;
    private javax.swing.JLabel lblRoutes;
    private javax.swing.JLabel lblSubName;
    private javax.swing.JLabel lblSuburbs;
    private javax.swing.JLabel lblTimes;
    private javax.swing.JList<String> lstRoutes;
    private javax.swing.JList<String> lstSuburbs;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlFields;
    private javax.swing.JPanel pnlHeading;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlNew;
    private javax.swing.JPanel pnlRoutes;
    private javax.swing.JPanel pnlSuburbs;
    private javax.swing.JTextField txfCurrDriver;
    private javax.swing.JTextField txfRouteID;
    private javax.swing.JTextField txfSuburbID;
    private javax.swing.JTextField txfSuburbName;
    // End of variables declaration//GEN-END:variables
}
