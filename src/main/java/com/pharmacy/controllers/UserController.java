package com.pharmacy.controllers;

import com.pharmacy.database.DatabaseInstance;
import com.pharmacy.models.UserModel;
import com.pharmacy.utils.EncryptionUtils;

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

    LogsController logsController;

    int logId;

    public UserController(int logId) {
        this.logId = logId;

        try {
            conn = new DatabaseInstance().getConnection();
            statement = conn.createStatement();
            logsController = new LogsController();
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
                EncryptionUtils encryptionUtils = new EncryptionUtils();
                byte[] secretKey = encryptionUtils.generateKeyBytes();

                String insertQuery = "INSERT INTO users (name,phone,username,password,user_type,secret_key) "
                    + "VALUES(?,?,?,?,?,?)";
                prepStatement = conn.prepareStatement(insertQuery);
                prepStatement.setString(1, userModel.getName());
                prepStatement.setString(2, userModel.getPhone());
                prepStatement.setString(3, userModel.getUsername());
                prepStatement.setBytes(4, encryptionUtils.encrypt(userModel.getPassword(), secretKey));
                prepStatement.setString(5, userModel.getType());
                prepStatement.setBytes(6, secretKey);

                prepStatement.executeUpdate();
                logsController.addLogEntry(logId, "Added new user: " + userModel.getName());
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Update existing user (password excluded!)
     * <p>
     * See {@link #updatePass(int id, String username, String password)} for updating
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
            logsController.addLogEntry(logId, "User updated: " + userModel.getId() + " - " + userModel.getName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Method to delete existing user
    public void deleteUser(int id, String username) {
        try {
            String query = "DELETE FROM users WHERE id=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setInt(1, id);

            prepStatement.executeUpdate();
            logsController.addLogEntry(logId, "Deleted user: " + username);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
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

    public int getUserId(String username) {
        int id = 0;

        try {
            String query = "SELECT id FROM users WHERE username='" + username + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                id = resultSet.getInt("id");
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return id;
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

    public ResultSet getTimesheet() {
        try {
            String query = """
                SELECT users.name as name, users.username AS username, in_time, out_time
                FROM timesheet
                INNER JOIN users ON timesheet.username=users.username
                ORDER BY in_time DESC;
                """;
            resultSet = statement.executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return resultSet;
    }

    public void addTimesheetEntry(UserModel userModel) {
        try {
            String query = """
                INSERT INTO timesheet (username, name, in_time, out_time) values(?,?,?,?);
                """;

            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userModel.getUsername());
            prepStatement.setString(2, userModel.getUsername());
            prepStatement.setString(3, userModel.getInTime());
            prepStatement.setString(4, userModel.getOutTime());

            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean matchPasswords(String username, String password) {
        try {
            String query = "SELECT password,secret_key FROM users WHERE username='"
                + username
                + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                byte[] secretKey = resultSet.getBytes("secret_key");
                byte[] encryptedPassword = resultSet.getBytes("password");

                String decryptedPass = new EncryptionUtils().decrypt(encryptedPassword, secretKey);

                return decryptedPass.equals(password);
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return false;
    }

    public void updatePass(int id, String username, String password) {
        try {
            EncryptionUtils encryptionUtils = new EncryptionUtils();
            byte[] secretKey = encryptionUtils.generateKeyBytes();

            byte[] encryptedPass = encryptionUtils.encrypt(password, secretKey);

            String query = "UPDATE users SET password=?, secret_key=? WHERE id='" + id + "'";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setBytes(1, encryptedPass);
            prepStatement.setBytes(2, secretKey);

            prepStatement.executeUpdate();
            logsController.addLogEntry(logId, "Password updated for user: " + username);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }
}
