package DSC;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerDateModel;
import javax.swing.table.DefaultTableModel;

/**
 * @author Amina Latief
 */

public class DSC_ViewOrder extends javax.swing.JFrame {
    
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
//INITIALIZATION
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
   
    //max order table value
    int tbcounter = 30;
    
    //global count used for next and previous buttons, in finding which list the user is currently veiwing
    int count = 0;
    int count2 = 0;
    
    //total entries in database of orders (children count)
    int total;

    //variable that will be used to check if input from user was searched for
    boolean search = true;
    
    //variable that will be used to check if the edit button was clicked
    boolean orderEdited = false;
    
    //variable that will be used to check whether a row(order) from the order table has been selected
    boolean orderSelected = false;

    //ArrayList initialization
    
    //stores all client objects
    ArrayList<Client> allclients = new ArrayList<>();
    
    //stores all order objects
    ArrayList<Order> allorders = new ArrayList<>();
    
    //stores all objects currently displayed in the order table
    ArrayList<Order> tborders = new ArrayList<>();
    
    //stores all active orders
    ArrayList<Order> activeOrders = new ArrayList<>();
    
    //stores all inactive orders
    ArrayList<Order> inactiveOrders = new ArrayList<>();

    //stores all suburbs
    ArrayList<String> suburbs = new ArrayList<>();
    
    //stores all active suburbs
    ArrayList<String> activeSuburbs = new ArrayList<>();
    
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-

    /**
     * Creates new form DSC_Main
     */
    public DSC_ViewOrder() {
        initComponents();

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        
        //disables fields that should not be edited by the user
        txfClientID.setEnabled(false);
        txfOrderID.setEnabled(false);
        txfOrderRouteID.setEnabled(false);
        
        //disables all fields
        disableFields();

        btnSave.setEnabled(false);
        btnDeactivate.setText("Deactivate");
        cmbVeiw.setSelectedItem("Active");
        cmbSuburbs.removeAllItems();
        cmbSuburbs.addItem("Collection");
        btnDeactivate.setVisible(true);

        //methods getting client and order data from database
        saveClient();
        saveOrder();

        //methods getting suburbs from database
        callActiveSuburbs();
        callAllSuburbs();

        //contains table listener, if row selected it populates client fields, order fields and the meal table accordingly
        setTextFields();

        //spinner initialization for start and end dates
        Date datenow = Calendar.getInstance().getTime();
        SpinnerDateModel smb = new SpinnerDateModel(datenow, null, null, Calendar.HOUR_OF_DAY);
        spnStartDate.setModel(smb);
        spnEndDate.setModel(smb);

        //edit spinner to specific format of date
        JSpinner.DateEditor sd = new JSpinner.DateEditor(spnStartDate, "dd-MM-yyyy");
        JSpinner.DateEditor ed = new JSpinner.DateEditor(spnEndDate, "dd-MM-yyyy");
        spnStartDate.setEditor(sd);
        spnEndDate.setEditor(ed);

        //combobox listener, when item clicked table loads accordingly
        cmbListener();
        
        //get number of orders in the database
        getNumChildren();

    }
    
    
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
//FIELD METHODS
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
    
    public final void enableFields() {
        //enables client fields
        txfClientName.setEnabled(true);
        txfClientSurname.setEnabled(true);
        txfClientContactNo.setEnabled(true);
        txfClientAddress.setEnabled(true);
        txfAddInfo.setEnabled(true);
        txfClientEmail.setEnabled(true);
        txfAltNum.setEnabled(true);
        cmbSuburbs.setEnabled(true);
        
        //enables order fields
        spnOrderFamilySize.setEnabled(true);
        cmbDuration.setEnabled(true);
        spnEndDate.setEnabled(true);
        spnStartDate.setEnabled(true);
        txfOrderRouteID.setEnabled(true);
        
        //enables meal buttons
        btnAddMeal.setEnabled(true);
        btnRemoveMeal.setEnabled(true);
        btnEditMeal.setEnabled(true);
    }

