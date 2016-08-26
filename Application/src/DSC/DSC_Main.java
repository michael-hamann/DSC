package DSC;

import static DSC.DBClass.getConnection;
import javax.swing.JFrame;

/**
 *
 * @author Aliens_Michael
 */
public class DSC_Main extends javax.swing.JFrame {

    /**
     * Creates new form DSC_Main
     */
    public DSC_Main() {
        initComponents();
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        getConnection("Website");
        MainScreen_Charts.createBarGraph_Meals(pnlBarChart);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        entityManager1 = java.beans.Beans.isDesignTime() ? null : javax.persistence.Persistence.createEntityManagerFactory("doorstepchef?zeroDateTimeBehavior=convertToNullPU").createEntityManager();
        pnlBackground = new javax.swing.JPanel();
        pnlHeading = new javax.swing.JPanel();
        lblLogo = new javax.swing.JLabel();
        lblName = new javax.swing.JLabel();
        pnlTables = new javax.swing.JPanel();
        btnDriverTbl = new javax.swing.JButton();
        btnMealsTbl = new javax.swing.JButton();
        btnClientTbl = new javax.swing.JButton();
        btnSuburbTbl = new javax.swing.JButton();
        lblView = new javax.swing.JLabel();
        btnRouteView = new javax.swing.JButton();
        btnViewOrders = new javax.swing.JButton();
        pnlReports = new javax.swing.JPanel();
        btnDriverRpt = new javax.swing.JButton();
        btnChefRpt = new javax.swing.JButton();
        btnPackerRpt = new javax.swing.JButton();
        btnHandlerRpt = new javax.swing.JButton();
        lblGenerate = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        btnChangeChart = new javax.swing.JButton();
        btnChangeStatistics = new javax.swing.JButton();
        pnlBarChart = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("DSC");

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
                .addContainerGap(70, Short.MAX_VALUE)
                .addComponent(lblLogo, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblName, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(70, Short.MAX_VALUE))
        );
        pnlHeadingLayout.setVerticalGroup(
            pnlHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlHeadingLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlHeadingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblName, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblLogo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pnlTables.setBackground(new java.awt.Color(0, 204, 51));
        pnlTables.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnDriverTbl.setText("Driver Table");
        btnDriverTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDriverTblActionPerformed(evt);
            }
        });

        btnMealsTbl.setText("Meals Table");
        btnMealsTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnMealsTblActionPerformed(evt);
            }
        });

        btnClientTbl.setText("Client Table");
        btnClientTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnClientTblActionPerformed(evt);
            }
        });

        btnSuburbTbl.setText("Suburb Table");
        btnSuburbTbl.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuburbTblActionPerformed(evt);
            }
        });

        lblView.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblView.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblView.setText("View Tables");

        btnRouteView.setText("Route View");
        btnRouteView.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRouteViewActionPerformed(evt);
            }
        });

        btnViewOrders.setText("View Orders");
        btnViewOrders.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnViewOrdersActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pnlTablesLayout = new javax.swing.GroupLayout(pnlTables);
        pnlTables.setLayout(pnlTablesLayout);
        pnlTablesLayout.setHorizontalGroup(
            pnlTablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTablesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlTablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDriverTbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnMealsTbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnClientTbl, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnSuburbTbl, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                    .addComponent(lblView, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRouteView, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnViewOrders, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlTablesLayout.setVerticalGroup(
            pnlTablesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTablesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblView, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDriverTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnMealsTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnClientTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSuburbTbl, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnRouteView, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnViewOrders, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        pnlReports.setBackground(new java.awt.Color(0, 204, 51));
        pnlReports.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        btnDriverRpt.setText("Driver Report");
        btnDriverRpt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDriverRptActionPerformed(evt);
            }
        });

        btnChefRpt.setText("Chef Report");
        btnChefRpt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChefRptActionPerformed(evt);
            }
        });

        btnPackerRpt.setText("Packer Report");
        btnPackerRpt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPackerRptActionPerformed(evt);
            }
        });

        btnHandlerRpt.setText("Handler Report");
        btnHandlerRpt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHandlerRptActionPerformed(evt);
            }
        });

        lblGenerate.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lblGenerate.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblGenerate.setText("Generate Reports");

        javax.swing.GroupLayout pnlReportsLayout = new javax.swing.GroupLayout(pnlReports);
        pnlReports.setLayout(pnlReportsLayout);
        pnlReportsLayout.setHorizontalGroup(
            pnlReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlReportsLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pnlReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnDriverRpt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnChefRpt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPackerRpt, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnHandlerRpt, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblGenerate, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE))
                .addContainerGap())
        );
        pnlReportsLayout.setVerticalGroup(
            pnlReportsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlReportsLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGenerate, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnDriverRpt, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnChefRpt, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnPackerRpt, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnHandlerRpt, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jPanel3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        btnChangeChart.setText("Change Chart");
        btnChangeChart.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChangeChartActionPerformed(evt);
            }
        });

        btnChangeStatistics.setText("Change Statistics");

        pnlBarChart.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        javax.swing.GroupLayout pnlBackgroundLayout = new javax.swing.GroupLayout(pnlBackground);
        pnlBackground.setLayout(pnlBackgroundLayout);
        pnlBackgroundLayout.setHorizontalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlHeading, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pnlTables, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlBarChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnChangeStatistics, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                    .addComponent(btnChangeChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pnlReports, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlBackgroundLayout.setVerticalGroup(
            pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                .addComponent(pnlHeading, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pnlTables, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pnlReports, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlBackgroundLayout.createSequentialGroup()
                        .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                                .addComponent(btnChangeChart)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(pnlBarChart, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(pnlBackgroundLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(pnlBackgroundLayout.createSequentialGroup()
                                .addComponent(btnChangeStatistics)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(pnlBackground, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnDriverTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDriverTblActionPerformed
        this.dispose();
        new DSC_DriverTable().setVisible(true);
    }//GEN-LAST:event_btnDriverTblActionPerformed

    private void btnMealsTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnMealsTblActionPerformed
        this.dispose();
        new DSC_MealsTable().setVisible(true);
    }//GEN-LAST:event_btnMealsTblActionPerformed

    private void btnClientTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnClientTblActionPerformed
        this.dispose();
        new DSC_ClientTable().setVisible(true);
    }//GEN-LAST:event_btnClientTblActionPerformed

    private void btnSuburbTblActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuburbTblActionPerformed
        this.dispose();
        new DSC_SuburbTable().setVisible(true);
    }//GEN-LAST:event_btnSuburbTblActionPerformed

    private void btnDriverRptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDriverRptActionPerformed
      
        DriverReport.getDriverData_Clientstb();
        
    }//GEN-LAST:event_btnDriverRptActionPerformed

    private void btnChefRptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChefRptActionPerformed

        ChefReport.getChefData_Ordertb(); // get data relavant to ChefReport

    }//GEN-LAST:event_btnChefRptActionPerformed

    private void btnPackerRptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPackerRptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnPackerRptActionPerformed

    private void btnHandlerRptActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHandlerRptActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnHandlerRptActionPerformed

    private void btnRouteViewActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRouteViewActionPerformed
        this.dispose();
        new DSC_RouteView().setVisible(true);
    }//GEN-LAST:event_btnRouteViewActionPerformed

    private void btnViewOrdersActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnViewOrdersActionPerformed
        this.dispose();
        new DSC_VeiwOrder().setVisible(true);
    }//GEN-LAST:event_btnViewOrdersActionPerformed

    private void btnChangeChartActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChangeChartActionPerformed
        
    }//GEN-LAST:event_btnChangeChartActionPerformed

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
            java.util.logging.Logger.getLogger(DSC_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(DSC_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(DSC_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(DSC_Main.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new DSC_Main().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChangeChart;
    private javax.swing.JButton btnChangeStatistics;
    private javax.swing.JButton btnChefRpt;
    private javax.swing.JButton btnClientTbl;
    private javax.swing.JButton btnDriverRpt;
    private javax.swing.JButton btnDriverTbl;
    private javax.swing.JButton btnHandlerRpt;
    private javax.swing.JButton btnMealsTbl;
    private javax.swing.JButton btnPackerRpt;
    private javax.swing.JButton btnRouteView;
    private javax.swing.JButton btnSuburbTbl;
    private javax.swing.JButton btnViewOrders;
    private javax.persistence.EntityManager entityManager1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JLabel lblGenerate;
    private javax.swing.JLabel lblLogo;
    private javax.swing.JLabel lblName;
    private javax.swing.JLabel lblView;
    private javax.swing.JPanel pnlBackground;
    private javax.swing.JPanel pnlBarChart;
    private javax.swing.JPanel pnlHeading;
    private javax.swing.JPanel pnlReports;
    private javax.swing.JPanel pnlTables;
    // End of variables declaration//GEN-END:variables
}
