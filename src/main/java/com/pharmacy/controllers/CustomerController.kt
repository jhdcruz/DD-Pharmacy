package com.pharmacy.controllers;

import com.pharmacy.database.DatabaseInstance;
import com.pharmacy.models.CustomerModel;

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

    LogsController logsController;
    private final int id;

    public CustomerController(int id) {
        this.id = id;

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
            String validateQuery = "SELECT * FROM customers WHERE last_name='" + customerModel.getLastName() + "' AND first_name='" + customerModel.getFirstName() + "' AND middle_name='" + customerModel.getMiddleName() + "'";
            resultSet = statement.executeQuery(validateQuery);

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "Customer already exists.");
            } else {
                // else, save customer to database
                String insertQuery = "INSERT INTO customers VALUES(null,?,?,?,?,?,?,DEFAULT)";
                preparedStatement = connection.prepareStatement(insertQuery);

                preparedStatement.setString(1, customerModel.getCode());
                preparedStatement.setString(2, customerModel.getLastName());
                preparedStatement.setString(3, customerModel.getFirstName());
                preparedStatement.setString(4, customerModel.getMiddleName());
                preparedStatement.setString(5, customerModel.getConditions());
                preparedStatement.setString(6, customerModel.getPhone());

                preparedStatement.executeUpdate();
                logsController.addLogEntry(id, "Added customer " + customerModel.getName() + " to database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Edit/update customer data.
     *
     * @param customerModel Customer to be edited/updated (derived from
     *                      CustomerModel)
     */
    public void updateCustomer(CustomerModel customerModel) {
        try {
            String query = "UPDATE customers SET customer_code=?, last_name=?,first_name=?, middle_name=?, conditions=?,phone=?"
                + "WHERE cid=?";
            preparedStatement = connection.prepareStatement(query);

            preparedStatement.setString(1, customerModel.getCode());
            preparedStatement.setString(2, customerModel.getLastName());
            preparedStatement.setString(3, customerModel.getFirstName());
            preparedStatement.setString(4, customerModel.getMiddleName());
            preparedStatement.setString(5, customerModel.getConditions());
            preparedStatement.setString(6, customerModel.getPhone());

            preparedStatement.setInt(7, customerModel.getId());
            preparedStatement.executeUpdate();
            logsController.addLogEntry(id, "Updated customer " + customerModel.getName());
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
            logsController.addLogEntry(id, "Deleted customer: " + customerCode);
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
            String query = "SELECT * FROM customers";
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
            String query = "SELECT customer_code,last_name,first_name,middle_name,conditions,phone,last_updated," +
                "FROM customers " +
                "WHERE customer_code LIKE '%" + searchQuery + "%' " +
                "OR last_name LIKE '%" + searchQuery + "%'  " +
                "OR first_name LIKE '%" + searchQuery + "%'  " +
                "OR middle_name LIKE '%" + searchQuery + "%'  " +
                "OR " + "conditions LIKE '%" + searchQuery + "%' " +
                "OR phone LIKE '%" + searchQuery + "%'";

            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return resultSet;
    }
}