    public final void disableFields() {
        //disables client fields
        txfClientName.setEnabled(false);
        txfClientSurname.setEnabled(false);
        txfClientContactNo.setEnabled(false);
        txfClientAddress.setEnabled(false);
        txfAddInfo.setEnabled(false);
        txfClientEmail.setEnabled(false);
        txfAltNum.setEnabled(false);
        cmbSuburbs.setEnabled(false);
        
        //disables order fields
        spnOrderFamilySize.setEnabled(false);
        spnStartDate.setEnabled(false);
        txfOrderRouteID.setEnabled(false);
        cmbDuration.setEnabled(false);
        spnEndDate.setEnabled(false);
        
        //disables meal buttons
        btnAddMeal.setEnabled(false);
        btnRemoveMeal.setEnabled(false);
        btnEditMeal.setEnabled(false);
    }

    public final void clearFields() {
        
        //clears all client fields
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
        
         //clears all order fields
        txfOrderID.setText(null);
        spnOrderFamilySize.setValue(0);
        txfOrderRouteID.setText(null);
        cmbDuration.setSelectedIndex(0);
        
        //clears the meal table
        DefaultTableModel model = (DefaultTableModel) tblOrderTable.getModel();
        model.setRowCount(0);
        
    }
    
    private boolean checkEmpty() {
        boolean empty = false;

        //checks if required fiels are empty
        if (txfClientName.getText().isEmpty() || txfClientSurname.getText().isEmpty() || txfClientContactNo.getText().isEmpty()
                || txfClientAddress.getText().isEmpty() || txfClientContactNo.getText().isEmpty() || txfClientEmail.getText().isEmpty()) {
            empty = true;
        }

        return empty;
    }
    
