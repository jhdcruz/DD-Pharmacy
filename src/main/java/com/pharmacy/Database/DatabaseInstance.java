package com.pharmacy.Database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;


/**
 * Retrieves connection for database and login verification.
 */
public class DatabaseInstance {

    static final String driver = "com.mysql.cj.jdbc.Driver";
    static final String url = "jdbc:mysql://localhost:3306/inventory";

    static String username;
    static String password;

    Properties prop;

    Connection conn = null;
    Statement statement = null;
    ResultSet resultSet = null;

    public DatabaseInstance() {
        try {
            // Username and Password saved as configurable properties
            // to allow changes without recompilation.
            prop = new Properties();
            prop.loadFromXML(new FileInputStream("lib/DBCredentials.xml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Get username and password from properties file
        username = prop.getProperty("username");
        password = prop.getProperty("password");

        try {
            Class.forName(driver);
            conn = DriverManager.getConnection(url, username, password);
            statement = conn.createStatement();
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
            conn = DriverManager.getConnection(url, username, password);

            System.out.println("Connected successfully.");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

        return conn;
    }

    /**
     * Login verification method
     *
     * @param username user login
     * @param password user password
     * @param userType user access type
     *
     * @return login state (boolean)
     */
    public boolean validateLogin(String username, String password, String userType) {
        String query = "SELECT * FROM users WHERE username='"
            + username
            + "' AND password='"
            + password
            + "' AND usertype='"
            + userType
            + "' LIMIT 1";

        try {
            resultSet = statement.executeQuery(query);
            if (resultSet.next()) return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return false;
    }
}