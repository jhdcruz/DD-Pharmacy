package com.pharmacy.Controllers;

import com.pharmacy.Database.DatabaseInstance;
import com.pharmacy.Models.UserModel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class LogsController {

    Connection connection = null;
    PreparedStatement prepStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;

    /**
     * System/application logs
     */
    public LogsController() {
        try {
            connection = new DatabaseInstance().getConnection();
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ResultSet getLogs() {
        try {
            String query = """
                SELECT users.username AS username,users.name AS name,event,date
                FROM logs
                INNER JOIN users ON logs.username=users.username
                ORDER BY date DESC;
                """;

            resultSet = statement.executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return resultSet;
    }

    public void addLogEntry(UserModel userModel) {
        try {
            String query = "INSERT INTO logs (username, name, event, date) values(?,?,?, DEFAULT)";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setString(1, userModel.getUsername());
            prepStatement.setString(2, userModel.getInTime());
            prepStatement.setString(3, userModel.getOutTime());

            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
