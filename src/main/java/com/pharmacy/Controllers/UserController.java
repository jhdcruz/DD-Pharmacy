
package com.pharmacy.Controllers;

import com.pharmacy.Models.UserModel;
import com.pharmacy.Database.DatabaseInstance;
import com.pharmacy.Views.UsersPage;

import javax.swing.JOptionPane;
import javax.swing.table.*;
import java.sql.*;
import java.util.*;

public class UserController {
    Connection conn = null;
    PreparedStatement prepStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public UserController() {
        try {
            conn = new DatabaseInstance().getConnection();
            statement = conn.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Methods to add new user
    public void addUser(UserModel userModel, String userType) {
        try {
            String duplicateQuery = "SELECT * FROM users WHERE name='"
                + userModel.getName()
                + "' AND location='"
                + userModel.getLocation()
                + "' AND phone='"
                + userModel.getPhone()
                + "' AND usertype='"
                + userModel.getType()
                + "'";
            resultSet = statement.executeQuery(duplicateQuery);

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "User already exists");
            } else {
                String username = null;
                String password = null;
                String oldUsername = null;

                String insertQuery = "INSERT INTO users (name,location,phone,username,password,usertype) " +
                    "VALUES(?,?,?,?,?,?)";
                prepStatement = conn.prepareStatement(insertQuery);
                prepStatement.setString(1, userModel.getName());
                prepStatement.setString(2, userModel.getLocation());
                prepStatement.setString(3, userModel.getPhone());
                prepStatement.setString(4, userModel.getUsername());
                prepStatement.setString(5, userModel.getPassword());
                prepStatement.setString(6, userModel.getType());

                prepStatement.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    // Method to edit existing user
    public void updateUser(UserModel userModel) {

        try {
            String query = "UPDATE users SET name=?,location=?,phone=?,usertype=? WHERE username=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userModel.getName());
            prepStatement.setString(2, userModel.getLocation());
            prepStatement.setString(3, userModel.getPhone());
            prepStatement.setString(4, userModel.getType());
            prepStatement.setString(5, userModel.getUsername());

            prepStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Method to delete existing user
    public void deleteUser(String username) {
        try {
            String query = "DELETE FROM users WHERE username=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, username);

            prepStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        new UsersPage().loadDataSet();
    }

    // Method to retrieve data set to display in table
    public ResultSet getUsers() {
        try {
            String query = "SELECT * FROM users";
            resultSet = statement.executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet findUser(String username) {
        try {
            String query = "SELECT * FROM users WHERE username='" + username + "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return resultSet;
    }

    public void getUserFullName(UserModel userModel, String username) {
        try {
            String query = "SELECT * FROM users WHERE username='" + username + "' LIMIT 1";
            resultSet = statement.executeQuery(query);
            String fullName = null;
            if (resultSet.next()) fullName = resultSet.getString(2);
            userModel.setName(fullName);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    public ResultSet getUserLogs() {
        try {
            String query = "SELECT users.name,userlogs.username,in_time,out_time,location FROM userlogs" +
                " INNER JOIN users on userlogs.username=users.username";
            resultSet = statement.executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return resultSet;
    }

    public void addUserLogin(UserModel userModel) {
        try {
            String query = "INSERT INTO userlogs (username, in_time, out_time) values(?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userModel.getUsername());
            prepStatement.setString(2, userModel.getInTime());
            prepStatement.setString(3, userModel.getOutTime());

            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getPassword(String username, String password) {
        try {
            String query = "SELECT password FROM users WHERE username='"
                + username
                + "' AND password='"
                + password
                + "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return resultSet;
    }

    /**
     * Display database results and headers in a table.
     *
     * @param resultSet database result
     * @return table model that contains data and the data columns
     * @throws SQLException SQL exception thrown when database error occurs
     */
    public DefaultTableModel buildUsersTable(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();

        // add column names to the table column headers
        for (int col = 1; col <= columnCount; col++) {
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        // Do not display passwords column
        columnNames.remove("PASSWORD");

        Vector<Vector<Object>> data = new Vector<>();
        while (resultSet.next()) {
            Vector<Object> dataRow = new Vector<>();

            for (int col = 1; col <= columnCount; col++) {
                dataRow.add(resultSet.getObject(col));
            }

            data.add(dataRow);

            // remove password index
            dataRow.remove(5);
        }

        return new DefaultTableModel(data, columnNames);
    }

    public void changePass(String username, String password) {
        try {
            String query = "UPDATE users SET password=? WHERE username='" + username + "'";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, password);
            prepStatement.setString(2, username);

            prepStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
