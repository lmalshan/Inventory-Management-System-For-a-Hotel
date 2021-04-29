/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Model.InvalidInputException;
import Model.Stock;
import ServiceLayer.DeleteStockService;
import ServiceLayer.StockService;
import ServiceLayer.UpdateStockService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Dean
 */
public class StockManagement extends javax.swing.JFrame {

    /**
     * Creates new form StockManagement
     */
    public StockManagement() {
        initComponents();
        try {
            viewStock();
        } catch (SQLException ex) {
            Logger.getLogger(StockManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addStock() {
        try {
            if ((!txtDate.getText().isEmpty())
                    && (!txtItemID.getText().isEmpty())
                    && (!txtName.getText().isEmpty())
                    && (!txtQuantity.getText().isEmpty())
                    && (!txtPrice.getText().isEmpty())
                    && (!txtSuppID.getText().isEmpty())) {

                String Date = txtDate.getText();
                int ItemID = Integer.parseInt(txtItemID.getText());
                String Name = txtName.getText();
                String Category = (String) ComboCategory.getSelectedItem();
                int Quantity = Integer.parseInt(txtQuantity.getText());
                float Price = Float.parseFloat(txtPrice.getText());
                int SupplierID = Integer.parseInt(txtSuppID.getText());

                Stock stock = new Stock(ItemID, Name, Category, Quantity, Price, SupplierID, Date);

                StockService service1 = new StockService();
                service1.AddStock(stock);

                JOptionPane.showMessageDialog(rootPane, "Submitted Successfully");

            } else {
                throw new InvalidInputException();
            }
        } catch (InvalidInputException ex) {

            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 0);
        }
    }

    public void deleteStock() {
        try {
            if ((!txtDItemID.getText().isEmpty())
                    && (!txtDDate.getText().isEmpty())) {

                int ItemID = Integer.parseInt(txtDItemID.getText());
                String Date = txtDDate.getText();

                DeleteStockService ser = new DeleteStockService();
                ser.DeleteStock(ItemID, Date);

                JOptionPane.showMessageDialog(rootPane, "Deleted Successfully");
            } else {
                throw new InvalidInputException();
            }
        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "error", 0);
        }
    }

    public void updateStock() {
        try {
            if ((!txtUDate.getText().isEmpty())
                    && (!txtUItemID.getText().isEmpty())
                    && (!txtUName.getText().isEmpty())
                    && (!txtUQuantity.getText().isEmpty())
                    && (!txtUPrice.getText().isEmpty())
                    && (!txtUSuppID.getText().isEmpty())) {

                /*String Date = txtUDate.getText();
                int ItemID = Integer.parseInt(txtUItemID.getText());
                String Name = txtUName.getText();
                String Category = (String) ComboUCategory.getSelectedItem();
                int Quantity = Integer.parseInt(txtUQuantity.getText());
                float Price = Float.parseFloat(txtUPrice.getText());
                int SupplierID = Integer.parseInt(txtUSuppID.getText());*/
                
                 String Date = txtUDate.getText();
                int ItemID = Integer.parseInt(txtUItemID.getText());
                String Name = txtUName.getText();
                String Category = (String) ComboUCategory.getSelectedItem();
                int Quantity = Integer.parseInt(txtUQuantity.getText());
                float Price = Float.parseFloat(txtUPrice.getText());
                int SupplierID = Integer.parseInt(txtUSuppID.getText());

                Stock stock = new Stock(ItemID, Name, Category, Quantity, Price, SupplierID, Date);

                UpdateStockService ser = new UpdateStockService();
                ser.UpdateStock(stock, Date);

                JOptionPane.showMessageDialog(rootPane, "Updated Successfully");

            } else {
                throw new InvalidInputException();
            }
        } catch (InvalidInputException ex) {

            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 0);
        }
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
        DefaultTableModel model = (DefaultTableModel) jTable_view_stock.getModel();
        Object[] row = new Object[7];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getItemID();
            row[1] = list.get(i).getName();
            row[2] = list.get(i).getCategory();
            row[3] = list.get(i).getQuantity();
            row[4] = list.get(i).getUnitPrice();
            row[5] = list.get(i).getSupplier();
            row[6] = list.get(i).getDate();
            model.addRow(row);
        }
    }

    public ArrayList<Stock> searchList() throws SQLException {
        ArrayList<Stock> searchList = new ArrayList<>();

        try {
            String URL = "jdbc:sqlserver://localhost:1433;databaseName=InventoryDBB;user=sa;password=sa123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(URL);
            String word = txtSearch.getText();
            String query1 = "select * from StockTable where Name = '" + word + "' or Category = '" + word + "'";
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
        DefaultTableModel model = (DefaultTableModel) jTable_view_stock.getModel();
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
        jTable_view_stock = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtDDate = new javax.swing.JTextField();
        txtDItemID = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        ibiDValidate = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtDate = new javax.swing.JTextField();
        txtName = new javax.swing.JTextField();
        txtItemID = new javax.swing.JTextField();
        txtQuantity = new javax.swing.JTextField();
        ComboCategory = new javax.swing.JComboBox<>();
        txtPrice = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        jLabel13 = new javax.swing.JLabel();
        txtSuppID = new javax.swing.JTextField();
        jButton8 = new javax.swing.JButton();
        ibiValidate = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        txtUDate = new javax.swing.JTextField();
        txtUName = new javax.swing.JTextField();
        txtUItemID = new javax.swing.JTextField();
        txtUQuantity = new javax.swing.JTextField();
        ComboUCategory = new javax.swing.JComboBox<>();
        txtUPrice = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        txtUSuppID = new javax.swing.JTextField();
        jButton9 = new javax.swing.JButton();
        ibiUValidate = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Stock Management");
        setMaximumSize(new java.awt.Dimension(1200, 650));
        setMinimumSize(new java.awt.Dimension(1200, 650));
        setPreferredSize(new java.awt.Dimension(1200, 650));

        jPanel1.setBackground(new java.awt.Color(31, 83, 136));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(760, 650));
        jPanel1.setLayout(null);

        jTable_view_stock.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Name", "Category", "Quantity", "Unit Price", "Supplier ID", "Date"
            }
        ));
        jTable_view_stock.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_view_stockMouseClicked(evt);
            }
        });
        jTable_view_stock.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTable_view_stockKeyPressed(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_view_stock);

        jPanel1.add(jScrollPane1);
        jScrollPane1.setBounds(10, 70, 732, 450);

        jLabel3.setFont(new java.awt.Font("Sitka Heading", 1, 22)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("View Stock");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(338, 23, 130, 44);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Search");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(34, 567, 50, 15);

        txtSearch.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });
        jPanel1.add(txtSearch);
        txtSearch.setBounds(130, 564, 169, 21);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/preferences-system-search-icon (1).png"))); // NOI18N
        jButton3.setText("Search");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(371, 553, 100, 33);

        jButton4.setBackground(new java.awt.Color(255, 255, 255));
        jButton4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton4.setText("View Items");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton4);
        jButton4.setBounds(621, 553, 93, 30);

        jPanel2.setBackground(new java.awt.Color(31, 83, 136));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(298, 250));
        jPanel2.setLayout(null);

        jLabel1.setFont(new java.awt.Font("Sitka Heading", 1, 22)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Delete Stock");
        jPanel2.add(jLabel1);
        jLabel1.setBounds(174, 15, 130, 29);

        jLabel2.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Date");
        jPanel2.add(jLabel2);
        jLabel2.setBounds(56, 65, 26, 15);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Item ID");
        jPanel2.add(jLabel5);
        jLabel5.setBounds(56, 98, 39, 15);

        jButton1.setBackground(new java.awt.Color(255, 255, 255));
        jButton1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/Status-dialog-error-icon (1).png"))); // NOI18N
        jButton1.setText("Delete Stock");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton1);
        jButton1.setBounds(272, 146, 129, 30);

        txtDDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtDDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDDateKeyTyped(evt);
            }
        });
        jPanel2.add(txtDDate);
        txtDDate.setBounds(173, 59, 200, 21);

        txtDItemID.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtDItemID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDItemIDKeyTyped(evt);
            }
        });
        jPanel2.add(txtDItemID);
        txtDItemID.setBounds(173, 98, 200, 21);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/back-icon.png"))); // NOI18N
        jButton2.setText("Home");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton2);
        jButton2.setBounds(305, 185, 100, 33);

        jButton10.setBackground(new java.awt.Color(255, 255, 255));
        jButton10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/Delete-icon (1).png"))); // NOI18N
        jButton10.setText("Clear");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton10);
        jButton10.setBounds(39, 146, 90, 33);

        ibiDValidate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ibiDValidate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(ibiDValidate);
        ibiDValidate.setBounds(153, 146, 114, 23);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(478, 400));

        jPanel3.setBackground(new java.awt.Color(31, 83, 136));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jPanel3KeyTyped(evt);
            }
        });
        jPanel3.setLayout(null);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Date");
        jPanel3.add(jLabel7);
        jLabel7.setBounds(34, 18, 40, 15);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Category");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(34, 102, 51, 15);

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Name");
        jPanel3.add(jLabel9);
        jLabel9.setBounds(34, 74, 33, 15);

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Item ID");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(34, 46, 39, 15);

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Quantity");
        jPanel3.add(jLabel11);
        jLabel11.setBounds(34, 130, 47, 15);

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Unit Price");
        jPanel3.add(jLabel12);
        jLabel12.setBounds(34, 158, 55, 15);

        txtDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDateActionPerformed(evt);
            }
        });
        txtDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDateKeyTyped(evt);
            }
        });
        jPanel3.add(txtDate);
        txtDate.setBounds(182, 15, 200, 21);

        txtName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jPanel3.add(txtName);
        txtName.setBounds(182, 71, 200, 21);

        txtItemID.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtItemID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtItemIDKeyTyped(evt);
            }
        });
        jPanel3.add(txtItemID);
        txtItemID.setBounds(182, 43, 200, 21);

        txtQuantity.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtQuantityKeyTyped(evt);
            }
        });
        jPanel3.add(txtQuantity);
        txtQuantity.setBounds(182, 127, 200, 21);

        ComboCategory.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ComboCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kitchen", "Tea Pantry", "Grocery", "Soft-Drinks" }));
        jPanel3.add(ComboCategory);
        ComboCategory.setBounds(182, 99, 200, 21);

        txtPrice.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPriceKeyTyped(evt);
            }
        });
        jPanel3.add(txtPrice);
        txtPrice.setBounds(182, 155, 200, 21);

        jButton5.setBackground(new java.awt.Color(255, 255, 255));
        jButton5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/math-add-icon (1).png"))); // NOI18N
        jButton5.setText("Add Stock");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton5);
        jButton5.setBounds(257, 250, 120, 33);

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Supplier ID");
        jPanel3.add(jLabel13);
        jLabel13.setBounds(34, 186, 61, 15);

        txtSuppID.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtSuppID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtSuppIDKeyTyped(evt);
            }
        });
        jPanel3.add(txtSuppID);
        txtSuppID.setBounds(182, 183, 200, 21);

        jButton8.setBackground(new java.awt.Color(255, 255, 255));
        jButton8.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/Delete-icon (1).png"))); // NOI18N
        jButton8.setText("Clear");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton8);
        jButton8.setBounds(34, 247, 90, 33);

        ibiValidate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ibiValidate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(ibiValidate);
        ibiValidate.setBounds(203, 247, 77, 20);

        jTabbedPane1.addTab("Add Stock", jPanel3);

        jPanel4.setBackground(new java.awt.Color(31, 83, 136));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(null);

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Date");
        jPanel4.add(jLabel14);
        jLabel14.setBounds(34, 18, 30, 15);

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Category");
        jPanel4.add(jLabel15);
        jLabel15.setBounds(34, 102, 51, 15);

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Name");
        jPanel4.add(jLabel16);
        jLabel16.setBounds(34, 74, 33, 15);

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Item ID");
        jPanel4.add(jLabel17);
        jLabel17.setBounds(34, 46, 39, 15);

        jLabel18.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Quantity");
        jPanel4.add(jLabel18);
        jLabel18.setBounds(34, 130, 47, 15);

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Unit Price");
        jPanel4.add(jLabel19);
        jLabel19.setBounds(34, 158, 55, 15);

        txtUDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUDateActionPerformed(evt);
            }
        });
        txtUDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUDateKeyTyped(evt);
            }
        });
        jPanel4.add(txtUDate);
        txtUDate.setBounds(182, 15, 200, 21);

        txtUName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jPanel4.add(txtUName);
        txtUName.setBounds(182, 71, 200, 21);

        txtUItemID.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUItemID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUItemIDKeyTyped(evt);
            }
        });
        jPanel4.add(txtUItemID);
        txtUItemID.setBounds(182, 43, 200, 21);

        txtUQuantity.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUQuantity.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUQuantityKeyTyped(evt);
            }
        });
        jPanel4.add(txtUQuantity);
        txtUQuantity.setBounds(182, 127, 200, 21);

        ComboUCategory.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ComboUCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kitchen", "Tea Pantry", "Grocery", "Soft-Drinks" }));
        jPanel4.add(ComboUCategory);
        ComboUCategory.setBounds(182, 99, 200, 21);

        txtUPrice.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUPriceKeyTyped(evt);
            }
        });
        jPanel4.add(txtUPrice);
        txtUPrice.setBounds(182, 155, 200, 21);

        jButton7.setBackground(new java.awt.Color(255, 255, 255));
        jButton7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/math-add-icon (1).png"))); // NOI18N
        jButton7.setText("Update Stock");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7);
        jButton7.setBounds(240, 250, 140, 33);

        jLabel20.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel20.setForeground(new java.awt.Color(255, 255, 255));
        jLabel20.setText("Supplier ID");
        jPanel4.add(jLabel20);
        jLabel20.setBounds(34, 186, 61, 15);

        txtUSuppID.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUSuppID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUSuppIDKeyTyped(evt);
            }
        });
        jPanel4.add(txtUSuppID);
        txtUSuppID.setBounds(182, 183, 200, 21);

        jButton9.setBackground(new java.awt.Color(255, 255, 255));
        jButton9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/Delete-icon (1).png"))); // NOI18N
        jButton9.setText("Clear");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton9);
        jButton9.setBounds(32, 248, 91, 33);

        ibiUValidate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ibiUValidate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(ibiUValidate);
        ibiUValidate.setBounds(280, 222, 111, 19);

        jTabbedPane1.addTab("Update Stock", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 478, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDateActionPerformed

    private void txtUDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUDateActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ManagerHomePage mh = new ManagerHomePage();
        setVisible(false);
        mh.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        txtDate.setText("");
        txtItemID.setText("");
        txtName.setText("");
        txtQuantity.setText("");
        txtPrice.setText("");
        txtSuppID.setText("");
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        txtUDate.setText("");
        txtUItemID.setText("");
        txtUName.setText("");
        txtUQuantity.setText("");
        txtUPrice.setText("");
        txtUSuppID.setText("");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        txtDDate.setText("");
        txtDItemID.setText("");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            // TODO add your handling code here:
            DefaultTableModel model = (DefaultTableModel) jTable_view_stock.getModel();
            model.setRowCount(0);
            viewSearchStock();
        } catch (SQLException ex) {
            Logger.getLogger(StockManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            // TODO add your handling code here:
            addStock();
            DefaultTableModel model = (DefaultTableModel) jTable_view_stock.getModel();
            model.setRowCount(0);
            viewStock();
        } catch (SQLException ex) {
            Logger.getLogger(StockManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        try {
            // TODO add your handling code here:
            updateStock();
            DefaultTableModel model = (DefaultTableModel) jTable_view_stock.getModel();
            model.setRowCount(0);
            viewStock();
        } catch (SQLException ex) {
            Logger.getLogger(StockManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            deleteStock();
            DefaultTableModel model = (DefaultTableModel) jTable_view_stock.getModel();
            model.setRowCount(0);
            viewStock();
        } catch (SQLException ex) {
            Logger.getLogger(StockManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable_view_stockMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_view_stockMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable_view_stock.getModel();
        int selectedRowIndex = jTable_view_stock.getSelectedRow();

        txtUItemID.setText(model.getValueAt(selectedRowIndex, 0).toString());
        txtUName.setText(model.getValueAt(selectedRowIndex, 1).toString());
        txtUQuantity.setText(model.getValueAt(selectedRowIndex, 3).toString());
        txtUPrice.setText(model.getValueAt(selectedRowIndex, 4).toString());
        txtUSuppID.setText(model.getValueAt(selectedRowIndex, 5).toString());
        txtUDate.setText(model.getValueAt(selectedRowIndex, 6).toString());
    }//GEN-LAST:event_jTable_view_stockMouseClicked

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        // TODO add your handling code here:
//        DefaultTableModel model = (DefaultTableModel) jTable_view_stock.getModel();
//        String search = txtSearch.getText();
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
//        jTable_view_stock.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(search));
    }//GEN-LAST:event_txtSearchKeyPressed

    private void jTable_view_stockKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTable_view_stockKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTable_view_stockKeyPressed

    private void jPanel3KeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jPanel3KeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_jPanel3KeyTyped

    private void txtDateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDateKeyTyped
        // TODO add your handling code here:
//        String ptt = "^[0-9]{0,30}";
//        Pattern ptn = Pattern.compile(ptt);
//        Matcher match = ptn.matcher(txtDate.getText());
//        if (!match.matches()) {
//            ibiValidate.setText("Invalid Date");
//        } else {
//            ibiValidate.setText(null);
//        }
    }//GEN-LAST:event_txtDateKeyTyped

    private void txtItemIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtItemIDKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtItemID.getText());
        if (!match.matches()) {
            ibiValidate.setText("Invalid ItemID");
        } else {
            ibiValidate.setText(null);
        }
    }//GEN-LAST:event_txtItemIDKeyTyped

    private void txtQuantityKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtQuantityKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtQuantity.getText());
        if (!match.matches()) {
            ibiValidate.setText("Invalid Qty");
        } else {
            ibiValidate.setText(null);
        }
    }//GEN-LAST:event_txtQuantityKeyTyped

    private void txtPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPriceKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtPrice.getText());
        if (!match.matches()) {
            ibiValidate.setText("Invalid Price");
        } else {
            ibiValidate.setText(null);
        }
    }//GEN-LAST:event_txtPriceKeyTyped

    private void txtSuppIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSuppIDKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtSuppID.getText());
        if (!match.matches()) {
            ibiValidate.setText("Invalid SuppID");
        } else {
            ibiValidate.setText(null);
        }
    }//GEN-LAST:event_txtSuppIDKeyTyped

    private void txtUDateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUDateKeyTyped
        // TODO add your handling code here:
