package com.inventory.Controllers;

import com.inventory.Models.SupplierModel;
import com.database.ConnectionFactory;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Locale;
import java.util.Vector;

public class SupplierController {

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public SupplierController() {
        try {
            connection = new ConnectionFactory().getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add a new supplier to the database
     *
     * @param supplierModel populate the supplier model
     */
    public void addSupplier(SupplierModel supplierModel) {
        try {
            // check if supplier already exists
            String duplicateQuery = "SELECT * FROM suppliers WHERE fullname='"
                    + supplierModel.getSupplierName()
                    + "' AND location='"
                    + supplierModel.getLocation()
                    + "' AND mobile='"
                    + supplierModel.getPhone()
                    + "'";
            resultSet = statement.executeQuery(duplicateQuery);

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "This supplier already exists.");
            } else {
                // else, add new supplier
                String insertQuery = "INSERT INTO suppliers VALUES(null,?,?,?,?)";
                preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, supplierModel.getSupplierCode());
                preparedStatement.setString(2, supplierModel.getSupplierName());
                preparedStatement.setString(3, supplierModel.getLocation());
                preparedStatement.setString(4, supplierModel.getPhone());
                preparedStatement.executeUpdate();
                JOptionPane.showMessageDialog(null, "New supplier has been added successfully.");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /**
     * Update existing supplier
     *
     * @param supplierModel populated supplier model
     */
    public void updateSupplier(SupplierModel supplierModel) {
        try {
            String query = "UPDATE suppliers SET fullname=?,location=?,mobile=? WHERE suppliercode=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, supplierModel.getSupplierName());
            preparedStatement.setString(2, supplierModel.getLocation());
            preparedStatement.setString(3, supplierModel.getPhone());
            preparedStatement.setString(4, supplierModel.getSupplierCode());
            preparedStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "Supplier details have been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete existing supplier
     *
     * @param supplierCode supplier code to be deleted
     */
    public void deleteSupplier(String supplierCode) {
        try {
            String query = "DELETE FROM suppliers WHERE suppliercode='" + supplierCode + "'";

            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Supplier has been removed.");
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /**
     * Get all suppliers
     *
     * @return ResultSet of all suppliers
     */
    public ResultSet getSuppliers() {
        try {
            String query = "SELECT suppliercode, fullname, location, mobile FROM suppliers";
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    /**
     * Search for suppliers that matches the search term
     *
     * @param searchText search keywords to be matched
     * @return ResultSet of matched suppliers
     */
    public ResultSet searchSuppliers(String searchText) {
        try {
            String query = "SELECT suppliercode, fullname, location, mobile FROM suppliers " +
                    "WHERE suppliercode LIKE '%" + searchText + "%' OR location LIKE '%" + searchText + "%' " +
                    "OR fullname LIKE '%" + searchText + "%' OR mobile LIKE '%" + searchText + "%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return resultSet;
    }

    public DefaultComboBoxModel<String> setComboItems(ResultSet resultSet) throws SQLException {
        Vector<String> supplierNames = new Vector<>();

        while (resultSet.next()) {
            supplierNames.add(resultSet.getString("fullname"));
        }

        return new DefaultComboBoxModel<>(supplierNames);
    }

    // Method to display retrieved data set in tabular form
    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int colCount = metaData.getColumnCount();

        for (int col = 1; col <= colCount; col++) {
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<>();
            for (int col = 1; col <= colCount; col++) {
                vector.add(resultSet.getObject(col));
            }

            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }
}
