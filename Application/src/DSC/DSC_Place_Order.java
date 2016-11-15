package DSC;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 * @author Rossouw Binedell
 */
public class DSC_Place_Order extends javax.swing.JFrame {

    private ArrayList<Meal> orderMeals = new ArrayList<>();
    private ArrayList<SuburbData> subList = new ArrayList<>();
    private String clientID = "1";
    private Calendar[] orderDates = new Calendar[4];
    private ArrayList<Route> routes = new ArrayList<>();
    private boolean online;
    private boolean connection;

    /**
     * Creates new form DSC_Main
     */
    public DSC_Place_Order() {
        initComponents();
    }

    public DSC_Place_Order(boolean online) {
        this.online = online;

        if (online) {
            initComponents();
            getSuburbs();

        } else {
            retryConnection();
            initComponents();
            btnBack.setEnabled(false);
            getSuburbsFromText();
            lblName.setText(lblName.getText() + "          --Offline");
        }
        getSurveyQuestions(online);
        getDates();
        refreshTable();
        rbtAfternoon.setEnabled(false);
        rbtEvening.setEnabled(false);
        rbtLateAfternoon.setEnabled(false);

        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rbgTimeSlots = new javax.swing.ButtonGroup();
        rbgTimeFrames = new javax.swing.ButtonGroup();
        pnlBackground = new javax.swing.JPanel();
        pnlHeading = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        pnlClientInfo = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        lblClientContactNumber = new javax.swing.JLabel();
        txfClientSurname = new javax.swing.JTextField();
        txfClientAdditionalInfo = new javax.swing.JTextField();
        txfClientContactNumber = new javax.swing.JTextField();
        txfClientEmail = new javax.swing.JTextField();
        lblClientEmail = new javax.swing.JLabel();
        lblClientCollection = new javax.swing.JLabel();
        lblClientAddress = new javax.swing.JLabel();
        lblClientSuburb = new javax.swing.JLabel();
        cmbClientSuburb = new javax.swing.JComboBox<>();
        lblClientSurname = new javax.swing.JLabel();
        ckbClientCollection = new javax.swing.JCheckBox();
        lblClientName = new javax.swing.JLabel();
        lblClientAlternativeNumber = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaClientDeliveryAddress = new javax.swing.JTextArea();
        lblClientAddInfo = new javax.swing.JLabel();
        txfClientAlternativeNumber = new javax.swing.JTextField();
        txfClientName = new javax.swing.JTextField();
        lblClientInfo = new javax.swing.JLabel();
        pnlSurveyInfo = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblSurveyReason = new javax.swing.JLabel();
        lblSurveySource = new javax.swing.JLabel();
        cmbSurveyReason = new javax.swing.JComboBox<>();
        cmbSurveySource = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        txaSurveyComments = new javax.swing.JTextArea();
        lblSurveyComments = new javax.swing.JLabel();
        lblSurveyInfo = new javax.swing.JLabel();
        pnlOrderInfo = new javax.swing.JPanel();
        lblOrderInfo = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        rbtAfternoon = new javax.swing.JRadioButton();
        rbtMonToThur = new javax.swing.JRadioButton();
        jLabel3 = new javax.swing.JLabel();
        cmbOrderDate = new javax.swing.JComboBox<>();
        rbtLateAfternoon = new javax.swing.JRadioButton();
        rbtMonToFri = new javax.swing.JRadioButton();
        jLabel2 = new javax.swing.JLabel();
        rbtEvening = new javax.swing.JRadioButton();
        pnlMealInfo = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        btnAddMeal = new javax.swing.JButton();
        btnEditMeal = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblOrderMeals = new javax.swing.JTable();
        txfMealsTotal = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        btnDeleteMeal = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        btnBack = new javax.swing.JButton();
        lblOrderInfo1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Place Order");
        setBackground(new java.awt.Color(0, 0, 0));
        setMinimumSize(new java.awt.Dimension(950, 730));

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

        pnlClientInfo.setBackground(new java.awt.Color(0, 204, 51));
        pnlClientInfo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel1.setBackground(new java.awt.Color(0, 204, 51));

        lblClientContactNumber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClientContactNumber.setText("Contact Number:* ");

        lblClientEmail.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClientEmail.setText("E-Mail Address: ");

        lblClientCollection.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClientCollection.setText("Collection: ");

        lblClientAddress.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClientAddress.setText("Delivery Address:* ");

        lblClientSuburb.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClientSuburb.setText("Suburb:* ");

        cmbClientSuburb.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Suburb" }));
        cmbClientSuburb.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbClientSuburbItemStateChanged(evt);
            }
        });

        lblClientSurname.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClientSurname.setText("Surname:* ");

        ckbClientCollection.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                ckbClientCollectionStateChanged(evt);
            }
        });

        lblClientName.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClientName.setText("Name:* ");

        lblClientAlternativeNumber.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClientAlternativeNumber.setText("Alternative Number: ");

        txaClientDeliveryAddress.setColumns(20);
        txaClientDeliveryAddress.setRows(5);
        txaClientDeliveryAddress.setWrapStyleWord(true);
        jScrollPane2.setViewportView(txaClientDeliveryAddress);

        lblClientAddInfo.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblClientAddInfo.setText("Additional Information: ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClientContactNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblClientSurname, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblClientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblClientEmail, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblClientAlternativeNumber, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txfClientSurname)
                    .addComponent(txfClientContactNumber)
                    .addComponent(txfClientAlternativeNumber)
                    .addComponent(txfClientName, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(txfClientEmail))
                .addGap(150, 150, 150))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblClientSuburb, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblClientAddress, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblClientAddInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblClientCollection, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(ckbClientCollection)
                    .addComponent(cmbClientSuburb, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane2)
                    .addComponent(txfClientAdditionalInfo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 350, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfClientName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblClientName))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClientSurname)
                    .addComponent(txfClientSurname, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClientContactNumber)
                    .addComponent(txfClientContactNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClientAlternativeNumber)
                    .addComponent(txfClientAlternativeNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfClientEmail, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblClientEmail))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClientCollection)
                    .addComponent(ckbClientCollection))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClientSuburb)
                    .addComponent(cmbClientSuburb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblClientAddress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfClientAdditionalInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblClientAddInfo))
                .addContainerGap())
        );

        lblClientInfo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblClientInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblClientInfo.setText("Client Info");

        javax.swing.GroupLayout pnlClientInfoLayout = new javax.swing.GroupLayout(pnlClientInfo);
        pnlClientInfo.setLayout(pnlClientInfoLayout);
        pnlClientInfoLayout.setHorizontalGroup(
            pnlClientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClientInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlClientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlClientInfoLayout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(lblClientInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlClientInfoLayout.setVerticalGroup(
            pnlClientInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClientInfoLayout.createSequentialGroup()
                .addComponent(lblClientInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, 387, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlSurveyInfo.setBackground(new java.awt.Color(0, 204, 51));
        pnlSurveyInfo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel2.setBackground(new java.awt.Color(0, 204, 51));

        lblSurveyReason.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSurveyReason.setText("Reason For Choosing: ");

        lblSurveySource.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSurveySource.setText("Refrence Source: ");

        cmbSurveyReason.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cmbSurveySource.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        txaSurveyComments.setColumns(20);
        txaSurveyComments.setLineWrap(true);
        txaSurveyComments.setRows(2);
        txaSurveyComments.setWrapStyleWord(true);
        jScrollPane3.setViewportView(txaSurveyComments);

        lblSurveyComments.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lblSurveyComments.setText("Comments: ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblSurveySource, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(lblSurveyReason)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(cmbSurveySource, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cmbSurveyReason, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblSurveyComments, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblSurveyReason)
                    .addComponent(cmbSurveyReason, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSurveyComments))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cmbSurveySource, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblSurveySource)))
        );

        lblSurveyInfo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblSurveyInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblSurveyInfo.setText("Survey Info");

        javax.swing.GroupLayout pnlSurveyInfoLayout = new javax.swing.GroupLayout(pnlSurveyInfo);
        pnlSurveyInfo.setLayout(pnlSurveyInfoLayout);
        pnlSurveyInfoLayout.setHorizontalGroup(
            pnlSurveyInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSurveyInfoLayout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(pnlSurveyInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblSurveyInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlSurveyInfoLayout.setVerticalGroup(
            pnlSurveyInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlSurveyInfoLayout.createSequentialGroup()
                .addComponent(lblSurveyInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlOrderInfo.setBackground(new java.awt.Color(0, 204, 51));
        pnlOrderInfo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblOrderInfo.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblOrderInfo.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOrderInfo.setText("Order Info");

        jPanel3.setBackground(new java.awt.Color(0, 204, 51));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("TimeFrame:* ");

        rbgTimeSlots.add(rbtAfternoon);
        rbtAfternoon.setSelected(true);
        rbtAfternoon.setText("Afternoon");

        rbgTimeFrames.add(rbtMonToThur);
        rbtMonToThur.setText("Monday - Thursday");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("TimeSlot:* ");

        cmbOrderDate.setMaximumRowCount(5);
        cmbOrderDate.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Date" }));

        rbgTimeSlots.add(rbtLateAfternoon);
        rbtLateAfternoon.setText("Late Afternoon");

        rbgTimeFrames.add(rbtMonToFri);
        rbtMonToFri.setSelected(true);
        rbtMonToFri.setText("Monday - Friday");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Starting Date:* ");

        rbgTimeSlots.add(rbtEvening);
        rbtEvening.setText("Evening");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cmbOrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(rbtAfternoon)
                    .addComponent(rbtLateAfternoon)
                    .addComponent(rbtEvening)
                    .addComponent(rbtMonToThur)
                    .addComponent(rbtMonToFri))
                .addContainerGap(69, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbOrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(rbtAfternoon))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtLateAfternoon)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtEvening)
                .addGap(13, 13, 13)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(rbtMonToFri))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(rbtMonToThur)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlOrderInfoLayout = new javax.swing.GroupLayout(pnlOrderInfo);
        pnlOrderInfo.setLayout(pnlOrderInfoLayout);
        pnlOrderInfoLayout.setHorizontalGroup(
            pnlOrderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlOrderInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlOrderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblOrderInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlOrderInfoLayout.setVerticalGroup(
            pnlOrderInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlOrderInfoLayout.createSequentialGroup()
                .addComponent(lblOrderInfo, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlMealInfo.setBackground(new java.awt.Color(0, 204, 51));
        pnlMealInfo.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jPanel4.setBackground(new java.awt.Color(0, 204, 51));

        btnAddMeal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Add.png"))); // NOI18N
        btnAddMeal.setText(" Add");
        btnAddMeal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAddMeal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddMealActionPerformed(evt);
            }
        });

        btnEditMeal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Edit 2.png"))); // NOI18N
        btnEditMeal.setText(" Edit");
        btnEditMeal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEditMeal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditMealActionPerformed(evt);
            }
        });

        tblOrderMeals.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Quantity", "MealType", "Allergy", "Exclusions"
            }
        ));
        tblOrderMeals.setColumnSelectionAllowed(true);
        jScrollPane5.setViewportView(tblOrderMeals);
        tblOrderMeals.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        if (tblOrderMeals.getColumnModel().getColumnCount() > 0) {
            tblOrderMeals.getColumnModel().getColumn(0).setResizable(false);
            tblOrderMeals.getColumnModel().getColumn(0).setPreferredWidth(10);
            tblOrderMeals.getColumnModel().getColumn(1).setResizable(false);
            tblOrderMeals.getColumnModel().getColumn(1).setPreferredWidth(20);
            tblOrderMeals.getColumnModel().getColumn(2).setResizable(false);
            tblOrderMeals.getColumnModel().getColumn(3).setResizable(false);
        }

        txfMealsTotal.setEditable(false);

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Total");
        jLabel7.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);

        btnDeleteMeal.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Bin.png"))); // NOI18N
        btnDeleteMeal.setText("Delete");
        btnDeleteMeal.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnDeleteMeal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteMealActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Save 2.png"))); // NOI18N
        btnSave.setText(" Save");
        btnSave.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        btnBack.setBackground(new java.awt.Color(255, 0, 0));
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("Back");
        btnBack.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnBack))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txfMealsTotal, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                        .addComponent(btnDeleteMeal)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnEditMeal)
                        .addGap(18, 18, 18)
                        .addComponent(btnAddMeal, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnAddMeal)
                    .addComponent(btnEditMeal)
                    .addComponent(btnDeleteMeal)
                    .addComponent(txfMealsTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnSave)
                    .addComponent(btnBack)))
        );

        lblOrderInfo1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblOrderInfo1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblOrderInfo1.setText("Meals Info");

        javax.swing.GroupLayout pnlMealInfoLayout = new javax.swing.GroupLayout(pnlMealInfo);
        pnlMealInfo.setLayout(pnlMealInfoLayout);
        pnlMealInfoLayout.setHorizontalGroup(
            pnlMealInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlMealInfoLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblOrderInfo1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlMealInfoLayout.setVerticalGroup(
            pnlMealInfoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlMealInfoLayout.createSequentialGroup()
                .addComponent(lblOrderInfo1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlClientInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlSurveyInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlOrderInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlMealInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addComponent(pnlHeading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addComponent(pnlOrderInfo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlMealInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addComponent(pnlClientInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(pnlSurveyInfo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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

    private void cmbClientSuburbItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbClientSuburbItemStateChanged
        changeTimeSlots();
    }//GEN-LAST:event_cmbClientSuburbItemStateChanged

    private void ckbClientCollectionStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_ckbClientCollectionStateChanged

        boolean checked = ckbClientCollection.isSelected();
        if (checked) {
            txaClientDeliveryAddress.setText("-");
            txfClientAdditionalInfo.setText("-");
            cmbClientSuburb.setSelectedIndex(0);
            cmbClientSuburb.setEnabled(false);
            txaClientDeliveryAddress.setEnabled(false);
            txfClientAdditionalInfo.setEnabled(false);
            rbtAfternoon.setEnabled(true);
            rbtAfternoon.setSelected(true);
            rbtLateAfternoon.setEnabled(true);
            rbtEvening.setEnabled(true);

        } else if (txaClientDeliveryAddress.getText().equals("-")) {
            txaClientDeliveryAddress.setText("");
            txfClientAdditionalInfo.setText("");
            cmbClientSuburb.setEnabled(true);
            txaClientDeliveryAddress.setEnabled(true);
            txfClientAdditionalInfo.setEnabled(true);
            rbtAfternoon.setEnabled(false);
            rbtLateAfternoon.setEnabled(false);
            rbtEvening.setEnabled(false);
            changeTimeSlots();
        }

    }//GEN-LAST:event_ckbClientCollectionStateChanged

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

        btnSave.setEnabled(false);
        boolean allGood = true;
        String invalid = "";

        String clientname = txfClientName.getText().trim();
        if (clientname.isEmpty()) {
            invalid += "\nName";
            allGood = false;
        }

        String clientSurname = txfClientSurname.getText().trim();
        if (clientSurname.isEmpty()) {
            invalid += "\nSurname";
            allGood = false;
        }

        String clientContactNumber = txfClientContactNumber.getText().trim();
        if ((clientContactNumber.isEmpty() || clientContactNumber.length() != 10 || !clientContactNumber.matches("[0-9]+"))) {
            invalid += "\nContact Number";
            allGood = false;
        }

        String clientAlternativeNumber = txfClientAlternativeNumber.getText().trim();
        if ((!(clientAlternativeNumber.length() == 0 || clientAlternativeNumber.length() == 10)
                || (!clientAlternativeNumber.matches("[0-9]+") && !clientAlternativeNumber.isEmpty()))) {
            invalid += "\nAlternative Contact Number";
            allGood = false;
        } else if (clientAlternativeNumber.isEmpty()) {
            clientAlternativeNumber = "N/A";
        }

        String clientEmail = txfClientEmail.getText().trim();
        if (!clientEmail.matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$") && !clientEmail.isEmpty()) {
            invalid += "\nEmail Address";
            allGood = false;
        }

        String clientSuburb;
        if (ckbClientCollection.isSelected()) {
            clientSuburb = "Collection";

        } else {
            clientSuburb = cmbClientSuburb.getSelectedItem().toString();
        }
        String clientAddress = txaClientDeliveryAddress.getText();
        if (clientAddress.isEmpty()) {
            invalid += "\nAddress";
            allGood = false;
        }

        String clientAdditionalInfo = txfClientAdditionalInfo.getText().trim();

        Calendar orderDate = Calendar.getInstance();
        if (cmbOrderDate.getSelectedIndex() == 4) {
            orderDate.setTimeInMillis(DriverReport.returnWeekMili());
        } else {
            orderDate = orderDates[cmbOrderDate.getSelectedIndex()];
        }

        String timeSlot = "";
        if (rbtAfternoon.isSelected()) {
            timeSlot = "Afternoon";
        } else if (rbtLateAfternoon.isSelected()) {
            timeSlot = "Late Afternoon";
        } else if (rbtEvening.isSelected()) {
            timeSlot = "Evening";
        }

        String routeID = "0";

        for (Route route : routes) {
            if (route.getSuburbs().contains(clientSuburb) && route.getTimeFrame().equals(timeSlot)) {
                routeID = route.getID();
            }
        }

        String timeFrame = "";
        if (rbtMonToFri.isSelected()) {
            timeFrame = "Monday - Friday";
        } else if (rbtMonToThur.isSelected()) {
            timeFrame = "Monday - Thursday";
        }

        if (orderMeals.isEmpty()) {
            invalid += "\nNo Meals Created";
            allGood = false;
        }

        int familySize = 0;
        for (Meal orderMeal : orderMeals) {
            familySize += orderMeal.getQuantity();
        }

        Client client = new Client(null, clientname, clientSurname, clientContactNumber,
                clientAlternativeNumber, clientEmail, clientSuburb, clientAddress, clientAdditionalInfo);

        Order order = new Order(null, true, client, timeFrame, orderDate, null, routeID, orderMeals, familySize);

        if (allGood) {
            if (online) {
                clientID = "";
                Firebase ref = DBClass.getInstance().child("META-Data");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot ds) {
                        clientID = ds.child("ClientID").getValue(Integer.class) + "";
                        String orderID = ds.child("OrderID").getValue(Integer.class) + "";
                        client.setID(clientID);
                        order.setID(orderID);

                        addToMetaData("OrderID", Integer.parseInt(orderID) + 1);
                        addToMetaData("ClientID", Integer.parseInt(clientID) + 1);
                        addOrderToFirebase(order, true);
                    }

                    @Override
                    public void onCancelled(FirebaseError fe) {
                        JOptionPane.showMessageDialog(null, "ERROR: " + fe, "Database Err", JOptionPane.ERROR_MESSAGE);
                    }
                });
            } else {
                client.setID(clientID);
                order.setID(clientID);
                clientID = "" + (Integer.parseInt(clientID) + 1);
                writeOrdersToFile(order);
                btnSave.setEnabled(true);

                if (connection) {
                    JOptionPane.showMessageDialog(null, "Connection to the database has been made. Your orders will now be entered to the database automatically. Opening Main Screen.", "Connection", JOptionPane.OK_OPTION);
                    DSC_Main main = new DSC_Main();
                    main.setVisible(true);
                    main.writeOfflineOrdersToFirebase();
                    this.dispose();
                } else {
                    resetFields();
                }
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please fill in the following fields: " + invalid, "Warning", JOptionPane.WARNING_MESSAGE);
            btnSave.setEnabled(true);
        }

    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        this.dispose();
        new DSC_Main().setVisible(true);
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnDeleteMealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteMealActionPerformed

        int selectedIndex = tblOrderMeals.getSelectedRow();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "Please select a meal to edit.");
        } else {
            orderMeals.remove(selectedIndex);
            int total = 0;
            for (Meal orderMeal : orderMeals) {
                total += orderMeal.getQuantity();
            }
            txfMealsTotal.setText(total + "");
            refreshTable();
        }

    }//GEN-LAST:event_btnDeleteMealActionPerformed

    private void btnEditMealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditMealActionPerformed

        int selectedIndex = tblOrderMeals.getSelectedRow();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(null, "Please select a meal to edit.");
        } else {
            Meal meal = new Meal(
                    (int) tblOrderMeals.getValueAt(selectedIndex, 0),
                    tblOrderMeals.getValueAt(selectedIndex, 1).toString(),
                    tblOrderMeals.getValueAt(selectedIndex, 2).toString(),
                    tblOrderMeals.getValueAt(selectedIndex, 3).toString()
            );
            DSC_PlaceOrder_Mealpane pane = new DSC_PlaceOrder_Mealpane(meal, selectedIndex);
            pane.setBack(this);
            pane.setVisible(true);
            pane.setFocusableWindowState(true);
            this.setEnabled(false);
        }

    }//GEN-LAST:event_btnEditMealActionPerformed

    private void btnAddMealActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddMealActionPerformed

        DSC_PlaceOrder_Mealpane pane = new DSC_PlaceOrder_Mealpane();
        pane.setBack(this);
        pane.setVisible(true);
        pane.setFocusableWindowState(true);
        this.setEnabled(false);

    }//GEN-LAST:event_btnAddMealActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddMeal;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDeleteMeal;
    private javax.swing.JButton btnEditMeal;
    private javax.swing.JButton btnSave;
    private javax.swing.JCheckBox ckbClientCollection;
    private javax.swing.JComboBox<String> cmbClientSuburb;
    private javax.swing.JComboBox<String> cmbOrderDate;
    private javax.swing.JComboBox<String> cmbSurveyReason;
    private javax.swing.JComboBox<String> cmbSurveySource;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lblClientAddInfo;
    private javax.swing.JLabel lblClientAddress;
    private javax.swing.JLabel lblClientAlternativeNumber;
    private javax.swing.JLabel lblClientCollection;
    private javax.swing.JLabel lblClientContactNumber;
    private javax.swing.JLabel lblClientEmail;
    private javax.swing.JLabel lblClientInfo;
    private javax.swing.JLabel lblClientName;
    private javax.swing.JLabel lblClientSuburb;
    private javax.swing.JLabel lblClientSurname;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblOrderInfo;
    private javax.swing.JLabel lblOrderInfo1;
    private javax.swing.JLabel lblSurveyComments;
    private javax.swing.JLabel lblSurveyInfo;
    private javax.swing.JLabel lblSurveyReason;
    private javax.swing.JLabel lblSurveySource;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlClientInfo;
    private javax.swing.JPanel pnlHeading;
    private javax.swing.JPanel pnlMealInfo;
    private javax.swing.JPanel pnlOrderInfo;
    private javax.swing.JPanel pnlSurveyInfo;
    private javax.swing.ButtonGroup rbgTimeFrames;
    private javax.swing.ButtonGroup rbgTimeSlots;
    private javax.swing.JRadioButton rbtAfternoon;
    private javax.swing.JRadioButton rbtEvening;
    private javax.swing.JRadioButton rbtLateAfternoon;
    private javax.swing.JRadioButton rbtMonToFri;
    private javax.swing.JRadioButton rbtMonToThur;
    private javax.swing.JTable tblOrderMeals;
    private javax.swing.JTextArea txaClientDeliveryAddress;
    private javax.swing.JTextArea txaSurveyComments;
    private javax.swing.JTextField txfClientAdditionalInfo;
    private javax.swing.JTextField txfClientAlternativeNumber;
    private javax.swing.JTextField txfClientContactNumber;
    private javax.swing.JTextField txfClientEmail;
    private javax.swing.JTextField txfClientName;
    private javax.swing.JTextField txfClientSurname;
    private javax.swing.JTextField txfMealsTotal;
    // End of variables declaration//GEN-END:variables

    protected void refreshTable() {
        Object[][] mealsArr = new Object[orderMeals.size()][4];
        for (int i = 0; i < orderMeals.size(); i++) {
            mealsArr[i] = orderMeals.get(i).returnObj();
        }

        tblOrderMeals.setModel(new DefaultTableModel(
                mealsArr,
                new String[]{"Quantity", "MealType", "Allergy", "Exclusions"}
        ));
    }

    protected void addMealToList(Meal meal) {
        orderMeals.add(meal);
        int total = 0;
        for (Meal orderMeal : orderMeals) {
            total += orderMeal.getQuantity();
        }
        txfMealsTotal.setText(total + "");
    }

    protected void replaceMealOnList(Meal meal, int index) {
        orderMeals.set(index, meal);
        int total = 0;
        for (Meal orderMeal : orderMeals) {
            total += orderMeal.getQuantity();
        }
        txfMealsTotal.setText(total + "");
    }

    private void getSuburbs() {

        Firebase ref = DBClass.getInstance().child("Routes");
        ref.orderByChild("Active").equalTo(true).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                for (DataSnapshot dataSnapshot : ds.getChildren()) {
                    String subArr[] = dataSnapshot.child("Suburbs").getValue(String[].class);
                    
                    Route route = new Route();
                    route.setID(dataSnapshot.getKey());
                    route.setTimeFrame(dataSnapshot.child("TimeFrame").getValue(String.class));
                    for (String string : subArr) {
                        route.addSuburb(string);
                    }
                    routes.add(route);

                    if (subArr[0].equals("Collection")) {
                        continue;
                    }

                    for (String string : subArr) {
                        if (string == null) {
                            continue;
                        }
                        boolean found = false;
                        for (SuburbData suburbData : subList) {
                            if (suburbData.suburb.equals(string)) {
                                if (dataSnapshot.child("TimeFrame").getValue(String.class).equals("Afternoon")) {
                                    suburbData.afternoon = true;
                                } else if (dataSnapshot.child("TimeFrame").getValue(String.class).equals("Late Afternoon")) {
                                    suburbData.lateAfternoon = true;
                                } else {
                                    suburbData.evening = true;
                                }
                                found = true;
                            }
                        }
                        if (!found) {
                            subList.add(new SuburbData(string, dataSnapshot.child("TimeFrame").getValue(String.class)));
                        }
                    }
                }

                String[] subArr = new String[subList.size()];
                for (int i = 0; i < subArr.length; i++) {
                    subArr[i] = subList.get(i).suburb;
                }

                try {
                    java.util.Arrays.sort(subArr);
                } catch (NullPointerException e) {
                    System.out.println("Warning: Arrays (null)");
                }
                cmbClientSuburb.setModel(new DefaultComboBoxModel<>(subArr));
                changeTimeSlots();
                writeSuburbsToText();
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                JOptionPane.showMessageDialog(null, "Could not fetch suburbs.\nCheck logs for error report.", "Suburb Error", JOptionPane.ERROR_MESSAGE);
                System.err.print("Database connection error (Suburb): " + fe);
            }
        });
    }

    private void getSuburbsFromText() {
        String path = "Suburb.ser";
        try {
            ObjectInputStream subRead = new ObjectInputStream(new java.io.FileInputStream(path));
            routes = (ArrayList<Route>) subRead.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            System.err.println("Could not get data from '" + path + "'.");
            ex.printStackTrace();
        }

        for (Route route : routes) {
            for (String suburb : route.getSuburbs()) {
                System.out.println(suburb);
                boolean found = false;
                for (SuburbData suburbData : subList) {
                    if (suburbData.suburb.equals(suburb)) {
                        if (route.getTimeFrame().equals("Afternoon")) {
                            suburbData.afternoon = true;
                        } else if (route.getTimeFrame().equals("Late Afternoon")) {
                            suburbData.afternoon = true;
                        } else if (route.getTimeFrame().equals("Evening")) {
                            suburbData.afternoon = true;
                        }
                        found = true;
                    }
                }
                if (!found && !suburb.equals("Collection")) {
                    subList.add(new SuburbData(suburb, route.getTimeFrame()));
                }
            }
        }
        String[] subArr = new String[subList.size()];

        for (int i = 0; i < subArr.length; i++) {
            subArr[i] = subList.get(i).suburb;
        }

        try {
            java.util.Arrays.sort(subArr);
        } catch (NullPointerException e) {
            System.out.println("Warning: Arrays (null)");
        }
        cmbClientSuburb.setModel(new DefaultComboBoxModel<>(subArr));
        changeTimeSlots();
    }

    private void writeSuburbsToText() {
        try {
            ObjectOutputStream subOut = new ObjectOutputStream(new java.io.FileOutputStream("Suburb.ser"));
            subOut.writeObject(routes);
            System.out.println("Suburbs Successfully Written.");
        } catch (IOException ex) {
            System.err.println("Error: Could not write to existing file. (" + ex.toString() + ")");
            System.err.println("Creating new Serialized File.");
        }
    }

    private void writeOrdersToFile(Order order) {
        String path = "Offline Orders.ser";
        ArrayList<Order> offOrders = new ArrayList<>();
        try {
            ObjectInputStream ordersIn = new ObjectInputStream(new java.io.FileInputStream(path));
            offOrders = (ArrayList<Order>) ordersIn.readObject();
        } catch (java.io.FileNotFoundException e) {
            try {
                java.io.File file = new java.io.File(path);
                file.createNewFile();
            } catch (IOException ex) {
                System.err.print("Error Writing'" + path + "' (FileNotFound): " + ex.getLocalizedMessage());
            }
        } catch (ClassNotFoundException | IOException io) {
            System.err.print("Error Writing'" + path + "' (IOException) : " + io.getLocalizedMessage());
        }
        offOrders.add(order);

        try {
            ObjectOutputStream ordersOut = new ObjectOutputStream(new java.io.FileOutputStream(path));
            ordersOut.writeObject(offOrders);
            System.out.println("File Order's successfully topped up.");
            JOptionPane.showMessageDialog(null, "Order Successfully added.", "Success", JOptionPane.PLAIN_MESSAGE);
        } catch (IOException e) {
            System.err.print("Error Writing '" + path + "' (IOException) : " + e.getLocalizedMessage());
            e.printStackTrace();
        }

    }

    private void changeTimeSlots() {
        String selectedSuburb = cmbClientSuburb.getSelectedItem().toString();

        rbtAfternoon.setEnabled(false);
        rbtAfternoon.setSelected(true);
        rbtEvening.setEnabled(false);
        rbtLateAfternoon.setEnabled(false);

        for (SuburbData suburbData : subList) {
            if (suburbData.suburb.equals(selectedSuburb)) {
                if (suburbData.evening) {
                    rbtEvening.setEnabled(true);
                    rbtEvening.setSelected(true);
                }
                if (suburbData.lateAfternoon) {
                    rbtLateAfternoon.setEnabled(true);
                    rbtLateAfternoon.setSelected(true);
                }
                if (suburbData.afternoon) {
                    rbtAfternoon.setEnabled(true);
                    rbtAfternoon.setSelected(true);
                }
            }
        }
    }

    private void getDates() {
        Calendar currentDate = Calendar.getInstance();
        int counter = 0;
        String[] weeks = new String[5];
        weeks[4] = "*Current Week";
        while (counter < 4) {
            for (int i = 0; i < 7; i++) {
                if (currentDate.get(Calendar.DAY_OF_WEEK) != 2) {
                    currentDate.add(Calendar.DAY_OF_WEEK, 1);
                }
            }
            orderDates[counter] = (Calendar) currentDate.clone();
            java.text.DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
            weeks[counter] = df.format(currentDate.getTime());
            currentDate.add(Calendar.DAY_OF_WEEK, 1);
            counter++;
        }

        cmbOrderDate.setModel(new DefaultComboBoxModel<>(weeks));
    }

    protected void addToMetaData(String ids, int value) {
        Firebase ref = DBClass.getInstance().child("META-Data");
        ref.child(ids).setValue(value);
    }

    protected void addOrderToFirebase(Order order, boolean orderPane) {

        Firebase ref = DBClass.getInstance().child("Clients");

        ClientContainer client = new ClientContainer(
                order.getClient().getAdditionalInfo(),
                order.getClient().getAddress(),
                order.getClient().getAlternativeNumber(),
                order.getClient().getContactNumber(),
                order.getClient().getEmail(),
                order.getClient().getName(),
                order.getClient().getSuburb(),
                order.getClient().getSurname()
        );

        ref.child(order.getClient().getID()).setValue(client);

        ref = DBClass.getInstance().child("Orders");

        MealContainer meals[] = new MealContainer[order.getMeals().size()];
        int totalMeals = 0;
        for (int i = 0; i < order.getMeals().size(); i++) {
            meals[i] = new MealContainer(
                    order.getMeals().get(i).getAllergies(),
                    order.getMeals().get(i).getExclusions(),
                    order.getMeals().get(i).getMealType(),
                    order.getMeals().get(i).getQuantity()
            );
            totalMeals += order.getMeals().get(i).getQuantity();
        }

        OrderContainer orderContainer = new OrderContainer(
                true,
                order.getClient().getID(),
                order.getDuration(),
                "-",
                totalMeals,
                order.getRoute(),
                order.getStartingDate().getTimeInMillis(),
                meals
        );

        ref.child(order.getID()).setValue(orderContainer);

        if (cmbSurveyReason.getSelectedIndex() != 0 && cmbSurveySource.getSelectedIndex() != 0 && online) {
            writeSurveyInfo();
        }
        if (orderPane) {
            JOptionPane.showMessageDialog(null, "Order Successfully placed!", "Success", JOptionPane.PLAIN_MESSAGE);
            btnBack.doClick();
        }
    }

    private void resetFields() {
        txfClientName.setText("");
        txfClientSurname.setText("");
        txfClientContactNumber.setText("");
        txfClientAlternativeNumber.setText("");
        txfClientEmail.setText("");
        ckbClientCollection.setSelected(false);
        cmbClientSuburb.setSelectedIndex(0);
        txaClientDeliveryAddress.setText("");
        txfClientAdditionalInfo.setText("");
        cmbSurveyReason.setSelectedIndex(0);
        cmbSurveySource.setSelectedIndex(0);
        txaSurveyComments.setText("");
        cmbOrderDate.setSelectedIndex(0);
        rbtMonToFri.setSelected(true);
        orderMeals = new ArrayList<>();
        refreshTable();
    }

    private void retryConnection() {
        Firebase ref = DBClass.getInstance();
        ref.child("META-Data/Connected").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                connection = true;
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.println("Error Trying To connect: " + fe.getMessage());
            }
        });
    }

    private void writeSurveyInfo() {
        String reason = cmbSurveyReason.getSelectedItem().toString();
        String source = cmbSurveySource.getSelectedItem().toString();
        String comments = txaSurveyComments.getText();
        SurveyContainer sc = new SurveyContainer(reason, source, comments, clientID);

        Firebase ref = DBClass.getInstance();
        ref.child("META-Data/SurveyID").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                int id = ds.getValue(Integer.class);
                ref.child("META-Data/SurveyID").setValue((id + 1));
                ref.child("Survey/Entries/" + id).setValue(sc);
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.println("Unable to connect to database" + fe.getDetails());
            }
        });

    }

    private void getSurveyQuestions(boolean online) {
        if (online) {
            Firebase ref = DBClass.getInstance().child("Survey/Questions");
            ref.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot ds) {
                    String surveyReasons[] = new String[(int) ds.child("Reason").getChildrenCount() + 1];
                    String surveySources[] = new String[(int) ds.child("Source").getChildrenCount() + 1];

                    surveyReasons[0] = "Reasons For Choosing";
                    int counter = 1;
                    for (DataSnapshot dataSnapshot : ds.child("Reason").getChildren()) {
                        surveyReasons[counter] = dataSnapshot.getValue(String.class);
                        counter++;
                    }

                    counter = 1;
                    surveySources[0] = "Source of Refrence";
                    for (DataSnapshot dataSnapshot : ds.child("Source").getChildren()) {
                        surveySources[counter] = dataSnapshot.getValue(String.class);
                        counter++;
                    }

                    cmbSurveyReason.setModel(new DefaultComboBoxModel<>(surveyReasons));
                    cmbSurveySource.setModel(new DefaultComboBoxModel<>(surveySources));

                }

                @Override
                public void onCancelled(FirebaseError fe) {
                    System.err.println("Unable to connect to database" + fe.getDetails());
                }
            });
        }
    }

