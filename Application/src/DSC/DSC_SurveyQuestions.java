/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DSC;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;

/**
 *
 * @author Aliens_Ross
 */
public class DSC_SurveyQuestions extends javax.swing.JFrame {

    private DSC_Main pane;
    private ArrayList<String> reasonQuestions = new ArrayList<>();
    private ArrayList<String> sourceQuestions = new ArrayList<>();
    private int selectedIndex;

    /**
     * Creates new form DSC_SurveyQuestions
     */
    public DSC_SurveyQuestions() {
        getQuestions();
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        btnBack = new javax.swing.JButton();
        btnSave = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstQuestions = new javax.swing.JList<>();
        cmbQuestionType = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        txaQuestion = new javax.swing.JTextArea();
        btnAdd = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DoorsepChef");
        setAlwaysOnTop(true);
        setResizable(false);

        jPanel5.setBackground(new java.awt.Color(0, 153, 0));

        jPanel6.setBackground(new java.awt.Color(0, 204, 51));
        jPanel6.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jPanel6.setToolTipText("");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel3.setText("Survey Questions");

        btnBack.setText("Back");
        btnBack.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnBackActionPerformed(evt);
            }
        });

        btnSave.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Save 2.png"))); // NOI18N
        btnSave.setText(" Save");
        btnSave.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSaveActionPerformed(evt);
            }
        });

        lstQuestions.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        lstQuestions.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        lstQuestions.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstQuestionsValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstQuestions);

        cmbQuestionType.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Reason For Choosing", "Source of Refrence" }));
        cmbQuestionType.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbQuestionTypeItemStateChanged(evt);
            }
        });

        txaQuestion.setColumns(20);
        txaQuestion.setRows(5);
        jScrollPane2.setViewportView(txaQuestion);

        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/PICS/Add.png"))); // NOI18N
        btnAdd.setText("Add");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(40, 40, 40)
                        .addComponent(cmbQuestionType, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(0, 7, Short.MAX_VALUE)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel3)
                                .addGap(24, 24, 24))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addComponent(btnBack)
                                .addContainerGap())))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(btnAdd)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSave)))
                        .addContainerGap())))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnAdd)
                            .addComponent(btnSave))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                        .addComponent(btnBack))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(cmbQuestionType, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnBackActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnBackActionPerformed
        pane.setEnabled(true);
        this.dispose();
    }//GEN-LAST:event_btnBackActionPerformed

    private void btnSaveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSaveActionPerformed

    }//GEN-LAST:event_btnSaveActionPerformed

    private void cmbQuestionTypeItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbQuestionTypeItemStateChanged
        if (cmbQuestionType.getSelectedIndex() == 0) {
            setList("Reason");
        }else{
            setList("Source");
        }
    }//GEN-LAST:event_cmbQuestionTypeItemStateChanged

    private void lstQuestionsValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstQuestionsValueChanged
        selectedIndex = lstQuestions.getSelectedIndex();
        System.out.println(selectedIndex);
        if (cmbQuestionType.getSelectedIndex() == 0) {
            txaQuestion.setText(reasonQuestions.get(selectedIndex));
        }else{
            txaQuestion.setText(sourceQuestions.get(selectedIndex));
        }
    }//GEN-LAST:event_lstQuestionsValueChanged

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
            java.util.logging.Logger.getLogger(DSC_SurveyQuestions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DSC_SurveyQuestions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DSC_SurveyQuestions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DSC_SurveyQuestions.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DSC_SurveyQuestions().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnBack;
    private javax.swing.JButton btnCancel;
    private javax.swing.JButton btnCancel1;
    private javax.swing.JButton btnSave;
    private javax.swing.JComboBox<String> cmbMealType;
    private javax.swing.JComboBox<String> cmbMealType1;
    private javax.swing.JComboBox<String> cmbQuestionType;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblAllergy;
    private javax.swing.JLabel lblAllergy1;
    private javax.swing.JLabel lblExclusions;
    private javax.swing.JLabel lblExclusions1;
    private javax.swing.JLabel lblMealType;
    private javax.swing.JLabel lblMealType1;
    private javax.swing.JLabel lblQuantity;
    private javax.swing.JLabel lblQuantity1;
    private javax.swing.JList<String> lstQuestions;
    private javax.swing.JSpinner spnQuantity;
    private javax.swing.JSpinner spnQuantity1;
    private javax.swing.JTextArea txaQuestion;
    private javax.swing.JTextField txfAllergy;
    private javax.swing.JTextField txfAllergy1;
    private javax.swing.JTextField txfExclusions;
    private javax.swing.JTextField txfExclusions1;
    // End of variables declaration//GEN-END:variables

    public void setBack(DSC_Main pane) {
        this.pane = pane;
    }

    private void getQuestions() {
        Firebase ref = DBClass.getInstance().child("Survey/Questions");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot ds) {
                DataSnapshot reasonQuestion = ds.child("Reason");
                DataSnapshot sourceQuestion = ds.child("Source");
                for (DataSnapshot dataSnapshot : reasonQuestion.getChildren()) {
                    reasonQuestions.add(dataSnapshot.getValue(String.class));
                }
                for (DataSnapshot dataSnapshot : sourceQuestion.getChildren()) {
                    sourceQuestions.add(dataSnapshot.getValue(String.class));
                }
                
                setList("Reason");
                
            }

            @Override
            public void onCancelled(FirebaseError fe) {
                System.err.println("Error: Unable to fetch Survey Questions!");
                JOptionPane.showMessageDialog(null, "Unable to open pane");
                System.exit(1);
            }
        });
    }

    private void setList(String type) {
        ArrayList<String> arrList;
        selectedIndex = 0;
        if (type.equals("Reason")) {
            arrList = reasonQuestions;
        }else{
            arrList = sourceQuestions;
        }
        
        DefaultListModel df = new DefaultListModel();
        for (int i = 0; i < arrList.size(); i++) {
            df.add(i, arrList.get(i));
        }
        lstQuestions.setModel(df);
    }

}
