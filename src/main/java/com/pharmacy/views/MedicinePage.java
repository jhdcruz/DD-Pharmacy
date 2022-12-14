package com.pharmacy.views;

import com.pharmacy.controllers.MedicineController;
import com.pharmacy.controllers.SupplierController;
import com.pharmacy.models.MedicineModel;
import com.pharmacy.utils.DataTableModel;
import com.pharmacy.utils.StringFormatting;
import com.pharmacy.views.dialogs.AddMedicineDialog;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.Objects;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class MedicinePage extends javax.swing.JPanel {

    private final Dashboard dashboard;

    private final int id;

    public MedicinePage(Dashboard dashboard, int id) {
        this.id = id;
        this.dashboard = dashboard;

        initComponents();
        loadDataSet();

        // We're not using invokeLater inside combobox since
        // suppComboBoxPopupMenuWillBecomeVisible will be stuck and not show
        EventQueue.invokeLater(this::loadComboBox);
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
        suppCombo = new javax.swing.JComboBox<>();
        addSuppButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        codeText = new javax.swing.JTextField();
        nameText = new javax.swing.JTextField();
        quantityText = new javax.swing.JTextField();
        costText = new javax.swing.JTextField();
        sellText = new javax.swing.JTextField();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        expirationDate = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        descriptionText = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        medicineTable = new javax.swing.JTable();
        refreshButton = new javax.swing.JButton();
        searchText = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        addButton = new javax.swing.JButton();
        jSeparator3 = new javax.swing.JSeparator();

        jLabel1.setFont(new java.awt.Font("Impact", 1, 24)); // NOI18N
        jLabel1.setText("MEDICINES");

        entryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Medicine Details"));
        entryPanel.setEnabled(false);

        suppCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Medicine Supplier..."}));
        suppCombo.setToolTipText("Select a supplier");
        suppCombo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }

            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }

            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                suppComboPopupMenuWillBecomeVisible(evt);
            }
        });

        addSuppButton.setText("+  Add New Supplier");
        addSuppButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addSuppButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSuppButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Medicine Code:");

        jLabel3.setText("Medicine Name:");

        jLabel5.setText("Quantity:");

        jLabel6.setText("Cost Price:");

        jLabel7.setText("Selling Price:");

        codeText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        nameText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        quantityText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        costText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        sellText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        editButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        editButton.setText("Update");
        editButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
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

        expirationDate.setDateFormatString("MMM dd, yyyy");

        jLabel10.setText("Expiration:");

        descriptionText.setColumns(20);
        descriptionText.setLineWrap(true);
        descriptionText.setRows(5);
        descriptionText.setToolTipText("Brief description of the medicine (1000 characters)");
        descriptionText.setWrapStyleWord(true);
        descriptionText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jScrollPane2.setViewportView(descriptionText);

        jLabel11.setText("Description:");

        javax.swing.GroupLayout entryPanelLayout = new javax.swing.GroupLayout(entryPanel);
        entryPanel.setLayout(entryPanelLayout);
        entryPanelLayout.setHorizontalGroup(
            entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(entryPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(suppCombo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(entryPanelLayout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(codeText))
                        .addGroup(entryPanelLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(nameText))
                        .addGroup(entryPanelLayout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(quantityText))
                        .addGroup(entryPanelLayout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(costText))
                        .addComponent(addSuppButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(entryPanelLayout.createSequentialGroup()
                            .addGap(108, 108, 108)
                            .addComponent(editButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(entryPanelLayout.createSequentialGroup()
                            .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(deleteButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addComponent(expirationDate, javax.swing.GroupLayout.DEFAULT_SIZE, 175, Short.MAX_VALUE)
                                .addComponent(sellText))))
                    .addContainerGap())
        );

        entryPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[]{jLabel2, jLabel3, jLabel5, jLabel6, jLabel7});

        entryPanelLayout.setVerticalGroup(
            entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(entryPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(suppCombo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(addSuppButton, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(codeText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(nameText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(quantityText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(costText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(sellText, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(entryPanelLayout.createSequentialGroup()
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(expirationDate, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(entryPanelLayout.createSequentialGroup()
                            .addGap(14, 14, 14)
                            .addComponent(jLabel10)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 133, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addContainerGap())
        );

        medicineTable.setAutoCreateRowSorter(true);
        medicineTable.setModel(new javax.swing.table.DefaultTableModel(
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
        medicineTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        medicineTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        medicineTable.setRowHeight(35);
        medicineTable.setShowGrid(true);
        medicineTable.getTableHeader().setReorderingAllowed(false);
        medicineTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                medicineTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(medicineTable);
        medicineTable.setAutoCreateRowSorter(true);

        DefaultTableCellRenderer centerCellRenderer = new DefaultTableCellRenderer();
        centerCellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        medicineTable.setDefaultRenderer(Object.class, centerCellRenderer);
        medicineTable.getTableHeader().setDefaultRenderer(centerCellRenderer);

        medicineTable.setDefaultEditor(Object.class, null);

        refreshButton.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        refreshButton.setText("REFRESH");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        searchText.setToolTipText("Search for products");
        searchText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextKeyReleased(evt);
            }
        });

        jLabel9.setText("Search:");
        jLabel9.setEnabled(false);
        jLabel9.setFocusable(false);

        addButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        addButton.setText("+  Add New Medicine");
        addButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(157, 157, 157)
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(searchText)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(refreshButton)
                            .addGap(6, 6, 6))
                        .addComponent(jSeparator1)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(entryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 301, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)))
                    .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                        .addComponent(jLabel9)
                        .addComponent(searchText, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(refreshButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(entryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE))
                    .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[]{refreshButton, searchText});

    }// </editor-fold>//GEN-END:initComponents

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        if (medicineTable.getSelectedRow() < 0)
            JOptionPane.showMessageDialog(this, "Please select medicine from the table.");
        else {
            MedicineModel medicineModel = new MedicineModel();

            if (nameText.getText().equals("") || costText.getText().equals("")
                || sellText.getText().equals("") || quantityText.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter all the required details.");
            } else {
                int pid = (int) medicineTable.getValueAt(medicineTable.getSelectedRow(), 0);

                medicineModel.setMedicineId(pid);
                medicineModel.setMedicineCode(codeText.getText());
                medicineModel.setMedicineName(nameText.getText());
                medicineModel.setQuantity(Integer.parseInt(quantityText.getText()));
                medicineModel.setCostPrice(Double.parseDouble(costText.getText()));
                medicineModel.setSellPrice(Double.parseDouble(sellText.getText()));
                medicineModel.setExpirationDate(expirationDate.getDate());
                medicineModel.setDescription(descriptionText.getText());
                medicineModel.setSuppliedBy(Objects.requireNonNull(suppCombo.getSelectedItem()).toString());

                EventQueue.invokeLater(() -> {
                    new MedicineController(id).updateMedicine(medicineModel);
                    loadDataSet();
                });
            }
        }
    }//GEN-LAST:event_editButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        new AddMedicineDialog(medicineTable, dashboard, id);
    }//GEN-LAST:event_addButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (medicineTable.getSelectedRow() < 0)
            JOptionPane.showMessageDialog(this, "Please select medicine from the table.");
        else {
            int opt = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this medicine?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);

            if (opt == JOptionPane.YES_OPTION) {
                EventQueue.invokeLater(() -> {
                    int id = (Integer) medicineTable.getValueAt(medicineTable.getSelectedRow(), 0);
                    String name = medicineTable.getValueAt(medicineTable.getSelectedRow(), 2).toString();

                    new MedicineController(id).deleteMedicine(id, name);
                    loadDataSet();
                });
            }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    //static String productName;
    private void medicineTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_medicineTableMouseClicked
        int row = medicineTable.getSelectedRow();
        int col = medicineTable.getColumnCount();

        Object[] data = new Object[col];

        // get cell values
        for (int i = 0; i < col; i++) {
            data[i] = medicineTable.getValueAt(row, i);
        }

        codeText.setText(data[1].toString());
        nameText.setText(data[2].toString());
        descriptionText.setText(data[3].toString());
        quantityText.setText(data[4].toString());
        costText.setText(data[5].toString());
        sellText.setText(data[6].toString());
        suppCombo.setSelectedItem(data[7]);
        expirationDate.setDate(new StringFormatting().stringToDate(data[8].toString()));
    }//GEN-LAST:event_medicineTableMouseClicked

    private void addSuppButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSuppButtonActionPerformed
        dashboard.addSuppPage();
    }//GEN-LAST:event_addSuppButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        loadDataSet();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void searchTextKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_searchTextKeyReleased
        loadSearchData(searchText.getText());
    }//GEN-LAST:event_searchTextKeyReleased

    private void suppComboPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_suppComboPopupMenuWillBecomeVisible
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));

        int count = suppCombo.getItemCount();
        loadComboBox();

        if (count != suppCombo.getItemCount()) {
            suppCombo.showPopup();
        }

        setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }//GEN-LAST:event_suppComboPopupMenuWillBecomeVisible

    public void loadComboBox() {
        try {
            SupplierController supplierController = new SupplierController(id);
            suppCombo.setModel(supplierController.setComboItems(supplierController.getSuppliers()));
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Something went wrong", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void processColumns() {
        // hide pid
        medicineTable.getColumnModel().getColumn(0).setMinWidth(0);
        medicineTable.getColumnModel().getColumn(0).setMaxWidth(0);

        // resize column widths
        medicineTable.getColumnModel().getColumn(1).setPreferredWidth(111);
        medicineTable.getColumnModel().getColumn(2).setPreferredWidth(131);
        medicineTable.getColumnModel().getColumn(3).setPreferredWidth(161);
        medicineTable.getColumnModel().getColumn(4).setPreferredWidth(81);
        medicineTable.getColumnModel().getColumn(5).setPreferredWidth(81);
        medicineTable.getColumnModel().getColumn(6).setPreferredWidth(81);
        medicineTable.getColumnModel().getColumn(7).setPreferredWidth(111);
        medicineTable.getColumnModel().getColumn(8).setPreferredWidth(121);
        medicineTable.getColumnModel().getColumn(9).setPreferredWidth(171);
    }

    // Method to load data into table
    public void loadDataSet() {
        EventQueue.invokeLater(() -> {
            try {
                medicineTable.setModel(new DataTableModel().buildTableModel(new MedicineController(id).getMedicines()));

                processColumns();
                loadComboBox();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Something went wrong", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // Method to display search result in table
    public void loadSearchData(String text) {
        EventQueue.invokeLater(() -> {
            try {
                medicineTable.setModel(new DataTableModel().buildTableModel(new MedicineController(id).getMedicineSearch(text)));

                processColumns();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, e.getMessage(), "Something went wrong", JOptionPane.WARNING_MESSAGE);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JButton addSuppButton;
    private javax.swing.JTextField codeText;
    private javax.swing.JTextField costText;
    private javax.swing.JButton deleteButton;
    private javax.swing.JTextArea descriptionText;
    private javax.swing.JButton editButton;
    private javax.swing.JPanel entryPanel;
    private com.toedter.calendar.JDateChooser expirationDate;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JSeparator jSeparator3;
    private javax.swing.JTable medicineTable;
    private javax.swing.JTextField nameText;
    private javax.swing.JTextField quantityText;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTextField searchText;
    private javax.swing.JTextField sellText;
    private javax.swing.JComboBox<String> suppCombo;
    // End of variables declaration//GEN-END:variables
}
