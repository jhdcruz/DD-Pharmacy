package com.pharmacy.Database;

import com.pharmacy.Utils.FileResourceUtils;

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
        String query = "SELECT * FROM users WHERE username='"
            + username
            + "' AND password='"
            + password
            + "'";

        try {
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                return resultSet.getString("user_type");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return null;
    }
}
