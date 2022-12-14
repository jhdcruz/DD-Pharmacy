package com.pharmacy.controllers

import com.pharmacy.database.DatabaseInstance
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class LogsController {
    private var connection: Connection? = null

    /**
     * System/application logs
     */
    init {
        try {
            connection = DatabaseInstance().getConnection()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }

    val logs: ResultSet?
        get() {
            var resultSet: ResultSet? = null

            try {
                val query = """
                SELECT users.username AS user_id,event,date
                FROM logs
                INNER JOIN users ON logs.user_id=users.id
                ORDER BY date DESC;
                """.trimIndent()

                val statement: Statement = connection!!.createStatement()
                resultSet = statement.executeQuery(query)
            } catch (sqlException: SQLException) {
                sqlException.printStackTrace()
            }

            return resultSet
        }


    fun addLogEntry(id: Int, event: String?) {
        try {
            val query = "INSERT INTO logs (user_id, event, date) values(?,?, DEFAULT)"

            val prepStatement: PreparedStatement = connection!!.prepareStatement(query)
            prepStatement.setInt(1, id)
            prepStatement.setString(2, event)

            prepStatement.executeUpdate()
        } catch (e: SQLException) {
            e.printStackTrace()
        }
    }
}
