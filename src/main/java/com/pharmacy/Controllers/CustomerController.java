package com.pharmacy.Controllers;

import com.pharmacy.Models.CustomerModel;
import com.pharmacy.Database.DatabaseInstance;

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

public class CustomerController {
    Connection connection = null;
    PreparedStatement preparedStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public CustomerController() {
        try {
            connection = new DatabaseInstance().getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Add customer to database.
     *
     * @param customerModel Customer to be added (derived from CustomerModel)
     */
    public void addCustomer(CustomerModel customerModel) {
        try {
            // check if customer already exists
            String validateQuery = "SELECT * FROM customers WHERE fullname='" + customerModel.getName() + "' AND location='" + customerModel.getLocation() + "' AND phone='" + customerModel.getPhone() + "'";
            resultSet = statement.executeQuery(validateQuery);

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Customer already exists.");
            } else {
                // else, save customer to database
                try {
                    String insertQuery = "INSERT INTO customers VALUES(null,?,?,?,?)";
                    preparedStatement = connection.prepareStatement(insertQuery);

                    preparedStatement.setString(1, customerModel.getCode());
                    preparedStatement.setString(2, customerModel.getName());
                    preparedStatement.setString(3, customerModel.getLocation());
                    preparedStatement.setString(4, customerModel.getPhone());

                    preparedStatement.executeUpdate();
                    JOptionPane.showMessageDialog(null, "New customer has been added.");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edit/update customer data.
     *
     * @param customerModel Customer to be edited/updated (derived from CustomerModel)
     */
    public void editCustomer(CustomerModel customerModel) {
        try {
            String query = "UPDATE customers SET fullname=?,location=?,phone=? WHERE customercode=?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, customerModel.getName());
            preparedStatement.setString(2, customerModel.getLocation());
            preparedStatement.setString(3, customerModel.getPhone());
            preparedStatement.setString(4, customerModel.getCode());

            preparedStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Customer details have been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Delete customer in the database.
     *
     * @param customerCode customer code to be deleted
     */
    public void deleteCustomer(String customerCode) {
        try {
            String query = "DELETE FROM customers WHERE customercode='" + customerCode + "'";

            statement.executeUpdate(query);
            JOptionPane.showMessageDialog(null, "Customer" + customerCode + "removed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all customers from database to be displayed.
     *
     * @return database result
     */
    public ResultSet getQueryResult() {
        try {
            String query = "SELECT customercode, fullname, location, phone FROM customers";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    /**
     * Search for matching customer based on given string.
     *
     * @param searchQuery search query to be matched with customer data
     * @return database result that matches the search query
     */
    public ResultSet getCustomerSearch(String searchQuery) {
        try {
            String query = "SELECT customercode,fullname,location,phone FROM customers " + "WHERE customercode LIKE '%" + searchQuery + "%' OR fullname LIKE '%" + searchQuery + "%' OR " + "location LIKE '%" + searchQuery + "%' OR phone LIKE '%" + searchQuery + "%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    /**
     * Get a customer based on provided customer code.
     *
     * @param customerCode customer code to be matched with customer name
     * @return customer details
     */
    public ResultSet getCustomer(String customerCode) {
        try {
            String query = "SELECT * FROM customers WHERE customercode='" + customerCode + "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    /**
     * TODO: Add description
     *
     * @param prodCode product code
     * @return result set of product details
     */
    public ResultSet getProdName(String prodCode) {
        try {
            String query = "SELECT productname,currentstock.quantity FROM products " + "INNER JOIN currentstock ON products.productcode=currentstock.productcode " + "WHERE currentstock.productcode='" + prodCode + "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
