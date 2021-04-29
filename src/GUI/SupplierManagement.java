/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Model.InvalidInputException;
import Model.Supplier;
import ServiceLayer.DeleteSupplierService;
import ServiceLayer.SupplierService;
import ServiceLayer.UpdateSupplierService;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Dean
 */
public class SupplierManagement extends javax.swing.JFrame {

    /**
     * Creates new form SupplierManagement
     */
    public SupplierManagement() {
        initComponents();
        try {
            viewSupplier();
        } catch (SQLException ex) {
            Logger.getLogger(SupplierManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addSupplier() {
        try {
            if ((!txtName.getText().isEmpty())
                    && (!txtAddress.getText().isEmpty())
                    && (!txtPhone.getText().isEmpty())
                    && (!txtMail.getText().isEmpty())) {

                int id = 0;
                String SupplierName = txtName.getText();
                String Address = txtAddress.getText();
                String Phone = txtPhone.getText();
                String Email = txtMail.getText();
                String Category = (String) ComboCategory.getSelectedItem();

                Supplier supplier = new Supplier(id, SupplierName, Address, Phone, Category, Email);

                SupplierService service1 = new SupplierService();
                service1.AddSupplier(supplier);

                JOptionPane.showMessageDialog(rootPane, "Submitted Successfully");

            } else {
                throw new InvalidInputException();
            }
        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 0);
        }
    }

    public void updateSupplier() {
        try {
            if ((!txtUName.getText().isEmpty())
                    && (!txtUSuppID.getText().isEmpty())
                    && (!txtUPhone.getText().isEmpty())
                    && (!txtUAddress.getText().isEmpty())
                    && (!txtUmail.getText().isEmpty())) {

                int SupplierID = Integer.parseInt(txtUSuppID.getText());

                String Name = txtUName.getText();
                String Category = (String) ComboUCategory.getSelectedItem();
                String Phone = txtUPhone.getText();
                String Address = txtUAddress.getText();
                String Email = txtUmail.getText();
       

                Supplier supplier = new Supplier(SupplierID, Name, Address, Phone, Category, Email);

                UpdateSupplierService service1 = new UpdateSupplierService();
                service1.UpdateSupplier(supplier, SupplierID);

                JOptionPane.showMessageDialog(rootPane, "Updated Successfully");

            } else {
                throw new InvalidInputException();
            }
        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 0);
        }
    }

    public void deleteSupplier() {
        try {

            if (!txtDSuppID.getText().isEmpty()) {

                int SupplierID = Integer.parseInt(txtDSuppID.getText());

                DeleteSupplierService delservice = new DeleteSupplierService();
                delservice.DeleteSupplier(SupplierID);

                JOptionPane.showMessageDialog(rootPane, "Deleted Successfully");

            } else {
                throw new InvalidInputException();
            }

        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "error", 0);
        }
    }

    public ArrayList<Supplier> supplierList() throws SQLException {
        ArrayList<Supplier> supplierList = new ArrayList<>();

        try {
            String URL = "jdbc:sqlserver://localhost:1433;databaseName=InventoryDBB;user=sa;password=sa123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(URL);

            String query1 = "select * from SupplierTable";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query1);
            Supplier supplier;
            while (rs.next()) {
                supplier = new Supplier(rs.getInt("SupplierID"), rs.getString("Name"), rs.getString("Address"), rs.getString("Telephone"), rs.getString("Email"), rs.getString("Category"));
                supplierList.add(supplier);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return supplierList;
    }

    public void viewSupplier() throws SQLException {
        ArrayList<Supplier> list = supplierList();
        DefaultTableModel model = (DefaultTableModel) jTable_view_supplier.getModel();
        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getSupplierID();
            row[1] = list.get(i).getSupplierName();
            row[2] = list.get(i).getAddress();
            row[3] = list.get(i).getTelephone();
            row[4] = list.get(i).getEmail();
            row[5] = list.get(i).getCategory();
            model.addRow(row);
        }
    }

