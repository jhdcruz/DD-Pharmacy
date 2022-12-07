package com.pharmacy.Views;

import com.pharmacy.Controllers.ProductController;
import com.pharmacy.Controllers.SupplierController;
import com.pharmacy.Models.ProductModel;
import com.pharmacy.Utils.DataTableModel;
import com.pharmacy.Utils.StringFormatting;
import com.pharmacy.Views.Dialogs.AddProductDialog;

import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.sql.SQLException;
import java.util.Objects;

public class ProductPage extends javax.swing.JPanel {

    Dashboard dashboard;
    ProductModel productModel;

    public ProductPage(Dashboard dashboard) {
        this.dashboard = dashboard;

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
        addButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jSeparator2 = new javax.swing.JSeparator();
        jSeparator3 = new javax.swing.JSeparator();
        expirationDate = new com.toedter.calendar.JDateChooser();
        jLabel10 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        descriptionText = new javax.swing.JTextArea();
        jLabel11 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        productTable = new javax.swing.JTable();
        refreshButton = new javax.swing.JButton();
        searchText = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Impact", 1, 24)); // NOI18N
        jLabel1.setText("PRODUCTS");

        entryPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Product Details"));

        suppCombo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[]{"Product Supplier"}));
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

        addSuppButton.setText("+ Add new supplier");
        addSuppButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addSuppButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSuppButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Product Code:");

        jLabel3.setText("Product Name:");

        jLabel5.setText("Quantity:");

        jLabel6.setText("Cost Price:");

        jLabel7.setText("Selling Price:");

        codeText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        nameText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        quantityText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        costText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        sellText.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));

        addButton.setFont(new java.awt.Font("Liberation Sans", 1, 15)); // NOI18N
        addButton.setText("+  Add New Product");
        addButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        editButton.setFont(new java.awt.Font("Liberation Sans", 0, 15)); // NOI18N
        editButton.setText("Update");
        editButton.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        deleteButton.setBackground(new java.awt.Color(255, 51, 51));
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
        descriptionText.setToolTipText("Brief description of the product (300 characters)");
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
                        .addComponent(jSeparator3)
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
                        .addComponent(addButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                                .addComponent(expirationDate, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
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
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(entryPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(8, 8, 8)
                    .addComponent(jSeparator3, javax.swing.GroupLayout.PREFERRED_SIZE, 5, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(12, 12, 12))
        );

        productTable.setAutoCreateRowSorter(true);
        productTable.setModel(new javax.swing.table.DefaultTableModel(
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
        productTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        productTable.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        productTable.setRowHeight(30);
        productTable.setShowGrid(true);
        productTable.getTableHeader().setReorderingAllowed(false);
        productTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                productTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(productTable);
        productTable.setAutoCreateRowSorter(true);

        DefaultTableCellRenderer centerCellRenderer = new DefaultTableCellRenderer();
        centerCellRenderer.setHorizontalAlignment(SwingConstants.LEFT);
        productTable.setDefaultRenderer(Object.class, centerCellRenderer);
        productTable.getTableHeader().setDefaultRenderer(centerCellRenderer);

        productTable.setDefaultEditor(Object.class, null);

        refreshButton.setFont(new java.awt.Font("Liberation Sans", 0, 14)); // NOI18N
        refreshButton.setText("REFRESH");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        searchText.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                searchTextKeyReleased(evt);
            }
        });

        jLabel9.setText("Search:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel9)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(searchText)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(refreshButton)
                            .addGap(215, 215, 215))
                        .addComponent(jSeparator1)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 601, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(entryPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGap(0, 0, 0))
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
                        .addComponent(entryPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1))
                    .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[]{refreshButton, searchText});

    }// </editor-fold>//GEN-END:initComponents

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        if (productTable.getSelectedRow() < 0)
            JOptionPane.showMessageDialog(null, "Please select product from the table.");
        else {
            productModel = new ProductModel();
            if (nameText.getText().equals("") || costText.getText().equals("")
                || sellText.getText().equals("") || quantityText.getText().equals("")) {
                JOptionPane.showMessageDialog(this, "Please enter all the required details.");
            } else {
                int pid = (int) productTable.getValueAt(productTable.getSelectedRow(), 0);

                productModel.setProductId(pid);
                productModel.setProductCode(codeText.getText());
                productModel.setProductName(nameText.getText());
                productModel.setQuantity(Integer.parseInt(quantityText.getText()));
                productModel.setCostPrice(Double.parseDouble(costText.getText()));
                productModel.setSellPrice(Double.parseDouble(sellText.getText()));
                productModel.setExpirationDate(expirationDate.getDate());
                productModel.setDescription(descriptionText.getText());
                productModel.setSuppliedBy(Objects.requireNonNull(suppCombo.getSelectedItem()).toString());

                EventQueue.invokeLater(() -> {
                    new ProductController().updateProduct(productModel);
                    loadDataSet();
                });
            }
        }
    }//GEN-LAST:event_editButtonActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        new AddProductDialog(productTable, dashboard);
    }//GEN-LAST:event_addButtonActionPerformed

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        if (productTable.getSelectedRow() < 0)
            JOptionPane.showMessageDialog(null, "Please select product from the table.");
        else {
            int opt = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to delete this product?",
                "Confirmation",
                JOptionPane.YES_NO_OPTION);
            if (opt == JOptionPane.YES_OPTION) {
                new ProductController().deleteProduct(
                    (Integer) productTable.getValueAt(
                        productTable.getSelectedRow(), 0));
                loadDataSet();
            }
        }
    }//GEN-LAST:event_deleteButtonActionPerformed

    //static String productName;
    private void productTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_productTableMouseClicked
        int row = productTable.getSelectedRow();
        int col = productTable.getColumnCount();

        Object[] data = new Object[col];

        // get cell values
        for (int i = 0; i < col; i++) {
            data[i] = productTable.getValueAt(row, i);
        }

        codeText.setText(data[1].toString());
        nameText.setText(data[2].toString());
        descriptionText.setText(data[3].toString());
        quantityText.setText(data[4].toString());
        costText.setText(data[5].toString());
        sellText.setText(data[6].toString());
        suppCombo.setSelectedItem(data[7]);
        expirationDate.setDate(new StringFormatting().stringToDate(data[8].toString()));
    }//GEN-LAST:event_productTableMouseClicked

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
            SupplierController supplierController = new SupplierController();
            suppCombo.setModel(supplierController.setComboItems(supplierController.getSuppliers()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void processColumns() {
        // hide pid
        productTable.getColumnModel().getColumn(0).setMinWidth(0);
        productTable.getColumnModel().getColumn(0).setMaxWidth(0);

        // resize column widths
        productTable.getColumnModel().getColumn(1).setPreferredWidth(111);
        productTable.getColumnModel().getColumn(2).setPreferredWidth(121);
        productTable.getColumnModel().getColumn(3).setPreferredWidth(151);
        productTable.getColumnModel().getColumn(4).setPreferredWidth(81);
        productTable.getColumnModel().getColumn(5).setPreferredWidth(81);
        productTable.getColumnModel().getColumn(6).setPreferredWidth(81);
        productTable.getColumnModel().getColumn(7).setPreferredWidth(101);
        productTable.getColumnModel().getColumn(8).setPreferredWidth(121);
        productTable.getColumnModel().getColumn(9).setPreferredWidth(171);
    }

    // Method to load data into table
    public void loadDataSet() {
        EventQueue.invokeLater(() -> {
            try {
                ProductController productController = new ProductController();
                productTable.setModel(new DataTableModel().buildTableModel(productController.getProducts()));
                processColumns();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    // Method to display search result in table
    public void loadSearchData(String text) {
        EventQueue.invokeLater(() -> {
            try {
                ProductController productController = new ProductController();
                productTable.setModel(new DataTableModel().buildTableModel(productController.getProductSearch(text)));

                processColumns();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
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
    private javax.swing.JTextField nameText;
    private javax.swing.JTable productTable;
    private javax.swing.JTextField quantityText;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTextField searchText;
    private javax.swing.JTextField sellText;
    private javax.swing.JComboBox<String> suppCombo;
    // End of variables declaration//GEN-END:variables
}