    private void setTextFields() {

        DefaultTableModel mealmodel = (DefaultTableModel) tblMeals.getModel();

        tblOrderTable.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override

            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orderSelected = true;
                if (orderEdited == false) {
                    mealmodel.setRowCount(0);

                    int row = tblOrderTable.rowAtPoint(evt.getPoint());

                    if (tborders.get(row).isActive()) {
                        btnDeactivate.setText("Deactivate");
                    } else {
                        btnDeactivate.setText("Activate");
                    }

                    txfClientID.setText(tborders.get(row).getClient().getID());
                    txfClientName.setText(tborders.get(row).getClient().getName());
                    txfClientSurname.setText(tborders.get(row).getClient().getSurname());
                    txfClientAddress.setText(tborders.get(row).getClient().getAddress());
                    txfAddInfo.setText(tborders.get(row).getClient().getAdditionalInfo());
                    txfClientContactNo.setText(tborders.get(row).getClient().getContactNumber());
                    txfAltNum.setText(tborders.get(row).getClient().getAlternativeNumber());
                    txfClientEmail.setText(tborders.get(row).getClient().getEmail());
                    cmbSuburbs.setSelectedItem(tborders.get(row).getClient().getSuburb().trim());
                    txfOrderID.setText(tborders.get(row).getID());
                    cmbDuration.setSelectedItem(tborders.get(row).getDuration());

                    Date date = tborders.get(row).getStartingDate().getTime();
                    SpinnerDateModel smb = new SpinnerDateModel(date, null, null, Calendar.HOUR_OF_DAY);
                    spnStartDate.setModel(smb);
                    JSpinner.DateEditor d = new JSpinner.DateEditor(spnStartDate, "dd-MM-yyyy");
                    spnStartDate.setEditor(d);

                    spnOrderFamilySize.setValue(tborders.get(row).getFamilySize());
                    if (tborders.get(row).getEndDate() == null) {
                    } else {
                        Date enddate = tborders.get(row).getEndDate().getTime();
                        SpinnerDateModel endD = new SpinnerDateModel(enddate, null, null, Calendar.HOUR_OF_DAY);
                        spnEndDate.setModel(endD);
                        JSpinner.DateEditor endDE = new JSpinner.DateEditor(spnEndDate, "dd-MM-yyyy");
                        spnStartDate.setEditor(endDE);
                    }
                    txfOrderRouteID.setText(tborders.get(row).getRoute());

                    for (Meal meals : tborders.get(row).getMeals()) {
                        mealmodel.addRow(meals.returnObj());
                    }
                    mealmodel.fireTableDataChanged();
                } else {
                    tblOrderTable.setEnabled(false);
                }
            }
        });

    }
    
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
//ARRAYLIST POPULATION 
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
    
    private void callActiveSuburbs() {
        Firebase ref = DBClass.getInstance().child("Routes");

        //gets all active suburbs
        ref.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    //stores all suburbs within node into an array
                    String subList[] = dataSnapshot.child("Suburbs").getValue(String[].class);
                    //iterates through array
                    for (int i = 0; i < subList.length; i++) {
                        
                        if (subList[i].equals("Collection")) {
                        } else {
                            //adds suburb to arraylist
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
    }

    private void callAllSuburbs() {
        Firebase ref = DBClass.getInstance().child("Routes");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    //stores all suburbs within node into an array
                    String subArr[] = dataSnapshot.child("Suburbs").getValue(String[].class);
                    //iterates through array
                    for (int i = 0; i < subArr.length; i++) {
                        if (subArr[0].equals("Collection")) {
                        } else {
                            //adds suburb to arraylist of all suburbs as well as to the combobox
                            suburbs.add(subArr[i]);
                            cmbSuburbs.addItem(subArr[i]);
                        }
                    }

                }

            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "Unable to fetch suburbs.\nCheck logs for error report.", "Suburb Error", JOptionPane.ERROR_MESSAGE);
                System.err.print("Database connection error (Suburb): " + fe);
            }
        });

    }

    private void saveOrder() {
        LoadIcon load = new LoadIcon();
        load.iconLoader(lblLoad);
        Firebase ord = DBClass.getInstance().child("Orders");// Go to specific Table

        ord.addChildEventListener(new ChildEventListener() {
            // Retrieve new posts as they are added to the database
            long ordercount = 0;
            int activecount=0;
            @Override
            public void onChildAdded(DataSnapshot ds, String previousChildKey) {
                getNumChildren();
                ordercount++;
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

                if (o.isActive() && count2 < tbcounter) {
                    setOrderTB(o, model);
                    activeOrders.add(o);
                    activecount++;
                    count2++;

                } else if (o.isActive()) {
                    activeOrders.add(o);
                    activecount++;
                }
                if (o.isActive() == false && count2 < tbcounter) {
                    inactiveOrders.add(o);
                    count2++;
                } else if (o.isActive() == false) {
                    inactiveOrders.add(o);
                }
                if (activecount == getNrOfOrders()) {
                    lblLoad.setIcon(null);
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
                        reloadTB();
                    }
                }
            }

            @Override
            public void onChildRemoved(DataSnapshot ds) {
                String id = txfOrderID.getText();
                for (int i = 0; i < allorders.size(); i++) {
                    if (allorders.get(i).getID().equals(id)) {
                        allorders.remove(allorders.get(i));
                        if (allorders.get(i).isActive()) {
                            for (int j = 0; j < activeOrders.size(); j++) {
                                if (activeOrders.get(j).getID().equals(allorders.get(i).getID())) {
                                    activeOrders.remove(activeOrders.get(j));
                                }
                            }
                        } else {
                            for (int j = 0; j < inactiveOrders.size(); j++) {
                                if (inactiveOrders.get(j).getID().equals(allorders.get(i).getID())) {
                                    inactiveOrders.remove(activeOrders.get(j));
                                }
                            }
                        }
                    }
                }
                reloadTB();
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

    private void saveClient() {
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
    
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
//NUMBER OF ORDER ENTRIES METHODS -> LOADING
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
    
    private void getNumChildren() {
        Firebase ord = DBClass.getInstance().child("Orders");// Go to specific Table

        ord.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                setNrOfOrders((int) ds.getChildrenCount());
            }

            @Override

            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        }
        );
    }

    public int getNrOfOrders () {
        return total;
    }

    public void setNrOfOrders(int total) {
        this.total = total;
    }
    
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
    
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
        lblSearchBy = new javax.swing.JLabel();
        btnSearch = new javax.swing.JButton();
        cmbVeiw = new javax.swing.JComboBox<>();
        btnPrevious = new javax.swing.JButton();
        btnNext = new javax.swing.JButton();
        lblLoad = new javax.swing.JLabel();
        lblPrevious = new javax.swing.JLabel();
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
        btnEditMeal = new javax.swing.JButton();

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
        pnlTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        btnPrevious.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/go_previous_1.png"))); // NOI18N
        btnPrevious.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPreviousActionPerformed(evt);
            }
        });

        btnNext.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/go_previous.png"))); // NOI18N
        btnNext.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNextActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTableLayout = new javax.swing.GroupLayout(pnlTable);
        pnlTable.setLayout(pnlTableLayout);
        pnlTableLayout.setHorizontalGroup(
            pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTableLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTableLayout.createSequentialGroup()
                        .addComponent(lblSearchBy, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cmbSearchColumn, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txfSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(cmbVeiw, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(pnlTableLayout.createSequentialGroup()
                        .addComponent(btnPrevious)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 77, Short.MAX_VALUE)
                        .addComponent(lblPrevious)
                        .addGap(956, 956, 956)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(btnSearch, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lblLoad, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cmbVeiw, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlTableLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnPrevious, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnNext, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlTableLayout.createSequentialGroup()
                        .addComponent(lblPrevious)
                        .addContainerGap())))
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
                        .addGap(75, 75, 75))
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
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblMeals.setSurrendersFocusOnKeystroke(true);
        tblMeals.getTableHeader().setReorderingAllowed(false);
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

        btnEditMeal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Edit 2.png"))); // NOI18N
        btnEditMeal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditMeal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditMealActionPerformed(evt);
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
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                                .addComponent(btnAddMeal, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnRemoveMeal)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnEditMeal)
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
                .addComponent(pnlTable, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEditMeal)
                            .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(btnSave)
                                .addComponent(btnBack)
                                .addComponent(btnAddMeal)
                                .addComponent(btnRemoveMeal))))
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

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        //path to specific node in database
        Firebase upd = DBClass.getInstance().child("Clients/" + txfClientID.getText());

        //method checks if all fields are filled
        boolean empty = checkEmpty();

        int famSize = (int) spnOrderFamilySize.getValue();
        int total = 0;

        for (int i = 0; i < tblMeals.getRowCount(); i++) {
            int quantity = Integer.parseInt(tblMeals.getValueAt(i, 0).toString());
            total = total + quantity;
        }

        if (empty) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
            //displays message to user if all fields have not been filled in
        } else if (total != famSize) {
            JOptionPane.showMessageDialog(this, "Total meals does not correspond with family size.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            boolean subActive = false; //variable used to track whether suburbs edited are active

            //loops through all active suburbs and checks whether the suburb changed is active
            for (String s : activeSuburbs) {
                if (s.equals(cmbSuburbs.getSelectedItem().toString())) {

                    if (orderEdited) {

                        //updates all values in database
                        upd.child("Suburb").setValue(cmbSuburbs.getSelectedItem().toString());
                        upd.child("Name").setValue(txfClientName.getText().trim());
                        upd.child("Surname").setValue(txfClientSurname.getText().trim());
                        upd.child("Address").setValue(txfClientAddress.getText().trim());
                        upd.child("ContactNum").setValue(txfClientContactNo.getText().trim());
                        upd.child("AdditionalInfo").setValue(txfAddInfo.getText().trim());
                        upd.child("AlternativeNumber").setValue(txfAltNum.getText().trim());
                        upd.child("Email").setValue(txfClientEmail.getText().trim());

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
                            Calendar e = Calendar.getInstance();
                            e.setTime(enddate);
                            e.add(Calendar.HOUR, 0);
                            e.add(Calendar.MINUTE, 0);
                            e.add(Calendar.SECOND, 0);

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
                    disableFields();
                    btnSave.setEnabled(false);
                    btnEditOrder.setEnabled(true);
                    tblOrderTable.setEnabled(true);

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

                        if (orderEdited) {
                            orderEdited = false;
                            DefaultTableModel meals = (DefaultTableModel) tblMeals.getModel();

                            //updates all values in database
                            upd.child("Suburb").setValue(cmbSuburbs.getSelectedItem().toString());
                            upd.child("Name").setValue(txfClientName.getText().trim());
                            upd.child("Surname").setValue(txfClientSurname.getText().trim());
                            upd.child("Address").setValue(txfClientAddress.getText().trim());
                            upd.child("ContactNum").setValue(txfClientContactNo.getText().trim());
                            upd.child("AdditionalInfo").setValue(txfAddInfo.getText().trim());
                            upd.child("AlternativeNumber").setValue(txfAltNum.getText().trim());
                            upd.child("Email").setValue(txfClientEmail.getText().trim());

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
                        disableFields();
                        btnSave.setEnabled(false);
                        btnEditOrder.setEnabled(true);
                        tblOrderTable.setEnabled(true);

                        orderEdited = false;

                        break;

                    case JOptionPane.NO_OPTION:

                        break;

                    //no changes are saved if cancel is selected,relevant components are simply enabled and disabled
                    case JOptionPane.CANCEL_OPTION:
                        disableFields();
                        btnSave.setEnabled(false);
                        btnEditOrder.setEnabled(true);
                        break;
                }
            }

        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        //if user pressed the edit button
        if (orderEdited) {
            //ask user if they want to discard changes made
            int ans = JOptionPane.showConfirmDialog(this, "Do you wish to discard unsaved changes?");
            
            switch (ans) {
                case JOptionPane.YES_OPTION:
                    disableFields();
                    btnEditOrder.setEnabled(true);
                    tblOrderTable.setEnabled(true);
                    orderEdited = false;
                    break;
                case JOptionPane.NO_OPTION:
                    btnSave.setEnabled(true);
                    break;
                default:
                    btnSave.setEnabled(true);
                    break;
            }
        } else {
            //discard this frame
            this.dispose();
            //display new Main frame
            new DSC_Main().setVisible(true);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        
        //gets which type of value the user is searching for ex. name, contact nr, etc.
        String column = (String) cmbSearchColumn.getSelectedItem();
        
        //input typed by user
        String searchFor = txfSearch.getText();
        
        //whether user wants to see "All", "Active",or "Inactive"
        String veiw = (String) cmbVeiw.getSelectedItem();

        //clears order table
        DefaultTableModel model = (DefaultTableModel) tblOrderTable.getModel();
        model.setRowCount(0);

        //clears all fields
        clearFields();
        
        //clears order tables arraylist 
        tborders.clear();

        //checks that the user actually entered something
        if (txfSearch.getText().equals("")) {
            JOptionPane.showMessageDialog(rootPane, "Please enter search value!");
            search = false;
        } else {
            search=true;
            switch (veiw) {
                case "All":
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
        //sets all textfields according to row selected in table
        setTextFields();
        
        //if unable to find what the user is looking for display message
        if (tblOrderTable.getRowCount() < 1 && search) {
            JOptionPane.showMessageDialog(rootPane, "No orders found for '" + searchFor + "'.");
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void btnEditOrderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditOrderActionPerformed
        // if a row is selected from the table
        if (orderSelected) {
            enableFields();
            btnEditOrder.setEnabled(false);
            btnSave.setEnabled(true);
            orderEdited = true;
        } else {
            JOptionPane.showMessageDialog(rootPane, "No order has been selected to edit!");
        }
    }//GEN-LAST:event_btnEditOrderActionPerformed

    private void btnDeactivateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeactivateActionPerformed

        //set firebase reference
        Firebase updOrder = DBClass.getInstance().child("Orders/" + txfOrderID.getText());
        boolean orderactive = true;
        int index = -1;
        
        //for each order if the order is the one the user wants to deactivate/activate
        //change order status in database : active -> false/true
        //as well as in their arraylists
        for (Order o : allorders) {
            if (txfOrderID.getText().equals(o.getID())) {
                if (o.isActive()) {
                    orderactive = true;
                    updOrder.child("Active").setValue(false);
                    btnDeactivate.setText("Activate");
                } else {
                    orderactive = false;
                    updOrder.child("Active").setValue(true);
                    btnDeactivate.setText("Deactivate");
                }
            }
        }
        
        //change the objects status in other appropriate arraylists as well
        //removing from current arraylist and adding to another
        if (orderactive) {
            for (int i = 0; i < activeOrders.size(); i++) {
                if (activeOrders.get(i).getID().equals(txfOrderID.getText())) {
                    index = i;
                }
            }
            inactiveOrders.add(activeOrders.get(index));
            activeOrders.remove(index);
        } else {
            for (int i = 0; i < inactiveOrders.size(); i++) {
                if (inactiveOrders.get(i).getID().equals(txfOrderID.getText())) {
                    index = i;
                }
            }
            activeOrders.add(inactiveOrders.get(index));
            inactiveOrders.remove(index);
        }

    }//GEN-LAST:event_btnDeactivateActionPerformed

    private void btnRemoveMealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRemoveMealActionPerformed
        DefaultTableModel mealmod = (DefaultTableModel) tblMeals.getModel();
        mealmod.removeRow(tblMeals.getSelectedRow());
    }//GEN-LAST:event_btnRemoveMealActionPerformed

    private void btnAddMealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMealActionPerformed
        //creates Meal
        Meal m = new Meal(0, "Standard", "-", "-");

        //displays meal pane
        DSC_PlaceOrder_Mealpane mp = new DSC_PlaceOrder_Mealpane(tblMeals.getSelectedRow(), m);
        mp.setBackViewOrder(this);
        mp.setVisible(true);
        mp.setFocusableWindowState(true);
        
        //disable current frame
        this.setEnabled(false);
    }//GEN-LAST:event_btnAddMealActionPerformed

    private void btnNextActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNextActionPerformed
        tborders.clear();
        
        DefaultTableModel ordertb = (DefaultTableModel) tblOrderTable.getModel();
        String view = (String) cmbVeiw.getSelectedItem();
        
        ArrayList<List<Order>> arrays;
        int parts = -1;

        count++;
        switch (view) {
            case "All":
                if ((allorders.size() % 30) == 0) {
                    parts = (allorders.size() / 30);
                } else {
                    parts = (allorders.size() / 30) + 1;
                }
                arrays = split(allorders);
                if (count < parts) {
                    ordertb.setRowCount(0);
                    List<Order> orders = arrays.get(count);
                    for (Order order : orders) {
                        setOrderTB(order, ordertb);
                    }
                } else {
                    count--;
                    JOptionPane.showMessageDialog(rootPane, "No further order entries.");
                }

                break;
            case "Active":
                if ((activeOrders.size() % 30) == 0) {
                    parts = (activeOrders.size() / 30);
                } else {
                    parts = (activeOrders.size() / 30) + 1;
                }
                arrays = split(activeOrders);
                if (count < parts) {
                    ordertb.setRowCount(0);
                    List<Order> orders2 = arrays.get(count);
                    for (Order order : orders2) {
                        setOrderTB(order, ordertb);
                    }
                } else {
                    count--;
                    JOptionPane.showMessageDialog(rootPane, "No further order entries.");
                }
                break;
            case "Inactive":
                if ((inactiveOrders.size() % 30) == 0) {
                    parts = (inactiveOrders.size() / 30);
                } else {
                    parts = (inactiveOrders.size() / 30) + 1;
                }
                arrays = split(inactiveOrders);
                if (count < parts) {
                    ordertb.setRowCount(0);
                    List inactorders = arrays.get(count);
                    for (Object order : inactorders) {
                        setOrderTB((Order) order, ordertb);
                    }
                } else {
                    count--;
                    JOptionPane.showMessageDialog(rootPane, "No further order entries.");
                }
                break;
        }


    }//GEN-LAST:event_btnNextActionPerformed

    private void btnPreviousActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPreviousActionPerformed
        tborders.clear();
        
        DefaultTableModel ordertb = (DefaultTableModel) tblOrderTable.getModel();
        String view = (String) cmbVeiw.getSelectedItem();

        ArrayList<List<Order>> arrays = new ArrayList();
        if (count < 1) {
            JOptionPane.showMessageDialog(rootPane, "No previous order entries.");
        } else {
            count--;
            switch (view) {
                case "All":
                    arrays = split(allorders);
                    ordertb.setRowCount(0);
                    List orders = arrays.get(count);
                    for (Object order : orders) {
                        setOrderTB((Order) order, ordertb);
                    }
                    break;
                case "Active":
                    arrays = split(activeOrders);
                    ordertb.setRowCount(0);
                    List actorders = arrays.get(count);
                    for (Object order : actorders) {
                        setOrderTB((Order) order, ordertb);
                    }
                    break;
                case "Inactive":
                    arrays = split(inactiveOrders);
                    ordertb.setRowCount(0);
                    List inactorders = arrays.get(count);
                    for (Object order : inactorders) {
                        setOrderTB((Order) order, ordertb);
                    }
                    break;
            }
        }

    }//GEN-LAST:event_btnPreviousActionPerformed

    private void btnEditMealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMealActionPerformed
        Meal m = new Meal((int) tblMeals.getValueAt(tblMeals.getSelectedRow(), 0), tblMeals.getValueAt(tblMeals.getSelectedRow(), 1) + "",
                tblMeals.getValueAt(tblMeals.getSelectedRow(), 2) + "", tblMeals.getValueAt(tblMeals.getSelectedRow(), 3) + "");

        DSC_PlaceOrder_Mealpane mp = new DSC_PlaceOrder_Mealpane(tblMeals.getSelectedRow(), m);
        mp.setBackViewOrder(this);
        mp.setVisible(true);
        mp.setFocusableWindowState(true);
        this.setEnabled(false);
    }//GEN-LAST:event_btnEditMealActionPerformed
    /**
     * @param args the command line arguments
     */
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
//COMBO BOX LISTENER, ON CLICK VALUE, TABLE RELOADS ACCORDINGLY   
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
    private void cmbListener() {

        cmbVeiw.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reloadTB();
            }
        });
    }
    
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
//ORDER TABLE METHODS
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-

    public void reloadTB() {
        
        DefaultTableModel ordertb = (DefaultTableModel) tblOrderTable.getModel();
        
        //fetches which item is selected from combobox
        String veiw = (String) cmbVeiw.getSelectedItem();
        
        //clears table
        ordertb.setRowCount(0);
        
        //clears arraylist of objects in table
        tborders.clear();
        
        //sets max elements allowed in table to 30
        tbcounter = 30;
        
        //initiates counter variable
        int counter = 0;
        
        //sets static count to zero
        count = 0;
        
        //depending on whether "All","Active" or "Inactive" is selected, the order table will be populated with the first 30 
        //elements from their ArrayList
        switch (veiw) {
            case "All":
                if (allorders.size() > counter || allorders.size() == counter) {
                    if (tbcounter > allorders.size()) {
                        while (counter < allorders.size() && counter < tbcounter) {
                            setOrderTB(allorders.get(counter), ordertb);
                            counter++;
                        }
                    } else {
                        while (counter < tbcounter) {
                            setOrderTB(allorders.get(counter), ordertb);
                            counter++;
                        }
                    }
                }
                break;
            case "Inactive":
                if (tbcounter > inactiveOrders.size()) {
                    while (counter < inactiveOrders.size() && counter < tbcounter) {
                        setOrderTB(inactiveOrders.get(counter), ordertb);
                        counter++;
                    }
                } else {
                    while (counter < tbcounter) {
                        setOrderTB(inactiveOrders.get(counter), ordertb);
                        counter++;
                    }
                }
                break;
            case "Active":
                if (tbcounter > activeOrders.size()) {
                    while (counter < activeOrders.size() && counter < tbcounter) {
                        setOrderTB(activeOrders.get(counter), ordertb);
                        counter++;
                    }
                } else {
                    while (counter < tbcounter) {
                        setOrderTB(activeOrders.get(counter), ordertb);
                        counter++;
                    }
                }
                break;
        }
        //notifies table of changes
        ordertb.fireTableDataChanged();
    }
    
    
    private void setOrderTB(Order order, DefaultTableModel d) {
        tborders.add(order);
        //creates row object
        Object[] row = {order.getClient().getName(), order.getClient().getSurname(), order.getClient().getContactNumber(),
            order.getClient().getEmail(), order.getClient().getSuburb(), order.isActive(), order.getDuration(), order.getFamilySize()};
        //adds this object to the order table
        d.addRow(row);
        //notifies all listeners cell values may have changed
        d.fireTableDataChanged();
    }
    
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
//METHODS ADDING & EDITING MEALS IN THE MEALS TABLE
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
   
    protected void addMealToList(Meal meal) {
        DefaultTableModel mealmod = (DefaultTableModel) tblMeals.getModel();
        
        //creates row object to be inserted in meal table
        Object[] row = {meal.getQuantity(), meal.getMealType(), meal.getAllergies(), meal.getExclusions()};
       
        //adds object to table
        mealmod.addRow(row);
        
        //notifies the table of the change
        mealmod.fireTableDataChanged();
    }

    protected void replaceMealOnList(Meal meal, int index) {
        DefaultTableModel mealmod = (DefaultTableModel) tblMeals.getModel();
        
        //removes the selected index
        mealmod.removeRow(index);
        
        //creates new row objects with edited data from the meal pane
        Object[] row = {meal.getQuantity(), meal.getMealType(), meal.getAllergies(), meal.getExclusions()};
       
        //adds the object to the meal table
        mealmod.addRow(row);
        
        //notifies the table of the change
        mealmod.fireTableDataChanged();
    }
  
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-
//METHOD TO SPLIT ARRAYLIST DATA INTO ARRAYS OF 30
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-

    public ArrayList<List<Order>> split(ArrayList<Order> list) {

        //k-> counter variable
        int k = 0;
        
        //initializes list for order objects, this list will later be added to another list
        List<Order> ordersplit;
        
        //creates a list of lists
        ArrayList<List<Order>> arrays = new ArrayList();
        
        //stores the size of the list parsed to the method
        int size = list.size();
        
        if (size % 30 == 0) {
            
            //parts defines the number of lists the parsed list will be split into
            int parts = size / 30;
            
            //loops for the number of parts
            for (int i = 0; i < parts; i++) {
                //creates new arraylist
                ordersplit = new ArrayList();
                
                //loops 30 times, in order to add 30 objects
                for (int j = 0; j < 30; j++) {
                    //checks whether he count is still within the size of the list
                    if (k < size) {
                        //adds the object at k of list to new list
                        ordersplit.add(list.get(k));
                        //increments k
                        k++;
                    }
                }
                //adds new list of 30 to another list
                arrays.add(ordersplit);
            }
        } else {
            
            //parts defines the number of lists the parsed list will be split into
            int parts = (size / 30) + 1;
            
            //loops for the number of parts
            for (int i = 0; i < parts; i++) {
                
                //creates new arraylist
                ordersplit = new ArrayList();
                
                 //loops 30 times, in order to add 30 objects
                for (int j = 0; j < 30; j++) {
                    
                    //checks whether he count is still within the size of the list
                    if (k < size) {
                        //adds the object at k of list to new list
                        ordersplit.add(list.get(k));
                        //increments k
                        k++;
                    }
                }
                //adds new list of 30 to another list
                arrays.add(ordersplit);
            }
        }
        //returns the list of lists
        return arrays;

    }
    
//=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=--=-=-=-=-=-=-=-=-=-=-

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
            java.util.logging.Logger.getLogger(DSC_ViewOrder.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DSC_ViewOrder.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DSC_ViewOrder.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);

        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DSC_ViewOrder.class
                    .getName()).log(java.util.logging.Level.SEVERE, null, ex);
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
    private javax.swing.JButton btnEditMeal;
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
    private javax.swing.JLabel lblLoad;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblOrderID;
    private javax.swing.JLabel lblOrdersDetails;
    private javax.swing.JLabel lblPrevious;
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
