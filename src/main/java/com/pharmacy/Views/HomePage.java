package com.pharmacy.Views;

import com.pharmacy.Controllers.UserController;
import com.pharmacy.Models.UserModel;

import javax.swing.JOptionPane;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

// Welcome page for the application
public class HomePage extends javax.swing.JPanel {

    ResultSet resultSet = null;
    UserModel userModel;

    String username;

    /**
     * Creates new form HomePage
     */
    public HomePage(String username) {
        this.username = username;
        userModel = new UserModel();

        initComponents();

        getUser();
        greetUser(userModel.getName());
        setupDateTime();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        entryPanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        phoneText = new javax.swing.JTextField();
        usernameText = new javax.swing.JTextField();
        updateButton = new javax.swing.JButton();
        changePassword = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jSeparator1 = new javax.swing.JSeparator();
        timeLabel = new javax.swing.JLabel();
        welcomeLabel = new javax.swing.JLabel();
        dateLabel = new javax.swing.JLabel();

        entryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Your Details"));
        entryPanel.setToolTipText("");

        jLabel4.setText("Contact:");

        jLabel5.setText("Username:");

        jLabel6.setText("Password:");

        updateButton.setFont(new java.awt.Font("Liberation Sans", 0, 15)); // NOI18N
        updateButton.setText("Update");
        updateButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        changePassword.setText("Change Password");
        changePassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changePasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout entryPanelLayout = new javax.swing.GroupLayout(entryPanel);
        entryPanel.setLayout(entryPanelLayout);
        entryPanelLayout.setHorizontalGroup(
            entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(entryPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator3)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, entryPanelLayout.createSequentialGroup()
                            .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, entryPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(phoneText, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, entryPanelLayout.createSequentialGroup()
                                    .addGap(0, 0, Short.MAX_VALUE)
                                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(entryPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel5)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addComponent(usernameText, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(entryPanelLayout.createSequentialGroup()
                                            .addGap(1, 1, 1)
                                            .addComponent(jLabel6)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                            .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(entryPanelLayout.createSequentialGroup()
                                                    .addGap(0, 0, Short.MAX_VALUE)
                                                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addComponent(changePassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                            .addContainerGap())))
        );
        entryPanelLayout.setVerticalGroup(
            entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(entryPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(phoneText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(usernameText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(changePassword, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );

        timeLabel.setFont(new java.awt.Font("Liberation Sans", 1, 28)); // NOI18N
        timeLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        timeLabel.setText("Time");

        welcomeLabel.setFont(new java.awt.Font("Liberation Sans", 1, 36)); // NOI18N
        welcomeLabel.setText("Good Morning, ");
        welcomeLabel.setVerifyInputWhenFocusTarget(false);

        dateLabel.setText("Date");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(28, 28, 28)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(welcomeLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGap(71, 71, 71)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(dateLabel)
                                .addComponent(timeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 257, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGap(18, 18, 18))
                        .addComponent(jSeparator1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(entryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 624, Short.MAX_VALUE)))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(16, 16, 16)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(welcomeLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(timeLabel)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(dateLabel)))
                    .addGap(18, 18, 18)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addComponent(entryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        UserModel updatedUserModel = new UserModel();

        // check if a row is selected
        if (phoneText.getText().equals("") || usernameText.getText().equals("")) {
            JOptionPane.showMessageDialog(this, "Please fill all the required fields.", "Error", JOptionPane.ERROR_MESSAGE);
        } else {
            updatedUserModel.setId(userModel.getId());
            updatedUserModel.setName(userModel.getName());
            updatedUserModel.setType(userModel.getType());
            updatedUserModel.setPhone(phoneText.getText());
            updatedUserModel.setUsername(usernameText.getText());

            EventQueue.invokeLater(() -> {
                new UserController().updateUser(updatedUserModel);
            });
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void changePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasswordActionPerformed
        String verify = JOptionPane.showInputDialog(this, "Enter current password for " + username + ":", "Change Password", JOptionPane.PLAIN_MESSAGE);

        // Check if the current password matches the one in the database
        EventQueue.invokeLater(() -> {
            // wait cursor
            setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

            if (verify != null) {
                // password contains the decrypted password
                boolean match = new UserController().matchPasswords(username, verify);

                // if matches, change password to the new one
                if (match) {
                    String newPassword = JOptionPane.showInputDialog(this, "Enter new password for " + username + ":", "Change Password", JOptionPane.PLAIN_MESSAGE);

                    if (newPassword != null) {
                        new UserController().updatePass(userModel.getId(), newPassword);
                        JOptionPane.showMessageDialog(this, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Incorrect password.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            // revert cursor
            setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        });
    }//GEN-LAST:event_changePasswordActionPerformed

    private void getUser() {
        try {
            resultSet = new UserController().findUser(username);

            if (resultSet.next()) {
                // save to user model
                userModel.setId(resultSet.getInt("id"));
                userModel.setUsername(resultSet.getString("username"));
                userModel.setName(resultSet.getString("name"));
                userModel.setPhone(resultSet.getString("phone"));
                userModel.setType(resultSet.getString("user_type"));

                // set to text fields
                usernameText.setText(userModel.getUsername());
                phoneText.setText(userModel.getPhone());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setupDateTime() {
        // set date and time
        new Thread(() -> {
            while (true) {
                timeLabel.setText(new SimpleDateFormat("hh:mm a").format(new Date()));
                dateLabel.setText(new SimpleDateFormat("EEE, MMMM dd, yyyy").format(new Date()));
            }
        }).start();
    }

    /**
     * Greet the user based on the time of the day
     *
     * @param name name of the current user
     */
    private void greetUser(String name) {
        // say good morning or good evening
        Calendar cal = Calendar.getInstance();
        int timeOfDay = cal.get(Calendar.HOUR_OF_DAY);

        if (timeOfDay < 12) {
            welcomeLabel.setText("Good Morning, " + name);
        } else if (timeOfDay < 16) {
            welcomeLabel.setText("Good Afternoon, " + name);
        } else {
            welcomeLabel.setText("Good Evening, " + name);
        }
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton changePassword;
    private javax.swing.JLabel dateLabel;
    private javax.swing.JPanel entryPanel;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField phoneText;
    private javax.swing.JLabel timeLabel;
    private javax.swing.JButton updateButton;
    private javax.swing.JTextField usernameText;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables
}