//        String ptt = "^[0-9]{0,30}";
//        Pattern ptn = Pattern.compile(ptt);
//        Matcher match = ptn.matcher(txtUDate.getText());
//        if (!match.matches()) {
//            ibiUValidate.setText("Invalid Date");
//        } else {
//            ibiUValidate.setText(null);
//        }
    }//GEN-LAST:event_txtUDateKeyTyped

    private void txtUItemIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUItemIDKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtUItemID.getText());
        if (!match.matches()) {
            ibiUValidate.setText("Invalid ItemID");
        } else {
            ibiUValidate.setText(null);
        }
    }//GEN-LAST:event_txtUItemIDKeyTyped

    private void txtUQuantityKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUQuantityKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtUQuantity.getText());
        if (!match.matches()) {
            ibiUValidate.setText("Invalid Qty");
        } else {
            ibiUValidate.setText(null);
        }
    }//GEN-LAST:event_txtUQuantityKeyTyped

    private void txtUPriceKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUPriceKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtUPrice.getText());
        if (!match.matches()) {
            ibiUValidate.setText("Invalid Price");
        } else {
            ibiUValidate.setText(null);
        }
    }//GEN-LAST:event_txtUPriceKeyTyped

    private void txtUSuppIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUSuppIDKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtUSuppID.getText());
        if (!match.matches()) {
            ibiUValidate.setText("Invalid SuppID");
        } else {
            ibiUValidate.setText(null);
        }
    }//GEN-LAST:event_txtUSuppIDKeyTyped

    private void txtDDateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDDateKeyTyped
        // TODO add your handling code here:
