package com.pharmacy.controllers;

import com.pharmacy.database.DatabaseInstance;
import com.pharmacy.models.SupplierModel;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

public class SupplierController {

    Connection connection = null;
    Statement statement = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    String supplierCode;
    LogsController logsController;

    int logId;

    public SupplierController(int id) {
        this.logId = id;

        try {
            connection = new DatabaseInstance().getConnection();
            statement = connection.createStatement();
            logsController = new LogsController();
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
            String duplicateQuery = "SELECT * FROM suppliers WHERE full_name='"
                + supplierModel.getSupplierName()
                + "' AND location='"
                + supplierModel.getLocation()
                + "' AND contact='"
                + supplierModel.getPhone()
                + "'";
            resultSet = statement.executeQuery(duplicateQuery);

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "This supplier already exists.");
            } else {
                // else, add new supplier
                String insertQuery = "INSERT INTO suppliers VALUES(null,?,?,?,?,DEFAULT)";
                preparedStatement = connection.prepareStatement(insertQuery);
                preparedStatement.setString(1, supplierModel.getSupplierCode());
                preparedStatement.setString(2, supplierModel.getSupplierName());
                preparedStatement.setString(3, supplierModel.getLocation());
                preparedStatement.setString(4, supplierModel.getPhone());

                preparedStatement.executeUpdate();
                logsController.addLogEntry(logId, "Added new supplier: " + supplierModel.getSupplierName());
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
    public void updateSupplier(SupplierModel supplierModel, String oldCode) {
        try {
            String query = "UPDATE suppliers SET supplier_code=?, full_name=?,location=?,contact=? WHERE supplier_code=?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, supplierModel.getSupplierCode());
            preparedStatement.setString(2, supplierModel.getSupplierName());
            preparedStatement.setString(3, supplierModel.getLocation());
            preparedStatement.setString(4, supplierModel.getPhone());
            preparedStatement.setString(5, oldCode);

            preparedStatement.executeUpdate();
            logsController.addLogEntry(logId, "Updated supplier: " + supplierModel.getSupplierCode() + " - " + supplierModel.getSupplierName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete existing supplier
     *
     * @param supplierCode supplier code to be deleted
     */
    public void deleteSupplier(String supplierCode, String supplierName) {
        try {
            String query = "DELETE FROM suppliers WHERE supplier_code='" + supplierCode + "'";
            statement.executeUpdate(query);
            logsController.addLogEntry(logId, "Deleted supplier: " + supplierName);
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
            String query = "SELECT supplier_code, full_name, location, contact, last_updated FROM suppliers";
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    public String getSupplierCode(String supplierName) {

        try {
            String query = "SELECT supplier_code FROM suppliers WHERE full_name='" + supplierName + "'";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                supplierCode = resultSet.getString("supplier_code");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return supplierCode;
    }

    /**
     * Search for suppliers that matches the search term
     *
     * @param searchText search keywords to be matched
     * @return ResultSet of matched suppliers
     */
    public ResultSet searchSuppliers(String searchText) {
        try {
            String query = "SELECT supplier_code, full_name, location, contact FROM suppliers "
                + "WHERE supplier_code LIKE '%" + searchText + "%' OR location LIKE '%" + searchText + "%' "
                + "OR full_name LIKE '%" + searchText + "%' OR contact LIKE '%" + searchText + "%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return resultSet;
    }

    public DefaultComboBoxModel<String> setComboItems(ResultSet resultSet) throws SQLException {
        Vector<String> supplierNames = new Vector<>();

        while (resultSet.next()) {
            supplierNames.add(resultSet.getString("full_name"));
        }

        return new DefaultComboBoxModel<>(supplierNames);
    }
}
