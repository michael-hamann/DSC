package DSC;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import javax.swing.JOptionPane;

/**
 * @author Michael Hamann
 */
public class DSC_NewDriver extends javax.swing.JFrame {
    
    private String id = getNewDriverID();
    private DSC_DriverDetails main;

    /**
     * Creates new form DSC_NewDriver
     */
    public DSC_NewDriver(DSC_DriverDetails main) {
        this.main = main;
        initComponents();
    }

    /**
     * Checks if text fields are empty
     */
    private boolean checkEmpty() {
        boolean empty = false;

        if (txfName.getText().trim().isEmpty() && txfContactNum.getText().trim().isEmpty()
                && txfAddress.getText().trim().isEmpty() && txfVehicleReg.getText().trim().isEmpty()) {
            empty = true;
        }

        return empty;
    }
    
    private boolean validateFields(){
        boolean valid = false;
        
        if (txfContactNum.getText().length() == 10) {
            valid = true;
        }
        
        return valid;
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
        pnlNewDriver = new javax.swing.JPanel();
        lblNewDriver = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        lblContactNumber = new javax.swing.JLabel();
        lblAddress = new javax.swing.JLabel();
        lblVehicleReg = new javax.swing.JLabel();
        txfName = new javax.swing.JTextField();
        txfContactNum = new javax.swing.JTextField();
        txfAddress = new javax.swing.JTextField();
        txfVehicleReg = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Add New Driver");

        pnlBackground.setBackground(new java.awt.Color(0, 153, 0));

        pnlNewDriver.setBackground(new java.awt.Color(0, 204, 51));
        pnlNewDriver.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblNewDriver.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lblNewDriver.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblNewDriver.setText("Add New Driver");

        lblName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblName.setText("Name: ");

        lblContactNumber.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblContactNumber.setText("Contact Number:");

        lblAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAddress.setText("Address: ");

        lblVehicleReg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblVehicleReg.setText("Vehicle Registration:");

        txfName.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txfContactNum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txfAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txfVehicleReg.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        btnCancel.setBackground(new java.awt.Color(255, 0, 0));
        btnCancel.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnCancel.setForeground(new java.awt.Color(255, 255, 255));
        btnCancel.setText("Cancel");
        btnCancel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
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

        javax.swing.GroupLayout pnlNewDriverLayout = new javax.swing.GroupLayout(pnlNewDriver);
        pnlNewDriver.setLayout(pnlNewDriverLayout);
        pnlNewDriverLayout.setHorizontalGroup(
            pnlNewDriverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewDriverLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlNewDriverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblNewDriver, javax.swing.GroupLayout.DEFAULT_SIZE, 456, Short.MAX_VALUE)
                    .addGroup(pnlNewDriverLayout.createSequentialGroup()
                        .addGroup(pnlNewDriverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblVehicleReg, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE)
                            .addComponent(lblAddress, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblContactNumber, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblName, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(pnlNewDriverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txfName)
                            .addComponent(txfContactNum)
                            .addComponent(txfAddress, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txfVehicleReg, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlNewDriverLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnSave)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCancel)))
                .addContainerGap())
        );
        pnlNewDriverLayout.setVerticalGroup(
            pnlNewDriverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlNewDriverLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblNewDriver)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlNewDriverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblName)
                    .addComponent(txfName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlNewDriverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblContactNumber)
                    .addComponent(txfContactNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlNewDriverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblAddress)
                    .addComponent(txfAddress, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlNewDriverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblVehicleReg)
                    .addComponent(txfVehicleReg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pnlNewDriverLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnCancel)
                    .addComponent(btnSave))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlNewDriver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlNewDriver, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
        if (checkEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            if (validateFields()) {
                //Save new driver
                Firebase ref = DBClass.getInstance().child("Drivers");
                ref.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot ds) {
                        String name = txfName.getText().trim();
                        String contactNum = txfContactNum.getText().trim();
                        String address = txfAddress.getText().trim();
                        String vehicleReg = txfVehicleReg.getText().trim();
                        
                        Driver driver = new Driver(address, contactNum, name, vehicleReg);
                        addToFirebase(driver);
                        String currID = getNewDriverID();
                        addToMetaData("DriverID", Integer.parseInt(currID) + 1);
                    }
                    
                    @Override
                    public void onCancelled(FirebaseError fe) {
                        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                    }
                });
                JOptionPane.showMessageDialog(this, "New Driver Saved", "Saved", JOptionPane.INFORMATION_MESSAGE);
                main.setDrivers();
                this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Please enter a valid contact number", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnSaveActionPerformed

    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        int ans = JOptionPane.showConfirmDialog(this, "Are you sure you wish to discard new driver?");
        if (ans == JOptionPane.YES_OPTION) {
            this.dispose();
        }
    }//GEN-LAST:event_btnCancelActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnSave;
    private javax.swing.JLabel lblAddress;
    private javax.swing.JLabel lblContactNumber;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblNewDriver;
    private javax.swing.JLabel lblVehicleReg;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlNewDriver;
    private javax.swing.JTextField txfAddress;
    private javax.swing.JTextField txfContactNum;
    private javax.swing.JTextField txfName;
    private javax.swing.JTextField txfVehicleReg;
    // End of variables declaration//GEN-END:variables

    private class DriverContainer {

        public String Address;
        public String ContactNumber;
        public String DriverName;
        public String VehicleReg;

        public DriverContainer(String Address, String ContactNumber, String DriverName, String VehicleReg){
            this.Address = Address;
            this.ContactNumber = ContactNumber;
            this.DriverName = DriverName;
            this.VehicleReg = VehicleReg;
        }
    }
    
    protected void addToMetaData(String ids, int value) {
        Firebase ref = DBClass.getInstance().child("META-Data");
        ref.child(ids).setValue(value);
    }
    
    protected String getNewDriverID(){
        Firebase ref = DBClass.getInstance().child("META-Data");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                id = ds.child("DriverID").getValue(Integer.class) + "";
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        return id;
    }
    
    protected void addToFirebase(Driver d){
        String newID = getNewDriverID();
        Firebase ref = DBClass.getInstance().child("Drivers");
        DriverContainer driver = new DriverContainer(
                d.isAddress(), 
                d.getContactNumber(), 
                d.getDriverName(), 
                d.getVehicleRegistration()
        );
        ref.child(newID).setValue(driver);
    }
}
