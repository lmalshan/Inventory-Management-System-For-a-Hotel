/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Model.Stock;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.RowFilter;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Dean
 */
public class ViewStockOwner extends javax.swing.JFrame {

    /**
     * Creates new form ViewStockOwner
     */
    public ViewStockOwner() {
        initComponents();
        showTime();
        showDate();
        try {
            viewStock();
        } catch (SQLException ex) {
            Logger.getLogger(ViewStockOwner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    void showDate() {
        java.util.Date d = new java.util.Date();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        lblDate.setText(s.format(d));
    }

    void showTime() {
        new Timer(0, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                java.util.Date d = new java.util.Date();
                SimpleDateFormat s = new SimpleDateFormat("hh:mm:ss a");
                lblTime.setText(s.format(d));
            }
        }).start();
    }
    
    
    
    public ArrayList<Stock> stockList() throws SQLException {
        ArrayList<Stock> stockList = new ArrayList<>();

        try {
            String URL = "jdbc:sqlserver://localhost:1433;databaseName=InventoryDBB;user=sa;password=sa123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(URL);
            String query1 = "select * from StockTable";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query1);
            Stock stock;
            while (rs.next()) {
                stock = new Stock(rs.getInt("ItemID"), rs.getString("Name"), rs.getString("Category"), rs.getInt("Quantity"), rs.getFloat("UnitPrice"), rs.getInt("SupplierID"), rs.getString("Date"));
                stockList.add(stock);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return stockList;
    }

    public void viewStock() throws SQLException {
        ArrayList<Stock> list = stockList();
        DefaultTableModel model = (DefaultTableModel) jTable_view_stockO.getModel();
        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getItemID();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getCategory();
            row[3] = list.get(i).getQuantity();
            row[4] = list.get(i).getUnitPrice();
            row[5] = list.get(i).getSupplier();
            model.addRow(row);
        }
    }
    
    public ArrayList<Stock> searchList() throws SQLException {
        ArrayList<Stock> searchList = new ArrayList<>();

        try {
            String URL = "jdbc:sqlserver://localhost:1433;databaseName=InventoryDBB;user=sa;password=sa123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(URL);
            String word = txtSearchO.getText();
            String query1 = "select * from StockTable where Name = '"+word+"' or Category = '"+word+"'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query1);
            Stock stock;
            while (rs.next()) {
                stock = new Stock(rs.getInt("ItemID"), rs.getString("Name"), rs.getString("Category"), rs.getInt("Quantity"), rs.getFloat("UnitPrice"), rs.getInt("SupplierID"), rs.getString("Date"));
                searchList.add(stock);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return searchList;
    }

    public void viewSearchStock() throws SQLException {
        ArrayList<Stock> list = searchList();
        DefaultTableModel model = (DefaultTableModel) jTable_view_stockO.getModel();
        model.setRowCount(0);
        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getItemID();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getCategory();
            row[3] = list.get(i).getQuantity();
            row[4] = list.get(i).getUnitPrice();
            row[5] = list.get(i).getSupplier();
            model.addRow(row);
        }
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_view_stockO = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        txtSearchO = new javax.swing.JTextField();
        jButton3O = new javax.swing.JButton();
        jButton4O = new javax.swing.JButton();
        lblDate = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("View Stock");
        setSize(new java.awt.Dimension(804, 600));

        jPanel1.setMaximumSize(new java.awt.Dimension(804, 600));
        jPanel1.setMinimumSize(new java.awt.Dimension(804, 600));
        jPanel1.setPreferredSize(new java.awt.Dimension(804, 600));
        jPanel1.setLayout(null);

        jTable_view_stockO.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Item ID", "Name", "Category", "Quantity", "Unit Price", "Supplier ID"
            }
        ));
        jTable_view_stockO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_view_stockOMouseClicked(evt);
            }
        });
        jTable_view_stockO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable_view_stockOKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_view_stockO);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(34, 131, 736, 390);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Search");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(54, 543, 60, 15);

        txtSearchO.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtSearchO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchOKeyPressed(evt);
            }
        });
        jPanel1.add(txtSearchO);
        txtSearchO.setBounds(150, 540, 169, 21);

        jButton3O.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton3O.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/preferences-system-search-icon (1).png"))); // NOI18N
        jButton3O.setText("Search");
        jButton3O.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3OActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3O);
        jButton3O.setBounds(340, 535, 130, 30);

        jButton4O.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton4O.setText("View Items");
        jButton4O.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4OActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4O);
        jButton4O.setBounds(513, 535, 110, 30);

        lblDate.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("jLabel3");
        jPanel1.add(lblDate);
        lblDate.setBounds(650, 30, 90, 13);

        lblTime.setFont(new java.awt.Font("Times New Roman", 0, 11)); // NOI18N
        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setText("jLabel4");
        jPanel1.add(lblTime);
        lblTime.setBounds(650, 70, 90, 13);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/back-icon.png"))); // NOI18N
        jButton1.setText("Back");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(680, 540, 100, 30);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/bg2.jpg"))); // NOI18N
        jLabel1.setText("egerger");
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 804, 600);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTable_view_stockOMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_view_stockOMouseClicked
        // TODO add your handling code here:
        
    }//GEN-LAST:event_jTable_view_stockOMouseClicked

    private void jTable_view_stockOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable_view_stockOKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable_view_stockOKeyPressed

    private void txtSearchOKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchOKeyPressed
        // TODO add your handling code here:
//        DefaultTableModel model = (DefaultTableModel)jTable_view_stockO.getModel();
//        String search = txtSearchO.getText();
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
//        jTable_view_stockO.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(search));
    }//GEN-LAST:event_txtSearchOKeyPressed

    private void jButton3OActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3OActionPerformed
        try {
            // TODO add your handling code here:
            DefaultTableModel model = (DefaultTableModel) jTable_view_stockO.getModel();
        model.setRowCount(0);
            viewSearchStock();
        } catch (SQLException ex) {
            Logger.getLogger(StockManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3OActionPerformed

    private void jButton4OActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4OActionPerformed
        try {
            // TODO add your handling code here:
            DefaultTableModel model = (DefaultTableModel) jTable_view_stockO.getModel();
            model.setRowCount(0);
            viewStock();
        } catch (SQLException ex) {
            Logger.getLogger(ViewStockOwner.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4OActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        OwnerHomePage oh = new OwnerHomePage();
        setVisible(false);
        oh.setVisible(true);
    }//GEN-LAST:event_jButton1ActionPerformed

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
            java.util.logging.Logger.getLogger(ViewStockOwner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ViewStockOwner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ViewStockOwner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ViewStockOwner.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ViewStockOwner().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3O;
    private javax.swing.JButton jButton4O;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable_view_stockO;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblTime;
    private javax.swing.JTextField txtSearchO;
    // End of variables declaration//GEN-END:variables
}
