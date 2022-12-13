package com.pharmacy.Controllers;

import com.pharmacy.Database.DatabaseInstance;
import com.pharmacy.Models.UserModel;
import com.pharmacy.Utils.EncryptionUtils;
import com.pharmacy.Views.UsersPage;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
    public void addUser(UserModel userModel) {
        try {
            String duplicateQuery = "SELECT * FROM users WHERE name='"
                + userModel.getName()
                + "' AND phone='"
                + userModel.getPhone()
                + "' AND user_type='"
                + userModel.getType()
                + "'";
            resultSet = statement.executeQuery(duplicateQuery);

            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null, "User already exists");
            } else {
                String encryptedPass = new EncryptionUtils().encrypt(userModel.getPassword());

                String insertQuery = "INSERT INTO users (name,phone,username,password,user_type) "
                    + "VALUES(?,?,?,?,?)";
                prepStatement = conn.prepareStatement(insertQuery);
                prepStatement.setString(1, userModel.getName());
                prepStatement.setString(2, userModel.getPhone());
                prepStatement.setString(3, userModel.getUsername());
                prepStatement.setString(4, encryptedPass);
                prepStatement.setString(5, userModel.getType());

                prepStatement.executeUpdate();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Update existing user (password excluded!)
     * <p>
     * See {@link #updatePass(int id, String password)} for updating
     * password
     *
     * @param userModel populated UserModel
     */
    public void updateUser(UserModel userModel) {

        try {
            String query = "UPDATE users SET username=?,name=?,phone=?,user_type=? WHERE id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userModel.getUsername());
            prepStatement.setString(2, userModel.getName());
            prepStatement.setString(3, userModel.getPhone());
            prepStatement.setString(4, userModel.getType());
            prepStatement.setInt(5, userModel.getId());

            prepStatement.executeUpdate();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Method to delete existing user
    public void deleteUser(String id) {
        try {
            String query = "DELETE FROM users WHERE id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, id);

            prepStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
        new UsersPage().loadDataSet();
    }

    // Method to retrieve data set to display in table
    public ResultSet getUsers() {
        try {
            String query = "SELECT id, name, username, phone, user_type FROM users";
            resultSet = statement.executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet searchUsers(String search) {
        try {
            String query = "SELECT * FROM users WHERE name LIKE '%" + search + "%' OR phone LIKE '%" + search + "%' OR user_type LIKE '%" + search + "%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet findUser(String username) {
        try {
            String query = "SELECT id, username, name, phone, user_type FROM users WHERE username='" + username + "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return resultSet;
    }

    public ResultSet getUserLogs() {
        try {
            String query = "SELECT users.name,userlogs.username,in_time,out_time FROM userlogs"
                + " INNER JOIN users on userlogs.username=users.username ORDER BY in_time DESC";
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

    public boolean matchPasswords(String username, String password) {
        try {
            String query = "SELECT password FROM users WHERE username='"
                + username
                + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String decryptedPass = new EncryptionUtils().decrypt(resultSet.getString("password"));

                return decryptedPass.equals(password);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return false;
    }

    public void updatePass(int id, String password) {
        try {
            String encryptedPass = new EncryptionUtils().encrypt(password);

            String query = "UPDATE users SET password=? WHERE id='" + id + "'";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, encryptedPass);

            prepStatement.executeUpdate();
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
