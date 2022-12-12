package com.pharmacy.Database;

import com.pharmacy.Controllers.EncryptionController;
import com.pharmacy.Utils.FileResourceUtils;

import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Retrieves connection for database and login verification.
 */
public class DatabaseInstance {

    FileResourceUtils fileResourceUtils;
    private static String username;
    private static String password;

    protected static String driver;
    protected static String url;

    Connection connection = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public DatabaseInstance() {
        fileResourceUtils = new FileResourceUtils();

        // Get database properties
        String resourceFile = "database/datasource.properties";
        username = fileResourceUtils.getProperty("datasource.username", resourceFile);
        password = fileResourceUtils.getProperty("datasource.password", resourceFile);
        driver = fileResourceUtils.getProperty("datasource.driver", resourceFile);
        url = fileResourceUtils.getProperty("datasource.url", resourceFile);

        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);

            statement = connection.createStatement();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Cannot establish connection to the database.", "Database error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Get database connection.
     *
     * @return Database connection session
     */
    public Connection getConnection() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return connection;
    }

    /**
     * Login verification method
     *
     * @param username user login
     * @param password user password
     * @return user role / user type
     */
    public String authUser(String username, String password) {
        try {
            String query = "SELECT username, password, user_type FROM users WHERE username='"
                + username
                + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String decryptedPass = new EncryptionController().decrypt(resultSet.getString("password"));

                if (resultSet.getString("username").equals(username) && decryptedPass.equals(password)) {
                    return resultSet.getString("user_type");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
