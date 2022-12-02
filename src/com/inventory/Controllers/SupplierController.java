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

    Connection conn = null;
    Statement statement = null;
    PreparedStatement prepStatement = null;
    ResultSet resultSet = null;

    public SupplierController() {
        try {
            conn = new ConnectionFactory().getConnection();
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods to add new supplier
    public void addSupplierDAO(SupplierModel supplierModel) {
        try {
            String query = "SELECT * FROM suppliers WHERE fullname='"
                    + supplierModel.getSupplierName()
                    + "' AND location='"
                    + supplierModel.getLocation()
                    + "' AND mobile='"
                    + supplierModel.getPhone()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "This supplier already exists.");
            else
                addFunction(supplierModel);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addFunction(SupplierModel supplierModel) {
        try {
            String query = "INSERT INTO suppliers VALUES(null,?,?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, supplierModel.getSupplierCode());
            prepStatement.setString(2, supplierModel.getSupplierName());
            prepStatement.setString(3, supplierModel.getLocation());
            prepStatement.setString(4, supplierModel.getPhone());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "New supplier has been added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to edit existing suppleir details
    public void editSupplierDAO(SupplierModel supplierModel) {
        try {
            String query = "UPDATE suppliers SET fullname=?,location=?,mobile=? WHERE suppliercode=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, supplierModel.getSupplierName());
            prepStatement.setString(2, supplierModel.getLocation());
            prepStatement.setString(3, supplierModel.getPhone());
            prepStatement.setString(4, supplierModel.getSupplierCode());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Supplier details have been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete existing supplier
    public void deleteSupplierDAO(String suppCode) {
        try {
            String query = "DELETE FROM suppliers WHERE suppliercode='" +suppCode+ "'";
            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Supplier has been removed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Supplier data set retrieval method
    public ResultSet getQueryResult() {
        try {
            String query = "SELECT suppliercode, fullname, location, mobile FROM suppliers";
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Search method
    public ResultSet getSearchResult(String searchText) {
        try {
            String query = "SELECT suppliercode, fullname, location, mobile FROM suppliers " +
                    "WHERE suppliercode LIKE '%"+searchText+"%' OR location LIKE '%"+searchText+"%' " +
                    "OR fullname LIKE '%"+searchText+"%' OR mobile LIKE '%"+searchText+"%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to set/update supplier combo box
    public DefaultComboBoxModel<String> setComboItems(ResultSet resultSet) throws SQLException {
        Vector<String> suppNames = new Vector<>();
        while (resultSet.next()){
            suppNames.add(resultSet.getString("fullname"));
        }
        return new DefaultComboBoxModel<>(suppNames);
    }

    // Method to display retrieved data set in tabular form
    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int colCount = metaData.getColumnCount();

        for (int col=1; col <= colCount; col++){
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int col=1; col<=colCount; col++) {
                vector.add(resultSet.getObject(col));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }
}
