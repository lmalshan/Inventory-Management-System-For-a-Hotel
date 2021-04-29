/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Model.Item;
import Model.InvalidInputException;
import ServiceLayer.DeleteItemService;
import ServiceLayer.ItemService;
import ServiceLayer.UpdateItemService;
import ServiceLayer.ViewItemService;
import java.awt.Toolkit;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class ItemManagement extends javax.swing.JFrame {

    /**
     * Creates new form ItemManagement
     */
    public ItemManagement() {
        initComponents();
        try {
            viewItems();
        } catch (SQLException ex) {
            Logger.getLogger(ItemManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addItem() {
        try {
            if ((!txtName.getText().isEmpty())
                    && (!txtPrice.getText().isEmpty())
                    && (!txtExpDate.getText().isEmpty())
                    && (!txtROamount.getText().isEmpty())
                    && (!txtROlevel.getText().isEmpty())) {

                int id = 0;
                String Name = txtName.getText();
                String Category = (String) ComboCategory.getSelectedItem();
                float Price = Float.parseFloat(txtPrice.getText());
                String ExpDate = txtExpDate.getText();
                int ROA = Integer.parseInt(txtROamount.getText());
                int ROL = Integer.parseInt(txtROlevel.getText());

                Item item = new Item(id, Name, Category, Price, ExpDate, ROA, ROL);

                ItemService ser = new ItemService();
                ser.AddItem(item);

                JOptionPane.showMessageDialog(rootPane, "Submitted Successfully");

            } else {
                throw new InvalidInputException();
            }

        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 0);
        }
    }

    public void deleteItem() {
        try {

            if (!txtDItemID.getText().isEmpty()) {
                int DItemID = Integer.parseInt(txtDItemID.getText());

                DeleteItemService ser = new DeleteItemService();
                ser.DeleteItem(DItemID);

                JOptionPane.showMessageDialog(rootPane, "Deleted Successfully");

            } else {
                throw new InvalidInputException();
            }

        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 0);
        }
    }

    public void updateItem() {
        try {
            if ((!txtUName.getText().isEmpty())
                    && (!txtUPrice.getText().isEmpty())
                    && (!txtUExpDate.getText().isEmpty())
                    && (!txtUROamount.getText().isEmpty())
                    && (!txtUROlevel.getText().isEmpty())) {

                int UItemID = Integer.parseInt(txtUItemID.getText());

                String Name = txtUName.getText();
                String Category = (String) ComboUCategory.getSelectedItem();
                float Price = Float.parseFloat(txtUPrice.getText());
                String ExpDate = txtUExpDate.getText();
                int ROA = Integer.parseInt(txtUROamount.getText());
                int ROL = Integer.parseInt(txtUROlevel.getText());

                Item item = new Item(UItemID, Name, Category, Price, ExpDate, ROA, ROL);

                UpdateItemService ser = new UpdateItemService();
                ser.UpdateItem(item, UItemID);

                JOptionPane.showMessageDialog(rootPane, "Updated Successfully");

            } else {
                throw new InvalidInputException();
            }
        } catch (InvalidInputException ex) {

            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 0);
        }
    }

    public ArrayList<Item> itemList() throws SQLException {
        ArrayList<Item> itemList = new ArrayList<>();

        try {
            String URL = "jdbc:sqlserver://localhost:1433;databaseName=InventoryDBB;user=sa;password=sa123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(URL);
            String query1 = "select * from ItemTable";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query1);
            Item item;
            while (rs.next()) {
                item = new Item(rs.getInt("ItemID"), rs.getString("Name"), rs.getString("Category"), rs.getFloat("Price"), rs.getString("ExpDate"), rs.getInt("ROAmount"), rs.getInt("ROLevel"));
                itemList.add(item);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return itemList;
    }

    public void viewItems() throws SQLException {
        ArrayList<Item> list = itemList();
        DefaultTableModel model = (DefaultTableModel) jTable_view_items.getModel();
        Object[] row = new Object[7];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getItemID();
            row[1] = list.get(i).getItemName();
            row[2] = list.get(i).getCategory();
            row[3] = list.get(i).getPrice();
            row[4] = list.get(i).getExpdate();
            row[5] = list.get(i).getROAmount();
            row[6] = list.get(i).getROLevel();
            model.addRow(row);
        }
    }

    public ArrayList<Item> searchList() throws SQLException {
        ArrayList<Item> searchList = new ArrayList<>();

        try {
            String URL = "jdbc:sqlserver://localhost:1433;databaseName=InventoryDBB;user=sa;password=sa123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(URL);
            String word = txtSearch.getText();
            String query1 = "select * from ItemTable where Name = '" + word + "' or Category = '" + word + "'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query1);
            Item item;
            while (rs.next()) {
                item = new Item(rs.getInt("ItemID"), rs.getString("Name"), rs.getString("Category"), rs.getFloat("Price"), rs.getString("ExpDate"), rs.getInt("ROAmount"), rs.getInt("ROLevel"));
                searchList.add(item);
            }

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return searchList;
    }

    public void viewSearchItems() throws SQLException {
        ArrayList<Item> list = searchList();
        DefaultTableModel model = (DefaultTableModel) jTable_view_items.getModel();
        model.setRowCount(0);
        Object[] row = new Object[7];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getItemID();
            row[1] = list.get(i).getItemName();
            row[2] = list.get(i).getCategory();
            row[3] = list.get(i).getPrice();
            row[4] = list.get(i).getExpdate();
            row[5] = list.get(i).getROAmount();
            row[6] = list.get(i).getROLevel();
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

        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable_view_items = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        btnSearch = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtDItemID = new javax.swing.JTextField();
        btnDelete = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        ibiDValidate = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtPrice = new javax.swing.JTextField();
        txtExpDate = new javax.swing.JTextField();
        txtROlevel = new javax.swing.JTextField();
        ComboCategory = new javax.swing.JComboBox<>();
        txtROamount = new javax.swing.JTextField();
        btnAdd = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        ibiValidate = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        txtUName = new javax.swing.JTextField();
        txtUPrice = new javax.swing.JTextField();
        txtUExpDate = new javax.swing.JTextField();
        txtUROlevel = new javax.swing.JTextField();
        ComboUCategory = new javax.swing.JComboBox<>();
        txtUROamount = new javax.swing.JTextField();
        btnUpdate = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtUItemID = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        ibiUValidate = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Item Management");
        setPreferredSize(new java.awt.Dimension(1200, 650));

        jPanel2.setBackground(new java.awt.Color(31, 83, 136));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setPreferredSize(new java.awt.Dimension(760, 650));
        jPanel2.setLayout(null);

        jTable_view_items.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item ID", "Name", "Category", "Price", "Exp. Date", "Reorder Amount", "Reorder Level"
            }
        ));
        jTable_view_items.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_view_itemsMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable_view_items);

        jPanel2.add(jScrollPane1);
        jScrollPane1.setBounds(14, 75, 732, 460);

        jLabel3.setFont(new java.awt.Font("Sitka Heading", 1, 22)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("View Items");
        jPanel2.add(jLabel3);
        jLabel3.setBounds(338, 23, 140, 44);

        jLabel4.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Search");
        jPanel2.add(jLabel4);
        jLabel4.setBounds(24, 562, 60, 20);

        txtSearch.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });
        jPanel2.add(txtSearch);
        txtSearch.setBounds(130, 564, 200, 21);

        btnSearch.setBackground(new java.awt.Color(255, 255, 255));
        btnSearch.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnSearch.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/preferences-system-search-icon.png"))); // NOI18N
        btnSearch.setText("Search");
        btnSearch.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSearchActionPerformed(evt);
            }
        });
        jPanel2.add(btnSearch);
        btnSearch.setBounds(371, 553, 107, 41);

        jButton6.setBackground(new java.awt.Color(255, 255, 255));
        jButton6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton6.setText("View Items");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton6);
        jButton6.setBounds(621, 553, 93, 40);

        jPanel1.setBackground(new java.awt.Color(31, 83, 136));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(298, 250));
        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Sitka Heading", 1, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Delete Item");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(160, 15, 140, 29);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Item ID");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(24, 72, 39, 15);

        txtDItemID.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtDItemID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDItemIDKeyTyped(evt);
            }
        });
        jPanel1.add(txtDItemID);
        txtDItemID.setBounds(154, 69, 119, 21);

        btnDelete.setBackground(new java.awt.Color(255, 255, 255));
        btnDelete.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnDelete.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/Status-dialog-error-icon (1).png"))); // NOI18N
        btnDelete.setText("Delete Item");
        btnDelete.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnDeleteActionPerformed(evt);
            }
        });
        jPanel1.add(btnDelete);
        btnDelete.setBounds(143, 108, 130, 33);

        jButton3.setBackground(new java.awt.Color(255, 255, 255));
        jButton3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/back-icon.png"))); // NOI18N
        jButton3.setText("Home");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(315, 185, 100, 33);
        jPanel1.add(ibiDValidate);
        ibiDValidate.setBounds(310, 83, 103, 30);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jPanel3.setBackground(new java.awt.Color(31, 83, 136));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(null);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Name");
        jPanel3.add(jLabel7);
        jLabel7.setBounds(25, 26, 40, 15);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Category");
        jPanel3.add(jLabel8);
        jLabel8.setBounds(25, 60, 51, 15);

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Price");
        jPanel3.add(jLabel9);
        jLabel9.setBounds(25, 94, 30, 15);

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Exp Date");
        jPanel3.add(jLabel10);
        jLabel10.setBounds(25, 128, 50, 15);

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Reorder Level");
        jPanel3.add(jLabel11);
        jLabel11.setBounds(25, 165, 79, 15);

        jLabel12.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("Reorder Amount");
        jPanel3.add(jLabel12);
        jLabel12.setBounds(25, 196, 93, 15);

        txtName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        jPanel3.add(txtName);
        txtName.setBounds(173, 23, 200, 21);

        txtPrice.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPriceKeyTyped(evt);
            }
        });
        jPanel3.add(txtPrice);
        txtPrice.setBounds(173, 91, 200, 21);

        txtExpDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtExpDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtExpDateKeyTyped(evt);
            }
        });
        jPanel3.add(txtExpDate);
        txtExpDate.setBounds(173, 125, 200, 21);

        txtROlevel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtROlevel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtROlevelKeyTyped(evt);
            }
        });
        jPanel3.add(txtROlevel);
        txtROlevel.setBounds(173, 159, 200, 21);

        ComboCategory.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ComboCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kitchen", "Tea Pantry", "Grocery", "Soft-Drinks" }));
        ComboCategory.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboCategoryActionPerformed(evt);
            }
        });
        jPanel3.add(ComboCategory);
        ComboCategory.setBounds(173, 57, 200, 21);

        txtROamount.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtROamount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtROamountKeyTyped(evt);
            }
        });
        jPanel3.add(txtROamount);
        txtROamount.setBounds(173, 193, 200, 21);

        btnAdd.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnAdd.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/math-add-icon (1).png"))); // NOI18N
        btnAdd.setText("Add Item");
        btnAdd.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddActionPerformed(evt);
            }
        });
        jPanel3.add(btnAdd);
        btnAdd.setBounds(263, 265, 110, 33);

        jButton1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/Delete-icon (1).png"))); // NOI18N
        jButton1.setText("Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton1);
        jButton1.setBounds(39, 265, 90, 33);

        ibiValidate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ibiValidate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(ibiValidate);
        ibiValidate.setBounds(173, 262, 114, 26);

        jTabbedPane1.addTab("Add Item", jPanel3);

        jPanel5.setBackground(new java.awt.Color(31, 83, 136));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setLayout(null);

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Name");
        jPanel5.add(jLabel13);
        jLabel13.setBounds(25, 62, 33, 15);

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Category");
        jPanel5.add(jLabel14);
        jLabel14.setBounds(25, 96, 51, 15);

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Price");
        jPanel5.add(jLabel15);
        jLabel15.setBounds(25, 130, 30, 15);

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Exp Date");
        jPanel5.add(jLabel16);
        jLabel16.setBounds(25, 161, 50, 15);

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Reorder Level");
        jPanel5.add(jLabel17);
        jLabel17.setBounds(25, 195, 79, 15);

        jLabel18.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setText("Reorder Amount");
        jPanel5.add(jLabel18);
        jLabel18.setBounds(25, 229, 93, 15);

        txtUName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUNameActionPerformed(evt);
            }
        });
        jPanel5.add(txtUName);
        txtUName.setBounds(175, 59, 200, 21);

        txtUPrice.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUPrice.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUPriceKeyTyped(evt);
            }
        });
        jPanel5.add(txtUPrice);
        txtUPrice.setBounds(176, 127, 200, 21);

        txtUExpDate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUExpDate.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUExpDateKeyTyped(evt);
            }
        });
        jPanel5.add(txtUExpDate);
        txtUExpDate.setBounds(176, 161, 200, 21);

        txtUROlevel.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUROlevel.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUROlevelKeyTyped(evt);
            }
        });
        jPanel5.add(txtUROlevel);
        txtUROlevel.setBounds(176, 195, 200, 21);

        ComboUCategory.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ComboUCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kitchen", "Tea Pantry", "Grocery", "Soft-Drinks" }));
        jPanel5.add(ComboUCategory);
        ComboUCategory.setBounds(176, 93, 200, 21);

        txtUROamount.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUROamount.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUROamountKeyTyped(evt);
            }
        });
        jPanel5.add(txtUROamount);
        txtUROamount.setBounds(176, 229, 200, 21);

        btnUpdate.setBackground(new java.awt.Color(255, 255, 255));
        btnUpdate.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        btnUpdate.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/math-add-icon (1).png"))); // NOI18N
        btnUpdate.setText("Update Item");
        btnUpdate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdateActionPerformed(evt);
            }
        });
        jPanel5.add(btnUpdate);
        btnUpdate.setBounds(246, 267, 130, 33);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Item ID");
        jPanel5.add(jLabel1);
        jLabel1.setBounds(25, 28, 50, 15);

        txtUItemID.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUItemID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUItemIDKeyTyped(evt);
            }
        });
        jPanel5.add(txtUItemID);
        txtUItemID.setBounds(175, 25, 200, 21);

        jButton2.setBackground(new java.awt.Color(255, 255, 255));
        jButton2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/Delete-icon (1).png"))); // NOI18N
        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton2);
        jButton2.setBounds(42, 267, 90, 33);

        ibiUValidate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ibiUValidate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel5.add(ibiUValidate);
        ibiUValidate.setBounds(168, 267, 104, 23);

        jTabbedPane1.addTab("Update Item", jPanel5);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(2, 2, 2)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 7, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 370, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 281, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtUNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUNameActionPerformed

    private void btnAddActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddActionPerformed
        try {
            // TODO add your handling code here:
            addItem();
            DefaultTableModel model = (DefaultTableModel) jTable_view_items.getModel();
            model.setRowCount(0);
            viewItems();
        } catch (SQLException ex) {
            Logger.getLogger(ItemManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnAddActionPerformed

    private void btnUpdateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnUpdateActionPerformed
        try {
            // TODO add your handling code here:
            updateItem();
            DefaultTableModel model = (DefaultTableModel) jTable_view_items.getModel();
            model.setRowCount(0);
            viewItems();
        } catch (SQLException ex) {
            Logger.getLogger(ItemManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnUpdateActionPerformed

    private void btnDeleteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnDeleteActionPerformed
        try {
            // TODO add your handling code here:
            deleteItem();
            DefaultTableModel model = (DefaultTableModel) jTable_view_items.getModel();
            model.setRowCount(0);
            viewItems();
        } catch (SQLException ex) {
            Logger.getLogger(ItemManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnDeleteActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        txtName.setText("");
        ComboCategory.setSelectedItem(" ");
        txtPrice.setText("");
        txtExpDate.setText("");
        txtROlevel.setText("");
        txtROamount.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        txtUItemID.setText("");
        txtUName.setText("");
        txtUPrice.setText("");
        txtUExpDate.setText("");
        txtUROlevel.setText("");
        txtUROamount.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        ManagerHomePage mh = new ManagerHomePage();
        setVisible(false);
        mh.setVisible(true);

    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnSearchActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSearchActionPerformed
        try {
            // TODO add your handling code here:
            viewSearchItems();
        } catch (SQLException ex) {
            Logger.getLogger(ItemManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_btnSearchActionPerformed

    private void jTable_view_itemsMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_view_itemsMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable_view_items.getModel();
        int selectedRowIndex = jTable_view_items.getSelectedRow();

        txtUItemID.setText(model.getValueAt(selectedRowIndex, 0).toString());
        txtUName.setText(model.getValueAt(selectedRowIndex, 1).toString());
        txtUPrice.setText(model.getValueAt(selectedRowIndex, 3).toString());
        txtUExpDate.setText(model.getValueAt(selectedRowIndex, 4).toString());
        txtUROlevel.setText(model.getValueAt(selectedRowIndex, 5).toString());
        txtUROamount.setText(model.getValueAt(selectedRowIndex, 6).toString());
    }//GEN-LAST:event_jTable_view_itemsMouseClicked

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        // TODO add your handling code here:
//        DefaultTableModel model = (DefaultTableModel) jTable_view_items.getModel();
//        String search = txtSearch.getText();
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
//        jTable_view_items.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(search));
    }//GEN-LAST:event_txtSearchKeyPressed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            // TODO add your handling code here:
            DefaultTableModel model = (DefaultTableModel) jTable_view_items.getModel();
            model.setRowCount(0);
            viewItems();
        } catch (SQLException ex) {
            Logger.getLogger(ItemManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

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

    private void txtExpDateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtExpDateKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtExpDate.getText());
        if (!match.matches()) {
            ibiValidate.setText("Invalid ExpDate");
        } else {
            ibiValidate.setText(null);
        }
    }//GEN-LAST:event_txtExpDateKeyTyped

    private void txtROlevelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtROlevelKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtROlevel.getText());
        if (!match.matches()) {
            ibiValidate.setText("Invalid ROlevel");
        } else {
            ibiValidate.setText(null);
        }
    }//GEN-LAST:event_txtROlevelKeyTyped

    private void txtROamountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtROamountKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtROamount.getText());
        if (!match.matches()) {
            ibiValidate.setText("Invalid ROamount");
        } else {
            ibiValidate.setText(null);
        }
    }//GEN-LAST:event_txtROamountKeyTyped

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

    private void txtUExpDateKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUExpDateKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtUExpDate.getText());
        if (!match.matches()) {
            ibiUValidate.setText("Invalid ExpDate");
        } else {
            ibiUValidate.setText(null);
        }
    }//GEN-LAST:event_txtUExpDateKeyTyped

    private void txtUROlevelKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUROlevelKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtUROlevel.getText());
        if (!match.matches()) {
            ibiUValidate.setText("Invalid ROlevel");
        } else {
            ibiUValidate.setText(null);
        }
    }//GEN-LAST:event_txtUROlevelKeyTyped

    private void txtUROamountKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUROamountKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,30}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtUROamount.getText());
        if (!match.matches()) {
            ibiUValidate.setText("Invalid ROamount");
        } else {
            ibiUValidate.setText(null);
        }
    }//GEN-LAST:event_txtUROamountKeyTyped

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

    private void ComboCategoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboCategoryActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ComboCategoryActionPerformed

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
            java.util.logging.Logger.getLogger(ItemManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ItemManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ItemManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ItemManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ItemManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboCategory;
    private javax.swing.JComboBox<String> ComboUCategory;
    private javax.swing.JButton btnAdd;
    private javax.swing.JButton btnDelete;
    private javax.swing.JButton btnSearch;
    private javax.swing.JButton btnUpdate;
    private javax.swing.JLabel ibiDValidate;
    private javax.swing.JLabel ibiUValidate;
    private javax.swing.JLabel ibiValidate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton6;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable_view_items;
    private javax.swing.JTextField txtDItemID;
    private javax.swing.JTextField txtExpDate;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtROamount;
    private javax.swing.JTextField txtROlevel;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUExpDate;
    private javax.swing.JTextField txtUItemID;
    private javax.swing.JTextField txtUName;
    private javax.swing.JTextField txtUPrice;
    private javax.swing.JTextField txtUROamount;
    private javax.swing.JTextField txtUROlevel;
    // End of variables declaration//GEN-END:variables
}
