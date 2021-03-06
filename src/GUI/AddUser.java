/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import javax.swing.JOptionPane;
import Model.User;
import ServiceLayer.UserServices;
import Model.InvalidInputException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.Timer;

/**
 *
 * @author Stefan
 */
public class AddUser extends javax.swing.JFrame {

    /**
     * Creates new form AddUser
     */
    public AddUser() {
        initComponents();
        showDate();
        showTime();
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        txtfirstname = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtlastname = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtusername = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1_usertype = new javax.swing.JComboBox<>();
        txtconfirmpassword = new javax.swing.JPasswordField();
        txtpassword = new javax.swing.JPasswordField();
        ibiValidate = new javax.swing.JLabel();
        lblDate = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Add New User");
        setMaximumSize(new java.awt.Dimension(804, 600));
        setMinimumSize(new java.awt.Dimension(804, 600));
        setPreferredSize(new java.awt.Dimension(804, 600));
        setSize(new java.awt.Dimension(804, 600));
        getContentPane().setLayout(null);

        jPanel1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jPanel1.setMaximumSize(new java.awt.Dimension(804, 600));
        jPanel1.setMinimumSize(new java.awt.Dimension(804, 600));
        jPanel1.setLayout(null);

        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("First Name");
        jPanel1.add(jLabel2);
        jLabel2.setBounds(178, 192, 100, 16);

        txtfirstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtfirstnameActionPerformed(evt);
            }
        });
        jPanel1.add(txtfirstname);
        txtfirstname.setBounds(320, 189, 284, 22);

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Last Name");
        jPanel1.add(jLabel3);
        jLabel3.setBounds(178, 236, 100, 16);
        jPanel1.add(txtlastname);
        txtlastname.setBounds(320, 233, 284, 22);

        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("User Name");
        jPanel1.add(jLabel5);
        jLabel5.setBounds(178, 324, 100, 16);

        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("Password");
        jPanel1.add(jLabel6);
        jLabel6.setBounds(178, 368, 100, 16);

        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("Confirm Password");
        jPanel1.add(jLabel7);
        jLabel7.setBounds(178, 412, 130, 16);

        txtusername.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtusernameActionPerformed(evt);
            }
        });
        txtusername.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtusernameKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtusernameKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtusernameKeyTyped(evt);
            }
        });
        jPanel1.add(txtusername);
        txtusername.setBounds(320, 321, 284, 22);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/math-add-icon.png"))); // NOI18N
        jButton1.setText("Register");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton1);
        jButton1.setBounds(465, 490, 140, 50);

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/Delete-icon (2).png"))); // NOI18N
        jButton2.setText("Clear");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton2);
        jButton2.setBounds(165, 490, 120, 50);

        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("User Type");
        jPanel1.add(jLabel4);
        jLabel4.setBounds(178, 280, 110, 16);

        jComboBox1_usertype.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Owner", "Manager" }));
        jPanel1.add(jComboBox1_usertype);
        jComboBox1_usertype.setBounds(320, 277, 284, 22);

        txtconfirmpassword.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtconfirmpasswordKeyTyped(evt);
            }
        });
        jPanel1.add(txtconfirmpassword);
        txtconfirmpassword.setBounds(320, 409, 284, 22);
        jPanel1.add(txtpassword);
        txtpassword.setBounds(320, 365, 284, 22);

        ibiValidate.setForeground(new java.awt.Color(255, 255, 255));
        jPanel1.add(ibiValidate);
        ibiValidate.setBounds(506, 477, 98, 27);

        lblDate.setForeground(new java.awt.Color(255, 255, 255));
        lblDate.setText("jLabel3");
        jPanel1.add(lblDate);
        lblDate.setBounds(700, 20, 70, 16);

        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setText("jLabel4");
        jPanel1.add(lblTime);
        lblTime.setBounds(700, 60, 70, 16);

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Add User");
        jPanel1.add(jLabel8);
        jLabel8.setBounds(320, 110, 140, 60);

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/back-icon.png"))); // NOI18N
        jButton3.setText(" Back");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        jPanel1.add(jButton3);
        jButton3.setBounds(679, 545, 110, 30);

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GUI/images/bg2.jpg"))); // NOI18N
        jLabel1.setMaximumSize(new java.awt.Dimension(804, 600));
        jLabel1.setMinimumSize(new java.awt.Dimension(804, 600));
        jLabel1.setPreferredSize(new java.awt.Dimension(804, 600));
        jLabel1.setVerifyInputWhenFocusTarget(false);
        jPanel1.add(jLabel1);
        jLabel1.setBounds(0, 0, 804, 600);

        getContentPane().add(jPanel1);
        jPanel1.setBounds(0, 0, 804, 600);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtconfirmpasswordKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtconfirmpasswordKeyTyped
        // TODO add your handling code here:
        if(txtpassword.getText().equals(txtconfirmpassword.getText())){
            ibiValidate.setText("Invalid Confirmation");
        }
    }//GEN-LAST:event_txtconfirmpasswordKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        txtfirstname.setText("");
        txtlastname.setText("");
        jComboBox1_usertype.setSelectedItem(" ");
        txtusername.setText("");
        txtpassword.setText("");
        txtconfirmpassword.setText("");
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        try {
            if ((!txtfirstname.getText().isEmpty())
                && (!txtlastname.getText().isEmpty())
                && (!txtusername.getText().isEmpty())
                && (!txtpassword.getText().isEmpty())
                && (!txtconfirmpassword.getText().isEmpty())) {

                String FirstName = txtfirstname.getText();
                String LastName = txtlastname.getText();
                String Username = txtusername.getText();
                String Password = txtpassword.getText();
                String UserType = (String) jComboBox1_usertype.getSelectedItem();
                String Date = lblDate.getText();
                String Time = lblTime.getText();

                if (txtpassword.getText().equals(txtconfirmpassword.getText())) {
                    User user = new User(0, FirstName, LastName, UserType, Username, Password, Date, Time);
                    UserServices service1 = new UserServices();
                    service1.AddUser(user);

                    JOptionPane.showMessageDialog(rootPane, "Registered Successfully");
                }else{
                    JOptionPane.showMessageDialog(rootPane, "Password miss-matched");
                }

            } else {
                throw new InvalidInputException();
            }
        } catch (InvalidInputException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage(), "Error", 0);
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void txtusernameKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusernameKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_txtusernameKeyTyped

    private void txtusernameKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusernameKeyReleased
        // TODO add your handling code here:
        String ptt = "^[a-z0-9]+[@]{1}+[a-z]+[.]{1}+[a-z]{3}+$";

        Pattern ptn = Pattern.compile(ptt);
        Matcher match = ptn.matcher(txtusername.getText());
        if (!match.matches()) {
            ibiValidate.setText("Invalid username");
        }else{
            ibiValidate.setText("");
        }
    }//GEN-LAST:event_txtusernameKeyReleased

    private void txtusernameKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtusernameKeyPressed
        // TODO add your handling code here:

    }//GEN-LAST:event_txtusernameKeyPressed

    private void txtusernameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtusernameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtusernameActionPerformed

    private void txtfirstnameActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtfirstnameActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtfirstnameActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        Options o = new Options();
        setVisible(false);
        o.setVisible(true);
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(AddUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AddUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AddUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AddUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AddUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel ibiValidate;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox<String> jComboBox1_usertype;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JLabel lblDate;
    private javax.swing.JLabel lblTime;
    private javax.swing.JPasswordField txtconfirmpassword;
    private javax.swing.JTextField txtfirstname;
    private javax.swing.JTextField txtlastname;
    private javax.swing.JPasswordField txtpassword;
    private javax.swing.JTextField txtusername;
    // End of variables declaration//GEN-END:variables
}
