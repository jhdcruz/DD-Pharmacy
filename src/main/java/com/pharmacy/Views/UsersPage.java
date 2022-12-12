package com.pharmacy.Views;

import com.pharmacy.Controllers.UserController;
import com.pharmacy.Models.UserModel;
import com.pharmacy.Views.Dialogs.AddUserDialog;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.EventQueue;
import java.sql.SQLException;

public class UsersPage extends javax.swing.JPanel {

    public UsersPage() {
        initComponents();
        loadDataSet();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        entryPanel = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        nameText = new javax.swing.JTextField();
        phoneText = new javax.swing.JTextField();
        usernameText = new javax.swing.JTextField();
        userTypeCombo = new javax.swing.JComboBox<>();
        updateButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        changePassword = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        userTable = new javax.swing.JTable();
        jLabel8 = new javax.swing.JLabel();
        searchText = new javax.swing.JTextField();
        refreshButton = new javax.swing.JButton();
        addUserButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();

        jLabel1.setFont(new java.awt.Font("Impact", 1, 24)); // NOI18N
        jLabel1.setText("USERS");

        entryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Enter User Details"));

        jLabel2.setText("Full Name:");

        jLabel4.setText("Contact:");

        jLabel5.setText("Username:");

        jLabel6.setText("Password:");

        userTypeCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Administrator", "Employee"}));

        updateButton.setFont(new java.awt.Font("Liberation Sans", 0, 15)); // NOI18N
        updateButton.setText("Update");
        updateButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        deleteButton.setBackground(new java.awt.Color(255, 51, 51));
        deleteButton.setForeground(new java.awt.Color(51, 51, 51));
        deleteButton.setText("Delete");
        deleteButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
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
                        .addGroup(entryPanelLayout.createSequentialGroup()
                            .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(entryPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(23, 23, 23)
                                    .addComponent(nameText))
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, entryPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel5)
                                    .addGap(23, 23, 23)
                                    .addComponent(usernameText))
                                .addGroup(entryPanelLayout.createSequentialGroup()
                                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(entryPanelLayout.createSequentialGroup()
                                            .addComponent(jLabel6)
                                            .addGap(23, 23, 23))
                                        .addGroup(entryPanelLayout.createSequentialGroup()
                                            .addGap(0, 0, Short.MAX_VALUE)
                                            .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(changePassword, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(userTypeCombo, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGroup(entryPanelLayout.createSequentialGroup()
                                    .addComponent(jLabel4)
                                    .addGap(23, 23, 23)
                                    .addComponent(phoneText)))
                            .addGap(4, 4, 4))
                        .addComponent(jSeparator3)))
        );

        entryPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[]{jLabel2, jLabel4, jLabel5, jLabel6});

        entryPanelLayout.setVerticalGroup(
            entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(entryPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                    .addComponent(userTypeCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
        );

        entryPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[]{nameText, phoneText, usernameText});

        entryPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[]{jLabel2, jLabel4, jLabel5, jLabel6});

        userTable.setAutoCreateRowSorter(true);
        userTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object[][]{
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String[]{
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ) {
            boolean[] canEdit = new boolean[]{
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
        userTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userTable.setName("Users"); // NOI18N
        userTable.setRowHeight(35);
        userTable.setShowGrid(true);
        userTable.getTableHeader().setReorderingAllowed(false);
        userTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(userTable);
        if (userTable.getColumnModel().getColumnCount() > 0) {
            userTable.getColumnModel().getColumn(0).setHeaderValue("Title 1");
            userTable.getColumnModel().getColumn(1).setHeaderValue("Title 2");
            userTable.getColumnModel().getColumn(2).setHeaderValue("Title 3");
            userTable.getColumnModel().getColumn(3).setHeaderValue("Title 4");
        }
        userTable.setAutoCreateRowSorter(true);

        DefaultTableCellRenderer centerCellRenderer = new DefaultTableCellRenderer();
        centerCellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        userTable.setDefaultRenderer(Object.class, centerCellRenderer);
        userTable.getTableHeader().setDefaultRenderer(centerCellRenderer);

        userTable.setDefaultEditor(Object.class, null);

        jLabel8.setText("Search:");
        jLabel8.setEnabled(false);
        jLabel8.setFocusable(false);

        searchText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextKeyReleased(evt);
            }
        });

        refreshButton.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        refreshButton.setText("REFRESH");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        addUserButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        addUserButton.setText("+  Add New User");
        addUserButton.setToolTipText("");
        addUserButton.setActionCommand("addUserDialog");
        addUserButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addUserButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addContainerGap()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(245, 245, 245)
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(searchText)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(refreshButton))
                        .addGroup(layout.createSequentialGroup()
                            .addGap(6, 6, 6)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(addUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, 338, Short.MAX_VALUE)
                                            .addComponent(jSeparator2))
                                        .addComponent(entryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 502, Short.MAX_VALUE))
                                .addComponent(jSeparator1))))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(addUserButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(entryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[]{refreshButton, searchText});

        addUserButton.getAccessibleContext().setAccessibleName("");
    }// </editor-fold>//GEN-END:initComponents

    String userType;

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        UserModel userModel = new UserModel();

        // check if a row is selected
        if (userTable.getSelectionModel().getSelectedItemsCount() == 1) {
            // then, update the user
            if (nameText.getText().equals("") || phoneText.getText().equals("")) {
                JOptionPane.showMessageDialog(null, "Please fill all the required fields.");
            } else {
                int id = (int) userTable.getValueAt(userTable.getSelectedRow(), 0);

                userModel.setId(id);
                userModel.setUsername(usernameText.getText());
                userModel.setName(nameText.getText());
                userModel.setPhone(phoneText.getText());
                userModel.setUsername(usernameText.getText());
                userType = (String) userTypeCombo.getSelectedItem();
                userModel.setType(userType);

                EventQueue.invokeLater(() -> {
                    new UserController().updateUser(userModel);
                    loadDataSet();
                });
            }
        } else if (userTable.getSelectionModel().getSelectedItemsCount() > 1) {
            JOptionPane.showMessageDialog(this, "Unfortunately, You can only update 1 data at a time", "Invalid action", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No data selected to update.", "No data selected", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        // Get username of the selected row
        String username = (String) userTable.getValueAt(userTable.getSelectedRow(), 4);

        if (userTable.getSelectionModel().getSelectedItemsCount() > 1) {
            JOptionPane.showMessageDialog(this, "As a security measure, you can only delete one user at a time.", "Suspicious activity detected", JOptionPane.WARNING_MESSAGE);
        } else if (userTable.getSelectionModel().getSelectedItemsCount() == 1) {
            int opt = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to delete " + username + "?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

            if (opt == JOptionPane.YES_OPTION) {
                EventQueue.invokeLater(() -> {
                    new UserController().deleteUser(String.valueOf(userTable.getValueAt(userTable.getSelectedRow(), 3)));
                    loadDataSet();
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select an entry from the table", "No entry selected", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void userTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userTableMouseClicked
        int row = userTable.getSelectedRow();
        int col = userTable.getColumnCount();
        Object[] val = new Object[col];

        for (int i = 0; i < col; i++) {
            val[i] = userTable.getValueAt(row, i);
        }

        nameText.setText(val[1].toString());
        phoneText.setText(val[2].toString());
        usernameText.setText(val[3].toString());
        userTypeCombo.setSelectedItem(val[4].toString());
    }//GEN-LAST:event_userTableMouseClicked

    private void searchTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextKeyReleased
        String text = searchText.getText();
        loadSearchData(text);
    }//GEN-LAST:event_searchTextKeyReleased

    private void addUserButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addUserButtonActionPerformed
        new AddUserDialog(userTable);
    }//GEN-LAST:event_addUserButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        loadDataSet();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void changePasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changePasswordActionPerformed
        // get current username from selected row
        int row = userTable.getSelectedRow();

        if (userTable.getSelectionModel().getSelectedItemsCount() == 1) {
            String username = userTable.getValueAt(row, 3).toString();

            // change password prompt
            String password = JOptionPane.showInputDialog(this, "Enter new password for " + username + ":", "Change Password", JOptionPane.PLAIN_MESSAGE);

            if (password != null) {
                EventQueue.invokeLater(() -> {
                    new UserController().updatePass(username, password);
                    JOptionPane.showMessageDialog(this, "Password changed successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                });
            }
        } else if (userTable.getSelectionModel().getSelectedItemsCount() > 1) {
            JOptionPane.showMessageDialog(this, "As a security measure, you can only change password for one user at a time.", "Suspicious activity detected", JOptionPane.WARNING_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "There are no user selected in the table.", "No user selected", JOptionPane.INFORMATION_MESSAGE);
        }

    }//GEN-LAST:event_changePasswordActionPerformed

    private void processColumns() {
        // hide PID column (data still accessible)
        userTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        userTable.getColumnModel().getColumn(0).setMinWidth(0);
        userTable.getColumnModel().getColumn(0).setMaxWidth(0);
    }

    public void loadSearchData(String text) {
        EventQueue.invokeLater(() -> {
            try {
                UserController userController = new UserController();
                userTable.setModel(userController.buildUsersTable(userController.searchUsers(text)));

                processColumns();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }

    public final void loadDataSet() {
        EventQueue.invokeLater(() -> {
            try {
                UserController userController = new UserController();
                userTable.setModel(userController.buildUsersTable(userController.getUsers()));

                processColumns();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addUserButton;
    private javax.swing.JButton changePassword;
    private javax.swing.JButton deleteButton;
    private javax.swing.JPanel entryPanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTextField nameText;
    private javax.swing.JTextField phoneText;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTextField searchText;
    private javax.swing.JButton updateButton;
    private javax.swing.JTable userTable;
    private javax.swing.JComboBox<String> userTypeCombo;
    private javax.swing.JTextField usernameText;
    // End of variables declaration//GEN-END:variables
}
