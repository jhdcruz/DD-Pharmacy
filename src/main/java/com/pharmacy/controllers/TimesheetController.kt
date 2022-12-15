package com.pharmacy.controllers

import com.pharmacy.database.DatabaseInstance
import com.pharmacy.models.UserModel
import java.sql.Connection
import java.sql.PreparedStatement
import java.sql.ResultSet
import java.sql.SQLException
import java.sql.Statement

class TimesheetController {
    private var connection: Connection? = null
    private var statement: Statement? = null

    init {
        try {
            connection = DatabaseInstance().getConnection()
            statement = connection!!.createStatement()
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    val timesheet: ResultSet?
        get() {
            val resultSet: ResultSet?

            try {
                val query = """
                SELECT users.name as name, users.username AS username, in_time, out_time
                FROM timesheet
                INNER JOIN users ON timesheet.username=users.username
                ORDER BY in_time DESC;
                """.trimIndent()

                resultSet = statement!!.executeQuery(query)
            } catch (e: SQLException) {
                throw SQLException(e)
            }

            return resultSet
        }

    fun addTimesheetEntry(userModel: UserModel) {
        try {
            val query = """
                INSERT INTO timesheet (username, name, in_time, out_time) values(?,?,?,?);
                """.trimIndent()

            val prepStatement: PreparedStatement = connection!!.prepareStatement(query)
            prepStatement.setString(1, userModel.username)
            prepStatement.setString(2, userModel.username)
            prepStatement.setString(3, userModel.inTime)
            prepStatement.setString(4, userModel.outTime)

            prepStatement.executeUpdate()
        } catch (e: SQLException) {
            throw SQLException(e)
        }
    }

    fun searchTimesheet(search: String): ResultSet? {
        val resultSet: ResultSet?

        try {
            val query = """
                SELECT users.name as name, users.username AS username, in_time, out_time
                FROM timesheet
                INNER JOIN users ON timesheet.username=users.username
                WHERE users.name LIKE ?
                OR users.username LIKE ?
                OR in_time LIKE ?
                OR out_time LIKE ?
                ORDER BY in_time DESC;
                """.trimIndent()

            val prepStatement: PreparedStatement = connection!!.prepareStatement(query)
            prepStatement.setString(1, "%$search%")
            prepStatement.setString(2, "%$search%")
            prepStatement.setString(3, "%$search%")
            prepStatement.setString(4, "%$search%")

            resultSet = prepStatement.executeQuery()
        } catch (e: SQLException) {
            throw SQLException(e)
        }

        return resultSet
    }
}