//        String ptt = "^[0-9]{0,30}";
//        Pattern ptn = Pattern.compile(ptt);
//        Matcher match = ptn.matcher(txtDDate.getText());
//        if (!match.matches()) {
//            ibiDValidate.setText("Invalid Date");
//        } else {
//            ibiDValidate.setText(null);
//        }
    }//GEN-LAST:event_txtDDateKeyTyped

    private void txtDItemIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDItemIDKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtDItemID.getText());
        if (!match.matches()) {
            ibiDValidate.setText("Invalid ItemID");
        } else {
            ibiDValidate.setText(null);
        }
    }//GEN-LAST:event_txtDItemIDKeyTyped

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            DefaultTableModel model = (DefaultTableModel) jTable_view_stock.getModel();
            model.setRowCount(0);
            viewStock();
        } catch (SQLException ex) {
            Logger.getLogger(ItemManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton4ActionPerformed

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
            java.util.logging.Logger.getLogger(StockManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(StockManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(StockManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(StockManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new StockManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboCategory;
    private javax.swing.JComboBox<String> ComboUCategory;
    private javax.swing.JLabel ibiDValidate;
    private javax.swing.JLabel ibiUValidate;
    private javax.swing.JLabel ibiValidate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable_view_stock;
    private javax.swing.JTextField txtDDate;
    private javax.swing.JTextField txtDItemID;
    private javax.swing.JTextField txtDate;
    private javax.swing.JTextField txtItemID;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQuantity;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtSuppID;
    private javax.swing.JTextField txtUDate;
    private javax.swing.JTextField txtUItemID;
    private javax.swing.JTextField txtUName;
    private javax.swing.JTextField txtUPrice;
    private javax.swing.JTextField txtUQuantity;
    private javax.swing.JTextField txtUSuppID;
    // End of variables declaration//GEN-END:variables
}
