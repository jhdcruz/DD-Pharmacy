package com.pharmacy.Controllers;

import com.pharmacy.Database.DatabaseInstance;
import com.pharmacy.Models.CustomerModel;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
            String validateQuery = "SELECT * FROM customers WHERE full_name='" + customerModel.getName() + "' AND location='" + customerModel.getLocation() + "' AND phone='" + customerModel.getPhone() + "'";
            resultSet = statement.executeQuery(validateQuery);

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Customer already exists.");
            } else {
                // else, save customer to database
                try {
                    String insertQuery = "INSERT INTO customers VALUES(null,?,?,?,?,null)";
                    preparedStatement = connection.prepareStatement(insertQuery);

                    preparedStatement.setString(1, customerModel.getCode());
                    preparedStatement.setString(2, customerModel.getName());
                    preparedStatement.setString(3, customerModel.getLocation());
                    preparedStatement.setString(4, customerModel.getPhone());

                    preparedStatement.executeUpdate();
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
    public void updateCustomer(CustomerModel customerModel) {
        try {
            String query = "UPDATE customers SET customer_code=?, full_name=?,location=?,phone=?" +
                "WHERE cid=?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, customerModel.getCode());
            preparedStatement.setString(2, customerModel.getName());
            preparedStatement.setString(3, customerModel.getLocation());
            preparedStatement.setString(4, customerModel.getPhone());

            preparedStatement.setInt(5, customerModel.getId());
            preparedStatement.executeUpdate();
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
            String query = "DELETE FROM customers WHERE customer_code='" + customerCode + "'";

            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Get all customers from database to be displayed.
     *
     * @return database result
     */
    public ResultSet getCustomers() {
        try {
            String query = "SELECT customer_code, full_name, location, phone, last_updated FROM customers";
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
            String query = "SELECT customer_code,full_name,location,phone,last_updated FROM customers " + "WHERE customer_code LIKE '%" + searchQuery + "%' OR full_name LIKE '%" + searchQuery + "%' OR " + "location LIKE '%" + searchQuery + "%' OR phone LIKE '%" + searchQuery + "%'";
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
            String query = "SELECT * FROM customers WHERE customer_code='" + customerCode + "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }

    /**
     * Get product info based on provided product code.
     * This method is tailored for sales/pos usage.
     *
     * @param prodCode product code
     * @return result set of product details
     */
    public ResultSet getProdName(String prodCode) {
        try {
            String query = "SELECT product_name, quantity, sell_price FROM products WHERE product_code='" + prodCode + "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
