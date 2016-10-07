package DSC;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.awt.Color;
import java.awt.Cursor;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

/**
 * @author Michael Hamann
 */
public class DSC_RouteView extends javax.swing.JFrame {

    boolean editClicked = false;
    ArrayList<Route> allRoutes = new ArrayList<>();
    ArrayList<String> suburbs = new ArrayList<>();
    String driverName;
    String newRouteNum;

    /**
     * Creates new form DSC_Main
     */
    public DSC_RouteView() {
        initComponents();
        btnShowOther.setText("Show inactive");
        disableTime();
        btnSave.setVisible(false);
        pnlNew.setVisible(false);
        setRoutesList("Active");
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    private void disableTime() {
        rbtAfternoon.setEnabled(false);
        rbtLateAfternoon.setEnabled(false);
        rbtEvening.setEnabled(false);
    }

    private void enableTime() {
        rbtAfternoon.setEnabled(true);
        rbtLateAfternoon.setEnabled(true);
        rbtEvening.setEnabled(true);
    }

    private void disableFields() {
        txfRouteID.setEditable(false);
        txfSuburbID.setEditable(false);
        txfCurrDriver.setEditable(false);
        txfSuburbName.setEditable(false);
    }

    private void clearFields() {
        txfRouteID.setText(null);
        txfSuburbID.setText(null);
        txfSuburbName.setText(null);
        txfCurrDriver.setText(null);
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
                        r.setActive(data.child("Active").getValue(boolean.class));
                        r.setTimeFrame(data.child("TimeFrame").getValue(String.class));
                        allRoutes.add(r);
                    }
                }
                if (active.equalsIgnoreCase("Active")) {
                    DefaultListModel model = new DefaultListModel();
                    for (Route r : allRoutes) {
                        if (r.isActive()) {
                            model.addElement(r.toString());
                        }
                    }
                    lstRoutes.setModel(model);
                    lstRoutes.setSelectedIndex(0);
                    String curr = getSelectedRoute();
                    setTime(curr);
                    setSuburbsList(curr, "Active");
                } else if (active.equalsIgnoreCase("Inactive")) {
                    DefaultListModel model = new DefaultListModel();
                    for (Route r : allRoutes) {
                        if (!r.isActive()) {
                            model.addElement(r.toString());
                        }
                    }
                    lstRoutes.setModel(model);
                    lstRoutes.setSelectedIndex(0);
                    if (lstRoutes.getSelectedIndex() <= 0) {
                        JOptionPane.showMessageDialog(null, "There are no inactive routes");
                    } else {
                        String curr = getSelectedRoute();
                        setTime(curr);
                        setSuburbsList(curr, "Inactive");
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "Error: " + fe.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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

    private void getRouteDriver(String routeNum) {
        Firebase ref = DBClass.getInstance().child("Routes/" + routeNum);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot data : ds.getChildren()) {
                    for (DataSnapshot data2 : data.getChildren()) {
                        if (data.getKey().equalsIgnoreCase("Drivers")) {
                            if (data2.child("EndDate").getValue(String.class).equalsIgnoreCase("-")) {
                                String currDriverID = data2.child("DriverID").getValue(String.class);
                                getDriverName(currDriverID);
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
    }

    private void getDriverName(String id) {
        Firebase ref = DBClass.getInstance().child("Drivers/" + id);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                driverName = ds.child("DriverName").getValue(String.class);
                txfCurrDriver.setText(driverName);
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "Error: " + fe.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void setTextFields() {
        String routeID = getSelectedRoute();
        txfRouteID.setText(routeID);
        String suburbID = lstSuburbs.getSelectedIndex() + "";
        txfSuburbID.setText(suburbID);
        String suburb = lstSuburbs.getSelectedValue();
        txfSuburbName.setText(suburb);
        getRouteDriver(routeID);
    }

    private void setTime(String curr) {
        for (Route r : allRoutes) {
            if (r.getID().equalsIgnoreCase(curr)) {
                switch (r.getTimeFrame()) {
                    case "Afternoon":
                        rbtAfternoon.setSelected(true);
                        break;
                    case "Late Afternoon":
                        rbtLateAfternoon.setSelected(true);
                        break;
                    case "Evening":
                        rbtEvening.setSelected(true);
                        break;
                }
            }
        }
    }

    private boolean timeChanged(String curr, String selected) {
        boolean changed = false;
        for (Route r : allRoutes) {
            if (r.getID().equalsIgnoreCase(curr)) {
                if (!r.getTimeFrame().equalsIgnoreCase(selected)) {
                    changed = true;
                    r.setTimeFrame(selected);
                }
            }
        }
        return changed;
    }

    private void updateTimeFrame(String route, String newTime) {
        Firebase ref = DBClass.getInstance().child("Routes/" + route);
        ref.child("TimeFrame").setValue(newTime);
    }

    protected void addToMetaData(String ids, int value) {
        Firebase ref = DBClass.getInstance().child("META-Data");
        ref.child(ids).setValue(value);
    }

    private String getNewRoute() {
        Firebase ref = DBClass.getInstance().child("META-Data");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                newRouteNum = ds.child("RouteID").getValue(Integer.class) + "";
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "Error: " + fe.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        return newRouteNum;
    }

    private class RouteContainer {

        public boolean Active;
        public String Drivers;
        public String EndDate;
        public String StartingDate;
        public String Suburbs;
        public String TimeFrame;

        public RouteContainer(boolean Active, String Drivers, String EndDate, String StartingDate, String Suburbs, String TimeFrame) {
            this.Active = Active;
            this.Drivers = Drivers;
            this.EndDate = EndDate;
            this.StartingDate = StartingDate;
            this.Suburbs = Suburbs;
            this.TimeFrame = TimeFrame;
        }

    }

    private void addRoute() {
        String newRouteID = getNewRoute();
        Calendar cal = Calendar.getInstance();
        String startingDate = cal.getTimeInMillis() + "";
        Firebase ref = DBClass.getInstance().child("Routes");
        RouteContainer route = new RouteContainer(
                true,
                null,
                "-",
                startingDate,
                null,
                "Afternoon");
        ref.child(newRouteID).setValue(route);
    }

    private void addSuburb(String name, String route) {
        int count = 0;
        for (String s : suburbs) {
            count++;
        }
        String index = count + "";
        Firebase ref = DBClass.getInstance().child("Routes/" + route + "/Suburbs");
        ref.child(index).setValue(name);
    }

    private void updateSuburbName(String route, String index, String newName) {
        Firebase ref = DBClass.getInstance().child("Routes/" + route + "/Suburbs");
        ref.child(index).setValue(newName);
    }

    private void deleteSuburb(String subIndex, String route) {
        Firebase ref = DBClass.getInstance().child("Routes/" + route + "/Suburbs");
        ref.child(subIndex).removeValue();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbgTimeFrame = new javax.swing.ButtonGroup();
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
        lblSuburbID = new javax.swing.JLabel();
        txfSuburbID = new javax.swing.JTextField();
        pnlNew = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txfNewSuburb = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        btnCancel = new javax.swing.JButton();
        rbtEvening = new javax.swing.JRadioButton();
        rbtLateAfternoon = new javax.swing.JRadioButton();
        rbtAfternoon = new javax.swing.JRadioButton();
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
        btnAddRoute.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddRoute.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddRouteActionPerformed(evt);
            }
        });

        btnDeleteRoute.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDeleteRoute.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Bin.png"))); // NOI18N
        btnDeleteRoute.setText("Delete");
        btnDeleteRoute.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        javax.swing.GroupLayout pnlRoutesLayout = new javax.swing.GroupLayout(pnlRoutes);
        pnlRoutes.setLayout(pnlRoutesLayout);
        pnlRoutesLayout.setHorizontalGroup(
            pnlRoutesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlRoutesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlRoutesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblRoutes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        btnEdit.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
        txfRouteID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txfCurrDriver.setEditable(false);
        txfCurrDriver.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfCurrDriver.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfCurrDriver.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        txfSuburbName.setEditable(false);
        txfSuburbName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfSuburbName.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfSuburbName.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        lblSuburbID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSuburbID.setText("Suburb ID:");

        txfSuburbID.setEditable(false);
        txfSuburbID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfSuburbID.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfSuburbID.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        pnlNew.setBackground(new java.awt.Color(0, 204, 51));
        pnlNew.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)), "New Thing", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 12))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel2.setText("Name:");

        txfNewSuburb.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnAdd.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Add.png"))); // NOI18N
        btnAdd.setText("Add");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });

        btnCancel.setBackground(new java.awt.Color(255, 0, 0));
        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlNewLayout = new javax.swing.GroupLayout(pnlNew);
        pnlNew.setLayout(pnlNewLayout);
        pnlNewLayout.setHorizontalGroup(
            pnlNewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlNewLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnAdd)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel))
                    .addGroup(pnlNewLayout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(10, 10, 10)
                        .addComponent(txfNewSuburb, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlNewLayout.setVerticalGroup(
            pnlNewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewLayout.createSequentialGroup()
                .addGap(34, 34, 34)
                .addGroup(pnlNewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txfNewSuburb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(pnlNewLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAdd)
                    .addComponent(btnCancel))
                .addContainerGap())
        );

        rbgTimeFrame.add(rbtEvening);
        rbtEvening.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rbtEvening.setText("Evening");

        rbgTimeFrame.add(rbtLateAfternoon);
        rbtLateAfternoon.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rbtLateAfternoon.setText("Late Afternoon");

        rbgTimeFrame.add(rbtAfternoon);
        rbtAfternoon.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        rbtAfternoon.setSelected(true);
        rbtAfternoon.setText("Afternoon");

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
                            .addComponent(lblSuburbID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                    .addComponent(rbtAfternoon)
                                    .addComponent(rbtLateAfternoon)
                                    .addComponent(rbtEvening))
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
                    .addComponent(lblSuburbID)
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
                .addGroup(pnlInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTimes)
                    .addGroup(pnlInfoLayout.createSequentialGroup()
                        .addComponent(rbtAfternoon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtLateAfternoon)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(rbtEvening)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(pnlNew, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        btnChangeDriver.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnChangeDriver.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/driver.gif"))); // NOI18N
        btnChangeDriver.setText("Change Driver");
        btnChangeDriver.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnChangeDriver.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeDriverActionPerformed(evt);
            }
        });

        btnShowOther.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnShowOther.setText("Show inactive");
        btnShowOther.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnShowOther.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnShowOtherActionPerformed(evt);
            }
        });

        btnSave.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Save 2.png"))); // NOI18N
        btnSave.setText("Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnEdit)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnBack, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnEdit)
                        .addComponent(btnSave))
                    .addGroup(pnlFieldsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnChangeDriver)
                        .addComponent(btnShowOther)))
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
        btnDeleteSuburb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteSuburb.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteSuburbActionPerformed(evt);
            }
        });

        btnAddSuburb.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAddSuburb.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Add.png"))); // NOI18N
        btnAddSuburb.setText("Add");
        btnAddSuburb.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
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
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlSuburbs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
        if (editClicked) {
            int ans = JOptionPane.showConfirmDialog(this, "Do you wish to discard unsaved changes?");
            switch (ans) {
                case JOptionPane.YES_OPTION:
                    this.dispose();
                    new DSC_Main().setVisible(true);
                    break;
            }
        } else {
            this.dispose();
            new DSC_Main().setVisible(true);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnEditActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditActionPerformed
        if (lstRoutes.getSelectedIndex() >= 0) {
            enableTime();
            txfSuburbName.setEditable(true);
            txfSuburbName.requestFocusInWindow();
            txfSuburbName.setCursor(new Cursor(Cursor.TEXT_CURSOR));
            rbtAfternoon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            rbtLateAfternoon.setCursor(new Cursor(Cursor.HAND_CURSOR));
            rbtEvening.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnEdit.setVisible(false);
            btnSave.setVisible(true);
            editClicked = true;
            txfSuburbName.setForeground(Color.red);
            rbtAfternoon.setForeground(Color.red);
            rbtLateAfternoon.setForeground(Color.red);
            rbtEvening.setForeground(Color.red);
        } else {
            JOptionPane.showMessageDialog(null, "There is nothing to edit", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnEditActionPerformed

    private void btnShowOtherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnShowOtherActionPerformed
        clearFields();
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
        boolean changesMade = false;
        boolean changed = false;
        String time = "";
        if (rbtAfternoon.isSelected()) {
            time = "Afternoon";
        } else if (rbtLateAfternoon.isSelected()) {
            time = "Late Afternoon";
        } else if (rbtEvening.isSelected()) {
            time = "Evening";
        }

        changed = timeChanged(getSelectedRoute(), time);

        if (changed) {
            //save changes of times
            updateTimeFrame(getSelectedRoute(), time);
            changesMade = true;
        }

        if (!txfSuburbName.getText().trim().equalsIgnoreCase(lstSuburbs.getSelectedValue().toString())) {
            //save change of suburb name
            updateSuburbName(getSelectedRoute(), lstSuburbs.getSelectedIndex() + "", txfSuburbName.getText().trim());
            changesMade = true;
            String active = btnShowOther.getText();
            if (active.equalsIgnoreCase("Show inactive")) {
                active = "Active";
            } else {
                active = "Inactive";
            }
            setSuburbsList(getSelectedRoute(), active);
        }

        if (changesMade) {
            JOptionPane.showMessageDialog(null, "Changes saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
        }

        disableTime();
        editClicked = false;
        txfSuburbName.setForeground(Color.black);
        rbtAfternoon.setForeground(Color.gray);
        rbtLateAfternoon.setForeground(Color.gray);
        rbtEvening.setForeground(Color.gray);
        txfSuburbName.setEditable(false);
        txfSuburbName.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        rbtAfternoon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        rbtLateAfternoon.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        rbtEvening.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        btnSave.setVisible(false);
        btnEdit.setVisible(true);
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnAddRouteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddRouteActionPerformed
        //Add new route to firebase
        addRoute();
        String currID = getNewRoute();
        addToMetaData("RouteID", Integer.parseInt(currID) + 1);
        //refresh list

    }//GEN-LAST:event_btnAddRouteActionPerformed

    private void btnAddSuburbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddSuburbActionPerformed
        pnlNew.setBorder(new TitledBorder(new EtchedBorder(), "New Suburb"));
        pnlNew.setVisible(true);
        btnEdit.setVisible(false);
        btnSave.setVisible(false);
    }//GEN-LAST:event_btnAddSuburbActionPerformed

    private void btnChangeDriverActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeDriverActionPerformed
        if (editClicked) {
            int ans = JOptionPane.showConfirmDialog(this, "Do you wish to discard unsaved changes?");
            switch (ans) {
                case JOptionPane.YES_OPTION:
                    this.dispose();
                    new DSC_DriverDetails().setVisible(true);
                    break;
            }
        } else {
            this.dispose();
            new DSC_DriverDetails().setVisible(true);
        }
    }//GEN-LAST:event_btnChangeDriverActionPerformed

    private void lstRoutesValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstRoutesValueChanged
        String curr = getSelectedRoute();
        setTime(curr);
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

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        if (!txfNewSuburb.getText().trim().isEmpty()) {
            String newSuburb = txfNewSuburb.getText().trim();
            //Add to firebase
            addSuburb(newSuburb, getSelectedRoute());
            String curr = getSelectedRoute();
            String active = btnShowOther.getText();
            if (active.equalsIgnoreCase("Show inactive")) {
                active = "Active";
            } else {
                active = "Inactive";
            }
            setSuburbsList(curr, active);
            txfNewSuburb.setText(null);
            pnlNew.setVisible(false);
            btnEdit.setVisible(true);
            JOptionPane.showMessageDialog(null, newSuburb + " has been added to Route " + getSelectedRoute(), "Success", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Please enter a suburb name", "Error", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnDeleteSuburbActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteSuburbActionPerformed
        String route = getSelectedRoute();
        String suburb = lstSuburbs.getSelectedValue();
        String subIndex = lstSuburbs.getSelectedIndex() + "";

        int ans = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete "
                + suburb + " from Route " + route + "?");
        if (ans == JOptionPane.YES_OPTION) {
            deleteSuburb(subIndex, route);
            String active = btnShowOther.getText();
            if (active.equalsIgnoreCase("Show inactive")) {
                active = "Active";
            } else {
                active = "Inactive";
            }
            setSuburbsList(route, active);
            JOptionPane.showMessageDialog(null, suburb + " has been deleted from Route " + route, "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_btnDeleteSuburbActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        txfNewSuburb.setText(null);
        pnlNew.setVisible(false);
        btnEdit.setVisible(true);
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnAddRoute;
    private javax.swing.JButton btnAddSuburb;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnChangeDriver;
    private javax.swing.JButton btnDeleteRoute;
    private javax.swing.JButton btnDeleteSuburb;
    private javax.swing.JButton btnEdit;
    private javax.swing.JButton btnSave;
    private javax.swing.JButton btnShowOther;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblCurrDriver;
    private javax.swing.JLabel lblInformation;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblRouteID;
    private javax.swing.JLabel lblRoutes;
    private javax.swing.JLabel lblSubName;
    private javax.swing.JLabel lblSuburbID;
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
    private javax.swing.ButtonGroup rbgTimeFrame;
    private javax.swing.JRadioButton rbtAfternoon;
    private javax.swing.JRadioButton rbtEvening;
    private javax.swing.JRadioButton rbtLateAfternoon;
    private javax.swing.JTextField txfCurrDriver;
    private javax.swing.JTextField txfNewSuburb;
    private javax.swing.JTextField txfRouteID;
    private javax.swing.JTextField txfSuburbID;
    private javax.swing.JTextField txfSuburbName;
    // End of variables declaration//GEN-END:variables
}
