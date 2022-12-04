package com.pharmacy.Controllers;

import com.pharmacy.Database.DatabaseInstance;
import com.pharmacy.Models.SupplierModel;

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

    public SupplierController() {
        try {
            connection = new DatabaseInstance().getConnection();
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
            String duplicateQuery = "SELECT * FROM suppliers WHERE full_name='"
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
            String query = "UPDATE suppliers SET full_name=?,location=?,mobile=? WHERE supplier_code=?";
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
            String query = "DELETE FROM suppliers WHERE supplier_code='" + supplierCode + "'";

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
            String query = "SELECT supplier_code, full_name, location, mobile FROM suppliers";
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
            String query = "SELECT supplier_code, full_name, location, mobile FROM suppliers " +
                "WHERE supplier_code LIKE '%" + searchText + "%' OR location LIKE '%" + searchText + "%' " +
                "OR full_name LIKE '%" + searchText + "%' OR mobile LIKE '%" + searchText + "%'";
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
