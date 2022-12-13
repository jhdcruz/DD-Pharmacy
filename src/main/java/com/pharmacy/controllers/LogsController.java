package com.pharmacy.controllers;

import com.pharmacy.database.DatabaseInstance;

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
                SELECT users.username AS user_id,event,date
                FROM logs
                INNER JOIN users ON logs.user_id=users.id
                ORDER BY date DESC;
                """;

            resultSet = statement.executeQuery(query);
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }

        return resultSet;
    }

    public void addLogEntry(int id, String event) {
        try {
            String query = "INSERT INTO logs (user_id, event, date) values(?,?, DEFAULT)";
            prepStatement = connection.prepareStatement(query);
            prepStatement.setInt(1, id);
            prepStatement.setString(2, event);

            prepStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