    public ArrayList<Supplier> searchList() throws SQLException {
        ArrayList<Supplier> searchList = new ArrayList<>();

        try {
            String URL = "jdbc:sqlserver://localhost:1433;databaseName=InventoryDBB;user=sa;password=sa123";
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            Connection conn = DriverManager.getConnection(URL);
            String word = txtSearch.getText();
            String query1 = "select * from SupplierTable where Name = '" + word + "' or Address = '" + word + "' or Telephone = '" + word + "' or Email = '" + word + "' or Category = '" + word + "'";
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query1);
            Supplier supplier;
            while (rs.next()) {
                supplier = new Supplier(rs.getInt("SupplierID"), rs.getString("Name"), rs.getString("Address"), rs.getString("Telephone"), rs.getString("Email"), rs.getString("Category"));
                searchList.add(supplier);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return searchList;
    }

    public void viewSearchSupplier() throws SQLException {
        ArrayList<Supplier> list = searchList();
        DefaultTableModel model = (DefaultTableModel) jTable_view_supplier.getModel();
        Object[] row = new Object[6];
        for (int i = 0; i < list.size(); i++) {
            row[0] = list.get(i).getSupplierID();
            row[1] = list.get(i).getSupplierName();
            row[2] = list.get(i).getAddress();
            row[3] = list.get(i).getTelephone();
            row[4] = list.get(i).getEmail();
            row[5] = list.get(i).getCategory();
            model.addRow(row);
        }
    }

    public void sendEmail() {
        String To_Recieve = txtMSuppMail.getText();
        String Subject = txtMSubject.getText();
        String Text_content = txtMBody.getText();

        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.port", "587");

        Session session = Session.getDefaultInstance(props,
                new javax.mail.Authenticator() {

            @Override
            protected PasswordAuthentication getPasswordAuthentication() {

                return new PasswordAuthentication("newwasanthahotel@gmail.com", "wasantha99");

            }

        }
        );

        try {

            Message message = new MimeMessage(session);

            message.setFrom(new InternetAddress("newwasanthahotel@gmail.com"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(To_Recieve));
            message.setSubject(Subject);
            message.setText(Text_content);

            MimeBodyPart messagebodypart = new MimeBodyPart();

            messagebodypart.setText(Text_content);

            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(messagebodypart);

            messagebodypart = new MimeBodyPart();
//            javax.activation.DataSource source = new FileDataSource(attachementpath);

//            messagebodypart.setDataHandler(new DataHandler(source));
//            messagebodypart.setFileName(txtAtachmentNamee.getText());
            multipart.addBodyPart(messagebodypart);
            Transport.send(message);
            message.setContent(multipart);

            JOptionPane.showMessageDialog(null, "message Sent");

        } catch (Exception ex) {

            JOptionPane.showMessageDialog(null, ex);
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

        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_view_supplier = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txtSearch = new javax.swing.JTextField();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        txtDSuppID = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        ibiDValidate = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        txtName = new javax.swing.JTextField();
        txtAddress = new javax.swing.JTextField();
        txtPhone = new javax.swing.JTextField();
        txtMail = new javax.swing.JTextField();
        ComboCategory = new javax.swing.JComboBox<>();
        jButton5 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        ibiValidate = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        txtUName = new javax.swing.JTextField();
        txtUAddress = new javax.swing.JTextField();
        txtUPhone = new javax.swing.JTextField();
        txtUmail = new javax.swing.JTextField();
        ComboUCategory = new javax.swing.JComboBox<>();
        jButton6 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txtUSuppID = new javax.swing.JTextField();
        jButton10 = new javax.swing.JButton();
        ibiUValidate = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        txtMSuppMail = new javax.swing.JTextField();
        txtMSubject = new javax.swing.JTextField();
        jButton7 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        txtMBody = new javax.swing.JTextArea();
        jButton11 = new javax.swing.JButton();
        ibiMValidate = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Supplier Management");
        setMaximumSize(new java.awt.Dimension(1200, 650));
        setMinimumSize(new java.awt.Dimension(1200, 650));
        setPreferredSize(new java.awt.Dimension(1200, 650));

        jPanel5.setBackground(new java.awt.Color(31, 83, 136));
        jPanel5.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel5.setPreferredSize(new java.awt.Dimension(760, 650));
        jPanel5.setLayout(null);

        jTable_view_supplier.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Supplier ID", "Name", "Address", "Telephone", "Category", "Email"
            }
        ));
        jTable_view_supplier.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable_view_supplierMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(jTable_view_supplier);

        jPanel5.add(jScrollPane2);
        jScrollPane2.setBounds(10, 70, 732, 460);

        jLabel4.setFont(new java.awt.Font("Sitka Heading", 1, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("View Suppliers");
        jPanel5.add(jLabel4);
        jLabel4.setBounds(275, 23, 170, 44);

        jLabel5.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("Search");
        jPanel5.add(jLabel5);
        jLabel5.setBounds(34, 559, 41, 15);

        txtSearch.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtSearch.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtSearchKeyPressed(evt);
            }
        });
        jPanel5.add(txtSearch);
        txtSearch.setBounds(132, 558, 169, 21);