//---------------------------------------------------------------------------------------------------------------------------AnonymousClasses        
    private class SurveyContainer implements java.io.Serializable {

        public String Reason;
        public String Source;
        public String Comments;
        public String ClientID;

        public SurveyContainer(String Reason, String Source, String comments, String ClientID) {
            this.Reason = Reason;
            this.Source = Source;
            this.Comments = comments;
            this.ClientID = ClientID;
        }

        @Override
        public String toString() {
            return "SurveyContainer{" + "Reason=" + Reason + ", Source=" + Source + ", Comments=" + Comments + ", ClientID=" + ClientID + '}';
        }
    }

    private class ClientContainer {

        public String AdditionalInfo;
        public String Address;
        public String AlternativeNumber;
        public String ContactNum;
        public String Email;
        public String Name;
        public String Suburb;
        public String Surname;

        public ClientContainer(String AdditionalInfo, String Address, String AlternativeNumber, String ContactNum, String Email, String Name, String Suburb, String Surname) {
            this.AdditionalInfo = AdditionalInfo;
            this.Address = Address;
            this.AlternativeNumber = AlternativeNumber;
            this.ContactNum = ContactNum;
            this.Email = Email;
            this.Name = Name;
            this.Suburb = Suburb;
            this.Surname = Surname;
        }

    }

    private class OrderContainer {

        public boolean Active;
        public String ClientID;
        public String Duration;
        public String EndDate;
        public int FamilySize;
        public String RouteID;
        public long StartingDate;
        public MealContainer[] Meals;

        public OrderContainer(boolean Active, String ClientID, String Duration, String EndDate, int FamilySize, String RouteID, long StartingDate, MealContainer[] meals) {
            this.Active = Active;
            this.ClientID = ClientID;
            this.Duration = Duration;
            this.EndDate = EndDate;
            this.FamilySize = FamilySize;
            this.RouteID = RouteID;
            this.StartingDate = StartingDate;
            this.Meals = meals;
        }

    }

    private class MealContainer {

        public String Allergies;
        public String Exclusions;
        public String MealType;
        public int Quantity;

        public MealContainer(String Allergies, String Exclusions, String MealType, int Quantity) {
            this.Allergies = Allergies;
            this.Exclusions = Exclusions;
            this.MealType = MealType;
            this.Quantity = Quantity;
        }

    }

    private class SuburbData implements java.io.Serializable {

        public String suburb = "";
        public boolean evening = false, afternoon = false, lateAfternoon = false;

        public SuburbData(String suburb, String time) {
            this.suburb = suburb;
            this.evening = false;
            this.afternoon = false;
            this.lateAfternoon = false;
            if (time.equals("Evening")) {
                evening = true;
            } else if (time.equals("Afternoon")) {
                afternoon = true;
            } else {
                lateAfternoon = true;
            }

        }
    }
}
