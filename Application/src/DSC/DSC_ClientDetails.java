
package DSC;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author Aliens_Michael
 */
public class DSC_ClientDetails extends javax.swing.JFrame {

    boolean editClicked = false;
    int listIndex = 0;

    /**
     * Creates new form DSC_DriverDetails
     */
    public DSC_ClientDetails() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        disableFields();
        btnSaveClient.setText("Save");
        btnSaveClient.setVisible(false);
        txfClientID.setEnabled(false);
        txfSuburb.setEnabled(false);
        lstClient.setSelectedIndex(0);
    }

    public final void enableFields() {
        txfClientName.setEnabled(true);
        txfClientSurname.setEnabled(true);
        txfClientContactNo.setEnabled(true);
        txfClientAddress.setEnabled(true);
        txfAddInfo.setEnabled(true);
        txfClientEmail.setEnabled(true);
        txfAltNum.setEnabled(true);
        txfSuburbID.setEnabled(true);

    }

    public final void disableFields() {
        txfClientName.setEnabled(false);
        txfClientSurname.setEnabled(false);
        txfClientContactNo.setEnabled(false);
        txfClientAddress.setEnabled(false);
        txfAddInfo.setEnabled(false);
        txfClientEmail.setEnabled(false);
        txfAltNum.setEnabled(false);
        txfSuburbID.setEnabled(false);

    }

    public final void clearFields() {
        txfClientID.setText(null);
        txfClientName.setText(null);
        txfClientSurname.setText(null);
        txfClientContactNo.setText(null);
        txfClientAddress.setText(null);
        txfAddInfo.setText(null);
        txfClientEmail.setText(null);
        txfAltNum.setText(null);
        txfSuburbID.setText(null);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pnlBackgroundClient = new javax.swing.JPanel();
        pnlClients = new javax.swing.JPanel();
        lblClients = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstClient = new javax.swing.JList<>();
        btnAddClient = new javax.swing.JButton();
        btnDeleteClient = new javax.swing.JButton();
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
        btnBack = new javax.swing.JButton();
        btnEditClient = new javax.swing.JButton();
        btnSaveClient = new javax.swing.JButton();
        lblAltNum = new javax.swing.JLabel();
        lblEmail = new javax.swing.JLabel();
        txfClientEmail = new javax.swing.JTextField();
        lblSuburb = new javax.swing.JLabel();
        txfSuburbID = new javax.swing.JTextField();
        btnView = new javax.swing.JButton();
        txfSuburb = new javax.swing.JTextField();
        txfAltNum = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Driver Details");

        pnlBackgroundClient.setBackground(new java.awt.Color(0, 153, 0));

        pnlClients.setBackground(new java.awt.Color(0, 204, 51));
        pnlClients.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblClients.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblClients.setText("Clients:");

        lstClient.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lstClient.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(lstClient);

        btnAddClient.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnAddClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Add.png"))); // NOI18N
        btnAddClient.setText(" Add");
        btnAddClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddClientActionPerformed(evt);
            }
        });

        btnDeleteClient.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnDeleteClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Bin.png"))); // NOI18N
        btnDeleteClient.setText("Delete");
        btnDeleteClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteClientActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlClientsLayout = new javax.swing.GroupLayout(pnlClients);
        pnlClients.setLayout(pnlClientsLayout);
        pnlClientsLayout.setHorizontalGroup(
            pnlClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClientsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(pnlClientsLayout.createSequentialGroup()
                        .addComponent(lblClients, javax.swing.GroupLayout.PREFERRED_SIZE, 118, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAddClient)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteClient)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pnlClientsLayout.setVerticalGroup(
            pnlClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlClientsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlClientsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblClients, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAddClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteClient))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
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
        txfClientContactNo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfClientContactNoActionPerformed(evt);
            }
        });

        txfClientAddress.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfClientAddress.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        txfAddInfo.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfAddInfo.setHorizontalAlignment(javax.swing.JTextField.TRAILING);

        btnBack.setBackground(new java.awt.Color(255, 0, 0));
        btnBack.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        btnBack.setForeground(new java.awt.Color(255, 255, 255));
        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnEditClient.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEditClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Edit 2.png"))); // NOI18N
        btnEditClient.setText(" Edit");
        btnEditClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEditClientActionPerformed(evt);
            }
        });

        btnSaveClient.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnSaveClient.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Save 2.png"))); // NOI18N
        btnSaveClient.setText(" Save");
        btnSaveClient.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveClientActionPerformed(evt);
            }
        });

        lblAltNum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblAltNum.setText("Alternative Number:");

        lblEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblEmail.setText("Email:");

        txfClientEmail.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfClientEmail.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfClientEmail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfClientEmailActionPerformed(evt);
            }
        });

        lblSuburb.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        lblSuburb.setText("Suburb:");

        txfSuburbID.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfSuburbID.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfSuburbID.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfSuburbIDActionPerformed(evt);
            }
        });

        btnView.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnView.setText("View Suburb");
        btnView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewActionPerformed(evt);
            }
        });

        txfSuburb.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N

        txfAltNum.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        txfAltNum.setHorizontalAlignment(javax.swing.JTextField.TRAILING);
        txfAltNum.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txfAltNumActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlDetailsClientLayout = new javax.swing.GroupLayout(pnlDetailsClient);
        pnlDetailsClient.setLayout(pnlDetailsClientLayout);
        pnlDetailsClientLayout.setHorizontalGroup(
            pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailsClientLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblClientDetails, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlDetailsClientLayout.createSequentialGroup()
                        .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(pnlDetailsClientLayout.createSequentialGroup()
                                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblEmail, javax.swing.GroupLayout.DEFAULT_SIZE, 111, Short.MAX_VALUE)
                                    .addComponent(lblSuburb, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(57, 57, 57)
                                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txfClientEmail)
                                    .addComponent(txfSuburbID)
                                    .addGroup(pnlDetailsClientLayout.createSequentialGroup()
                                        .addComponent(txfSuburb)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnView, javax.swing.GroupLayout.PREFERRED_SIZE, 1, Short.MAX_VALUE))))
                            .addGroup(pnlDetailsClientLayout.createSequentialGroup()
                                .addComponent(btnEditClient)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 74, Short.MAX_VALUE)
                                .addComponent(btnSaveClient)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(btnBack)))
                        .addGap(4, 4, 4))
                    .addGroup(pnlDetailsClientLayout.createSequentialGroup()
                        .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(lblAltNum, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblVehicleReg, javax.swing.GroupLayout.DEFAULT_SIZE, 164, Short.MAX_VALUE)
                                    .addComponent(lblClientSurname, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblClientName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lblClientID, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(lblClientAddress)
                                .addComponent(lblClientContactNo, javax.swing.GroupLayout.PREFERRED_SIZE, 111, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pnlDetailsClientLayout.createSequentialGroup()
                                .addComponent(txfAltNum)
                                .addGap(5, 5, 5))
                            .addComponent(txfClientContactNo)
                            .addComponent(txfClientID)
                            .addComponent(txfClientName, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txfClientSurname, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txfAddInfo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txfClientAddress))))
                .addContainerGap())
        );
        pnlDetailsClientLayout.setVerticalGroup(
            pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlDetailsClientLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblClientDetails, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
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
                    .addComponent(txfSuburbID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txfSuburb, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnView))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                .addGroup(pnlDetailsClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEditClient)
                    .addComponent(btnSaveClient)
                    .addComponent(btnBack))
                .addContainerGap())
        );

        javax.swing.GroupLayout pnlBackgroundClientLayout = new javax.swing.GroupLayout(pnlBackgroundClient);
        pnlBackgroundClient.setLayout(pnlBackgroundClientLayout);
        pnlBackgroundClientLayout.setHorizontalGroup(
            pnlBackgroundClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundClientLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlClients, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(pnlDetailsClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        pnlBackgroundClientLayout.setVerticalGroup(
            pnlBackgroundClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundClientLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlBackgroundClientLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlClients, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlDetailsClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackgroundClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackgroundClient, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private boolean checkEmpty() {
        boolean empty = false;

        if (txfClientName.getText().isEmpty() && txfClientSurname.getText().isEmpty() && txfClientContactNo.getText().isEmpty()
                && txfClientAddress.getText().isEmpty() && txfAddInfo.getText().isEmpty()
                && txfClientContactNo.getText().isEmpty() && txfAltNum.getText().isEmpty() && txfClientEmail.getText().isEmpty()
                && txfSuburbID.getText().isEmpty()) {
            empty = true;
        }

        return empty;
    }

    private void btnAddClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddClientActionPerformed
        clearFields();
        enableFields();
        btnEditClient.setEnabled(false);
        btnSaveClient.setText("Add");
        btnSaveClient.setVisible(true);
        editClicked = true;

        String query = "SELECT MAX(ClientID) FROM doorstepchef.client_tb;";
        ResultSet rs;
        int numRows = 0;

        try {
          //  Connection c = DBClass.getConnection();
        //    Statement stmt = c.createStatement();
         //   rs = stmt.executeQuery(query);
         //   rs.next();
         //   numRows = rs.getInt(1);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
        numRows += 1;
        txfClientID.setText(numRows + "");

    }//GEN-LAST:event_btnAddClientActionPerformed

    private void btnDeleteClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteClientActionPerformed
        String name = txfClientName.getText() + " " + txfClientSurname.getText();

        int clientID = Integer.parseInt(txfClientID.getText());

        String message = "Are you sure you want to delete " + name + "?";
        int answer = JOptionPane.showConfirmDialog(this, message, "Confirm", JOptionPane.INFORMATION_MESSAGE);

        switch (answer) {
            case JOptionPane.YES_OPTION:
                JOptionPane.showMessageDialog(this, name + " will be deleted", "Delete Notification", JOptionPane.INFORMATION_MESSAGE);

                try {
                   // Connection c = DBClass.getConnection();
                 //   Statement stmt = c.createStatement();

//                    String updateOrderID = "UPDATE order_tb SET Client_ID =0 WHERE Client_ID = '" + clientID + "'";
//                    stmt.executeUpdate(updateOrderID);
//
//                    String updateSuburbID = "UPDATE client_tb SET SuburbID = 0 WHERE ClientID = '" + clientID + "'";
//                    stmt.executeUpdate(updateSuburbID);
//
//                    String deleteClient = "DELETE FROM doorstepchef.client_tb WHERE ClientID LIKE '" + clientID + "'";
//                    stmt.executeUpdate(deleteClient);
//
//                    String deleteOrders = "DELETE FROM doorstepchef.order_tb WHERE Client_ID LIKE '" + clientID + "'";
//                    stmt.executeUpdate(deleteOrders);

                    JOptionPane.showMessageDialog(this, "Client has been deleted. \n Orders of this client have been removed.");

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
    }//GEN-LAST:event_btnDeleteClientActionPerformed

    private void txfClientEmailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfClientEmailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfClientEmailActionPerformed

    private void btnSaveClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveClientActionPerformed
        boolean back = false;
        if (btnSaveClient.getText().equals("Save")) {

            short ID = Short.parseShort(txfClientID.getText().trim());
            String newName = txfClientName.getText().trim();
            String newSurname = txfClientSurname.getText().trim();
            String newAdditionalInfo = txfAddInfo.getText().trim();
            String newContactNumber = txfClientContactNo.getText().trim();
            String newAlternativeNo = txfAltNum.getText().trim();
            String newAddress = txfClientAddress.getText().trim();
            String newEmail = txfClientEmail.getText().trim();
            String newSuburbID = txfSuburbID.getText().trim();
           
            String query = "SELECT SuburbID FROM suburb_tb ;";
            try {
//                Connection c = DBClass.getConnection();
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
//                JOptionPane.showMessageDialog(this, "Changes Saved");
//                //Refresh
//                back = true;
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }

            back = true;
        } else if (btnSaveClient.getText().equals("Add")) {
            //Add to database
            boolean empty = checkEmpty();
            if (empty) { 
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
                short newSuburbID = Short.parseShort(txfSuburbID.getText().trim());

                String query = "INSERT INTO doorstepchef.client_tb (`ClientID`, `Name`, `Surname`, `Address`,`AdditionalInfo`,"
                        + " `ContactNumber`, `AlternativeNumber`, `Email`,`SuburbID`) \n"
                        + "	VALUES (" + newID + ", '" + newName + "', '" + newSurname + "', '" + newAddress + "', '"
                        + newAddInfo + "', '" + newContactNo + "', '" + newAltNum + "', '" + newEmail + "','" + newSuburbID + "');";

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
        }

        if (back) {
            disableFields();
            btnSaveClient.setVisible(false);
            btnEditClient.setEnabled(true);
            editClicked = false;
        }
    }//GEN-LAST:event_btnSaveClientActionPerformed

    private void btnEditClientActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditClientActionPerformed
        listIndex = lstClient.getSelectedIndex();
        enableFields();
        btnEditClient.setEnabled(false);
        btnSaveClient.setVisible(true);
        editClicked = true;
    }//GEN-LAST:event_btnEditClientActionPerformed

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        if (editClicked) {
            int ans = JOptionPane.showConfirmDialog(this, "Do you wish to discard unsaved changes?");
            switch (ans) {
                case JOptionPane.YES_OPTION:
                    btnSaveClient.setVisible(false);
                    btnEditClient.setEnabled(true);
                    disableFields();
                    lstClient.setSelectedIndex(listIndex);
                    editClicked = false;
                    break;
                case JOptionPane.NO_OPTION:
                    break;
                default:
                    break;
            }
        } else {
            this.dispose();
            new DSC_ClientTable().setVisible(true);
        }
    }//GEN-LAST:event_btnBackActionPerformed

    private void txfClientContactNoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfClientContactNoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfClientContactNoActionPerformed

    private void txfSuburbIDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfSuburbIDActionPerformed

    }//GEN-LAST:event_txfSuburbIDActionPerformed

    private void btnViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewActionPerformed
        
        ResultSet rs;
        String suburbID = txfSuburbID.getText();

        try {
//            Connection c = DBClass.getConnection();
//            Statement stmt = c.createStatement();
//            String findSuburb = "SELECT Suburb FROM suburb_tb WHERE SuburbID = '" + suburbID + "'";
//            rs = stmt.executeQuery(findSuburb);
//            rs.next();
//            String suburb = rs.getString(1);
//            txfSuburb.setText(suburb);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnViewActionPerformed

    private void txfAltNumActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txfAltNumActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txfAltNumActionPerformed

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
            java.util.logging.Logger.getLogger(DSC_ClientDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DSC_ClientDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DSC_ClientDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DSC_ClientDetails.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DSC_ClientDetails().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddClient;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnDeleteClient;
    private javax.swing.JButton btnEditClient;
    private javax.swing.JButton btnSaveClient;
    private javax.swing.JButton btnView;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblAltNum;
    private javax.swing.JLabel lblClientAddress;
    private javax.swing.JLabel lblClientContactNo;
    private javax.swing.JLabel lblClientDetails;
    private javax.swing.JLabel lblClientID;
    private javax.swing.JLabel lblClientName;
    private javax.swing.JLabel lblClientSurname;
    private javax.swing.JLabel lblClients;
    private javax.swing.JLabel lblEmail;
    private javax.swing.JLabel lblSuburb;
    private javax.swing.JLabel lblVehicleReg;
    private javax.swing.JList<String> lstClient;
    private javax.swing.JPanel pnlBackgroundClient;
    private javax.swing.JPanel pnlClients;
    private javax.swing.JPanel pnlDetailsClient;
    private javax.swing.JTextField txfAddInfo;
    private javax.swing.JTextField txfAltNum;
    private javax.swing.JTextField txfClientAddress;
    private javax.swing.JTextField txfClientContactNo;
    private javax.swing.JTextField txfClientEmail;
    private javax.swing.JTextField txfClientID;
    private javax.swing.JTextField txfClientName;
    private javax.swing.JTextField txfClientSurname;
    private javax.swing.JTextField txfSuburb;
    private javax.swing.JTextField txfSuburbID;
    // End of variables declaration//GEN-END:variables
}