        jButton3.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/preferences-system-search-icon (1).png"))); // NOI18N
        jButton3.setText("Search");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton3);
        jButton3.setBounds(371, 553, 100, 33);

        jButton4.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton4.setText("View Suppliers");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        jPanel5.add(jButton4);
        jButton4.setBounds(621, 553, 115, 30);

        jPanel1.setBackground(new java.awt.Color(31, 83, 136));
        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel1.setPreferredSize(new java.awt.Dimension(298, 250));
        jPanel1.setLayout(null);

        jLabel2.setFont(new java.awt.Font("Sitka Heading", 1, 22)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Delete Supplier");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(153, 15, 160, 29);

        jLabel3.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Supplier ID");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(52, 86, 61, 15);

        jButton1.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/Status-dialog-error-icon (1).png"))); // NOI18N
        jButton1.setText("Delete Supplier");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(190, 138, 150, 33);

        txtDSuppID.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtDSuppID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDSuppIDKeyTyped(evt);
            }
        });
        jPanel1.add(txtDSuppID);
        txtDSuppID.setBounds(156, 83, 184, 21);

        jButton2.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/back-icon.png"))); // NOI18N
        jButton2.setText("Home");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(305, 185, 100, 33);

        ibiDValidate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ibiDValidate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(ibiDValidate);
        ibiDValidate.setBounds(85, 138, 120, 23);

        jTabbedPane1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jTabbedPane1.setPreferredSize(new java.awt.Dimension(462, 370));

        jPanel2.setBackground(new java.awt.Color(31, 83, 136));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel2.setLayout(null);

        jLabel7.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Name");
        jPanel2.add(jLabel7);
        jLabel7.setBounds(30, 27, 33, 15);

        jLabel8.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Category");
        jPanel2.add(jLabel8);
        jLabel8.setBounds(30, 163, 51, 15);

        jLabel9.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("Address");
        jPanel2.add(jLabel9);
        jLabel9.setBounds(30, 61, 48, 15);

        jLabel10.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("Telephone");
        jPanel2.add(jLabel10);
        jLabel10.setBounds(30, 95, 59, 15);

        jLabel11.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Email");
        jPanel2.add(jLabel11);
        jLabel11.setBounds(30, 129, 31, 15);

        txtName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtNameActionPerformed(evt);
            }
        });
        jPanel2.add(txtName);
        txtName.setBounds(156, 24, 200, 21);

        txtAddress.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jPanel2.add(txtAddress);
        txtAddress.setBounds(156, 58, 200, 21);

        txtPhone.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtPhoneKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtPhoneKeyTyped(evt);
            }
        });
        jPanel2.add(txtPhone);
        txtPhone.setBounds(156, 92, 200, 21);

        txtMail.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtMail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtMailKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMailKeyTyped(evt);
            }
        });
        jPanel2.add(txtMail);
        txtMail.setBounds(156, 126, 200, 21);

        ComboCategory.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ComboCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kitchen", "Tea Pantry", "Grocery", "Soft-Drinks" }));
        jPanel2.add(ComboCategory);
        ComboCategory.setBounds(156, 160, 200, 21);

        jButton5.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/math-add-icon (1).png"))); // NOI18N
        jButton5.setText("Add Supplier");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton5);
        jButton5.setBounds(216, 238, 140, 33);

        jButton9.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton9.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/Delete-icon (1).png"))); // NOI18N
        jButton9.setText("Clear");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        jPanel2.add(jButton9);
        jButton9.setBounds(30, 238, 100, 33);

        ibiValidate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ibiValidate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel2.add(ibiValidate);
        ibiValidate.setBounds(236, 210, 120, 21);

        jTabbedPane1.addTab("Add Supplier", jPanel2);

        jPanel3.setBackground(new java.awt.Color(31, 83, 136));
        jPanel3.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel3.setLayout(null);

        jLabel13.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setText("Name");
        jPanel3.add(jLabel13);
        jLabel13.setBounds(28, 55, 33, 15);

        jLabel14.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setText("Category");
        jPanel3.add(jLabel14);
        jLabel14.setBounds(28, 190, 51, 15);

        jLabel15.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setText("Address");
        jPanel3.add(jLabel15);
        jLabel15.setBounds(28, 89, 48, 15);

        jLabel16.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Telephone");
        jPanel3.add(jLabel16);
        jLabel16.setBounds(28, 123, 59, 15);

        jLabel17.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Email");
        jPanel3.add(jLabel17);
        jLabel17.setBounds(28, 157, 31, 15);

        txtUName.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUName.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtUNameActionPerformed(evt);
            }
        });
        jPanel3.add(txtUName);
        txtUName.setBounds(152, 52, 200, 21);

        txtUAddress.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jPanel3.add(txtUAddress);
        txtUAddress.setBounds(152, 86, 200, 21);

        txtUPhone.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUPhone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUPhoneKeyTyped(evt);
            }
        });
        jPanel3.add(txtUPhone);
        txtUPhone.setBounds(152, 120, 200, 21);

        txtUmail.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUmail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUmailKeyTyped(evt);
            }
        });
        jPanel3.add(txtUmail);
        txtUmail.setBounds(152, 154, 200, 21);

        ComboUCategory.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        ComboUCategory.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Kitchen", "Tea Pantry", "Grocery", "Soft-Drinks" }));
        jPanel3.add(ComboUCategory);
        ComboUCategory.setBounds(152, 187, 200, 21);

        jButton6.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/math-add-icon (1).png"))); // NOI18N
        jButton6.setText("Update Supplier");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton6);
        jButton6.setBounds(210, 254, 150, 33);

        jLabel1.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Supplier ID");
        jPanel3.add(jLabel1);
        jLabel1.setBounds(28, 21, 70, 15);

        txtUSuppID.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtUSuppID.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUSuppIDKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtUSuppIDKeyTyped(evt);
            }
        });
        jPanel3.add(txtUSuppID);
        txtUSuppID.setBounds(152, 18, 200, 21);

        jButton10.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/Delete-icon (1).png"))); // NOI18N
        jButton10.setText("Clear");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        jPanel3.add(jButton10);
        jButton10.setBounds(28, 254, 100, 33);

        ibiUValidate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ibiUValidate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.add(ibiUValidate);
        ibiUValidate.setBounds(232, 226, 120, 21);

        jTabbedPane1.addTab("Update Supplier", jPanel3);

        jPanel4.setBackground(new java.awt.Color(31, 83, 136));
        jPanel4.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        jPanel4.setLayout(null);

        jLabel19.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(255, 255, 255));
        jLabel19.setText("Supplier Mail");
        jPanel4.add(jLabel19);
        jLabel19.setBounds(25, 18, 80, 15);

        jLabel21.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("Subject");
        jPanel4.add(jLabel21);
        jLabel21.setBounds(25, 52, 43, 15);

        jLabel22.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setText("Body");
        jPanel4.add(jLabel22);
        jLabel22.setBounds(25, 88, 28, 15);

        txtMSuppMail.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtMSuppMail.setRequestFocusEnabled(false);
        txtMSuppMail.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMSuppMailActionPerformed(evt);
            }
        });
        txtMSuppMail.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtMSuppMailKeyTyped(evt);
            }
        });
        jPanel4.add(txtMSuppMail);
        txtMSuppMail.setBounds(158, 15, 200, 21);

        txtMSubject.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtMSubject.setRequestFocusEnabled(false);
        txtMSubject.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtMSubjectActionPerformed(evt);
            }
        });
        jPanel4.add(txtMSubject);
        txtMSubject.setBounds(158, 49, 200, 21);

        jButton7.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/email-2-icon (2).png"))); // NOI18N
        jButton7.setText("Send");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton7);
        jButton7.setBounds(268, 244, 90, 33);

        txtMBody.setColumns(20);
        txtMBody.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        txtMBody.setRows(5);
        jScrollPane1.setViewportView(txtMBody);

        jPanel4.add(jScrollPane1);
        jScrollPane1.setBounds(158, 88, 200, 138);

        jButton11.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/Delete-icon (1).png"))); // NOI18N
        jButton11.setText("Clear");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        jPanel4.add(jButton11);
        jButton11.setBounds(49, 244, 91, 33);

        ibiMValidate.setFont(new java.awt.Font("Arial", 1, 12)); // NOI18N
        ibiMValidate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel4.add(ibiMValidate);
        ibiMValidate.setBounds(172, 244, 120, 27);

        jTabbedPane1.addTab("Send Messages", jPanel4);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 274, Short.MAX_VALUE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtNameActionPerformed

    private void txtMSuppMailActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMSuppMailActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMSuppMailActionPerformed

    private void txtUNameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtUNameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtUNameActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        ManagerHomePage mh = new ManagerHomePage();
        setVisible(false);
        mh.setVisible(true);
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        // TODO add your handling code here:
        txtName.setText("");
        txtAddress.setText("");
        txtPhone.setText("");
        txtMail.setText("");
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        txtUSuppID.setText("");
        txtUName.setText("");
        txtUAddress.setText("");
        txtUPhone.setText("");
        txtUmail.setText("");
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        txtMSuppMail.setText("");
        txtMSubject.setText("");
        txtMBody.setText("");
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        try {
            // TODO add your handling code here:
            DefaultTableModel model = (DefaultTableModel) jTable_view_supplier.getModel();
            model.setRowCount(0);
            viewSearchSupplier();
        } catch (SQLException ex) {
            Logger.getLogger(SupplierManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        sendEmail();
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        try {
            // TODO add your handling code here:
            addSupplier();
            DefaultTableModel model = (DefaultTableModel) jTable_view_supplier.getModel();
            model.setRowCount(0);
            viewSupplier();
        } catch (SQLException ex) {
            Logger.getLogger(SupplierManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        try {
            // TODO add your handling code here:
            updateSupplier();
            DefaultTableModel model = (DefaultTableModel) jTable_view_supplier.getModel();
            model.setRowCount(0);
            viewSupplier();
        } catch (SQLException ex) {
            Logger.getLogger(SupplierManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            // TODO add your handling code here:
            deleteSupplier();
            DefaultTableModel model = (DefaultTableModel) jTable_view_supplier.getModel();
            model.setRowCount(0);
            viewSupplier();
        } catch (SQLException ex) {
            Logger.getLogger(SupplierManagement.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTable_view_supplierMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable_view_supplierMouseClicked
        // TODO add your handling code here:
        DefaultTableModel model = (DefaultTableModel) jTable_view_supplier.getModel();
        int selectedRowIndex = jTable_view_supplier.getSelectedRow();

        txtUSuppID.setText(model.getValueAt(selectedRowIndex, 0).toString());
        txtUName.setText(model.getValueAt(selectedRowIndex, 1).toString());
        txtUAddress.setText(model.getValueAt(selectedRowIndex, 2).toString());
        txtUPhone.setText(model.getValueAt(selectedRowIndex, 3).toString());
        txtUmail.setText(model.getValueAt(selectedRowIndex, 4).toString());

        txtMSuppMail.setText(model.getValueAt(selectedRowIndex, 5).toString());
    }//GEN-LAST:event_jTable_view_supplierMouseClicked

    private void txtSearchKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtSearchKeyPressed
        // TODO add your handling code here:
//        DefaultTableModel model = (DefaultTableModel) jTable_view_supplier.getModel();
//        String search = txtSearch.getText();
//        TableRowSorter<DefaultTableModel> tr = new TableRowSorter<DefaultTableModel>(model);
//        jTable_view_supplier.setRowSorter(tr);
//        tr.setRowFilter(RowFilter.regexFilter(search));
    }//GEN-LAST:event_txtSearchKeyPressed

    private void txtPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txtPhoneKeyTyped

    private void txtMailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMailKeyTyped
        // TODO add your handling code here:
        String ptt = "^[a-z0-9]+[@]{1}+[a-z]+[.]{1}+[a-z]{3}+$";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtMail.getText());
        if (!match.matches()) {
            ibiValidate.setText("Invalid mail");
        } else {
            ibiValidate.setText(null);
        }
    }//GEN-LAST:event_txtMailKeyTyped

    private void txtDSuppIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDSuppIDKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,10}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtDSuppID.getText());
        if (!match.matches()) {
            ibiDValidate.setText("Invalid SuppID");
        } else {
            ibiDValidate.setText(null);
        }
    }//GEN-LAST:event_txtDSuppIDKeyTyped

    private void txtUSuppIDKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUSuppIDKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txtUSuppIDKeyTyped

    private void txtUPhoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUPhoneKeyTyped
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,10}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtUPhone.getText());
        if (!match.matches()) {
            ibiUValidate.setText("Invalid Phone no");
        } else {
            ibiUValidate.setText(null);
        }
    }//GEN-LAST:event_txtUPhoneKeyTyped

    private void txtUmailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUmailKeyTyped
        // TODO add your handling code here:
        String ptt = "^[a-z0-9]+[@]{1}+[a-z]+[.]{1}+[a-z]{3}+$";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtUmail.getText());
        if (!match.matches()) {
            ibiUValidate.setText("Invalid mail");
        } else {
            ibiUValidate.setText(null);
        }
    }//GEN-LAST:event_txtUmailKeyTyped

    private void txtMSuppMailKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMSuppMailKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txtMSuppMailKeyTyped

    private void txtMailKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtMailKeyReleased
        // TODO add your handling code here:
        String ptt = "^[a-z0-9]+[@]{1}+[a-z]+[.]{1}+[a-z]{3}+$";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtMail.getText());
        if (!match.matches()) {
            ibiMValidate.setText("Invalid mail");
        } else {
            ibiMValidate.setText(null);
        }
    }//GEN-LAST:event_txtMailKeyReleased

    private void txtPhoneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtPhoneKeyReleased
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,10}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtPhone.getText());
        if (!match.matches()) {
            ibiValidate.setText("Invalid Phone no");
        } else {
            ibiValidate.setText(null);
        }
    }//GEN-LAST:event_txtPhoneKeyReleased

    private void txtUSuppIDKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUSuppIDKeyReleased
        // TODO add your handling code here:
        String ptt = "^[0-9]{0,10}";
        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtUSuppID.getText());
        if (!match.matches()) {
            ibiUValidate.setText("Invalid SuppID");
        } else {
            ibiUValidate.setText(null);
        }
    }//GEN-LAST:event_txtUSuppIDKeyReleased

    private void txtMSubjectActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtMSubjectActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtMSubjectActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        try {
            // TODO add your handling code here:
            DefaultTableModel model = (DefaultTableModel) jTable_view_supplier.getModel();
            model.setRowCount(0);
            viewSupplier();
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
            java.util.logging.Logger.getLogger(SupplierManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SupplierManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SupplierManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SupplierManagement.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SupplierManagement().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> ComboCategory;
    private javax.swing.JComboBox<String> ComboUCategory;
    private javax.swing.JLabel ibiDValidate;
    private javax.swing.JLabel ibiMValidate;
    private javax.swing.JLabel ibiUValidate;
    private javax.swing.JLabel ibiValidate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable_view_supplier;
    private javax.swing.JTextField txtAddress;
    private javax.swing.JTextField txtDSuppID;
    private javax.swing.JTextArea txtMBody;
    private javax.swing.JTextField txtMSubject;
    private javax.swing.JTextField txtMSuppMail;
    private javax.swing.JTextField txtMail;
    private javax.swing.JTextField txtName;
    private javax.swing.JTextField txtPhone;
    private javax.swing.JTextField txtSearch;
    private javax.swing.JTextField txtUAddress;
    private javax.swing.JTextField txtUName;
    private javax.swing.JTextField txtUPhone;
    private javax.swing.JTextField txtUSuppID;
    private javax.swing.JTextField txtUmail;
    // End of variables declaration//GEN-END:variables
}
