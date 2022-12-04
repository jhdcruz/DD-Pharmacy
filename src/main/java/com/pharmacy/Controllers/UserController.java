
package com.pharmacy.Controllers;

import com.pharmacy.Database.DatabaseInstance;
import com.pharmacy.Models.UserModel;
import com.pharmacy.Views.UsersPage;
import org.apache.commons.text.WordUtils;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Vector;

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
                String insertQuery = "INSERT INTO users (name,phone,username,password,user_type) " +
                    "VALUES(?,?,?,?,?,?)";
                prepStatement = conn.prepareStatement(insertQuery);
                prepStatement.setString(1, userModel.getName());
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

    /**
     * Update existing user (password excluded!)
     * <p>
     * See {@link #updatePass(String username, String password)} for updating password
     *
     * @param userModel populated UserModel
     */
    public void updateUser(UserModel userModel) {

        try {
            String query = "UPDATE users SET name=?,phone=?,user_type=? WHERE username=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userModel.getName());
            prepStatement.setString(2, userModel.getPhone());
            prepStatement.setString(3, userModel.getType());
            prepStatement.setString(4, userModel.getUsername());

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

    public ResultSet searchUsers(String search) {
        try {
            String query = "SELECT * FROM users WHERE name LIKE '%" + search + "%' OR phone LIKE '%" + search + "%' OR user_type LIKE '%" + search + "%'";
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
            String query = "SELECT users.name,userlogs.username,in_time,out_time FROM userlogs" +
                " INNER JOIN users on userlogs.username=users.username ORDER BY in_time DESC";
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

    public void updatePass(String username, String password) {
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

    /**
     * Display database results and headers in a table.
     * <p>
     * this table model also removes the password column
     * compared to the DataTableModel class which displays all columns
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
            // replace underscores with spaces
            String columnName = metaData.getColumnName(col).replaceAll("_", " ");
            // capitalize the first letter of each word
            columnName = WordUtils.capitalizeFully(columnName);

            columnNames.add(columnName);
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

    /**
     * Display database results and headers in a table.
     * <p>
     * This table model parses the in_time and out_time columns
     * to a human-readable format
     *
     * @param resultSet database result
     * @return table model that contains data and the data columns
     * @throws SQLException SQL exception thrown when database error occurs
     */
    public DefaultTableModel buildUserLogsTable(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();

        // add column names to the table column headers
        for (int col = 1; col <= columnCount; col++) {
            // replace underscores with spaces
            String columnName = metaData.getColumnName(col).replaceAll("_", " ");
            // capitalize the first letter of each word
            columnName = WordUtils.capitalizeFully(columnName);

            columnNames.add(columnName);
        }


        Vector<Vector<Object>> data = new Vector<>();
        while (resultSet.next()) {
            Vector<Object> dataRow = new Vector<>();

            for (int col = 1; col <= columnCount; col++) {
                dataRow.add(resultSet.getObject(col));

                // replace time values with human-readable ones
                if (col == 3 || col == 4) {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a - MMM dd, yyyy", Locale.getDefault());
                    LocalDateTime dateTime = LocalDateTime.parse(dataRow.get(col - 1).toString());
                    dataRow.set(col - 1, dateTime.format(formatter));
                }
            }

            data.add(dataRow);
        }

        return new DefaultTableModel(data, columnNames);
    }
}
